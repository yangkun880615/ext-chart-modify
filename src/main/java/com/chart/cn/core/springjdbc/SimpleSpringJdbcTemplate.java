
package com.chart.cn.core.springjdbc;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import com.chart.cn.core.pagination.Pagination;
import com.chart.cn.core.pagination.PaginationQueryLanguage;




/**
 * @ClassName: SimpleSpringJdbcTemplate
 * @Description: TODO
 * @author 
 * @date 2013年11月20日 上午9:07:06
 * 
 */
public class SimpleSpringJdbcTemplate extends SimpleSpringJdbcTemplateAbstract implements ExpandSimpleJdbcTemplate {

	private PaginationQueryLanguage paginationQueryLanguage;

	/**
	 * <p>
	 * Title: 构造SimpleSpringJdbcTemplate
	 * </p>
	 * <p>
	 * Description: 如果不在外部注入封装该jdbc操作的模板所需要的资源的话就需要在这个构造方法中设置数据源
	 * </p>
	 * 
	 * @param dataSource
	 */
	public SimpleSpringJdbcTemplate(DataSource dataSource) {
		Assert.notNull(dataSource, "数据源为空");
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/*
	 * 
	 * <p>Title: queryForPagination</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param pagination
	 * 
	 * @param beanProperty
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see
	 * com.cct9k.common.ExpandSimpleJdbcTemplate#queryForPagination(java.lang
	 * .StringBuffer, com.cct9k.common.Pagination, java.lang.Class,
	 * java.util.Map)
	 */
	@Override
	public Pagination queryForPagination(StringBuffer sql, Pagination pagination, Class<?> beanProperty, Map<String, Object> paramMap) {
		StringBuffer nativeSql = settingPagination(sql, pagination, paramMap, null);
		// 获取结果集
		List resultList = this.queryForList(nativeSql, paramMap, beanProperty);
		pagination.setList(resultList);
		return pagination;
	}

	/*
	 * 
	 * <p>Title: queryForPaginationMap</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param pagination
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public Pagination queryForPaginationMap(StringBuffer sql, Pagination pagination, Map<String, Object> paramMap) {
		StringBuffer nativeSql = settingPagination(sql, pagination, paramMap, null);
		// 获取结果集
		List resultList = this.queryForMap(nativeSql, paramMap);
		pagination.setList(resultList);
		return pagination;
	}

	/**
	 * 添加分页小计和总计支持
	 */
	public Pagination queryForPaginationMap(StringBuffer sql, Pagination pagination, Map<String, Object> paramMap, Map<String, Object> sumParamMap,
			boolean isMinSum, boolean isTotalSum) {
		StringBuffer nativeSql = settingPagination(sql, pagination, paramMap, null);
		// 获取结果集
		List resultList = this.queryForMap(nativeSql, paramMap);
		pagination.setList(resultList);
		if (isMinSum && sumParamMap != null) {
			StringBuffer sumSql = buildSumSql(nativeSql, sumParamMap);
			Map minSumMap = this.namedParameterJdbcTemplate.queryForMap(sumSql.toString(), paramMap);
			pagination.setMinSumMap(minSumMap);
		}
		if (isMinSum && sumParamMap != null) {
			StringBuffer sumSql = buildSumSql(sql, sumParamMap);
			Map totalSumMap = this.namedParameterJdbcTemplate.queryForMap(sumSql.toString(), paramMap);
			pagination.setTotolalSumMap(totalSumMap);
		}
		return pagination;
	}

	/*
	 * 
	 * <p>Title: queryForPagination</p> <p>Description: </p>
	 * 
	 * @param sql
	 * 
	 * @param recordCount
	 * 
	 * @param pagination
	 * 
	 * @param beanProperty
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * java.lang.Class, java.util.Map)
	 */
	@Override
	public Pagination queryForPagination(StringBuffer sql, Long recordCount, Pagination pagination, Class<?> beanProperty, Map<String, Object> paramMap) {
		StringBuffer nativeSql = settingPagination(sql, pagination, paramMap, recordCount);
		// 获取结果集
		List resultList = this.queryForList(nativeSql, paramMap, beanProperty);
		pagination.setList(resultList);
		return pagination;
	}

	public Pagination queryForPagination(StringBuffer sql, Long recordCount, Pagination pagination, Class<?> beanProperty, Map<String, Object> paramMap,
			Map<String, Object> sumParamMap, boolean isMinSum, boolean isTotalSum) {
		StringBuffer nativeSql = settingPagination(sql, pagination, paramMap, recordCount);
		// 获取结果集
		List resultList = this.queryForList(nativeSql, paramMap, beanProperty);
		pagination.setList(resultList);

		if (isMinSum && sumParamMap.size() > 0) {
			StringBuffer sumSql = buildSumSql(nativeSql, sumParamMap);
			Map minSumMap = this.namedParameterJdbcTemplate.queryForMap(sumSql.toString(), paramMap);
			pagination.setMinSumMap(minSumMap);
		}
		if (isMinSum && sumParamMap.size() > 0) {
			StringBuffer sumSql = buildSumSql(sql, sumParamMap);
			Map totalSumMap = this.namedParameterJdbcTemplate.queryForMap(sumSql.toString(), paramMap);
			pagination.setTotolalSumMap(totalSumMap);
		}
		return pagination;
	}

	private StringBuffer buildSumSql(StringBuffer nativeSql, Map<String, Object> sumParamMap) {
		StringBuffer sb = new StringBuffer("select ");
		for (Map.Entry<String, Object> entry : sumParamMap.entrySet()) {
			sb.append(entry.getValue()).append(" as ").append(entry.getKey()).append(",");
		}
		String s = replaceLastString(sb.toString(), ",");
		return new StringBuffer(s).append(" from (").append(nativeSql).append(")");
	}
	
	
	public static String replaceLastString(String str, String strReg) {
		if (str.endsWith(strReg)) {
			return str.substring(0, str.lastIndexOf(strReg));
		} else {
			return str;
		}
	}

	/**
	 * @Title: settingPagination
	 * @Description: 对Pagination对象进行处理
	 * @param sql
	 * @param pagination
	 * @param paramMap
	 * @return
	 * @throws
	 */
	private StringBuffer settingPagination(StringBuffer sql, Pagination pagination, Map<String, Object> paramMap, Long recordCount) {
		// 获得总记录数
		if (null != recordCount && recordCount.compareTo(0L) == 1) {
			pagination.setTotalCount(recordCount.intValue());
		} else {
			Long count = this.getRecordCount(new StringBuffer(sql), paramMap);
			pagination.setTotalCount(count.intValue());
		}
		// 获取本地sql
		StringBuffer nativeSql = paginationQueryLanguage.getNativeQueryLanguage(sql, pagination);
		return nativeSql;
	}

	public PaginationQueryLanguage getPaginationQueryLanguage() {
		return paginationQueryLanguage;
	}

	public void setPaginationQueryLanguage(PaginationQueryLanguage paginationQueryLanguage) {
		this.paginationQueryLanguage = paginationQueryLanguage;
	}

}
