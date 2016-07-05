/**
 * AutenticacionDAO.java
 */
package com.casewaresa.framework.dao;

import org.apache.ibatis.session.SqlSession;

import com.casewaresa.framework.dao.impl.DaoStandard;
import com.casewaresa.framework.dto.Autenticacion;

/**
 * @author Oscar Villamil
 * @date 01/03/2010
 * @name AutenticacionDAO.java
 * @desc clase que se especializa en el acceso a la base de datos, y que esta enfocada
 *       a la obtención de información de la funcion GEN_Autenticacion
 */
public class AutenticacionDAO extends DaoStandard {
	
	private SqlSession session;

	/* (non-Javadoc)
	 * @see com.casewaresa.framework.dao.IDao#obtenerRegistroPorLLave(java.lang.Object)
	 */
	public Object obtenerRegistroPorLLave(Autenticacion objeto) throws Exception {
		
		session = sqlSessionFactory.openSession();
		
		try{
			
			//AutenticacionMapper autenticacionMapper = session.getMapper(AutenticacionMapper.class);
			return session.selectOne("getAutenticacionUsuario",objeto);
			//return autenticacionMapper.getAutenticacionUsuario(objeto);
			
		}finally{
			
			session.close();
		}
		
		
	}
}
