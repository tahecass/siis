/**
 * 
 */
package com.casewaresa.framework.Filter.anottations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jhoanseve2
 * @name Mapper.java
 * @date 21/02/2011
 * @desc
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface Mapper {
	String id();

	String label();

	String view();

	String viewl();
	
	String viewJ();
	
	String viewR();
}
