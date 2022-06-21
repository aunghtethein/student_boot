package com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;

import static org.assertj.core.api.Assertions.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.demo.ds.SignupForm;
import com.demo.ds.User;
import com.demo.repo.UserRepo;
import com.demo.service.impl.UserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {
	@Autowired
	private MockMvc mvc;
	@MockBean
	UserServiceImpl service;
	@MockBean
	UserRepo repo;
	
	@Test
	public void shoudlFetchIndex() throws Exception {
		this.mvc.perform(get("/"));
		this.mvc.perform(get("/home"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"));
	}
	@Test
	public void shouldShowAccountWithNotSession() throws Exception {
		this.mvc.perform(get("/account"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));	
	}
	
	@Test
	public void shouldShowAccount() throws Exception {
		this.mvc.perform(get("/account")
				.sessionAttr("currentUser", "test"))
				.andExpect(view().name("account"));
	}


	@Test
	public void shouldShowLogin() throws Exception {
		this.mvc.perform(get("/login"))
		.andExpect(model().attributeExists("user"))
		.andExpect(view().name("login"));
	}
	
	@Test
	public void shouldDoLoginValidateFail() throws Exception {
		this.mvc.perform(post("/login-process"))
		.andExpect(status().isOk())
		.andExpect(view().name("login"));
	}
	@Test
	public void shouldDoLoginValidatePass() throws Exception {
		User u1 = new User();
		u1.setId(1);
		u1.setUsername("test1");
		u1.setPassword("test1");
		when(service.findByUsername("test1")).thenReturn(u1);
		when(service.findByPassword("test1")).thenReturn(u1);
		
		this.mvc.perform(post("/login-process").flashAttr("user", u1).sessionAttr("currentUser", "te"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));

	}
	@Test
	public void shouldDoLoginValidatePassWithNull() throws Exception {
		User u1 = new User();
		u1.setId(1);
		u1.setUsername("test");
		u1.setPassword("test");
		when(service.findByUsername("test1")).thenReturn(u1);
		when(service.findByPassword("test1")).thenReturn(u1);
	
		this.mvc.perform(post("/login-process").flashAttr("user", u1))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));

	}
	
		
	@Test
	public void shouldLogoutOk() throws Exception {
		this.mvc.perform(get("/logout")
				.sessionAttr("currentUser", "test"))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void shouldShowUserListWithNoSession() throws Exception {
		this.mvc.perform(get("/user-list"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));	
	}
	@Test
	public void shouldShowUserList() throws Exception {
		User u1 = new User();
		u1.setId(1);
		u1.setUsername("test1");
		u1.setPassword("test1");
		
		User u2 = new User();
		u2.setId(2);
		u2.setUsername("test2");
		u2.setPassword("test2");
		when(service.listAll("test1")).thenReturn(Arrays.asList(u1,u2));
		mvc.perform(get("/user-list")
			.sessionAttr("currentUser", "test"))
			.andExpect(status().isOk())
			.andExpect(view().name("userList"));
			verify(service, times(1)).listAll(null);
	}
	
	
	@Test
	public void shouldRegister() throws Exception {
		this.mvc.perform(get("/register"))
		.andExpect(model().attributeExists("signupForm"))
		.andExpect(view().name("register"));
	}
	
	
	@Test
	public void shouldSaveUserValidateFail() throws Exception {
		this.mvc.perform(post("/saveUser"))
		.andExpect(status().isOk())
		.andExpect(view().name("register"));
	}

	@Test
	public void shouldSaveUserValidatePass() throws Exception {
		SignupForm obj = new SignupForm();
		obj.setUsername("123");
		obj.setPassword("123");
		obj.setRepeatPassword("123");
		this.mvc.perform(post("/saveUser").flashAttr("signupForm", obj))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/home"));
	}
	
	@Test
	public void shouldSaveUserValidatePassNotSame() throws Exception {
		SignupForm obj = new SignupForm();
		obj.setUsername("123");
		obj.setPassword("123");
		obj.setRepeatPassword("12");
		this.mvc.perform(post("/saveUser").flashAttr("signupForm", obj))
		.andExpect(status().isOk())
		.andExpect(view().name("register"));
	}
	
	
	@Test
	public void shouldEditUser() throws Exception {
		User u = new User();
		u.setId(1);
		u.setUsername("test");
		u.setPassword("user");
		
		when(service.findById(1)).thenReturn(u);
		this.mvc.perform(get("/edit/{id}", 1).sessionAttr("currentUser", "user"))
				.andExpect(status().isOk())
				.andExpect(view().name("userUpdate"))
				.andExpect(model().attributeExists("user"));
	}
	
	@Test
	public void shouldEditUserWithNoSession() throws Exception {
		this.mvc.perform(get("/edit/{id}", 1))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}
	
	@Test
	public void shouldEditUserValidateFail() throws Exception {
		this.mvc.perform(post("/update/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(view().name("userUpdate"));
	}
	
	@Test
	public void shouldEditUserValidatePass() throws Exception {
		User u = new User();
		u.setId(1);
		u.setUsername("test");
		u.setPassword("test");
		this.mvc.perform(post("/update/{id}", 1).flashAttr("user", u))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/user-list"));
	}
	
	@Test
	public void shouldDeleteUser() throws Exception {
		User u = new User();
		u.setId(1);
		u.setUsername("test");
		u.setPassword("user");
		
		when(service.findById(1)).thenReturn(u);
		this.mvc.perform(get("/delete/{id}", 1).sessionAttr("currentUser", "user"))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/user-list"));
	}

	@Test
	public void shouldDeleteUserWithNoSession() throws Exception {
		this.mvc.perform(get("/delete/{id}", 1))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));
	}

}
