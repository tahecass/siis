package com.casewaresa.framework.helper;

import java.util.HashMap;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name Lenguaje.java
 * @desc Agrupa toda la funcionalidad para el Lenguaje
 */
@SuppressWarnings("rawtypes")
public class LenguajeHelper {

	/** Creates a new instance of Configuracion */
    public static final LenguajeHelper lenguajeAplicacion = new LenguajeHelper();
    
    //---hash map que guarda todo el lenguaje de la aplicaci�n    
	private HashMap lenguaje = null;

    /**
     * @type   Constructor de la clase Lenguaje
     * @name   Lenguaje
     * @desc   crea una instancia de la clase
     */
    private LenguajeHelper(){
    	//...
    }
    
    /**
     * @type   M�todo de la clase Lenguaje
     * @name   getLenguaje
     * @return Lenguaje
     * @desc   obtiene el lenguaje en el cual esta la aplicaci�n
     */
    public static LenguajeHelper getLenguaje() {
        return lenguajeAplicacion;
    }

	/**
	 * @type   M�todo de la clase Lenguaje
	 * @name   setLenguaje
	 * @return void
	 * @param lenguaje
	 * @desc   actualiza el lenguaje al 
	 */
	public void setLenguaje(HashMap lenguaje) {
		this.lenguaje = lenguaje;
	}

	         
	 /**
	 * @type   M�todo de la clase Lenguaje
	 * @name   retornarlabel
	 * @return String
	 * @param  key
	 * @desc   Obtiene un valir internacionalizado a partir de su llave key
	 */
	public String retornarlabel(String key) {

  	  //---si existe la llave la retorna si no retorna null
	  return (String) lenguaje.get(key);
	}

	/**
	 * @type   M�todo de la clase LenguajeHelper
	 * @name   getLenguajeAplicacion
	 * @return void
	 * @param lenguajeAplicacion
	 * @desc   obtiene el valor de la propiedad lenguajeAplicacion
	 */
	public HashMap getDatosLenguaje() {
		return lenguaje;
	}
}