/**
 * ExportadorReporte.java
 */
package com.casewaresa.framework.reportEngine.ExportadoresJasper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.zkoss.util.media.AMedia;

/**
 * @author Fabio Bar�n 
 * @date Nov 8, 2007
 * @name ExportadorReporte.java
 * @desc Define el comportamiento de los exportadores de reportes con JasperReports
 */
@SuppressWarnings("rawtypes")
public interface IExportadorReporteHelper {
	

	/**
	 * @type   M�todo de la clase IExportadorReporteHelper
	 * @name   generarReporte
	 * @return JasperPrint
	 * @param rutaReporte
	 * @param conn
	 * @param propiedades
	 * @desc   Este m�todo se encarga de esportar un reporte utilizando la API nativa de jasperReports
	 */
	public abstract void generarReporte (String rutaReporte, Connection conn, HashMap propiedades, List colsToDelete) 
					throws Exception;

	
	/**
	 * @type   M�todo de la clase IExportadorReporteHelper
	 * @name   generarReporte
	 * @return byte []
	 * @param rutaReporte
	 * @param propiedades
	 * @param colsToDelete
	 * @desc   Este metodo se encarga de generar el reporte y retornarlo en un array de bytes con el fin de 
	 * 			almacenarlo en un BLOB en la BD
	 */
	public abstract byte [] generarReporteBytes (String rutaReporte, HashMap propiedades, List colsToDelete) 
					throws Exception;
	
	/**
	 * @type   M�todo de la clase IExportadorReporteHelper
	 * @name   exportar
	 * @return AMedia
	 * @param jasperPrint
	 * @desc   Este m�todo se encarga de exportar el reporte al formato requerido
	 */
	public abstract AMedia exportarAMedia ()
					throws Exception;
	
	
	/**
	 * @type   M�todo de la clase IExportadorReporteHelper
	 * @name   getContentType
	 * @return String
	 * @desc   Este metodo define el tipo de salida que tendr� el exportador.
	 */
	public abstract String getTipoContenido();
	
	
	/**
	 * @type   M�todo de la clase IExportadorReporteHelper
	 * @name   cargarCompilarReporte
	 * @return void
	 * @param rutaReporte
	 * @param conn
	 * @param propiedades
	 * @desc   Este m�todo se encarga de cargar y compilar un reporte jasper
	 */
	/*public abstract JasperPrint cargarCompilarReporte (String rutaReporte, Connection conn,HashMap propiedades)
					throws Exception;*/
}
