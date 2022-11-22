package com.dante.hutool;

import org.junit.Test;
import cn.hutool.core.util.IdcardUtil;

public class IdCardTest {
	
	/*
	 * 孙茂远 身份证号码：420521199110187717 出生日期：1991年10月18日 性别：男 省份：湖北省 市：宜昌市 县：宜昌县 手机号：13564758670
	 * 张太贵 身份证号码：610431198404075719 出生日期：1984年04月07日 性别：男 省份：陕西省 市：咸阳市 县：武功县 手机号：16734056531
	 * 姓名：彭国均 身份证号码：431221198707013818 出生日期：1987年07月01日 性别：男 省份：湖南省 市：怀化市 县：中方县 手机号：19568958718
	 * 
	 * 15位身份证号码：310112850409522
	 */
	
	@Test
	public void testIdCardUtil() {
		String ID_18 = "420521199110187717";
		String ID_15 = "310112850409522";
		
		//是否有效
		boolean valid = IdcardUtil.isValidCard(ID_18);
		boolean valid15 = IdcardUtil.isValidCard(ID_15);
		System.out.println("ID_18: " + valid);
		System.out.println("ID_15: " + valid15);
		
		//转换
		String convert15To18 = IdcardUtil.convert15To18(ID_15);
		System.out.println(ID_15 + " <--> " + convert15To18);

		//年龄
		int age = IdcardUtil.getAgeByIdCard(ID_18);
		int age2 = IdcardUtil.getAgeByIdCard(ID_15);
		System.out.println("ID_18: " + age);
		System.out.println("ID_15: " + age2);
		
		//性别
		int gen = IdcardUtil.getGenderByIdCard(ID_18);
		int gen2 = IdcardUtil.getGenderByIdCard(ID_18);
		System.out.println("ID_18: " + gen);
		System.out.println("ID_15: " + gen2);

		//生日
		String birth = IdcardUtil.getBirthByIdCard(ID_18);
		String birth2 = IdcardUtil.getBirthByIdCard(ID_15);
		System.out.println("ID_18: " + birth);
		System.out.println("ID_15: " + birth2);
		
		//省份
		String province = IdcardUtil.getProvinceByIdCard(ID_18);
		String province2 = IdcardUtil.getProvinceByIdCard(ID_15);
		System.out.println("ID_18: " + province);
		System.out.println("ID_15: " + province2);
		
		//城市
		String city = IdcardUtil.getCityCodeByIdCard(ID_18);
		String city2 = IdcardUtil.getCityCodeByIdCard(ID_15);
		System.out.println("ID_18: " + city);
		System.out.println("ID_15: " + city2);
		
	}
	
	
}
