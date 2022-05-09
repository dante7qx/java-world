package com.dante.boss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BossDbUtil {
	private static final String DRIVERCLASSNAME = "net.sourceforge.jtds.jdbc.Driver";
	
	private static final String OCS_URL = "jdbc:jtds:sqlserver://10.71.88.214:1433;DatabaseName=deerjet_ocs";
	private static final String CRM_URL = "jdbc:jtds:sqlserver://10.71.88.214:1433;DatabaseName=deerjet_crm";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "1234,qwer";
	
//	private static final String GARSP_URL = "jdbc:jtds:sqlserver://10.70.71.42:1433;DatabaseName=deerjet_dorcs";
//	private static final String GARSP_URL = "jdbc:jtds:sqlserver://10.70.71.42:1433/deerjet_dorcs";
	private static final String GARSP_URL = "jdbc:jtds:sqlserver://10.72.24.6:1433/deerjet_me";
	
	/*
	private static final String OCS_URL = "jdbc:jtds:sqlserver://10.70.71.42:1433;DatabaseNameo=deerjet_ocs";
	private static final String CRM_URL = "jdbc:jtds:sqlserver://10.70.71.42:1433;DatabaseName=deerjet_crm";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "999999999";
	*/
	private static Connection ocsConn = null;
	private static Connection crmConn = null;

	public static Connection getOcsConn() {
		try {
			Class.forName(DRIVERCLASSNAME);
			ocsConn = DriverManager.getConnection(OCS_URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ocsConn;
	}
	
	public static Connection getCrmConn() {
		try {
			Class.forName(DRIVERCLASSNAME);
			crmConn = DriverManager.getConnection(CRM_URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return crmConn;
	}
	
	public static Connection getConn(String url, String userName, String password) {
		try {
			Class.forName(DRIVERCLASSNAME);
			ocsConn = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ocsConn;
	}

	public static void closeOcsConn() {
		try {
			ocsConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeCrmConn() {
		try {
			crmConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Connection conn = getConn(GARSP_URL, "sa", "12345,qwert");
		System.out.println(conn.getMetaData().getDatabaseProductName());
		conn.close();
	}
	
}
