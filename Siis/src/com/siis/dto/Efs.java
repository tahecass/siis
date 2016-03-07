package com.siis.dto;

import java.util.Date;

public class Efs {
	private Integer secuencia;
	private Integer contrato;
	 private byte[] contenidoBinarioArchivo;

	private Date fechaCreacion;
	private Date fechaActualizacion; 

	public Efs() {
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public Integer getContrato() {
		return contrato;
	}

	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}

	public byte[] getContenidoBinarioArchivo() {
		return contenidoBinarioArchivo;
	}

	public void setContenidoBinarioArchivo(byte[] contenidoBinarioArchivo) {
		this.contenidoBinarioArchivo = contenidoBinarioArchivo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

 
}