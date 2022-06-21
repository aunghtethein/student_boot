package com.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.demo.ds.Student;

public interface StudentRepo extends JpaRepository<Student,Integer> {
	@Query("select s from Student s where s.classBean.className=:name")
	List<Student> findStudentByClassName(@Param("name") String name);
}
