package org.java.world.dante.controller;

import java.util.List;

import org.java.world.dante.dao.StudentDao;
import org.java.world.dante.po.StudentPO;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

	private final StudentDao studentDao;

	public StudentController(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@GetMapping("/find_all")
	public ResponseEntity<List<StudentPO>> listAll() {
		List<StudentPO> students = studentDao.findAll(Sort.by(Direction.DESC, "age"));
		log.info("所有学生 => {}", students);
		return ResponseEntity.ok(students);
	}

}
