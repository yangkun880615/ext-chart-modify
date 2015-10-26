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
public class ParameterException extends RuntimeException  {

	/** serialVersionUID */
	private static final long serialVersionUID = 6417641452178955756L;

	public ParameterException() {
		super();
	}

	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}

	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}


}
  


