package com.hegongshan.easy.generator.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.hegongshan.easy.generator.datasource.DefaultDataSource;
import com.hegongshan.easy.generator.log.Logger;

public final class JdbcUtils {
	
	private static Connection conn ;
	private static DefaultDataSource ds = new DefaultDataSource();
	private static final Logger LOG = new Logger(JdbcUtils.class);
	
	public static Connection getConnection() {
		try {
			if(conn == null) {
				conn = ds.getConnection();
			}
			return conn;
		} catch (SQLException e) {
			LOG.error("获取连接发生异常",e);
			return null;
		}
	}
	
	public static void close() {
		ds.close(conn);
	}
}
