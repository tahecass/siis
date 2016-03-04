package com.siis.dto;

import java.util.Date;

public class AmortizacionCredito {
private Integer secuencia; 
private Credito credito;
private Double interes;
private Double capital;
private Double saldo;
private String periodo;
private Double cuota;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public AmortizacionCredito() { 
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

public Credito getCredito() {
	return credito;
}

public void setCredito(Credito credito) {
	this.credito = credito;
}

public String getPeriodo() {
	return periodo;
}

public void setPeriodo(String periodo) {
	this.periodo = periodo;
}

public Double getCuota() {
	return cuota;
}

public void setCuota(Double cuota) {
	this.cuota = cuota;
}

 
 
}