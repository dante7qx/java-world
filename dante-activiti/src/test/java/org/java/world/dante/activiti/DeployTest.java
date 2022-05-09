package org.java.world.dante.activiti;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveProcessTest.class);

	/**
	 * 部署 Zip 文件
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void deployZip() throws FileNotFoundException {
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addZipInputStream(new ZipInputStream(new FileInputStream(
				new File("/Users/dante/Documents/Project/dante-activiti/src/main/resources/testZipDeploy.zip"))));
		builder.name("deploy_zip_test").deploy();
		logger.info("ZipInputStream 部署示例......");
	}
	
	@Test
	public void deployBpmn() {
		repositoryService.createDeployment().addClasspathResource("diagrams/testDeploy.bpmn").deploy();
		logger.info("Classpath 部署示例......");
	}
	
	/**
	 * 部署 Bpmn 实体
	 */
	@Test
	public void deployBpmnModel() {
		repositoryService.createDeployment().addBpmnModel("bpmn_model_deploy", createBpmnModel()).deploy();
		logger.info("BpmnModel 部署示例......");
	}
	
	private BpmnModel createBpmnModel() {
		BpmnModel bpmnModel = new BpmnModel();
		// 定义流程
		org.activiti.bpmn.model.Process process = new org.activiti.bpmn.model.Process(); 
		process.setId("BpmnModelDeployProcess");
		process.setName("使用 Bpmn model 部署流程");
		
		// 开始事件
		StartEvent startEvent = new StartEvent();
		startEvent.setId("startEvent");
		startEvent.setName("开始流程");
		process.addFlowElement(startEvent);
		
		// 用户任务
		UserTask userTask = new UserTask();
		userTask.setId("userTask");
		userTask.setName("用户任务");
		process.addFlowElement(userTask);
		
		// 结束事件
		EndEvent endevent = new EndEvent();
		endevent.setId("endEvent");
		endevent.setName("结束流程");
		process.addFlowElement(endevent);
		
		// 添加流程事件
		process.addFlowElement(new SequenceFlow("startEvent", "userTask"));
		process.addFlowElement(new SequenceFlow("userTask", "endEvent"));
		
		bpmnModel.addProcess(process);
		return bpmnModel;
	}
	
	/**
	 * 获取部署文件内容
	 * 
	 * @throws IOException
	 */
	@Test
	public void getDeployText() throws IOException {
		InputStream is = repositoryService.getResourceAsStream("10001", "test1.txt");
		int count = is.available();
		byte[] contents = new byte[count];
		is.read(contents);
		String content = new String(contents);
		logger.info("10001 部署内容是 {}。", content);
	}
	
	@Test
	public void getDeployBpmn() throws IOException {
		// 查询流程定义实体
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition process = query.deploymentId("15001").singleResult();
		InputStream is = repositoryService.getProcessModel(process.getId());
		int count = is.available();
		byte[] contents = new byte[count];
		is.read(contents);
		String content = new String(contents);
		logger.info("15001 部署内容是 {}。", content);
	}
	
	@Test
	public void getDeployBpmnModel() throws IOException {
		// 查询流程定义实体
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition process = query.deploymentId("15001").singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(process.getId());
		logger.info(" {} ", bpmnModel);
	}
	
	/**
	 * 查询流程图片
	 * @throws IOException 
	 */
	@Test
	public void getDeployBpmnImage() throws IOException {
		// 查询流程定义实体
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition process = query.deploymentId("15001").singleResult();
		InputStream is = repositoryService.getProcessDiagram(process.getId());
		BufferedImage image = ImageIO.read(is);
		File file = new File("result.png");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		ImageIO.write(image, "png", fos);
		fos.close();
		is.close();
	}
	
	/**
	 * 删除部署信息
	 * 1. 不管是否指定级联，都会删除部署相关的身份数据、流程定义数据、流程资源与部署数据
	 * 2. 级联删除，会将运行时的流程实例、流程任务以及流程实例相关的历史数据删除
	 * 3. 不级联删除，但是存在运行时数据，会删除失败
	 * 
	 */
	@Test
	public void deleteDeploy() {
		repositoryService.deleteDeployment("10001", true);
	}
	
}
