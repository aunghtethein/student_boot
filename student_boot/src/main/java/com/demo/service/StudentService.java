package com.demo.service;

import java.util.List;

import com.demo.ds.Student;

public interface StudentService {
	
	Student save(Student student);
	Student findStudentById(int id);
	List<Student> findAll();
	List<Student> findStudentByClassName(String name);
	void delete(Student student);
}
