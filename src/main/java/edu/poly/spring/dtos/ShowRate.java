package edu.poly.spring.dtos;

public class ShowRate {

	public String username;
	public Double point;
	
	public ShowRate() {
		super();
	}
	
	public ShowRate(String username, Double point) {
		super();
		this.username = username;
		this.point = point;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}
	
}
