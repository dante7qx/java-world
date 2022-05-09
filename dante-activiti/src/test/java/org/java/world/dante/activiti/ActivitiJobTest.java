package org.java.world.dante.activiti;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Activiti 工作产生、管理
 * 
 *  ACT_RU_JOB
 *  定时工作表：ACT_RU_TIMER_JOB
 *  无法执行的任务：ACT_RU_DEADLETTER_JOB
 *  暂停工作表：ACT_RU_SUSPENDED_JOB
 * 
 * @author dante
 *
 */
public class ActivitiJobTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(ActivitiJobTest.class);
	
	/**
	 * 异步任务
	 */
	@Test
	public void testServiceTask() {
		repositoryService.createDeployment().addClasspathResource("diagrams/deadletterJob.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("serviceTaskProcess");
		long count = managementService.createJobQuery().count();
		logger.info("流程 {}, 工作数量 {}。", pi.getProcessInstanceId(), count);
	}
	
	/**
	 * 定时事件
	 */
	@Test
	public void testTimerJob() {
		repositoryService.createDeployment().addClasspathResource("diagrams/timerJobProcess.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("timerProcess");
		long count = managementService.createJobQuery().count();
		logger.info("流程 {}, 工作数量 {}。", pi.getProcessInstanceId(), count);
	}
	
	/**
	 * 暂停工作事件
	 */
	@Test
	public void testSuspendedJob() {
		repositoryService.createDeployment().addClasspathResource("diagrams/timerJobProcess.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("timerProcess");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		// 暂停流程实例，job从 ACT_RU_TIMER_JOB 转移到 ACT_RU_SUSPENDED_JOB
		runtimeService.suspendProcessInstanceById(pi.getId());
		// 重新启动流程，暂停的工作会重新执行
//		runtimeService.startProcessInstanceByKey("timerProcess");
	}
	
	/**
	 * 失败任务，Job会存入ACT_RU_DEADLETTER_JOB
	 * 
	 * @throws InterruptedException 
	 */
	@Test
	public void testExceptionTask() throws InterruptedException {
		repositoryService.createDeployment().addClasspathResource("diagrams/exceptionJob.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("exceptionProcess");
		long count = managementService.createJobQuery().count();
		logger.info("流程 {}, 工作数量 {}。", pi.getProcessInstanceId(), count);
		Execution exec = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).onlyChildExecutions().singleResult();
		Job job = managementService.createJobQuery().executionId(exec.getId()).singleResult();
		managementService.setJobRetries(job.getId(), 1);
		managementService.executeJob(job.getId());
		Thread.sleep(180000);
	}
	
	@Test
	public void testQuery() {
		managementService.createDeadLetterJobQuery().list().forEach(j -> logger.info("无法执行的Job {} - {}.", j.getId(), j.getExecutionId()));
	}
	
}
