package com.epolleo.bp.pub;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * PagingForm
 * <p>
 * Date: 2012-06-30,18:21:30 +0800
 * 
 * @version 1.0
 */
public class PagingForm {
    int pageNo = 1;
    int pageSize = 10;
    String sort;
    String order;
    Map<String, Object> filter;

    public Map<String, Object> getFilter() {
        if (filter != null)
            return filter;
        return Collections.emptyMap();
    }

    public void setFilterMap(Map<String, Object> filter) {
        this.filter = filter;
    }
    
    /**
     * 添加查询过滤条件
     * @param key 条件参数名
     * @param value 用户输入值
     */
    public void addFilter(String key, Object value) {
    	if (filter == null) {
    		filter = new HashMap<String, Object>();
    	}
    	if (key != null && value != null) {
    		filter.put(key, value);
    	}
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setPage(int page) {
        this.pageNo = page;
    }

    public void setRows(int rows) {
        this.pageSize = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getSkip() {
        return pageSize * (pageNo - 1);
    }

}
