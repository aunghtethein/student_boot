package com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import com.demo.ds.ClassBean;
import com.demo.ds.SignupForm;
import com.demo.ds.User;
import com.demo.service.impl.ClassServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class TestClassController {
	@Autowired
	private MockMvc mvc;
	@MockBean
	ClassServiceImpl service;
	
	@Test
	public void shouldAddClass() throws Exception {
		this.mvc.perform(get("/class-reg")
				.sessionAttr("currentUser", "user"))
		.andExpect(model().attributeExists("classBean"))
		.andExpect(status().isOk())
		.andExpect(view().name("classRegister"));
	}
	@Test
	public void shouldAddClassWithNoSession() throws Exception {
		this.mvc.perform(get("/class-reg"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
	@Test
	public void shouldSaveClassValidateFail() throws Exception {
		this.mvc.perform(post("/class-process"))
		.andExpect(status().isOk())
		.andExpect(view().name("classRegister"));
	}
	
	@Test
	public void shouldSaveClassValidatePass() throws Exception {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("a");
		bean.setFees(2000);
		this.mvc.perform(post("/class-process").flashAttr("classBean", bean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/class-list"));
	}
	
	@Test
	public void shouldShowClassList() throws Exception {
		ClassBean bean1 = new ClassBean();
		bean1.setId(1);
		bean1.setClassName("a");
		bean1.setFees(2000);
		ClassBean bean2 = new ClassBean();
		bean2.setId(2);
		bean2.setClassName("b");
		bean2.setFees(2000);
		when(service.findAll()).thenReturn(Arrays.asList(bean1, bean2));
		mvc.perform(get("/class-list")
			.sessionAttr("currentUser", "test"))
			.andExpect(model().attributeExists("classes"))
			.andExpect(status().isOk())
			.andExpect(view().name("classList"));
			verify(service, times(1)).findAll();
	}
	@Test
	public void shouldShowClassListWithNoSession() throws Exception {
		this.mvc.perform(get("/class-list"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void shouldDeleteClass() throws Exception {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("a");
		bean.setFees(2000);
		
		when(service.findById(1)).thenReturn(bean);
		this.mvc.perform(get("/delete-class/{id}", 1).sessionAttr("currentUser", "user"))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/class-list"));
	}
	@Test
	public void shouldDeleteClassWithNoSession() throws Exception {
		this.mvc.perform(get("/delete-class/{id}", 1))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
}
