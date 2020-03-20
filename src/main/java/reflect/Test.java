package reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 	如果一个注解没有任何的属性，一般称之为标识注解。
 *  注解默认情况下只会保留到字节码文件里面，运行时就会被删除，如果
 *  希望在运行时仍然能够访问到注解，需要设置注解的生存时间。
 *  
 *  @Retention是一个元注解(即由系统提供，专门用来解释其它注解的注解)。
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
	/*
	 * value是注解的属性，类型是String。
	 * 如果注解名称为value,且只有一个属性，则在使用该注解时，可以
	 * 不用写属性名。
	 */
	public String value();
	
}





