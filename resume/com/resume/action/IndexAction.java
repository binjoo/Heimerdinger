package com.resume.action;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.io.FileUtils;
import org.pegdown.Parser;
import org.pegdown.PegDownProcessor;

import com.base.action.CoreAction;
import com.base.utils.AppConfig;
import com.base.utils.CharsetUtils;
import com.base.utils.CoreMap;

@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexAction extends CoreAction {
	private File resumeFile = new File(AppConfig.class.getResource("/").getPath() + "/resume.md");

	public CoreMap action(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		String content = null;
		out.put("title", AppConfig.getPro("title", ""));
		out.put("subtitle", AppConfig.getPro("subtitle", ""));
		out.put("password", AppConfig.getPro("password", ""));

		try {
			content = FileUtils.readFileToString(resumeFile, CharsetUtils.UTF_8);
			content = new PegDownProcessor(Parser.ALL).markdownToHtml(content);
			out.put("content", content);
		} catch (FileNotFoundException e) {
			out.put("content", "");
		}
		out.setOutRender("index");
		return out;
	}
}
