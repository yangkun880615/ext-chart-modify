/**  
 * Project ：              asset-cmp  
 * FileName ：           com.cntv.cn.service.sysrole.SysUserServiceImp.java 
 * Description：
 * @version:     1.0
 * Create at ：        2014-9-29 下午2:35:10  
 *
 * Modification History : 
 * Date                    Author       Version         Description
 *------------------------------------------------------
 * 2014-9-29 下午2:35:10         yk        1.0              
 */

package com.chart.cn.service.resconfig.sysuser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.chart.cn.constant.TableNameConstant;
import com.chart.cn.core.SysUserThread;
import com.chart.cn.core.pagination.Pagination;
import com.chart.cn.core.service.BaseServiceImpl;
import com.chart.cn.dao.resconfig.sysuser.SysUserDao;
import com.chart.cn.entity.resconfig.SysUser;
import com.chart.cn.exception.BusinessException;
import com.chart.cn.util.DateUtil;
import com.chart.cn.util.JsonUtils;
import com.chart.cn.util.MD5Util;

@Service
@Transactional
public class SysUserServiceImp extends BaseServiceImpl<SysUser, Integer>
		implements SysUserService {

	@Resource
	private SysUserDao sysUserDao;


	@Autowired
	public void setBaseDao() {
		super.setBaseDao(sysUserDao);
	}


	@Override
	public SysUser verifyloginUser(String username, String password) {
		SysUser u = sysUserDao.verifyloginUser(username, password);
		return u;
	}


	@Override
	public String queryForSysUserFriendsJson(boolean isShowCheck) {
		List<SysUser> sysUserList = sysUserDao.queryForSysUserFriendsJson();
		// 拼接Json 串
	   StringBuffer sysUserStringBuff = new StringBuffer();
	   sysUserStringBuff.append("{");
		// 根节点
	   sysUserStringBuff
				.append(JsonUtils.buildData("string", "text", "我的好友")).append(",");
	   sysUserStringBuff.append(JsonUtils.buildData("boolean", "leaf", false)).append(",");
		// 部门不为空
		if (null != sysUserList && sysUserList.size() > 0) {
			// 封装部门的json串
			sysUserStringBuff.append("\"children\":[");
			for (SysUser sysUser : sysUserList) {
			sysUserStringBuff.append("{");
			// 部门的信息，名称
			sysUserStringBuff.append(JsonUtils.buildData("string", "id", sysUser.getId()))
					.append(",");
			sysUserStringBuff.append(
					JsonUtils.buildData("string", "text", sysUser.getUserName())).append(",");
			sysUserStringBuff.append(JsonUtils.buildData("boolean", "leaf", true)).append(",");
			sysUserStringBuff.append(JsonUtils.buildData("string", "iconCls", "user"));

			
			if (sysUserList.indexOf(sysUserList) == sysUserList.size() - 1) {
				sysUserStringBuff.append("}");
				break;
			}
			sysUserStringBuff.append("},");
			
			}
			
			// 结束所有的Json数据
			sysUserStringBuff.append("]");
		}
		sysUserStringBuff.append("}");
		return sysUserStringBuff.toString();
	}



}
