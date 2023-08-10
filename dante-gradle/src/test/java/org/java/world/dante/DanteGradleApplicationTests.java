package org.java.world.dante;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.java.world.dante.dao.StudentDao;
import org.java.world.dante.po.StudentPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class DanteGradleApplicationTests {

	@Autowired
	private StudentDao studentDao;
	
	@Test
	public void findAll() {
		StudentPO student = new StudentPO();
		student.setName("测试");
		student.setAge(new Random().nextInt(39 - 21 + 1) + 21);
		student.setUpdateDate(Date.from(Instant.now()));
		studentDao.save(student);
		
		List<StudentPO> students = studentDao.findAll();
		log.info("{} ==> ", students);
	}
	
}
