package com.dante.jdk8.stream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.dante.jdk8.stream.vo.UserVO;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;

public class UserWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UserVO userVO;
	
	public UserWrapper(UserVO userVO) {
		this.userVO = userVO;
	}
	
	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	@Override
	public String toString() {
		return "UserWrapper [" + userVO + "]";
	}

	public static void main(String[] args) {
		List<UserWrapper> wrappers = new ArrayList<>();
		IntStream.range(1, 10).forEachOrdered(i -> {
			String objectId = IdUtil.objectId();
			if (i % 3 != 0) {
				objectId = DateUtil.now();
			} 
			wrappers.add(new UserWrapper(new UserVO(i, "姓名".concat(i + ""), 20 + i, objectId)));

		});
		System.out.println("去重前：" + wrappers);
		List<UserWrapper> newList = wrappers.stream().filter(distinctByKey(UserWrapper::getUserVO)).collect(Collectors.toList());
		System.out.println("去重后：" + newList);
	}
	
    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> keyExtractor.apply(t)!=null && seen.add(keyExtractor.apply(t));
	}

}
