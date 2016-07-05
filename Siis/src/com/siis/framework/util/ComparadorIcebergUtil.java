package com.casewaresa.framework.util;

import org.apache.log4j.Logger;
import org.zkoss.zul.Column;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.impl.HeaderElement;
import org.zkoss.zul.impl.HeadersElement;

import com.casewaresa.framework.util.FechasUtil.FORMATOS;
import com.casewaresa.framework.zk.control.comparator.ComparadorIceberg.TipoOrdenamiento;
import com.casewaresa.framework.zk.control.comparator.impl.ComparadorIcebergFecha;
import com.casewaresa.framework.zk.control.comparator.impl.ComparadorIcebergNumero;
import com.casewaresa.framework.zk.control.comparator.impl.ComparadorIcebergSeleccionables;

/**
 * Clase de utilidad para el ordenamiento de un componente columna
 * @author CASEWAREDES05
 *
 */
public class ComparadorIcebergUtil {
    private static Logger log = Logger.getLogger(ComparadorIcebergUtil.class);
    /**
     * Agregar el ordenamiento a un componente columna
     * @param componenteColumna Componente columna al que se va a agregar el ordenamiento
     * @param tipoOrdenamiento Indica que tipo de ordenamiento se le va a asignar @see {@link TipoOrdenamiento}
     * @param columna Hace referencia al indice de la columna a la que se le va a hacer el ordenamiento
     * @param formato Formato de la fecha para las comparaciones
     */
    public static void ordenamientoFecha(HeaderElement componenteColumna, TipoOrdenamiento tipoOrdenamiento, Integer columna, FORMATOS formato){
	log.debug("Ejecutando método [ ordenamientoFecha() ]");
	log.trace("HeaderElement ==> "+componenteColumna);
	log.trace("TipoOrdenamiento ==> "+tipoOrdenamiento);
	log.trace("columna ==> "+columna);
	log.trace("FORMATOS ==> "+formato);
	switch (tipoOrdenamiento) {
	
	case AMBOS:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortAscending(new ComparadorIcebergFecha(true, formato, columna));
		((Listheader) componenteColumna).setSortDescending(new ComparadorIcebergFecha(false, formato, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortAscending(new ComparadorIcebergFecha(true, formato, columna));
		((Column) componenteColumna).setSortDescending(new ComparadorIcebergFecha(false, formato, columna));
	    }
	    break;
	    
	case ASCENDENTE:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortAscending(new ComparadorIcebergFecha(true, formato, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortAscending(new ComparadorIcebergFecha(true, formato, columna));
	    }
	    break;
	
	case DESCENDENTE:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortDescending(new ComparadorIcebergFecha(false, formato, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortDescending(new ComparadorIcebergFecha(false, formato, columna));
	    }
	    break;
	}
    }
    
    /**
     * Agregar el ordenamiento a un componente columna
     * @param componenteColumna Componente columna al que se va a agregar el ordenamiento
     * @param tipoOrdenamiento Indica que tipo de ordenamiento se le va a asignar @see {@link TipoOrdenamiento}
     * @param columna Hace referencia al indice de la columna a la que se le va a hacer el ordenamiento
     * @param pattern Patron del formateador para las comparaciones. Se le manda vacio en el patron para trabajar con el patron por defecto
     */
    
    public static void ordenamientoNumero(HeaderElement componenteColumna, TipoOrdenamiento tipoOrdenamiento, Integer columna, String pattern){
	log.debug("Ejecutando método [ ordenamientoNumero() ]");
	log.trace("HeaderElement ==> "+componenteColumna);
	log.trace("TipoOrdenamiento ==> "+tipoOrdenamiento);
	log.trace("columna ==> "+columna);
	log.trace("pattern ==> "+pattern);
	switch (tipoOrdenamiento) {
	
	case AMBOS:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortAscending(new ComparadorIcebergNumero(true, pattern, columna));
		((Listheader) componenteColumna).setSortDescending(new ComparadorIcebergNumero(false, pattern, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortAscending(new ComparadorIcebergNumero(true, pattern, columna));
		((Column) componenteColumna).setSortDescending(new ComparadorIcebergNumero(false, pattern, columna));
	    }
	    break;
	    
	case ASCENDENTE:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortAscending(new ComparadorIcebergNumero(true, pattern, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortAscending(new ComparadorIcebergNumero(true, pattern, columna));
	    }
	    break;
	
	case DESCENDENTE:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortDescending(new ComparadorIcebergNumero(false, pattern, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortDescending(new ComparadorIcebergNumero(false, pattern, columna));
	    }
	    break;
	}
    }
   
    
    
    /**
     * Agregar el ordenamiento a un componente columna
     * @param componenteColumna Componente columna al que se va a agregar el ordenamiento
     * @param tipoOrdenamiento Indica que tipo de ordenamiento se le va a asignar @see {@link TipoOrdenamiento}
     * @param columna Hace referencia al indice de la columna a la que se le va a hacer el ordenamiento
     */
    
    public static void ordenamientoSeleccionables(HeaderElement componenteColumna, TipoOrdenamiento tipoOrdenamiento, Integer columna){
	log.debug("Ejecutando método [ ordenamientoSeleccionables() ]");
	log.trace("HeaderElement ==> "+componenteColumna);
	log.trace("TipoOrdenamiento ==> "+tipoOrdenamiento);
	log.trace("columna ==> "+columna);
	
	switch (tipoOrdenamiento) {
	
	case AMBOS:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortAscending(new ComparadorIcebergSeleccionables(true,columna));
		((Listheader) componenteColumna).setSortDescending(new ComparadorIcebergSeleccionables(false, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortAscending(new ComparadorIcebergSeleccionables(true, columna));
		((Column) componenteColumna).setSortDescending(new ComparadorIcebergSeleccionables(false, columna));
	    }
	    break;
	    
	case ASCENDENTE:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortAscending(new ComparadorIcebergSeleccionables(true, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortAscending(new ComparadorIcebergSeleccionables(true, columna));
	    }
	    break;
	
	case DESCENDENTE:
	    if(componenteColumna instanceof Listheader){
		((Listheader) componenteColumna).setSortDescending(new ComparadorIcebergSeleccionables(false, columna));
	    }else if(componenteColumna instanceof Column){
		((Column) componenteColumna).setSortDescending(new ComparadorIcebergSeleccionables(false, columna));
	    }
	    break;
	}
    }
    
    /**
     * Agregar el ordenamiento a un conjunto de componentes o columnas
     * @param componenteColumnas Componente columnas al que se va a agregar el ordenamiento
     * @param tipoOrdenamiento Indica que tipo de ordenamiento se le va a asignar @see {@link TipoOrdenamiento}
     * @param columnas Hace referencia un array de indices de las columnas a las que se le va a hacer el ordenamiento
     * @param formato Formato de la fecha para las comparaciones
     */
    public static void agregarOrdenamientoFechas(HeadersElement componenteColumnas, TipoOrdenamiento tipoOrdenamiento,FORMATOS formato, Integer ...columnas){
	log.debug("Ejecutando método [ ordenamientoSeleccionables() ]");
	log.trace("HeaderElement ==> "+componenteColumnas);
	log.trace("TipoOrdenamiento ==> "+tipoOrdenamiento);
	log.trace("FORMATOS ==> "+formato);
	log.trace("columnas ===> "+columnas);
	for(Integer columna : columnas){
	    HeaderElement headerElement = (HeaderElement)componenteColumnas.getChildren().get(columna);
	    ordenamientoFecha(headerElement, tipoOrdenamiento, columna, formato);
	}
    }
    
    /**
     * Agregar el ordenamiento a un conjunto de componentes o columnas
     * @param componenteColumnas  Componente columnas al que se va a agregar el ordenamiento
     * @param tipoOrdenamiento Indica que tipo de ordenamiento se le va a asignar @see {@link TipoOrdenamiento}
     * @param columnas Hace referencia un array de indices de las columnas a las que se le va a hacer el ordenamiento
     * @param pattern Patron del formateador para las comparaciones. Se le manda vacio en el patron para trabajar con el patron por defecto
     */
    
    public static void agregarOrdenamientoNumeros(HeadersElement componenteColumnas, TipoOrdenamiento tipoOrdenamiento,String pattern, Integer ...columnas ){
	log.debug("Ejecutando método [ ordenamientoSeleccionables() ]");
	log.trace("HeaderElement ==> "+componenteColumnas);
	log.trace("TipoOrdenamiento ==> "+tipoOrdenamiento);
	log.trace("pattern ==> "+pattern);
	log.trace("columnas ===> "+columnas);
	for(Integer columna : columnas){
	    HeaderElement headerElement = (HeaderElement)componenteColumnas.getChildren().get(columna);
	    ordenamientoNumero(headerElement, tipoOrdenamiento, columna, pattern);
	}
    }
    
}
