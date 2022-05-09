package org.java.world.dante.activiti;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程控制 API 测试
 * 
 * @author dante
 *
 */
public class HandleProcessTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(HandleProcessTest.class);
	
	/**
	 * Receive Task 测试，适用 trigger 触发执行
	 * 
	 */
	@Test
	public void testReceiveProcess() {
		repositoryService.createDeployment().addClasspathResource("diagrams/receive.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("receiveProcess");
		Execution firstExecu = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).onlyChildExecutions().singleResult();
		logger.info("当前执行流 {} - {}.", pi.getId(), firstExecu.getActivityId());
		
		// 流程前进
		runtimeService.trigger(firstExecu.getId());
		
		Execution nextExecu = runtimeService.createExecutionQuery().activityId("taskA").singleResult();
		logger.info("当前执行流 {} - {}.", pi.getId(), nextExecu.getActivityId());
	}
	
	/**
	 * 流程中断，Catching到信号后继续向下执行
	 * 
	 */
	@Test
	public void testSignalCatchingProcess() {
		repositoryService.createDeployment().addClasspathResource("diagrams/signalCatching.bpmn").deploy();
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("signalCatchingProcess");
		
		Execution execu = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).onlyChildExecutions().singleResult();
		logger.info("当前执行流 {} - {}.", pi.getId(), execu.getActivityId());
		
		// 流程前进, 参数是 signalName
		logger.info("抛出信号 signalTest ...");
		runtimeService.signalEventReceived("signalTest");
		
		execu = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).onlyChildExecutions().singleResult();
		logger.info("当前执行流 {} - {}.", pi.getId(), execu.getActivityId());
	}
	
	/**
	 * 流程中断，Catching到消息后继续向下执行
	 * 
	 */
	@Test
	public void testMessageCatchingProcess() {
		repositoryService.createDeployment().addClasspathResource("diagrams/messageCatching.bpmn").deploy();
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("messageCatchingProcess");
		
		Execution execu = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).onlyChildExecutions().singleResult();
		logger.info("当前执行流 {} - {}.", pi.getId(), execu.getActivityId());
		
		// 流程前进, 参数是 message name
		logger.info("抛出消息 messageTest ...");
		runtimeService.messageEventReceived("messageTest", execu.getId());
		
		execu = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).onlyChildExecutions().singleResult();
		logger.info("当前执行流 {} - {}.", pi.getId(), execu.getActivityId());
	}
	
}
