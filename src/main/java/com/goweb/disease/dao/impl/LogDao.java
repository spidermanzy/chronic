package com.goweb.disease.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

import com.goweb.disease.dao.IBaseDao;
import com.goweb.disease.entity.Log;

/**
 * 
 * 日志消息Dao
 * 
 * @author yinsheng
 *
 */

@Component("LogDao")
public class LogDao implements IBaseDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final Logger logger = LogManager.getLogger(LogDao.class);

	@Override
	public void save(Object entity) {
		Log log = (Log) entity;
		int ret = -1;
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		String strSql = "insert into t_aaa_monitor_log (id ,lo_createdate ,log_comment  ,log_content ,log_type,log_firstline,log_offset,log_offsetcount) values (?,?,?,?,?,?,?,?) ";
		try {
			ret = jdbcTemplate.update(
					strSql,
					new Object[] { uuid, log.getCreateDate(), log.getComment(), log.getContent(), log.getLogType(),
							log.getFirstline(), log.getOffset(), log.getOffsetCount() }, new int[] { Types.VARCHAR,
							Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARBINARY,
							Types.BIGINT, Types.INTEGER });
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
		String strSql = "SELECT * FROM t_aaa_monitor_log" + " WHERE id = ?";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { param.get("value") }, new int[] { type },
					new LogRowMapper());
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
		String strSql = "SELECT * FROM t_aaa_monitor_log" + " WHERE " + property + " = ?";
		try {
			list = jdbcTemplate.query(strSql, new Object[] { param.get("value") }, new int[] { type },
					new LogRowMapper());
		} catch (DataAccessException e) {
			logger.warn("find with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("find with a unexpected Exception", e);
		}
		return list;
	}

	@Override
	public void update(Object entity) {
		Log log = (Log) entity;
		int ret = -1;

		String strSql = "update t_aaa_monitor_log set lo_createdate = ? ,log_comment = ? ,log_content=? ,log_type = ?,log_firstline=?,log_offset=?,log_offsetcount=? where id = ?  ";
		try {
			ret = jdbcTemplate.update(strSql, new Object[] { log.getCreateDate(), log.getComment(), log.getContent(),
					log.getLogType(), log.getFirstline(), log.getOffset(), log.getOffsetCount(), log.getId() },
					new int[] { Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARBINARY,
							Types.BIGINT, Types.INTEGER, Types.VARCHAR });
		} catch (DataAccessException e) {
			logger.warn("updateLog with a DataAccessException", e);
		} catch (Exception e) {
			logger.warn("updateLog with a unexpected Exception", e);
		}
	}

	@SuppressWarnings({ "cast" })
	private int getType(Map param, Object value) {
		int type = 0;
		if (value instanceof Date) {
			param.put("value", (Date) value);
			type = Types.TIMESTAMP;
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
		} else if (value instanceof Long) {
			param.put("value", (Float) value);
			type = Types.BIGINT;
		} else {
			param.put("value", (String) value);
			type = Types.VARCHAR;
		}
		return type;
	}

	class LogRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Log m = new Log();
			m.setId(rs.getString("id"));
			m.setCreateDate(rs.getDate("lo_createdate"));
			m.setComment(rs.getString("log_comment"));
			m.setContent(rs.getString("log_content"));
			m.setLogType(rs.getString("log_type"));
			m.setFirstline(rs.getString("log_firstline"));
			m.setOffset(rs.getLong("log_offset"));
			m.setOffsetCount(rs.getInt("log_offsetcount"));
			return m;
		}
	}
}
