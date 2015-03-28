package com.mousbah.mysis.web.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mousbah.mysis.web.client.beans.GWTCourse;
import com.mousbah.mysis.web.client.beans.GWTStudent;
import com.mousbah.mysis.web.client.beans.GWTUser;


@RemoteServiceRelativePath("databaseOperations")
public interface DatabaseOperations extends RemoteService {
	void insertUser(GWTUser user) throws Exception;

	GWTUser validateUser(String userName, String password) throws Exception;

	List<GWTUser> getAllUsers() throws Exception;

	List<GWTCourse> getRegisteredCourses(GWTStudent student) throws Exception;

	GWTUser getUser(String userName) throws Exception;

	GWTStudent getStudentfromUser(GWTUser user) throws Exception;

	List<GWTCourse> registerStudentInCourse(GWTStudent student , int crn) throws Exception;

	List<GWTCourse> dropStudentfromCourse(GWTStudent student , GWTCourse course) throws Exception;
	
	List<GWTUser> deleteUser(int id) throws Exception;
}
