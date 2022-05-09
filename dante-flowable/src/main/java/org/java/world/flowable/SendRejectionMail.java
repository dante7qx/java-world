package org.java.world.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 服务任务(service task) 的处理类
 * 
 * @author dante
 *
 */
public class SendRejectionMail implements JavaDelegate {

	/**
	 * 审核不通过，系统自动给员工发邮件
	 */
	@Override
	public void execute(DelegateExecution execution) {
		
		System.out.println("【" + execution.getEventName() + "】" + "发邮件给员工" + execution.getVariable("employee") + "：" + execution.getVariable("approveDescription"));
	}

}
