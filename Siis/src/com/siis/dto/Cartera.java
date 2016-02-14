package com.siis.dto;

import java.util.Date;

public class Cartera {
private Integer secuencia;
private Usuario usuario;
private Cliente cliente;
private Date fechaPago;
private Date fechaHoraActualizacion;

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

public void setFechaHoraActualizacion(Date fechaHoraActualizacion) {
	this.fechaHoraActualizacion = fechaHoraActualizacion;
}

 
}
