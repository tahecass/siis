package com.siis.dto;

public class TipoIdentificacion {
	private Integer sec;
	private String tipo;
	private String nombre;
	private String documentacion;

	
	public TipoIdentificacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipoIdentificacion(Integer sec, String nombre) {
		super();
		this.sec = sec;
		this.nombre = nombre;
	}

	public Integer getSec() {
		return sec;
	}

	public void setSec(Integer sec) {
		this.sec = sec;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
