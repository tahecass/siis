package com.casewaresa.framework.manager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.casewaresa.framework.dao.ParametrizacionDao;

public class ParametrizacionMgr extends ManagerStandard {

    public List<Object> obtenerListado(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return (List<Object>) parametrizacionDao
		.obtenerListado(qryName, objeto);
    }

    public List<Object> obtenerListado(String qryName) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return (List<Object>) parametrizacionDao.obtenerListado(qryName);
    }

    public Object obtenerRegistro(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.obtenerRegistro(qryName, objeto);
    }

    public Object obtenerRegistro(String qryName) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.obtenerRegistro(qryName);
    }

    public Object ejecutarProcedimiento(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.ejecutarProcedimiento(qryName, objeto);
    }

    public Object insertarRegistro(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.insertarRegistro(qryName, objeto);
    }

    public Object actualizarRegistro(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.actualizarRegistro(qryName, objeto);
    }

    public Object borrarRegistro(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.borrarRegistro(qryName, objeto);
    }

    public Object ejecutarProcedimiento(String qryName) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.ejecutarProcedimiento(qryName);
    }

    public Object validarSQL(String sql) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.validarSQL(sql);
    }

    public Object obtenerDinamico(Object objeto, String sql) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.obtenerDinamico(objeto, sql);
    }

    public <T> List<T> listadoDinamico(Object objeto, String sql)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.listadoDinamico(objeto, sql);
    }

    public <T> List<T> listadoDinamico(Object objeto, String sql,
	    Map<String, Object> parametros) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.listadoDinamico(objeto, sql, parametros);
    }

    @Override
    public Collection<Object> obtenerListado(String sqlName, int omitir,
	    int maximo) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return (List<Object>) parametrizacionDao.obtenerListado(sqlName,
		omitir, maximo);
    }
    
    @Override
    public Collection<Object> obtenerListado(String sqlName, Object objeto,
            int omitir, int maximo) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return (List<Object>) parametrizacionDao.obtenerListado(sqlName,objeto,
		omitir, maximo);
    }
    
    public <T> List<T> listadoDinamico(Object objeto, String sql,
	    int omitir,int maximo)
	    throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.listadoDinamico(objeto, sql,omitir,maximo);
    }

    public <T> List<T> listadoDinamico(Object objeto, String sql,
	    Map<String, Object> parametros,int omitir,
	    int maximo) throws Exception {
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.listadoDinamico(objeto, sql, parametros,omitir,maximo);
    }
    
    public Object obtenerDinamico(String sql, Map<String,Object> parametros)throws Exception{
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.obtenerDinamico(sql,parametros);
    }
    
    /**
     * Metodo para obtener una consulta sql de un mapper(XML)
     * @param sqlName
     * @param objeto
     * @return
     * @throws Exception
     */
    public String obtenerSqlMapper(String sqlName, Object objeto)throws Exception{
	ParametrizacionDao parametrizacionDao = new ParametrizacionDao();
	return parametrizacionDao.obtenerSqlMapper(sqlName, objeto);
    }

}
