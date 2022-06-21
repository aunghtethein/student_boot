package com.demo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.ds.ClassBean;
import com.demo.ds.Student;
import com.demo.ds.User;
import com.demo.service.impl.StudentServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class TestStudentController {
	@Autowired
	private MockMvc mvc;
	@MockBean
	StudentServiceImpl service;
	
	@Test
	public void shouldAddStudent() throws Exception {
		this.mvc.perform(get("/student-reg")
				.sessionAttr("currentUser", "user"))
		.andExpect(model().attributeExists("student"))
		.andExpect(model().attributeExists("classes"))
		.andExpect(status().isOk())
		.andExpect(view().name("studentRegister"));
	}
	@Test
	public void shouldAddStudentWithNoSession() throws Exception {
		this.mvc.perform(get("/student-reg"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
	@Test
	public void shouldSaveStudentValidateFail() throws Exception {
		this.mvc.perform(post("/students"))
		.andExpect(status().isOk())
		.andExpect(view().name("studentRegister"));
	}
	
	@Test
	public void shouldSaveStudentValidatePass() throws Exception {
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
		this.mvc.perform(post("/students").flashAttr("student", s))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/student/all"));
	}
	
	@Test
	public void shouldShowStudentList() throws Exception {
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
		
		when(service.findAll()).thenReturn(Arrays.asList(s));
		mvc.perform(get("/student/all")
			.sessionAttr("currentUser", "test"))
			.andExpect(model().attributeExists("students"))
			.andExpect(status().isOk())
			.andExpect(view().name("studentList"));
			verify(service, times(1)).findAll();
	}
	@Test
	public void shouldShowStudentListWithNoSession() throws Exception {
		this.mvc.perform(get("/student/all"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void shouldEditStudent() throws Exception {
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
		
		when(service.findStudentById(1)).thenReturn(s);
		this.mvc.perform(get("/edit-student/{id}", 1).sessionAttr("currentUser", "user"))
				.andExpect(status().isOk())
				.andExpect(view().name("studentUpdate"))
				.andExpect(model().attributeExists("student"))
				.andExpect(model().attributeExists("classes"));
	}
	
	@Test
	public void shouldEditStudentWithNoSession() throws Exception {
		this.mvc.perform(get("/edit-student/{id}", 1))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void shouldUpdateStudentValidateFail() throws Exception {
		this.mvc.perform(post("/update-student/{studentId}", 1))
		.andExpect(status().isOk())
		.andExpect(view().name("studentUpdate"));
	}
	
	@Test
	public void shouldUpdateStudentValidatePass() throws Exception {
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
		this.mvc.perform(post("/update-student/{studentId}", 1).flashAttr("student", s))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/student/all"));
		verify(service, times(1)).save(s);
	}
	
	
	
	@Test
	public void shouldDeleteStudent() throws Exception {
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
		
		when(service.findStudentById(1)).thenReturn(s);
		this.mvc.perform(get("/delete-student/{id}", 1).sessionAttr("currentUser", "user"))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/student/all"));
		verify(service, times(1)).delete(s);
	}
	@Test
	public void shouldDeleteClassWithNoSession() throws Exception {
		this.mvc.perform(get("/delete-student/{id}", 1))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
}
