package base.common;

import java.lang.reflect.Method;

/**
 * 	为了方便利用java反射机制去调用处理器的方法而设计的一个辅助类。
 *	其中，obj是处理器实例，mh是处理器方法所对应的Method对象。
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
