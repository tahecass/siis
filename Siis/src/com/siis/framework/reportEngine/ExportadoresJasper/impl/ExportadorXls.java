/**
 * ExportadorXls.java
 */
package com.casewaresa.framework.reportEngine.ExportadoresJasper.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.zkoss.util.media.AMedia;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.facade.ReportesFacade;
import com.casewaresa.framework.helper.DynamicJasperEngineHelper;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.ExportadorReporteStandard;

/**
 * @author Diego Roedriguez
 * @date Nov 9, 2007
 * @name ExportadorXls.java
 * @desc Esta clase implementa la l�gica para exportar los reportes a EXCEL (plano en JasperDinamic)
 */
@SuppressWarnings("rawtypes")
public class ExportadorXls extends ExportadorReporteStandard {

	/**
	 * @type   Constructor de la clase ExportadorXls
	 * @name   ExportadorXls
	 * @param pExportadorReporteStandard
	 * @desc   Este es el contructor de la clase, y en este caso tiene un parametro
	 * 		   tipo ExportadorReporteStandard, y si este valor es nulo no hace nada
	 * 		   pero si es diferente a nulo, quiere decir que ya ejecutaron un reporte
	 * 		   por tanto solo debe exportarlo
	 */	
	public ExportadorXls(ExportadorReporteStandard pExportadorReporteStandard) {
		if (pExportadorReporteStandard != null){
			 //-- si pExportadorReporteStandard es diferente de nulo entonces asignamos	
			 super.jasperPrint = pExportadorReporteStandard.getJasperPrint();
			 super.nombreReporte = pExportadorReporteStandard.getNombreReporte();
			}
		}

	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#exportarAMedia()
	 */
	public AMedia exportarAMedia() throws Exception {
		
		AMedia aMedia = null;
		
		//--configuramos el objeto amedia
		aMedia = new AMedia( super.nombreReporte + "." + IConstantes.REP_EXTESION_ARCHIVO_XLS
							 ,IConstantes.REP_FORMATO_SALIDA_XLS
							 ,null
							 ,super.out.toByteArray()
							);
		
		return aMedia;
	}

	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#generarReporte(java.lang.String, java.sql.Connection, java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public void generarReporte(String rutaReporte, Connection conn,HashMap propiedades, List colsToDelete) 
			    throws Exception {

		//-- Este exportador es una excepción a la regla ya que a pesar de que el reporte
		//-- a pesar de provisualizarse, se debe ejecutar de nuevo ya que esta vez 
		//-- el jasperprint no es generado por la API de jasper sino por dinamic jasper

		//-- como este exportador cambia totalmente el jasperprint entones generamos uno
		//-- nuevo solo para este caso, de lo contrario al intentar exportar el reporte a 
		//-- otros formatos ya quedaría sin columnas
		JasperPrint neoJasperPrint  = null;
		
		//obtenemos una nueva conexión
		conn = ReportesFacade.getFacade().obtenerConexionReporte();
		
		//preparamos los parámetros del reporte			 
		configurarParametrosPredefinidos (propiedades, IConstantes.REP_FORMATO_SALIDA_XLS);

		//--compilado con dinamic jasper
		DynamicJasperEngineHelper pDynamicJasperEngineHelper =  new DynamicJasperEngineHelper();
		neoJasperPrint = pDynamicJasperEngineHelper.generarCoumnas(rutaReporte, conn, propiedades, colsToDelete);

		//--verificamos que siempre haya un stream abierto antes de sacar un reporte
		if (super.out == null )
			super.out = new ByteArrayOutputStream();

		JRXlsExporter exporter = new JRXlsExporter(); 
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, neoJasperPrint); 
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, super.out);
		
		exporter.exportReport();
	}

	
	/**
	 * @type   M�todo de la clase ExportadorXls
	 * @name   generarReporte
	 * @return byte []
	 * @param rutaReporte
	 * @param propiedades
	 * @param colsToDelete Columnas que se desean borrar del reporte en xls
	 * @desc   Este metodo se encarga de generar el reporte en XLS y retornarlo en un array de bytes con el fin de 
	 * 			almacenarlo en un BLOB en la BD
	 */
	public  byte[] generarReporteBytes(String rutaReporte, HashMap parametros, List colsToDelete)throws Exception{
		
		Connection conn = ReportesFacade.getFacade().obtenerConexionReporte();
		
		generarReporte(rutaReporte, conn, parametros, colsToDelete);
		
		return super.out.toByteArray();
	}	
	
	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#getTipoContenido()
	 */
	public String getTipoContenido() {
		
		return "application/vnd.ms-excel";
		
	}

}
