package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.ds.ClassBean;
import com.demo.ds.User;
import com.demo.repo.ClassRepo;
import com.demo.service.impl.ClassServiceImpl;

@SpringBootTest
public class TestClassService {
	@Mock
	ClassRepo repo;
	@InjectMocks
	ClassServiceImpl service;
	
	@Test
	public void shouldSave() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		service.save(bean);
		verify(repo, times(1)).save(bean);
	}
	
	@Test
	public void shouldDelete() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		when(repo.findById(1)).thenReturn(Optional.of(bean));
		ClassBean b = service.findById(1);
		service.delete(b);
		verify(repo, times(1)).delete(b);
	}
	
	@Test
	public void shouldFindByClassName() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		
		when(repo.findByClassName("test")).thenReturn(bean);
		ClassBean obj = service.findByClassName("test");
		assertEquals("test", obj.getClassName());
		assertEquals(200.0, obj.getFees());
	}
	
	@Test
	public void shouldFindById() {
		ClassBean bean = new ClassBean();
		bean.setId(1);
		bean.setClassName("test");
		bean.setFees(200.0);
		
		when(repo.findById(1)).thenReturn(Optional.of(bean));
		ClassBean obj= service.findById(1);
		assertEquals("test", obj.getClassName());
		assertEquals(200.0, obj.getFees());
	}
	
	@Test
	public void shouldFindAllClassList() {
		ClassBean bean1 = new ClassBean();
		bean1.setId(1);
		bean1.setClassName("test");
		bean1.setFees(200.0);
		ClassBean bean2 = new ClassBean();
		bean2.setId(1);
		bean2.setClassName("test");
		bean2.setFees(200.0);
		when(repo.findAll()).thenReturn(Arrays.asList(bean1, bean2));
		List<ClassBean> cList = service.findAll();
		assertEquals(2, cList.size());
		verify(repo, times(1)).findAll();
	}
}
