package com.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demo.ds.User;
import com.demo.repo.UserRepo;

@SpringBootApplication
public class StudentBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentBootApplication.class, args);
	}

	 //@Bean
	    public CommandLineRunner demo(UserRepo repo) {
	        return (args) -> {
	            User user1 = new User("admin", "admin");
	            repo.save(user1);
	            
	        };
	    }
}
