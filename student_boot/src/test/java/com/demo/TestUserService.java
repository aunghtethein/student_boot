package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.ds.User;
import com.demo.repo.UserRepo;
import com.demo.service.impl.UserServiceImpl;

@SpringBootTest
public class TestUserService {
	@Mock
	UserRepo repo;
	@InjectMocks
	UserServiceImpl service;
	
	@Test
	public void shouldGetAllUserList() {
		List<User> list = new ArrayList<>();
		User u1 = new User();
		u1.setId(001);
		u1.setUsername("u1");
		u1.setPassword("123");
		User u2 = new User();
		u2.setId(002);
		u2.setUsername("u2");
		u2.setPassword("123");
		list.add(u1);
		list.add(u2);
		when(repo.findAll()).thenReturn(list);
		List<User> uList = service.findAll();
		assertEquals(2, uList.size());
		verify(repo, times(1)).findAll();
	}
	@Test
	public void shouldGetFindById() {
		User u1 = new User();
		u1.setId(001);
		u1.setUsername("u1");
		u1.setPassword("123");
		
		when(repo.findById(001)).thenReturn(Optional.of(u1));
		User u = service.findById(001);
		assertEquals("u1", u.getUsername());
		assertEquals("123", u.getPassword());
	}
	
	@Test
	public void shouldGetName() {
		List<User> u = new ArrayList<>();
		User u1 = new User();
		u1.setId(001);
		u1.setUsername("u1");
		u1.setPassword("123");
		User u2 = new User();
		u1.setId(002);
		u1.setUsername("u2");
		u1.setPassword("123");
		u.add(u1);
		u.add(u2);
		when(repo.findAll("u1")).thenReturn(u);
		List<User> r = service.listAll("u1");
		assertEquals(1, r.size());
		System.out.println(r.get(0).getUsername());
	}
	
	
	@Test
	public void shouldSave() {
		User u1 = new User();
		u1.setId(001);
		u1.setUsername("u1");
		u1.setPassword("123");
		service.save(u1);
		verify(repo, times(1)).save(u1);
	}
	@Test
	public void shouldDelete() {
		User u1 = new User();
		u1.setId(001);
		u1.setUsername("u1");
		u1.setPassword("123");
		when(repo.findById(001)).thenReturn(Optional.of(u1));
		User u = service.findById(001);
		service.delete(u);
		verify(repo, times(1)).delete(u);
	}
	
	
}
