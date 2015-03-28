package com.mousbah.mysis.web.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mousbah.mysis.web.client.beans.GWTCourse;
import com.mousbah.mysis.web.client.beans.GWTStudent;
import com.mousbah.mysis.web.client.beans.GWTUser;

/**
 * The async counterpart of <code>DatabaseOperations</code>.
 */
public interface DatabaseOperationsAsync {
	void insertUser(GWTUser user, AsyncCallback<Void> callback);

	void validateUser(String userName, String password, AsyncCallback<GWTUser> callback);

	void getAllUsers(AsyncCallback<List<GWTUser>> callback);

	void getRegisteredCourses(GWTStudent student,AsyncCallback<List<GWTCourse>> callback);

	void getUser(String userName, AsyncCallback<GWTUser> callback);

	void getStudentfromUser(GWTUser user, AsyncCallback<GWTStudent> callback);

	void registerStudentInCourse(GWTStudent student, int crn, AsyncCallback<List<GWTCourse>> callback);

	void dropStudentfromCourse(GWTStudent student, GWTCourse course,AsyncCallback<List<GWTCourse>> callback);

	void deleteUser(int id, AsyncCallback<List<GWTUser>> callback); 
}
 