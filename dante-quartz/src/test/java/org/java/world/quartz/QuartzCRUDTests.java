package org.java.world.quartz;

import org.java.world.quartz.job.CurdJob;
import org.junit.Test;

public class QuartzCRUDTests {
	
	public static String JOB_NAME = "动态任务调度";  
    public static String TRIGGER_NAME = "动态任务触发器";  
    public static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";  
    public static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP"; 
	
	@Test
	public void quartzCurd() {
		try {  
            System.out.println("【系统启动】开始(每1秒输出一次)...");    
            QuartzCURD.addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, CurdJob.class, "0/1 * * * * ?");    

            Thread.sleep(5000);    
            System.out.println("【修改时间】开始(每2秒输出一次)...");    
            QuartzCURD.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/2 * * * * ?");    

            Thread.sleep(6000);    
            System.out.println("【暂停定时】开始...");    
            QuartzCURD.pauseJob(JOB_NAME, JOB_GROUP_NAME);    
            System.out.println("【暂停定时】成功");  
            
            Thread.sleep(3000);    
            System.out.println("【恢复定时】开始...");    
            QuartzCURD.resumeJob(JOB_NAME, JOB_GROUP_NAME);    
            System.out.println("【恢复定时】成功");  
            
            Thread.sleep(7000);
            System.out.println("【移除定时】开始...");    
            QuartzCURD.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);    
            System.out.println("【移除定时】成功");    
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

}
