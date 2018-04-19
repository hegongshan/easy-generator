package com.hegongshan.easy.generator.entity;

public class Field implements Comparable<Field>{

	private String fieldName;
	private String fieldType;
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	@Override
	public int compareTo(Field another) {
		return fieldName.compareTo(another.getFieldName());
	}

}
