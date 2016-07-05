/**
 * PropiedadesLDAPHelper.java
 */
package com.casewaresa.framework.helper;

import java.util.HashMap;

/**
 * @author Caseware Ingenier�a
 * @date Sep 21, 2007
 * @name PropiedadesLDAPHelper.java
 * @desc Clase encargada de contener las propiedades de conexion al servidor LDAP 
 */
@SuppressWarnings("rawtypes")
public class PropiedadesLDAPHelper {
	
	
	/** Creates a new instance of Configuracion */
    public static final PropiedadesLDAPHelper propiedadesLDAP = new PropiedadesLDAPHelper();
    
    private static HashMap propiedades=null;


    
    /**
     * @type   Constructor de la clase PropiedadesLDAPHelper
     * @name   PropiedadesLDAPHelper
     * @desc   crea una instancia de la clase
     */
    private PropiedadesLDAPHelper(){
    	//...
    }
    
    /**
     * @type   M�todo de la clase PropiedadesLDAPHelper
     * @name   getPropiedadesLDAPHelper
     * @return PropiedadesLDAPHelper
     * @desc   obtiene el PropiedadesLDAPHelper en el cual esta la aplicaci�n
     */
    public static PropiedadesLDAPHelper getPropiedadesLDAPHelper() {
        return propiedadesLDAP;
    }

	/**
	 * @type   M�todo de la clase PropiedadesLDAPHelper
	 * @name   getPropiedades
	 * @return void
	 * @param propiedades
	 * @desc   obtiene el valor de la propiedad propiedades
	 */
	public static HashMap getPropiedades() {
		return propiedades;
	}

	/**
	 * @type   M�todo de la clase PropiedadesLDAPHelper
	 * @name   setPropiedades
	 * @return HashMap
	 * @param propiedades
	 * @desc   Actualiza el valor de la propiedad propiedades
	 */
	public static void setPropiedades(HashMap propiedades) {
		PropiedadesLDAPHelper.propiedades = propiedades;
	}

}
