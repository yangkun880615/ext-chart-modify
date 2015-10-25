/**  
 * Project ：              asset-cmp  
 * FileName ：           com.cntv.cn.util.Recursion.java 
 * Description：
 * @version:     1.0
 * Create at ：        2014-9-24 下午7:03:14  
 *
 * Modification History : 
 * Date                    Author       Version         Description
 *------------------------------------------------------
 * 2014-9-24 下午7:03:14         yk        1.0              
 */

package com.chart.cn.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Recursion {
	List nodeList = new ArrayList();

	Recursion() {// 构造方法里初始化模拟List

		Node node1 = new Node(1, 0);
		Node node2 = new Node(2, 1);
		Node node3 = new Node(3, 1);
		Node node4 = new Node(4, 2);
		Node node5 = new Node(5, 2);
		Node node6 = new Node(6, 2);
		Node node7 = new Node(7, 6);
		Node node8 = new Node(8, 6);

		nodeList.add(node1);
		nodeList.add(node2);
		nodeList.add(node3);
		nodeList.add(node4);
		nodeList.add(node5);
		nodeList.add(node6);
		nodeList.add(node7);
		nodeList.add(node8);

	}

	StringBuffer returnStr = new StringBuffer();

	public void recursionFn(List list, Node node) {
		if (hasChild(list, node)) {
			returnStr.append("{id:");
			returnStr.append(node.getId());
			returnStr.append(",parentId:");
			returnStr.append(node.getParentId());
			returnStr.append(",children:[");
			List childList = getChildList(list, node);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Node n = (Node) it.next();
				recursionFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{id:");
			returnStr.append(node.getId());
			returnStr.append(",parentId:");
			returnStr.append(node.getParentId());
			returnStr.append(",leaf:true},");
		}

	}

	public boolean hasChild(List list, Node node) { // 判断是否有子节点

		return getChildList(list, node).size() > 0 ? true : false;
	}

	public List getChildList(List list, Node node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Node n = (Node) it.next();
			if (n.getParentId() == node.getId()) {
				li.add(n);
			}
		}
		return li;
	}

	public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式

		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}

	public static void main(String[] args) {
		Recursion r = new Recursion();
		r.recursionFn(r.nodeList, new Node(1, 0));
		System.out.println(r.modifyStr(r.returnStr.toString()));
	}
}
