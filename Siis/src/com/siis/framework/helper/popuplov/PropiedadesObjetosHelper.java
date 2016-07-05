package com.casewaresa.framework.helper.popuplov;

import java.lang.reflect.Field;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author Caseware
 * @date Dic 13, 2006
 * @name Fondo.java
 * @desc Clase que ayuda a consultar las propiedades de una clase
 */
public class PropiedadesObjetosHelper {

	/** desc esta es la variable [ log ] de la clase [ AccesorPropiedadesHelper.java ]*/
	protected static Logger log = Logger.getLogger(PropiedadesObjetosHelper.class);

    /**
	 * @type   M�todo de la clase AccesorPropiedadesHelper
	 * @name   getPropiedades
	 * @return Field[]
     * @throws IllegalAccessException 
     * @throws Exception 
	 * @desc   obtiene y hace accesibles las propiedades de un objeto
	 */
	public Vector<Field> getPropiedadesFiltradas ( Object objetoConsulta ) throws Exception{		
		log.debug("ejecutando el m�todo [ getPropiedadesFiltradas ]");

		Vector<Field> camposFiltrados = new Vector<Field>();
		//--obtenemos las propiedades del objeto
		Field fields[] = new AccesorPropiedadesHelper().getPropiedades( objetoConsulta );		
		
		//--filtramos solo a las que necesitamos; en esta versi�n solo tipo:
		//-- java.lang.Number
		//-- java.lang.String
	    
		for (int i = 0; i < fields.length; i++) {
	    	
	    	if (fields[i].get(objetoConsulta) instanceof java.lang.Number) {
	    		//--este campo deber�a estar en la tabla
	    		camposFiltrados.add( fields[i] );
	    		//log.info("tipo --> " + fields[i].getGenericType());
	    	}
	    	else if ( fields[i].getType().getName().equals("java.lang.String") ) {
	    		//--este campo deber�a estar en la tabla
	    		camposFiltrados.add( fields[i] );
	    		//log.info("tipo --> " + fields[i].getGenericType());
	    	}
		}	
	 
	 //--los retornamos	
	 return camposFiltrados;
	}
	
    /**
	 * @type   M�todo de la clase AccesorPropiedadesHelper
	 * @name   getPropiedades
	 * @return Field[]
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws Exception 
	 * @desc   obtiene el valor de la propiedad del objeto dado
	 */
	public Object getValorPropiedad ( Object objetoConsulta,String campo ) throws IllegalArgumentException, IllegalAccessException{		
		log.debug("ejecutando el m�todo [ getValorPropiedad ]");

		//--obtenemos las propiedades del objeto
		Field fields[] = new AccesorPropiedadesHelper().getPropiedades( objetoConsulta );		
		
		//--filtramos solo a las que necesitamos; en esta versi�n solo tipo:
		//-- java.lang.Number
		//-- java.lang.String
	    
		for (int i = 0; i < fields.length; i++) {
			
	    	if (fields[i].getName().equals(campo)) {
	    		//--este es el campo el cual debo hallar el valor	    		
	    		//log.info("Campo encontrado --> " + fields[i].getName() + " = " + fields[i].get(objetoConsulta));
	    		
	    		return fields[i].get(objetoConsulta); 
	    	}
		}	
	 //--los retornamos	
	 return null;
	}		
	
    /**
	 * @type   M�todo de la clase AccesorPropiedadesHelper
	 * @name   getPropiedades
	 * @return Field[]
     * @throws IllegalAccessException 
     * @throws Exception 
	 * @desc   obtiene el valor de la propiedad del objeto dado
	 */
	public String getClasePropiedad ( Object objetoConsulta,String campo ){		
		log.debug("ejecutando el m�todo [ getValorPropiedad ]");

		//--obtenemos las propiedades del objeto
		Field fields[] = new AccesorPropiedadesHelper().getPropiedades( objetoConsulta );		
		
		for (int i = 0; i < fields.length; i++) {
			
	    	if (fields[i].getName().equals(campo)) {
	    		//--este es el campo el cual debo hallar el valor	    		
	    		//log.info("Campo encontrado --> " + fields[i].getName() + " = " + fields[i].get(objetoConsulta));
	    		
	    		return fields[i].getType().getName(); 
	    	}
		}	
	 //--los retornamos	
	 return null;
	}	
}
