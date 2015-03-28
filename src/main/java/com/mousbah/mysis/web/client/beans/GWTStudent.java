package com.mousbah.mysis.web.client.beans;


public class GWTStudent extends GWTUser {

	private static final long serialVersionUID = 1L;
	private double cumulativeGpa;

	public GWTStudent() {
		super();
	}
	
	public GWTStudent(GWTUser user) {
		super(user);
	}


	public double getCumulativeGpa() {
		return cumulativeGpa;
	}

	public void setCumulativeGpa(double cumulativeGpa) {
		this.cumulativeGpa = cumulativeGpa;
	}
}
