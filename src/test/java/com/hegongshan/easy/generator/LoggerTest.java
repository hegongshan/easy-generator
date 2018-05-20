package com.hegongshan.easy.generator;

import org.junit.Test;

import com.hegongshan.easy.generator.log.Logger;

public class LoggerTest {
	private static final Logger LOG = new Logger();
	@Test
	public void testInfo() {
		LOG.info("你好");
	}
	
	@Test
	public void testError() {
		try{
			int i = 1 / 0;
			System.out.println(i);
		} catch(Exception e) {
			LOG.error("被除數不能為零",e);
		}
	}
	
	@Test
	public void testWarning() throws ClassNotFoundException {
		Class c = Class.forName("com.hegongshan.easy.generator.LoggerTest");
		LOG.warning("Class is a raw type. References to generic type Class<T> should be parameterized");
	}
}
