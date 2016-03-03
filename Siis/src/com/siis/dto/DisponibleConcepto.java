package com.siis.dto;

import java.util.Date;

public class DisponibleConcepto {
private Integer secuencia;
private Disponible disponible;
private String concepto;
private Date fecha;
private Date fechaCreacion;
private Date fechaHoraActualizacion;
private String tipoNota;
private Double valor;

public DisponibleConcepto() { 
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
 
public String getTipoNota() {
	return tipoNota;
}

public void setTipoNota(String tipoNota) {
	this.tipoNota = tipoNota;
}

public Disponible getDisponible() {
	return disponible;
}

public void setDisponible(Disponible disponible) {
	this.disponible = disponible;
}

public String getConcepto() {
	return concepto;
}

public void setConcepto(String concepto) {
	this.concepto = concepto;
}

public Date getFecha() {
	return fecha;
}

public void setFecha(Date fecha) {
	this.fecha = fecha;
}

public Double getValor() {
	return valor;
}

public void setValor(Double valor) {
	this.valor = valor;
}

 
}