package com.hegongshan.easy.generator.log;

import java.util.logging.Level;

import com.hegongshan.easy.generator.Constants;

public class Logger {
	
	private final java.util.logging.Logger log;
	
	public Logger() {
		log = java.util.logging.Logger.getLogger(Constants.LOG_NAME);
	}
	
	public void error(String msg,Throwable thrown) {
		log.log(Level.SEVERE, msg, thrown);
	}
	
	public void error(String msg) {
		log.log(Level.SEVERE, msg);
	}
	
	public void info(String msg) {
		log.info(msg);
	}
	
	public void debug(String msg) {
		log.log(Level.CONFIG, msg);
	}
	
	public void warning(String msg) {
		log.warning(msg);
	}

}
