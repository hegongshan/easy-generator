package com.hegongshan.easy.generator.util;

public final class StringUtil {
	public static String toJavaStyle(String column) {
		String field = "";
		if (column.startsWith("t_")) {
			column = column.substring(column.indexOf("t_") + 2);
		} else if (column.startsWith("tb_")) {
			column = column.substring(column.indexOf("tb_") + 3);
		}
		column = column.toLowerCase();
		String[] array = column.split("_");
		for (int i = 0 ; i < array.length ; i++) {
			if(i!=0) {
				field += firstToUpperCase(array[i]);
			} else {
				field += array[i];
			}
		}
		return field;
	}

	public static String firstToUpperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
