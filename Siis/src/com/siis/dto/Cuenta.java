package com.siis.dto;

import java.util.Date;

public class Cuenta {
private Integer secuencia;
private Banco banco;
private String responsable;
private String numeroCuenta;
private String tipo;
private String estado;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public Cuenta() { 
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

public Banco getBanco() {
	return banco;
}

public void setBanco(Banco banco) {
	this.banco = banco;
}

public String getResponsable() {
	return responsable;
}

public void setResponsable(String responsable) {
	this.responsable = responsable;
}

public String getNumeroCuenta() {
	return numeroCuenta;
}

public void setNumeroCuenta(String numeroCuenta) {
	this.numeroCuenta = numeroCuenta;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}


 
}