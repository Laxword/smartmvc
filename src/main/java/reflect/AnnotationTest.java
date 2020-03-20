package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

public class AnnotationTest {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		Class clazz = Class.forName(className);
		Object obj = clazz.newInstance();
		
		Method[] methods = clazz.getDeclaredMethods();
		for(Method mh : methods) {
			//获得加在方法前的@Test注解
			Test test = mh.getAnnotation(Test.class);
			System.out.println("@Test:"  + test);
			if(test != null) {
				//只执行带有@Test注解的方法
				mh.invoke(obj);
				//读取注解的属性值
				String v1 = test.value();
				System.out.println("v1:"  + v1);
			}
		}
	}
}





