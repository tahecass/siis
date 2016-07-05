package com.casewaresa.framework.util;

import java.util.Comparator;

import org.zkoss.zul.DefaultTreeNode;

@SuppressWarnings({"rawtypes"})
public class SortChilds implements Comparator{

	public int compare(Object emp1, Object emp2){

		//parameter are of type Object, so we have to downcast it to Employee objects

		String emp1Name = ( (DefaultTreeNode<?>) emp1 ).getData().toString();
		String emp2Name = ( (DefaultTreeNode<?>) emp2 ).getData().toString();

		//uses compareTo method of String class to compare names of the employee

		return emp1Name.compareTo(emp2Name);

		}	

}
