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

public class RowFiltro extends BeanAbstracto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8009492483298674605L;
	private String label = new String("");
	private String tipoDato = new String("");
	private String id = new String("");

	public RowFiltro(String label, String tipoDato,String id) {
		this.label = label;
		this.tipoDato = tipoDato;
		this.id = id;
	}

	public RowFiltro(){
		
	}
	
	public String getLabel() {
		return label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	@Override
	public List<LlaveNatural> getLlaveNatural() {
		// TODO Auto-generated method stub
		return null;
	}

}
