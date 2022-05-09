package org.java.world.quartz.listener;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IJobListener implements JobListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(IJobListener.class);
	
	@Override
	public String getName() {
		return "IJobListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
//		LOGGER.info("=====> jobToBeExecuted...");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		LOGGER.info("=====> jobExecutionVetoed...");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		LOGGER.info("=====> jobWasExecuted...");
		Date previousFireTime = context.getPreviousFireTime();
		Date nextFireTime = context.getNextFireTime();
		Date fireTime = context.getFireTime();
		LOGGER.info("previousFireTime {}\n", previousFireTime);
		LOGGER.info("fireTime {}\n", fireTime);
		LOGGER.info("nextFireTime {}\n", nextFireTime);
		LOGGER.info("<===== jobWasExecuted...");
		if(jobException != null) {
			LOGGER.error("*****************************\n {} \n**************************************", getStackMsg(jobException.getStackTrace()), jobException);
		}
	}
	
	private static String getStackMsg(StackTraceElement[] stackArray) { 
		StringBuffer sb = new StringBuffer();    
        for (int i = 0; i < stackArray.length; i++) {    
            StackTraceElement element = stackArray[i];    
            sb.append(element.toString() + "\n");    
        }    
        return sb.toString();    
    }   

}
