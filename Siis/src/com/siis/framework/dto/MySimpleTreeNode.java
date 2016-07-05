package com.casewaresa.framework.dto;

import java.util.Collection;
import java.util.List;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

import com.casewaresa.framework.util.MyItemTree;


public class MySimpleTreeNode extends DefaultTreeNode<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5500806465434965607L;
	private MyItemTree myData = null;
	
	@SuppressWarnings("unchecked")
	public MySimpleTreeNode(MyItemTree data, List<Object> children) {
		super(data, (Collection<? extends TreeNode<Object>>) children);
		myData = data;
	}

	/**
	 * @type   Método de la clase MySimpleTreeNode
	 * @name   getMyData
	 * @return void
	 * @param myData
	 * @desc   obtiene el valor de la propiedad myData
	 */
	public MyItemTree getMyData() {
		return myData;
	}

	/**
	 * @type   Método de la clase MySimpleTreeNode
	 * @name   setMyData
	 * @return MyItemTree
	 * @param myData
	 * @desc   Actualiza el valor de la propiedad myData
	 */
	public void setMyData(MyItemTree myData) {
		this.myData = myData;
	}
	
}
