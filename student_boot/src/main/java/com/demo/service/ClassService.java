package com.demo.service;

import java.util.List;

import com.demo.ds.ClassBean;

public interface ClassService {
	void save(ClassBean bean);
	void delete(ClassBean bean);
	ClassBean findById(Integer id);
	ClassBean findByClassName(String className);
	List<ClassBean> findAll();
}
