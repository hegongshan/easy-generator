package com.hegongshan.easy.generator.parser;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.hegongshan.easy.generator.Constants;
import com.hegongshan.easy.generator.entity.Column;
import com.hegongshan.easy.generator.entity.Table;
import com.hegongshan.easy.generator.util.JdbcUtil;
import com.hegongshan.easy.generator.util.PropertiesUtil;
import com.hegongshan.easy.generator.util.StringUtil;

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
		DatabaseMetaData dmd = JdbcUtil.getConnection().getMetaData();
		
		String tableNamePattern = null;
		
		PropertiesUtil prop = new PropertiesUtil(Constants.DEFAULT_PROPERTIES);
		String tableNames = prop.getProperty("table");
		if(StringUtil.isEmpty(tableNames) || tableNames.equals("*") || tableNames.equals("%")) 
			tableNamePattern = "%";
		else {
			tableNamePattern = tableNames;
		}
		ResultSet tableRS = dmd.getTables(null, null, tableNamePattern, new String[]{"TABLE"});
		int totalTable = tableRS.getMetaData().getColumnCount();
		tables = new ArrayList<Table>(totalTable);
		while(tableRS.next()) {
			Table table = new Table();
			String dbTable = tableRS.getString("TABLE_NAME");
			ResultSet columnRS = dmd.getColumns(null, null, dbTable , "%");
			table.setTableName(dbTable);

			Set<Column> columns = new LinkedHashSet<Column>();
			while(columnRS.next()) {
				Column column = new Column();
				column.setColumnType(columnRS.getString("TYPE_NAME").toLowerCase());
				column.setColumnName(columnRS.getString("COLUMN_NAME"));
				columns.add(column);
			}
			table.setColumns(columns);
			tables.add(table);
		}
		setTables(tables);
		JdbcUtil.close();
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
