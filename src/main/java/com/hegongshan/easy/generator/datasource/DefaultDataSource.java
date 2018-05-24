package com.hegongshan.easy.generator.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.hegongshan.easy.generator.Config;

public class DefaultDataSource implements DataSource {
	
	private static final com.hegongshan.easy.generator.log.Logger LOG = 
			new com.hegongshan.easy.generator.log.Logger(DefaultDataSource.class);
	
	private static List<Connection> pool;
	private int poolMinSize = 5;
	private int poolMaxSize = 20;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	public DefaultDataSource(boolean pooled) {
		driverClassName = Config.getDriverClassName();
		url = Config.getUrl();
		username = Config.getUsername();
		password = Config.getPassword();
		try {
			init();
		} catch (SQLException e) {
			LOG.error("数据库连接池初始化时发生异常",e);
		}
	}
	
	public DefaultDataSource() {
		this(false);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		DriverManager.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException(getClass().getName() + " is not a wrapper.");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		if(pool == null) {
			init();
		}
		int lastIndex = pool.size() - 1;
		Connection connection = pool.get(lastIndex);
		pool.remove(lastIndex);
		return connection;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}
	
	public synchronized void close(Connection connection) {
		if(pool.size() > poolMaxSize) {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LOG.error("关闭数据库连接时发生异常",e);
				}
			}
		} else {
			if(connection != null) {
				pool.add(connection);
			}
		}
	}
	
	private void init() throws SQLException {
		if(pool == null) {
			pool = new ArrayList<Connection>(poolMinSize);
		}
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			LOG.error("找不到数据库驱动类",e);
		}
		while(pool.size() < poolMinSize) {
			pool.add(DriverManager.getConnection(url, username, password));
		}
	}

}
