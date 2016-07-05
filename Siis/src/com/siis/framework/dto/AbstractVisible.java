/**
 * 
 */
package com.casewaresa.framework.dto;

/**
 * @author jhoanseve2
 * @name StringBoolean.java
 * @date 20/05/2011
 * @desc
 */

public class AbstractVisible<T> {
	private T bean;
	private Boolean visible;

	/**
	 * @param value
	 * @param visible
	 */
	public AbstractVisible(T bean, Boolean visible) {
		super();
		this.bean = bean;
		this.visible = visible;
	}

	/**
	 * @param bean
	 */
	public AbstractVisible(T bean) {
		super();
		this.bean = bean;
	}

	/**
	 * 
	 */
	public AbstractVisible() {
		super();
	}

	/**
	 * @type Método de la clase AbstractVisible.java
	 * @name getBean
	 * @return bean
	 * @descp obtiene el valor de bean
	 */
	public T getBean() {
		return bean;
	}

	/**
	 * @type Método de la clase AbstractVisible.java
	 * @name setBean
	 * @return void
	 * @param recibe
	 *            el parametro bean
	 * @descp modifica el atributo bean
	 */
	public void setBean(T bean) {
		this.bean = bean;
	}

	/**
	 * @type Método de la clase AbstractVisible.java
	 * @name getVisible
	 * @return visible
	 * @descp obtiene el valor de visible
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * @type Método de la clase AbstractVisible.java
	 * @name setVisible
	 * @return void
	 * @param recibe
	 *            el parametro visible
	 * @descp modifica el atributo visible
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
