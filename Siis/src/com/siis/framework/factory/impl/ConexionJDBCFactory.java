/**
 * ConexionFactory.java
 */
package com.casewaresa.framework.factory.impl;

import com.casewaresa.framework.factory.FactoryStandard;

/**
 * @author Fabio Bar�n
 * @date Jun 25, 2007
 * @name ConexionFactory.java
 * @desc Esta f�brica se enacarga de instanciar clases de tipo conexiones JDBC
 */
public class ConexionJDBCFactory extends FactoryStandard {

	/**
	 * NOTAS: Este modelo fuciona de esta forma, este framework tiene predeterminado
	 * la configuraci�n del pool de conexiones proxool http://proxool.sourceforge.net
	 * la forma en que ser� utilizado es:
	 * 
	 * hay una clase que define el comportamiento de como se debe comportar un objeto
	 * que debe entregar conexiones a la base de datos que se llama "ConexionStandard"
	 * de hecho las nuevas clases que provean este servicio deber�n implementara. y 
	 * definir la forma en que obtendr�n las conexiones
	 * 
	 * Ahora �stas clases ser�n usadas en las clases que provean servicios de persistencia
	 * por ejemplo los DAO, ellos deber�n obtener un proveedor de servicios de conexiones
	 * JDBC por medio de un provider el cual a su vez, obtendr� el proveedor de conexiones
	 * JDBC que est� configurado en un contrato (esto porque obntendr� una instancia de la
	 * clase deseada por medio de un "Factory")
	 * 
	 * Esta f�brica est� encargada de crear onjetos con el prefijo Conexion:
	 *   - ConexionProxool
	 *   - ConexionXXXXX
	 *   - ConexionYYYY
	 */
	
	/** Propiedad TIPO_CLASE de la clase [ ProviderFactory.java ] 
	 *  @desc: define el postfijo utilizado para filtrar las clases que puede instanciar */
	final static String TIPO_CLASE = "Connection"; 

	@Override
	public Object getObject(String pObject) {
		
		if(pObject.endsWith(TIPO_CLASE)){
			return super.getObject(pObject);	
		}		
		else 
		{
			try {
				throw new Exception("Esta clase solo puede instanciar objetos de tipo Conexion");
			} 
			catch (Exception e) {
				log.error(e.getMessage(),e);
			} 
		}
		
		return null;
	}	
}
