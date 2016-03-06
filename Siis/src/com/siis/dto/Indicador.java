package com.siis.dto;

import java.util.Date;

public class Indicador extends Bean {

	private Integer secuencia;
	private Integer numero;
	private String nombre;
	private Long objetivoEsperado;
	private Long objetivoReal;
	private String anoMes;
	private String formula;
	private Date fechaCreacion;
	private Date fechaHoraActualizacion;
	private String estado;

	public Indicador() {

	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getObjetivoEsperado() {
		return objetivoEsperado;
	}

	public void setObjetivoEsperado(Long objetivoEsperado) {
		this.objetivoEsperado = objetivoEsperado;
	}

	public Long getObjetivoReal() {
		return objetivoReal;
	}

	public void setObjetivoReal(Long objetivoReal) {
		this.objetivoReal = objetivoReal;
	}

	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
