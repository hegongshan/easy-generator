package com.hegongshan.easy.generator.entity;

import java.util.Set;

public class Table {
	private String tableName;

	private Set<Column> columns;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public void setColumns(Set<Column> columns) {
		this.columns = columns;
	}
	
}
