package org.java.world.dante.activiti;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.NativeGroupQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Activiti 查询API测试
 * 
 * @author dante
 *
 */
public class QueryTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(QueryTest.class);
	
	@Test
	public void createGroup() {
		for (int i = 0; i < 10; i++) {
			Group group = identityService.newGroup(i + "");
			group.setName("Group_" + i);
			group.setType("Type_" + i);
			identityService.saveGroup(group);
		}
		logger.info("创建组......");
	}
	
	@Test
	public void queryAll() {
		GroupQuery gq = identityService.createGroupQuery();
		List<Group> groups = gq.list();
		groups.forEach(g -> logger.info("[ {} -> {} -> {} ]", g.getId(), g.getName(), g.getType()));
	}
	
	@Test
	public void queryPage() {
		GroupQuery gq = identityService.createGroupQuery();
		List<Group> groups = gq.listPage(0, 5);
		groups.forEach(g -> logger.info("[ {} -> {} -> {} ]", g.getId(), g.getName(), g.getType()));
	}
	
	@Test
	public void querySort() {
		GroupQuery gq = identityService.createGroupQuery();
		List<Group> groups = gq.orderByGroupName().desc().listPage(0, 5);
		groups.forEach(g -> logger.info("[ {} -> {} -> {} ]", g.getId(), g.getName(), g.getType()));
	}
	
	@Test
	public void queryCount() {
		GroupQuery gq = identityService.createGroupQuery();
		long count = gq.count();
		logger.info("组数量 {}.", count);
	}
	
	@Test
	public void querySingle() {
		GroupQuery gq = identityService.createGroupQuery();
		Group g = gq.groupNameLike("%8").singleResult();
		logger.info("[ {} -> {} -> {} ]", g.getId(), g.getName(), g.getType());
	}
	
	@Test
	public void queryBySql() {
		NativeGroupQuery nativeGroupQuery = identityService.createNativeGroupQuery();
		List<Group> groups = nativeGroupQuery.sql("select * from ACT_ID_GROUP where NAME_ = #{name} or TYPE_ = #{type}").parameter("name", "Group_1").parameter("type", "Type_6").list();
		groups.forEach(g -> logger.info("[ {} -> {} -> {} ]", g.getId(), g.getName(), g.getType()));
	}
	
	@Test
	public void queryDeploy() {
		DeploymentQuery query = repositoryService.createDeploymentQuery();
		List<Deployment> list = query.orderByDeploymenTime().desc().list();
		logger.info("部署信息......");
		list.forEach(d -> logger.info("[ {} -> {} -> {} ]", d.getId(), d.getName(), d.getKey()));
		
		ProcessDefinitionQuery processQuery = repositoryService.createProcessDefinitionQuery();
		logger.info("流程信息......");
		List<ProcessDefinition> list2 = processQuery.list();
		list2.forEach(d -> logger.info("[ {} -> {} -> {} ]", d.getId(), d.getName(), d.getKey()));
		
		logger.info("按Key查询流程信息......");
		List<ProcessDefinition> list3 = processQuery.processDefinitionKey("myProcess").list();
		list3.forEach(d -> logger.info("[ {} -> {} -> {} ]", d.getId(), d.getName(), d.getKey()));
	}
}
