package com.dante.jdk8.stream;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cn.hutool.core.util.IdUtil;

public class OrderSeq {
	private static final AtomicInteger SEQ = new AtomicInteger(1000);
    private static final DateTimeFormatter DF_FMT_PREFIX = DateTimeFormatter.ofPattern("yyMMddHHmmssSS");
    private static ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    
    public static String generateOrderNo(){
        LocalDateTime dataTime = LocalDateTime.now(ZONE_ID);
        if(SEQ.intValue()>9990){
            SEQ.getAndSet(1000);
        }
        return dataTime.format(DF_FMT_PREFIX)+SEQ.getAndIncrement();
    }
    
    public static String generateUidOrderNo(){
//    	return IdUtil.fastSimpleUUID();
    	return IdUtil.nanoId();
    }
    
    public static void main(String[] args) {
    	System.out.println(IdUtil.fastSimpleUUID().length());
    	System.out.println(IdUtil.nanoId().length());
    	List<String> orderNos = Collections.synchronizedList(new ArrayList<String>());
    	// 并发生成订单号
    	IntStream.range(0, 1000000).parallel().forEach(i -> {
    		/*
    		try {
    			TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	    System.out.println(Thread.currentThread().getName());
    	    System.out.println("线程数量：" + Thread.activeCount());
    	    */
//    		orderNos.add(generateOrderNo());
    		orderNos.add(generateUidOrderNo());
    	});
    	
    	 List<String> filterOrderNos = orderNos.stream().distinct().collect(Collectors.toList());

         System.out.println("生成订单数："+orderNos.size());
         System.out.println("过滤重复后订单数："+filterOrderNos.size());
         System.out.println("重复订单数："+(orderNos.size()-filterOrderNos.size()));
         
	}
}
