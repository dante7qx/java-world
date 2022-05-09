package org.java.world.dante.activiti;

import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(ActivitiJobTest.class);
	
	/**
	 * 定时启动事件
	 * 
	 * 设置 <property name="asyncExecutorActivate" value="true" />
	 * 
	 * @throws InterruptedException 
	 */
	@Test
	public void timerStartEvent() throws InterruptedException {
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("diagrams/timerStartEvent.bpmn").deploy();
		long count = runtimeService.createProcessInstanceQuery().deploymentId(deploy.getId()).count();
		logger.info("流程实例数量 {}", count);
		Thread.sleep(70000);
		count = runtimeService.createProcessInstanceQuery().deploymentId(deploy.getId()).count();
		logger.info("流程实例数量 {}", count);
	}
	
	/**
	 * 消息启动事件
	 * 
	 * 注意：消息名称在整个流程中必须惟一，数据存储在 ACT_RU_EVENT_SUBSCR
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void messageStartEvent() throws InterruptedException {
		repositoryService.createDeployment().addClasspathResource("diagrams/MsgStartEvent.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByMessage("msgStartEvt");
		logger.info("启动消息事件 {}.", pi.getProcessDefinitionName());
	}
	
	/**
	 * 错误启动事件 - 只能在事件子流程中定义
	 * 
	 */
	@Test
	public void errorStartEvent() throws InterruptedException {
		repositoryService.createDeployment().addClasspathResource("diagrams/errorStartEvent.bpmn").deploy();
		runtimeService.startProcessInstanceByKey("errorStartEventProcess");
	}
	
	/**
	 * 取消结束事件
	 * 
	 * 1. 只能在事务子流程中定义
	 * 2. 与取消边界事件一起使用
	 * @throws InterruptedException 
	 * 
	 */
	@Test
	public void cancelEndEvent() {
		repositoryService.createDeployment().addClasspathResource("diagrams/cancelEndEvent.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("cancelEndEventProcess");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前流程任务 {}.", task.getName());
		taskService.complete(task.getId());
		
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前流程任务 {}.", task.getName());
		
	}
	
	/**
	 * 定时器边界事件
	 * 1. 可中断 cancelActivity="true"，触发边界事件，原来的执行流被中断（执行流数据从数据库中被删除）
	 * 2. 不可中断 cancelActivity="false"
	 * 
	 */
	@Test
	public void timerBoundaryTest() {
		repositoryService.createDeployment().addClasspathResource("diagrams/timerBounder.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("timerBoundProcess");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前流程任务 {}.", task.getName());
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前流程任务 {}.", task.getName());
	}
	
	/**
	 * 信号边界事件
	 * 
	 * 信号边界事件会进行全局范围的信号捕获，同定时器边界事件一样具有可中断和不可中断两种
	 * 
	 */
	@Test
	public void signalBoundaryTest() {
		repositoryService.createDeployment().addClasspathResource("diagrams/signalBoundEvent.bpmn").deploy();
		// 启动两个流程实例
		ProcessInstance pi1 = runtimeService.startProcessInstanceByKey("signalBoundEventProcess");
		ProcessInstance pi2 = runtimeService.startProcessInstanceByKey("signalBoundEventProcess");
		
		Task task1 = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi1.getId(), task1.getName());
		taskService.complete(task1.getId());
		
		Task task2 = taskService.createTaskQuery().processInstanceId(pi2.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi2.getId(), task2.getName());
		taskService.complete(task2.getId());
		
		// 触发信号
		runtimeService.signalEventReceived("contractSignal");
		
		task1 = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi1.getId(), task1.getName());
		task2 = taskService.createTaskQuery().processInstanceId(pi2.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi2.getId(), task2.getName());
	}
	
	/**
	 * 定时器中间事件，Catching事件，其一直等待被触发，
	 * 当到达定义的时间条件后，该定时器中间事件会被触发，流程继续执行
	 * 
	 */
	@Test
	public void timerMidEvent() {
		repositoryService.createDeployment().addClasspathResource("diagrams/timerMidEvent.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("timerMidEventProcess");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi.getId(), task.getName());
		taskService.complete(task.getId());
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi.getId(), task.getName());
	}
	
	/**
	 * 信号中间Catching事件，等待被触发，直到该事件接收到相应的信号。单信号是全局的。
	 * 
	 */
	@Test
	public void signalMidEvent() {
		repositoryService.createDeployment().addClasspathResource("diagrams/signalMidEvent.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("signalMidProcess");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi.getId(), task.getName());
		taskService.complete(task.getId());
		
		Task payTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi.getId(), payTask.getName());
		taskService.complete(payTask.getId());
		
		runtimeService.signalEventReceived("orderGenSignal");
		Task completeOrderTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi.getId(), completeOrderTask.getName());
	}
	
	/**
	 * 信号中间Catching事件，等待被触发，直到该事件接收到相应的信号。单信号是全局的。
	 * 信号中间Throwing事件，用于抛出信号，分为同步和异步
	 * 
	 * 异步：Activiti 会为每一个信号Catching事件生成一条工作数据，JobExecutor会执行
	 * <signalEventDefinition signalRef="signalCatchingAndThrowing" activiti:async="true">
	 * 
	 */
	@Test
	public void signalMidEventWithCatchingAndThrowing() {
		repositoryService.createDeployment().addClasspathResource("diagrams/signalCatchingAndThrowingEvent.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("signalCatchingAndThrowingEventProcess");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi.getId(), task.getName());
		taskService.complete(task.getId());
		
		Task payTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("流程实例 {} 的任务 {}.", pi.getId(), payTask.getName());
		taskService.complete(payTask.getId());
		
//		// 同步 Throwing 事件
//		Task completeOrderTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
//		logger.info("流程实例 {} 的任务 {}.", pi.getId(), completeOrderTask.getName());
		
		// 异步 Throwing 事件
		List<Job> jobs = managementService.createJobQuery().list();
		logger.info("任务数量：{}", jobs.size());
	}
	
	/**
	 * 补偿中间事件
	 * 
	 * 1. 补偿次数和流程执行次数一致
	 * 2. 补偿边界事件保存在 ACT_RU_EVENT_SUBSCR 中
	 * 
	 */
	@Test
	public void compensationMidEvent() {
		repositoryService.createDeployment().addClasspathResource("diagrams/compensationMidEvent.bpmn").deploy();
		runtimeService.startProcessInstanceByKey("compensationMidEventProcess");
	}
}
