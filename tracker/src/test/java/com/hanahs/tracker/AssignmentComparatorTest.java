package com.hanahs.tracker;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignmentComparatorTest {
	private List<Assignment> assignments;
	private List<Assignment> sortedResult;
	private AssignmentComparator comparator;
	
	@BeforeEach
	void setUp() throws Exception {
		assignments = Arrays.asList(
			new Assignment("과제8", "너기출 모든 문항을 첨부한 양식에 풀어서 제출하세요.",
					LocalDateTime.of(2020, 11, 18, 11, 59)),
			new Assignment("[수행평가] 물리학 2 실험보고서", "물리학 2 실험 보고서를 작성해서 제출하세요.",
					LocalDateTime.of(2020, 11, 30, 11, 59)),
			new Assignment("발표자료 제출", "AP물리학 논문리뷰, 실험 발표자료를 여기 제출하세요.",
					LocalDateTime.of(2020, 11, 29, 11, 59)),
			new Assignment("일상 속 예술두기 11.16", "영상을 통해 설명과 함께 작품을 감상하고 감상문을 작성하세요.",
					LocalDateTime.of(2020, 11, 19, 11, 59)),
			new Assignment("조별 프로젝트 - 프로그램 구현과정 및 보고서 제출", "프로그램 코드, 보고서를 제출할 것.",
					LocalDateTime.of(2020, 11, 27, 11, 59))
		);
		sortedResult = Arrays.asList(
			new Assignment("과제8", "너기출 모든 문항을 첨부한 양식에 풀어서 제출하세요.",
					LocalDateTime.of(2020, 11, 18, 11, 59)),
			new Assignment("일상 속 예술두기 11.16", "영상을 통해 설명과 함께 작품을 감상하고 감상문을 작성하세요.",
					LocalDateTime.of(2020, 11, 19, 11, 59)),
			new Assignment("발표자료 제출", "AP물리학 논문리뷰, 실험 발표자료를 여기 제출하세요.",
					LocalDateTime.of(2020, 11, 29, 11, 59)),
			new Assignment("조별 프로젝트 - 프로그램 구현과정 및 보고서 제출", "프로그램 코드, 보고서를 제출할 것.",
					LocalDateTime.of(2020, 11, 27, 11, 59)),
			new Assignment("[수행평가] 물리학 2 실험보고서", "물리학 2 실험 보고서를 작성해서 제출하세요.",
					LocalDateTime.of(2020, 11, 30, 11, 59))
		);
		comparator = new AssignmentComparator(LocalDate.of(2020, 11, 20));
	}
	
	@Test
	void constructorWorksProperly() {
		assertNotNull(comparator);
		assertEquals(comparator.getPivotDate(), LocalDate.of(2020, 11, 20));
	}

	@Test
	void sortsProperlyWithComparator() {
		assignments.sort(comparator);
		assertTrue(assignments.equals(sortedResult));
	}

	@AfterEach
	void tearDown() throws Exception {
		assignments = null;
		comparator = null;
	}
}
