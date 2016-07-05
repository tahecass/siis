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
 * @name Relation.java
 * @date 22/02/2011
 * @desc
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Relation {
	String property();
	
	String[] fields();
	
	String[] labels();
	
	String[] columnsName();
	
	String idMapperForRelation();
	
	int[] index();
	
}
