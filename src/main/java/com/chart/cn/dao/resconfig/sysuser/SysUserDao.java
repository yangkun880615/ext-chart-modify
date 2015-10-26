/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.dao.sysrole.SysUserDao.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-29 上午11:31:01  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-29 上午11:31:01         yk        1.0              
*/  

package com.chart.cn.dao.resconfig.sysuser;


import java.util.List;

import com.chart.cn.core.dao.BaseDao;
import com.chart.cn.core.pagination.Pagination;
import com.chart.cn.entity.resconfig.SysUser;

public interface SysUserDao extends BaseDao<SysUser, Integer> {

	
   
	

	Pagination querySysUserForListBydeptName(String deptName, int pageSize, int pageNo);

	     /**
	      * 
	      * @param username
	      * @param password
	      * @return
	      */
	       
	 SysUser verifyloginUser(String username,String password);
	  /**
	    * 
		*
			* Description :  
			* @author :     qly
			* @Version      1.0
			* @param username
	        * @param password
			* @return
			* date :        2014年12月26日
			* MethodName :  checkloginUser
			* ReturnType :  SysUser
			* --------------------------------------------------
			* 修改人          修改日期                         修改描述
			* qly             2014年1月9日                   创建
			* --------------------------------------------------
	   */
	 SysUser  checkloginUser(String username,String password);
	 
	/**
	 * 
	* Description :  验证用户名不能重复
	* @author :     yk
	* @Version      1.0
	* date :        2015年1月30日
	* MethodName :  queryCheckUserNameList
	* ReturnType :  List<SysUser> 
	* --------------------------------------------------
	* 修改人          修改日期                                  修改描述
	* yk   2015年1月30日          创建
	* --------------------------------------------------
	 */
	List<SysUser> queryCheckUserNameList(String userName, Integer id);

	List<SysUser> queryForSysUserFriendsJson();
}
  


