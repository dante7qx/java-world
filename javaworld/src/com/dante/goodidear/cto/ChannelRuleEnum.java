package com.dante.goodidear.cto;

public enum ChannelRuleEnum {
	/**
     * 头条
     */
    TOUTIAO("TOUTIAO",new TouTiaoChannelRule()),
    /**
     * 腾讯
     */
    TENCENT("TENCENT",new TencentChannelRule()),
    ;
	
	public String name;
	
	public GeneralChannelRule channel;

    ChannelRuleEnum(String name, GeneralChannelRule channel) {
        this.name = name;
        this.channel = channel;
    }
    
    public String getName() {
        return name;
    }

    public GeneralChannelRule getChannel() {
        return channel;
    }
    
    /**
     * 匹配
     * @throws IllegalAccessException 
     */
    public static ChannelRuleEnum match(String name) throws IllegalAccessException{
        ChannelRuleEnum[] values = ChannelRuleEnum.values();
        for (ChannelRuleEnum value : values) {
            if(value.name.equals(name)){
                return value;
            }
        }
        throw new IllegalAccessException("渠道 [" + name + "]尚未开通！");
    }
}
