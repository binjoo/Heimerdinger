package com.resume.action;

import com.base.action.CoreAction;

import com.base.utils.AppConfig;
import com.base.utils.Constants;
import com.base.utils.CoreMap;

@SuppressWarnings("rawtypes")
public class AdminAction extends CoreAction {
	public CoreMap action(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if ("save".equals(inMap.getString("action"))) {
			return save(inMap);
		} else {
			out.setOutRender("admin");
		}
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
