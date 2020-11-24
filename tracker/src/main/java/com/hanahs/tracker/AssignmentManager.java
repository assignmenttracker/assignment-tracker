package com.hanahs.tracker;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class AssignmentManager implements Serializable {
	private static final long serialVersionUID = 1000L;
	private List<AssignmentProvider> providers;
	private LocalDate today;
	
	public AssignmentManager(LocalDate today) {
		this.today = today;
		this.providers = new ArrayList<>();
	}
	
	public AssignmentManager() {
		this(LocalDate.now());
	}
	
	List<List<Assignment>> scheduleAssignments(int days, int maximumAssignmentPerDay) throws IOException {
		List<List<Assignment>> schedule = new LinkedList<>();
		AssignmentComparator comparator = new AssignmentComparator();
		PriorityQueue<Assignment> priorityQueue = new PriorityQueue<>(comparator);
		for (AssignmentProvider provider: this.providers) {
			List<Assignment> assignments = provider.fetchAssignments();
			priorityQueue.addAll(assignments);
		}
		LocalDateTime endOfToday = LocalDateTime.of(today, LocalTime.of(23, 59, 59));
		for (int day = 0; day < days && priorityQueue.size() > 0; ++day) {
			LocalDateTime endOfCurrentDay = endOfToday.plusDays(day);
			List<Assignment> currentSchedule = new LinkedList<>();
			Assignment currentAssignment = priorityQueue.peek();
			boolean finishedUrgentAssignments = false;
			do {
				if (currentAssignment.getDue().isAfter(endOfCurrentDay)) finishedUrgentAssignments = true;
				currentSchedule.add(currentAssignment);
				priorityQueue.poll();
				currentAssignment = priorityQueue.peek();
			} while (currentAssignment != null &&
					 (!finishedUrgentAssignments || currentSchedule.size() < maximumAssignmentPerDay));
			schedule.add(currentSchedule);
		}
		return schedule;
	}

	public List<AssignmentProvider> getProviders() {
		return providers;
	}
}
