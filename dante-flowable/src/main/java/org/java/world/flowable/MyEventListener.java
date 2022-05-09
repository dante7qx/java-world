package org.java.world.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

public class MyEventListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) {
		execution.setVariable("FINISHED", Boolean.TRUE);
		System.out.println("=====================================" + execution.getProcessInstanceId());
		System.out.println(execution.getId() + "___" + execution.getEventName() + "___" + execution.isEnded());
		System.out.println(execution.getCurrentFlowElement().getId()  + "___" + execution.getCurrentFlowElement().getName());
		System.out.println(execution.getVariable("approved"));
		System.out.println("=====================================" + execution.getVariable("nrOfHolidays"));
	}

	

}
