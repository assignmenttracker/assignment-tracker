package com.hanahs.tracker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AssignmentTest {

	@Test
	void test() {
		String name = "Physics Report";
		String description = "Write an experiment report";
		Assignment assignment = new Assignment(name, description);
		assertNotNull(assignment);
		assertEquals(name, assignment.getName());
		assertEquals(description, assignment.getDescription());
	}

}
