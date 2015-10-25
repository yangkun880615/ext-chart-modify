/**  
* Project ：              asset-cmp  
* FileName ：           com.cntv.cn.dao.sysrole.SysUserDaoImp.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-29 上午11:31:51  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-29 上午11:31:51         yk        1.0              
*/  

package com.chart.cn.dao.resconfig.sysuser;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.chart.cn.core.dao.BaseDaoImpl;
import com.chart.cn.core.pagination.Pagination;
import com.chart.cn.entity.resconfig.SysUser;

@SuppressWarnings({ "rawtypes", "unchecked" })



@Repository
public class SysUserDaoImp extends BaseDaoImpl<SysUser, Integer> implements SysUserDao  {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	NamedParameterJdbcTemplate named;


	@Override
	public Pagination querySysUserForListBydeptName(String userName,
			int pageSize, int pageNo) {
		Map<String,Object> paramMap=null;
		StringBuffer sql=new StringBuffer(" SELECT a.id,a.birthday,a.create_time,a.create_user_id,a.user_name, ");
		sql.append(" b.user_name as create_user_name,a.email,a.real_name,a.sex ")
		.append(" FROM TB_SYS_USER A LEFT JOIN TB_SYS_USER B ON A.CREATE_USER_ID = B.ID ");
		if(!StringUtils.isEmpty(userName))
			sql.append(" where A.USER_NAME  like :userName");
		sql.append(" order by a.create_time desc  ");
		if(!StringUtils.isEmpty(userName)){
			if(paramMap==null)
			paramMap=new HashMap<String,Object>();
			paramMap.put("userName","%"+userName+"%");
		}
		Pagination pagination=null;
		if(pagination==null)
		pagination=new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination=this.expandSpringJdbcTemplate.queryForPaginationMap(sql, pagination, paramMap);
		if(null!=pagination){
		return pagination;
		}
		else 
		return null;
	}




	
	@Override
	public SysUser verifyloginUser(String username, String password) {
		
	
		String selectSql = "select * from tb_sys_user where user_name=:username and password=:password";  		
		Map params=new HashMap();
		params.put("username", username);
		params.put("password",password);	
		List<SysUser> list=named.query(selectSql,params,new BeanPropertyRowMapper(SysUser.class));
		SysUser u=null;
		if(list.isEmpty()==false)
		{
		u=list.get(0);
		}
		return u;
	}





	@Override
	public SysUser checkloginUser(String username , String password){
		String selectSql = "select * from tb_sys_user where user_name=:username and password=:password";  		
		SysUser u=null;
		Map params=new HashMap();
		params.put("username", username);
		params.put("password",password);	
		List<SysUser> list=named.query(selectSql,params,new BeanPropertyRowMapper(SysUser.class));
		if(list.isEmpty()==false)
		{
		   u=list.get(0);
		
		}
		return u;
	}





	@Override
	public List<SysUser> queryCheckUserNameList(String userName, Integer id) {
		Object[] params = new Object[] { id,userName};
		List<SysUser> checkNameList=jdbcTemplate.query("select * from  tb_sys_user where id <> ?  and  user_name=?", params,new  BeanPropertyRowMapper(SysUser.class));
		return checkNameList;
	}





	@Override
	public List<SysUser> queryForSysUserFriendsJson() {
		Object[] params = new Object[] {};
		List<SysUser> checkNameList=jdbcTemplate.query("select * from  tb_sys_user ", params,new  BeanPropertyRowMapper(SysUser.class));
		return checkNameList;
	}



}
  


