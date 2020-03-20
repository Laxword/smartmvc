package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * 	映射处理器类：
 * 		负责提供请求路径与处理器及方法的对应关系，比如:
 * 		请求路径为"/hello.do"，由HelloController的
 * 		hello方法来处理。
 */
public class HandlerMapping {
	
	/*
	 * handlerMap存放有请求路径与处理器及方法的对应关系。
	 * 注：
	 * 		Handler是处理器及方法的封装。
	 */
	private Map<String,Handler> handlerMap = 
			new HashMap<String,Handler>();
	/**
	 * 依据请求路径返回相应的Handler对象
	 * @param path 请求路径
	 */
	public Handler getHandler(String path) {
		return handlerMap.get(path);
	}
	
	/**
	 * 负责建立请求路径与处理器及方法的对应关系。
	 * @param beans:处理器实例组成的集合
	 */
	public void process(List beans) {
		for(Object bean : beans) {
			
			//获得加在处理器类名前的@RequestMapping
			RequestMapping rm1 = bean.getClass().getAnnotation(RequestMapping.class);
			//path1是加在处理器类名前面的路径
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
			//获得处理器的所有方法
			Method[] methods = bean.getClass().getDeclaredMethods();
			//遍历这些方法
			for(Method mh : methods) {
				//获得加在方法前的@RequestMapping注解
				RequestMapping rm = mh.getAnnotation(RequestMapping.class);
				//处理器方法有可能没有添加@RequestMapping注解
				if(rm != null) {
					//获得请求路径
					String path = rm.value();
					if(!path.startsWith("/")) {
						path="/"+path;
					}
					System.out.println("handlerMapping--path-----"+path1+path);
					//将处理器实例及方法对象封装成Handler对象
					Handler handler = new Handler();
					handler.setObj(bean);
					handler.setMh(mh);
					//将请求路径与处理器实例及方法对应关系存放到map。
					handlerMap.put(path1 + path, handler);
					
				}
				
			}
		}
		System.out.println("handlerMap:" + handlerMap);
	}

}




