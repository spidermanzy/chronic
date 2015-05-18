package com.goweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goweb.entity.Message;
import com.goweb.mapper.JsonMapper;
import com.goweb.utils.DateUtil;
import com.goweb.web.vo.RealTimeDataVo;

/**
 * 
 * 监控
 * 
 *
 */
@Component
public class Monitor {
	
	@Autowired
	private  ApplicationConfig applicationConfig;
	
	public static final Pattern dataPattern = Pattern.compile("\\{\"destAddr\"[\\s\\S]*\"oriAddr\"[\\s\\S]*\\}");
	
	public static final Pattern datetimePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}");

	public static Date lastestDataDatetime = null;
	
	public static Date recordLastestDataDatetime = null;
	
	
	public  Message parseMessage(String line){
		
		if(line == null || "".equals(line.trim())){
			return null;
		}
		
		Date datetime = null;
		try{
			datetime = parseLogDatetime(line);
		}catch(Exception e){
			return null;
		}
		
		//System.out.println(line);
		
		if(datetime == null){
			return null;
		}
		Matcher matcher = dataPattern.matcher(line);
		if(!matcher.find()){
			return null;
		}
		
		String jsonData = matcher.group();
		
		JsonMapper jsonMapper = new JsonMapper();
		Message message = null;
		try{
			message = jsonMapper.fromJson(jsonData, Message.class);
		}catch(Exception e){
			return null;
		}
		if(message == null){
			return null;
		}
		
		message.setLogDate(datetime);
		
		
		
		return message;
	}
	
	
	public  Date parseLogDatetime(String line){
		
		if(line == null || "".equals(line.trim())){
			return null;
		}
		
		Date datetime = null;
		try{
			Matcher m = datetimePattern.matcher(line);
			if(m.find()){
				datetime =  DateUtil.parseDate(m.group(), applicationConfig.getDatetimeFormat());
			}
		}catch(Exception e){
			return null;
		}
		
		return datetime;
	}

	
	//更新监听结果显示数据
	public void updateMonitorData(Message message,List<RealTimeDataVo> monitorDataList){
		
		if(message == null){
			return;
		}
		
		Date logDate = message.getLogDate();
		Long logTimeL = logDate.getTime();
		
		int dataMonitorInterval = applicationConfig.getDataMonitorInterval();
		Long currentDateL = logTimeL%(dataMonitorInterval*60*1000) == 0 ? logTimeL : logTimeL-(logTimeL%(dataMonitorInterval*60*1000))+dataMonitorInterval*60*1000;
		RealTimeDataVo monitorData = null;
		if(monitorDataList.size() == 0){
			monitorData = new RealTimeDataVo();
			monitorData.setCount(1l);
			monitorData.setMonitor(new Date(currentDateL));
			monitorDataList.add(monitorData);
			return ;
		}
		
		for(int j=monitorDataList.size()-1;j>=0;j--){
			RealTimeDataVo tmp = monitorDataList.get(j);
			if(tmp.getMonitor().getTime() == currentDateL){
				monitorData = tmp;
				break;
			}
		}
		
		if(monitorData == null){
			monitorData = new RealTimeDataVo();
			monitorData.setCount(1l);
			monitorData.setMonitor(new Date(currentDateL));
			monitorDataList.add(monitorData);
		}else{
			monitorData.setCount(monitorData.getCount()+1l);
		}
		
	}
	
	//更新最新的数据日期
	public static void  updateLastestDataDatetime(Message message){
		
		if(message == null){
			return;
		}
		
		Date logDattime = message.getLogDate();
		
		if(lastestDataDatetime == null){
			lastestDataDatetime = logDattime;
			return;
		}
		
		if(logDattime.getTime() > lastestDataDatetime.getTime()){
			lastestDataDatetime = logDattime;
			recordLastestDataDatetime = new Date();
		}
		
	}
	

}
