package com.hanahs.tracker;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class AssignmentTest {

	@Test
	void test() {
		String name = "Physics Report";
		String description = "Write an experiment report";
		LocalDateTime due = LocalDateTime.now();
		Assignment assignment = new Assignment(name, description, due);
		assertNotNull(assignment);
		assertEquals(name, assignment.getName());
		assertEquals(description, assignment.getDescription());
		assertEquals(due, assignment.getDue());
	}

}
