
package com.chart.cn.core.pagination;

/**
 * @ClassName: PaginationSelectQueryLanguage
 * @Description: 分页查询SQL语句定义接口
 * @date 2013年11月20日 上午8:29:19
 *
 */
public interface PaginationQueryLanguage {


    /**
     * 
    * @Title: getNativeQueryLanguage
    * @Description: 获取本地查询语言
    * @param sql sql
    * @param pagination 分页对象
    * @return     
    * @throws
     */
    StringBuffer getNativeQueryLanguage(StringBuffer sql, Pagination pagination);
}
