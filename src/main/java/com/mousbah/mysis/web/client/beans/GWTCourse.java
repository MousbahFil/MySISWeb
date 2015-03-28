package com.mousbah.mysis.web.client.beans;

import java.io.Serializable;


public class GWTCourse implements Serializable{

	private static final long serialVersionUID = 1L;
	private String courseName;
	private String description;
	private int crn;
	private int credits;

	public GWTCourse() {
		super();
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCrn() {
		return crn;
	}

	public void setCrn(int crn) {
		this.crn = crn;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

}
