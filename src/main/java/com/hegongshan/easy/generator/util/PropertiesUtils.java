package com.hegongshan.easy.generator.util;

import java.io.IOException;
import java.util.Properties;

import com.hegongshan.easy.generator.log.Logger;

public final class PropertiesUtils {
	private static final Properties PROP = new Properties();
	private static final Logger LOG = new Logger(PropertiesUtils.class);

	public PropertiesUtils(String properties) {
		init(properties);
	}

	private static void init(String properties) {
		try {
			PROP.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(properties));
		} catch (IOException e) {
			LOG.error("导入配置文件发生异常！", e);
		}
	}

	public String getProperty(String key) {
		return PROP.getProperty(key);
	}
}
