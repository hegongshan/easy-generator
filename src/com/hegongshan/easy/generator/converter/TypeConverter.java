package com.hegongshan.easy.generator;

public class TypeConvertor {
	public static String toJavaType(String dbType) {
		if (equalsIgnoreCase(dbType, "integer", "int", "tinyint", "smallint", "int")) {
			return "Integer";
		} else if (equalsIgnoreCase(dbType,"mediumint", "bigint")) {
			return "java.math.BigInteger";
		} else if (equalsIgnoreCase(dbType,  "real", "float", "double")) {
			return "Double";
		} else if (dbType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (equalsIgnoreCase(dbType,  "tinytext", "text", "mediumtext", "longtext", "varchar",
				"longchar", "char", "clob", "tinyclob", "mediumclob", "longclob")) {
			return "String";
		} else if (equalsIgnoreCase(dbType,  "date", "time", "year", "datetime", "timestamp")) {
			return "java.util.Date";
		} else if (equalsIgnoreCase(dbType,  "decimal", "numeric")) {
			return "java.math.BigDecimal";
		} else if (equalsIgnoreCase(dbType,
				 "binary", "varbinary", "longbinary", "blob", "tinyblob", "mediumblob", "longblob" )) {
			return "Byte[]";
		} else {
			return "Object";
		}
	}

	private static boolean equalsIgnoreCase(String dbType, String ... array) {
		boolean flag = false;
		for (String s : array) {
			flag = dbType.equalsIgnoreCase(s);
			if (flag == true) {
				break;
			}
		}
		return flag;
	}
}
