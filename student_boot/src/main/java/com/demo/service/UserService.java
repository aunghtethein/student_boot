package com.demo.service;

import java.util.List;

import com.demo.ds.User;

public interface UserService {
	void save(User user);
	void delete(User user);
	User findById(Integer id);
	List<User> findAll();
	List<User> listAll(String keyword);
	User findByUsername(String username);

	User findByPassword(String password);
	
}
