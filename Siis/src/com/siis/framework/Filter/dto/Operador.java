/**
 * 
 */
package com.casewaresa.framework.Filter.dto;

/**
 * @author casewaredes02
 * @name Operador.java
 * @date 17/12/2010
 * @desc
 */

public class Operador {
	private String label;
	private String valor;

	/**
 * 
 */
	public Operador() {
	}
	
	public Operador(String label, String valor) {
		this.label = label;
		this.valor = valor;
	}

	/**
	 * @type Método de la clase Operador.java
	 * @name getLabel
	 * @return label
	 * @descp obtiene el valor de label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @type Método de la clase Operador.java
	 * @name setLabel
	 * @return void
	 * @param recibe
	 *            el parametro label
	 * @descp modifica el atributo label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @type Método de la clase Operador.java
	 * @name getValor
	 * @return valor
	 * @descp obtiene el valor de valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @type Método de la clase Operador.java
	 * @name setValor
	 * @return void
	 * @param recibe
	 *            el parametro valor
	 * @descp modifica el atributo valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Operador [label=" + label + ", valor=" + valor + "]";
	}

}
