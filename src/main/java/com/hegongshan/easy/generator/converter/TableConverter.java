package com.hegongshan.easy.generator.converter;

import java.util.LinkedHashSet;
import java.util.Set;

import com.hegongshan.easy.generator.entity.Column;
import com.hegongshan.easy.generator.entity.Entity;
import com.hegongshan.easy.generator.entity.Field;
import com.hegongshan.easy.generator.entity.Table;
import com.hegongshan.easy.generator.util.StringUtil;

public class TableConverter {
	public static Entity toJavaEntity(Table table) {
		Entity entity = new Entity();
		String entityName = StringUtil.firstToUpperCase(StringUtil.toJavaStyle(table.getTableName()));
		entity.setEntityName(entityName);
		Set<Field> fields = new LinkedHashSet<>();
		for(Column column : table.getColumns()) {
			Field field = toJavaField(column);
			fields.add(field);
		}
		entity.setFields(fields);
		return entity;
	}
	/**
	 * 把列对象转换为实体类属性对象
	 * @param column 需要转换的列对象
	 * @return 转换后的属性对象
	 */
	public static Field toJavaField(Column column) {
		String fieldName = StringUtil.toJavaStyle(column.getColumnName());
		Field field = new Field();
		field.setFieldName(fieldName);
		field.setFieldType(TypeConverter.toJavaType(column.getColumnType()));
		return field;
	}
	
	public static Field toJavaField(Field field,Column column) {
		String fieldName = StringUtil.toJavaStyle(column.getColumnName());
		field.setFieldName(fieldName);
		field.setFieldType(TypeConverter.toJavaType(column.getColumnType()));
		return field;
	}
	
	public static String toJavaEntityName(String tableName) {
		return StringUtil.firstToUpperCase(StringUtil.toJavaStyle(tableName));
	}
}
