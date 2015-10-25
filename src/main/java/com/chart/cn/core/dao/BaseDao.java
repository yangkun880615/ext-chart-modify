package com.chart.cn.core.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.hibernate.Query;

import com.chart.cn.core.pagination.Finder;
import com.chart.cn.core.pagination.Pagination;
import com.chart.cn.core.springjdbc.ExpandSimpleJdbcTemplate;



/**
 * Dao接口 - Dao基接口
 */

public interface BaseDao<T, PK extends Serializable> {

    /**
     * 根据ID获取实体对象.
     * 
     * @param id
     *            记录ID
     * @return 实体对象
     */
    public T get(PK id);

    /**
     * 根据ID获取实体对象.
     * 
     * @param id
     *            记录ID
     * @return 实体对象
     */
    public T load(PK id);

    /**
     * 根据ID数组获取实体对象集合.
     * 
     * @param ids
     *            ID对象数组
     * @return 实体对象集合
     */
    public List<T> get(PK[] ids);

    /**
     * 根据属性名和属性值获取实体对象.
     * 
     * @param propertyName
     *            属性名称
     * @param value
     *            属性值
     * @return 实体对象
     */
    public T get(String propertyName, Object value);

    /**
     * 根据hql语句获取实体对象
     * 
     * @param hql
     * @return 实体对象集合
     */
    public List<T> getListByHql(String hql);

    /**
     * 根据属性名和属性值获取实体对象集合.
     * 
     * @param propertyName
     *            属性名称
     * @param value
     *            属性值
     * @return 实体对象集合
     */
    public List<T> getList(String propertyName, Object value);

    /**
     * 获取所有实体对象集合.
     * 
     * @return 实体对象集合
     */
    public List<T> getAll();

    /**
     * 获取所有实体对象总数.
     * 
     * @return 实体对象总数
     */
    public Long getTotalCount();

    /**
     * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
     * 
     * @param propertyName
     *            属性名称
     * @param oldValue
     *            修改前的属性值
     * @param oldValue
     *            修改后的属性值
     * @return boolean
     */
    public boolean isUnique(String propertyName, Object oldValue, Object newValue);

    /**
     * 根据属性名判断数据是否已存在.
     * 
     * @param propertyName
     *            属性名称
     * @param value
     *            值
     * @return boolean
     */
    public boolean isExist(String propertyName, Object value);

    /**
     * 保存实体对象.
     * 
     * @param entity
     *            对象
     * @return ID
     */
    public PK save(T entity);

    /**
     * 保存或更新实体对象.
     * 
     * @param entity
     *            对象
     */
    public void saveOrUpdate(T entity);

    /**
     * 更新实体对象.
     * 
     * @param entity
     *            对象
     */
    public void update(T entity);

    /**
     * 删除实体对象.
     * 
     * @param entity
     *            对象
     * @return
     */
    public void delete(T entity);

    /**
     * 根据ID删除实体对象.
     * 
     * @param id
     *            记录ID
     */
    public void delete(PK id);

    /**
     * 根据ID数组删除实体对象.
     * 
     * @param ids
     *            ID数组
     */
    public void delete(PK[] ids);

    /**
     * 更新对应类型的实体
     * 
     * @param type
     * @return
     */
    public <T> T merge(T type);

    /**
     * 根据对象类型和PKID删除对象
     * 
     * @param type
     * @param id
     */
    public <T, PK extends Serializable> void delete(Class<T> type, PK id);

    /**
     * 根据对象类型和PKID查询对象
     * 
     * @param type
     * @param id
     */
    public <T, PK extends Serializable> T find(Class<T> type, PK id);

    /**
     * 创建对象
     * 
     * @param t
     * @return
     */
    public <T> T create(T t);

    /**
     * 根据条件查询对象
     * 
     * @param queryName
     * @param params
     * @return
     */
    public <T> T findObjectQuery(String queryName, Map<String, Object> params);

    /**
     * 根据条件查询对象
     * 
     * @param params
     * @return
     */
    public T findObjectQuery(Map<String, Object> params);

    /**
     * 根据条件删除对象
     * 
     * @param params
     * @return
     */
    public boolean deleteObject(Map<String, Object> params);

    /**
     * 根据条件查询集合
     * 
     * @param queryName
     * @param params
     * @return
     */
    public <T> List<T> findListQuery(String queryName, Map<String, Object> params);

    /**
     * 根据条件查询集合
     * 
     * @param params
     * @return
     */
    public <T> List<T> findListQuery(Map<String, Object> params);

    public boolean updateBySQL(String sql);

    /**
     * 刷新session.
     */
    public void flush();

    /**
     * 清除Session.
     */
    public void clear();

    /**
     * 清除某一对象.
     * 
     * @param object
     *            需要清除的对象
     */
    public void evict(Object object);

    public List<T> find(Finder finder);

    public Pagination find(Finder finder, int pageNo, int pageSize);

    public String getSeqn();
    
    public String getApplyCodeSeq();
    
    public String getTLPublicSeq();
	
	public String getFormatDBTime(String formatstr);

    public Connection getConnection();

    public ExpandSimpleJdbcTemplate getSimpleSpringJdbcTemplate();

    /**
     * 描述: .原生态SQL分页
     * 
     * @param sql
     * @param pageNo
     * @param pageSize
     * @return
     * @author chp
     *         <p>
     *         Sample: 该方法使用样例
     *         </p>
     *         date 2013-6-27 ----------------------------------------------------------- 修改人 修改日期 修改描述 chp 2013-6-27 创建
     *         -----------------------------------------------------------
     * @Version Ver1.0
     */
    @SuppressWarnings("unchecked")
    public Pagination findSql(String sql, int pageNo, int pageSize);

    public List<T> getListBySql(String sql);

    public List<DynaBean> getListByJdbcSql(String sql);

    public Pagination findSql(String sql, Class<?> classs, Map<String, Object> params, int pageNo, int pageSize);
    
    public String getSelectAllColumsSql(String tableName);
}