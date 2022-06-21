package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.ds.ClassBean;
import com.demo.ds.Student;
import com.demo.repo.StudentRepo;
import com.demo.service.impl.StudentServiceImpl;

@SpringBootTest
public class TestStudentService {
	@Mock
	StudentRepo repo;
	@InjectMocks
	StudentServiceImpl service;
	
	@Test
	public void shouldSave() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("a");
		s.setClassBean(bean);
		s.setRegisterDate(LocalDate.of(2020, 5, 5));
		s.setAddress("a");
		service.save(s);
		verify(repo, times(1)).save(s);
	}
	
	@Test
	public void shouldDelete() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("a");
		s.setClassBean(bean);
		s.setRegisterDate(LocalDate.of(2020, 5, 5));
		s.setAddress("a");
		when(repo.findById(1)).thenReturn(Optional.of(s));
		Student obj = service.findStudentById(1);
		service.delete(obj);
		verify(repo, times(1)).delete(obj);
	}
	
	@Test
	public void shouldFindStudentByClassName() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("a");
		s.setClassBean(bean);
		s.setRegisterDate(LocalDate.of(2020, 5, 5));
		s.setAddress("a");
		
		when(repo.findStudentByClassName("test")).thenReturn(Arrays.asList(s));
		List<Student> obj = service.findStudentByClassName("test");
		assertEquals(1, obj.get(0).getStudentId());
		assertEquals("a", obj.get(0).getStudentName());
		
	}
	
	@Test
	public void shouldFindStudentById() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("a");
		s.setClassBean(bean);
		s.setRegisterDate(LocalDate.of(2020, 5, 5));
		s.setAddress("a");
		
		when(repo.findById(1)).thenReturn(Optional.of(s));
		Student obj= service.findStudentById(1);
		assertEquals("a", obj.getStudentName());
	}
	@Test
	public void shouldFindAllStudentList() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("a");
		s.setClassBean(bean);
		s.setRegisterDate(LocalDate.of(2020, 5, 5));
		s.setAddress("a");
		when(repo.findAll()).thenReturn(Arrays.asList(s));
		List<Student> sList = service.findAll();
		assertEquals(1, sList.size());
		verify(repo, times(1)).findAll();
	}
}
