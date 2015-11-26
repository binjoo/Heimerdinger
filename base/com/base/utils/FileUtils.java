package com.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 文件操作类
 * 
 * @author liangj
 * 
 */
public class FileUtils {
	public static void save(String fileUri, String content) throws Exception {
		save(new File(fileUri), content, false);
	}

	public static void save(String fileUri, String content, boolean isAppend)
			throws Exception {
		save(new File(fileUri), content, isAppend);
	}

	public static void save(File file, String content) throws Exception {
		save(file, content, false);
	}

	public static void save(File file, String content, boolean isAppend)
			throws Exception {
		if (!file.exists()) {
			file.createNewFile();
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, isAppend));
		pw.write(content);
		pw.close();
	}
}
