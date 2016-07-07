/**
 * 
 */
package com.siis.framework.dao;

import java.util.Collection;

public interface IDao {

	/**
	 * @type   Metodo de la clase IDao
	 * @name   obtenerListado
	 * @return Collection
	 * @param objeto
	 * @desc   Este metodo es la plantilla para traer un listado de objetos producto de una consulta
	 */
	abstract Collection<Object> obtenerListado ( String  sqlName) throws Exception;	
	abstract Collection<Object> obtenerListado ( String  sqlName, Object object) throws Exception;


	/**
	 * @type   M�todo de la clase IDao
	 * @name   obtenerRegistro
	 * @return Object
	 * @param objeto
	 * @desc   
	 */
	abstract Object obtenerRegistro ( String sqlName) throws Exception;
	
	/**
	 * @type   M�todo de la clase IDao
	 * @name   obtenerRegistro
	 * @return Object
	 * @param objeto
	 * @desc   
	 */
	abstract Object obtenerRegistro (String sqlName,  Object objeto ) throws Exception;
	
	
	/**
	 * @type   M�todo de la clase IDao
	 * @name   insertarRegistro
	 * @return Object
	 * @param objeto
	 * @desc   Este m�todo es la plantilla para insertar registros a la base de datos
	 */
	abstract Object insertarRegistro (String sqlName, Object objeto ) throws Exception;
	

	/**
	 * @type   Método de la clase IDao
	 * @name   actualizarRegistro
	 * @return Object
	 * @param objeto
	 * @throws Exception
	 * @desc   Este m�todo es la plantilla para actualizar registros a la base de datos
	 */
	abstract Object actualizarRegistro (String sqlName, Object objeto ) throws Exception;	
	
	/**
	 * @type   Método de la clase IDao
	 * @name   borrarRegistro
	 * @return Object
	 * @param objeto
	 * @throws Exception
	 * @desc   Este m�todo es la plantilla para borrar registros a la base de datos
	 */
	abstract Object borrarRegistro (String sqlName, Object objeto ) throws Exception;	
	
	abstract Object validarSQL(String sql) throws Exception;
	
		
}
