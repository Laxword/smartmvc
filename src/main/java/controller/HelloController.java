package controller;

import base.annotation.RequestMapping;

/**
 *   �������ࣺ
 *     ����ҵ���߼��Ĵ���
 *     ע��Ҳ���Ե���������������ҵ���߼���
 */
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello() {
		System.out.println("HelloController's hello()");
		//������ͼ��
		return "hello";
	}
}






