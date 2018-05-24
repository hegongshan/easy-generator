package com.hegongshan.easy.generator;

public enum Suffix {
	
	JAVA(".java"),XML(".xml"),TXT(".txt");
	
	private String name;
	
	private Suffix(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
