package com.siis.dto;

import java.util.Date;

public class Formulacion extends Bean {

	private Integer secuencia;
	private Indicador indicador;
	private String formulacion; 
	private Date fechaCreacion;
	private Date fechaHoraActualizacion;

	public Formulacion() {

	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	 

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaHoraActualizacion() {
		return fechaHoraActualizacion;
	}

	public void setFechaHoraActualizacion(Date fechaHoraActualizacion) {
		this.fechaHoraActualizacion = fechaHoraActualizacion;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public String getFormulacion() {
		return formulacion;
	}

	public void setFormulacion(String formulacion) {
		this.formulacion = formulacion;
	}
	
	

}
