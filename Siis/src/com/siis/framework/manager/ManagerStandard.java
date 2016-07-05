/**
 * DaoFactory.java
 */
package com.casewaresa.framework.manager;

import java.util.Collection;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name ManagerStandard.java
 * @desc Define el comportamietno de los manager de la aplicaci�n
 */
public abstract class ManagerStandard {

    public ManagerStandard() {
	super();
    }

    public Collection<Object> obtenerListado(String sqlName) throws Exception {
	throw new Exception(
		"Metodo [ obtenerListado ] no implementado por la clase "
			+ this.getClass());
    }

    public Collection<Object> obtenerListado(String sqlName, Object objeto)
	    throws Exception {
	throw new Exception(
		"Metodo [ obtenerListado ] no implementado por la clase "
			+ this.getClass());
    }

    public Object obtenerRegistro(String sqlName) throws Exception {
	throw new Exception(
		"Metodo [ obtenerRegistro ] no implementado por la clase "
			+ this.getClass());
    }

    public Object obtenerRegistro(String sqlName, Object objeto)
	    throws Exception {
	throw new Exception(
		"Metodo [ obtenerRegistro ] no implementado por la clase "
			+ this.getClass());
    }

    public Object ejecutarProcedimiento(String sqlName) throws Exception {
	throw new Exception(
		"Metodo [ ejecutarProcedimiento ] no implementado por la clase "
			+ this.getClass());
    }

    public Object ejecutarProcedimiento(String sqlName, Object objeto)
	    throws Exception {
	throw new Exception(
		"Metodo [ ejecutarProcedimiento ] no implementado por la clase "
			+ this.getClass());
    }

    public Object insertarRegistro(String sqlName, Object objeto)
	    throws Exception {
	throw new Exception(
		"Metodo [ insertarRegistro ] no implementado por la clase "
			+ this.getClass());
    }

    public Object actualizarRegistro(String sqlName, Object objeto)
	    throws Exception {
	throw new Exception(
		"Metodo [ actualizarRegistro ] no implementado por la clase "
			+ this.getClass());
    }

    public Object borrarRegistro(String sqlName, Object objeto)
	    throws Exception {
	throw new Exception(
		"Metodo [ borrarRegistro ] no implementado por la clase "
			+ this.getClass());
    }

    public Object validarSQL(String sql) throws Exception {
	throw new Exception(
		"Metodo [ validarSQL ] no implementado por la clase "
			+ this.getClass());
    }

    public Collection<Object> obtenerListado(String sqlName, final int omitir,
	    final int maximo) throws Exception {
	throw new Exception(
		"Metodo [ obtenerListado ] no implementado por la clase "
			+ this.getClass());
    }

    public Collection<Object> obtenerListado(String sqlName, Object objeto,
	    final int omitir, final int maximo) throws Exception {
	throw new Exception(
		"Metodo [ obtenerListado ] no implementado por la clase "
			+ this.getClass());
    }
}
