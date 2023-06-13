package com.dante.jdk19;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Feature19 {

	public static void main(String[] args) throws Throwable {
		// 并行执行一万个 sleep 一秒的任务，对比总的执行时间和所用系统线程数量

		// 平台线程 （耗时：50171 ms 7 os thread）
//		systemThreadRun();

		// 虚拟线程（耗时：1233ms 24 os thread）
//		virtualThreadRun();
		
		// 引入外部函数和内存 API 
		externalInvoke();
	}

	/**
	 * 平台线程
	 * 
	 * https://www.liaoxuefeng.com/wiki/1252599548343744/1501390373388322
	 * 
	 * 1. 线程是由操作系统创建并调度的资源； 2. 线程切换会耗费大量CPU时间； 3. 一个系统能同时调度的线程数量是有限的，通常在几百至几千级别。
	 */
	private static void systemThreadRun() {
		// 记录系统线程数
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
			ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
			System.out.println(threadInfo.length + " os thread");
		}, 1, 1, TimeUnit.SECONDS);

		long l = System.currentTimeMillis();
		try (var executor = Executors.newFixedThreadPool(200)) {
			IntStream.range(0, 10000)
				.forEach(i -> {
					executor.submit(() -> {
						Thread.sleep(Duration.ofSeconds(1));
						System.out.println(i);
						return i;
					});
				});
		}
		System.out.printf("耗时：%d ms", System.currentTimeMillis() - l);
	}

	/**
	 * 虚拟线程
	 * 
	 * https://heapdump.cn/article/4592243
	 * 
	 * 1. 程序并发任务数量很高 2. IO密集型、工作负载不受 CPU 约束
	 */
	@SuppressWarnings("preview")
	private static void virtualThreadRun() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
			ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
			System.out.println(threadInfo.length + " os thread");
		}, 10, 10, TimeUnit.MILLISECONDS);

		long l = System.currentTimeMillis();
		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			IntStream.range(0, 10000)
				.forEach(i -> {
					executor.submit(() -> {
						Thread.sleep(Duration.ofSeconds(1));
						System.out.println(i);
						return i;
					});
				});
		}

		System.out.printf("耗时：%dms\n", System.currentTimeMillis() - l);
	}

	/**
	 * 引入外部函数和内存 API 
	 * 
	 * @throws Throwable
	 */
	private static void externalInvoke() throws Throwable {
		// 1. 在C库路径上查找外部函数
		SymbolLookup stdlib = Linker.nativeLinker().defaultLookup();

		// 2. 获取 C 标准库中“strlen”函数的句柄
		MethodHandle strlen = Linker.nativeLinker()
			.downcallHandle(stdlib.lookup("strlen")
				.orElseThrow(), FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));

		// 3. 将 Java 字符串转换为 C 字符串并存储在堆外内存中
		MemorySegment str = SegmentAllocator.implicitAllocator().allocateUtf8String("hello Java19!");

		// 4. 调用外部函数
		long len = (long) strlen.invoke(str);

		System.out.println("len = " + len);
	}

}
