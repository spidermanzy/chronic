package com.goweb.disease;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.goweb.disease.entity.Log;
import com.goweb.disease.entity.Message;
import com.goweb.disease.service.ILogService;
import com.goweb.disease.service.IMessageService;
import com.goweb.disease.utils.DateUtil;

/**
 * 
 * FTP客户端
 * 
 * @author yinsheng
 *
 */
public class FtpClient {

	private static final Logger logger = LogManager.getLogger(FtpClient.class);

	private String ip;

	private String port;

	private String username;

	private String password;

	private String monitorDirectory;

	private String fileName;

	private FTPClient ftpClient;

	private String logType;

	@Autowired
	private IMessageService messageService;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private ILogService logService;

	@Autowired
	private Monitor monitor;

	/**
	 * @param ip
	 * @param port
	 * @param userName
	 * @param userPwd
	 * @param path
	 * @throws SocketException
	 * @throws IOException function:连接到服务器
	 */
	public void connectServer() {
		ftpClient = new FTPClient();
		try {
			// 连接
			ftpClient.connect(ip, Integer.parseInt(port));
			// 登录
			ftpClient.login(username, password);
			if (monitorDirectory != null && monitorDirectory.length() > 0) {
				// 跳转到指定目录
				ftpClient.changeWorkingDirectory(monitorDirectory);
			}
			logger.info("connect ftp success");
		} catch (SocketException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * @throws IOException function:关闭连接
	 */
	public void closeServer() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
				logger.info("close ftp success");
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * @param path
	 * @return function:读取指定目录下的文件名
	 * @throws IOException
	 */
	public List<String> getFileList(String path) {
		List<String> fileLists = new ArrayList<String>();
		// 获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		try {

			ftpFiles = ftpClient.listFiles(path);

			for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
				FTPFile file = ftpFiles[i];
				if (file.isFile()) {
					fileLists.add(file.getName());
				}
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return fileLists;
	}

	public List<String> getFileList() {
		return getFileList("/");
	}

	// 获取最新的日志文件名称
	public Date getLastestLogDate() {

		if (ftpClient.isConnected()) {
			closeServer();
			connectServer();
		}

		List<String> nameList = getFileList();

		if (nameList == null || nameList.size() == 0) {
			return null;
		}

		Date date = null;
		for (String tmp : nameList) {
			if (!tmp.contains(fileName)) {
				continue;
			}
			Date tmpDate = DateUtil.parseDate(tmp.substring(fileName.length(), tmp.length()), DateUtil.yyyy_MM_dd);
			if (date == null) {
				date = tmpDate;
				continue;
			}
			if (date.getTime() < tmpDate.getTime()) {
				date = tmpDate;
				continue;
			}

		}

		return date;
	}

	public Message saveLogOnDate(String line, Log messageLog) {

		if (line == null || "".equals(line.trim())) {
			return null;
		}

		Message message = monitor.parseMessage(line);

		if (message == null) {
			return null;
		}
		logger.debug("读取报文行：" + line);
		messageService.saveSingleMessage(message);

		logService.update(messageLog);

		return message;

	}

	// 监听最新日期的日志
	public void monitorLogOnLastestDate(Log messageLog) {

		InputStream is = null;
		int tmp = -1;
		byte[] tmpBytes = new byte[2000000];
		int i = 0;
		int linenum = 0;
		try {
			try {
				is = ftpClient.retrieveFileStream(fileName);

				Long realOffset = 0l;
				for (int j = 0; j < messageLog.getOffsetCount(); j++) {
					realOffset = is.skip(Long.MAX_VALUE);
					if (realOffset < messageLog.getOffset()) {
						// 说明重新开了一个日志文件，需要将offset重置为0
						messageLog.setOffset(0l);
						messageLog.setOffsetCount(0);
						logService.update(messageLog);
						logger.info("根据realOffset<Long.MAX_VALUE)判断，日志文件内容已更改，更新日志offset信息");
						break;
					}
				}
				realOffset = is.skip(messageLog.getOffset());
				if (realOffset < messageLog.getOffset()) {
					// 说明重新开了一个日志文件，需要将offset重置为0
					messageLog.setOffset(0l);
					messageLog.setOffsetCount(0);
					logService.update(messageLog);
					logger.info("根据realOffset<offset)判断，日志文件内容已更改，更新日志offset信息");
				}

			} catch (IOException e2) {
				logger.debug(ExceptionUtils.getFullStackTrace(e2));
			}
			Long offset = messageLog.getOffset();
			while ((tmp = is.read()) != -1) {
				tmpBytes[i++] = (byte) tmp;

				offset++;
				if (offset == Long.MAX_VALUE) {
					messageLog.setOffsetCount(messageLog.getOffsetCount() + 1);
					offset = 0l;
				}

				// 判断行结尾
				if ((char) tmp == '\n') {
					byte[] lineBytes = new byte[i];
					for (int j = 0; j < i; j++) {
						lineBytes[j] = tmpBytes[j];
					}

					String line = new String(lineBytes);
					if (linenum++ == 0 && messageLog.getOffset() == 0) {
						// 根据日志文件的第一行是否与数据库中存储的一致，判断是否是一个文件
						if (StringUtils.isEmpty(messageLog.getFirstline())) {
							messageLog.setFirstline(line);
							logService.update(messageLog);
						} else if (!messageLog.getFirstline().equals(line)) {
							logger.info("根据日志第一行判断，日志文件内容已更改，更新日志offset信息并结束本次job");
							messageLog.setOffset(0);
							messageLog.setOffsetCount(0);
							messageLog.setFirstline(line);
							logService.update(messageLog);
							break;
						}
					}

					// 保存日志监听信息
					messageLog.setOffset(offset);
					Message message = saveLogOnDate(line, messageLog);

					Monitor.updateLastestDataDatetime(message);

					tmpBytes = new byte[2000000];
					i = 0;
				}
			}
			ftpClient.getReply();
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				logger.debug(ExceptionUtils.getFullStackTrace(e1));
			}
		}

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMonitorDirectory() {
		return monitorDirectory;
	}

	public void setMonitorDirectory(String monitorDirectory) {
		this.monitorDirectory = monitorDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

}
