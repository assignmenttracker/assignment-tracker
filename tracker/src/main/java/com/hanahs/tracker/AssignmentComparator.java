package com.hanahs.tracker;

import java.time.LocalDate;
import java.util.Comparator;

public class AssignmentComparator implements Comparator<Assignment> {
	private LocalDate pivotDate;
	
	public AssignmentComparator(LocalDate pivotDate) {
		this.pivotDate = pivotDate;
	}
	
	public AssignmentComparator() {
		this(LocalDate.now());
	}
	
	@Override
	public int compare(Assignment arg0, Assignment arg1) {
		if (arg0.equals(arg1)) return 0;
		boolean arg0ShouldBeStarted = arg0.getEstimatedStartDate().isBefore(pivotDate);
		boolean arg1ShouldBeStarted = arg1.getEstimatedStartDate().isBefore(pivotDate);
		if (arg0ShouldBeStarted && arg1ShouldBeStarted) return 0;
		else if (arg0ShouldBeStarted) return -1;
		else if (arg1ShouldBeStarted) return 1;
		if (arg0.getEstimatedStartDate().isBefore(arg1.getEstimatedStartDate())) return 1;
		else if (arg0.getEstimatedStartDate().equals(arg1.getEstimatedStartDate())) return 0;
		else return -1;
	}

	public LocalDate getPivotDate() {
		return pivotDate;
	}
}
