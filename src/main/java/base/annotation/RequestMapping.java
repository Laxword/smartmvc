package base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * ע�ⷽ��
 * @author Lax
 * @date 2020��3��15�� ����6:10:10
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	public String value();
}
