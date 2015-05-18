package com.goweb.web;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goweb.ApplicationConfig;
import com.goweb.model.page.Page;
import com.goweb.model.page.PageParemeter;
import com.goweb.service.IMessageService;
import com.goweb.utils.DateUtil;
import com.goweb.utils.ReportUtil;

/**
 * 登陆Controller
 * 
 * @author yinsheng
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {

	private static final Logger logger = LogManager.getLogger(ReportController.class);

	@Autowired
	private IMessageService messageService;

	@Autowired
	private ApplicationConfig applicationConfig;

	private Page detailDataPage;

	private Page staticDataPage;

	@RequestMapping
	public String index() {
		return "report/index";
	}

	@RequestMapping(value = "/grid/detailreport.json", consumes = { "application/json" }, produces = { "application/json" })
	public @ResponseBody Page messageJson(@RequestBody PageParemeter pageParemeter) {
		try {
			Page page = null;
			if (pageParemeter == null || (pageParemeter.getPageCount() == 0 && pageParemeter.getSearchValue() == null)) {

				page = new Page();
				page.setElements(messageService.getMessageOnDay(new Date()));
				detailDataPage = page;
				return page;
			}

			String searchValue = pageParemeter.getSearchValue();
			if (pageParemeter.getPageCount() == 0 && searchValue != null && !"".equals(searchValue.trim())) {
				String strBeginDate = searchValue.split("\\|")[0];
				String strEndDate = ReportUtil.parseEndDate(searchValue.split("\\|")[1]);
				page = new Page();
				page.setElements(messageService.getMessage(ReportUtil.parseSearchDate(strBeginDate),
						ReportUtil.parseSearchDate(strEndDate)));
				detailDataPage = page;
				return page;
			}

			page = messageService.findAll(pageParemeter);
			page.setElements(page.getElements());

			detailDataPage = page;
			return page;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	// 获取统计报表
	@RequestMapping(value = "/grid/statisticsreport.json", consumes = { "application/json" })
	public @ResponseBody Page statisticsreportJson(@RequestBody PageParemeter pageParemeter) {

		try {
			Page page = null;

			if (pageParemeter == null || (pageParemeter.getPageCount() == 0 && pageParemeter.getSearchValue() == null)) {

				page = new Page();
				page.setElements(messageService.getStaticOnDay(new Date()));
				staticDataPage = page;
				return page;
			}

			String searchValue = pageParemeter.getSearchValue();
			if (pageParemeter.getPageCount() == 0 && searchValue != null && !"".equals(searchValue.trim())) {
				String strBeginDate = searchValue.split("\\|")[0];
				String strEndDate = ReportUtil.parseEndDate(searchValue.split("\\|")[1]);
				page = new Page();
				page.setElements(messageService.getStatic(ReportUtil.parseSearchDate(strBeginDate),
						ReportUtil.parseSearchDate(strEndDate)));
				staticDataPage = page;
				return page;
			}

			page = messageService.findAll(pageParemeter);
			// page.setElements(ReportUtil.convertStatisticsReportVo(page.getElements()));
			staticDataPage = page;
			return page;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	@RequestMapping(value = "/download/detailreport.json")
	public @ResponseBody String downloandDetailReport(final HttpServletRequest request) throws Exception {
		try {
			if (detailDataPage == null)
				return null;
			File file = null;
			String fileName = "DetailReport" + DateUtil.format(new Date(), DateUtil.yyyyMMddHHmmss) + ".xls";

			file = ReportUtil.createExcelFile(detailDataPage.getElements(), ReportUtil.messageReportHeadList,
					ReportUtil.messageReportPropertyList, fileName, request.getSession().getServletContext()
							.getRealPath("/")
							+ ReportUtil.DOWNLOADFILE_DIR);

			if (file != null) {
				return "downloadfile/" + fileName;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	@RequestMapping(value = "/download/statisticsreport.json")
	public @ResponseBody String downloadStatisticsReport(final HttpServletRequest request) throws Exception {

		try {
			File file = null;
			String fileName = "StatisticsReport" + DateUtil.format(new Date(), DateUtil.yyyyMMddHHmmss) + ".xls";

			file = ReportUtil.createExcelFile(staticDataPage.getElements(), ReportUtil.static01reportHeadList,
					ReportUtil.static01reportPropertyList, fileName, request.getSession().getServletContext()
							.getRealPath("/")
							+ ReportUtil.DOWNLOADFILE_DIR);

			if (file != null) {
				return "downloadfile/" + fileName;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

}
