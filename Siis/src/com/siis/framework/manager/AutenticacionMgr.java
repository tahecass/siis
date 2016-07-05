/**
 * AutenticacionMgr.java
 */
package com.casewaresa.framework.manager;

import com.casewaresa.framework.dao.AutenticacionDAO;
import com.casewaresa.framework.dto.Autenticacion;


/**
 * @author Fabio Barón
 * @date 4/12/2008
 * @name AutenticacionMgr.java
 * @desc --
 */

public class AutenticacionMgr extends ManagerStandard {

	/**
	 * @type   Método de la clase AutenticacionMgr
	 * @name   autenticarUsuario
	 * @return Autenticacion
	 * @param usuario
	 * @throws Exception
	 * @desc   M�todo que autentica a un usuario
	 */
	public Autenticacion autenticarUsuario (Autenticacion usuario) throws Exception{
	
		//Obtemos la referencia a la clase DAO encargada del acceso a la Vista
		AutenticacionDAO autenticacionDAO = new AutenticacionDAO(); 
		
		return (Autenticacion) autenticacionDAO.obtenerRegistroPorLLave( usuario );		
	}
}
