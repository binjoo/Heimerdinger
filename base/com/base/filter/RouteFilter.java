package com.base.filter;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.base.action.CoreAction;
import com.base.utils.CharsetUtils;
import com.base.utils.Constants;
import com.base.utils.CoreMap;
import com.base.utils.RequestUtils;

import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class RouteFilter implements Filter {
	private static final Logger log = Logger.getLogger(RouteFilter.class);

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

		String ignores = cfg.getInitParameter("ignoreURIs");
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

		for (String ignoreExt : ignoreExts) {
			if (reqUri.endsWith(ignoreExt)) {
				chain.doFilter(request, response);
				return;
			}
		}
		for (String ignoreURI : ignoreURIs) {
			if (reqUri.startsWith(ignoreURI)) {
				chain.doFilter(request, response);
				return;
			}
		}

		try {
			String uri = request.getRequestURI();
			String[] parts = StringUtils.split(uri, "/");
			if (parts.length < 1) {
				parts = new String[] { "index" };
			}

			String cls = "com.resume.action." + StringUtils.capitalize(parts[0]) + "Action";
				
			CoreAction action = (CoreAction) Class.forName(cls).newInstance();

			action.setRequest(request);
			action.setResponse(response);
			action.setSession(request.getSession(false));
			
			Method method = action.getClass().getMethod("action", CoreMap.class);
			
			CoreMap inMap = RequestUtils.getInMap(request);
			outMap = (CoreMap) method.invoke(action, inMap);
			
			if(outMap != null){
				String type = outMap.getOutType();
				String render = outMap.getOutRender();
				
				if (type.equals(Constants.OUT_TYPE__PAGE)) {
					Template temp = this.cfg.getTemplate(outMap.getOutRender() + ".ftl");
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
					} finally{
						out.flush();
						out.close();
					}
				} else if (type.equals(Constants.OUT_TYPE__JSON)) {
					response.setContentType("text/json; charset=" + CharsetUtils.UTF_8);
					response.getWriter().print(JSONObject.fromObject(outMap));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		} finally {
		}
	}

}
