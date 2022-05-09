package org.java.world.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

import org.java.world.quartz.job.CronJob;
import org.java.world.quartz.job.HelloJob;
import org.java.world.quartz.listener.ISchedulerListener;
import org.java.world.quartz.listener.ITriggerListener;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

public class QuartzTest {

	public static void main(String[] args) {
		try {
			// 从 SchedulerFactory 中抢占一个 Scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// 构造一个 HelloJob 实例
			JobDetail job = newJob(HelloJob.class)
					.withIdentity("job1", "group1")
					.usingJobData("JobDataMap", "Job运行时进行存储")
					.build();
			JobDetail cronJob = newJob(CronJob.class)
					.withIdentity("jobCron", "groupCron")
					.build();
			
			// 构造一个Trigger，触发 HelloJob，指定每 40s 执行一次
			Trigger trigger = newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(simpleSchedule()
					.withIntervalInSeconds(40)
					.repeatForever())
					.forJob(job)
					.build();
			
			CronTrigger cronTrigger = newTrigger()
				.withIdentity("cronTrigger", "cronTrigger")
				.withSchedule(cronSchedule("0/10 * * * * ?"))
				.forJob(cronJob)
				.build();
			
			// 注册到 Quartz，指定根据 trigger 来调度 job
			if(!scheduler.checkExists(trigger.getKey())) {
				scheduler.scheduleJob(job, trigger);
			}
			if(!scheduler.checkExists(cronTrigger.getKey())) {
				scheduler.scheduleJob(cronJob, cronTrigger);
			}
			
			// 添加监听器
			scheduler.getListenerManager().addTriggerListener(new ITriggerListener(), KeyMatcher.keyEquals(triggerKey("cronTrigger", "cronTrigger")));
			scheduler.getListenerManager().addSchedulerListener(new ISchedulerListener());
			// 调度器开始运行
			scheduler.start();

			// scheduler.shutdown();
			
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
