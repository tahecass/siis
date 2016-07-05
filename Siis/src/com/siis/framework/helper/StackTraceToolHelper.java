/**
 * StackTraceToolHelper.java
 */
package com.casewaresa.framework.helper;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * @author Fabio Bar�n
 * @date 15/01/2007
 * @name StackTraceToolHelper.java
 * @desc permite manipular el stacktrace de una excepci�n
 */
public class StackTraceToolHelper {
		
	/** Propiedad log de la clase [ StackTraceToolHelper.java ] 
	 *  @desc: permite la visualizaci�n de mensajes en la salida configurada opr log4j */
	protected static Logger log = Logger.getLogger( StackTraceToolHelper.class );

	/**
	 * @type   M�todo de la clase StackTraceToolHelper
	 * @name   getStack2String
	 * @return String
	 * @param e
	 * @return
	 * @desc   este m�todo pasa un objeto StackTrace a un String
	 */
	public static String getStack2String (Exception exception){
		 	
			if (exception == null){
				return "Excepción nula!!";
			}
		
			//--obtenemos un writer de tipo String en el que se volcar� el contenido del stacktrace
			StringWriter sw = new StringWriter();
			
			//--obtenemos el printeriter a partir del String Writer
			PrintWriter pw = new PrintWriter(sw);
			
			//--volcamos el contenido del stacktrace al writer anteriormente generado..			
			exception.printStackTrace(pw);
			
			//--retornamos el objeto String con el contenido del StackTrace
			return sw.toString();
	}
}
