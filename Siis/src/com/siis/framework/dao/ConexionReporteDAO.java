/**
 * ConexionReporteDAO.java
 */
package com.casewaresa.framework.dao;



import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;

import com.casewaresa.framework.dao.impl.DaoStandard;

/**
 * @author Marlene Rojas
 * @date 22/01/2009
 * @name ConexionReporteDAO.java
 * @desc Proporciona la Conexión requerida para la ejecución de los reportes
 */
public class ConexionReporteDAO extends DaoStandard {
	
    /**
     * @type   Método de la clase ConexionReporteDAO
     * @name   getConexionReporte
     * @return Connection
     * @return Connection
     * @throws Exception
     * @desc   Retorna la conexión necesaria para la ejecución de un reporte
     */
    public Connection getConexionReporte() throws Exception{
    	
    	SqlSession session = sqlSessionFactory.openSession(true);
    	//--- obtenemos conexiones por demanda, no del pool de conexiones    	
    	return session.getConnection();
    }
}
