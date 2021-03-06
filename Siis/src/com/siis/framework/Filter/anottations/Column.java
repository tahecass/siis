package com.casewaresa.framework.Filter.anottations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	String name();

	String label();

	String type();

	String property();
	
	int index();

}