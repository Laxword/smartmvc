package base.common;

import java.lang.reflect.Method;

/**
 * 	Ϊ�˷�������java�������ȥ���ô������ķ�������Ƶ�һ�������ࡣ
 *	���У�obj�Ǵ�����ʵ����mh�Ǵ�������������Ӧ��Method����
 *		
 *			mh.invoke(obj);
 */
public class Handler {
	private Object obj;
	private Method mh;
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Method getMh() {
		return mh;
	}
	public void setMh(Method mh) {
		this.mh = mh;
	}
	
}
