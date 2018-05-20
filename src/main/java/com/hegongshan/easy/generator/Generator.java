package com.hegongshan.easy.generator;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.hegongshan.easy.generator.converter.TableConverter;
import com.hegongshan.easy.generator.entity.Column;
import com.hegongshan.easy.generator.entity.Field;
import com.hegongshan.easy.generator.entity.Table;
import com.hegongshan.easy.generator.parser.DatabaseParser;
import com.hegongshan.easy.generator.support.spring.jdbctemplate.RowMapperGenerator;
import com.hegongshan.easy.generator.util.FileUtils;
import com.hegongshan.easy.generator.util.PropertiesUtils;
import com.hegongshan.easy.generator.util.StringUtils;

public class Generator {

	private static final Logger LOG = Logger.getLogger(Generator.class.getName());
	
	private static PropertiesUtils prop = new PropertiesUtils(Constants.DEFAULT_PROPERTIES);
	
	private static String entityPackage = prop.getProperty("entityPackage");

	private static String rowMapperPackage = prop.getProperty("rowMapperPackage");
	
	private static String ignoreComment = prop.getProperty("ignoreComment");
	
	public static void generate() {
		List<Table> tables = new DatabaseParser().getTables();
		
		boolean generateRowMapper = false;
		if(StringUtils.isNotEmpty(rowMapperPackage)) {
			generateRowMapper = true;
		}
		for (Table table : tables) {
			String entityName = TableConverter.toJavaEntityName(table.getTableName());
			LOG.info("[easy-generator] 开始写" + entityName + ".java");
			FileUtils.write(entityPackage, entityName + ".java",
					createEntityContent(table));
			if(generateRowMapper) {
				LOG.info("[easy-generator] 开始写" + entityName + "RowMapper.java");
				String rowMapperFileName = entityName + "RowMapper.java";
				FileUtils.write(rowMapperPackage,
						rowMapperFileName,
						RowMapperGenerator.createRowMapperContent(rowMapperPackage, entityPackage, table));
			}
		}
	}

	private static String createEntityContent(Table table) {
		boolean ignoreComment = false;
		if(StringUtils.isNotEmpty(Generator.ignoreComment) && Generator.ignoreComment.equals("true")) {
			ignoreComment = true;
		}
		String className = StringUtils.firstToUpperCase(StringUtils.toJavaStyle(table.getTableName()));
		StringBuilder content = new StringBuilder();
		content.append("package " + entityPackage + ";\n");
		content.append("\n");
		if(!ignoreComment && StringUtils.isNotEmpty(table.getRemarks())) {
			content.append("//" + table.getRemarks() + "\n");
		}
		content.append("public class " + className + " implements java.io.Serializable {\n");
		content.append("\n");
		content.append("	private static final long serialVersionUID = 1L;\n");
		content.append("\n");
		
		Set<Column> columns = table.getColumns();
		Field field;
		for(Column column : columns) {
			field = TableConverter.toJavaField(column);
			if(!ignoreComment && StringUtils.isNotEmpty(column.getRemarks())) {
				content.append("	//" + column.getRemarks() + "\n");
			}
			if(column.getColumnName().startsWith("is_")) {
				content.append("	private " + field.getFieldType() + " " + StringUtils.firstToLowerCase(field.getFieldName().substring(2)) + ";\n");
			} else {
				content.append("	private " + field.getFieldType() + " " + field.getFieldName() + ";\n");
			}
		}
		content.append("\n");
		
		for(Column column : columns) {
			field = TableConverter.toJavaField(column);
			if(column.getColumnName().startsWith("is_")) {
				String fieldName = StringUtils.firstToLowerCase(field.getFieldName().substring(2));
				content.append("	public " + field.getFieldType() + " get" + StringUtils.firstToUpperCase(fieldName) + "() {\n");
				content.append("		return " + fieldName + ";\n");
				content.append("	}\n");
				content.append("\n");
				content.append("	public void " + field.getFieldName() + "("
						+ field.getFieldType() + " " + fieldName + ")" + " {\n");
				content.append("		this." + fieldName + " = " + fieldName + ";\n");
				content.append("	}\n");
				content.append("\n");
			} else {
				content.append("	public " + field.getFieldType() + " get" + StringUtils.firstToUpperCase(field.getFieldName()) + "() {\n");
				content.append("		return " + field.getFieldName() + ";\n");
				content.append("	}\n");
				content.append("\n");
				content.append("	public void set" + StringUtils.firstToUpperCase(field.getFieldName()) + "("
						+ field.getFieldType() + " " + field.getFieldName() + ")" + " {\n");
				content.append("		this." + field.getFieldName() + " = " + field.getFieldName() + ";\n");
				content.append("	}\n");
				content.append("\n");
			}
		}
		
		//return "Article [articleTitle=" + articleTitle + 
		//", gmtModify=" + gmtModify + ", articleClicks=" + articleClicks + "]";
		content.append("    @Override\n");
		content.append("	public String toString() {\n");
		content.append("		return \"").append(className).append(" [");
		int count = 1;
		for(Column column : columns) {
			field = TableConverter.toJavaField(column);
			if(count != 1)
				content.append(" + \", ");
			if(column.getColumnName().startsWith("is_")) {
				String fieldName = StringUtils.firstToLowerCase(field.getFieldName().substring(2));
				content.append(fieldName)
					   .append("=\" + ")
					   .append(fieldName);
			} else {
				content.append(field.getFieldName())
					   .append("=\" + ")
					   .append(field.getFieldName());
			}
			if(count % 3 == 0)
				content.append("\n			");
			count++;
		}
		content.append(" + \"]\";\n");
		
		content.append("	}\n");
		content.append("\n");
		
		content.append("}\n");

		return content.toString();
	}
/*	
	private static String createEntityContent(Entity entity) {
		String className = entity.getEntityName();
		StringBuilder content = new StringBuilder();
		content.append("package " + entityPackage + ";\n");
		content.append("\n");
		content.append("public class " + className + " implements java.io.Serializable {\n");
		content.append("\n");
		content.append("	private static final long serialVersionUID = 1L;\n");
		content.append("\n");
		
		Set<Field> fields = entity.getFields();
		for(Field field : fields) {
			content.append("	private " + field.getFieldType() + " " + field.getFieldName() + ";\n");
		}
		content.append("\n");
		
		for(Field field : fields) {
			content.append("	public " + field.getFieldType() + " get" + StringUtil.firstToUpperCase(field.getFieldName()) + " () {\n");
			content.append("		return " + field.getFieldName() + ";\n");
			content.append("	}\n");
			content.append("\n");
			content.append("	public void set" + StringUtil.firstToUpperCase(field.getFieldName()) + "("
					+ field.getFieldType() + " " + field.getFieldName() + ")" + " {\n");
			content.append("		this." + field.getFieldName() + " = " + field.getFieldName() + ";\n");
			content.append("	}\n");
			content.append("\n");
		}
		
		//return "Article [articleTitle=" + articleTitle + 
		//", gmtModify=" + gmtModify + ", articleClicks=" + articleClicks + "]";
		content.append("    @Override\n");
		content.append("	public String toString() {\n");
		content.append("		return \"").append(className).append(" [");
		int count = 1;
		for(Field field : fields) {
			if(count != 1)
				content.append(" + \", ");
			content.append(field.getFieldName()).
					append("=\" + ").
					append(field.getFieldName());
			if(count % 3 == 0)
				content.append("\n			");
			count++;
		}
		content.append(" + \"]\";\n");
		
		content.append("	}\n");
		content.append("\n");
		
		content.append("}\n");

		return content.toString();
	}*/
}
