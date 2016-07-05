/**
 * ExportadorHtml.java
 */
package com.casewaresa.framework.reportEngine.ExportadoresJasper.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.zkoss.util.media.AMedia;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.facade.ReportesFacade;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.ExportadorReporteStandard;

/**
 * @author Fabio E. Bar�n
 * @date Nov 9, 2007
 * @name ExportadorHtml.java
 * @desc Esta clase tiene la logica para exportar un reporte a formato HTML
 */
@SuppressWarnings("rawtypes")
public class ExportadorHtml extends ExportadorReporteStandard {

	
	/**
	 * @type   Constructor de la clase ExportadorHtml
	 * @name   ExportadorHtml
	 * @param pExportadorReporteStandard
	 * @desc   Este es el contructor de la clase, y en este caso tiene un parametro
	 * 		   tipo ExportadorReporteStandard, y si este valor es nulo no hace nada
	 * 		   pero si es diferente a nulo, quiere decir que ya ejecutaron un reporte
	 * 		   por tanto solo debe exportarlo
	 */	
	public ExportadorHtml ( ExportadorReporteStandard pExportadorReporteStandard ) {
		if (pExportadorReporteStandard != null){
			 //-- si pExportadorReporteStandard es diferente de nulo entonces asignamos	
			 super.jasperPrint = pExportadorReporteStandard.getJasperPrint();
			 super.nombreReporte = pExportadorReporteStandard.getNombreReporte();
			}
		}

	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#generarReporte(java.lang.String, java.sql.Connection, java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public void generarReporte(String rutaReporte, Connection conn,HashMap propiedades, List colsToDelete) 
				throws Exception {

		//ContextoAplicacion pContextoAplicacion = ContextoAplicacion.getInstance();
		//String destino = pContextoAplicacion.getRutaContexto() + IConstantes.REP_RUTA_IMAGENES_TEMP;
		
		//--estas l�neas solo se ejecutan siempre y cuando se corra un reporte por primera vez
		if (super.jasperPrint == null){
			
			//preparamos los par�metros del reporte			 
			configurarParametrosPredefinidos (propiedades, IConstantes.REP_FORMATO_SALIDA_HTML); 
	
			//---compilamos el reporte
			//super.jasperPrint = super.cargarCompilarReporte(rutaReporte, conn, propiedades);
		}	
		
		///--exportamos el reporte a formato HTML
		JRHtmlExporter exporter = new JRHtmlExporter();
		
		//--verificamos que siempre haya un stream abierto antes de sacar un reporte
		if (super.out == null )
			super.out = new ByteArrayOutputStream();
		
		//-- configuramos la exportaci�n
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, super.out);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,  Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,"c:\\fuentes\\");//repositorio/imagenes
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"file:\\c:\\fuentes\\");//repositorio/imagenes
	
	
		
		//--si existe el numero de inicio de la p�gina lo agregamos si no, lo dejamos sin ese par�metro
		if (propiedades.get( IConstantes.REP_INDICE_INICIAL_PAGINA ) != null)
		  exporter.setParameter(JRExporterParameter.START_PAGE_INDEX
				                ,(Integer)propiedades.get( IConstantes.REP_INDICE_INICIAL_PAGINA)) ;
		
		//--si existe el numero de fin de la p�gina lo agregamos si no, lo dejamos sin ese par�metro
		if (propiedades.get( IConstantes.REP_INDICE_FINAL_PAGINA) != null)
			  exporter.setParameter(JRExporterParameter.END_PAGE_INDEX
					                ,(Integer)propiedades.get( IConstantes.REP_INDICE_FINAL_PAGINA)) ;
		
		//-- finalmente exportamos ese reporte
		exporter.exportReport();	
		/**
		 * NOTA: la expresi�n anterior deja el resultado en la propiedad [ out ] de la clase padre
		 *       por eso no retorna nada. 
		 * */
	}

	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#exportarAMedia(java.io.ByteArrayOutputStream)
	 */
	public AMedia exportarAMedia(){

		AMedia aMedia = null;
		
		//--configuramos el objeto amedia
		aMedia = new AMedia( super.nombreReporte + "." + IConstantes.REP_EXTESION_ARCHIVO_HTML 
							 ,IConstantes.REP_FORMATO_SALIDA_HTML
							 ,null
							 ,super.out.toByteArray()
							);
		
		return aMedia; 	
	}
	
	
	/**
	 * @type   M�todo de la clase ExportadorHtml
	 * @name   generarReporte
	 * @return byte []
	 * @param rutaReporte
	 * @param propiedades
	 * @param colsToDelete 
	 * @desc   Este metodo se encarga de generar el reporte en html y retornarlo en un array de bytes con el fin de 
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
		
		return "text/html";
	}

	
}
