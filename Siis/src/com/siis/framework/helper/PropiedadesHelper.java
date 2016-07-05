/**
 * PropiedadesHelper.java
 */
package com.casewaresa.framework.helper;

import java.util.Properties;

public class PropiedadesHelper {
    /** Creates a new instance of Configuracion */
    private static final PropiedadesHelper instance = new PropiedadesHelper();
    
    private Properties propiedades = null;

    private PropiedadesHelper(){ }
    
    /**
     * @type   Método de la clase PropiedadesHelper
     * @name   getInstance
     * @return PropiedadesHelper
     * @desc   obtiene el una instancia de PropiedadesHelper
     */
    public static PropiedadesHelper getInstance() {
        return instance;
    }

    /**
     * @type   Método de la clase PropiedadesHelper
     * @name   getPropiedades
     * @return void
     * @param propiedades
     * @desc   obtiene el valor de la propiedad propiedades
     */
    public Properties getPropiedades() {
	return propiedades;
    }

    /**
     * @type   Método de la clase PropiedadesHelper
     * @name   setPropiedades
     * @return HashMap
     * @param  propiedades
     * @desc   Actualiza el valor de la propiedad propiedades
     */
    public void setPropiedades(Properties propiedades) {
	this.propiedades = propiedades;
    }
    
    /**
     * @type   Método de la clase PropiedadesHelper
     * @name   getPropiedad
     * @return String
     * @param  String
     * @desc   Retorna el valor de una propiedad
     */
    public String getPropiedad(String propiedad){
	return propiedades.getProperty(propiedad);
    }
}
