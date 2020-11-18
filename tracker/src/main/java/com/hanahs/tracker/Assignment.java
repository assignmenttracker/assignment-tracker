package com.hanahs.tracker;

import java.time.LocalDateTime;

public class Assignment implements Comparable<Assignment> {
	private String name;
	private String description;
	private LocalDateTime due;
	
	public Assignment(String name, String description, LocalDateTime due) {
		this.name = name;
		this.description = description;
		this.due = due;
	}

	@Override
	public int compareTo(Assignment arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDue() {
		return due;
	}

}