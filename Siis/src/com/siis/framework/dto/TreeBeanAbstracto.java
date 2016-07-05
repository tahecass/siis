/**
 * 
 */
package com.casewaresa.framework.dto;


/**
 * @author msuevis
 * @name TreeBeanAbstrato.java
 * @date 3/05/2011
 * @desc
 */

public abstract class TreeBeanAbstracto extends BeanAbstracto implements IBeanAbstracto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6627913656482653272L;
	private TreeBeanAbstracto predecesor;
	private Long ordenHermanos;
	
	/**
	 * @type Método de la clase TreeBeanAbstrato.java
	 * @name getPredecesor
	 * @return predecesor
	 * @descp obtiene el valor de predecesor
	 */
	public TreeBeanAbstracto getPredecesor() {
		return predecesor;
	}



	/**
	 * @type Método de la clase TreeBeanAbstrato.java
	 * @name setPredecesor
	 * @return void
	 * @param recibe el parametro predecesor
	 * @descp modifica el atributo predecesor 
	 */
	public void setPredecesor(TreeBeanAbstracto predecesor) {
		this.predecesor = predecesor;
	}



	/**
	 * @type Método de la clase TreeBeanAbstrato.java
	 * @name getOrdenHermanos
	 * @return ordenHermanos
	 * @descp obtiene el valor de ordenHermanos
	 */
	public Long getOrdenHermanos() {
		return ordenHermanos;
	}



	/**
	 * @type Método de la clase TreeBeanAbstrato.java
	 * @name setOrdenHermanos
	 * @return void
	 * @param recibe el parametro ordenHermanos
	 * @descp modifica el atributo ordenHermanos 
	 */
	public void setOrdenHermanos(Long ordenHermanos) {
		this.ordenHermanos = ordenHermanos;
	}


	public TreeBeanAbstracto(){
		super();
	}



	
}
