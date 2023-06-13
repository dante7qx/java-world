package com.dante.designmodel.chain.dao;

import java.util.HashMap;
import java.util.Map;

import com.dante.designmodel.chain.consts.GatewayEnum;
import com.dante.designmodel.chain.entity.GatewayEntity;

public class GatewayImpl implements GatewayDao {

	/**  
     * 初始化，将枚举中配置的handler初始化到map中，方便获取  
     */  
    private static Map<Integer, GatewayEntity> gatewayEntityMap = new HashMap<>();  

    static {  
        GatewayEnum[] values = GatewayEnum.values();  
        for (GatewayEnum value : values) {  
            GatewayEntity gatewayEntity = value.getGatewayEntity();  
            gatewayEntityMap.put(gatewayEntity.getHandlerId(), gatewayEntity);  
        }  
    }  

	
	@Override
	public GatewayEntity getGatewayEntity(Integer handlerId) {
		return gatewayEntityMap.get(handlerId);
	}

	@Override
	public GatewayEntity getFirstGatewayEntity() {
		for (Map.Entry<Integer, GatewayEntity> entry : gatewayEntityMap.entrySet()) {  
            GatewayEntity value = entry.getValue();  
            //  没有上一个handler的就是第一个  
            if (value.getPreHandlerId() == null) {  
                return value;  
            }  
        }  
        return null;  
	}

}
