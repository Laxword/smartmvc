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
			//��ü��ڷ���ǰ��@Testע��
			Test test = mh.getAnnotation(Test.class);
			System.out.println("@Test:"  + test);
			if(test != null) {
				//ִֻ�д���@Testע��ķ���
				mh.invoke(obj);
				//��ȡע�������ֵ
				String v1 = test.value();
				System.out.println("v1:"  + v1);
			}
		}
	}
}





