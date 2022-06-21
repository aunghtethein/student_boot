package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.ds.User;
import com.demo.repo.UserRepo;
import com.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Override
	public void save(User user) {
		userRepo.save(user);
	}

	@Override
	public void delete(User user) {
		userRepo.delete(user);
	}

	@Override
	public User findById(Integer id) {
		return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID "+id));
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public List<User> listAll(String keyword) {
		if(keyword != null) {
			return userRepo.findAll(keyword);
		}
 		return userRepo.findAll();
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public User findByPassword(String password) {
		return userRepo.findByPassword(password);
	}
	
	

	

	

}
