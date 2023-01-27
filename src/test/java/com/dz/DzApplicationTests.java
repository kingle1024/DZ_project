package com.dz;

import com.dz.util.EncryptHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DzApplicationTests {
	@Autowired
	private EncryptHelper encryptHelper;

	@Test
	void contextLoads() {
		String password = "JasonPassword!";
		String encrypted = encryptHelper.encrypt(password);
		System.out.println("encrypted = " + encrypted);
		assertTrue(encryptHelper.isMatch(password, encrypted));
	}

}
