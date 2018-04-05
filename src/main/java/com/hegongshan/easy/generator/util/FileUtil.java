package com.hegongshan.easy.generator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtil {
	/**
	 * 写实体类到指定包名下
	 * @param packageName 实体类包名
	 * @param fileName 实体类名
	 * @param content 实体类的内容
	 */
	public static void write(String packageName,String fileName,String content) {
		String filePath = packageName.replace(".", "/");
		File file = new File("src/"+filePath+File.separator+fileName);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(content);
			out.flush();
		} catch (IOException e) {
			new RuntimeException("[easy]-写文件异常",e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {}
		}
	}
}
