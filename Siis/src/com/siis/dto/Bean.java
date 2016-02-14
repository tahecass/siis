package com.siis.dto;

public class Bean {
	private String codigo;
	private String nombre;
	private Long secuencia;

	/**
	 * @type Método de la clase Bean.java
	 * @name getCodigo
	 * @return String
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @type Método de la clase Bean.java
	 * @name getNombre
	 * @return String
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @type Método de la clase Bean.java
	 * @name getEstado
	 * @return String
	 */
	// String getEstado();
	//
	// /**
	// * @type Método de la clase Bean.java
	// * @name getMD5
	// * @return String
	// * */
	// String getMD5();
	//
	// /**
	// * @type Método de la clase Bean.java
	// * @name getSecuencia
	// * @return Long
	// */
	public Long getPrimaryKey() {
		return secuencia;
	}

	/**
	 * @type Método de la clase Bean.java
	 * @name setCodigo
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @type Método de la clase Bean.java
	 * @name setNombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @type Método de la clase Bean.java
	 * @name setMD5
	 * @param MD5
	 */
	// void setMD5(String MD5);

	/**
	 * @type Método de la clase Bean.java
	 * @name setSecuencia
	 * @param sec
	 */
	void setPrimaryKey(Long sec) {
		this.secuencia = sec;
	}
}
