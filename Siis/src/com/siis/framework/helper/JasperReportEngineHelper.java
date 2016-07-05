/**
 * ReporteTest.java
 */
package com.casewaresa.framework.helper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.ExportadorReporteStandard;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.impl.ExportadorHtml;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.impl.ExportadorImagen;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.impl.ExportadorPdf;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.impl.ExportadorXls;

/**
 * @author CaseWare
 * @date Oct 29, 2007
 * @name ReporteTest.java
 * @desc motor que genera los reportes
 */
@SuppressWarnings({"rawtypes"})
public class JasperReportEngineHelper {

	/** Propiedad pExportadorReporteStandard de la clase [ JasperReportEngineHelper.java ] 
	 *  @desc: mantenemos una referencia hacia el reporte generado */
	private ExportadorReporteStandard pExportadorReporteStandard = null;

	/** Propiedad log de la clase [ JasperReportEngineHelper.java ] 
	 *  @desc: provee un mecanismo para el manejo de mensajes */
	protected static Logger log = Logger.getLogger(JasperReportEngineHelper.class);	
	
	/**
	 * @type   M�todo de la clase JasperReportEngineHelper
	 * @name   generateReport
	 * @return AMedia
	 * @param reportPath
	 * @param conn
	 * @param properties
	 * @param format
	 * @throws Exception 
	 * @desc   Este m�todo se encarga de esportar un reporte utilizando la API nativa de jasperReports
	 */
	public AMedia generarReporte(String rutaReporte, Connection conn, HashMap parametros, List colsToDelete, String formato) throws Exception { 
		log.info("[generando reporte...]");
		AMedia aMedia = null;

		 //-- cargamos, compilamos y ejecutamos el reporte y lo exportamos segun el par�metro formato  
		 if (formato.equals( IConstantes.REP_FORMATO_SALIDA_XLS) ){
			 pExportadorReporteStandard = new ExportadorXls( pExportadorReporteStandard );
		 }else if (formato.equals( IConstantes.REP_FORMATO_SALIDA_PDF )){ 
			 pExportadorReporteStandard = new ExportadorPdf( pExportadorReporteStandard );
		 }else if (formato.equals( IConstantes.REP_FORMATO_SALIDA_HTML )){
			 pExportadorReporteStandard = new ExportadorHtml( pExportadorReporteStandard );
		 }else if (formato.equals( IConstantes.REP_FORMATO_SALIDA_IMAGEN )){
			 pExportadorReporteStandard = new ExportadorImagen( pExportadorReporteStandard );
		 }
		 //-- agregar aqu� m�s salidas de reportes.... csv, rtf, image, ...
		 else{ 
			//-- si no esta el formato enviamos error
			throw new Exception ("Formato de reporte no soportado por la aplicación [ " + formato + " ]");
		 }
		 
		 ///--generamos el reporte (outpustream)
		 String name = pExportadorReporteStandard.getNombreReporte().substring(0, 7) + (parametros.get(IConstantes.REP_INDICE_PAGINA)!=null?parametros.get(IConstantes.REP_INDICE_PAGINA):"");
		 log.info("Imagen Name: " + name);
		 pExportadorReporteStandard.setNombreReporte(name);

		 pExportadorReporteStandard.generarReporte(rutaReporte, conn, parametros, colsToDelete);
		 
		 //--generamos el objeto aMedia que es el que Zk entiende como contenido
		 aMedia = pExportadorReporteStandard.exportarAMedia(  );
		 
		 		
		 //--lo retornamos
		 return aMedia; 
	}
	
	public int getFormato(){
		int pageWidth  = pExportadorReporteStandard.getJasperPrint().getPageWidth();
		int pageHeight = pExportadorReporteStandard.getJasperPrint().getPageHeight();
		
		if((pageWidth == 612 && pageHeight == 1008)||(pageWidth == 1008 && pageHeight == 612))
			return 1;//LEGAL
		else if((pageWidth == 842 && pageHeight == 1190)||(pageWidth == 1190 && pageHeight == 842))
			return 2;//A3
		else if((pageWidth == 592 && pageHeight == 842)||(pageWidth == 842 && pageHeight == 592))
			return 3;//A4
		else if((pageWidth == 421 && pageHeight == 595)||(pageWidth == 595 && pageHeight == 421))
			return 4;//A5
		else
			return 0;//LETTER		
	}
	
	/**
	 * @type   M�todo de la clase JasperReportEngineHelper
	 * @name   getNumeroHojas
	 * @return int
	 * @desc   Este m�todo me retorna el numero ed hojas del reporte
	 */
	public int getNumeroHojas (){
		return pExportadorReporteStandard.getJasperPrint().getPages().size();
	}
	
}
