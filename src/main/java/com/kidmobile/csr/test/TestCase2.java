package com.kidmobile.csr.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.stereotype.Component;

public class TestCase2 {

	public interface Bird {};
	
	@Component
	public class Penguin implements Bird {};
	
	@Component
	public class Chicken implements Bird {};
	
	

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
