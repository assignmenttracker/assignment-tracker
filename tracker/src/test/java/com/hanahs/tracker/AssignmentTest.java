package com.hanahs.tracker;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

class AssignmentTest {
	@Test
	void test() {
		String name = "물리학 실험보고서";
		String description = "실험 보고서를 작성하세요. 11월 12일까지 제출.";
		LocalDateTime due = LocalDateTime.of(2020, 11, 12, 11, 59);
		Assignment assignment = new Assignment(name, description, due);
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
}
