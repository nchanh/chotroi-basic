package edu.poly.spring.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class EdituserDto implements Serializable {
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
	@NotNull
	private MultipartFile image;
	@NotNull
	@NotEmpty(message = "Name is empty")
	private String fullname;
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthday;
	@NotNull
	@NotEmpty(message = "Address is empty")
	private String address;
	private String status;
	@NotNull
	private boolean role;
	@NotNull
	private boolean gender;
	public EdituserDto() {
		super();
	}
	public EdituserDto(Integer id, @NotNull @NotEmpty(message = "Name is empty") String username,
			@NotNull @NotEmpty(message = "Password is empty") String password,
			@NotNull @NotEmpty(message = "Phone is empty") String phone,
			@NotNull @NotEmpty(message = "Email is empty") String email, @NotNull MultipartFile image,
			@NotNull @NotEmpty(message = "Name is empty") String fullname, @NotNull Date birthday,
			@NotNull @NotEmpty(message = "Address is empty") String address, String status, @NotNull boolean role,
			@NotNull boolean gender) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.image = image;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.status = status;
		this.role = role;
		this.gender = gender;
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
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	
}
