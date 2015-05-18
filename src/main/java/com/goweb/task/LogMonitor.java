package com.goweb.task;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goweb.ApplicationConfig;
import com.goweb.FtpClient;
import com.goweb.Monitor;
import com.goweb.entity.Log;
import com.goweb.service.ILogService;
import com.goweb.service.IMessageService;

/**
 * 
 * 日志监听
 * 
 * 
 *
 */
@Component("logMonitor")
public class LogMonitor {

	private static final Logger log = LogManager.getLogger(LogMonitor.class);

	@Autowired
	private IMessageService messageService;

	@Autowired
	private ILogService logService;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private Monitor monitor;

	private FtpClient ftpClient;
	
	public void run() {

		log.info("log monitor begins");

		// 连接ftp服务器
		ftpClient.connectServer();

		// 获取最新日志日期
		Log messageLog = logService.findOne("log_type", ftpClient.getLogType());

		if (messageLog == null) {
			messageLog = new Log();
			messageLog.setLogType(ftpClient.getLogType());
			messageLog.setComment(ftpClient.getIp() + ":" + ftpClient.getPort() + " logType:" + ftpClient.getLogType());
			messageLog.setOffset(0);
			messageLog.setOffsetCount(0);
			logService.save(messageLog);
			messageLog = logService.findOne("log_type", ftpClient.getLogType());
		}

		// 监听最新日志信息
		ftpClient.monitorLogOnLastestDate(messageLog);
		ftpClient.closeServer();

	}

	public void setFtpClient(FtpClient ftpClient) {
		this.ftpClient = ftpClient;
	}

}
