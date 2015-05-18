package com.goweb.web.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 实时监听VO
 * 
 * @author yinsheng
 *	
 */
public class RealTimeVo {
	
	Date currentDate;
	
	List<RealTimeDataVo> realTimeData = new ArrayList<RealTimeDataVo>();
	
	Date heartBeatLastestDatetime;
	
	Boolean isHeartBeat;
	
	int lastestHour;
	
	
	
	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public List<RealTimeDataVo> getRealTimeData() {
		return realTimeData;
	}

	public void setRealTimeData(List<RealTimeDataVo> realTimeData) {
		this.realTimeData = realTimeData;
	}
	
	public Date getHeartBeatLastestDatetime() {
		return heartBeatLastestDatetime;
	}

	public void setHeartBeatLastestDatetime(Date heartBeatLastestDatetime) {
		this.heartBeatLastestDatetime = heartBeatLastestDatetime;
	}

	public Boolean getIsHeartBeat() {
		return isHeartBeat;
	}

	public void setIsHeartBeat(Boolean isHeartBeat) {
		this.isHeartBeat = isHeartBeat;
	}

	public int getLastestHour() {
		return lastestHour;
	}

	public void setLastestHour(int lastestHour) {
		this.lastestHour = lastestHour;
	}
	
	
	
}
