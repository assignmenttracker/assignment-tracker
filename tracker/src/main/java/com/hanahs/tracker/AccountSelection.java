package com.hanahs.tracker;

public class AccountSelection {
	private String accountType;
	private final static AccountSelection INSTANCE = new AccountSelection();
	
	private AccountSelection() {}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public static AccountSelection getInstance() {
		return INSTANCE;
	}
}
