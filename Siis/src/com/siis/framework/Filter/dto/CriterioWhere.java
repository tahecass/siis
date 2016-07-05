/**
 * 
 */
package com.casewaresa.framework.Filter.dto;

import java.util.List;

/**
 * @author casewaredes04
 * @name CriterioWhere.java
 * @date 17/12/2010
 * @desc
 */

public class CriterioWhere {
	private ColumnsMapper column;
	private Operador operador;
	private List<String> lista;
	private Operador operadorLogico;
	
	public CriterioWhere(){}
	
	/**
	 * @param column
	 * @param operador
	 * @param lista
	 */
	public CriterioWhere(ColumnsMapper column, Operador operador,Operador operadorLogico,
			List<String> lista) {
		super();
		this.column = column;
		this.operador = operador;
		this.lista = lista;
		this.operadorLogico = operadorLogico;
	}

	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name getColumn
	 * @return column
	 * @descp obtiene el valor de column
	 */
	public ColumnsMapper getColumn() {
		return column;
	}
	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name setColumn
	 * @return void
	 * @param recibe el parametro column
	 * @descp modifica el atributo column 
	 */
	public void setColumn(ColumnsMapper column) {
		this.column = column;
	}
	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name getOperador
	 * @return operador
	 * @descp obtiene el valor de operador
	 */
	public Operador getOperador() {
		return operador;
	}
	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name setOperador
	 * @return void
	 * @param recibe el parametro operador
	 * @descp modifica el atributo operador 
	 */
	public void setOperador(Operador operador) {
		this.operador = operador;
	}
	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name getLista
	 * @return lista
	 * @descp obtiene el valor de lista
	 */
	public List<String> getLista() {
		return lista;
	}
	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name setLista
	 * @return void
	 * @param recibe el parametro lista
	 * @descp modifica el atributo lista 
	 */
	public void setLista(List<String> lista) {
		this.lista = lista;
	}

	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name getOperadorLogico
	 * @return operadorLogico
	 * @descp obtiene el valor de operadorLogico
	 */
	public Operador getOperadorLogico() {
		return operadorLogico;
	}

	/**
	 * @type Método de la clase CriterioWhere.java
	 * @name setOperadorLogico
	 * @return void
	 * @param recibe el parametro operadorLogico
	 * @descp modifica el atributo operadorLogico 
	 */
	public void setOperadorLogico(Operador operadorLogico) {
		this.operadorLogico = operadorLogico;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CriterioWhere [column=" + column + ", lista=" + lista
				+ ", operador=" + operador + ", operadorLogico="
				+ operadorLogico + "]";
	}

	
	
	
}
