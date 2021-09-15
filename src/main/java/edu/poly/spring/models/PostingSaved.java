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
@Table(name = "postingsaveds")
public class PostingSaved {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "nvarchar(50)")
	private String assessor;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String supplyUnit;
	
	private Integer postingID;

	public PostingSaved(Integer id, String assessor, String supplyUnit, Integer postingID) {
		super();
		this.id = id;
		this.assessor = assessor;
		this.supplyUnit = supplyUnit;
		this.postingID = postingID;
	}

	public PostingSaved() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public String getSupplyUnit() {
		return supplyUnit;
	}

	public void setSupplyUnit(String supplyUnit) {
		this.supplyUnit = supplyUnit;
	}

	public Integer getPostingID() {
		return postingID;
	}

	public void setPostingID(Integer postingID) {
		this.postingID = postingID;
	}

	
}