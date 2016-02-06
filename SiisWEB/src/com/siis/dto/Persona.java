package com.siis.dto;

public class Persona {
	
private Integer secuencia;
private String nombreRazonSocial;
private String apellidos;
private String tipo;
private String nit;
private String identificacion;
private TipoIdentificacion tipoId;

private String correoEletronico;
private String direccion;
private String telefono;
private String celular;


	public Persona() {
		 
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public TipoIdentificacion getTipoId() {
		return tipoId;
	}

	public void setTipoId(TipoIdentificacion tipoId) {
		this.tipoId = tipoId;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipo() {
		return tipo;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCorreoEletronico() {
		return correoEletronico;
	}

	public void setCorreoEletronico(String correoEletronico) {
		this.correoEletronico = correoEletronico;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	

}
