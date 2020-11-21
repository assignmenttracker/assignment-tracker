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
		LocalDate startDate0 = arg0.getEstimatedStartDate();
		LocalDate startDate1 = arg1.getEstimatedStartDate();
		boolean arg0ShouldBeStarted = startDate0.isBefore(pivotDate);
		boolean arg1ShouldBeStarted = startDate1.isBefore(pivotDate);
		if (arg0ShouldBeStarted && arg1ShouldBeStarted) {
			if (arg0.getDue().isBefore(arg1.getDue())) return -1;
			else if (arg0.getDue().isAfter(arg1.getDue())) return 1;

			if (startDate0.isBefore(startDate1)) return -1;
			else if (startDate0.equals(startDate1)) return 0;
			else return 1;
		}
		else if (arg0ShouldBeStarted) return -1;
		else if (arg1ShouldBeStarted) return 1;
		if (arg0.getEstimatedStartDate().isBefore(arg1.getEstimatedStartDate())) return -1;
		else if (arg0.getEstimatedStartDate().equals(arg1.getEstimatedStartDate())) return 0;
		else return 1;
	}

	public LocalDate getPivotDate() {
		return pivotDate;
	}
}
