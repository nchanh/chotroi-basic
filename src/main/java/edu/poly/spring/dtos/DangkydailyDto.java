package edu.poly.spring.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class DangkydailyDto implements Serializable  {
	private Integer id;
	@NotNull
	@NotEmpty(message = "Name is empty")
	private String username;
	@NotNull
	@NotEmpty(message = "Password is empty")
	private String password;
	@NotNull
	@NotEmpty(message = "Phone is empty")
	private String phone;
	@NotNull
	@NotEmpty(message = "Email is empty")
	private String email;
	public DangkydailyDto() {
		super();
	}
	public DangkydailyDto(Integer id, @NotNull @NotEmpty(message = "Name is empty") String username,
			@NotNull @NotEmpty(message = "Password is empty") String password,
			@NotNull @NotEmpty(message = "Phone is empty") String phone,
			@NotNull @NotEmpty(message = "Email is empty") String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
