/**
 * 
 */
package com.casewaresa.framework.dto;

/**
 * @author casewaredes02
 * @name IDetalleGrupo.java
 * @date 25/04/2011
 * @desc
 */

public interface IDetalleGrupo extends IBeanAbstracto {
  
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name setSecuenciaDto
	 * @param secDto
	 * @desc secuencia del objeto que se relaciona con el grupo para generar el detalle
	 */
	void setComponente(IBeanAbstracto dto);
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name setSecuenciaDtoGrupo
	 * @param secDtoGrupo
	 * @desc
	 */
	void setGrupo(IBeanAbstracto dto);
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name setIncluyeExcluye
	 * @param ie
	 * @desc
	 */
	void setIncluyeExcluye(String ie);
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name setDescripcion
	 * @param descripcion
	 * @desc
	 */
	void setDescripcion(String descripcion);
	
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name getSecuenciaDto
	 * @return
	 * @desc
	 */
	IBeanAbstracto getComponente();
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name getSecuenciaDtoGrupo
	 * @return
	 * @desc
	 */
	IBeanAbstracto getGrupo();
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name getIncluyeExcluye
	 * @return
	 * @desc
	 */
	String getIncluyeExcluye();
	/**
	 * @type Método de la clase IDetalleGrupo.java
	 * @name getDescripcion
	 * @return
	 * @desc
	 */
	String getDescripcion();
	
	
}
