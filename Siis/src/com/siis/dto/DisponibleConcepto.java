package com.siis.dto;

import java.util.Date;

public class DisponibleConcepto {
private Integer secuencia;
private Banco banco;
private Cuenta cuenta;
private Usuario usuario;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public DisponibleConcepto() { 
}

public Integer getSecuencia() {
	return secuencia;
}

public void setSecuencia(Integer secuencia) {
	this.secuencia = secuencia;
}

public Usuario getUsuario() {
	return usuario;
}

public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
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

public Cuenta getCuenta() {
	return cuenta;
}

public void setCuenta(Cuenta cuenta) {
	this.cuenta = cuenta;
}

 
}