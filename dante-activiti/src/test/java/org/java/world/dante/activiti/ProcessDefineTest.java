package org.java.world.dante.activiti;

import java.util.List;
import java.util.UUID;

import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程定义测试
 * 
 * @author dante
 *
 */
public class ProcessDefineTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(ProcessDefineTest.class);
	
	@Test
	public void processDefine() {
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("diagrams/test1.bpmn").deploy();
		
		// 流程定义
		ProcessDefinitionQuery processDefineQuery = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition processDefine = processDefineQuery.deploymentId(deploy.getId()).singleResult();
		
		// 指定流程启动用户
		String userId = UUID.randomUUID().toString();
		User user = identityService.newUser(userId);
		user.setFirstName("dante");
		identityService.saveUser(user);

		// 指定流程启动候选人
		repositoryService.addCandidateStarterUser(processDefine.getId(), userId);
		
		// 查询指定用户所能启动的流程
		List<ProcessDefinition> list = processDefineQuery.startableByUser(userId).list();
		list.forEach(d -> logger.info("[ {} -> {} -> {} ]", d.getId(), d.getName(), d.getKey()));
	}
}
