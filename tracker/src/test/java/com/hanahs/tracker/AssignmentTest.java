package com.hanahs.tracker;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignmentTest {
	private Assignment assignment;
	private String name = "물리학 실험보고서";
	private String description = "실험 보고서를 작성하세요. 11월 12일까지 제출.";
	private LocalDateTime due = LocalDateTime.of(2020, 11, 12, 11, 59);

	@BeforeEach
	void setUp() throws Exception {
		assignment = new Assignment(name, description, due);
	}

	@Test
	void constructorWorksProperly() {
		assertNotNull(assignment);
		assertEquals(name, assignment.getName());
		assertEquals(description, assignment.getDescription());
		assertEquals(due, assignment.getDue());
		Set<String> wordPool = assignment.getWordPool();
		assertTrue(wordPool.contains("보고서"));
		assertTrue(wordPool.contains("실험"));
		int estimatedETA = assignment.getEstimatedETA();
		assertEquals(2, estimatedETA);
		LocalDate startDate = LocalDate.from(due).minusDays(estimatedETA);
		assertEquals(startDate, LocalDate.of(2020, 11, 10));
	}

	@Test
	void equalsWorksProperly() {
		assertTrue(assignment.equals(assignment));
		Assignment another1 = new Assignment("다른 과제", description, due);
		assertFalse(assignment.equals(another1));
		Assignment another2 = new Assignment(name, "다른 디스크립션", due);
		assertFalse(assignment.equals(another2));
		Assignment another3 = new Assignment(name, description, LocalDateTime.of(2020, 11, 11, 11, 59));
		assertFalse(assignment.equals(another3));
	}

	@AfterEach
	void tearDown() throws Exception {
		assignment = null;
	}
}
