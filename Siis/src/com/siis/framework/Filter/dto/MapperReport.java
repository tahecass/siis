/**
 * 
 */
package com.casewaresa.framework.Filter.dto;

/**
 * @author casewaredes02
 * @name MapperReport.java
 * @date 13/01/2011
 * @desc
 */

public class MapperReport {
	
	private String label;
	private String valor;
	private String view;
	private String viewl;
	private String viewJ;
	private String viewR;
	private Class<?> clazz;
	/**
	 * @param label
	 * @param valor
	 * @param view
	 * @param viewl
	 */
	public MapperReport(String label, String valor, String view, String viewl,String viewJ, String viewR) {
		super();
		this.label = label;
		this.valor = valor;
		this.view = view;
		this.viewl = viewl;
		this.viewJ= viewJ;
		this.viewR = viewR;
	}
	
	public MapperReport(String label, String valor, String view, String viewl, Class<?> clazz,String viewJ, String viewR) {
		super();
		this.label = label;
		this.valor = valor;
		this.view = view;
		this.viewl = viewl;
		this.viewJ= viewJ;
		this.clazz = clazz;
		this.viewR= viewR;
	}
    
	/**
	 * 
	 */
	public MapperReport() {}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name getLabel
	 * @return label
	 * @descp obtiene el valor de label
	 */
	public String getLabel() {
		return label;
	}
	
	public String getViewJ() {
		return viewJ;
	}

	public void setViewJ(String viewJ) {
		this.viewJ = viewJ;
	}
	/**
	 * @type Método de la clase MapperReport.java
	 * @name setLabel
	 * @return void
	 * @param recibe el parametro label
	 * @descp modifica el atributo label 
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name getValor
	 * @return valor
	 * @descp obtiene el valor de valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name setValor
	 * @return void
	 * @param recibe el parametro valor
	 * @descp modifica el atributo valor 
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name getView
	 * @return view
	 * @descp obtiene el valor de view
	 */
	public String getView() {
		return view;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name setView
	 * @return void
	 * @param recibe el parametro view
	 * @descp modifica el atributo view 
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name getViewl
	 * @return viewl
	 * @descp obtiene el valor de viewl
	 */
	public String getViewl() {
		return viewl;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name setViewl
	 * @return void
	 * @param recibe el parametro viewl
	 * @descp modifica el atributo viewl 
	 */
	public void setViewl(String viewl) {
		this.viewl = viewl;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name getClazz
	 * @return clazz
	 * @descp obtiene el valor de clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @type Método de la clase MapperReport.java
	 * @name setClazz
	 * @return void
	 * @param recibe el parametro clazz
	 * @descp modifica el atributo clazz 
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public String getViewR() {
	    return viewR;
	} 

        public void setViewR(String viewR) {
	    this.viewR = viewR;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MapperReport [label=" + label + ", valor=" + valor + ", view="
				+ view + ", viewl=" + viewl + "]";
	}
	
	
		
}
