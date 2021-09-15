package edu.poly.spring.dtos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class EditshopDto implements Serializable {
	private Integer id;
	@NotNull
	@NotEmpty(message = "Name is empty")
	private String username;
	@NotNull
	private MultipartFile image;
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
	@NotEmpty(message = "Shopname is empty")
	private String shopname;
	@NotNull
	@NotEmpty(message = "Businesscode is empty")
	private String businesscode;
	@NotNull
	@NotEmpty(message = "address is empty")
	private String address;
	@NotNull
	@NotEmpty(message = "information is empty")
	private String information;
	private String status;
	public EditshopDto(Integer id, @NotNull @NotEmpty(message = "Name is empty") String username,
			@NotNull MultipartFile image, @NotNull @NotEmpty(message = "Password is empty") String password,
			@NotNull @NotEmpty(message = "Phone is empty") String phone,
			@NotNull @NotEmpty(message = "Email is empty") String email,
			@NotNull @NotEmpty(message = "Shopname is empty") String shopname,
			@NotNull @NotEmpty(message = "Businesscode is empty") String businesscode,
			@NotNull @NotEmpty(message = "address is empty") String address,
			@NotNull @NotEmpty(message = "information is empty") String information,
			 String status) {
		super();
		this.id = id;
		this.username = username;
		this.image = image;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.shopname = shopname;
		this.businesscode = businesscode;
		this.address = address;
		this.information = information;
		this.status = status;
	}
	public EditshopDto() {
		super();
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
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
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
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getBusinesscode() {
		return businesscode;
	}
	public void setBusinesscode(String businesscode) {
		this.businesscode = businesscode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
