package com.casewaresa.framework.helper.popuplov;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/**
 * @author Caseware
 * @date Dic 13, 2006
 * @name Fondo.java
 * @desc Clase que ayuda a consultar las propiedades de una clase
 */
public class AccesorPropiedadesHelper {

	/** desc esta es la variable [ log ] de la clase [ AccesorPropiedadesHelper.java ]*/
	protected static Logger log = Logger.getLogger(AccesorPropiedadesHelper.class);
	
	
    /**
	 * @type   Método de la clase AccesorPropiedadesHelper
	 * @name   getPropiedades
	 * @return Field[]
	 * @desc   obtiene y hace accesibles las propiedades de un objeto
	 */
	public Field[] getPropiedades (Object obj){		
		log.debug("ejecutando el método [ getPropiedades ]");
		
		Field fields[] = null;		
		
	    //---obtenemos las propiedades de un objeto
		fields = obj.getClass().getDeclaredFields();
		
		//--los hacemos accesibles
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
		}
	 
	 //--los retornamos	
	 return fields;
	}
}
