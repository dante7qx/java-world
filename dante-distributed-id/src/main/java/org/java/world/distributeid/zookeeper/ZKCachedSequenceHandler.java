package org.java.world.distributeid.zookeeper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 根据开源项目mycat实现基于zookeeper的递增序列号
 *
 * 只要配置好ZK地址和表名的如下属性 
 * MINID 某线程当前区间内最小值 
 * MAXID 某线程当前区间内最大值 
 * CURID 某线程当前区间内当前值
 * 
 * 使用ZooKeeper作为分段节点协调工具。每台服务器首先从zookeeper缓存一段，如1-1000的id。
 * 此时zk上保存最大值1000，每次获取的时候都会进行判断，如果id小于本地最大值，即id<=1000，
 * 则更新本地的当前值，如果id大于本地当前值，比如说是1001，则会将从zk再获取下一个id数据段并在本地缓存。
 * 获取数据段的时候需要更新zk节点数据，更新的时候使用curator的分布式锁来实现。
 * 
 * 由于id是从本机获取，因此本方案的优点是性能非常好。缺点是如果多主机负载均衡，则会出现不连续的id，
 * 当然将递增区段设置为1也能保证连续的id，但是效率会受到很大影响。
 *
 * @author dante
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZKCachedSequenceHandler extends SequenceHandler {
	private static final String KEY_MIN_NAME = ".MINID";// 1
	private static final String KEY_MAX_NAME = ".MAXID";// 10000
	private static final String KEY_CUR_NAME = ".CURID";// 888
	private final static long PERIOD = 1000;// 每次缓存的ID段数量
	private static String ZK_ADDRESS = ""; // 127.0.0.1:2181
	private static String PATH = "";// /spirit
	private static String SEQ = "";// seq-;

	private Map<String, Map<String, String>> tableParaValMap = null;
	private CuratorFramework client;
	private InterProcessSemaphoreMutex interProcessSemaphore = null;

	private static ZKCachedSequenceHandler instance = new ZKCachedSequenceHandler();

	public static ZKCachedSequenceHandler getInstance(String zkAddress, String path, String seq) {
		ZK_ADDRESS = zkAddress;
		PATH = path;
		SEQ = seq;
		return instance;
	}

	public void loadZK() {
		try {
			this.client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
			this.client.start();
		} catch (Exception e) {
			log.error("Error caught while initializing ZK:" + e.getCause());
		}
	}

	public Map<String, String> getParaValMap(String prefixName) {
		if (tableParaValMap == null) {
			try {
				loadZK();
				fetchNextPeriod(prefixName);
			} catch (Exception e) {
				log.error("Error caught while loding configuration within current thread:" + e.getCause());
			}
		}
		Map<String, String> paraValMap = tableParaValMap.get(prefixName);
		return paraValMap;
	}

	public Boolean fetchNextPeriod(String prefixName) {
		try {
			Stat stat = this.client.checkExists().forPath(PATH + "/" + prefixName + SEQ);

			if (stat == null || (stat.getDataLength() == 0)) {
				try {
					client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
							.forPath(PATH + "/" + prefixName + SEQ, String.valueOf(0).getBytes());
				} catch (Exception e) {
					log.debug("Node exists! Maybe other instance is initializing!");
				}
			}
			if (interProcessSemaphore == null) {
				interProcessSemaphore = new InterProcessSemaphoreMutex(client, PATH + "/" + prefixName + SEQ);
			}
			interProcessSemaphore.acquire();
			if (tableParaValMap == null) {
				tableParaValMap = new ConcurrentHashMap<>();
			}
			Map<String, String> paraValMap = tableParaValMap.get(prefixName);
			if (paraValMap == null) {
				paraValMap = new ConcurrentHashMap<>();
				tableParaValMap.put(prefixName, paraValMap);
			}
			long now = Long.parseLong(new String(client.getData().forPath(PATH + "/" + prefixName + SEQ)));
			client.setData().forPath(PATH + "/" + prefixName + SEQ, ((now + PERIOD) + "").getBytes());
			if (now == 1) {
				paraValMap.put(prefixName + KEY_MAX_NAME, PERIOD + "");
				paraValMap.put(prefixName + KEY_MIN_NAME, "1");
				paraValMap.put(prefixName + KEY_CUR_NAME, "0");
			} else {
				paraValMap.put(prefixName + KEY_MAX_NAME, (now + PERIOD) + "");
				paraValMap.put(prefixName + KEY_MIN_NAME, (now) + "");
				paraValMap.put(prefixName + KEY_CUR_NAME, (now) + "");
			}
		} catch (Exception e) {
			log.error("Error caught while updating period from ZK:" + e.getCause());
		} finally {
			try {
				interProcessSemaphore.release();
			} catch (Exception e) {
				log.error("Error caught while realeasing distributed lock" + e.getCause());
			}
		}
		return true;
	}

	public Boolean updateCURIDVal(String prefixName, Long val) {
		Map<String, String> paraValMap = tableParaValMap.get(prefixName);
		if (paraValMap == null) {
			throw new IllegalStateException("ZKCachedSequenceHandler should be loaded first!");
		}
		paraValMap.put(prefixName + KEY_CUR_NAME, val + "");
		return true;
	}

	/**
	 * 获取自增ID
	 *
	 * @param sequenceEnum
	 * @return
	 */
	@Override
	public long nextId(SequenceEnum sequenceEnum) {
		String prefixName = sequenceEnum.getCode();
		Map<String, String> paraMap = this.getParaValMap(prefixName);
		if (null == paraMap) {
			throw new RuntimeException("fetch Param Values error.");
		}
		Long nextId = Long.parseLong(paraMap.get(prefixName + KEY_CUR_NAME)) + 1;
		Long maxId = Long.parseLong(paraMap.get(prefixName + KEY_MAX_NAME));
		if (nextId > maxId) {
			fetchNextPeriod(prefixName);
			return nextId(sequenceEnum);
		}
		updateCURIDVal(prefixName, nextId);
		return nextId.longValue();
	}

}
