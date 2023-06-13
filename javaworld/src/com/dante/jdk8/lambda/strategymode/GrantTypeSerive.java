package com.dante.jdk8.lambda.strategymode;

/**
 * 优惠券的派发方式和领取规则
 * 
 * 
 * @author dante
 *
 */
public class GrantTypeSerive {
	
	public static String redPaper(String resourceId){
        // 红包的发放方式
        return "每周末9点发放";
    }
    public static String shopping(String resourceId){
        // 购物券的发放方式
        return "每周三9点发放";
    }
    public static String QQVip(String resourceId){
        // qq会员的发放方式
        return "每周一0点开始秒杀";
    }
	
}
