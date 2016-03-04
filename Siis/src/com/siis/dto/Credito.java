package com.siis.dto;

import java.util.Date;

public class Credito {
private Integer secuencia;
private Banco entidad;
private String NroPrestamo;
private Double interes;
private Double capital;
private Double saldo;
private Date fecha;
private Double plazo;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public Credito() { 
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

public Banco getEntidad() {
	return entidad;
}

public void setEntidad(Banco entidad) {
	this.entidad = entidad;
}

public String getNroPrestamo() {
	return NroPrestamo;
}

public void setNroPrestamo(String nroPrestamo) {
	NroPrestamo = nroPrestamo;
}

public Double getInteres() {
	return interes;
}

public void setInteres(Double interes) {
	this.interes = interes;
}

public Double getCapital() {
	return capital;
}

public void setCapital(Double capital) {
	this.capital = capital;
}

public Double getSaldo() {
	return saldo;
}

public void setSaldo(Double saldo) {
	this.saldo = saldo;
}

public Date getFecha() {
	return fecha;
}

public void setFecha(Date fecha) {
	this.fecha = fecha;
}

public Double getPlazo() {
	return plazo;
}

public void setPlazo(Double plazo) {
	this.plazo = plazo;
}
 
}