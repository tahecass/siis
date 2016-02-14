package com.siis.dto;

public class Menu {

	private String nombre;
	private String formaAsociada;
	private String id;

	public Menu() {

	}

	public Menu(String nombre, String formaAsociada, String id) {
		super();
		this.nombre = nombre;
		this.formaAsociada = formaAsociada;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormaAsociada() {
		return formaAsociada;
	}

	public void setFormaAsociada(String formaAsociada) {
		this.formaAsociada = formaAsociada;
	}

}
