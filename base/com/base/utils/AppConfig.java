package com.base.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppConfig {
	private static Properties appConfig;
	private static File appFile;
    private final static Log log = LogFactory.getLog(AppConfig.class);

    static {
		try {
			init();
			appFile = new File(AppConfig.class.getResource("/").getPath() + "/appConfig.properties");
		} catch (Exception ex) {
			log.error(ex);
		}
    }
    
    public static void init(){
    	init(false);
    }
    
    public static void init(boolean isBorce){
		if (appConfig == null || isBorce) {
			try {
				InputStream in = AppConfig.class.getResourceAsStream("/appConfig.properties");
				appConfig = new Properties();
				appConfig.load(in);
			} catch (Exception ex) {
				log.error(ex);
			}
		}
    }
    
	public static String getPro(String key) {
		return getPro(key, null);
	}

	public static String getPro(String key, String val) {
		if (appConfig.containsKey(key)) {
			String value = appConfig.getProperty(key);
			if (StringUtils.isNotEmpty(value)) {
				value = StringUtils.trim(value);
			}
			return value;
		} else {
			return val == null ? null : val;
		}
	}
	
	public static void set(String key, String val) throws IOException{
		CoreMap inMap = new CoreMap();
		inMap.put(key, val);
		set(inMap);
	}
	
	public static void set(CoreMap inMap) throws IOException {
		FileOutputStream fos = new FileOutputStream(appFile);
	    for (Object objKey :  inMap.keySet()) {
	    	String key = objKey.toString();
	    	String val = inMap.getString(key);
	    	appConfig.setProperty(key, val);
	    }
		appConfig.store(fos, "Heimerdinger Resume Config");
		fos.close();
		init(true);
	}
}
