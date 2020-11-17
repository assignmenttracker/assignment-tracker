package com.hanahs.tracker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthenticationInfoTest {

	@Test
	void test(){
		AuthenticationInfo info = new AuthenticationInfo("testuser", "key");
		assertNotNull(info);
		assertEquals(info.getUsername(), "testuser");
		assertEquals(info.getPassword(), "key");
	}

}
