package com.dante.jdair;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.jdbc.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;


public class JdAirCC {

	public static void generateJdEmpXlsx(String excelName, String source) {
		StringBuffer addEmpBuf = new StringBuffer("INSERT INTO `datacenter`.`members`");
		addEmpBuf.append("\n")
				.append("(`id`, `user_mobile`, `pwd`,`yjpwd`, `real_name`, `source`, `date_entered`, `date_modified`)")
				.append("\n").append("VALUES").append("\n");
		StringBuffer updateEmpBuf = new StringBuffer();
		Connection conn = getConnection();
		try {
			int cw = 0;
			DecimalFormat df = new DecimalFormat("#");
			XSSFWorkbook xwb = new XSSFWorkbook(
					new FileInputStream(
							"/Users/dante/Documents/Project/javaworld/src/com/dante/jdair/" + excelName));
			XSSFSheet sheet = xwb.getSheetAt(0);
			int firstRow = sheet.getFirstRowNum();
			int rowSize = sheet.getPhysicalNumberOfRows();
			XSSFRow row = null;
			XSSFCell cellName = null;
			XSSFCell cellPhone = null;
			String name = "";
			String phone = "";
			String yjpwd = "";
			String existId = "";
			for (int i = firstRow; i < rowSize; i++) {
				row = sheet.getRow(i);
				cellName = row.getCell(0);
				cellPhone = row.getCell(1);
				name = cellName.getStringCellValue().trim().replaceAll(" ", "");
				if(XSSFCell.CELL_TYPE_NUMERIC == cellPhone.getCellType()) {
					phone = df.format((cellPhone.getNumericCellValue()));
				} else {
					phone = cellPhone.getStringCellValue().trim();
				}
				try{
					yjpwd = phone.substring(phone.length() - 4) + getStringPinYin(name).substring(0, 2);
					} catch(Exception e) {
						System.out.println("=======" + name);
					}
				
				existId = getExistMemberByMobile(conn, phone);
				if(StringUtils.isNullOrEmpty(existId)) {
					addEmpBuf.append("(uuid(),")
					.append("'").append(phone).append("'").append(",")
					.append("'").append(MD5Util.MD5Encode(yjpwd)).append("'").append(",")
					.append("'").append(yjpwd).append("'").append(",")
					.append("'").append(name).append("'").append(",")
					.append("'").append(source).append("'").append(",")
					.append("now()").append(",")
					.append("now()),").append("\n");
				} else if("no".equals(existId)) {
					cw++;
					continue;
				} else {
					updateEmpBuf.append("update `datacenter`.`members` set pwd=")
					.append("'").append(MD5Util.MD5Encode(yjpwd)).append("'").append(",")
					.append("yjpwd=").append("'").append(yjpwd).append("'").append(",")
					.append("real_name=").append("'").append(name).append("'").append(",")
					.append("source=").append("'purser'").append(",")
					.append("date_entered=now()").append(",")
					.append("date_modified=now()")
					.append(" where id = '").append(existId).append("';")
					.append("\n");
				}
				
				
			}
			System.out.println("cw:" + cw);
			System.out.println(addEmpBuf.toString());
			System.out.println("|*************************|");
			System.out.println(updateEmpBuf.toString());
			xwb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public static void generateJdEmpXls(String excelName, String source) {
		StringBuffer addEmpBuf = new StringBuffer("INSERT INTO `datacenter`.`members`");
		addEmpBuf.append("\n")
				.append("(`id`, `user_mobile`, `pwd`,`yjpwd`, `real_name`, `source`, `date_entered`, `date_modified`)")
				.append("\n").append("VALUES").append("\n");
		StringBuffer updateEmpBuf = new StringBuffer();
		Connection conn = getConnection();
		try {
			int cw = 0;
			DecimalFormat df = new DecimalFormat("#");
			HSSFWorkbook xwb = new HSSFWorkbook(
					new FileInputStream(
							"/Users/dante/Documents/Project/javaworld/src/com/dante/jdair/" + excelName));
			HSSFSheet sheet = xwb.getSheetAt(0);
			int firstRow = sheet.getFirstRowNum();
			int rowSize = sheet.getPhysicalNumberOfRows();
			HSSFRow row = null;
			HSSFCell cellName = null;
			HSSFCell cellPhone = null;
			String name = "";
			String phone = "";
			String yjpwd = "";
			String existId = "";
			for (int i = firstRow; i < rowSize; i++) {
				row = sheet.getRow(i);
				cellName = row.getCell(0);
				cellPhone = row.getCell(1);
				name = cellName.getStringCellValue().trim().replaceAll(" ", "");
				if(XSSFCell.CELL_TYPE_NUMERIC == cellPhone.getCellType()) {
					phone = df.format((cellPhone.getNumericCellValue()));
				} else {
					phone = cellPhone.getStringCellValue().trim();
				}
				try{
				yjpwd = phone.substring(phone.length() - 4) + getStringPinYin(name).substring(0, 2);
				} catch(Exception e) {
					System.out.println("=======" + name);
				}
				existId = getExistMemberByMobile(conn, phone);
				if(StringUtils.isNullOrEmpty(existId)) {
					addEmpBuf.append("(uuid(),")
					.append("'").append(phone).append("'").append(",")
					.append("'").append(MD5Util.MD5Encode(yjpwd)).append("'").append(",")
					.append("'").append(yjpwd).append("'").append(",")
					.append("'").append(name).append("'").append(",")
					.append("'").append(source).append("'").append(",")
					.append("now()").append(",")
					.append("now()),").append("\n");
				} else if("no".equals(existId)) {
					cw++;
					continue;
				} else {
					updateEmpBuf.append("update `datacenter`.`members` set pwd=")
					.append("'").append(MD5Util.MD5Encode(yjpwd)).append("'").append(",")
					.append("yjpwd=").append("'").append(yjpwd).append("'").append(",")
					.append("real_name=").append("'").append(name).append("'").append(",")
					.append("source=").append("'").append(source).append("'").append(",")
					.append("date_entered=now()").append(",")
					.append("date_modified=now()")
					.append(" where id = '").append(existId).append("';")
					.append("\n");
				}
				
				
			}
			System.out.println("cw:" + cw);
			System.out.println(addEmpBuf.toString());
			System.out.println("|*************************|");
			System.out.println(updateEmpBuf.toString());
			xwb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	private static String getStringPinYin(String str) {
		StringBuffer pinYin = new StringBuffer();
		try {
			for (int i = 0; i < str.length(); i++) {
				String[] arr = PinyinHelper.toHanyuPinyinStringArray(str.charAt(i));
				if(arr != null) {
					pinYin.append(arr[0].charAt(0));
				}
			}
		} catch(Exception e) {
			System.out.println("======>" + str);
		}
		return pinYin.toString();
	}
	
	private static String getExistMemberByMobile(Connection conn, String mobile) {
		String id = null;
		String source = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			pstmt = conn.prepareStatement("SELECT id,source FROM members where user_mobile = ?");
			pstmt.setString(1, mobile);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(i > 0) {
					System.out.println(mobile + "============");
				}
				id = rs.getString(1);
				source = rs.getString(2);
				if("purser".equals(source) || "jdemp".equals(source) || "gfpurser".equals(source)) {
					id = "no";
				}
				i++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
			rs.close();
			pstmt.close();
			} catch(Exception e) {
				
			}
		}
		return id;
	}
	
	
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://10.70.35.69:3306/datacenter", "jdair", "capital20141020");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private static void generateCaptcha() {
		StringBuffer buf = new StringBuffer("insert into captcha");
		buf.append("\n").append("(content,effect,createtime,updatetime)")
				.append("\n").append("values");
		for (int i = 0; i < 2000; i++) {
			buf.append("(").append("'")
					.append((int) ((Math.random() * 9 + 1) * 100000))
					.append("'").append(",").append(1).append(",")
					.append("now()").append(",").append("now()").append("),")
					.append("\n");
		}
		System.out.println(buf.toString());
	}
	
	public static void main(String[] args) throws Exception {
		generateJdEmpXlsx("0615.xlsx", "jdemp");
//		generateJdEmpXls("18期学员信息表最新.xls", "purser");
		
//		generateCaptcha();
		
		
//		System.out.println(MD5Util.MD5Encode("4555pd"));
	}
	
}
