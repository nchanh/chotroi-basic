package edu.poly.spring.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class changePasswordDto implements Serializable{
private int id;
@NotNull
@NotEmpty(message = "Oldpassword is empty")
private String oldpassword;
@NotNull
@NotEmpty(message = "Newpassword is empty")
private String newpassword;
@NotNull
@NotEmpty(message = "Repassword is empty")
private String repassword;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getOldpassword() {
	return oldpassword;
}
public void setOldpassword(String oldpassword) {
	this.oldpassword = oldpassword;
}
public String getNewpassword() {
	return newpassword;
}
public void setNewpassword(String newpassword) {
	this.newpassword = newpassword;
}
public String getRepassword() {
	return repassword;
}
public void setRepassword(String repassword) {
	this.repassword = repassword;
}
public changePasswordDto(int id, @NotNull @NotEmpty(message = "Oldpassword is empty") String oldpassword,
		@NotNull @NotEmpty(message = "Newpassword is empty") String newpassword,
		@NotNull @NotEmpty(message = "Repassword is empty") String repassword) {
	super();
	this.id = id;
	this.oldpassword = oldpassword;
	this.newpassword = newpassword;
	this.repassword = repassword;
}
public changePasswordDto() {
	super();
}

}
