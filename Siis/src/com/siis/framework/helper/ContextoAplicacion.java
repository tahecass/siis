/**
 * ContextoAplicacion.java
 */
package com.casewaresa.framework.helper;

/**
 * @author Fabio E. Bar�n
 * @date Nov 9, 2007
 * @name ContextoAplicacion.java
 * @desc Esta clase se encarga de mantener propiedades comunes a la aplicaci�n
 * 		 adem�s esta clase es singleton con esto queda a disposici�n de consultarla
 * 		 desde donde se requiera
 */

public class ContextoAplicacion {
    /** Propiedad pContextoAplicacion de la clase [ ContextoAplicacion.java ] 
     *  @desc: tiene una referencia a la �nica instancia  de la clase*/
    public static final ContextoAplicacion pContextoAplicacion = new ContextoAplicacion();

	/** Propiedad rutaContexto de la clase [ ContextoAplicacion.java ] 
	 *  @desc: guarda la ruta del contexto de la aplicaci�n */
	private String rutaContexto = "";

    /**
     * @type   Constructor de la clase ContextoAplicacion
     * @name   ContextoAplicacion
     * @desc   Esta clase tiene su constructor privado por ser singleton
     */	
    private ContextoAplicacion(){
    	//...
    }	
	
    
    /**
     * @type   M�todo de la clase ContextoAplicacion
     * @name   getInstance
     * @return ContextoAplicacion
     * @desc   permite acceder al aunica instancia de esta clase
     */
    public static ContextoAplicacion getInstance() {
        return pContextoAplicacion;
    }    
	/**
	 * @type   M�todo de la clase ContextoAplicacion
	 * @name   getRutaContexto
	 * @return void
	 * @param rutaContexto
	 * @desc   obtiene el valor de la propiedad rutaContexto
	 */
	public String getRutaContexto() {
		return rutaContexto;
	}

	/**
	 * @type   M�todo de la clase ContextoAplicacion
	 * @name   setRutaContexto
	 * @return String
	 * @param rutaContexto
	 * @desc   Actualiza el valor de la propiedad rutaContexto
	 */
	public void setRutaContexto(String rutaContexto) {
		this.rutaContexto = rutaContexto;
	}
}
