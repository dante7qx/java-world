package com.dante.jdair;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.dante.http.HttpImgCapture;
import com.dante.jdair.vo.ProductDetailVo;
import com.dante.jdair.vo.ProductVo;

public class JdProduct {
	
	/**
	 insert into deal_product_simple 
(deal_code,name,site_type,cate_id,toshow,img_url,date_begin,date_end,min_bought,max_bought,user_min_bought,user_max_bought,
current_price,title,p_type,subtitle) 
values
	 * 
	 * @param excelName
	 */

	public static void addNewProduct(String excelName) {
		StringBuffer mainBuf = new StringBuffer();
		mainBuf.append("insert into deal_product_simple ")
				.append("\n")
				.append("(deal_code,name,site_type,cate_id,toshow,img_url,date_begin,date_end,")
				.append("min_bought,max_bought,user_min_bought,user_max_bought,")
				.append("current_price,purser_price,postage,title,p_type,subtitle)")
				.append("\n").append("values").append("\n");
		StringBuffer subBuf = new StringBuffer();
		subBuf.append(
				"insert into deal_product_detail(deal_code, sort,img_url,createtime,updatetime) values")
				.append("\n");
		
		try {
			HSSFWorkbook xwb = new HSSFWorkbook(
					new FileInputStream(
							"/Users/dante/Documents/Project/javaworld/src/com/dante/jdair/" + excelName));
			HSSFSheet sheet = xwb.getSheetAt(0);
			int firstRow = sheet.getFirstRowNum();
			int rowSize = sheet.getPhysicalNumberOfRows();
			HSSFRow row = null;
			HSSFCell cellName = null;
			HSSFCell cellCurrentPrice = null;
			HSSFCell cellPurserPrice = null;
			HSSFCell cellPostage = null;
			HSSFCell cellImg = null;
			HSSFCell cellDetailCount = null;
			
			String dealCode = "";
			String name = "";
			double currentPrice = 0;
			double purserPrice = 0;
			double postage = 0;
			String imgPreffix = "";
			int detailCount = 0;

/*
Date startDate = new Date(115, 7, 27);
Date endDate = new Date(115, 7, 31);
while(startDate.compareTo(endDate) <= 0) {
*/
			
			for (int i = firstRow; i < rowSize; i++) {
				row = sheet.getRow(i);
				cellName = row.getCell(1);
				cellCurrentPrice = row.getCell(2);
				cellPurserPrice = row.getCell(3);
				cellPostage = row.getCell(4);
				cellImg = row.getCell(5);
				cellDetailCount = row.getCell(6);
				
				dealCode = UUID.randomUUID().toString();
				name = cellName.getStringCellValue().trim();
				currentPrice = cellCurrentPrice.getNumericCellValue();
				purserPrice = cellPurserPrice.getNumericCellValue();
				postage = cellPostage.getNumericCellValue();
				imgPreffix = cellImg.getStringCellValue().trim();
				detailCount = (int) cellDetailCount.getNumericCellValue();
				mainBuf.append("(")
				.append("'").append(dealCode).append("'").append(",")
				.append("'").append(name).append("'").append(",")
				.append("'").append("microblog").append("'").append(",")
				.append(9).append(",")
				.append(1).append(",")
				.append("'").append("/img/").append(imgPreffix).append("0.jpg").append("'").append(",")
				.append("'").append("2015-11-09 00:00:00").append("'").append(",")
				.append("'").append("2015-12-31 23:59:59").append("'").append(",")
				
//				.append("'").append(formateDate(startDate)).append(" 00:00:00").append("'").append(",")
//				.append("'").append(formateDate(startDate)).append(" 23:59:59").append("'").append(",")
				
				.append(1).append(",")
				.append(900000).append(",")
				.append(1).append(",")
				.append(900000).append(",")
				.append(currentPrice).append(",")
				.append(purserPrice).append(",")
				.append(postage).append(",")
				.append("'").append(name).append("'").append(",")
				.append(0).append(",")
				.append("'").append(name).append("'")
				.append(")").append(",").append("\n");
						
				for (int j = 1; j < detailCount; j++) {
					subBuf.append("(")
					.append("'").append(dealCode).append("'").append(",")
					.append(j).append(",")
					.append("'").append("/img/").append(imgPreffix).append(j).append(".jpg").append("'").append(",")
					.append("now()").append(",")
					.append("now()").append("),")
					.append("\n");
				}
			}
/*			
startDate = getNextDay(startDate);
}
*/
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(mainBuf.toString());
		System.out.println(subBuf.toString());
	}
	
	
	public static void addNewMiaoSha(String excelName) {
		StringBuffer mainBuf = new StringBuffer();
		mainBuf.append("insert into deal_product_simple ")
				.append("\n")
				.append("(deal_code,name,site_type,cate_id,toshow,img_url,date_begin,date_end,")
				.append("min_bought,max_bought,user_min_bought,user_max_bought,")
				.append("current_price,purser_price,title,p_type,subtitle)")
				.append("\n").append("values").append("\n");
		StringBuffer subBuf = new StringBuffer();
		subBuf.append(
				"insert into deal_product_detail(deal_code, sort,img_url,createtime,updatetime) values")
				.append("\n");
		
		try {
			HSSFWorkbook xwb = new HSSFWorkbook(
					new FileInputStream(
							"/Users/dante/Documents/Project/javaworld/src/com/dante/jdair/" + excelName));
			HSSFSheet sheet = xwb.getSheetAt(0);
			int firstRow = sheet.getFirstRowNum();
			int rowSize = sheet.getPhysicalNumberOfRows();
			HSSFRow row = null;
			HSSFCell cellName = null;
			HSSFCell cellImgUrl = null;
			HSSFCell cellCount = null;
			HSSFCell cellPrice = null;
			HSSFCell cellDate = null;
			HSSFCell cellTime = null;
			
			String name = "";
			String img = "";
			int count = 0;
			double price = 0;
			String dateStr = "";
			String time = "";
			for (int i = firstRow; i < rowSize; i++) {
				row = sheet.getRow(i);
				cellName = row.getCell(1);
				cellImgUrl = row.getCell(2);
				cellCount = row.getCell(3);
				cellPrice = row.getCell(4);
				cellDate = row.getCell(5);
				cellTime = row.getCell(6);
				
				name = cellName.getStringCellValue().trim();
				img = cellImgUrl.getStringCellValue().trim();
				count = (int) cellCount.getNumericCellValue();
				price = cellPrice.getNumericCellValue();
				dateStr = cellDate.getStringCellValue().trim();
				time = ((int) cellTime.getNumericCellValue()) + "";
				
				if(Integer.parseInt(time) < 10) {
					time = "0" + time;
				}
				
				if(dateStr.indexOf("-") > 0) {
					int start = Integer.parseInt(dateStr.split("-")[0]);
					int end = Integer.parseInt(dateStr.split("-")[1]);
					
					for (int j = start; j <= end; j++) {
						
						mainBuf.append("(")
						.append("'").append(UUID.randomUUID().toString()).append("'").append(",")
						.append("'").append(name).append("'").append(",")
						.append("'").append("microblog").append("'").append(",")
						.append(13).append(",")
						.append(1).append(",")
						.append("'").append(img).append("'").append(",")
						.append("'").append("2015-10-").append(j).append(" ").append(time).append(":00:00").append("'").append(",")
						.append("'").append("2015-10-").append(j).append(" ").append("23:59:59").append("'").append(",")
						.append(1).append(",")
						.append(count).append(",")
						.append(1).append(",")
						.append(count).append(",")
						.append(price).append(",")
						.append(price).append(",")
						.append("'").append(name).append("'").append(",")
						.append(0).append(",")
						.append("'").append(name).append("'")
						.append(")").append(",").append("\n");
						
					}
				} else {
					mainBuf.append("(")
					.append("'").append(UUID.randomUUID().toString()).append("'").append(",")
					.append("'").append(name).append("'").append(",")
					.append("'").append("microblog").append("'").append(",")
					.append(13).append(",")
					.append(1).append(",")
					.append("'").append(img).append("'").append(",")
					.append("'").append("2015-07-").append(dateStr).append(" ").append(time).append(":00:00").append("'").append(",")
					.append("'").append("2015-07-").append(dateStr).append(" ").append("23:59:59").append("'").append(",")
					.append(1).append(",")
					.append(count).append(",")
					.append(1).append(",")
					.append(count).append(",")
					.append(price).append(",")
					.append(price).append(",")
					.append("'").append(name).append("'").append(",")
					.append(0).append(",")
					.append("'").append(name).append("'")
					.append(")").append(",").append("\n");
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(mainBuf.toString());
	}
	
	
	private static String formateDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	private static Date getNextDay(Date date){  
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
        int day = cl.get(Calendar.DATE);  
        cl.set(Calendar.DATE, day + 1); 
        return cl.getTime();  
    } 
	
	public static List<ProductVo> getAllInlineProduct() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		PreparedStatement dstmt = null;
		ResultSet drs = null;
		List<ProductVo> list = new ArrayList<ProductVo>();
		try {
			pstmt = conn.prepareStatement("select deal_code,name,img_url,current_price,purser_price from deal_product_simple where cate_id not in (select cate_id from deal_product_category where cate_id=13 or p_cate_id=13) and toshow=1 and date_end >='2015-08-25 00:00:00'");
			dstmt = conn.prepareStatement("select img_url from deal_product_detail where deal_code = ? order by sort asc");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String dealCode = rs.getString(1);
				ProductVo vo = new ProductVo();
				vo.setDealCode(dealCode);
				vo.setName(rs.getString(2));
				vo.setImgUrl("http://m.jdair.net/" + rs.getString(3));
				vo.setCurrentPrice(rs.getString(4));
				vo.setPursePrice(rs.getString(5));
				
				dstmt.setString(1, dealCode);
				drs = dstmt.executeQuery();
				while(drs.next()) {
					ProductDetailVo detail = new ProductDetailVo();
					detail.setImgUrl("http://m.jdair.net/" + drs.getString(1));
					vo.getDetails().add(detail);
				}
				
				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
			rs.close();
			drs.close();
			pstmt.close();
			dstmt.close();
			} catch(Exception e) {}
		} 
		return list;
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
	
	
	public static void getProductImg() {
		String basePath = "/Users/dante/Documents/Project/javaworld/webapp/jdair/img/";
		List<ProductVo> vos = getAllInlineProduct();
		for (ProductVo vo : vos) {
			String name = vo.getName();
			String imgUrl = vo.getImgUrl();
			int ind = imgUrl.lastIndexOf("/") + 1;
			String filePath = basePath + name + File.separator;
			String imgName = imgUrl.substring(ind, imgUrl.length());
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdirs();
			}
			HttpImgCapture.exportImg(imgUrl, filePath, imgName);
			List<ProductDetailVo> details = vo.getDetails();
			for (ProductDetailVo detail : details) {
				String url = detail.getImgUrl();
				int inx = url.lastIndexOf("/") + 1;
				String urlName = url.substring(inx, url.length());
				HttpImgCapture.exportImg(url, filePath, urlName);
			}
					
			
		}
		
	}
	
	public static void main(String[] args) {
		addNewProduct("商品demo.xls");
		
//		addNewMiaoSha("临时0929.xls");
		
//		getProductImg();
		
	}
	
}
