package org.java.world.dante.json;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.java.world.dante.json.vo.BaseResp;
import org.java.world.dante.json.vo.Country;
import org.java.world.dante.json.vo.Province;
import org.java.world.dante.json.vo.Result;
import org.junit.Before;
import org.junit.Test;

/**
 * 配置信息：http://blog.csdn.net/kobejayandy/article/details/45869861
 * 美化输出：http://www.cnblogs.com/xiwang/p/5844199.html
 * 
 * @author dante
 *
 */
public class JacksonTests {

	private ObjectMapper objectMapper = new ObjectMapper();
	private XmlMapper xmlMapper = new XmlMapper();
	protected BaseResp<List<Country>> baseResp;

	@Before
	public void init() {
		baseResp = new BaseResp<>();
		baseResp.setSuccess(1);
		baseResp.setErrorCode("");
		Result<List<Country>> t = new Result<>();
		List<Country> countrys = Arrays.asList(
				new Country("CN", "中国", Arrays.asList(new Province("北京"), new Province("天津"), new Province("成都"))),
				new Country("US", "美国", Arrays.asList(new Province("纽约"), new Province("洛杉矶"))));
		t.setT(countrys);
		baseResp.setResult(t);
	}

	@Test
	public void testBeanToJson() {
		try {
			long startTime = System.currentTimeMillis();
			System.out.println(objectMapper.writeValueAsString(baseResp));
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testJsonToBean() {
		try {
			String json = "{\"success\":1,\"result\":{\"t\":[{\"code\":\"CN\",\"name\":\"中国\",\"provinces\":[{\"name\":\"北京\"},{\"name\":\"天津\"},{\"name\":\"成都\"}]},{\"code\":\"US\",\"name\":\"美国\",\"provinces\":[{\"name\":\"纽约\"},{\"name\":\"洛杉矶\"}]}]}}";
			TypeReference<BaseResp<List<Country>>> typeReference = new TypeReference<BaseResp<List<Country>>>() {};
			long startTime = System.currentTimeMillis();
			BaseResp<List<Country>> resp = objectMapper.readValue(json, typeReference);
			System.out.println(resp);
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 美化输出
	 * 
	 * 1. ObjectWriter objectWriter = xmlMapper.writerWithDefaultPrettyPrinter();
	 * 2. xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
	 * 
	 */
	@Test
	public void testBeanToXml() {
		try {
			long startTime = System.currentTimeMillis();
//			ObjectWriter objectWriter = xmlMapper.writerWithDefaultPrettyPrinter();
//			System.out.println(xmlMapper.writeValueAsString(baseResp));
//			System.out.println(objectWriter.writeValueAsString(baseResp));
//			xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
			System.out.println(xmlMapper.writeValueAsString(baseResp));
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testXmlToBean() {
		try {
			String xml = "<BaseResp><success>1</success><errorCode/><result><t><code>CN</code><name>中国</name><provinces><province><name>北京</name></province><province><name>天津</name></province><province><name>成都</name></province></provinces></t><t><code>US</code><name>美国</name><provinces><province><name>纽约</name></province><province><name>洛杉矶</name></province></provinces></t></result></BaseResp>";
			TypeReference<BaseResp<List<Country>>> typeReference = new TypeReference<BaseResp<List<Country>>>() {};
			long startTime = System.currentTimeMillis();
			BaseResp<List<Country>> resp = xmlMapper.readValue(xml, typeReference);
			System.out.println(resp);
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
