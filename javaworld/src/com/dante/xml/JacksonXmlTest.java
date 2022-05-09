package com.dante.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dante.hiapp.vo.ArrayOfResortInfoEntity;
import com.dante.hiapp.vo.HnaResponse;
import com.dante.hiapp.vo.ResortInfoEntity;
import com.dante.hiapp.vo.req.HnaRequest;
import com.dante.xml.vo.A;
import com.dante.xml.vo.BaseVo;
import com.dante.xml.vo.Oa;
import com.dante.xml.vo.Person;
import com.dante.xml.vo.ResA;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thoughtworks.xstream.XStream;

import HNA.Security.SecurityUtils;

public class JacksonXmlTest {

	private XmlMapper xmlMapper = new XmlMapper();

	private Person person;

	@Before
	public void init() {
		person = new Person();
		person.setName("sarah");
		person.setAge(10);
		person.setSex("male");
		person.setTelephone("12345678");
		List<String> friends = new ArrayList<String>();
		friends.add("mattew");
		friends.add("phoenix");
		person.setFriends(friends);
	}

	@Test
	public void testXmlToJavabean() throws JsonParseException, JsonMappingException, IOException {
		XStream xstream = new XStream();
		xstream.alias("Person", Person.class);
		String xml = xstream.toXML(person);
		System.out.println(xml);
		System.out.println(xmlMapper.writeValueAsString(person));
		
		// xml:
		// <person><name>sarah</name><age>10</age><sex>male</sex><telephone>12345678</telephone><friends><string>mattew</string><string>phoenix</string></friends></person>
		String x = "<?xml version=\"1.0\" encoding=\"utf-8\"?><person><name>sarah</name><age>10</age><sex>male</sex><telephone>12345678</telephone><friends><string>mattew</string><string>phoenix</string></friends></person>";
		// xml to java bean
		Person generatePerson = xmlMapper.readValue(x, Person.class);
		System.out.println("person:" + generatePerson.toString());
	}

	@Test
	public void testJavabeanToXml() throws IOException {
		// java bean to xml
		String xml = xmlMapper.writeValueAsString(person);
		System.out.println("xml:" + xml);
		// xml:<person
		// xmlns=""><name>sarah</name><age>10</age><sex>male</sex><telephone>12345678</telephone><friends><string>mattew</string><string>phoenix</string></friends></person>
		// String xml =
		// xmlMapper.writer().with(SerializationFeature.WRAP_ROOT_VALUE).withRootName("person").writeValueAsString(person);
		// System.out.println("xml:" + xml);
		
		Oa<String> oa = new Oa<>();
		oa.setName("1212");
//		oa.setT("Hello");
		System.out.println(xmlMapper.writeValueAsString(oa));
	}

	@Test
	public void xx() throws JsonParseException, JsonMappingException, IOException {
		xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
		String responseXml1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><HnaResponse><Success>1</Success><ErrorCode></ErrorCode><ErrorInfo></ErrorInfo><Result><ResortInfoEntity><ChainCode /><Brand>TLH</Brand><HotelCode>TLBJ</HotelCode><TypeCode>BUSINESS,5S</TypeCode><StarCode>商务,五星</StarCode><BusinessDate /><BeginDate>2006/12/1 0:00:00</BeginDate><Name>北京唐拉雅秀酒店</Name><NameEN>TANGLA Hotel Beijing</NameEN><LegalOwner /><CountryCode>CN</CountryCode><RegionCode>7373d578-5854-40f9-0001-8d6382d3e63a</RegionCode><State>PEK</State><City>PEK</City><ZipCode>100045</ZipCode><Address>北京市西长安街复兴门外大街19号</Address><AddressEN>No.19 Fuxingmenwai Street, West Chang’an Avenue, Beijing, P.R.China. 100045</AddressEN><Description>北京唐拉雅秀酒店位居北京西长安街上，地处首都政务金融核心区域，距金融街一步之遥，距天安门广场仅10分钟车程。酒店拥有300间豪华客房和套房，设有总面积逾2000平米的先进会议中心，拥有一个大宴会厅（最多容纳400人）、10间多功能厅和一个豪华董事会议厅；6个汇聚中西美食的餐厅；典雅温馨的SPA和健身中心，以及设于顶层的豪华商务会所。游泳池、健身中心、客房及公共区域Wi-Fi、购物商店等设施齐全。</Description><DescriptionEN>TANGLA Hotel Beijing is ideally located along West Chang’an Avenue, adjacent to the Central Government Ministries, Financial Street, shopping centers, and numerous historical sites. It is only a 10 minute drive to Tian’anmen Square, the heart of China.TANGLA Hotel Beijing offers 380 luxurious guest rooms and suites designed with a combination of Oriental elegance and Western individuality, grandeur, and luxury. Our signature Memory Foam mattresses in each guest room ensure each guest can enjoy a deep, refreshing sleep. TANGLA Hotel Beijing is equipped with an advanced Conference Center over 2,000 square meters in size. The hotel also has a Grand Ballroom, 10 multi-function rooms, and a magnifi cent Executive Boardroom. A swimming pool, fitness center, souvenir shop and Wi-Fi service are also provided.</DescriptionEN><AccountName /><BankName /><AccountID /><InvoiceTitle /><Phone>010-58676688</Phone><Tollfree /><Fax>010-58576677</Fax><Email>candy.li@tanglahotels.com</Email><WebPage>www.hnahotelsandresorts.com</WebPage><LocalCurrency>056e4461-80d7-4789-9a84-74cd0af8e496</LocalCurrency><CurrencyFormat>bd07a377-249a-4440-8fe0-fef214a3f0ee</CurrencyFormat><CurrencySymbol>￥</CurrencySymbol><NumberFloors>22</NumberFloors><NumberRooms>375</NumberRooms><NumberBeds>475</NumberBeds><DefaultCheckOutTime>12:00:00</DefaultCheckOutTime><DefaultCheckInTime>14:00:00</DefaultCheckInTime><EastLongitude>116.343181</EastLongitude><NorthLatitude>39.907915</NorthLatitude><PayTime>1</PayTime></ResortInfoEntity></Result></HnaResponse>";
		JavaType javaType = getCollectionType(HnaResponse.class, ArrayOfResortInfoEntity.class);
		HnaResponse<ArrayOfResortInfoEntity> hnaResponse = xmlMapper.readValue(responseXml1, javaType);
		System.out.println(hnaResponse);
	}

	@Test
	public void yy() {
		HnaResponse<ArrayOfResortInfoEntity> hnaResponse = new HnaResponse<>();
		hnaResponse.setSuccess(1);
		ArrayOfResortInfoEntity arrayOfResortInfoEntity = new ArrayOfResortInfoEntity();
		ResortInfoEntity resortInfoEntity = new ResortInfoEntity();
		resortInfoEntity.setName("Test");
		resortInfoEntity.setCity("BeiJing");
		arrayOfResortInfoEntity.setResortInfoEntity(resortInfoEntity);
		hnaResponse.setResult(arrayOfResortInfoEntity);
		try {
			System.out.println(xmlMapper.writeValueAsString(hnaResponse));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAToXML() {
		xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
		BaseVo<ResA> vo = new BaseVo<>();
		vo.setSuccess(1);
		ResA rsA = new ResA();
		rsA.setA(Arrays.asList(new A("11", "22"), new A("XX", "YY")));
		vo.setResult(rsA);
		try {
			System.out.println(xmlMapper.writeValueAsString(vo).replaceAll(" xmlns=\"\"", ""));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testXMLToA() {
		xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
		String xml = "<BaseResult xmlns=\"\"><Success>1</Success><ErrorCode/><Result><A><X>11</X><Y>22</Y></A><A><X>XX</X><Y>YY</Y></A></Result></BaseResult>";
		try {
			JavaType javaType = getCollectionType(BaseVo.class, ResA.class);
			BaseVo<ResA> vo = xmlMapper.readValue(xml, javaType);
			System.out.println(vo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHnaReqToXML() {
		xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
		HnaRequest<String> vo = new HnaRequest<>();
		vo.setRsaCode("1");
//		vo.setParamter(new Paramter<String>("CRS"));
		try {
			String xml = xmlMapper.writeValueAsString(vo).replaceAll(" xmlns=\"\"", "");
			System.out.println(xml);
			xml = SecurityUtils.HtmlEncode(xml);
			System.out.println(xml);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 泛型类转化，例子：Person<T>
	 * 参考：
	 * 	http://www.cnblogs.com/quanyongan/archive/2013/04/16/3024993.html
	 * 	http://huangyunbin.iteye.com/blog/2352243
	 * 
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return xmlMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
