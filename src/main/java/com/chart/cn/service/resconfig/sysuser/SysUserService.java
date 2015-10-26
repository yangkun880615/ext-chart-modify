/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.service.sysrole.SysUserService.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-29 下午2:33:39  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-29 下午2:33:39         yk        1.0              
*/  

package com.chart.cn.service.resconfig.sysuser;


import com.chart.cn.core.service.BaseService;
import com.chart.cn.entity.resconfig.SysUser;



public interface SysUserService extends BaseService<SysUser, Integer> {

	
	SysUser verifyloginUser(String username,String password);

	String queryForSysUserFriendsJson(boolean b);
	


}
  


