package controller;

import base.annotation.RequestMapping;

/**
 *   处理器类：
 *     负责业务逻辑的处理。
 *     注：也可以调用其它类来处理业务逻辑。
 */
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello() {
		System.out.println("HelloController's hello()");
		//返回视图名
		return "hello";
	}
}






