package com.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.ds.ClassBean;

public interface ClassRepo extends JpaRepository<ClassBean, Integer> {
	
	@Query("select c from ClassBean c where c.className=:className")
	ClassBean findByClassName(String className);
}
