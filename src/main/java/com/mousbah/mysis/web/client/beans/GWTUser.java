package com.mousbah.mysis.web.client.beans;

import java.io.Serializable;

public class GWTUser implements Serializable{

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private GWTUserType userType;


	public enum GWTUserType {

		STUDENT("STUDENT"), FACULTY("FACULTY"), STAFF("STAFF"), ADMINISTRATOR("ADMINISTRATOR");

		private String value;

		private GWTUserType(String value){
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

 
		public static GWTUserType fromString(String value) {
			for(GWTUserType temp: GWTUserType.values()){
				if(temp.getValue().equals(value)){
					return temp;
				}
			}
			throw new IllegalStateException("Could not find eum with value "+ value);
		}
	}

	public GWTUser() {
	}
	
	public GWTUser(GWTUser user) {
	this.id=user.id;
	this.firstName=user.firstName;
	this.lastName=user.lastName;
	this.email=user.email;
	this.password=user.password;
	this.userType=user.userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GWTUserType getUserType() {
		return userType;
	}

	public void setUserType(GWTUserType userType) {
		this.userType = userType;
	}

}
