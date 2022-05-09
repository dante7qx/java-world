package org.java.world.quartz.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISchedulerListener implements SchedulerListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ISchedulerListener.class);
	
	@Override
	public void jobScheduled(Trigger trigger) {
		LOGGER.info("jobScheduled: {} -------> {}.", trigger.getKey().getName(), trigger.getJobKey().getName());
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		LOGGER.info("jobUnscheduled: --------> {}.", triggerKey.getName());
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		LOGGER.info("triggerFinalized: --------> {}.", trigger.getKey().getName());
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		LOGGER.info("triggerPaused: --------> {}.", triggerKey.getName());
	}

	@Override
	public void triggersPaused(String triggerGroup) {
		LOGGER.info("Group triggersPaused: --------> {}.", triggerGroup);
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		LOGGER.info("triggerResumed: --------> {}.", triggerKey.getName());
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		LOGGER.info("Group triggersResumed: --------> {}.", triggerGroup);
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		LOGGER.info("jobAdded: --------> {}.", jobDetail.getKey().getName());
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		LOGGER.info("jobDeleted: --------> {}.", jobKey.getName());
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		LOGGER.info("jobPaused: --------> {}.", jobKey.getName());
	}

	@Override
	public void jobsPaused(String jobGroup) {
		LOGGER.info("Group jobsPaused: --------> {}.", jobGroup);
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		LOGGER.info("jobResumed: --------> {}.", jobKey.getName());
	}

	@Override
	public void jobsResumed(String jobGroup) {
		LOGGER.info("Group jobsResumed: --------> {}.", jobGroup);
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		LOGGER.error("schedulerError: --------> {}.", msg, cause);
	}

	@Override
	public void schedulerInStandbyMode() {
		LOGGER.info("schedulerInStandbyMode --------->");
	}

	@Override
	public void schedulerStarted() {
		LOGGER.info("schedulerStarted --------->");
	}

	@Override
	public void schedulerStarting() {
		LOGGER.info("schedulerStarting --------->");
	}

	@Override
	public void schedulerShutdown() {
		LOGGER.info("schedulerShutdown --------->");
	}

	@Override
	public void schedulerShuttingdown() {
		LOGGER.info("schedulerShuttingdown --------->");
	}

	@Override
	public void schedulingDataCleared() {
		LOGGER.info("schedulingDataCleared --------->");
	}

}
