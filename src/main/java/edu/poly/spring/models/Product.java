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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "nvarchar(100)")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "producttypeId")
	@JsonIgnoreProperties(value = {"products"})
	private ProductType producttype;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<Posting> postings;

	public Product(Integer id, String name, ProductType producttype, Set<Posting> postings) {
		super();
		this.id = id;
		this.name = name;
		this.producttype = producttype;
		this.postings = postings;
	}

	public Product() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("postings")
	public ProductType getProducttype() {
		return producttype;
	}

	public void setProducttype(ProductType producttype) {
		this.producttype = producttype;
	}

	public Set<Posting> getPostings() {
		return postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}
	
}
