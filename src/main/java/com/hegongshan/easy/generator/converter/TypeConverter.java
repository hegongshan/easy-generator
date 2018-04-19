package com.hegongshan.easy.generator.converter;

import com.hegongshan.easy.generator.util.StringUtil;

public class TypeConverter {
	public static String toJavaType(String dbType) {
		if (StringUtil.equalsIgnoreCase(dbType, "integer", "int", "tinyint", "smallint", "int")) {
			return "Integer";
		} else if (StringUtil.equalsIgnoreCase(dbType,"mediumint", "bigint")) {
			return "java.math.BigInteger";
		} else if (StringUtil.equalsIgnoreCase(dbType,  "real", "float", "double")) {
			return "Double";
		} else if (dbType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (StringUtil.equalsIgnoreCase(dbType,  "tinytext", "text", "mediumtext", "longtext", "varchar",
				"longchar", "char", "clob", "tinyclob", "mediumclob", "longclob")) {
			return "String";
		} else if (StringUtil.equalsIgnoreCase(dbType,  "date", "time", "year", "datetime", "timestamp")) {
			return "java.util.Date";
		} else if (StringUtil.equalsIgnoreCase(dbType,  "decimal", "numeric")) {
			return "java.math.BigDecimal";
		} else if (StringUtil.equalsIgnoreCase(dbType,
				 "binary", "varbinary", "longbinary", "blob", "tinyblob", "mediumblob", "longblob" )) {
			return "Byte[]";
		} else {
			return "Object";
		}
	}

}
