package com.goweb.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goweb.ApplicationConfig;
import com.goweb.Monitor;
import com.goweb.entity.Message;
import com.goweb.service.ILogService;
import com.goweb.service.IMessageService;
import com.goweb.utils.DateUtil;
import com.goweb.web.vo.RealTimeDataVo;
import com.goweb.web.vo.RealTimeVo;

/**
 * 登陆Controller
 * 
 * 
 */
@Controller
@RequestMapping(value = "/realtime")
public class MonitorController {
	
	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private ILogService logService;
	
	@Autowired
	private Monitor monitor;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	Logger logger = LogManager.getLogger(MonitorController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "realtime/index";
	}
	
	@RequestMapping(value = "/monitor.json",method = RequestMethod.POST,consumes={"application/json"},produces={"application/json"})
	public @ResponseBody RealTimeVo monitor(@RequestBody RealTimeVo realTimeVo) {

//		Date maxDatetime = null;
//		for (String tmp : applicationConfig.clientLogTypeList) {
//			Log messageLog = logService.findOne("log_type", tmp);
//			if (messageLog == null) {
//				continue;
//			}
//			String logContent = messageLog.getContent();
//			if (logContent == null) {
//				continue;
//			}
//			Date tmpDatetime = DateUtil.parseDate(logContent, applicationConfig.getDatetimeFormat());
//			if (maxDatetime == null) {
//				maxDatetime = tmpDatetime;
//			} else if (maxDatetime.getTime() < tmpDatetime.getTime()) {
//				maxDatetime = tmpDatetime;
//			}
//		}
//
//		if (maxDatetime == null) {
//			return null;
//		}
//		realTimeVo.setCurrentDate(DateUtil.parseDate(DateUtil.format(maxDatetime, DateUtil.yyyy_MM_dd),
//				DateUtil.yyyy_MM_dd));
//		realTimeVo.setLastestHour(maxDatetime.getHours());

		//List<Message> messageList = messageService.getMessageOnDay(realTimeVo.getCurrentDate());
		
		
		try {
			List<Message> messageList = messageService.getMessageOnDay(new Date());

			List<RealTimeDataVo> realTimeList = new ArrayList<RealTimeDataVo>();
			for (Message message : messageList) {
				monitor.updateMonitorData(message, realTimeList);
			}

			// 将0值添上
			List<RealTimeDataVo> realTimeList2 = new ArrayList<RealTimeDataVo>();
			int interval = applicationConfig.getDataMonitorInterval();
			for (int i = 1; i < realTimeList.size(); i++) {
				RealTimeDataVo nowV = realTimeList.get(i);
				RealTimeDataVo preV = realTimeList.get(i - 1);
				if (i == 1)
					realTimeList2.add(preV);
				long sub = preV.getMonitor().getTime() - nowV.getMonitor().getTime();
				long num = sub / (interval * 60 * 1000);
				for (int j = 1; j < num; j++) {
					RealTimeDataVo temp = new RealTimeDataVo();
					temp.setCount(0L);
					temp.setMonitor(new Date(preV.getMonitor().getTime() - j * (interval * 60 * 1000)));
					realTimeList2.add(temp);
				}
				realTimeList2.add(nowV);
			}
			
			if(realTimeList.size()==1)
				realTimeList2 = realTimeList;
			
			Date now = new Date();
			
			List<RealTimeDataVo> realTimeList3 = new ArrayList<RealTimeDataVo>();
			//将前面的0值也添上
			RealTimeDataVo first = realTimeList2.get(realTimeList2.size()-1);
			String nowStr = DateUtil.format(now, DateUtil.yyyy_MM_dd);
			Date begin = DateUtil.parseDate(nowStr + " 00:00:00 000", DateUtil.yyyy_MM_dd_HH_mm_ss_SSS);
			for (int i = 0; (begin.getTime() + i * (interval * 60 * 1000)) < first.getMonitor().getTime(); i++) {
				RealTimeDataVo temp = new RealTimeDataVo();
				temp.setCount(0L);
				temp.setMonitor(new Date(begin.getTime() + i * (interval * 60 * 1000)));
				realTimeList3.add(temp);
			}

			for (int i = realTimeList2.size() - 1; i >= 0; i--) {
				realTimeList3.add(realTimeList2.get(i));
			}

			// 将最后一次有值到最近的0值添上
			RealTimeDataVo last = realTimeList2.get(0);
			for (int i = 1; (last.getMonitor().getTime() + i * (interval * 60 * 1000)) <= now.getTime(); i++) {
				RealTimeDataVo temp = new RealTimeDataVo();
				temp.setCount(0L);
				temp.setMonitor(new Date(last.getMonitor().getTime() + i * (interval * 60 * 1000)));
				realTimeList3.add(temp);
			}

			realTimeVo.setRealTimeData(realTimeList3);
			return realTimeVo;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	@RequestMapping(value = "/heartbeat.json",method = RequestMethod.POST,consumes={"application/json"},produces={"application/json"})
	public @ResponseBody RealTimeVo heartbeat(@RequestBody RealTimeVo realTimeVo) {
		
		if(realTimeVo == null){
			realTimeVo =  new RealTimeVo();
		}
		
		Date heartBeatNowDatetime = Monitor.lastestDataDatetime ;
		
		Date heartBeatLastestDate = realTimeVo.getHeartBeatLastestDatetime();
		if(heartBeatLastestDate == null){
			realTimeVo.setHeartBeatLastestDatetime(heartBeatNowDatetime);
			if(heartBeatNowDatetime != null){
				realTimeVo.setIsHeartBeat(true);
			}
			return realTimeVo;
		}
		
		if(heartBeatNowDatetime.getTime() == heartBeatLastestDate.getTime() && (new Date().getTime() >= (Monitor.recordLastestDataDatetime.getTime() + applicationConfig.getLastestDataInterval()*60*1000))){
			realTimeVo.setIsHeartBeat(false);
			return realTimeVo;
		}
		
		realTimeVo.setHeartBeatLastestDatetime(heartBeatNowDatetime);
		realTimeVo.setIsHeartBeat(true);
		
		return realTimeVo;
	}

}
