package org.java.world.distributeid;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.java.world.distributeid.zookeeper.SequenceEnum;
import org.java.world.distributeid.zookeeper.ZKIncreaseSequenceHandler;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZKIncreaseSequenceHandlerTest {
	
	private static final String ZK_ADDR = "127.0.0.1:2181";
	private static final String PATH = "/spirit";

	@Test
	public void nextIncreaseSequence() {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
        long startTime = System.currentTimeMillis();   //获取开始时间
        final ZKIncreaseSequenceHandler sequenceHandler = ZKIncreaseSequenceHandler.getInstance(ZK_ADDR, PATH, "seq-");
        int count = 100;
        final CountDownLatch cd = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    log.info("线程 {} ============> {}", Thread.currentThread().getId(), sequenceHandler.nextId(SequenceEnum.ORDER));
                    cd.countDown();
                }
            });
        }
        try {
            cd.await();
        } catch (InterruptedException e) {
            log.error("Interrupted thread",e);
            Thread.currentThread().interrupt();
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        log.info("程序运行时间： " + (endTime - startTime) + "ms");
	}
	
}
