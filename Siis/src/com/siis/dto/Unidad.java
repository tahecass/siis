package com.siis.dto;

public class Unidad {

	public Integer secuenciaUnidad;
	public String simbolo;
	public String nombre;
	public String documentacion;
	
	public Unidad() { 
	}

	public Integer getSecuenciaUnidad() {
		return secuenciaUnidad;
	}

	public void setSecuenciaUnidad(Integer secuenciaUnidad) {
		this.secuenciaUnidad = secuenciaUnidad;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumentacion() {
		return documentacion;
	}

	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}

}
