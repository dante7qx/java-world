package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTaskListener implements TaskListener {

	private static final long serialVersionUID = 4263339403696082954L;
	
	private static final Logger logger = LoggerFactory.getLogger(MyTaskListener.class);
	
	private FixedValue email;

	@Override
	public void notify(DelegateTask delegateTask) {
		logger.info("UserTask {} 监听器开始运行，被监听的任务 {}。", email.getExpressionText(), delegateTask.getName());
	}

	public void setEmail(FixedValue email) {
		this.email = email;
	}	
	
}
