package edu.poly.spring.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserLoginDTO implements Serializable{

	@NotNull
	@NotEmpty(message = "Username is empty")
	private String username;
	
	@NotNull
	@NotEmpty(message = "Password is empty")
	private String password;

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
	
	
}
