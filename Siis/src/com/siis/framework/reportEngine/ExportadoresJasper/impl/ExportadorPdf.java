/**
 * ExportadorPdf.java
 */
package com.casewaresa.framework.reportEngine.ExportadoresJasper.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.zkoss.util.media.AMedia;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.facade.ReportesFacade;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.ExportadorReporteStandard;

/**
 * @author Fabio E. Barón
 * @date Nov 8, 2007
 * @name ExportadorPdf.java
 * @desc Esta clase se en carga de exportar el reporte a formato PDF
 */
@SuppressWarnings({"rawtypes"})
public class ExportadorPdf extends ExportadorReporteStandard {

	/**
	 * @type   Constructor de la clase ExportadorPdf
	 * @name   ExportadorPdf
	 * @param pExportadorReporteStandard
	 * @desc   Este es el contructor de la clase, y en este caso tiene un parametro
	 * 		   tipo ExportadorReporteStandard, y si este valor es nulo no hace nada
	 * 		   pero si es diferente a nulo, quiere decir que ya ejecutaron un reporte
	 * 		   por tanto solo debe exportarlo
	 */	
	public ExportadorPdf(ExportadorReporteStandard pExportadorReporteStandard)throws Exception {
		if (pExportadorReporteStandard != null){
		 //-- si pExportadorReporteStandard es diferente de nulo entonces asignamos	
		System.out.println("[generando reporte... pdf]"); 
		super.jasperPrint = pExportadorReporteStandard.getJasperPrint();
		 super.nombreReporte = pExportadorReporteStandard.getNombreReporte();
		}
	}

	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#generarReporte(java.lang.String, java.sql.Connection, java.util.HashMap)
	 */
	
	@SuppressWarnings({ "unchecked" })
	public void generarReporte(String rutaReporte, Connection conn, HashMap propiedades, List colsToDelete) throws Exception {

		//--estas líneas solo se ejecutan siempre y cuando se corra un reporte por primera vez
		if (super.jasperPrint == null || propiedades.get("COMPILER")!=null){
			//preparamos los parámetros del reporte			 
			configurarParametrosPredefinidos (propiedades, IConstantes.REP_FORMATO_SALIDA_PDF);
			
			
			
			JasperDesign jasperDesign= super.cargarReporte(rutaReporte,propiedades);
//			System.out.println("orientacion despues:"+jasperDesign.getOrientation());
			
			
			super.jasperPrint = super.compilarReporte(conn,propiedades, jasperDesign);
			
//			System.out.println("jasperPrint :"+super.jasperPrint.getOrientation());
			
		}
		
		
		
		
		//--verificamos que siempre haya un stream abierto antes de sacar un reporte
		if (super.out == null )
			super.out = new ByteArrayOutputStream();
		
		//--- finalmente exportamos ese reporte
		JasperExportManager.exportReportToPdfStream(jasperPrint, super.out);
		/**
		 * NOTA: la expresión anterior deja el resultado en la propiedad [ out ] de la clase padre
		 *       por eso no retorna nada. 
		 */		
	}

	
	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#exportarAMedia()
	 */
	public AMedia exportarAMedia() throws Exception {

		AMedia aMedia = null;
		
		//--configuramos el objeto amedia
		aMedia = new AMedia( super.nombreReporte + "." + IConstantes.REP_EXTESION_ARCHIVO_PDF
							 ,IConstantes.REP_FORMATO_SALIDA_PDF
							 ,null
							 ,super.out.toByteArray()
							);
		
		return aMedia;		
	}
	
	
	
	/**
	 * @type   M�todo de la clase ExportadorXls
	 * @name   generarReporte
	 * @return byte []
	 * @param rutaReporte
	 * @param propiedades
	 * @param colsToDelete no aplica para pdf puede ir null
	 * @desc   Este metodo se encarga de generar el reporte en PDF y retornarlo en un array de bytes con el fin de 
	 * 			almacenarlo en un BLOB en la BD
	 */
	public  byte[] generarReporteBytes(String rutaReporte, HashMap parametros, List colsToDelete)throws Exception{
		System.out.println("generando Bytes []");
		
		Connection conn = ReportesFacade.getFacade().obtenerConexionReporte();
		try
		{
			generarReporte(rutaReporte, conn, parametros, colsToDelete);
		}finally{
			//nos aseguramos que la conexion este cerrada
			if(!conn.isClosed())
				conn.close();
		}
		
		return super.out.toByteArray();
	}
	
	
	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#getTipoContenido()
	 */
	public String getTipoContenido() {
		
		return "application/pdf";
		
	}

	
}
