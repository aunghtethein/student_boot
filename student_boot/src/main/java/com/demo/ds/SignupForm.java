package com.demo.ds;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignupForm {
    @NotEmpty(message = "Username must not be empty.")
    private String username;

    @NotEmpty(message = "Password must not be empty.")
    private String password;

    @NotEmpty(message = "Password must not be empty.")
    private String repeatPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	

    
    
}
