
package com.chart.cn.core.pagination;



public class MysqlNativePaginationQuery implements PaginationQueryLanguage {

    /*
     * 
    * <p>Title: getNativeQueryLanguage</p>
    * <p>Description: </p>
    * @param sql
    * @param pagination
    * @return
     */
    public StringBuffer getNativeQueryLanguage(StringBuffer sql, Pagination pagination) {
        StringBuffer nativeSql = new StringBuffer();

        int fristResult = Math.abs(pagination.getPageSize() * (pagination.getPageNo() - 1));
 
       int lastResult =pagination.getPageSize();
        nativeSql.append(" select _t.* from ( select row_.* from ( ");
        nativeSql.append(sql);
        nativeSql.append(" ) row_     ");
        nativeSql.append(" ) _t  limit   ");
        nativeSql.append(fristResult);
        nativeSql.append(",");
        nativeSql.append(lastResult);
        return nativeSql;
    }

}
