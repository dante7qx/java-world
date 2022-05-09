package org.java.world.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date prevTime = context.getPreviousFireTime();
		Date fireTime = context.getFireTime();
		Date nextTime = context.getNextFireTime();
		String fireInstanceId = context.getFireInstanceId();
		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
		mergedJobDataMap.forEach((k, v) -> {
			LOGGER.info("{} -> {}", k, v);
		});
		LOGGER.info("你好 Quartz Job，fireInstanceId {}, prevTime {}, fireTime {}, nextTime {}", fireInstanceId, prevTime, fireTime, nextTime);
	}

}
