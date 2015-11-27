package com.resume.action;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.io.FileUtils;

import com.base.action.CoreAction;
import com.base.utils.AppConfig;
import com.base.utils.CharsetUtils;
import com.base.utils.Constants;
import com.base.utils.CoreMap;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdminAction extends CoreAction {
	private File resumeFile = new File(AppConfig.class.getResource("/").getPath() + "/resume.md");
	
	public CoreMap action(CoreMap inMap) throws Exception {
		if ("save".equals(inMap.getString("action"))) {
			return save(inMap);
		} else {
			return index(inMap);
		}
	}

	private CoreMap index(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		out.put("title", AppConfig.getPro("title", ""));
		out.put("subtitle", AppConfig.getPro("subtitle", ""));
		out.put("password", AppConfig.getPro("password", ""));
		try {
			String content = FileUtils.readFileToString(resumeFile, CharsetUtils.UTF_8);
			out.put("content", content);
		} catch (FileNotFoundException e) {
			out.put("content", "");
		}
		out.setOutRender("admin");
		return out;
	}

	private CoreMap save(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		out.setOutType(Constants.OUT_TYPE__JSON);
		String title = inMap.getString("title", "");
		String subtitle = inMap.getString("subtitle", "");
		String password = inMap.getString("password", "");
		String content = inMap.getString("content", "");

		CoreMap paramMap = new CoreMap();
		paramMap.put("title", title);
		paramMap.put("subtitle", subtitle);
		paramMap.put("password", password);

		AppConfig.set(paramMap);

		FileUtils.writeByteArrayToFile(resumeFile, content.getBytes(CharsetUtils.UTF_8));
		
		out.put("result", "success");
		out.put("message", "保存成功！");
		return out;
	}

}
