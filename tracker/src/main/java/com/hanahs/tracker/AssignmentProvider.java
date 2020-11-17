package com.hanahs.tracker;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public abstract class AssignmentProvider implements Serializable {
	public abstract void login(AuthenticationInfo authinfo);
	public abstract List<Assignment> fetchAssignments();
}