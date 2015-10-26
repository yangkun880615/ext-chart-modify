/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.core.dao.BusinessException.java 
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
public class BusinessException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 2332608236621015980L;

	private String code;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String code, String message, Throwable cause) {
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
  


