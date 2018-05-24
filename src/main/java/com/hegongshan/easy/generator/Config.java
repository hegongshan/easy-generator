package com.hegongshan.easy.generator;

import com.hegongshan.easy.generator.log.Logger;
import com.hegongshan.easy.generator.util.PropertiesUtils;
import com.hegongshan.easy.generator.util.StringUtils;

public class Config {
	
	private static final Logger LOG = new Logger(Config.class);
	
	private static PropertiesUtils prop = new PropertiesUtils(Constants.DEFAULT_PROPERTIES);

	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;
	private static String entityPackage;
	
	public static String getDriverClassName() {
		driverClassName = prop.getProperty("driverClassName");
		requireNotNull("driverClassName",driverClassName);
		return driverClassName;
	}

	public static String getUrl() {
		url = prop.getProperty("url");
		requireNotNull("url",url);
		return url;
	}

	public static String getUsername() {
		username = prop.getProperty("username");
		requireNotNull("username", username);
		return username;
	}

	public static String getPassword() {
		password = prop.getProperty("password");
		requireNotNull("password", password);
		return password;
	}

	public static String getEntityPackage() {
		entityPackage = prop.getProperty("entityPackage");
		requireNotNull("entityPackage", entityPackage);
		return entityPackage;
	}

	public static String getRowMapperPackage() {
		return prop.getProperty("rowMapperPackage");
	}

	public static boolean isIgnoreComment() {
		String rowMapperPackage = prop.getProperty("rowMapperPackage");
		if(StringUtils.isEmpty(rowMapperPackage) || rowMapperPackage.trim() != String.valueOf(true)) {
			return false;
		} else {
			return true;
		}
	}

	public static String getTable() {
		return prop.getProperty("table");
	}
	
	private static void requireNotNull(String name,String value) {
		if(value == null) {
			LOG.error(Constants.DEFAULT_PROPERTIES+"中"+name+"不能为空", new NullPointerException());
		}
	}

}
