package com.resume.action;

import com.base.action.CoreAction;
import com.base.utils.AppConfig;
import com.base.utils.CoreMap;

public class IndexAction extends CoreAction {
	public CoreMap save(CoreMap inMap) {
		return null;
	}

	@Override
	public CoreMap action(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		out.setOutRender("index");
		AppConfig.set("x", "v");
		return out;
	}
}
