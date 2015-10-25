package com.chart.cn.core.pagination;

import java.util.List;
import java.util.Map;

/**
 * 列表分页。包含list属性。
 * 
 */
@SuppressWarnings("serial")
public class Pagination extends SimplePage implements java.io.Serializable,
		Paginable {

	public Pagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(int pageNo, int pageSize, int totalCount, List<?> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<?> list;

	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */
	public void setList(@SuppressWarnings("rawtypes") List list) {
		this.list = list;
	}
	/**
	 * 分页小计数据
	 */
	private Map<String ,Integer> minSumMap;
	
	private Map<String ,Integer> totolalSumMap;

	public Map<String, Integer> getMinSumMap() {
		return minSumMap;
	}

	public void setMinSumMap(Map<String, Integer> minSumMap) {
		this.minSumMap = minSumMap;
	}

	public Map<String, Integer> getTotolalSumMap() {
		return totolalSumMap;
	}

	public void setTotolalSumMap(Map<String, Integer> totolalSumMap) {
		this.totolalSumMap = totolalSumMap;
	}

	@Override
	public String toString() {
		return "Pagination [totalCount=" + totalCount + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + "]";
	}

	
	

	
}
