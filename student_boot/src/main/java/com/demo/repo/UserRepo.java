package com.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.ds.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
  
	@Query("select u from User u where concat(u.id, u.username) like %?1%")
	public List<User> findAll(String keyword);

	User findByUsername(String username);

	User findByPassword(String password);
	
	
	
}
