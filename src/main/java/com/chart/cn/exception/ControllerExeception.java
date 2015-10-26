/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.core.dao.a.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-15 下午6:13:46  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-15 下午6:13:46         yk        1.0              
*/ 
package com.chart.cn.exception;
public class ControllerExeception extends RuntimeException {
	private static final long serialVersionUID = 2332608236621015980L;
	private String code;

	public ControllerExeception() {
		super();
	}

	public ControllerExeception(String message) {
		super(message);
	}

	public ControllerExeception(String code, String message) {
		super(message);
		this.code = code;
	}

	public ControllerExeception(Throwable cause) {
		super(cause);
	}

	public ControllerExeception(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerExeception(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
  


