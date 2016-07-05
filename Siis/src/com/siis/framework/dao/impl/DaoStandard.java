/**
 * 
 */
package com.casewaresa.framework.dao.impl;

import java.util.Collection;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.casewaresa.framework.dao.IDao;
import com.casewaresa.framework.util.ConfiguradorIbatis;

public abstract class DaoStandard implements IDao {

	public  SqlSessionFactory sqlSessionFactory = ConfiguradorIbatis.getInstance().getSqlSessionFactory();
	public Logger log = Logger.getLogger(this.getClass());
	/**
	 * Se escribe aqu� lo que deber�a inicializar todos los daos decendientes de esta clase
	 */
	public DaoStandard() {
		super();
	}

	
	public Collection<Object> obtenerListado(String sqlName) throws Exception {
		throw new Exception("Metodo [ obtenerListado ] no implementado por la clase " + this.getClass());
	}
	
	public Collection<Object> obtenerListado(String sqlName, Object objeto) throws Exception {
		throw new Exception("Metodo [ obtenerListado ] no implementado por la clase " + this.getClass());
	}
	
	public Object obtenerRegistro(String sqlName) throws Exception {
		throw new Exception("Metodo [ obtenerRegistro ] no implementado por la clase " + this.getClass());
	}

	public Object obtenerRegistro(String sqlName, Object objeto) throws Exception {
		throw new Exception("Metodo [ obtenerRegistro ] no implementado por la clase " + this.getClass());
	}

	public Object ejecutarProcedimiento(String sqlName) throws Exception {
		throw new Exception("Metodo [ ejecutarProcedimiento ] no implementado por la clase " + this.getClass());
	}
	
	public Object ejecutarProcedimiento(String sqlName, Object objeto) throws Exception {
		throw new Exception("Metodo [ ejecutarProcedimiento ] no implementado por la clase " + this.getClass());
	}

	public Object insertarRegistro(String sqlName, Object objeto) throws Exception {
		throw new Exception("Metodo [ insertarRegistro ] no implementado por la clase " + this.getClass());
	}


	public Object actualizarRegistro(String sqlName, Object objeto) throws Exception {
		throw new Exception("Metodo [ actualizarRegistro ] no implementado por la clase " + this.getClass());
	}

	
	public Object borrarRegistro(String sqlName, Object objeto) throws Exception {
		throw new Exception("Metodo [ borrarRegistro ] no implementado por la clase " + this.getClass());
	}
	
	public Object validarSQL(String sql) throws Exception {
		throw new Exception("Metodo [ validarSQL ] no implementado por la clase " + this.getClass());
	}
	
	public Collection<Object> obtenerListado(String sqlName,final int omitir,final int maximo) throws Exception {
		throw new Exception("Metodo [ obtenerListado ] no implementado por la clase " + this.getClass());
	}
	
	public Collection<Object> obtenerListado(String sqlName, Object objeto,final int omitir,final int maximo) throws Exception {
		throw new Exception("Metodo [ obtenerListado ] no implementado por la clase " + this.getClass());
	}
}
