package org.dante.shiro.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
	private static final String DRIVERCLASSNAME = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/shiro";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "iamdante";
	
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(DRIVERCLASSNAME);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConn(Connection conn) {
		try {
			if(null != conn) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Connection conn = DbUtils.getConn();
		System.out.println("连接成功：" + conn);
		DbUtils.closeConn(conn);
	}
	
}
