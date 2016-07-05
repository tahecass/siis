package com.siis.framework.dto.impl;

/**
 * @author Oscar Villamil
 * @date Feb 8, 2007
 * @name LlaveNatural.java
 * @desc Esta clase permite encapsular la llave(primary key DB) de un DTO
 *       
 */
public class LlaveNatural {
	
	 private String llave;
	 
	 private String valor;

	 /**
	  * @type   Constructor de la clase LlaveNatural
	  * @name   LlaveNatural
	  */	
	 public LlaveNatural() {

	 }
	 
	 /**
	 * @type   Constructor de la clase LlaveNatural
	 * @name   LlaveNatural
	 * @param llave
	 * @param valor
	 * 	 */	
	public LlaveNatural(String llave, String valor) {

		this.llave = llave;
		this.valor = valor;
	}


	/**
	 * @type   M�todo de la clase LlaveNatural
	 * @name   getLlave
	 * @return String
	 * @return
	 * @desc Obtiene el valor del atributo llave
	 */
	public String getLlave() {
		return llave;
	}

	/**
	 * @type   M�todo de la clase LlaveNatural
	 * @name   setLlave
	 * @return void
	 * @param llave
	 * @desc   Actualiza el valor del atributo llave
	 */
	public void setLlave(String llave) {
		this.llave = llave;
	}

	/**
	 * @type   M�todo de la clase LlaveNatural
	 * @name   getValor
	 * @return String
	 * @return
	 * @desc   Obtiene el valor del atributo valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @type   M�todo de la clase LlaveNatural
	 * @name   setValor
	 * @return void
	 * @param valor
	 * @desc   Actualiza el valor del atributo valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

}
