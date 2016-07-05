/**
 * URLHelper.java
 */
package com.casewaresa.framework.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.dto.Autenticacion;

/**
 * @author Fabio bar�n
 * @date Oct 1, 2007
 * @name URLHelper.java
 * @desc Esta clase atrapa extiende de DHtmlLayoutServlet (ZK) para implementar la l�gica
 * 		 necesaria para evaluar el acceso a la aplicaci�n por medio de las URL cuando el 
 * 		 usuario esta o no autenticado
 */

@SuppressWarnings("serial")
public class AutenticacionURLHelper extends DHtmlLayoutServlet {

	/** desc esta es la variable [ log ] de la clase [ ExcepcionAction.java ]*/	
	protected static Logger log = Logger.getLogger(AutenticacionURLHelper.class);
	
	private  String modoOperacion = null;
	
	@Override
	protected boolean process(Session session, HttpServletRequest request, HttpServletResponse response, 
					String path, boolean bRichlet) throws ServletException, IOException {
		log.info("Ejecutando el metodo [ process ]... ");
		
		log.debug("Path: " + path);
		
		this.modoOperacion = PropiedadesHelper.getInstance().getPropiedad("ger.modoOperacion");
		log.debug("modoOperacion: " + this.modoOperacion);
		
		String form = null;
		
		if (modoOperacion.equals(IConstantes.MODO_MANTENIMIENTO)) {
		    form = IConstantes.PANTALLA_MANTENIMIENTO;
		} else {
		    if (request.getParameterMap().isEmpty()) {
		    	form = IConstantes.PANTALLA_AUTENTICACION;
		    } else {
		    	form = IConstantes.PANTALLA_ANONIMO;
		    }
		}
		
		log.debug("Initial Form: " + form);
		
		//--Preguntamos si la sessi�n es NULA! que no deber�a pasar
		if(session == null){
			log.info ("SESSION NULA: El usuario no podria estar autenticado ");
			return super.process(session, request, response, form, bRichlet);
		}else{
			//--y que si hay sessi�n preguntamos si hay un usuario autenticado 
			Autenticacion usuario = (Autenticacion) session.getAttribute(IConstantes.USUARIO_SESSION);
			
			//-- preguntamos si existe un usuario en la sessi�n
			if(usuario == null){
				//--si no quiere decir que alguien esta intentado acceder sin permiso
				log.info("USUARIO NULO: El usuario no ha sido autenticado");			
				return super.process(session, request, response, form, bRichlet);
			}else if (usuario.getAutenticado() == IConstantes.USUARIO_AUTENTICADO){
				//--si lo hay el usuario esta autenticado
				log.info ("OK: Usuario autenticado");
				return super.process(session, request, response, path, bRichlet);
			}else {
				//--si no quiere decir que alguien esta intentado acceder sin permiso
				log.info("DESCONOCIDO: El usuario no ha sido autenticado y no hay session");			
				return super.process(session, request, response, form, bRichlet);
			}
		}	
	}	
}
