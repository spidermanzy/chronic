package com.goweb.disease.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.goweb.disease.entity.Message;
import com.goweb.disease.exception.BaseException;
import com.goweb.disease.model.page.Page;
import com.goweb.disease.web.vo.StaticsReportVo;


/**
 * 
 * 报表工具类
 * 
 * @author yinsheng
 * 
 */
public class ReportUtil {
	
	public static final String DOWNLANDTYPE_CSV="csv";
	
	public static final String DOWNLANDTYPE_XLS="xls";
	
	public static String  DOWNLOADFILE_DIR = "downloadfile";
	
	//消息报表
	public static List<String> messageReportHeadList = new ArrayList<String>();
	
	public static List<String> messageReportPropertyList = new ArrayList<String>();
	
	public static String messageReportName = "log-monitor-message";
	
	public static String MSGCONTENT_PROPERTY = "msgContent";
	
	//统计报表01
	
	public static List<String> static01reportHeadList = new ArrayList<String>();
	
	public static List<String> static01reportPropertyList = new ArrayList<String>();
	
	public static String static01reportName = "log-monitor-static01";
	
	
	static {
		
		
		messageReportHeadList.add("源地址");
		messageReportHeadList.add("目标地址");
		messageReportHeadList.add("消息类型");
		messageReportHeadList.add("时间");
		messageReportHeadList.add("消息内容");
		
		messageReportPropertyList.add("destAddr");
		messageReportPropertyList.add("oriAddr");
		messageReportPropertyList.add("msgType");
		messageReportPropertyList.add("logDate");
		messageReportPropertyList.add("msgContent");
		
		static01reportHeadList.add("源地址");
		static01reportHeadList.add("目标地址");
		static01reportHeadList.add("消息类型");
		static01reportHeadList.add("日期");
		static01reportHeadList.add("数量");
		
		static01reportPropertyList.add("destAddr");
		static01reportPropertyList.add("oriAddr");
		static01reportPropertyList.add("msgType");
		static01reportPropertyList.add("date");
		static01reportPropertyList.add("count");
		
		
		
	}
	
//	public static List<StaticsReportVo> convertStaticsReportVo(List<Message> messageList){
//		
//		if(messageList == null){
//			return null;
//		}
//		
//		List<StaticsReportVo> result = new ArrayList<StaticsReportVo>();
//		for(Message tmp01 :messageList){
//			boolean isFind = false;
//			for(StaticsReportVo tmp02 :result){
//				if(tmp01.getDestAddr() == null || tmp01.getOriAddr() == null || tmp01.getMsgType() == null || tmp01.getLogDate() == null){
//					continue;
//				}
//				if(tmp01.getDestAddr().equals(tmp02.getDestAddr()) && tmp01.getOriAddr().equals(tmp02.getOriAddr())
//					&& tmp01.getMsgType().equals(tmp02.getMsgType()) && DateUtil.format(tmp01.getLogDate(), DateUtil.yyyy_MM_dd).equals(DateUtil.format(tmp02.getDate(), DateUtil.yyyy_MM_dd))){
//					tmp02.setCount(tmp02.getCount()+1l);
//					isFind = true;
//					break;
//				}
//			}
//			if(!isFind){
//				StaticsReportVo reportSticsVo = new StaticsReportVo();
//				reportSticsVo.setDestAddr(tmp01.getDestAddr());
//				reportSticsVo.setOriAddr(tmp01.getOriAddr());
//				reportSticsVo.setMsgType(tmp01.getMsgType());
//				reportSticsVo.setDate(DateUtil.parseDate(DateUtil.format(tmp01.getLogDate(), DateUtil.yyyy_MM_dd), DateUtil.yyyy_MM_dd));
//				reportSticsVo.setCount(1l);
//				result.add(reportSticsVo);
//			}
//		}
//		
//		
//		return result;
//		
//	}
	
	public static List<StaticsReportVo> convertStaticsReportVoOnSearch(List<Message> messageList){
		
		if(messageList == null){
			return null;
		}
		
		List<StaticsReportVo> result = new ArrayList<StaticsReportVo>();
		for(Message tmp01 :messageList){
			boolean isFind = false;
			for(StaticsReportVo tmp02 :result){
				if(tmp01.getDestAddr() == null || tmp01.getOriAddr() == null || tmp01.getMsgType() == null || tmp01.getLogDate() == null){
					continue;
				}
				if(tmp01.getDestAddr().equals(tmp02.getDestAddr()) && tmp01.getOriAddr().equals(tmp02.getOriAddr())
					&& tmp01.getMsgType().equals(tmp02.getMsgType())){
					tmp02.setCount(tmp02.getCount()+1l);
					isFind = true;
					break;
				}
			}
			if(!isFind){
				StaticsReportVo reportSticsVo = new StaticsReportVo();
				reportSticsVo.setDestAddr(tmp01.getDestAddr());
				reportSticsVo.setOriAddr(tmp01.getOriAddr());
				reportSticsVo.setMsgType(tmp01.getMsgType());
				reportSticsVo.setCount(1l);
				result.add(reportSticsVo);
			}
		}
		
		
		return result;
		
	}
	

	
	public static Date parseSearchDate(String time){
		
		Date result = null;
		
		try{
			result = DateUtil.parseTime(time,"yyMMdd hhmmss");
		}catch(Exception e){
		}
		if(result != null){
			return result;
		}
		
		try{
			result = DateUtil.parseTime(time,"yyMMdd hhmmss");
		}catch(Exception e){
			
		}
		if(result != null){
			return result;
		}
		
		try{
			result = DateUtil.parseTime(time,"yyMMdd hhmm");
		}catch(Exception e){
		}
		if(result != null){
			return result;
		}
		
		try{
			result = DateUtil.parseTime(time,"yyMMdd hh");
		}catch(Exception e){
		}
		
		if(result != null){
			return result;
		}
		
		try{
			result = DateUtil.parseDate(time,"yyMMdd");
		}catch(Exception e){
			throw new BaseException(e);
		}
		
		return result;
		
	}
	
	public static String generateMessageReportOnTable(Page<Message> page){
		
		StringBuffer sb = new StringBuffer("");
		
		sb.append("<table class='table table-hover' style='margin: 0px;'>");
		sb.append("<thead><tr>");
		for(String tmp : messageReportHeadList){
			sb.append("<th>"+tmp+"</th>");
		}
		sb.append("</tr></thead>");
		
		List<Message> list = page.getElements();
		if(list!= null && list.size() > 0){
			sb.append("<tbody>");
			int i =0;
			for(Message tmp:list){
				
				sb.append("<tr>");
				for(String str: messageReportPropertyList){
					// 若该字段是需要导出的字段则写入Excel  
	                Object o = ReflectionUtil.getFieldValue(tmp, str);
	                
	                String value = o == null ? "" : o.toString();  
	                
	                if(MSGCONTENT_PROPERTY.equals(str)){
	                	// 设置cell的值
		                value = value.replace("\r\n", " ");
		                value = value.replace("\n", " ");
		                
		                if(value.length()> 20){
		                	sb.append("<td><div class='msg-content-div'  style='cursor: pointer;' value='"+value+"'>"+value.substring(0,20)+"..."+"</td>");
		                }else{
		                	sb.append("<td>"+value+"</td>");
		                }
		                
	                }else{
	                	sb.append("<td>"+value+"</td>");
	                }
	                
	            }
				sb.append("</tr>");
			}
			sb.append("</tbody>");
		}
		
		sb.append("</table>");
		return sb.toString();
	}
	
	public static List changeListTimeToString(List<Message> list) {
		SimpleDateFormat sf = new SimpleDateFormat("yyMMdd HHmmss");
		List<HashMap> ret = new ArrayList<HashMap>();
		for (Message m : list) {
			HashMap map = new HashMap();
			map.put("destAddr", m.getDestAddr());
			map.put("logDate", sf.format(m.getLogDate()));
			map.put("msgContent", m.getMsgContent());
			map.put("msgType", m.getMsgType());
			map.put("oriAddr", m.getOriAddr());
			ret.add(map);
		}
		return ret;
	}
	
	public static String generateStatics01reportOnTable(Page<StaticsReportVo> page){
		
		StringBuffer sb = new StringBuffer("");
		
		sb.append("<table class='table table-hover' style='margin: 0px;'>");
		sb.append("<thead><tr>");
		for(String tmp : static01reportHeadList){
			sb.append("<th>"+tmp+"</th>");
		}
		sb.append("</tr></thead>");
		
		List<StaticsReportVo> list = page.getElements();
		if(list!= null && list.size() > 0){
			sb.append("<tbody>");
			int i =0;
			for(StaticsReportVo tmp:list){
				
				sb.append("<tr>");
				for(String str: static01reportPropertyList){
					// 若该字段是需要导出的字段则写入Excel  
	                Object o = ReflectionUtil.getFieldValue(tmp, str);
	                
	                String value = getStringValue(o);
	                
	                sb.append("<td>"+value+"</td>");
	                
	            }
				sb.append("</tr>");
			}
			sb.append("</tbody>");
		}
		
		sb.append("</table>");
		return sb.toString();
	}
	
	/** 
     * 创建TXT或者CSV文件 
     *  
     * @param list 查询出的结果 
     * @param head 表头 
     * @param proerty 需要导出的列（与head对应） 
     * @param fileName 文件名 
     * @return 
     * @throws Exception 
     */  
      
    public static File createCsvFile(List list, List<String> head, List<String> proerty,String fileName,String fileDir) throws Exception {  
        
    	File file = new File(fileDir+"/"+fileName);
    	
    	StringBuilder sb = new StringBuilder();  
        for (String str : head) {  
            sb.append("\t" + str + ",");  
        }  
        sb.append("\r\n");  
        for (Object obj : list) {  
            Class className = obj.getClass();  
            // 反射所有字段  
            @SuppressWarnings("unused")  
            Field[] fields = className.getDeclaredFields();  
  
            for (String str : proerty) {  
                // 若该字段是需要导出的字段则写入Excel  
                Object o = ReflectionUtil.getFieldValue(obj, str);
                
                String value = getStringValue(o);
                	
                
                sb.append("\t" + value + ","); 
                
                 
            }  
            sb.append("\r\n");  
  
        }
        FileOutputStream fos = new FileOutputStream(file);
        OutputStream outputStream = new BufferedOutputStream(fos); 
	    outputStream.write(sb.toString().getBytes());  
	    outputStream.flush();  
	    outputStream.close();
	    
	    return file;
	    
	}  
  
    /** 
     * 创建excel文件 
     *  
     * @param list 
     * @param head 
     * @param proerty 
     * @param fileName 
     * @return 
     * @throws Exception 
     */  
    public static File createExcelFile(List list, List<String> head, List<String> proerty, String fileName,String fileDir) throws Exception {
    	
    	File file = new File(fileDir+"/"+fileName);
    	FileOutputStream fos = new FileOutputStream(file);
  
        int line = 0;  
        int row = 0;  
        int sheetNum = 1;  
        // 表头格式  
        WritableCellFormat wcfF = new jxl.write.WritableCellFormat();  
        wcfF.setAlignment(jxl.format.Alignment.CENTRE);  
        wcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);  
        WritableFont font = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK); 
        wcfF.setFont(font);
        
        WritableWorkbook wwb = Workbook.createWorkbook(fos);
        
        WritableSheet ws =wwb.createSheet(String.valueOf(sheetNum), sheetNum);
        
        // 设置冻结首行  
        ws.getSettings().setVerticalFreeze(1);  
        ws.getSettings().setFitWidth(100);  
        // 数据格式  
        WritableCellFormat dateDcfF = new jxl.write.WritableCellFormat();  
        dateDcfF.setWrap(true);  
        dateDcfF.setAlignment(jxl.format.Alignment.CENTRE);  
        dateDcfF.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);  
  
        // 控制列宽  
        ws.setColumnView(0, 25);  
        ws.setColumnView(1, 25);  
        ws.setColumnView(2, 15);  
        ws.setColumnView(3, 25);  
        ws.setColumnView(4, 80);  
       
        sheetNum++;  
        // 写入表头  
        for (String str : head) {  
            Label label = new Label(line, 0, str, wcfF);  
            ws.addCell(label);  
            line++;  
        }  
        row++;  
        // 写入数据  
        for (Object obj : list) {  
            line = 0;  
            Class className = obj.getClass();  
            // 反射所有字段  
            Field[] fields = className.getDeclaredFields();  
  
            for (String str : proerty) {  
                for (Field field : fields) {  
                    // 若该字段是需要导出的字段则写入Excel  
                    if (str.equals(field.getName())) {  
                        // 修改相应filed的权限  
                        boolean accessFlag = field.isAccessible();  
                        field.setAccessible(true);  
  
                        // 读取对象中相应的属性的值
                        Object object = field.get(obj) ;
                        
                        String value = getStringValue(object);
                        // 设置cell的值  
                        Label label = new Label(line, row, value, dateDcfF);  
                        ws.addCell(label);  
  
                        // 恢复相应field的权限  
                        field.setAccessible(accessFlag);  
                        line++;  
                    }  
                }  
            }  
            row++;  
            // 行数超过10000行是数据放入下一个sheet  
            if (row % 10000 == 0) {  
                // 设置标题格式  
                line = 0;  
                row = 0;
                
                ws = wwb.createSheet(String.valueOf(sheetNum), sheetNum);
                
                // 设置冻结首行  
                ws.getSettings().setVerticalFreeze(1);  
                // 控制列宽  
                ws.setColumnView(0, 10);  
                ws.setColumnView(1, 18);  
                ws.setColumnView(2, 18);  
                ws.setColumnView(3, 18);  
                ws.setColumnView(4, 18);  
                sheetNum++;  
                // 再次写入表头  
                for (String str : head) {  
                    Label label = new Label(line, 0, str, wcfF);  
                    ws.addCell(label);  
                    line++;  
                }  
                row++;  
            }  
        }  
        // 写入数据并关闭文件  
        wwb.write();  
        wwb.close();  
        
        return file;
  
    }
    
    private static String getStringValue(Object object){
    	 String value = null;
    	 if(object == null){
    		 return "";
    	 }else if(object instanceof String){
    		 value = (String)object;
    		 value = value.replace("\r\n", " ");
             value = value.replace("\n", " ");
          }else if(object instanceof Date){
         	value = DateUtil.format((Date)object, DateUtil.yyyy_MM_dd_HH_mm_ss);
         }else{
        	 return object.toString();
         }
    	 
    	 return value;
    }
    
    public static String parseEndDate(String strEndDate){
    	if(strEndDate.length() == 8){
    		return strEndDate  + " 23:59:59 999";
    	}else if(strEndDate.length() == 11){
    		return strEndDate  + ":59:59 999";
    	}else if(strEndDate.length() == 14){
    		return strEndDate  + ":59 999";
    	}
    		
    	return strEndDate;
    }
}
