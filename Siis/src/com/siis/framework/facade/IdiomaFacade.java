package com.casewaresa.framework.facade;

import java.util.HashMap;

/**
 * @author CaseWare
 * @date Oct 31, 2007
 * @name IdiomaFacade.java
 * @desc Esta fachada es la puerta de entrada a la parte de internacionalizaci�n de la aplicaci�n
 */
public class IdiomaFacade {
	
	  /** desc:  Esta clase es singlenton*/
	private static final IdiomaFacade Idiomafachada = new IdiomaFacade();
    
	/**
	 * @type   Constructor de la clase LogginFacade
	 * @name   LogginFacade 
	 * @return void
	 * @desc   Crea una instancia de LogginFacade
	 */	
    private IdiomaFacade() {
    	super();
    }
    
    /**
	 * @type   M�todo de la clase LogginFacade
	 * @name   getLogginFacade
	 * @return LogginFacade
	 * @desc   permite obtener la instancia del objeto LogginFacade (singlenton)
	 */
    public static IdiomaFacade getFacade ()
    {
      return Idiomafachada;
    }

    /**
     * @type   M�todo de la clase IdiomaFacade
     * @name   CosultarIdioma
     * @return String
     * @param ruta
     * @param llave
     * @throws Exception
     * @desc   Este m�todo retorna el nombre del lenguaje en la que la aplicaci�n 
     * 		   mostrar� los mensajes
     */
    public String ConsultarIdioma(String ruta, String llave)throws Exception     {
    	
    	return "";    	   
    } 

    /**
     * @type   M�todo de la clase IdiomaFacade
     * @name   CargarIdioma
     * @return HashMap
     * @param ruta
     * @throws Exception
     * @desc   Este m�todo se en carga de cargar el archivo de internacionalizaci�n
     */    
	public HashMap<String,Object> CargarIdioma(String ruta)throws Exception {
    	           	
    	return  null;    	   
    }
    
    
    /**
     * @type   M�todo de la clase IdiomaFacade
     * @name   getLabel
     * @return String
     * @param key
     * @desc   Este m�todo se encarga de obtener el valor del KEY especificado.
     */
    public static  String getLabel(String key) {
       
    	return key;  	   
    }
    
}
