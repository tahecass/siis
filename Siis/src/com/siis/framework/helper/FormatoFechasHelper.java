package com.casewaresa.framework.helper;


/**
 * @author Fabio Barón
 * @date 24/01/2009
 * @name FormatoNumeros.java
 * @desc Este helper ayuda a convertir y formatear fechas
 */
public class FormatoFechasHelper {
	
	/**
	 * @type   Método de la clase FormatoFechas
	 * @name   formatoTiempoTranscurrido
	 * @return String
	 * @param elapsedTime
	 * @desc   este metodo formatea un tiempo (long) transcurrido en un formato legible
	 */
	public static String formatoTiempoTranscurrido (long elapsedTime){
	    
	    // Get elapsed time in seconds
	    int elapsedTimeSec = (int) (elapsedTime/1000F);
	    
	    // Get elapsed time in minutes
	    int elapsedTimeMin = (int) (elapsedTime/(60*1000F));
	    
	    // Get elapsed time in hours
	    int elapsedTimeHour = (int) (elapsedTime/(60*60*1000F));
	    
	    
	    return (elapsedTimeHour + " Hora(s) " + elapsedTimeMin + " minuto(s) " + elapsedTimeSec + " segundo(s)");
	}
	
}