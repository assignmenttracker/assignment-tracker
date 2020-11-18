package com.hanahs.tracker;

import java.time.LocalDateTime;

public class Assignment {
	private String name;
	private String description;
	private LocalDateTime due;
	
	public Assignment(String name, String description, LocalDateTime due) {
		this.name = name;
		this.description = description;
		this.due = due;
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