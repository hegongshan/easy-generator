package com.hegongshan.easy.generator.support.spring.jdbctemplate;

import com.hegongshan.easy.generator.converter.TableConverter;
import com.hegongshan.easy.generator.entity.Column;
import com.hegongshan.easy.generator.entity.Field;
import com.hegongshan.easy.generator.entity.Table;
import com.hegongshan.easy.generator.util.StringUtil;

public class RowMapperGenerator {
	
	public static String createRowMapperContent(String rowMapperPackage,String entityPackage,Table table) {

		String entityName = TableConverter.toJavaEntityName(table.getTableName());
		String instanceName = StringUtil.firstToLowerCase(entityName);
		StringBuilder content = new StringBuilder();
		content.append("package ").append(rowMapperPackage).append(";\n\n");
		content.append("import java.sql.ResultSet;\n");
		content.append("import java.sql.SQLException;\n\n");
		content.append("import org.springframework.jdbc.core.RowMapper;\n\n");
		content.append("import ").append(entityPackage).append(".").append(entityName).append(";\n\n");
		content.append("public class ").append(entityName).append("RowMapper").append(" implements RowMapper<").append(entityName).append("> {\n");
		
		//@Override
		//public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
		content.append("	").append("@Override\n");
		content.append("	").append("public ").
				append(entityName).
				append(" mapRow(ResultSet rs, int rowNum) throws SQLException {");
		//T t = new T();
		content.append("	    ").
				append(entityName).append(" ").append(instanceName).append(" = ").append("new ").append(entityName).append("();\n");
		//if(rs.findColumn("admin_id") != 0) {
		//	admin.setAdminId(rs.getInt("admin_id"));
		//}
		for(Column column : table.getColumns()) {
			content.append("		").append("if(rs.findColumn(\"").append(column.getColumnName()).append("\") != 0) {\n");
			//admin.setAdminId(rs.getInt("admin_id"));
			Field field = TableConverter.toJavaField(column);
			content.append("			").
					append(instanceName).
					append(".set").
					append(StringUtil.firstToUpperCase(field.getFieldName())).
					append("(");
			if(field.getFieldType().equals("java.util.Date")) {
				content.append("rs.getDate(\"");
			} else if(field.getFieldType().equals("Integer")){
				content.append("rs.getInt(\"");
			} else if(field.getFieldType().equals("Long")){
				content.append("rs.getLong(\"");
			} else if(field.getFieldType().equals("Boolean")){
				content.append("rs.getBoolean(\"");
			} else {
				content.append("rs.getString(\"");
			}
			content.append(column.getColumnName()).append("\"));\n");
			content.append("        }\n");
		}
		content.append("        ").append("return ").append(instanceName).append(";\n");
		content.append("	}\n");
		content.append("}\n");
		return content.toString();
	}
	
}
