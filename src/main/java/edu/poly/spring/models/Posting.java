package edu.poly.spring.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "postings")
public class Posting implements java.lang.Comparable<Posting>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column(length = 100)
	private boolean type;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date;
	
	@Column(length = 15)
	private String status;
	
	@ManyToOne
	@JsonIgnoreProperties(value = {"postings"})
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JsonIgnoreProperties(value = {"postings"})
	@JoinColumn(name = "shopId")
	private Shop shop;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	@JsonIgnoreProperties(value = {"posting"})
	@OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
	private Set<PostingDetail> postings;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<PostingDetail> getPostings() {
		return postings;
	}

	public void setPostings(Set<PostingDetail> postings) {
		this.postings = postings;
	}


	public Posting(Integer id, boolean type, Date date, String status, User user, Shop shop, Product product,
			Set<PostingDetail> postings) {
		super();
		this.id = id;
		this.type = type;
		this.date = date;
		this.status = status;
		this.user = user;
		this.shop = shop;
		this.product = product;
		this.postings = postings;
	}

	public Posting() {
		super();
	}
	
	@Override
	public int compareTo(Posting p) {
		int id = p.getId();
		return id-this.id;
	}	
	
}
