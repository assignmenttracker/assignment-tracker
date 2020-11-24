package com.hanahs.tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignmentManagerTest {
	private ProviderMock mock;
	private AssignmentManager manager;

	@BeforeEach
	void setUp() throws Exception {
		mock = new ProviderMock(Arrays.asList(
			new Assignment("과제8", "너기출 모든 문항을 첨부한 양식에 풀어서 제출하세요.",
					LocalDateTime.of(2020, 11, 18, 10, 0)),
			new Assignment("[수행평가] 물리학 2 실험보고서", "물리학 2 실험 보고서를 작성해서 제출하세요.",
					LocalDateTime.of(2020, 11, 30, 11, 59)),
			new Assignment("발표자료 제출", "AP물리학 논문리뷰, 실험 발표자료를 여기 제출하세요.",
					LocalDateTime.of(2020, 11, 29, 11, 59)),
			new Assignment("일상 속 예술두기 11.16", "영상을 통해 설명과 함께 작품을 감상하고 감상문을 작성하세요.",
					LocalDateTime.of(2020, 11, 19, 11, 59)),
			new Assignment("조별 프로젝트 - 프로그램 구현과정 및 보고서 제출", "프로그램 코드, 보고서를 제출할 것.",
					LocalDateTime.of(2020, 11, 27, 11, 59)),
			new Assignment("발표자료 제출", "심화 미적분학 발표자료를 여기 제출하세요.",
					LocalDateTime.of(2020, 11, 20, 11, 59)),
			new Assignment("미적분 어디까지 알아봤니?", "최종보고서를 제출하세요. 기한은 11월 25일까지.",
					LocalDateTime.of(2020, 11, 25, 11, 59)),
			new Assignment("화작 수행평가: 토론 입론서", "수업 시간에 입론서를 작성해서 제출하세요.",
					LocalDateTime.of(2020, 11, 23, 11, 59)),
			new Assignment("경제 NIE 보고서", "양식에 맞게 NIE를 해서 제출하세요.",
					LocalDateTime.of(2020, 11, 22, 11, 59))
		));
		manager = new AssignmentManager(LocalDate.of(2020, 11, 20));
		manager.getProviders().add(mock);
	}
	
	@Test
	void constructorWorksProperly() {
		assertNotNull(manager);
		assertNotNull(manager.getProviders());
		assertEquals(manager.getProviders().size(), 1);
		assertEquals(manager.getProviders().get(0), mock);
	}

	@Test
	void schedulingWorksProperly() throws IOException {
		List<List<Assignment>> schedule = manager.scheduleAssignments(5, 2);
		assertFalse(schedule.size() == 0);
		assertNotNull(schedule.get(0));
		int day = 0;
		for (List<Assignment> assignments: schedule) {
			System.out.printf("Day %d\n", day);
			for (Assignment assignment: assignments) {
				System.out.println(assignment.getName());
			}
			System.out.println();
			day++;
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		mock = null;
	}
}
