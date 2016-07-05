/**
 * 
 */
package com.casewaresa.framework.Filter.anottations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Fitco
 * @name RelationFilter.java
 * @date 27/02/2012
 * @desc
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface RelationFilter{
	
	String property();
	
	String[] fields();
	
	String[] labels();
	
	String[] columnsName();
	
	String idMapperForRelation();
	
	int[] index();
	
}
