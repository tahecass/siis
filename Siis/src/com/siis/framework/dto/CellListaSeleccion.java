package com.casewaresa.framework.dto;


@SuppressWarnings("serial")
public class CellListaSeleccion extends ItemListaSeleccion{
	
	private Long secuencia;

	public CellListaSeleccion() {
		super();
	}

	public CellListaSeleccion(Long secuencia) {
		super();
		this.secuencia = secuencia;
	}

	public Long getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}
}
