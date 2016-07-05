package com.casewaresa.framework.facade;

import com.casewaresa.framework.dto.Autenticacion;
import com.casewaresa.framework.manager.AutenticacionMgr;


/**
 * @author Oscar Villamil
 * @date Dec 3, 2008
 * @name CotizacionFacade.java
 * @desc Esta fachada es la puerta de entrada a la logica del negocio para la autenticación
 */
public class AutenticacionFacade {

	/** desc:  Esta clase es singlenton*/
	private static final AutenticacionFacade pAutenticacionFacade = new AutenticacionFacade();

	
	/**
	 * @type   Constructor de la clase CotizacionFacade
	 * @name   LogginFacade 
	 * @return void
	 * @desc   Crea una instancia de CotizacionFacade
	 */	
    private AutenticacionFacade() {
    	super();
    }
    
    
    /**
	 * @type   Metodo de la clase CotizacionFacade
	 * @name   getCOTParametrizacionFacade
	 * @return LogginFacade
	 * @desc   permite obtener la instancia del objeto CotizacionFacade (singlenton)
	 */
    public static AutenticacionFacade getFacade ()
    {
      return pAutenticacionFacade;
    }

/****************************************************************************************/
/**				 				METODOS DE LA FACHADA 								   **/
/****************************************************************************************/
    
    /**
     * @type   Método de la clase AutenticacionFacade
     * @name   autenticarUsuario
     * @return Autenticacion
     * @param usuario
     * @throws Exception
     * @desc   método para autenticar un usuario a la aplicción
     */
    public Autenticacion autenticarUsuario (Autenticacion usuario) throws Exception{
    	
    	AutenticacionMgr autenticacionMgr = new AutenticacionMgr();
    	return autenticacionMgr.autenticarUsuario( usuario );
    }

    
}
