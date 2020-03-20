package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HandlerMapping handlerMapping;

	@Override
	/**
	 * 1.读取SmartMVC的配置文件(比如smartmvc.xml),获得处理器的类名。 2.将处理器实例化。
	 * 3.将处理器实例交给HandlerMapping来处理。
	 */
	public void init() throws ServletException {
		// 创建一个解析器
		SAXReader sax = new SAXReader();

		/*
		 * 读取配置文件名及路径。 getInitParameter方法来自于GenericServlet，而GenericServlet是
		 * HttpServlet的父类。
		 */
		String configLocation = getInitParameter("configLocation");

		// 构造一个指向配置文件的输入流
		InputStream in = getClass().getClassLoader().getResourceAsStream(configLocation);

		try {
			// 调用解析器方法进行解析
			Document doc = sax.read(in);
			// 找到根节点
			Element root = doc.getRootElement();
			// 找到根节点下面所有的子节点
			List<Element> elements = root.elements();
			// beans用于存放处理器实例
			List beans = new ArrayList();
			// 遍历所有的子节点
			for (Element ele : elements) {
				// 获得处理器类名
				String className = ele.attributeValue("class");
				System.out.println("className:" + className);
				// 将处理器实例化
				Object bean = Class.forName(className).newInstance();
				// 为了方便对处理器实例进行管理，将其添加到集合当中。
				beans.add(bean);
			}
			System.out.println("beans:" + beans);
			// 创建映射处理器实例
			handlerMapping = new HandlerMapping();
			// 将处理器实例交给映射处理器来处理
			handlerMapping.process(beans);

		} catch (Exception e) {
			System.out.println("初始化失败:" + e);
		}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		// 获得请求资源路径
		String uri = request.getRequestURI();
		// 获得应用名
		String contextPath = request.getContextPath();
		// 将请求资源路径中的应用名截取掉，获得请求路径
		String path = uri.substring(contextPath.length());
		System.out.println("path:" + path);
		// 依据请求路径来获得相应的Handler对象
		Handler handler = handlerMapping.getHandler(path);
		System.out.println("handler:" + handler);
		if (handler == null) {
			// 没有对应的处理器
			System.out.println("请求路径:" + path + "没有对应的处理器");
			response.sendError(404);
			return;

		} else {
			// 有对应的处理器,获得处理器实例
			Object obj = handler.getObj();
			// 获得相应的Method对象
			Method mh = handler.getMh();
			// rv是处理器方法的返回值
			Object rv = null;
			try {
				/*
				 * 调处理器的方法。 先获得处理器的方法的参数类型信息
				 */
				Class[] types = mh.getParameterTypes();
				if (types.length > 0) {
					// 处理器的方法带参,params用于存放实际参数值
					Object[] params = new Object[types.length];
					// 依据参数的类型进行相应的赋值
					for (int i = 0; i < types.length; i++) {
						if (types[i] == HttpServletRequest.class) {
							params[i] = request;
						}
						if (types[i] == HttpServletResponse.class) {
							params[i] = response;
						}
						// 目前SmartMVC只支持这两种类型，以后可以在这儿扩展。
					}
					rv = mh.invoke(obj, params);

				} else {
					// 处理器的方法不带参
					rv = mh.invoke(obj);
				}

				// 获得视图名
				String viewName = rv.toString();
				System.out.println("viewName:" + viewName);
				/*
				 * 处理视图名。 如果视图名是以"redirect:"开头，则重定向，否则 转发到"/WEB-INF/" + 视图名 + ".jsp"
				 * 
				 */
				if (viewName.startsWith("redirect:")) {
					// 生成重定向地址
					String redirectPath = contextPath + "/" + viewName.substring("redirect:".length());
					// 重定向
					response.sendRedirect(redirectPath);

				} else {
					// 生成转发的地址
					String forwardPath = "/WEB-INF/" + viewName + ".jsp";
					// 转发
					request.getRequestDispatcher(forwardPath).forward(request, response);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
