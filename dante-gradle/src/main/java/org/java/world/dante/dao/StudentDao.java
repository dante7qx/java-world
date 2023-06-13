package org.java.world.dante.dao;

import java.util.List;

import org.java.world.dante.po.StudentPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<StudentPO, Integer> {
	
	public List<StudentPO> findByNameLikeOrderByAgeDesc(String name);
} 
