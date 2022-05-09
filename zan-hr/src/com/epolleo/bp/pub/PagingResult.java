package com.epolleo.bp.pub;

import java.util.ArrayList;
import java.util.List;

/**
 * PagingResult
 * <p>
 * Date: 2012-06-30,18:21:39 +0800
 * 
 * @version 1.0
 */
public class PagingResult<T> {
    List<T> rows;
    int total;

    public PagingResult(List<T> records, int total) {
        super();
        this.rows = records;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static <T> PagingResult<T> fromSingleResult(T element) {
        List<T> records = new ArrayList<T>(1);
        records.add(element);
        PagingResult<T> result = new PagingResult<T>(records, 1);
        return result;
    }

}
