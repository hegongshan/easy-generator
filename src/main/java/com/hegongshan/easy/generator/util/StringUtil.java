package com.hegongshan.easy.generator.util;

public final class StringUtil {
	public static String toJavaStyle(String column) {
		StringBuilder field = new StringBuilder();
		if (column.startsWith("t_")) {
			column = column.substring(column.indexOf("t_") + 2);
		} else if (column.startsWith("tb_")) {
			column = column.substring(column.indexOf("tb_") + 3);
		}
		column = column.toLowerCase();
		String[] array = column.split("_");
		for (int i = 0 ; i < array.length ; i++) {
			if(i!=0) {
				field.append(firstToUpperCase(array[i]));
			} else {
				field.append(array[i]);
			}
		}
		System.out.println(field);
		return field.toString();
	}

	public static String firstToUpperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public static String firstToLowerCase(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static boolean equalsIgnoreCase(String source, String ... array) {
		boolean flag = false;
		for (String s : array) {
			flag = source.equalsIgnoreCase(s);
			if (flag == true) {
				break;
			}
		}
		return flag;
	}
	
	public static boolean equals(String source, String ... array) {
		boolean flag = false;
		for (String s : array) {
			flag = source.equals(s);
			if (flag == true) {
				break;
			}
		}
		return flag;
	}
}
