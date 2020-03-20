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
	 * 1.��ȡSmartMVC�������ļ�(����smartmvc.xml),��ô������������� 2.��������ʵ������
	 * 3.��������ʵ������HandlerMapping������
	 */
	public void init() throws ServletException {
		// ����һ��������
		SAXReader sax = new SAXReader();

		/*
		 * ��ȡ�����ļ�����·���� getInitParameter����������GenericServlet����GenericServlet��
		 * HttpServlet�ĸ��ࡣ
		 */
		String configLocation = getInitParameter("configLocation");

		// ����һ��ָ�������ļ���������
		InputStream in = getClass().getClassLoader().getResourceAsStream(configLocation);

		try {
			// ���ý������������н���
			Document doc = sax.read(in);
			// �ҵ����ڵ�
			Element root = doc.getRootElement();
			// �ҵ����ڵ��������е��ӽڵ�
			List<Element> elements = root.elements();
			// beans���ڴ�Ŵ�����ʵ��
			List beans = new ArrayList();
			// �������е��ӽڵ�
			for (Element ele : elements) {
				// ��ô���������
				String className = ele.attributeValue("class");
				System.out.println("className:" + className);
				// ��������ʵ����
				Object bean = Class.forName(className).newInstance();
				// Ϊ�˷���Դ�����ʵ�����й���������ӵ����ϵ��С�
				beans.add(bean);
			}
			System.out.println("beans:" + beans);
			// ����ӳ�䴦����ʵ��
			handlerMapping = new HandlerMapping();
			// ��������ʵ������ӳ�䴦����������
			handlerMapping.process(beans);

		} catch (Exception e) {
			System.out.println("��ʼ��ʧ��:" + e);
		}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		// ���������Դ·��
		String uri = request.getRequestURI();
		// ���Ӧ����
		String contextPath = request.getContextPath();
		// ��������Դ·���е�Ӧ������ȡ�����������·��
		String path = uri.substring(contextPath.length());
		System.out.println("path:" + path);
		// ��������·���������Ӧ��Handler����
		Handler handler = handlerMapping.getHandler(path);
		System.out.println("handler:" + handler);
		if (handler == null) {
			// û�ж�Ӧ�Ĵ�����
			System.out.println("����·��:" + path + "û�ж�Ӧ�Ĵ�����");
			response.sendError(404);
			return;

		} else {
			// �ж�Ӧ�Ĵ�����,��ô�����ʵ��
			Object obj = handler.getObj();
			// �����Ӧ��Method����
			Method mh = handler.getMh();
			// rv�Ǵ����������ķ���ֵ
			Object rv = null;
			try {
				/*
				 * ���������ķ����� �Ȼ�ô������ķ����Ĳ���������Ϣ
				 */
				Class[] types = mh.getParameterTypes();
				if (types.length > 0) {
					// �������ķ�������,params���ڴ��ʵ�ʲ���ֵ
					Object[] params = new Object[types.length];
					// ���ݲ��������ͽ�����Ӧ�ĸ�ֵ
					for (int i = 0; i < types.length; i++) {
						if (types[i] == HttpServletRequest.class) {
							params[i] = request;
						}
						if (types[i] == HttpServletResponse.class) {
							params[i] = response;
						}
						// ĿǰSmartMVCֻ֧�����������ͣ��Ժ�����������չ��
					}
					rv = mh.invoke(obj, params);

				} else {
					// �������ķ���������
					rv = mh.invoke(obj);
				}

				// �����ͼ��
				String viewName = rv.toString();
				System.out.println("viewName:" + viewName);
				/*
				 * ������ͼ���� �����ͼ������"redirect:"��ͷ�����ض��򣬷��� ת����"/WEB-INF/" + ��ͼ�� + ".jsp"
				 * 
				 */
				if (viewName.startsWith("redirect:")) {
					// �����ض����ַ
					String redirectPath = contextPath + "/" + viewName.substring("redirect:".length());
					// �ض���
					response.sendRedirect(redirectPath);

				} else {
					// ����ת���ĵ�ַ
					String forwardPath = "/WEB-INF/" + viewName + ".jsp";
					// ת��
					request.getRequestDispatcher(forwardPath).forward(request, response);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
