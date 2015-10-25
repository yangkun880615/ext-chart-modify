/**  
* Project ：              asset-cmp  
* FileName ：           com.cntv.cn.util.Node.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-24 下午7:02:36  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-24 下午7:02:36         yk        1.0              
*/  

package com.chart.cn.util;
public class Node {
	private int id;

	private int parentId;

	Node(){}

	Node(int id,int parentId){


	this.id=id;


	this.parentId = parentId;

	}

	public int getId() {


	return id;

	}

	public void setId(int id) {


	this.id = id;

	}

	public int getParentId() {


	return parentId;

	}

	public void setParentId(int parentId) {


	this.parentId = parentId;

	}
	}


