package com.hegongshan.easy.generator.converter;

import java.sql.Types;

import com.hegongshan.easy.generator.util.StringUtils;

public class TypeConverter {
	public static String toJavaType(String dbType) {
		if (StringUtils.equalsIgnoreCase(dbType, "integer", "int", "tinyint", "smallint")) {
			return "Integer";
		} else if (StringUtils.equalsIgnoreCase(dbType,"mediumint", "bigint")) {
			return "java.math.BigInteger";
		} else if (StringUtils.equalsIgnoreCase(dbType,  "real", "float", "double")) {
			return "Double";
		} else if (dbType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (StringUtils.equalsIgnoreCase(dbType,  "tinytext", "text", "mediumtext", "longtext", "varchar",
				"longchar", "char", "clob", "tinyclob", "mediumclob", "longclob")) {
			return "String";
		} else if (StringUtils.equalsIgnoreCase(dbType,  "date", "time", "year", "datetime", "timestamp")) {
			return "java.util.Date";
		} else if (StringUtils.equalsIgnoreCase(dbType,  "decimal", "numeric")) {
			return "java.math.BigDecimal";
		} else if (StringUtils.equalsIgnoreCase(dbType,
				 "binary", "varbinary", "longbinary", "blob", "tinyblob", "mediumblob", "longblob" )) {
			return "Byte[]";
		} else {
			return "Object";
		}
	}
	
	public static String toJavaType(int jdbcType) {
		String javaType = null;
		switch(jdbcType) {
			case Types.SMALLINT:
			case Types.INTEGER:
				javaType = "Integer";
				break;
			case Types.FLOAT:
			case Types.DOUBLE:
				javaType = "Double";
				break;
			case Types.BIGINT:
				javaType = "Long";
				break;
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
			case Types.TIMESTAMP_WITH_TIMEZONE:
				javaType = "java.util.Date";
				break;
			case Types.BIT:
				javaType = "Boolean";
				break;
			case Types.CHAR:
			case Types.NCHAR:
			case Types.VARCHAR:
			case Types.NVARCHAR:
			case Types.LONGVARCHAR:
			case Types.LONGNVARCHAR:
			case Types.CLOB:
			case Types.NCLOB:
				javaType = "String";
				break;
			case Types.BINARY:
			case Types.BLOB:
				javaType = "Byte[]";
				break;
			case Types.DECIMAL:
			case Types.NUMERIC:
				javaType = "java.math.BigDecimal";
				break;
			default:
				javaType = "Object";
		}
		return javaType;
	}

}
