/**
 * 
 */
package com.casewaresa.framework.Filter.dto;

/**
 * @author caswaredes03
 * @name OrderBy.java
 * @date 27/12/2010
 * @desc
 */

public class OrderBy {
	
	private ColumnsMapper columna;
	private String tipoOrden;
	private String label;
	/**
	 * @type Método de la clase OrderBy.java
	 * @name getLabel
	 * @return label
	 * @descp obtiene el valor de label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @type Método de la clase OrderBy.java
	 * @name setLabel
	 * @return void
	 * @param recibe el parametro label
	 * @descp modifica el atributo label 
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 */
	public OrderBy() {}
	
	/**
	 * @param columna
	 * @param tipoOrden
	 */
	public OrderBy(ColumnsMapper columna, String tipoOrden, String label) {
		this.columna = columna;
		this.tipoOrden = tipoOrden;
		this.label = label;
	}

	/**
	 * @type Método de la clase OrderBy.java
	 * @name getColumna
	 * @return columna
	 * @descp obtiene el valor de columna
	 */
	public ColumnsMapper getColumna() {
		return columna;
	}
	/**
	 * @type Método de la clase OrderBy.java
	 * @name getTipoOrden
	 * @return tipoOrden
	 * @descp obtiene el valor de tipoOrden
	 */
	public String getTipoOrden() {
		return tipoOrden;
	}
	/**
	 * @type Método de la clase OrderBy.java
	 * @name setColumna
	 * @return void
	 * @param recibe el parametro columna
	 * @descp modifica el atributo columna 
	 */
	public void setColumna(ColumnsMapper columna) {
		this.columna = columna;
	}
	/**
	 * @type Método de la clase OrderBy.java
	 * @name setTipoOrden
	 * @return void
	 * @param recibe el parametro tipoOrden
	 * @descp modifica el atributo tipoOrden 
	 */
	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderBy [");
		if (columna != null) {
			builder.append("columna=");
			builder.append(columna);
			builder.append(", ");
		}
		if (tipoOrden != null) {
			builder.append("tipoOrden=");
			builder.append(tipoOrden);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
