package com.chart.cn.core.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chart.cn.core.dao.BaseDao;



import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;



@Service
@Transactional
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	protected Logger log = LoggerFactory.getLogger(getClass());
   
	@Resource(name="baseDaoImpl")
	private BaseDao<T, PK> baseDao;


	
    public BaseDao<T, PK> getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao<T, PK> baseDao) {
        this.baseDao = baseDao;
    }

    public T get(PK id) {
    	if(id==null)return null;
        return baseDao.get(id);
    }

    public T load(PK id) {
    	if(id==null)return null;
        return baseDao.load(id);
    }

    public List<T> get(PK[] ids) {
        return baseDao.get(ids);
    }

    public T get(String propertyName, Object value) {
        return baseDao.get(propertyName, value);
    }

    public List<T> getList(String propertyName, Object value) {
        return baseDao.getList(propertyName, value);
    }

    public List<T> getAll() {
        return baseDao.getAll();
    }

    public Long getTotalCount() {
        return baseDao.getTotalCount();
    }

    public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
        return baseDao.isUnique(propertyName, oldValue, newValue);
    }

    public boolean isExist(String propertyName, Object value) {
        return baseDao.isExist(propertyName, value);
    }

    public PK save(T entity) {
        return baseDao.save(entity);
    }

    public void update(T entity) {
        baseDao.update(entity);
    }

    public void delete(T entity) {
        baseDao.delete(entity);
    }

    public void delete(PK id) {
        baseDao.delete(id);
    }

    public void delete(PK[] ids) {
        baseDao.delete(ids);
    }

    public void flush() {
        baseDao.flush();
    }

    public void clear() {
        baseDao.clear();
    }

    public void evict(Object object) {
        baseDao.evict(object);
    }

    public List<T> getListByHql(String hql) {
        return baseDao.getListByHql(hql);
    }

	public String getSeqn() {
		return baseDao.getSeqn();
	}

	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
	}
	

	

}
