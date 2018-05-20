package com.hegongshan.easy.generator.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hegongshan.easy.generator.util.StringUtils;

public class Template {
	
	private static final String VAR_TAG = "\\$\\{\\s*[a-zA-Z\\.]+(\\?[a-zA-Z]+)?\\s*\\}";
	
	private static final String FOREACH_TAG = "<\\s*foreach\\s+\\w+\\s*:\\s*[\\w\\.]+\\s*>[\\s\\S]+<\\s*\\/\\s*foreach\\s*>";//
	
	private String templatePath;
	public Template(String templatePath) {
		this.templatePath = templatePath;
	}
	public String readTemplate() throws IOException {
		
		String temp;
		StringBuilder content = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new FileReader(new File(templatePath)));
		while((temp = br.readLine()) != null) {
			content.append(temp+"\n");
		}
		br.close();
		return content.toString();
	}
	
	public String render(String template,Map<String,Object> data) {
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("(" + FOREACH_TAG + ")|(" + VAR_TAG + ")");
		Matcher matcher = pattern.matcher(template);
		String variable;
		while(matcher.find()) {
			variable = matcher.group();
			String key = variable.substring(2,variable.length()-1).trim();//${  }
			String value = null;
			if(key.contains("?")) {
				String[] arr = key.split("\\?");
				if(arr[1].equals("firstToUpperCase")) {
					value = StringUtils.firstToUpperCase(String.valueOf(data.get(arr[0])));
				}
			} else {
				value = String.valueOf(data.get(key));
			}
			matcher.appendReplacement(sb, value);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		Template template = new Template("./src/main/resources/com/hegongshan/easy/generator/template/Entity.txt");
		Map<String,Object> data = new HashMap<>();
		data.put("entityPackage", "com.hegongshan.easy.generator.model");
		data.put("entityClassName", "course");
		String content = template.render(template.readTemplate(),data);
		System.out.println(content);
	}

}
