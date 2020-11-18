package com.hanahs.tracker;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public abstract class AssignmentProvider implements Serializable {
	public abstract List<Assignment> fetchAssignments() throws IOException;
}