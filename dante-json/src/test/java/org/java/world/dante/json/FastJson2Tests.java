package org.java.world.dante.json;

import java.util.Arrays;
import java.util.List;

import org.java.world.dante.json.vo.BaseResp;
import org.java.world.dante.json.vo.Country;
import org.java.world.dante.json.vo.Province;
import org.java.world.dante.json.vo.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Stopwatch;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

/**
 * 测试 FastJson
 * 
 * 参考：http://blog.csdn.net/ykdsg/article/details/50432494
 * 
 * 
 * @author dante
 *
 */
public class FastJson2Tests {

	protected BaseResp<List<Country>> baseResp;
	private final int count = 100000;

	@Before
	public void init() {
		baseResp = new BaseResp<>();
		baseResp.setSuccess(1);
		Result<List<Country>> t = new Result<>();
		List<Country> countrys = Arrays.asList(
				new Country("CN", "中国", Arrays.asList(new Province("北京"), new Province("天津"), new Province("成都"))),
				new Country("US", "美国", Arrays.asList(new Province("纽约"), new Province("洛杉矶"))));
		t.setT(countrys);
		baseResp.setResult(t);
	}

	@Test
	public void testBeanToJson() {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			JSON.toJSON(baseResp);
		}
		System.out.println(JSON.toJSON(baseResp));
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
	}

	@Test
	public void testJsonToBean() {
		String json = "{\"success\":1,\"result\":{\"t\":[{\"code\":\"CN\",\"name\":\"中国\",\"provinces\":[{\"name\":\"北京\"},{\"name\":\"天津\"},{\"name\":\"成都\"}]},{\"code\":\"US\",\"name\":\"美国\",\"provinces\":[{\"name\":\"纽约\"},{\"name\":\"洛杉矶\"}]}]}}";
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			JSON.parseObject(json, new TypeReference<BaseResp<List<Country>>>() {}.getType());
		}
		BaseResp<List<Country>> parseObject = JSON.parseObject(json, new TypeReference<BaseResp<List<Country>>>() {}.getType());
		System.out.println(parseObject);
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
	}

}