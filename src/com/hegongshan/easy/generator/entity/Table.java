package com.hegongshan.easy.generator.entity;

import java.util.Map;

public class Table {
	private String tableName;
	Map<String, String> columns; //Map<columnName,columnType>
	//private Column column;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}
}
