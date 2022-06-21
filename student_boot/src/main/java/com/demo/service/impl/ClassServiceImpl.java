package com.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.ds.ClassBean;
import com.demo.repo.ClassRepo;
import com.demo.service.ClassService;
@Service
public class ClassServiceImpl implements ClassService {
	@Autowired
	private ClassRepo repo;
	
	@Override
	public void save(ClassBean bean) {
		repo.save(bean);
	}

	@Override
	public ClassBean findByClassName(String className) {
		return repo.findByClassName(className);
	}

	@Override
	public void delete(ClassBean bean) {
		repo.delete(bean);
	}

	@Override
	public List<ClassBean> findAll() {
		
		return repo.findAll();
	}

	@Override
	public ClassBean findById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID "+id));
	}




}
