package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.ds.Student;
import com.demo.repo.StudentRepo;
import com.demo.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepo studentRepo;

	@Override
	public Student save(Student student) {
		return studentRepo.save(student);
	}

	@Override
	public Student findStudentById(int id) {
		return studentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID "+id));
	}

	@Override
	public List<Student> findAll() {
		return studentRepo.findAll();
	}

	@Override
	public List<Student> findStudentByClassName(String name) {
		return studentRepo.findStudentByClassName(name);
	}

	@Override
	public void delete(Student student) {
		studentRepo.delete(student);
	}


}
