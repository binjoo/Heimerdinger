package com.resume.action;

import java.io.File;

import com.base.action.CoreAction;

import com.base.utils.AppConfig;
import com.base.utils.Constants;
import com.base.utils.CoreMap;
import com.base.utils.FileUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdminAction extends CoreAction {
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
		
		FileUtils.save(AppConfig.class.getResource("/").getPath() + "readme.md", "哇哈哈");

		out.setOutRender("admin");
		return out;
	}

	private CoreMap save(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		out.setOutType(Constants.OUT_TYPE__JSON);
		String title = inMap.getString("title", "");
		String subtitle = inMap.getString("subtitle", "");
		String password = inMap.getString("password", "");

		CoreMap paramMap = new CoreMap();
		paramMap.put("title", title);
		paramMap.put("subtitle", subtitle);
		paramMap.put("password", password);

		AppConfig.set(paramMap);
		out.put("result", "success");
		out.put("message", "保存成功！");
		return out;
	}

}
