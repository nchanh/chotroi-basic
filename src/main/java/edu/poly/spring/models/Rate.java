package edu.poly.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rates")
public class Rate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String reason;
	
	@Column
	private Double point;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String image;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String assessor;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String supplyUnit;

	public Rate() {
		super();
	}

	public Rate(Integer id, String reason, Double point, String image, String assessor, String supplyUnit) {
		super();
		this.id = id;
		this.reason = reason;
		this.point = point;
		this.image = image;
		this.assessor = assessor;
		this.supplyUnit = supplyUnit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

}
