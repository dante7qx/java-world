package com.epolleo.bp.repo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class SqlChecker {
	Pattern tablePattern = Pattern.compile("\\w+");
	Pattern columnPattern = Pattern.compile("\\w+");
	Pattern valuePattern = Pattern.compile("\\w+");

	public void checkTableName(String tableName) {
		Matcher matcher = tablePattern.matcher(tableName);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					"The table name is not conform with rule, only word character are allowed. tableName: " + tableName);
		}

	}

	public void checkColName(String colName) {
		Matcher matcher = columnPattern.matcher(colName);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					"The column name is not conform with rule, only word character are allowed. columnName: " + colName);
		}
	}

    public void checkColValue(String val) {
        Matcher matcher = valuePattern.matcher(val);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "The column value is not conform with rule, only word character are allowed. value: " + val);
        }
    }

}
