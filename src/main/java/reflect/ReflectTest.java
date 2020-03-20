package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

public class ReflectTest {

	public static void main(String[] args) throws Exception {
		// 通过控制台读取类名
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		System.out.println("className:" + className);

		// 加载类
		Class clazz = Class.forName(className);
		// 实例化
		Object obj = clazz.newInstance();
		System.out.println("obj:" + obj);

		/*
		 * 获得该类的所有方法。 Method用来描述一个方法（包括方法名、参数类型、返回类型，加在方法前的注解等等）
		 */
		Method[] methods = clazz.getDeclaredMethods();
		// 遍历所有方法
		for (Method mh : methods) {
			/*
			 * 我们把要调用的方法称之为目标方法。 目标不带参: mh.invoke(obj) 目标方法带参:
			 * mh.invoke(obj,params),params是一个对象数组，里面存放有实际参数值。
			 */
			// 获得目标方法的名称
			String name = mh.getName();
			if (name.startsWith("test")) {
				// 只执行以"test"开头的方法。
				// 先获得目标方法的参数类型信息
				Class[] types = mh.getParameterTypes();
				// rv是目标方法的返回值。
				Object rv = null;
				if (types.length == 0) {
					// 目标方法不带参
					rv = mh.invoke(obj);
				} else {
					// params用于存放实际参数值
					Object[] params = new Object[types.length];
					// 依据目标方法的参数类型进行赋值
					for (int i = 0; i < types.length; i++) {
						if (types[i] == String.class) {
							params[i] = "阿里巴巴";
						}
						if (types[i] == int.class) {
							params[i] = 100;
						}
					}
					// 调用目标方法（带参）
					rv = mh.invoke(obj, params);
				}

				System.out.println("rv:" + rv);
			}
		}

	}

}
