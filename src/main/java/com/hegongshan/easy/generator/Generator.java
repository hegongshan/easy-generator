package com.hegongshan.easy.generator;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import com.hegongshan.easy.generator.entity.Table;
import com.hegongshan.easy.generator.util.FileUtil;
import com.hegongshan.easy.generator.util.PropertiesUtil;
import com.hegongshan.easy.generator.util.StringUtil;

public class Generator {

	private static final Logger LOG = Logger.getLogger(Generator.class.getName());
	
	private static String entityPackage = new PropertiesUtil("easy.properties").getProperty("entityPackage");

	public static void generateEntity() {
		List<Table> tables = new DatabaseParser().getTables();
		for (Table table : tables) {
			LOG.info("[easy-generator] 开始写" + StringUtil.firstToUpperCase(table.getTableName()) + ".java");
			FileUtil.write(entityPackage, StringUtil.firstToUpperCase(table.getTableName()) + ".java",
					createEntityContent(table));
		}
	}

	private static String createEntityContent(Table table) {

		StringBuilder content = new StringBuilder();
		content.append("package " + entityPackage + ";\n");
		content.append("\n");
		content.append("public class " + StringUtil.firstToUpperCase(table.getTableName()) + " implements java.io.Serializable {\n");
		content.append("\n");
		content.append("	private static final long serialVersionUID = 1L;\n");
		content.append("\n");
		
		Set<Entry<String, String>> s = table.getColumns().entrySet();
		Iterator<Entry<String, String>> iter = s.iterator();
		while (iter.hasNext()) {
			Entry<String, String> e = iter.next();
			content.append("	private " + e.getValue() + " " + e.getKey() + ";\n");
		}
		content.append("\n");

		Iterator<Entry<String, String>> iter2 = s.iterator();
		while (iter2.hasNext()) {
			Entry<String, String> e = iter2.next();
			content.append("	public " + e.getValue() + " get" + StringUtil.firstToUpperCase(e.getKey()) + " () {\n");
			content.append("		return " + e.getKey() + ";\n");
			content.append("	}\n");
			content.append("\n");
			content.append("	public void set" + StringUtil.firstToUpperCase(e.getKey()) + "("
					+ e.getValue() + " " + e.getKey() + ")" + " {\n");
			content.append("		this." + e.getKey() + " = " + e.getKey() + ";\n");
			content.append("	}\n");
		}

		content.append("}");

		return content.toString();
	}
}
