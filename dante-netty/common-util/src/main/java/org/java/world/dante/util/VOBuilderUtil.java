package org.java.world.dante.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.java.world.dante.vo.ArticleVO;
import org.java.world.dante.vo.MsgPackVO;
import org.java.world.dante.vo.UserVO;

public class VOBuilderUtil {

	public static UserVO buildUserVO() {
		UserVO user = new UserVO();
		user.setUid("1001");
		user.setName("但丁");
		user.setAge(18);
		user.setSalary(38.3f);
		List<ArticleVO> articles = new ArrayList<>();
		articles.add(new ArticleVO("道德经", "老子"));
		articles.add(new ArticleVO("齐物论", "庄子"));
		user.setArticles(articles);
		return user;
	}
	
	public static List<UserVO> buildUserVOs() {
		List<UserVO> users = new ArrayList<>();
		UserVO user = new UserVO();
		user.setUid("1001");
		user.setName("但丁");
		user.setAge(18);
		user.setSalary(38.3f);
		List<ArticleVO> articles = new ArrayList<>();
		articles.add(new ArticleVO("道德经", "老子"));
		articles.add(new ArticleVO("齐物论", "庄子"));
		user.setArticles(articles);
		users.add(user);
		return users;
	}
	
	public static MsgPackVO buildMsgPackVO() {
		MsgPackVO msg = new MsgPackVO();
		msg.setUid("M-1001");
		msg.setMsg("MsgPack 信息！");
		msg.setCode(1000001);
		msg.setSendDate(Date.from(Instant.now()));
		return msg;
	}

}
