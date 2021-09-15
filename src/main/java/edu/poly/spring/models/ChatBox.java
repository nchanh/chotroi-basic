package edu.poly.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chatboxs")
public class ChatBox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition = "nvarchar(500)")
	private String message;
	@Column(columnDefinition = "nvarchar(100)")
	private String time;
	@ManyToOne
	@JoinColumn(name = "iduser")
	private User user;
	@ManyToOne
	@JoinColumn(name = "iduser2")
	private User user2;
	@ManyToOne
	@JoinColumn(name = "idshop")
	private Shop shop;
	@ManyToOne
	@JoinColumn(name = "idshop2")
	private Shop shop2;
	public ChatBox(Integer id, String message, String time, User user, User user2, Shop shop, Shop shop2) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.user = user;
		this.user2 = user2;
		this.shop = shop;
		this.shop2 = shop2;
	}
	public ChatBox() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Shop getShop2() {
		return shop2;
	}
	public void setShop2(Shop shop2) {
		this.shop2 = shop2;
	}
	
	
	
	
	
}
