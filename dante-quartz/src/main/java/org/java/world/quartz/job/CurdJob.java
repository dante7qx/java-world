package org.java.world.quartz.job;

import java.time.Instant;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurdJob implements Job {

	private static final Logger LOGGER = LoggerFactory.getLogger(CronJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String schedulerName = null;
		try {
			schedulerName = context.getScheduler().getSchedulerName();
			LOGGER.info("{} 触发任务 {} execute at {}", schedulerName, context.getJobDetail().getKey().getName(), Date.from(Instant.now()));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		throw new JobExecutionException("111111111");
	}


}
