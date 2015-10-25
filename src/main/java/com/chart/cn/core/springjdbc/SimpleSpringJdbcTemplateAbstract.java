package com.chart.cn.core.springjdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;



/**
 * @ClassName: SimpleSpringJdbcTemplate
 * @Description: TODO
 * @author yanwei_clear
 * @date 2013年11月12日 下午7:52:32
 * 
 */
public abstract class SimpleSpringJdbcTemplateAbstract implements ExpandSimpleJdbcTemplate {

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 数据源
	 */
	protected DataSource dataSource;

	/**
	 * 对jdbc操作提供了一些基本的处理
	 */
	protected JdbcTemplate jdbcTemplate;

	/**
	 * SimpleJdbcTemplate利用Java5的特性，比如自动装箱、通用和可变参数列表来简化jdbc模板的使用
	 * 该方法在spring2.5的时候已经标记过时
	 */
	// protected SimpleJdbcTemplate simpleJdbcTemplate;

	/**
	 * 命名参数jdbc操作模板该方法中提共了绑定到sql中的命名参数处理
	 */
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/*
	 * <p>Title: getNativeConnection</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 * 
	 * @see com.cct9k.common.ExpandSimpleJdbcTemplate#getNativeConnection()
	 */
	@Override
	public Connection getNativeConnection() throws SQLException {
		Assert.notNull(dataSource, "数据源为空");
		return this.dataSource.getConnection();
	}

	/**
	 * 
	 * @Title: getBaseSupport
	 * @Description: 获取底层支持类
	 * @return
	 * @throws
	 */
	public Object[] getBaseSupport() {
		return new Object[] { jdbcTemplate, this.namedParameterJdbcTemplate };
	}

	/*
	 * <p>Title: execute</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#execute(java.lang.StringBuffer)
	 */
	@Override
	public void execute(StringBuffer sql) {
		this.jdbcTemplate.execute(sql.toString());

	}

	/**
	 * 插入对象返回主键id
	 * 
	 * @param sql
	 * @param paramMap
	 * @return Object
	 * @throws DataAccessFailureException
	 * */
	@Override
	public Object executeAndReturnKey(final StringBuffer sql, final Map<String, Object> paramMap, String[] keys) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(sql.toString(), new MapSqlParameterSource(paramMap), keyHolder, keys);
		return keyHolder.getKey();
	}

	/*
	 * <p>Title: update</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#update(java.lang.StringBuffer,
	 * java.util.Map)
	 */
	@Override
	public int update(StringBuffer sql, Map<String, ?> paramMap) {
		return this.namedParameterJdbcTemplate.update(sql.toString(), paramMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cct9k.core.springjdbc.ExpandSimpleJdbcTemplate#executeBatchUpdate
	 * (java.lang.String, java.util.List)
	 */
	@Override
	public <T> int executeBatchUpdate(String sql, List<T> list) {
		return namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(list.toArray())).length;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cct9k.core.springjdbc.ExpandSimpleJdbcTemplate#executeBatchUpdateListMap
	 * (java.lang.String, java.util.List)
	 */
	@Override
	public int executeBatchUpdateListMap(String sql, List<Map<String, Object>> listMaps) {
		return namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(listMaps.toArray(new HashMap[listMaps.size()]))).length;
	}

	/*
	 * <p>Title: queryForMap</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#queryForMap(java.lang.StringBuffer
	 * , java.util.Map)
	 */

	public List<Map<String, Object>> queryForListMap(StringBuffer sql, Map<String, ?> paramMap) {
		return this.namedParameterJdbcTemplate.queryForList(sql.toString(), paramMap);
	}

	/*
	 * <p>Title: queryForMap</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#queryForMap(java.lang.StringBuffer
	 * , java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryForMap(StringBuffer sql, Map<String, ?> paramMap) {
		return this.namedParameterJdbcTemplate.queryForList(sql.toString(), paramMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cct9k.core.springjdbc.ExpandSimpleJdbcTemplate#queryForMap(java.lang
	 * .String, java.util.Map)
	 */
	@Override
	public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
		return namedParameterJdbcTemplate.queryForMap(sql, paramMap);
	}

	/*
	 * <p>Title: queryForList</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @param beanProperty
	 * 
	 * @return
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#queryForList(java.lang.StringBuffer
	 * , java.util.Map, java.lang.Class)
	 */
	@Override
	public <T> List<T> queryForList(StringBuffer sql, Map<String, ?> paramMap, Class<T> beanProperty) {
		// return this.simpleJdbcTemplate.query(sql.toString(),
		// BeanPropertyRowMapper.newInstance(beanProperty), paramMap);
		return this.queryForListByNamedParameter(sql, paramMap, beanProperty);
	}

	/*
	 * <p>Title: queryForListByNamedParameter</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @param beanProperty
	 * 
	 * @return
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#queryForListByNamedParameter
	 * (java.lang.StringBuffer, java.util.Map, java.lang.Class)
	 */
	@Override
	public <T> List<T> queryForListByNamedParameter(StringBuffer sql, Map<String, ?> paramMap, Class<T> beanProperty) {
		return this.namedParameterJdbcTemplate.query(sql.toString(), paramMap, BeanPropertyRowMapper.newInstance(beanProperty));
	}

	/*
	 * <p>Title: getSingleResult</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param beanProperty
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see com.cct9k.common.ExpandSimpleJdbcTemplate#getSingleResult(java.lang.
	 * StringBuffer, java.lang.Class, java.util.Map)
	 */
	@Override
	public <T> T getSingleResult(StringBuffer sql, Class<T> beanProperty, Map<String, ?> paramMap) {
		List<T> resultList = this.queryForList(sql, paramMap, beanProperty);
		return resultList.size() > 0 ? resultList.get(0) : null;
	}

	/*
	 * <p>Title: getSingleResult</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see com.cct9k.common.ExpandSimpleJdbcTemplate#getSingleResult(java.lang.
	 * StringBuffer, java.util.Map)
	 */
	@Override
	public Object getSingleResult(StringBuffer sql, Map<String, ?> paramMap) {
		Map<String, ?> resultMap = this.namedParameterJdbcTemplate.queryForMap(sql.toString(), paramMap);
		if (resultMap != null) {
			for (String key : resultMap.keySet()) {
				Object result = resultMap.get(key);
				return result;
			}
		}
		return null;
	}

	/*
	 * <p>Title: getRecordCount</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see com.cct9k.common.ExpandSimpleJdbcTemplate#getRecordCount(java.lang.
	 * StringBuffer, java.util.Map)
	 */
	@Override
	public Long getRecordCount(StringBuffer sql, Map<String, ?> paramMap) {
		StringBuffer recordSql = new StringBuffer();
		recordSql.append(" select count(*) recordCount from ( ").append(sql).append(") cct ");
		Map<String, ?> resultMap = this.namedParameterJdbcTemplate.queryForMap(recordSql.toString(), paramMap);
		if (resultMap != null) {
			for (String key : resultMap.keySet()) {
				Object result = resultMap.get(key);
				if (result instanceof Long) {
					return (Long) result;
				}
				if (result instanceof BigDecimal) {
					return ((BigDecimal) result).longValue();
				}
			}
		}
		return 0L;
	}

	/*
	 * <p>Title: isDataExists</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#isDataExists(java.lang.StringBuffer
	 * , java.util.Map)
	 */
	@Override
	public Boolean isDataExists(StringBuffer sql, Map<String, ?> paramMap) {
		long result = this.getRecordCount(sql, paramMap);
		return result > 0 ? true : false;
	}

	/*
	 * <p>Title: getDataSource</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.cct9k.common.ExpandSimpleJdbcTemplate#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * public SimpleJdbcTemplate getSimpleJdbcTemplate() { return
	 * simpleJdbcTemplate; }
	 * 
	 * public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate)
	 * { this.simpleJdbcTemplate = simpleJdbcTemplate; }
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

}
