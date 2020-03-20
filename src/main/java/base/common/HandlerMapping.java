package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * 	ӳ�䴦�����ࣺ
 * 		�����ṩ����·���봦�����������Ķ�Ӧ��ϵ������:
 * 		����·��Ϊ"/hello.do"����HelloController��
 * 		hello����������
 */
public class HandlerMapping {
	
	/*
	 * handlerMap���������·���봦�����������Ķ�Ӧ��ϵ��
	 * ע��
	 * 		Handler�Ǵ������������ķ�װ��
	 */
	private Map<String,Handler> handlerMap = 
			new HashMap<String,Handler>();
	/**
	 * ��������·��������Ӧ��Handler����
	 * @param path ����·��
	 */
	public Handler getHandler(String path) {
		return handlerMap.get(path);
	}
	
	/**
	 * ����������·���봦�����������Ķ�Ӧ��ϵ��
	 * @param beans:������ʵ����ɵļ���
	 */
	public void process(List beans) {
		for(Object bean : beans) {
			
			//��ü��ڴ���������ǰ��@RequestMapping
			RequestMapping rm1 = bean.getClass().getAnnotation(RequestMapping.class);
			//path1�Ǽ��ڴ���������ǰ���·��
			String path1 = "";
			if(rm1 != null) {
				path1 = rm1.value();
			}
			if(!path1.startsWith("/")) {
				path1="/"+path1;
			}
			if(!path1.endsWith("/")) {
				int index=path1.indexOf('/');
				path1=path1.substring(0,index);
			}
			//��ô����������з���
			Method[] methods = bean.getClass().getDeclaredMethods();
			//������Щ����
			for(Method mh : methods) {
				//��ü��ڷ���ǰ��@RequestMappingע��
				RequestMapping rm = mh.getAnnotation(RequestMapping.class);
				//�����������п���û�����@RequestMappingע��
				if(rm != null) {
					//�������·��
					String path = rm.value();
					if(!path.startsWith("/")) {
						path="/"+path;
					}
					System.out.println("handlerMapping--path-----"+path1+path);
					//��������ʵ�������������װ��Handler����
					Handler handler = new Handler();
					handler.setObj(bean);
					handler.setMh(mh);
					//������·���봦����ʵ����������Ӧ��ϵ��ŵ�map��
					handlerMap.put(path1 + path, handler);
					
				}
				
			}
		}
		System.out.println("handlerMap:" + handlerMap);
	}

}




