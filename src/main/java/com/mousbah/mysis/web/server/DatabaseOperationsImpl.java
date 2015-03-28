package com.mousbah.mysis.web.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mousbah.database.service.DatabaseServiceFactory;
import com.mousbah.database.service.api.CourseRepositoryService;
import com.mousbah.database.service.api.StudentRepositoryService;
import com.mousbah.database.service.api.UserAdminService;
import com.mousbah.database.service.tables.Course;
import com.mousbah.database.service.tables.Student;
import com.mousbah.database.service.tables.User;
import com.mousbah.database.service.tables.UserType;
import com.mousbah.mysis.web.client.DatabaseOperations;
import com.mousbah.mysis.web.client.beans.GWTCourse;
import com.mousbah.mysis.web.client.beans.GWTStudent;
import com.mousbah.mysis.web.client.beans.GWTUser;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseOperationsImpl extends RemoteServiceServlet implements DatabaseOperations {

	private static final DatabaseServiceFactory factory=DatabaseServiceFactory.newInstance();
	private static final UserAdminService userAdminService = factory.createUserAdminService();
	private static final CourseRepositoryService courseRepositoryService=factory.createCourseRepositoryService();
	private static final StudentRepositoryService studentRepoService=factory.createStudentRepositoryService();



	public void insertUser(GWTUser user) throws Exception {
		User finalUser = Adapters.adaptUser(user);
		factory.createUserAdminService().insertUser(finalUser);
	}

	public GWTUser validateUser(String userName, String password) throws Exception {
		Validate.isTrue(userAdminService.authenticateUser(userName, password), "Wrong user name or password provided");
		User user=userAdminService.getUser(userName);
		if(user.getUserType()==UserType.STUDENT){
			Student student=factory.createStudentRepositoryService().getStudentfromUser(user);
			return Adapters.adaptStudent(student);
		}else{
			return Adapters.adaptUser(user);
		}
	}



	public List<GWTUser> getAllUsers() throws Exception {
		List<User> actualUsers =userAdminService.getRegisteredUsers();
		return adaptUsers(actualUsers);
	}

	

	public List<GWTCourse> getRegisteredCourses(GWTStudent student) throws Exception {
		Student adaptedStudent=Adapters.adaptStudent(student);
		List<Course> courses =studentRepoService.getRegisteredCourses(adaptedStudent);
		List<GWTCourse> adaptedCourses=new ArrayList<GWTCourse>();
		for(Course course: courses){
			adaptedCourses.add(Adapters.adaptCourse(course));
		}
		return adaptedCourses;
	}

	public GWTUser getUser(String userName) throws Exception {
		return Adapters.adaptUser(userAdminService.getUser(userName));
	}

	public GWTStudent getStudentfromUser(GWTUser user) throws Exception {
		return Adapters.adaptStudent(studentRepoService.getStudentfromUser(Adapters.adaptUser(user)));
	}

	public List<GWTCourse> registerStudentInCourse(GWTStudent student, int crn) throws Exception {
		Course course=courseRepositoryService.getCourseByCrn(crn);
		Validate.isTrue(course!=null, String.format("Could not find course with crn %s", crn));
		Student adaptedStudent=Adapters.adaptStudent(student);
		studentRepoService.registerStudentInCourse(adaptedStudent, course);
		List<GWTCourse> adaptedCourses = getRegisteredCourses(adaptedStudent);
		return adaptedCourses;
	}


	public List<GWTCourse> dropStudentfromCourse(GWTStudent student, GWTCourse course)throws Exception {
		Student finalStudent=Adapters.adaptStudent(student);
		Course finalCourse=Adapters.adaptCourse(course);
		studentRepoService.dropStudentfromCourse(finalStudent, finalCourse);
		return getRegisteredCourses(finalStudent);
	}

	public List<GWTUser> deleteUser(int id) throws Exception {
		userAdminService.deleteUser(id);
		return getAllUsers();
	}

	private List<GWTCourse> getRegisteredCourses(Student adaptedStudent) throws Exception {
		List<Course> courses =studentRepoService.getRegisteredCourses(adaptedStudent);
		List<GWTCourse> adaptedCourses=new ArrayList<GWTCourse>();
		for(Course temp:courses){
			adaptedCourses.add(Adapters.adaptCourse(temp));
		}
		return adaptedCourses;
	}
	
	private List<GWTUser> adaptUsers(List<User> actualUsers) throws Exception {
		List<GWTUser> adaptedUsers = new ArrayList<GWTUser>();
		for(User user: actualUsers){
			adaptedUsers.add(Adapters.adaptUser(user));
		}
		return adaptedUsers;
	}

}
