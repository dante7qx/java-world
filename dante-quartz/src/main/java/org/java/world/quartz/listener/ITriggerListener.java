package org.java.world.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITriggerListener implements TriggerListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ITriggerListener.class);
	
	@Override
	public String getName() {
		return "ITriggerListener";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		LOGGER.info("{} --------> triggerFired", context.getFireInstanceId());
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		LOGGER.info("{} --------> triggerMisfired", trigger.getKey().getName());
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		LOGGER.info("{} --------> triggerComplete", context.getFireInstanceId());
	}

}
