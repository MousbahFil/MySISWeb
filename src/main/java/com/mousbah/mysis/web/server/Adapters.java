package com.mousbah.mysis.web.server;

import com.mousbah.database.service.tables.Course;
import com.mousbah.database.service.tables.Student;
import com.mousbah.database.service.tables.User;
import com.mousbah.database.service.tables.UserType;
import com.mousbah.mysis.web.client.beans.GWTCourse;
import com.mousbah.mysis.web.client.beans.GWTStudent;
import com.mousbah.mysis.web.client.beans.GWTUser;
import com.mousbah.mysis.web.client.beans.GWTUser.GWTUserType;

public class Adapters {

	
	public static User adaptUser(GWTUser user) throws Exception {
		User finalUser =new User();
		finalUser.setEmail(user.getEmail());
		finalUser.setFirstName(user.getFirstName());
		finalUser.setLastName(user.getLastName());
		finalUser.setUserName(user.getUserName());
		finalUser.setPassword(user.getPassword());
		finalUser.setEmail(user.getEmail());
		finalUser.setId(user.getId());
		finalUser.setUserType(UserType.fromString(user.getUserType().getValue()));
		return finalUser;
	}
	
	public static GWTUser adaptUser(User user) throws Exception {
		GWTUser finalUser =new GWTUser();
		finalUser.setEmail(user.getEmail());
		finalUser.setFirstName(user.getFirstName());
		finalUser.setLastName(user.getLastName());
		finalUser.setUserName(user.getUserName());
		finalUser.setPassword(user.getPassword());
		finalUser.setEmail(user.getEmail());
		finalUser.setId(user.getId());
		finalUser.setUserType(GWTUserType.fromString(user.getUserType().getValue()));
		return finalUser;
	}
	
	public static Student adaptStudent(GWTStudent student) throws Exception{
		Student result=new Student(adaptUser(student));
		result.setCumulativeGpa(student.getCumulativeGpa());
		result.setId(student.getId());
		return result; 
	}
	
	public static GWTStudent adaptStudent(Student student) throws Exception{
		GWTStudent result=new GWTStudent(adaptUser(student));
		result.setCumulativeGpa(student.getCumulativeGpa());
		return result;
	}
	
	public static GWTCourse adaptCourse(Course course) throws Exception{
		GWTCourse result=new GWTCourse();
		result.setCourseName(course.getCourseName());
		result.setCredits(course.getCredits());
		result.setCrn(course.getCrn());
		result.setDescription(course.getDescription());
		return result;
	}
	
	public static Course adaptCourse(GWTCourse course) throws Exception{
		Course result=new Course();
		result.setCourseName(course.getCourseName());
		result.setCredits(course.getCredits());
		result.setCrn(course.getCrn());
		result.setDescription(course.getDescription());
		return result;
	}
}
