package org.java.world.dante.demo;

import org.junit.jupiter.api.Test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import cn.hutool.core.lang.Console;


public class BloomFilterTest {

	@Test
	public void demo() {
		// 创建布隆过滤器，预计插入10000个元素，误判率为0.01
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.unencodedCharsFunnel(), 10000, 0.01);

        // 添加元素到布隆过滤器
        bloomFilter.put("apple");
        bloomFilter.put("banana");
        bloomFilter.put("orange");
        
//        String searchTxt = "watermelon";
        String searchTxt = "orange";
        if(!bloomFilter.mightContain(searchTxt)) {
        	Console.log("{} 不存在", searchTxt);
        } else {
        	
        	Console.log("{} 可能存在，从数据库中进行查询", searchTxt);
        }
	}
	
}
