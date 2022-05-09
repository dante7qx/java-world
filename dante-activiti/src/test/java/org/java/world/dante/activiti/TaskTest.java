package org.java.world.dante.activiti;

import java.util.List;
import java.util.UUID;

import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务服务测试
 * 
 * 1. 权限
 * 任务候选人（组）
 * 任务持有人
 * 任务代理人
 * 
 * @author dante
 *
 */
public class TaskTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskTest.class);
	
	/**
	 * 任务候选人 任务 * - 人员 *
	 */
	@Test
	public void candidateUser() {
		String taskId = UUID.randomUUID().toString();
		Task task = taskService.newTask(taskId);
		task.setName("测试任务 - 人员");
		taskService.saveTask(task);
		taskService.setVariable(taskId, "remark", "批示同意！");
		
		User user = identityService.newUser(UUID.randomUUID().toString());
		user.setFirstName("测试任务—人员");
		identityService.saveUser(user);
		
		taskService.addCandidateUser(taskId, user.getId());
		
		// 查询指定用户所候选的任务
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(user.getId()).list();
		tasks.forEach(t -> logger.info("任务名称 -> {}", t.getName()));
	}
	
	/**
	 * 任务持有人 任务 * - 人员 1
	 */
	@Test
	public void owenUser() {
		String taskId = UUID.randomUUID().toString();
		Task task = taskService.newTask(taskId);
		task.setName("测试任务 - 持有人员");
		taskService.saveTask(task);
		
		User user = identityService.newUser(UUID.randomUUID().toString());
		user.setFirstName("测试任务—持有人员");
		identityService.saveUser(user);
		
		taskService.setOwner(taskId, user.getId());
		
		// 查询指定用户所持有的任务
		List<Task> tasks = taskService.createTaskQuery().taskOwner(user.getId()).list();
		tasks.forEach(t -> logger.info("任务名称 -> {}", t.getName()));
	}
	
	/**
	 * 任务代理人 任务 * - 人员 1
	 */
	@Test
	public void assigneeUser() {
		String taskId = UUID.randomUUID().toString();
		Task task = taskService.newTask(taskId);
		task.setName("测试任务 - 代理人员");
		taskService.saveTask(task);
		
		User user = identityService.newUser(UUID.randomUUID().toString());
		user.setFirstName("测试任务—代理人员");
		identityService.saveUser(user);
		
		taskService.setAssignee(taskId, user.getId());
		
		// 查询指定用户所代理的任务
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(user.getId()).list();
		tasks.forEach(t -> logger.info("任务名称 -> {}", t.getName()));
	}
	
}
