package com.hegongshan.easy.generator.entity;

import java.util.Set;

public class Entity {
	private String entityName;
	private Set<Field> fields;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Set<Field> getFields() {
		return fields;
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}

}
