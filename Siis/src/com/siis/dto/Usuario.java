package com.siis.dto;

public class Usuario extends Bean {
	private Long sec;
	private String cuenta;
	private String clave;
	private String tipo;
	private Persona persona;
	private String estado;

	public Usuario() {

	}

	public Long getSec() {
		return sec;
	}

	public void setSec(Long sec) {
		this.sec = sec;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
