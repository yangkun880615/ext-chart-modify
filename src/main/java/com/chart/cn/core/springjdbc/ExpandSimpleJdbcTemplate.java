package com.chart.cn.core.springjdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.chart.cn.core.pagination.Pagination;



public interface ExpandSimpleJdbcTemplate {

	/**
	 * 
	 * @Title: getNativeConnection
	 * @Description: 获取本地连接
	 * @return 本地连接对象
	 * @throws SQLException
	 * @throws
	 */
	public abstract Connection getNativeConnection() throws SQLException;

	/**
	 * 
	 * @Title: getBaseSupport
	 * @Description: 获取底层支持类
	 * @return
	 * @throws
	 */
	public abstract Object[] getBaseSupport();

	/**
	 * 
	 * @Title: execute
	 * @Description: 执行某一条sql，该方法不带参数
	 * @param sql
	 *            执行语句
	 * @throws
	 */
	public abstract void execute(StringBuffer sql);

	/**
	 * 插入对象返回主键id
	 * 
	 * @param sql
	 * @param paramMap
	 * @return Object
	 * @throws DataAccessFailureException
	 * */
	public Object executeAndReturnKey(StringBuffer sql, Map<String, Object> paramMap, String[] keys) throws Exception;

	/**
	 * 
	 * @Title: update
	 * @Description: 执行某一条SQL，该方法接收一个Map参数列表</p>
	 *               该map的key为sql中命名参数名，value值为该命名参数所需要代替的值</p> 例:<code> 
	 *  StringBuffer sql = new StringBuffer();
	 *  sql.append("select * from dept where deptId = :deptId");
	 *  Map<String, object> paramMap = new Maps.newHashMap();
	 *  paramMap.put("deptId", 1);
	 *  simpleSpringJdbcTemplate.update(sql, paramMap)
	 * </code>
	 * @param sql
	 *            执行语句
	 * @param paramMap
	 *            参数
	 * @return
	 * @throws
	 */
	public abstract int update(StringBuffer sql, Map<String, ?> paramMap);

	/**
	 * 批量执行
	 * @param sql
	 * @param listMaps
	 * @return
	 */
	public abstract int executeBatchUpdateListMap(String sql, List<Map<String, Object>> listMaps);
	
	/**
	 * 批量执行
	 * @param sql
	 * @param listMaps
	 * @return
	 */
	public abstract <T> int executeBatchUpdate(String sql, List<T> list);
	
	/**
	 * 
	 * @Title: queryForListMap
	 * @Description: 查询数据返回到一个List<Map<String,
	 *               Object>>结构中，其中一个Map就是一个一条数据。Map的key为列名，value为该列的数据
	 * @param sql
	 *            执行的SQL语句
	 * @param paramMap
	 *            参数
	 * @return
	 * @throws
	 */
	public abstract List<Map<String, Object>> queryForListMap(StringBuffer sql, Map<String, ?> paramMap);

	/**
	 * 该方法被queryForListMap所代替
	 * @Title: queryForMap
	 * @Description: 查询数据返回到一个List<Map<String,
	 *               Object>>结构中，其中一个Map就是一个一条数据。Map的key为列名，value为该列的数据
	 * @param sql
	 *            执行的SQL语句
	 * @param paramMap
	 *            参数
	 * @return
	 * @throws
	 */
	@Deprecated
	public abstract List<Map<String, Object>> queryForMap(StringBuffer sql, Map<String, ?> paramMap);

	/**
	 * 查询结果返回一个map
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap);

	/**
	 * 
	 * @Title: queryForList
	 * @Description: 该方法会根据查询结果动态设置到一个类型T里面。
	 * @param sql
	 *            需要被执行的sql
	 * @param paramMap
	 *            参数
	 * @param beanProperty
	 *            返回类型
	 * @return
	 * @throws
	 */
	public abstract <T> List<T> queryForList(StringBuffer sql, Map<String, ?> paramMap, Class<T> beanProperty);

	/**
	 * 
	 * @Title: queryForListBynamedParameter
	 * @Description: 该方法会根据查询结果动态设置到一个类型T里面。
	 * @param sql
	 *            需要被执行的sql
	 * @param paramMap
	 *            参数
	 * @param beanProperty
	 *            返回类型
	 * @return
	 * @throws
	 */
	public abstract <T> List<T> queryForListByNamedParameter(StringBuffer sql, Map<String, ?> paramMap, Class<T> beanProperty);

	/**
	 * 
	 * @Title: getSingleResult
	 * @Description: 返回一个唯一结果集。该处没有做唯一验证。当返回多个结果的时候是取第一个结果。
	 * @param sql
	 *            需要被执行的SQL
	 * @param beanProperty
	 *            返回类型
	 * @param paramMap
	 *            参数
	 * @return
	 * @throws DataAccessFailureException
	 * @throws
	 */
	public abstract <T> T getSingleResult(StringBuffer sql, Class<T> beanProperty, Map<String, ?> paramMap);

	/**
	 * 
	 * @Title: getSingleResult
	 * @Description: 返回一个唯一的Object，当有多个结果的时候返回第一行的第一列的结果。
	 * @param sql
	 *            需要被执行的SQL
	 * @param beanProperty
	 *            返回类型
	 * @param paramMap
	 *            参数
	 * @return
	 * @throws DataAccessFailureException
	 * @throws
	 */
	public abstract Object getSingleResult(StringBuffer sql, Map<String, ?> paramMap);

	/**
	 * 
	 * @Title: getRecordCount
	 * @Description: 获取记录条数
	 * @param sql
	 * @param paramMap
	 * @return 返回一个Long的数据，为0则没有数据
	 * @throws
	 */
	public abstract Long getRecordCount(StringBuffer sql, Map<String, ?> paramMap);

	/**
	 * 
	 * @Title: isDataExists
	 * @Description: 判断数据是否存在
	 * @param sql
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public abstract Boolean isDataExists(StringBuffer sql, Map<String, ?> paramMap);

	/**
	 * 
	 * @Title: queryForPagination
	 * @Description: 分页查询数据
	 * @param sql
	 *            需要执行查询的sql
	 * @param pagination
	 *            分页对象
	 * @param beanProperty
	 *            List返回值的类型
	 * @param paramMap
	 *            命名参数
	 * @return 该对象返回一个List<beanProperty>集合的Pagination对象
	 * @throws
	 */
	Pagination queryForPagination(StringBuffer sql, Pagination pagination, Class<?> beanProperty, Map<String, Object> paramMap);

	/**
	 * 
	 * @Title: queryForPaginationMap
	 * @Description: 分页查询数据
	 * @param sql
	 *            需要执行查询的sql
	 * @param pagination
	 *            分页对象
	 * @param paramMap
	 *            命名参数
	 * @return 该对象返回一个List<Map<String,Object>>集合的Pagination对象
	 * @throws
	 */
	Pagination queryForPaginationMap(StringBuffer sql, Pagination pagination, Map<String, Object> paramMap);

	/**
	 * 
	 * @Title: queryForPagination
	 * @Description: 分页查询数据，指定总记录数
	 * @param sql
	 *            需要执行查询的sql
	 * @param recordCount
	 *            总记录数
	 * @param pagination
	 *            分页对象
	 * @param beanProperty
	 *            List返回值的类型
	 * @param paramMap
	 *            命名参数
	 * @return 该对象返回一个List<beanProperty>集合的Pagination对象
	 * @throws
	 */
	Pagination queryForPagination(StringBuffer sql, Long recordCount, Pagination pagination, Class<?> beanProperty, Map<String, Object> paramMap);

	/**
	 * 
	 * @Title: queryForPagination
	 * @Description: 分页查询数据，对多列小计和总计的支持
	 * @param sql
	 *            需要执行查询的sql
	 * @param recordCount
	 *            总记录数
	 * @param pagination
	 *            分页对象
	 * @param beanProperty
	 *            List返回值的类型
	 * @param paramMap
	 *            命名参数
	 * @return 该对象返回一个List<beanProperty>集合的Pagination对象
	 * @throws
	 */
	public Pagination queryForPagination(StringBuffer sql, Long recordCount, Pagination pagination, Class<?> beanProperty, Map<String, Object> paramMap,
			Map<String, Object> sumParamMapboolean, boolean isMinSum, boolean isTotalSum);

	public Pagination queryForPaginationMap(StringBuffer sql, Pagination pagination, Map<String, Object> paramMap, Map<String, Object> sumParamMap,
			boolean isMinSum, boolean isTotalSum);

	public abstract DataSource getDataSource();

}