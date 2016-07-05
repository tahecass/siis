/**
 * ExportadorImagen.java
 */
package com.casewaresa.framework.reportEngine.ExportadoresJasper.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.jfree.chart.encoders.SunPNGEncoderAdapter;
import org.zkoss.util.media.AMedia;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.reportEngine.ExportadoresJasper.ExportadorReporteStandard;

/**
 * @author Fabio Bar�n
 * @date Nov 13, 2007
 * @name ExportadorImagen.java
 * @desc Este exportador se encarga de mostrar el un reporte como im�gen
 */
@SuppressWarnings({"rawtypes"})
public class ExportadorImagen extends ExportadorReporteStandard {

		
	/**
	 * @type   M�todo de la clase ExportadorImagen
	 * @name   ExportadorHtml
	 * @return void
	 * @param pExportadorReporteStandard
	 * @desc   Este es el contructor de la clase, y en este caso tiene un parametro
	 * 		   tipo ExportadorReporteStandard, y si este valor es nulo no hace nada
	 * 		   pero si es diferente a nulo, quiere decir que ya ejecutaron un reporte
	 * 		   por tanto solo debe exportarlo
	 */
	public ExportadorImagen ( ExportadorReporteStandard pExportadorReporteStandard ) {
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
		
		//--miramos si se gener� alg�n reporte
		if (super.out ==  null){
			//--retornamos aMedia en nulo para demostar que no se gener� ningun reporte o result� vacio
			return aMedia; 
		}
		
		//--configuramos el objeto amedia
		aMedia = new AMedia( super.nombreReporte + "." + IConstantes.REP_EXTESION_ARCHIVO_IMAGEN 
							 ,IConstantes.REP_FORMATO_SALIDA_IMAGEN
							 ,null
							 ,super.out.toByteArray()
							);
		
		return aMedia;
	}

	/* (non-Javadoc)
	 * @see com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper#generarReporte(java.lang.String, java.sql.Connection, java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public void generarReporte(String rutaReporte, Connection conn,	HashMap propiedades, List colsToDelete) throws Exception {
		
		Integer pageIndex = new Integer(0);
		//int pageCount = 0;
		Float zoom = 1.2f;
		byte[] imageData = null;
		BufferedImage image = null;
				
		//--si existe el numero indice de p�gina lo agregamos si no, lo dejamos sin ese par�metro
		if (propiedades.get( IConstantes.REP_INDICE_PAGINA) != null){
			pageIndex = (Integer)propiedades.get( IConstantes.REP_INDICE_PAGINA);
			//pageIndex = new Integer(0);
		}

		//--si existe el zoom de p�gina lo agregamos si no, lo dejamos sin ese par�metro
		if (propiedades.get( IConstantes.REP_ZOOM_PAGINA) != null){
			zoom = (Float)propiedades.get( IConstantes.REP_ZOOM_PAGINA);
		}		
		
		
		
		if (super.jasperPrint == null || propiedades.get("COMPILER")!=null){
			
			//preparamos los par�metros del reporte			 
			configurarParametrosPredefinidos (propiedades, IConstantes.REP_FORMATO_SALIDA_IMAGEN); 
	

			
			
			@SuppressWarnings("unused")
			JasperDesign jasperDesign= super.cargarReporte(rutaReporte,propiedades);
			
//			System.out.println("jasperDesign orientation:"+jasperDesign.getOrientation());
//			//---compilamos el reporte
//			
//			super.jasperPrint = super.compilarReporte(conn,propiedades, jasperDesign);
//			System.out.println("jasperPrint orientation:"+jasperPrint.getOrientation());

		}
			
		
		
		
		
		
		
		
		//si el reporte no tiene p�ginas entonces no intentamos exportar nada
		if ( jasperPrint.getPages().size() <= 0 ){
			super.out = null;
			return;
		}
		System.out.println("File Name: " + jasperPrint.getName());
		
		image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageIndex, zoom);
		imageData = new SunPNGEncoderAdapter().encode(image);
		
		//--verificamos que siempre haya un stream abierto antes de sacar un reporte
		if (super.out == null )
			super.out = new ByteArrayOutputStream();
		
		//-- sacamos la im�gen por el outputstream
		super.out.write(imageData, 0, imageData.length);
		super.out.flush();
		super.out.close();		
		
		/**
		 * NOTA: la expresi�n anterior deja el resultado en la propiedad [ out ] de la clase padre
		 *       por eso no retorna nada. 
		 * */
	}

	/**
	 * @type   M�todo de la clase ExportadorHtml
	 * @name   generarReporte
	 * @return byte []
	 * @param rutaReporte
	 * @param propiedades
	 * @param colsToDelete 
	 * @desc   Este metodo no aplica para Imagen ya que se construyo para generar reportes en algun tipo con
	 *        el fin de poder guardarlos en la BD se implementa por que es obligatorio
	 */
	public  byte[] generarReporteBytes(String rutaReporte, HashMap parametros, List colsToDelete)throws Exception{
		
			
		return null;
	}
	
	public String getTipoContenido() {
		// FIXME poner el tipo de contenido		
		return null;
	}
}
