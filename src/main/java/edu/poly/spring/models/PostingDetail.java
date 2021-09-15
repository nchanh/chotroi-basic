package edu.poly.spring.models;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "postingdetails")
public class PostingDetail implements java.lang.Comparable<PostingDetail>{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "nvarchar(150)")
	private String title;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String content;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String picture1;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String picture2;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String picture3;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String picture4;
	
	@Column
	private double price;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String manufacturer;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String product_type;
	
	@Column(columnDefinition = "nvarchar(150)")
	private String address;
	
	@ManyToOne
	@JsonIgnoreProperties(value = {"holidayDates"})
	@JoinColumn(name = "postingId")
	private Posting posting;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture1() {
		return picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}

	public String getPicture2() {
		return picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public String getPicture3() {
		return picture3;
	}

	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}

	public String getPicture4() {
		return picture4;
	}

	public void setPicture4(String picture4) {
		this.picture4 = picture4;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public Posting getPosting() {
		return posting;
	}

	public void setPosting(Posting posting) {
		this.posting = posting;
	}
	
	public PostingDetail(Integer id, String title, String content, String picture1, String picture2, String picture3,
			String picture4, double price, String manufacturer, String product_type, Posting posting,
			Set<PostingSaved> postingsaveds) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
		this.picture4 = picture4;
		this.price = price;
		this.manufacturer = manufacturer;
		this.product_type = product_type;
		this.posting = posting;
	}

	public PostingDetail() {
		super();
	}

	@Override
	public int compareTo(PostingDetail pd) {
		int id = pd.getId();
		return id-this.id;
	}	
	
}
