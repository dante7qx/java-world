package org.java.world.dante.json;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.java.world.dante.json.vo.BaseResp;
import org.java.world.dante.json.vo.Country;
import org.java.world.dante.json.vo.Province;
import org.java.world.dante.json.vo.Result;
import org.java.world.dante.json.vo.UserVO;
import org.junit.Before;
import org.junit.Test;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;

public class HutoolJSONTests {

	protected BaseResp<List<Country>> baseResp;

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
		System.out.println(JSONUtil.toJsonStr(baseResp));
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
	}

	@Test
	public void testJsonToBean() {
		String json = "{\"success\":1,\"result\":{\"t\":[{\"code\":\"CN\",\"name\":\"中国\",\"provinces\":[{\"name\":\"北京\"},{\"name\":\"天津\"},{\"name\":\"成都\"}]},{\"code\":\"US\",\"name\":\"美国\",\"provinces\":[{\"name\":\"纽约\"},{\"name\":\"洛杉矶\"}]}]}}";
		long startTime = System.currentTimeMillis();
		
		BaseResp<List<Country>> parseObject = JSONUtil.toBean(json, new TypeReference<BaseResp<List<Country>>>() {}.getType(), false);
		System.out.println(parseObject);
		
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
	}
	
	@Test
	public void testToList() {
		UserVO user1 = new UserVO("男");
		user1.setId(1);
		user1.setName("张三1");
		user1.setBirthday(Date.from(Instant.now()));
		
		UserVO user2 = new UserVO("男");
		user2.setId(2);
		user2.setName("张三2");
		user2.setBirthday(Date.from(Instant.now()));
		
		List<UserVO> users = Arrays.asList(user1, user2);
		
		JSONConfig jsonConfig = new JSONConfig();
		jsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String json = JSONUtil.toJsonStr(users, jsonConfig);
		System.out.println(json);
		
		JSONArray parseArray = JSONUtil.parseArray(json, jsonConfig);
		
		System.out.println(parseArray);
//		List<UserVO> list = JSONUtil.toList(parseArray, UserVO.class);
//		System.out.println(list);
	}
	

}
