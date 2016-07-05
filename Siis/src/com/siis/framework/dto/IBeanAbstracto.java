/**
 * 
 */
package com.siis.framework.dto;

/**
 * Contiene los metodos basicos de una Entidad
 * 
 * @author casewaredes01
 * @name IBeanAbstracto.java
 * @date 27/10/2010
 */

public interface IBeanAbstracto {
	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name getCodigo
	 * @return String
	 */
	String getCodigo();

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name getNombre
	 * @return String
	 */
	String getNombre();

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name getEstado
	 * @return String
	 */
	String getEstado();

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name getMD5
	 * @return String
	 * */
	String getMD5();

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name getSecuencia
	 * @return Long
	 */
	Long getPrimaryKey();

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name setCodigo
	 * @param codigo
	 */
	void setCodigo(String codigo);

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name setNombre
	 * @param nombre
	 */
	void setNombre(String nombre);

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name setMD5
	 * @param MD5
	 */
	void setMD5(String MD5);

	/**
	 * @type Método de la clase IBeanAbstracto.java
	 * @name setSecuencia
	 * @param sec
	 */
	void setPrimaryKey(Long sec);

}
