package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

public class ReflectTest {

	public static void main(String[] args) throws Exception {
		// ͨ������̨��ȡ����
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		System.out.println("className:" + className);

		// ������
		Class clazz = Class.forName(className);
		// ʵ����
		Object obj = clazz.newInstance();
		System.out.println("obj:" + obj);

		/*
		 * ��ø�������з����� Method��������һ���������������������������͡��������ͣ����ڷ���ǰ��ע��ȵȣ�
		 */
		Method[] methods = clazz.getDeclaredMethods();
		// �������з���
		for (Method mh : methods) {
			/*
			 * ���ǰ�Ҫ���õķ�����֮ΪĿ�귽���� Ŀ�겻����: mh.invoke(obj) Ŀ�귽������:
			 * mh.invoke(obj,params),params��һ���������飬��������ʵ�ʲ���ֵ��
			 */
			// ���Ŀ�귽��������
			String name = mh.getName();
			if (name.startsWith("test")) {
				// ִֻ����"test"��ͷ�ķ�����
				// �Ȼ��Ŀ�귽���Ĳ���������Ϣ
				Class[] types = mh.getParameterTypes();
				// rv��Ŀ�귽���ķ���ֵ��
				Object rv = null;
				if (types.length == 0) {
					// Ŀ�귽��������
					rv = mh.invoke(obj);
				} else {
					// params���ڴ��ʵ�ʲ���ֵ
					Object[] params = new Object[types.length];
					// ����Ŀ�귽���Ĳ������ͽ��и�ֵ
					for (int i = 0; i < types.length; i++) {
						if (types[i] == String.class) {
							params[i] = "����Ͱ�";
						}
						if (types[i] == int.class) {
							params[i] = 100;
						}
					}
					// ����Ŀ�귽�������Σ�
					rv = mh.invoke(obj, params);
				}

				System.out.println("rv:" + rv);
			}
		}

	}

}
