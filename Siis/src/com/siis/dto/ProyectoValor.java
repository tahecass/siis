package com.siis.dto;

import java.util.Date;

public class ProyectoValor {
	private Integer secuencia;
	private Proyecto proyecto;
	private Double plazo;
	private Double porcentaje;

	private Double valor;
	private Date fechaCreacion;
	private Date fechaHoraActualizacion;

	public ProyectoValor() {
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public Date getFechaHoraActualizacion() {
		return fechaHoraActualizacion;
	}

	public void setFechaHoraActualizacion(Date fechaHoraActualizacion) {
		this.fechaHoraActualizacion = fechaHoraActualizacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Double getPlazo() {
		return plazo;
	}

	public void setPlazo(Double plazo) {
		this.plazo = plazo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}