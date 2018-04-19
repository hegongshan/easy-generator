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
import com.hegongshan.easy.generator.util.FileUtil;
import com.hegongshan.easy.generator.util.PropertiesUtil;
import com.hegongshan.easy.generator.util.StringUtil;

public class Generator {

	private static final Logger LOG = Logger.getLogger(Generator.class.getName());
	
	private static PropertiesUtil prop = new PropertiesUtil(Constants.DEFAULT_PROPERTIES);
	
	private static String entityPackage = prop.getProperty("entityPackage");

	private static String rowMapperPackage = prop.getProperty("rowMapperPackage");
	
	public static void generate() {
		List<Table> tables = new DatabaseParser().getTables();
		
		boolean generateRowMapper = false;
		if(StringUtil.isNotEmpty(rowMapperPackage))
			generateRowMapper = true;
		for (Table table : tables) {
			String entityName = TableConverter.toJavaEntityName(table.getTableName());
			LOG.info("[easy-generator] 开始写" + entityName + ".java");
			FileUtil.write(entityPackage, entityName + ".java",
					createEntityContent(table));
			if(generateRowMapper) {
				LOG.info("[easy-generator] 开始写" + entityName + "RowMapper.java");
				String rowMapperFileName = entityName + "RowMapper.java";
				FileUtil.write(rowMapperPackage,
						rowMapperFileName,
						RowMapperGenerator.createRowMapperContent(rowMapperPackage, entityPackage, table));
			}
		}
	}

	private static String createEntityContent(Table table) {
		String className = StringUtil.firstToUpperCase(StringUtil.toJavaStyle(table.getTableName()));
		StringBuilder content = new StringBuilder();
		content.append("package " + entityPackage + ";\n");
		content.append("\n");
		content.append("public class " + className + " implements java.io.Serializable {\n");
		content.append("\n");
		content.append("	private static final long serialVersionUID = 1L;\n");
		content.append("\n");
		
		Set<Column> columns = table.getColumns();
		Field field;
		for(Column column : columns) {
			field = TableConverter.toJavaField(column);
			content.append("	private " + field.getFieldType() + " " + field.getFieldName() + ";\n");
		}
		content.append("\n");
		
		for(Column column : columns) {
			field = TableConverter.toJavaField(column);
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
		for(Column column : columns) {
			field = TableConverter.toJavaField(column);
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
