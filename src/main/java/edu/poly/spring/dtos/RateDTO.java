package edu.poly.spring.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class RateDTO implements Serializable {
	
	private Integer id;
	
	@NotEmpty
	private String reason;
	
	@NotNull
	private MultipartFile image;
	
	@NotNull
	private Double point;
	
	@NotNull
	private String assessor;
	
	@NotNull
	private String supplyUnit;

	public RateDTO() {
		super();
	}

	public RateDTO(Integer id, @NotEmpty String reason, @NotNull MultipartFile image, @NotNull Double point,
			@NotNull String assessor, @NotNull String supplyUnit) {
		super();
		this.id = id;
		this.reason = reason;
		this.image = image;
		this.point = point;
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
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
