package com.hegongshan.easy.generator.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.hegongshan.easy.generator.datasource.DefaultDataSource;

public final class JdbcUtil {
	private static Connection conn ;
	private static DefaultDataSource ds = new DefaultDataSource();
	
	public static Connection getConnection() {
		try {
			conn = ds.getConnection();
			return conn;
		} catch (SQLException e) {
			new RuntimeException("[easy-generator] 获取连接发生异常");
			return null;
		}
	}
	
	public static void close() {
		ds.close(conn);
	}
}
