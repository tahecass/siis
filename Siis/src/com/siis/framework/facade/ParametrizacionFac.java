package com.casewaresa.framework.facade;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.casewaresa.framework.manager.ParametrizacionMgr;

public class ParametrizacionFac {

    /** desc: Esta clase es singlenton */
    private static final ParametrizacionFac parametrizacionFac = new ParametrizacionFac();

    protected static Logger log = Logger.getLogger(ParametrizacionFac.class);

    /**
     * @type Constructor de la clase ParametrizacionFac
     * @name ParametrizacionFac
     * @return void
     * @desc Crea una instancia de ParametrizacionFac
     */
    private ParametrizacionFac() {
	super();
    }

    /**
     * @type M�todo de la clase ParametrizacionFac
     * @name ParametrizacionFac
     * @return LogginFacade
     * @desc permite obtener la instancia del objeto ParametrizacionFac
     *       (singlenton)
     */
    public static ParametrizacionFac getFacade() {
	return parametrizacionFac;
    }

    /****************************************************************************************/
    /** METODOS DE LA FACHADA */
    /****************************************************************************************/

    public Object obtenerListado(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.obtenerListado(qryName, objeto);
    }

    public Object obtenerListado(String qryName) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.obtenerListado(qryName);
    }

    public Object obtenerRegistro(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.obtenerRegistro(qryName, objeto);
    }

    public Object obtenerRegistro(String qryName) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.obtenerRegistro(qryName);
    }

    public void guardarRegistro(String qryName, Object objeto) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	parametrizacionMgr.insertarRegistro(qryName, objeto);
    }

    public void actualizarRegistro(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	parametrizacionMgr.actualizarRegistro(qryName, objeto);
    }

    public void borrarRegistro(String qryName, Object objeto) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	parametrizacionMgr.borrarRegistro(qryName, objeto);
    }

    public Object ejecutarProcedimiento(String qryName, Object objeto)
	    throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.ejecutarProcedimiento(qryName, objeto);
    }

    public Object ejecutarProcedimiento(String qryName) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.ejecutarProcedimiento(qryName);
    }

    public Object validarSQL(String sql) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.validarSQL(sql);
    }

    public <T> List<T> listadoDinamico(Object objeto, String sql)
	    throws Exception {
	com.casewaresa.framework.manager.ParametrizacionMgr parametrizacionMgr = new com.casewaresa.framework.manager.ParametrizacionMgr();
	return parametrizacionMgr.listadoDinamico(objeto, sql);
    }

    public <T> List<T> listadoDinamico(Object objeto, String sql,
	    Map<String, Object> parametros) throws Exception {
	com.casewaresa.framework.manager.ParametrizacionMgr parametrizacionMgr = new com.casewaresa.framework.manager.ParametrizacionMgr();
	return parametrizacionMgr.listadoDinamico(objeto, sql, parametros);
    }

    public Object obtenerDinamico(Object objeto, String sql) throws Exception {
	com.casewaresa.framework.manager.ParametrizacionMgr parametrizacionMgr = new com.casewaresa.framework.manager.ParametrizacionMgr();
	return parametrizacionMgr.obtenerDinamico(objeto, sql);
    }

    public Object obtenerListado(String qryName, Object objeto,
	    final int omitir, final int maximo) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.obtenerListado(qryName, objeto, omitir,
		maximo);
    }

    public Object obtenerListado(String qryName, final int omitir,
	    final int maximo) throws Exception {
	ParametrizacionMgr parametrizacionMgr = new ParametrizacionMgr();
	return parametrizacionMgr.obtenerListado(qryName, omitir, maximo);
    }
    
    public <T> List<T> listadoDinamico(Object objeto, String sql,
	    final int omitir,final int maximo)throws Exception {
	com.casewaresa.framework.manager.ParametrizacionMgr parametrizacionMgr = new com.casewaresa.framework.manager.ParametrizacionMgr();
	return parametrizacionMgr.listadoDinamico(objeto, sql,omitir,maximo);
    }

    public <T> List<T> listadoDinamico(Object objeto, String sql,Map<String, Object> parametros,
	    final int omitir,final int maximo) throws Exception {
	com.casewaresa.framework.manager.ParametrizacionMgr parametrizacionMgr = new com.casewaresa.framework.manager.ParametrizacionMgr();
	return parametrizacionMgr.listadoDinamico(objeto, sql, parametros,omitir,maximo);
    }
    
    public Object obtenerDinamico(String sql, Map<String, Object> parametros)throws Exception{
	com.casewaresa.framework.manager.ParametrizacionMgr parametrizacionMgr = new com.casewaresa.framework.manager.ParametrizacionMgr();
	return parametrizacionMgr.obtenerDinamico(sql, parametros);
    }
    
    /**
     * Metodo para obtener una consulta sql de un mapper(XML)
     * @param sqlName
     * @param objeto
     * @return
     * @throws Exception
     */
    public String obtenerSqlMapper(String sqlName, Object objeto)throws Exception{
	com.casewaresa.framework.manager.ParametrizacionMgr parametrizacionMgr = new com.casewaresa.framework.manager.ParametrizacionMgr();
	return parametrizacionMgr.obtenerSqlMapper(sqlName, objeto);
    }

}