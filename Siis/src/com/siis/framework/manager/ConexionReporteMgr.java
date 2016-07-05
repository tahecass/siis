/**
 * ConexionReporteMgr.java
 */
package com.casewaresa.framework.manager;


import java.sql.Connection;

import com.casewaresa.framework.dao.ConexionReporteDAO;


/**
 * @author Marlene Rojas
 * @date 22/01/2009
 * @name ConexionReporteMgr.java
 * @desc --
 */

public class ConexionReporteMgr extends ManagerStandard {
	private ConexionReporteDAO conexionReporteDAO = new ConexionReporteDAO();

	/**
	 * @type   Método de la clase ConexionReporteMgr
	 * @name   obtenerConexionReporte
	 * @return Connection
	 * @return Conecction
	 * @throws Exception
	 * @desc   Retorna la conexión necesaria para realizar la ejecución de un reporte.
	 */
	public Connection obtenerConexionReporte()throws Exception{
		return this.conexionReporteDAO.getConexionReporte();
	}
}
