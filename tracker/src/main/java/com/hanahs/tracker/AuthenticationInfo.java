package com.hanahs.tracker;

import java.io.Serializable;

public class AuthenticationInfo implements Serializable {
	private static final long serialVersionUID = 1000L;
	private String username;
	private String authKey;

	public AuthenticationInfo(String username, String authKey) {
		this.username = username;
		this.authKey = authKey;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return authKey;
	}
}