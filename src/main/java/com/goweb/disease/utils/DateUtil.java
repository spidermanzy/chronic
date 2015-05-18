package com.goweb.disease.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.goweb.disease.exception.BaseException;


/**
 * 
 * 时间工具类
 * 
 * @author yinsheng
 * 
 */
public class DateUtil {
	
	public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss SSS";
	
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	
	public static final String HH_mm_ss_SSS = "HH:mm:ss SSS";
	
	public static final String HH_mm_ss = "HH:mm:ss";
	
	public static final String HH_mm = "HH:mm";
	
	public static final String HHmm = "HHmm";
	
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	public static final String ddMMMyy = "ddMMMyy";
	
	public static final char PLUS_NUMBER = '+';
	
	public static final char MINUS_NUMBER = '-';
	
	
	public static String format(Date date,String format){
		if(format == null){
			format = yyyy_MM_dd_HH_mm_ss_SSS;
		}
		DateFormat dateFormat =  new SimpleDateFormat(format);
		
		return dateFormat.format(date);
	}
	
	public static String format(Date date){
		
		DateFormat dateFormat =  new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss_SSS);
		
		return dateFormat.format(date);
	}
	
	public static Date parseDate(String date,String format){
		if(format == null){
			format = yyyy_MM_dd_HH_mm_ss_SSS;
		}
		DateFormat dateFormat =  new SimpleDateFormat(format,Locale.US);
		
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new BaseException(e.getMessage());
		}
	}

	public static Time parseTime(String date,String format){
		if(format == null){
			format = HH_mm_ss_SSS;
		}
		DateFormat dateFormat =  new SimpleDateFormat(format,Locale.US);
		
		
		try {
			Date tmp = dateFormat.parse(date);
			
			if(tmp == null){
				return null;
			}
			return new Time(tmp.getTime());
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
	}
	
	public static Date parseDateByAll(String date){
		
		Date result = null;
		
		try {
			result = parseDate(date,ddMMMyy);
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		date = date.replaceAll("[a-zA-Z]+", " ");
		
		try {
			result = parseDate(date,yyyy_MM_dd_HH_mm_ss_SSS);
		}catch(Exception e){
		}
		
		
		if(result != null){
			return result;
		}
		
		try {
			result = parseDate(date,yyyy_MM_dd_HH_mm_ss);
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		try {
			result = parseDate(date,yyyy_MM_dd);
		}catch(Exception e){
		
		}
		
		if(result != null){
			return result;
		}
		
		try {
		result = parseDate(date,HH_mm_ss_SSS);
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		try {
		result = parseDate(date,HH_mm_ss);
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		try {
		result = parseDate(date,HH_mm);
		}catch(Exception e){
			
		}
		
		if(result != null){
			return result;
		}
		
		try {
			result = parseDate(date,HHmm);
		}catch(Exception e){
			throw new BaseException(e);
		}
		
		if(result != null){
			return result;
		}
		
		
		
		return result;
		
	}
	
	public static Time parseTimeByAll(String time){
		
		Time result = null;
		
		try{
		result = parseTime(time,HH_mm_ss_SSS);
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		try{
		result = parseTime(time,HH_mm_ss);
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		try{
		result = parseTime(time,HH_mm);
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		try{
		result = parseTime(time,HHmm);
		}catch(Exception e){
			throw new BaseException(e);
		}
		
		if(result != null){
			return result;
		}
		
		return result;
		
	}
	
	/**
	 * 时间的时区转化
	 */
	public static Date convertDateByTimeZone(Date origTime,String origTimeZone,String destTimeZone){
		
		if(origTimeZone == null || destTimeZone == null || "".equals(origTimeZone.trim()) || "".equals(destTimeZone.trim()) || origTime == null){
			return null;
		}
		
		Long time = origTime.getTime();
		Long origValue = null;
		Long destValue = null;
		if(PLUS_NUMBER == origTimeZone.charAt(0)){
			origValue = Long.parseLong(origTimeZone.substring(1));
		}else if(MINUS_NUMBER == origTimeZone.charAt(0)){
			origValue = Long.parseLong(origTimeZone);
		}else{
			origValue = Long.parseLong(origTimeZone);
		}
		
		if(PLUS_NUMBER == destTimeZone.charAt(0)){
			destValue = Long.parseLong(destTimeZone.substring(1));
		}else if(MINUS_NUMBER == destTimeZone.charAt(0)){
			destValue = Long.parseLong(destTimeZone);
		}else{
			destValue = Long.parseLong(destTimeZone);
		}
		
		Long resultTime = time - 1000l*60l*60l*origValue + 1000l*60l*60l*destValue;
		
		return new Date(resultTime);
	}
	
	/**
	 * 转化为其他时间
	 * @param date
	 * @param formate
	 * @return
	 * @throws ParseException 
	 */
	public static Date covertOtherDate(Date date,String format) {
		
		DateFormat dateFormat =  new SimpleDateFormat(format);
		
		String strDate =  dateFormat.format(date);
		
		Date result = null;
		
		try {
			result = dateFormat.parse(strDate);
		} catch (ParseException e) {
			throw new BaseException(e);
		}
		
		return result;
	}
	
	/**
	 * 获取时间
	 */
	public static Time getTime(Date date) {
		
		return new Time(date.getTime());
	}
	
	/**
	 * 时间的时区转化
	 */
	public static Time convertTimeByTimeZone(Time origTime,String origTimeZone,String destTimeZone){
		
		if(origTimeZone == null || destTimeZone == null || "".equals(origTimeZone.trim()) || "".equals(destTimeZone.trim()) || origTime == null){
			return null;
		}
		
		Date date = convertDateByTimeZone(new Date(origTime.getTime()),origTimeZone,destTimeZone);
		
		return getTime(date);
	}
	
}
