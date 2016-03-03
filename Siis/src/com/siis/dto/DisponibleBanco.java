package com.siis.dto;

import java.util.Date;

public class DisponibleBanco {
private Integer secuencia;
private Disponible disponible;
private String beneficiario;
private String nroCheque;
private Date fechaGiro;
private Double valor;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public DisponibleBanco() { 
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

public String getBeneficiario() {
	return beneficiario;
}

public void setBeneficiario(String beneficiario) {
	this.beneficiario = beneficiario;
}

public String getNroCheque() {
	return nroCheque;
}

public void setNroCheque(String nroCheque) {
	this.nroCheque = nroCheque;
}

public Date getFechaGiro() {
	return fechaGiro;
}

public void setFechaGiro(Date fechaGiro) {
	this.fechaGiro = fechaGiro;
}

public Double getValor() {
	return valor;
}

public void setValor(Double valor) {
	this.valor = valor;
}

public Disponible getDisponible() {
	return disponible;
}

public void setDisponible(Disponible disponible) {
	this.disponible = disponible;
}

 

 
}