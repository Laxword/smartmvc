package reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 	���һ��ע��û���κε����ԣ�һ���֮Ϊ��ʶע�⡣
 *  ע��Ĭ�������ֻ�ᱣ�����ֽ����ļ����棬����ʱ�ͻᱻɾ�������
 *  ϣ��������ʱ��Ȼ�ܹ����ʵ�ע�⣬��Ҫ����ע�������ʱ�䡣
 *  
 *  @Retention��һ��Ԫע��(����ϵͳ�ṩ��ר��������������ע���ע��)��
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
	/*
	 * value��ע������ԣ�������String��
	 * ���ע������Ϊvalue,��ֻ��һ�����ԣ�����ʹ�ø�ע��ʱ������
	 * ����д��������
	 */
	public String value();
	
}





