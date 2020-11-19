package com.hanahs.tracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.CourseWork;
import com.google.api.services.classroom.model.Date;
import com.google.api.services.classroom.model.ListCourseWorkResponse;
import com.google.api.services.classroom.model.ListCoursesResponse;
import com.google.api.services.classroom.model.ListStudentSubmissionsResponse;
import com.google.api.services.classroom.model.StudentSubmission;
import com.google.api.services.classroom.model.TimeOfDay;

public class GoogleClassroomProvider extends AssignmentProvider {
	private static final long serialVersionUID = 1000L;
	private static final String credentialsFilePath = "/cred.json";
	private static final String tokensDirectoryPath = "tokens";
	private static final JacksonFactory factory = JacksonFactory.getDefaultInstance();
	private static final List<String> scopes = Arrays.asList(
		ClassroomScopes.CLASSROOM_COURSES_READONLY,
		ClassroomScopes.CLASSROOM_COURSEWORK_ME_READONLY
	);
	
	private Classroom service;
	private List<Course> courses;
	
	private static Credential getCredentials(final NetHttpTransport transport) throws IOException {
		InputStream input = GoogleClassroomProvider.class.getResourceAsStream(credentialsFilePath);
		if (input == null) {
			throw new FileNotFoundException("cred.json not found");
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(factory, new InputStreamReader(input));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, factory, clientSecrets, scopes)
				.setDataStoreFactory(new FileDataStoreFactory(new File(tokensDirectoryPath))).build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
	
	private boolean checkCourseWorkCompletion(CourseWork courseWork) throws IOException {
		String courseId = courseWork.getCourseId();
		String courseWorkId = courseWork.getId();
		Classroom.Courses.CourseWork.StudentSubmissions.List list = this.service.courses().courseWork()
				.studentSubmissions().list(courseId, courseWorkId);
		list.setStates(Arrays.asList("TURNED_IN", "RETURNED"));
		ListStudentSubmissionsResponse response = list.execute();
		List<StudentSubmission> submissions = response.getStudentSubmissions();
		return submissions != null && submissions.size() > 0;
	}
	
	private Assignment convertCourseWorkToAssignment(CourseWork courseWork) {
		String name = courseWork.getTitle();
		String description = courseWork.getDescription();
		Date courseWorkDueDate = courseWork.getDueDate();
		TimeOfDay courseWorkDueTime = courseWork.getDueTime();
		if (courseWorkDueDate == null || courseWorkDueTime == null) return null;
		int year = Optional.ofNullable(courseWorkDueDate.getYear()).orElse(0);
		int month = Optional.ofNullable(courseWorkDueDate.getMonth()).orElse(0);
		int dayOfMonth = Optional.ofNullable(courseWorkDueDate.getDay()).orElse(0);
		int hour = Optional.ofNullable(courseWorkDueTime.getHours()).orElse(0);
		int minute = Optional.ofNullable(courseWorkDueTime.getMinutes()).orElse(0);
		LocalDateTime due = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		return new Assignment(name, description, due);
	}
	
	public GoogleClassroomProvider() throws GeneralSecurityException, IOException {
		super();
		final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
		this.service = new Classroom.Builder(transport, factory, getCredentials(transport))
				.setApplicationName("Assignment Tracker").build();
		ListCoursesResponse response = service.courses().list().execute();
		courses = response.getCourses();
	}

	@Override
	public List<Assignment> fetchAssignments() throws IOException {
		if (courses == null || courses.size() == 0) return null;
		ArrayList<Assignment> assignments = new ArrayList<>();
		this.courses.parallelStream().forEach(course -> {
			String courseId = course.getId();
			try {
				ListCourseWorkResponse response = service.courses().courseWork().list(courseId).execute();
				List<CourseWork> courseWorks = response.getCourseWork();
				if (courseWorks == null || courseWorks.size() == 0) return;
				courseWorks.parallelStream().forEach((courseWork) -> {
					Assignment assignment = this.convertCourseWorkToAssignment(courseWork);
					try {
						if (this.checkCourseWorkCompletion(courseWork)) return;
					} catch (IOException e) {}
					if (assignment == null) return;
					assignments.add(assignment);
				});
			} catch (IOException e) {}
		});
		return assignments;
	}
}
