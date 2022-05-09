package com.dante.jdair;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JdAirZYJ {

	/*
	INSERT INTO datacenter.deal_product_simple
	(deal_code, name, sub_name, site_type, cate_id, toshow, img_url, date_begin, date_end, min_bought, max_bought, user_min_bought, user_max_bought, current_price, title, p_type, subtitle, createDateTime)
	VALUES('da9c5fe8-0b60-11e5-abea-020027fb0009', '5周年纪念版套装限量版编号小熊', NULL, 'microblog', 25, 1, NULL, '/img/bannerxx_1.png', null, 1, 5, 1, 2, 0.0, 100.0, 0.0, 0.0, 0.0, 0.0, '5周年纪念版套装限量版编号小熊', 0, '首航线上商品', NULL, NULL, NULL, NULL, NULL, NULL, 10, 0, NULL, NULL, NULL, NULL, 0.0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, STR_TO_DATE('2015-06-05 19:08:30','%Y-%m-%d %H:%i:%s'));

	*/
	
	public static void main(String[] args) {
		StringBuffer buf = new StringBuffer();
		buf.append("INSERT INTO datacenter.deal_product_simple")
		.append("\n")
		.append("(deal_code, name, site_type, cate_id, toshow, img_url, date_begin, date_end, min_bought, max_bought, user_min_bought, user_max_bought, current_price, postage, title, p_type, subtitle, createDateTime)")
		.append("\n")
		.append("VALUES")
		.append("\n");
		
		
//		buildXiaoXiong(buf);
//		buildduanwujie(buf, "铝框拉杆箱24寸", 26, "/img/banner_lklgx0.jpg", 10, 499, 40, "11:55:00", "23:59:59");
//		buildduanwujie(buf, "铝框拉杆箱28寸", 26, "/img/banner_lklgx0.jpg", 10, 598, 40, "11:55:00", "23:59:59");
//		buildduanwujie(buf, "九朵云美白祛斑面霜", 28, "/img/banner_qbms0.jpg", 10, 100, 20, "11:55:00", "23:59:59");
//		buildduanwujie(buf, "谜尚魅力靓白柔护霜", 28, "/img/banner_brhs0.jpg", 10, 79, 20, "11:55:00", "23:59:59");
		buildduanwujie(buf, "咔咔小马可调节手机支架", 29, "/img/banner_sjzj0.jpg", 10, 30, 0, "00:00:00", "23:59:59");
//		
//		buildduanwujie(buf, "MOMAX 移动电源8400毫安", 27, "/img/banner_mlxxdy0.jpg", 10, 229, 20, "17:55:00", "23:59:59");
//		buildduanwujie(buf, "收纳七件套", 26, "/img/banner_snqjt0.jpg", 10, 149, 20, "17:55:00", "23:59:59");
//		buildduanwujie(buf, "奇迹马油霜", 28, "/img/banner_qjmys0.jpg", 10, 109, 20, "17:55:00", "23:59:59");
//		buildduanwujie(buf, "可莱丝N.M.F水库针剂补水面膜升级版", 28, "/img/banner_mmsj0.jpg", 10, 99, 20, "17:55:00", "23:59:59");
//		
//		buildduanwujie(buf, "吕 防脱固发生发洗发水", 28, "/img/banner_lfxh0.jpg", 10, 119, 20, "21:55:00", "23:59:59");
//		buildduanwujie(buf, "SNP动物面膜", 28, "/img/banner_lfxh0.jpg", 10, 119, 20, "21:55:00", "23:59:59");
//		buildduanwujie(buf, "苹果品牌移动电源9000mAh", 27, "/img/banner_zndy90.jpg", 10, 329, 20, "21:55:00", "23:59:59");
//		buildduanwujie(buf, "多功能阿拉神灯", 29, "/img/banner_aldd0.jpg", 10, 189, 20, "21:55:00", "23:59:59");
		
		System.out.println(buf.toString());
	}
	
	private static String formateDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	private static String buildduanwujie(StringBuffer buf, String name, int cateId, String imgUrl, int count, int price, int postage, String start, String end) {
		Date startDate = new Date(115, 7, 1);
		Date endDate = new Date(115, 7, 31);
		while(startDate.compareTo(endDate) <= 0) {
			buf.append("(uuid(),")
			.append("'").append(name).append("'").append(",")
			.append("'microblog'").append(",")
			.append(cateId).append(",")
			.append("1").append(",")
			.append("'").append(imgUrl).append("'").append(",")
			.append("'").append(formateDate(startDate)).append(" ").append(start).append("'").append(",")
			.append("'").append(formateDate(startDate)).append(" ").append(end).append("'").append(",")
			.append(1).append(",")
			.append(count).append(",")
			.append(1).append(",")
			.append(count).append(",")
			.append(price).append(",")
			.append(postage).append(",")
			.append("'").append(name).append("'").append(",")
			.append("0").append(",")
			.append("'").append(name).append("'").append(",")
			.append("now())")
			.append(",")
			.append("\n");
			
			startDate = getNextDay(startDate);
		}
		
		return buf.toString();
	}
	
	/**
	 * 周一见 小熊
	 */
	private static String buildXiaoXiong(StringBuffer buf) {
		Date startDate = new Date(115, 5, 8);
		Date endDate = new Date(115, 5, 30);
		while(startDate.compareTo(endDate) <= 0) {
			buf.append("(uuid(),")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("'microblog'").append(",")
			.append("25").append(",")
			.append("1").append(",")
			.append("'/img/bannerxx_1.png'").append(",")
			.append("'").append(formateDate(startDate)).append(" 11:55:00'").append(",")
			.append("'").append(formateDate(startDate)).append(" 13:35:00'").append(",")
			.append("1").append(",")
			.append("5").append(",")
			.append("1").append(",")
			.append("1").append(",")
			.append("100").append(",")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("0").append(",")
			.append("'首航线上商品'").append(",")
			.append("now())")
			.append(",")
			.append("\n")
			
			.append("(uuid(),")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("'microblog'").append(",")
			.append("25").append(",")
			.append("1").append(",")
			.append("'/img/bannerxx_1.png'").append(",")
			.append("'").append(formateDate(startDate)).append(" 14:55:00'").append(",")
			.append("'").append(formateDate(startDate)).append(" 16:35:00'").append(",")
			.append("1").append(",")
			.append("5").append(",")
			.append("1").append(",")
			.append("1").append(",")
			.append("150").append(",")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("0").append(",")
			.append("'首航线上商品'").append(",")
			.append("now())")
			.append(",")
			.append("\n")
			
			.append("(uuid(),")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("'microblog'").append(",")
			.append("25").append(",")
			.append("1").append(",")
			.append("'/img/bannerxx_1.png'").append(",")
			.append("'").append(formateDate(startDate)).append(" 21:55:00'").append(",")
			.append("'").append(formateDate(startDate)).append(" 23:35:00'").append(",")
			.append("1").append(",")
			.append("10").append(",")
			.append("1").append(",")
			.append("1").append(",")
			.append("200").append(",")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("0").append(",")
			.append("'首航线上商品'").append(",")
			.append("now())")
			.append(",")
			.append("\n")
			
			.append("(uuid(),")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("'microblog'").append(",")
			.append("25").append(",")
			.append("1").append(",")
			.append("'/img/bannerxx_1.png'").append(",")
			.append("'").append(formateDate(startDate)).append(" 23:55:00'").append(",")
			.append("'").append(formateDate(getNextDay(startDate))).append(" 01:35:00'").append(",")
			.append("1").append(",")
			.append("3").append(",")
			.append("1").append(",")
			.append("1").append(",")
			.append("80").append(",")
			.append("'5周年纪念版套装限量版编号小熊'").append(",")
			.append("0").append(",")
			.append("'首航线上商品'").append(",")
			.append("now())")
			.append(",")
			.append("\n");
			startDate = getNextDay(startDate);
		}
		return buf.toString();
	}
	
	private static Date getNextDay(Date date){  
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
        int day = cl.get(Calendar.DATE);  
        cl.set(Calendar.DATE, day + 1); 
        return cl.getTime();  
    }  
	
}
