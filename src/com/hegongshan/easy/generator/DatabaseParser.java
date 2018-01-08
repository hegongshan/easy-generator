package com.hegongshan.easy.generator;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hegongshan.easy.generator.entity.Table;
import com.hegongshan.easy.generator.util.JdbcUtil;
import com.hegongshan.easy.generator.util.StringUtil;

public class DatabaseParser {
	private List<Table> tables;
	
	public DatabaseParser() {
		try {
			init();
		} catch (Exception e) {
			new RuntimeException("[easy-generator] 数据库解析异常",e);
		}
	}
	
	private void init() throws Exception {
		DatabaseMetaData mdm = JdbcUtil.getConnection().getMetaData();
		ResultSet tableRS = mdm.getTables(null, null, "%", new String[]{"TABLE"});
		int totalTable = tableRS.getMetaData().getColumnCount();
		tables = new ArrayList<Table>(totalTable);
		while(tableRS.next()) {
			Table table = new Table();
			String dbTable = tableRS.getString("TABLE_NAME");
			ResultSet columnRS = mdm.getColumns(null, null, dbTable , "%");
			table.setTableName(StringUtil.toJavaStyle(dbTable));

			Map<String,String> columns = new HashMap<String,String>();
			while(columnRS.next()) {
				columns.put(StringUtil.toJavaStyle(columnRS.getString("COLUMN_NAME")),TypeConvertor.toJavaType(columnRS.getString("TYPE_NAME").toLowerCase()));
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
