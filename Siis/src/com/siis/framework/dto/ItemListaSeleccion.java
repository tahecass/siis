/**
 * ItemListaSeleccion.java
 */
package com.casewaresa.framework.dto;

import java.util.List;

import com.casewaresa.framework.dto.BeanAbstracto;
import com.casewaresa.framework.dto.impl.LlaveNatural;

/**
 * @author CaseWare Ingenieria
 * @date 27/04/2009
 * @name ItemListaSeleccion.java
 * @desc --
 */

public class ItemListaSeleccion extends BeanAbstracto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8009492483298674605L;
	private String valor = new String("");
	private String etiqueta = new String("");

	/**
	 * @type   Constructor de la clase ItemListaSeleccion
	 * @name   ItemListaSeleccion
	 * @param valor
	 * @param etiqueta
	 * @desc   --
	 */	
	
	public ItemListaSeleccion() {

	}
	
	public ItemListaSeleccion(String valor, String etiqueta) {
		super();
		this.valor = valor;
		this.etiqueta = etiqueta;
	}
	/**
	 * @type   Método de la clase ItemListaSeleccion
	 * @name   getValor
	 * @return void
	 * @param valor
	 * @desc   obtiene el valor de la propiedad valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @type   Método de la clase ItemListaSeleccion
	 * @name   setValor
	 * @return String
	 * @param valor
	 * @desc   Actualiza el valor de la propiedad valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * @type   Método de la clase ItemListaSeleccion
	 * @name   getEtiqueta
	 * @return void
	 * @param etiqueta
	 * @desc   obtiene el valor de la propiedad etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}
	/**
	 * @type   Método de la clase ItemListaSeleccion
	 * @name   setEtiqueta
	 * @return String
	 * @param etiqueta
	 * @desc   Actualiza el valor de la propiedad etiqueta
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	@Override
	public List<LlaveNatural> getLlaveNatural() {
		return null;
	}

	@Override
	public String toString() {
		return etiqueta;
	}
	
	
}
