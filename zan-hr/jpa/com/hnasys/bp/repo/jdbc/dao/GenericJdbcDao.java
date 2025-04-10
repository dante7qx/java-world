package com.epolleo.bp.repo.jdbc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import com.epolleo.bp.repo.paging.PagingParam;
import com.epolleo.bp.repo.paging.PagingResult;
import com.epolleo.bp.repo.utils.SimpleSqlBuilder;
import com.epolleo.bp.repo.utils.SimpleSqlBuilder.UpdateStatement;

@Repository
public class GenericJdbcDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SimpleSqlBuilder sqlBuilder;

	public PagingResult<Map<String, Object>> findPaing(DataSource dataSource, String tableName, PagingParam pagingParam) {
		String countSql = sqlBuilder.count(dataSource, tableName, pagingParam);
		String findAllSql = sqlBuilder.findAll(dataSource, tableName, pagingParam);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		RowMapper<Map<String, Object>> rowMapper = new ColumnMapRowMapper();
		List<Map<String, Object>> resultList = jdbcTemplate.query(findAllSql, rowMapper);
		long total = jdbcTemplate.queryForLong(countSql);
		PagingResult<Map<String, Object>> result = new PagingResult<Map<String, Object>>(resultList, (int) total);
		return result;
	}

	public void updateTable(DataSource dataSource, String tableName, String pKeyCol, HashMap<String, SqlParameterValue> sqlParams) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		UpdateStatement statement = sqlBuilder.update(dataSource, tableName, pKeyCol, sqlParams);
		int update = jdbcTemplate.update(statement.sql, statement.args);
		if (update != 1) {
			logger.warn("update statement expect one row to be affected, but the reuslt is {}.\n"
					+ "table:{}, content: {}", new Object[] { update, tableName, sqlParams });
		}
	}
	public void insertRecord(DataSource dataSource, String tableName, String pKeyCol, HashMap<String, SqlParameterValue> sqlParams) {
	    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	    UpdateStatement statement = sqlBuilder.insert(dataSource, tableName, pKeyCol, sqlParams);
	    //TODO: return generated id value
	    int update = jdbcTemplate.update(statement.sql, statement.args);
	    if (update != 1) {
	        logger.warn("update statement expect one row to be affected, but the reuslt is {}.\n"
	                + "table:{}, content: {}", new Object[] { update, tableName, sqlParams });
	    }
	}

    public void deleteRecord(DataSource dataSource, String tableName, String pKeyCol, Integer id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UpdateStatement statement = sqlBuilder.delete(dataSource, tableName, pKeyCol, id);
        int update = jdbcTemplate.update(statement.sql, statement.args);
        if (update != 1) {
            logger.warn("update statement expect one row to be affected, but the reuslt is {}.\n"
                    + "table:{}, content: {}", new Object[] { update, tableName, id });
        }
    }

}
