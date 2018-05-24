package com.hegongshan.easy.generator.converter;

import java.sql.Types;

public class TypeConverter {

	public static String toJavaType(int jdbcType) {
		String javaType = null;
		switch (jdbcType) {
		case Types.SMALLINT:
		case Types.INTEGER:
			javaType = "Integer";break;
		case Types.FLOAT:
		case Types.DOUBLE:
			javaType = "Double";break;
		case Types.BIGINT:
			javaType = "Long";break;
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
		case Types.TIMESTAMP_WITH_TIMEZONE:
			javaType = "java.util.Date";break;
		case Types.BIT:
			javaType = "Boolean";break;
		case Types.CHAR:
		case Types.NCHAR:
		case Types.VARCHAR:
		case Types.NVARCHAR:
		case Types.LONGVARCHAR:
		case Types.LONGNVARCHAR:
		case Types.CLOB:
		case Types.NCLOB:
			javaType = "String";break;
		case Types.BINARY:
		case Types.BLOB:
			javaType = "Byte[]";break;
		case Types.DECIMAL:
		case Types.NUMERIC:
			javaType = "java.math.BigDecimal";break;
		default:
			javaType = "Object";
		}
		return javaType;
	}

}
