package com.siis.dto;

import java.sql.Timestamp;
import java.util.Date;

public class Cartera {
private Integer secuencia;
private Usuario usuario;
private Cliente cliente;
private Date fechaPago;
private Date fechaCreacion;
private Timestamp fechaHoraActualizacion;

public Cartera() { 
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

public Cliente getCliente() {
	return cliente;
}

public void setCliente(Cliente cliente) {
	this.cliente = cliente;
}

public Date getFechaPago() {
	return fechaPago;
}

public void setFechaPago(Date fechaPago) {
	this.fechaPago = fechaPago;
}

public Date getFechaHoraActualizacion() {
	return fechaHoraActualizacion;
}

public void setFechaHoraActualizacion(Timestamp fechaHoraActualizacion) {
	this.fechaHoraActualizacion = fechaHoraActualizacion;
}

public Date getFechaCreacion() {
	return fechaCreacion;
}

public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
}

 
}