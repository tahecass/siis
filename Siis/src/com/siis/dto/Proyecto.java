package com.siis.dto;

import java.util.Date;

public class Proyecto {
private Integer secuencia;
private Banco entidad;
private Integer contrato;
private String objeto;
private Double porcentajeEjecucion;
private Double valorLegalizado;
private Date fecha;
private Date fechaCreacion;
private Date fechaHoraActualizacion;

public Proyecto() { 
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

public Integer getContrato() {
	return contrato;
}

public void setContrato(Integer contrato) {
	this.contrato = contrato;
}

public String getObjeto() {
	return objeto;
}

public void setObjeto(String objeto) {
	this.objeto = objeto;
}

public Double getPorcentajeEjecucion() {
	return porcentajeEjecucion;
}

public void setPorcentajeEjecucion(Double porcentajeEjecucion) {
	this.porcentajeEjecucion = porcentajeEjecucion;
}

public Double getValorLegalizado() {
	return valorLegalizado;
}

public void setValorLegalizado(Double valorLegalizado) {
	this.valorLegalizado = valorLegalizado;
}

public Date getFecha() {
	return fecha;
}

public void setFecha(Date fecha) {
	this.fecha = fecha;
}


}