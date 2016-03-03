package com.siis.dto;

import java.util.Date;

public class Banco {
private Integer secuencia;
private String codigo;
private String nombre;
private String estado;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public Banco() { 
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

public String getCodigo() {
	return codigo;
}

public void setCodigo(String codigo) {
	this.codigo = codigo;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}

 
}