package org.java.world.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.java.world.quartz.job.CronJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzClusterTest {

	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			JobDetail cronJob = newJob(CronJob.class)
					.withIdentity("jobCron", "groupCron")
					.build();
			CronTrigger cronTrigger = newTrigger()
				.withIdentity("cronTrigger", "cronTrigger")
				.withSchedule(cronSchedule("0/30 * * * * ?"))
				.forJob(cronJob)
				.build();
			if(!scheduler.checkExists(cronTrigger.getKey())) {
				scheduler.scheduleJob(cronJob, cronTrigger);
			} 
			// 调度器开始运行
			scheduler.start();
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
	
}
