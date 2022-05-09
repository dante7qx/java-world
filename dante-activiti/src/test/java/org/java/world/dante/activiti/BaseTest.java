package org.java.world.dante.activiti;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.After;
import org.junit.Before;

public class BaseTest {
	
	public ProcessEngine engine;
	public RepositoryService repositoryService;
	public RuntimeService runtimeService;
	public TaskService taskService;
	public IdentityService identityService;
	public ManagementService managementService;

	@Before
	public void init() {
		// 获取流程引擎
		engine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = engine.getRepositoryService();
		runtimeService = engine.getRuntimeService();
		taskService = engine.getTaskService();
		identityService = engine.getIdentityService();
		managementService = engine.getManagementService();
	}
	
	@After
	public void close() {
		engine.close();
	}
	
}
