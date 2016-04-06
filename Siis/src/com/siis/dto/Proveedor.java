package com.siis.dto;

import java.util.Date;

public class Proveedor {
private Integer secuencia;
private Usuario usuario;
private String proveedor;
private Date fechaPago;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public Proveedor() { 
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

public String getProveedor() {
	return proveedor;
}

public void setProveedor(String proveedor) {
	this.proveedor = proveedor;
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

public Date getFechaCreacion() {
	return fechaCreacion;
}

public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
}

 
}