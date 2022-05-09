package com.epolleo.bp.repo.jpa.dao;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Repository;

import com.epolleo.bp.repo.jpa.po.CodeDictionary;
import com.epolleo.bp.repo.paging.PagingParam;

//@Repository
public class CodeDictionaryDao extends AbstractDaoSupport {

	public long findAliasCount(String table, String column) {
		PagingParam pagingParam = new PagingParam();
		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();
		filters.put("tableName", table);
		filters.put("columnName", column);
		pagingParam.setFilter(filters);
		return countAll(CodeDictionary.class, pagingParam);
	}
}
