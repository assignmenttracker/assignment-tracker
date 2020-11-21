package com.hanahs.tracker;

import java.io.IOException;
import java.util.List;

class ProviderMock extends AssignmentProvider {
	private static final long serialVersionUID = 1000L;
	private List<Assignment> assignments;
	
	public ProviderMock(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	@Override
	public List<Assignment> fetchAssignments() throws IOException {
		return assignments;
	}
}
