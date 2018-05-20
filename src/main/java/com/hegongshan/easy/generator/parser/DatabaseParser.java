package com.hegongshan.easy.generator.parser;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.hegongshan.easy.generator.Constants;
import com.hegongshan.easy.generator.entity.Column;
import com.hegongshan.easy.generator.entity.Table;
import com.hegongshan.easy.generator.util.JdbcUtils;
import com.hegongshan.easy.generator.util.PropertiesUtils;
import com.hegongshan.easy.generator.util.StringUtils;

public class DatabaseParser {
	private List<Table> tables;
	
	public DatabaseParser() {
		try {
			init();
		} catch (Exception e) {
			new RuntimeException("[easy-generator]-数据库解析异常",e);
		}
	}
	
	private void init() throws Exception {
		DatabaseMetaData dmd = JdbcUtils.getConnection().getMetaData();

		tables = new ArrayList<Table>();
		String tableNamePattern = null;
		PropertiesUtils prop = new PropertiesUtils(Constants.DEFAULT_PROPERTIES);
		String tableNames = prop.getProperty("table");
		/**
		 * 如果没有配置table字段，或者table为*或%，则取全部的表，
		 * 如果table为以逗号分隔的多个字符串，则取指定的多个表，
		 * 如果table为指定的单个表，则取指定的某一个表
		 */
		if(StringUtils.isEmpty(tableNames) || tableNames.equals("*") || tableNames.equals("%")) { 
			tableNamePattern = "%";
			parse(dmd,tableNamePattern);
	    } else if(tableNames.contains(",")) {
			String[] tables = tableNames.split(",");
			for(int i = 0 ; i < tables.length ; i++) {
				parse(dmd,tables[i]);
			}
		} else {
			tableNamePattern = tableNames;
			parse(dmd,tableNamePattern);
		}
		setTables(tables);
		JdbcUtils.close();
	}
	
	private void parse(DatabaseMetaData dmd,String tableNamePattern) throws SQLException {
		ResultSet tableRS = dmd.getTables(null, null, tableNamePattern, new String[]{"TABLE"});
		while(tableRS.next()) {
			Table table = new Table();
			String dbTable = tableRS.getString("TABLE_NAME");
			String remarks = tableRS.getString("REMARKS");
			ResultSet columnRS = dmd.getColumns(null, null, dbTable , "%");
			table.setTableName(dbTable);
			table.setRemarks(remarks);
			
			Set<Column> columns = new LinkedHashSet<Column>();
			while(columnRS.next()) {
				Column column = new Column();
				column.setColumnType(columnRS.getInt("DATA_TYPE"));
				column.setColumnName(columnRS.getString("COLUMN_NAME"));
				column.setRemarks(columnRS.getString("REMARKS"));
				columns.add(column);
			}
			table.setColumns(columns);
			tables.add(table);
		}
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
