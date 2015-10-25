package com.chart.cn.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.chart.cn.core.pagination.Finder;
import com.chart.cn.core.pagination.Pagination;
import com.chart.cn.core.springjdbc.ExpandSimpleJdbcTemplate;


/**
 * Dao实现类 - Dao实现类基类
 */
@SuppressWarnings("unchecked")
@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	protected SessionFactory sessionFactory;
	
	protected ExpandSimpleJdbcTemplate expandSpringJdbcTemplate;
	
	@SuppressWarnings("deprecation")
	SimpleJdbcTemplate sj;
	NamedParameterJdbcTemplate nj;
    
	private Class<T> entityClass;

	@SuppressWarnings("rawtypes")
	public BaseDaoImpl() {

		this.entityClass = null;
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public Connection getConnection(){
		try {
			return SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
		} catch (SQLException e) {
			return null;
		}
	}
	
	public ExpandSimpleJdbcTemplate getSimpleSpringJdbcTemplate() {
		return expandSpringJdbcTemplate;
	}
	@Autowired
	public void setSimpleSpringJdbcTemplate(
			ExpandSimpleJdbcTemplate simpleSpringJdbcTemplate) {
		this.expandSpringJdbcTemplate = simpleSpringJdbcTemplate;
	}

	/**
	 * 按属性统计记录数
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	protected int countByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number) (createCriteria(Restrictions.eq(property, value))
				.setProjection(Projections.rowCount()).uniqueResult()))
				.intValue();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName()
				+ " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids)
				.list();
	}

	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model."
				+ propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model."
				+ propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		return getSession().createQuery(hql).list();
	}

	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null
					&& StringUtils.equalsIgnoreCase((String) oldValue,
							(String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}

	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		int count = countByProperty(propertyName, value);
		return count > 0;
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	public void saveOrUpdate(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().saveOrUpdate(entity);
	}
	
	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = load(id);
		getSession().delete(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			getSession().delete(entity);
		}
	}
	
	//--------------------add by zy start 

	/**
	 * 根据ID查询对象
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public <T, PK extends Serializable> T find(Class<T> type, PK id) {
		return (T) getSession().get(type, id);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public <T, PK extends Serializable> void delete(Class<T> type, PK id) {
		T ref = (T) getSession().get(type, id);
		getSession().delete(ref);
	}

	/**
	 * 创建对象
	 * 
	 * @param t
	 * @return
	 */
	public <T>T create(T t) {
		getSession().persist(t);
		getSession().flush();
		getSession().refresh(t);
		return t;
	}

	
	
	/**
	 * 查询实体对象
	 * @param queryName
	 * @param params
	 * @return
	 */
	public <T> T findObjectQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = getSession().createQuery(queryName);
		
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());

		}
		return (T) query.uniqueResult();
	}
	
	public T  findObjectQuery(Map<String, Object> params) {
		if(params==null)return null;
		String sql = buildSqlByMap("from ", params); 
		Query query = getSession().createQuery(sql);
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey().replace(".", "_"), entry.getValue());
		}
		return (T) query.uniqueResult();
	}
	

	public boolean deleteObject(Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		String sql = buildSqlByMap("delete ", params); 
		Query query = getSession().createQuery(sql);
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey().replace(".", "_"), entry.getValue());
		}
		
		return query.executeUpdate()>0;
	}
	
	private String buildSqlByMap(String prefix, Map<String, Object> params){
		StringBuffer sql = new StringBuffer(prefix+entityClass.getName()+" where ");
		for(Iterator<String> iter = params.keySet().iterator(); iter.hasNext();){
			String key = iter.next();
			sql.append(key+"=:"+key.replace(".", "_")+" and ");
		}
		return sql.substring(0, sql.lastIndexOf(" and "));
	}
	
	public <T> List<T> findListQuery(String queryName, Map<String, Object> params){
		Set<Entry<String, Object>> rawParameters=null;
		if(params!=null){
			rawParameters  = params.entrySet();
		}
		Query query = getSession().createQuery(queryName);
        if(rawParameters!=null){
			for (Entry<String, Object> entry : rawParameters) {
				query.setParameter(entry.getKey(), entry.getValue());
	
			}
        }
		return query.list();
	}
	
	public <T> List<T> findListQuery(Map<String, Object> params){
		if(params==null)return null;
		String sql = buildSqlByMap("from ", params); 
		Query query = getSession().createQuery(sql);
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey().replace(".", "_"), entry.getValue());
        }
		return query.list();
	}
	
	public <T> T merge(T type)
    {
		getSession().merge(type);
        return type;
    }
	
	public boolean updateBySQL(String sql){
		Query query = this.getSession().createSQLQuery(sql);
        int result = query.executeUpdate();
        if (result > 0) {
            return true;
        } else {
            return false;
        }
	}
	
	//--------------------add by zy end
	
	


	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}

	public List<T> getListByHql(String hql) {
		return getSession().createQuery(hql).list();
	}

	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 通过Finder获得分页数据
	 * 
	 * @param finder
	 *            Finder对象
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination find(Finder finder, int pageNo, int pageSize) {
		int totalCount = countQueryResult(finder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		List list = query.list();
		p.setList(list);
		return p;
	}
	
	
	public Pagination findSql(String  sql, int pageNo, int pageSize) {
		return findSql(sql, null, null, pageNo, pageSize);
	}
	
	public Pagination findSql(String  sql, Class<?> classs, Map<String, Object> params, int pageNo, int pageSize) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if(params!=null && !params.isEmpty()){
			for (Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
	        }
		}
		if(classs!=null) query.addEntity(classs);
		int totalCount = query.list().size();
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList<Object>());
			return p;
		}
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		p.setList(query.list());
		return p;
	}
	
	/**
	 * 查询返回结果为ScrollableResults的语句为sql的方法 add by zhoubi
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ScrollableResults findSqlToSrs(String sql,int pageNo,int pageSize){
		Query query = getSession().createQuery(sql);
		int totalCount = query.list().size();
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		ScrollableResults srs = query.scroll();
		return srs;
	}
	
	/**
	 * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
	 */
	public Query createQuery(String queryString, Object... values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	/**
	 * 通过Finder获得列表数据
	 * 
	 * @param finder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(Finder finder) {
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(finder.getFirstResult());
		if (finder.getMaxResults() > 0)
			query.setMaxResults(finder.getMaxResults());
		if (finder.isCacheable())
			query.setCacheable(true);
		List<T> list = query.list();
		return list;
	}

	/**
	 * 获得Finder的记录总数
	 * 
	 * @param finder
	 * @return
	 */
	protected int countQueryResult(Finder finder) {
		Query query = getSession().createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		return ((Number) query.iterate().next()).intValue();
	}
	

	public String getSeqn() {
		String sql = " select s_public.nextval from dual";
		Query query = getSession().createSQLQuery(sql);
		BigDecimal b = (BigDecimal) query.uniqueResult();
		return b.toString();
	}
	
	//8位流水序号 
	public String getApplyCodeSeq(){
		String sql = " select S_APPLY_CODE.nextval from dual";
		Query query = getSession().createSQLQuery(sql);
		BigDecimal b = (BigDecimal) query.uniqueResult();
		return b.toString();
	}
	
	public String getTLPublicSeq(){
		String sql = " select S_TL_PUBLIC.nextval from dual";
		Query query = getSession().createSQLQuery(sql);
		BigDecimal b = (BigDecimal) query.uniqueResult();
		return b.toString();
	}
	
	//取所在数据库日期
	public String getFormatDBTime(String formatstr){
//	    String sql="select to_char(sysdate,'"+formatstr+"') from dual";
		String sql="select date_format(now(),'"+formatstr+"')";
		
		
        Query query = this.getSession().createSQLQuery(sql);
        List<String> resultList = query.list();
        String sDate = "";
        if(resultList!=null&&resultList.size()>0){
            sDate = resultList.get(0);
        }
        return sDate;
	}
	
	public List<T> getListBySql(String sql) {
		Query query = getSession().createSQLQuery(sql).addEntity(entityClass);
		return query.list();
	}
	public List<DynaBean> getListByJdbcSql(String sql) {
		// TODO Auto-generated method stub
		Connection conn =getConnection();
		RowSetDynaClass result ;
		ResultSet rs =null;
		PreparedStatement ps=null;
		List<DynaBean> list =null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			result = new RowSetDynaClass(rs);
			list =(List<DynaBean>)result.getRows();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(rs !=null){
					rs.close();
				}
				if(ps !=null){
					ps.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public String getSelectAllColumsSql(String tableName) {
		// TODO Auto-generated method stub
		Connection conn =getConnection();
		ResultSet rs =null;
		PreparedStatement ps=null;
		String sql ="select * from "+tableName;
		StringBuffer sb= new StringBuffer("SELECT ");
		try {
			ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
		    ResultSetMetaData metaData = rs.getMetaData(); 
		    int colum = metaData.getColumnCount();  
		    for (int i = 1; i < colum; i++)   {
		    	String columName = metaData.getColumnName(i);  
		    	sb.append("T.").append(columName).append(",");
		    }
		    	sb.append(metaData.getColumnName(colum));
				sb.append(" FROM ").append(tableName.toUpperCase()).append(" T ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(rs !=null){
					rs.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}




}