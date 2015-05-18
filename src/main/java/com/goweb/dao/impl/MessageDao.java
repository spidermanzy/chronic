package com.goweb.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.goweb.dao.IMessageDao;
import com.goweb.entity.Message;
import com.goweb.utils.DateUtil;
import com.goweb.web.vo.StaticsReportVo;

/**
 * 
 * 日志消息Dao
 * 
 * @author yinsheng
 *
 */

@Component("MessageDao")
public class MessageDao implements IMessageDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final Logger logger = LogManager.getLogger(MessageDao.class);

	@Override
	public List<Message> getMessageOnDay(Date date) {
		String strDate = DateUtil.format(date, DateUtil.yyyy_MM_dd);
		String begin = strDate + " 00:00:00";
		date = new Date(date.getTime() + 24 * 60 * 60 * 1000);
		strDate = DateUtil.format(date, DateUtil.yyyy_MM_dd);
		String end = strDate + " 00:00:00";

		List<Message> list = null;
		String strSql = "SELECT * FROM t_aaa_monitor_message m"
				+ " WHERE to_date(?, 'YYYY-MM-DD HH24:MI:SS') <= m.log_date"
				+ "	AND m.log_date <= to_date(?, 'YYYY-MM-DD HH24:MI:SS')" + " ORDER BY m.log_date DESC";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { begin, end }, new int[] { Types.VARCHAR, Types.VARCHAR },
					new MessageRowMapper());
		} catch (DataAccessException e) {
			logger.warn("findMessageOnDay with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("findMessageOnDay with a unexpected Exception", e);
		}
		return list;
	}

	@Override
	public List<Message> getMessage(Date beginDate, Date endDate) {
		String strBeginDate = DateUtil.format(beginDate, DateUtil.yyyy_MM_dd_HH_mm_ss);
		String strEndDate = DateUtil.format(endDate, DateUtil.yyyy_MM_dd_HH_mm_ss);

		List<Message> list = null;
		String strSql = "SELECT * FROM t_aaa_monitor_message m"
				+ " WHERE to_date(?, 'YYYY-MM-DD HH24:MI:SS') <= m.log_date AND m.log_date <= to_date(?, 'YYYY-MM-DD HH24:MI:SS')"
				+ " ORDER BY m.log_date DESC";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { strBeginDate, strEndDate }, new int[] { Types.VARCHAR,
					Types.VARCHAR }, new MessageRowMapper());
		} catch (DataAccessException e1) {
			logger.warn("getMessage with a DataAccessException", e1);
		} catch (Exception e2) {
			logger.warn("getMessage with a unexpected Exception", e2);
		}
		return list;
	}

	@Override
	public List<Message> getMessageByLogDate() {

		// String pql = "select m from Message m  order by m.logDate desc";
		// Query query = entityManager.createQuery(pql);
		//
		// return query.getResultList();
		return null;
	}

	@Override
	public List<StaticsReportVo> getStaticOnDay(Date date) {
		String strDate = DateUtil.format(date, DateUtil.yyyy_MM_dd);
		String begin = strDate + " 00:00:00";
		date = new Date(date.getTime() + 24 * 60 * 60 * 1000);
		strDate = DateUtil.format(date, DateUtil.yyyy_MM_dd);
		String end = strDate + " 00:00:00";

		List<StaticsReportVo> list = null;
		String strSql = "SELECT monitor_oriaddr,monitor_destaddr,monitor_msgtype,to_char(log_date ,'yyyy-mm-dd') monitor_da,count(*) monitor_count from t_aaa_monitor_message m "
				+ "WHERE to_date(?, 'YYYY-MM-DD HH24:MI:SS') <= m.log_date AND m.log_date <= to_date(?, 'YYYY-MM-DD HH24:MI:SS') "
				+ "group by monitor_oriaddr,monitor_destaddr,monitor_msgtype,to_char(log_date ,'yyyy-mm-dd') "
				+ "order by to_char(log_date ,'yyyy-mm-dd') ";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { begin, end }, new int[] { Types.VARCHAR, Types.VARCHAR },
					new StaticsRowMapper());
		} catch (DataAccessException e) {
			logger.warn("findMessageOnDay with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("findMessageOnDay with a unexpected Exception", e);
		}
		return list;
	}

	@Override
	public List<StaticsReportVo> getStatic(Date beginDate, Date endDate) {
		String strBeginDate = DateUtil.format(beginDate, DateUtil.yyyy_MM_dd_HH_mm_ss);
		String strEndDate = DateUtil.format(endDate, DateUtil.yyyy_MM_dd_HH_mm_ss);

		List<StaticsReportVo> list = null;
		String strSql = "SELECT monitor_oriaddr,monitor_destaddr,monitor_msgtype,to_char(log_date ,'yyyy-mm-dd') monitor_da,count(*) monitor_count "
				+ "from t_aaa_monitor_message m "
				+ "WHERE to_date(?, 'YYYY-MM-DD HH24:MI:SS') <= m.log_date AND m.log_date <= to_date(?, 'YYYY-MM-DD HH24:MI:SS') "
				+ "group by monitor_oriaddr,monitor_destaddr,monitor_msgtype,to_char(log_date ,'yyyy-mm-dd') "
				+ "order by to_char(log_date ,'yyyy-mm-dd') ";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { strBeginDate, strEndDate }, new int[] { Types.VARCHAR,
					Types.VARCHAR }, new StaticsRowMapper());
		} catch (DataAccessException e) {
			logger.warn("findMessageOnDay with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("findMessageOnDay with a unexpected Exception", e);
		}
		// 将总数放在最后
		Long count = (long) 0;
		for (StaticsReportVo s : list) {
			count += s.getCount();
		}
		StaticsReportVo zong = new StaticsReportVo();
		zong.setOriAddr("TOTAL");
		zong.setDestAddr("TOTAL");
		zong.setMsgType("TOTAL");
		zong.setCount(count);
		zong.setDate(strBeginDate + "——" + strEndDate);
		List ret = new ArrayList();
		ret.add(zong);
		ret.addAll(list);
		return ret;
	}

	@Override
	public void save(Object entity) {
		Message msg = (Message) entity;
		int ret = -1;
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		String strSql = "insert into t_aaa_monitor_message (id, lo_createdate ,monitor_oriaddr ,"
				+ "monitor_destaddr,monitor_msgtype,monitor_msgcontent,log_date) values (?,?,?,?,?,?,?) ";
		try {
			ret = jdbcTemplate.update(strSql,
					new Object[] { uuid, msg.getCreateDate(), msg.getOriAddr(), msg.getDestAddr(), msg.getMsgType(),
							msg.getMsgContent(), msg.getLogDate() }, new int[] { Types.VARCHAR, Types.TIMESTAMP,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		} catch (DataAccessException e) {
			logger.warn("updateLog with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("updateLog with a unexpected Exception", e);
		}
	}

	@Override
	public Object find(Serializable id) {
		List list = null;
		HashMap param = new HashMap();
		int type = 0;
		type = getType(param, id);
		String strSql = "SELECT * FROM t_aaa_monitor_message" + " WHERE id = ?";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { param.get("value") }, new int[] { type },
					new MessageRowMapper());
		} catch (DataAccessException e) {
			logger.warn("find with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("find with a unexpected Exception", e);
		}
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	@Override
	public List find(String property, Object value) {
		List list = null;
		HashMap param = new HashMap();
		int type = 0;
		type = getType(param, value);
		String strSql = "SELECT * FROM t_aaa_monitor_message" + " WHERE " + property + " = ?";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { param.get("value") }, new int[] { type },
					new MessageRowMapper());
		} catch (DataAccessException e) {
			logger.warn("find with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("find with a unexpected Exception", e);
		}
		return list;
	}

	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "cast" })
	private int getType(Map param, Object value) {
		int type = 0;
		if (value instanceof Date) {
			param.put("value", (Date) value);
			type = Types.TIME;
		} else if (value instanceof String) {
			param.put("value", (String) value);
			type = Types.VARCHAR;
		} else if (value instanceof Integer) {
			param.put("value", (Integer) value);
			type = Types.INTEGER;
		} else if (value instanceof Double) {
			param.put("value", (Double) value);
			type = Types.DOUBLE;
		} else if (value instanceof Float) {
			param.put("value", (Float) value);
			type = Types.FLOAT;
		} else {
			param.put("value", (String) value);
			type = Types.INTEGER;
		}
		return type;
	}

	class MessageRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setId(rs.getString("id"));
			m.setCreateDate(rs.getDate("lo_createdate"));
			m.setLogDate(rs.getTimestamp("log_date"));
			m.setDestAddr(rs.getString("monitor_destaddr"));
			m.setOriAddr(rs.getString("monitor_oriaddr"));
			m.setMsgType(rs.getString("monitor_msgtype"));
			m.setMsgContent(rs.getString("monitor_msgcontent"));
			return m;
		}
	}

	class StaticsRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			StaticsReportVo s = new StaticsReportVo();
			s.setDestAddr(rs.getString("monitor_destaddr"));
			s.setOriAddr(rs.getString("monitor_oriaddr"));
			s.setMsgType(rs.getString("monitor_msgtype"));
			s.setDate(rs.getString("monitor_da"));
			s.setCount(rs.getLong("monitor_count"));
			return s;
		}
	}
}
