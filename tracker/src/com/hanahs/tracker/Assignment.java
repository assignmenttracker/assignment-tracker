package com.hanahs.tracker;

public class Assignment implements Comparable<Assignment> {
	private String name;
	private String description;
	
	public Assignment(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public int compareTo(Assignment arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
