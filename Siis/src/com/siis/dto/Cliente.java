package com.siis.dto;

public class Cliente extends Bean {

private Integer secuencia;
private String nombreRazonSocial;
private String nit;
private Persona persona;

public Cliente() {
	 
}


public String getNit() {
	return nit;
}

public void setNit(String nit) {
	this.nit = nit;
}
public String getNombreRazonSocial() {
	return nombreRazonSocial;
}

public void setNombreRazonSocial(String nombreRazonSocial) {
	this.nombreRazonSocial = nombreRazonSocial;
}
public Integer getSecuencia() {
	return secuencia;
}

public void setSecuencia(Integer secuencia) {
	this.secuencia = secuencia;
}


public Persona getPersona() {
	return persona;
}


public void setPersona(Persona persona) {
	this.persona = persona;
}

}
