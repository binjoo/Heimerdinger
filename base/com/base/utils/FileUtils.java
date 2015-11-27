package com.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 文件操作类
 * 
 * @author liangj
 * 
 */
public class FileUtils {
	public static void write(String fileUri, String content) throws Exception {
		write(new File(fileUri), content, false);
	}

	public static void write(String fileUri, String content, boolean isAppend)
			throws Exception {
		write(new File(fileUri), content, isAppend);
	}

	public static void write(File file, String content) throws Exception {
		write(file, content, false);
	}

	public static void write(File file, String content, boolean isAppend)
			throws Exception {
		if (!file.exists()) {
			file.createNewFile();
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, isAppend));
		pw.write(content);
		pw.close();
	}

	public static String read(String fileUri) throws Exception {
		return read(new File(fileUri));
	}

	public static String read(File file) throws Exception {
		if (!file.exists()) {
			throw new FileNotFoundException("File Not Found.");
		}
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		int len = fis.read(buf);
		while (len != -1) {
			sb.append(new String(buf, 0, len));
			len = fis.read(buf);
		}
		fis.close();
		return sb.toString();
	}
}
