package com.dante.goodidear.cto;

public class Test {

	public static void main(String[] args) {
		String sign = "TENCENT";
		ChannelRuleEnum channelRule;
		try {
			channelRule = ChannelRuleEnum.match(sign);
			GeneralChannelRule rule = channelRule.channel;
			rule.process();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

}
