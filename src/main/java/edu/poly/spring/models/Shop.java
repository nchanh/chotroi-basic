package edu.poly.spring.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "shops")
public class Shop {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String username;

	@Column(length = 100)
	private String password;

	@Column(length = 11)
	private String phone;

	@Column(length = 40)
	private String email;

	@Column(length = 50)
	private String picture;

	@Column(columnDefinition = "nvarchar(50)")
	private String shopname;

	@Column(length = 100, columnDefinition = "nvarchar(500)")
	private String address;

	@Column(length = 100, columnDefinition = "nvarchar(500)")
	private String information;

	@Column(length = 20)
	private String status;
	
	@JsonIgnoreProperties(value = {"shop"})
	@JsonIgnore
	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private Set<Posting> postings;

	public Shop() {
		super();
	}

	public Shop(Integer id, String username, String passwrod, String phone, String email, String picture,
			String shopname, String businesscode, String address, String information, String status,
			Set<Posting> postings, Set<PostingSaved> postingsaveds, Set<Rate> rates) {
		super();
		this.id = id;
		this.username = username;
		this.password = passwrod;
		this.phone = phone;
		this.email = email;
		this.picture = picture;
		this.shopname = shopname;
		this.address = address;
		this.information = information;
		this.status = status;
		this.postings = postings;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
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

	public Set<Posting> getPostings() {
		return postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}
}
