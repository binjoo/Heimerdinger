package com.base.filter;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.base.utils.AppConfig;
import com.base.utils.CharsetUtils;
import com.base.utils.CoreMap;

import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class RouteFilter implements Filter {
	private static final Logger log = Logger.getLogger(RouteFilter.class);

	private List<String> actionPackages = null;
	private ServletContext context;
	private Configuration cfg;

	private List<String> ignoreURIs = new ArrayList<String>();
	private List<String> ignoreExts = new ArrayList<String>();

	public void init(FilterConfig cfg) throws ServletException {
		try {
			freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.context = cfg.getServletContext();
		/**
		 * 初始化前台引擎模版
		 */
		this.cfg = new Configuration();
		this.cfg.setDefaultEncoding(CharsetUtils.UTF_8);
		this.cfg.setClassForTemplateLoading(this.getClass(), "/../templates");
		this.cfg.setObjectWrapper(new DefaultObjectWrapper());

		/**
		 * 获得ACTION包
		 */
		String packages = cfg.getInitParameter("packages");
		this.actionPackages = Arrays.asList(StringUtils.split(packages, ','));

		String ignores = cfg.getInitParameter("ignore");
		if (ignores != null) {
			for (String ig : StringUtils.split(ignores, ',')) {
				ignoreURIs.add(ig.trim());
			}
		}

		ignores = cfg.getInitParameter("ignoreExts");
		if (ignores != null) {
			for (String ig : StringUtils.split(ignores, ',')) {
				ignoreExts.add('.' + ig.trim());
			}
		}
	}

	public void destroy() {
		System.out.println("filter destroy");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		request.setCharacterEncoding(CharsetUtils.UTF_8);
		response.setCharacterEncoding(CharsetUtils.UTF_8);

		String reqUri = request.getRequestURI();
		CoreMap outMap = new CoreMap();

		if (reqUri.startsWith("/assets")) {
			chain.doFilter(request, response);
			return;
		}

		try {
			Template temp = this.cfg.getTemplate("admin.ftl");
			Writer out = response.getWriter();
			try {
				CoreMap data = outMap; // 传递数据
				data.put("request", request);
				data.put("response", response);
				data.put("session", new HttpSessionHashModel(request.getSession(), cfg.getObjectWrapper()));
				data.put("ms", System.currentTimeMillis());
				
				temp.process(data, out);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		} finally {
		}
	}

}
