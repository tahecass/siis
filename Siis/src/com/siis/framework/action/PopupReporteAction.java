/**
 * PopupReporte.java
 */
package com.siis.framework.action;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Toolbarbutton;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ReportesFacade;
import com.casewaresa.framework.helper.JasperReportEngineHelper;

/**
 * @author Fabio E. Baron
 * @date Oct 29, 2007
 * @name PopupReporte.java
 * @desc  Esta clase se en carga de ejecutar y manipular los reportes generados
 */
public class PopupReporteAction extends ActionStandard {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = -6892393583260336275L;


	/** Propiedad log de la clase [ LiquidacionRubro.java ] 
	 *  @desc: provee un mecanismo para el manejo de mensajes */
	protected static Logger log = Logger.getLogger(PopupReporteAction.class);

	
	/** Propiedad pJasperReportEngineHelper de la clase [ PopupReporteAction.java ] 
	 *  @desc: esta propiedad me da acceso al reporte generado */
	private JasperReportEngineHelper pJasperReportEngineHelper = new JasperReportEngineHelper();;
	
	private String     rutaReporte  = "";
	private String     formato      = ""; //xls,html,pdf,rtf
	private Connection conn         = null;
	private HashMap<String,Object>    parametros   = null;
	private List<Object> 	   colsToDelete = null;
	/**
	 * @type   M�todo de la clase PopupReporte
	 * @name   Parametros
	 * @return void
	 * @param reportPath
	 * @param conn
	 * @param properties
	 * @param format
	 * @desc   Este m�todo actualiza los par�metros neecasrios para correr el reporte
	 */
	public void setParametros (String rutaReporte, Connection conn, HashMap<String,Object> parametros, List<Object> colsToDelete, String formato){
		this.rutaReporte  = rutaReporte; 
		this.conn         = conn;
		this.parametros   = parametros;
		this.colsToDelete = colsToDelete;
		this.formato      = formato;
	}
	
	
	/* (non-Javadoc)
	 * @see org.zkoss.zul.Window#doOverlapped()
	 */
	@Override
	public void doOverlapped() {
		log.info("ejecutando [ doOverlapped ]... ");
		
		AMedia pAMedia = null;
		Iframe controlIframe = null;
		Date horaInicio  = null;
		Date horaFin     = null;
		
		//--tomamos la hora en la que se inicio el reporte
		horaInicio  = Calendar.getInstance().getTime();
		//horaInicio = new Date();
		log.info("inicio : " + horaInicio.getTime() + " - " + horaInicio.toString());
		
		try {
			//-- obtenemos referencia al control que mostrar� el reporte
			controlIframe = (Iframe)this.getFellow("resultadoReporte");
			
			//--ejecutamos el reporte...
			pAMedia = pJasperReportEngineHelper.generarReporte(rutaReporte, conn, parametros, colsToDelete, formato);
			
			//--si pAMedia retorn� != null, quiere decir que se gener� un reporte de lo contrario vendr�a
			//--vacio
			if (pAMedia == null){
				//--se remueve el previsualizador y mostramos un mensaje informativo
				this.detach();
				
				//--creamos un popuplov
				ReporteVacioAction win = (ReporteVacioAction)Executions.createComponents(
															"/iucomunes/reportevacio.zul"
															, null
															, null);
				win.setParametros( parametros );
				win.setPosition("center");				
				//mostramos el reporte en una ventana nueva
				win.doModal();		
				
			}
			
			super.setMaximizable(true);
			//--mostramos una ventana emergente
			super.doOverlapped();
			
			//-- creamos la nueva venatana		
			controlIframe.setContent( pAMedia );
			controlIframe.invalidate();

			//--tomamos la hora de terminado 
			horaFin = Calendar.getInstance().getTime();
			log.info("horaFin : " + horaFin.getTime() + " - " + horaFin.toString());
			
			//-- mostramos el tiempo que dur�
			Toolbarbutton toolBarLabelTiempoEjecucion = (Toolbarbutton)this.getFellow("toolBarLabelTiempoEjecucion");
			SimpleDateFormat timedf = new SimpleDateFormat("mm' min' :ss' seg'");
			toolBarLabelTiempoEjecucion.setLabel("Tiempo de Ejecución: " + timedf.format( new Date(horaFin.getTime() - horaInicio.getTime())));
			
			//-- mostramos el numero de hojas
			Label numHojas = (Label)this.getFellow("labelNumeroHojas");
			numHojas.setValue(" " + pJasperReportEngineHelper.getNumeroHojas() + " ");
			
			((Listbox)this.getFellow("lbxFormato")).setSelectedIndex(pJasperReportEngineHelper.getFormato());
		} 
		catch (Exception e) {
//			this.detach();
			log.error(e.getMessage(),e);
	//		super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL,e));
		}
	}

	@Override
	public void doModal() {
		log.info("ejecutando [ doModal ]... ");
		
		AMedia pAMedia = null;
		Iframe controlIframe = null;
		Date horaInicio  = null;
		Date horaFin     = null;
		
		//--tomamos la hora en la que se inicio el reporte
		horaInicio  = Calendar.getInstance().getTime();
		//horaInicio = new Date();
		log.info("inicio : " + horaInicio.getTime() + " - " + horaInicio.toString());
		
		try {
			//-- obtenemos referencia al control que mostrar� el reporte
			controlIframe = (Iframe)this.getFellow("resultadoReporte");
			
			//--ejecutamos el reporte...
			pAMedia = pJasperReportEngineHelper.generarReporte(rutaReporte, conn, parametros, colsToDelete, formato);
			
			//--si pAMedia retorn� != null, quiere decir que se gener� un reporte de lo contrario vendr�a
			//--vacio
			if (pAMedia == null){
				//--se remueve el previsualizador y mostramos un mensaje informativo
				this.detach();
				
				//--creamos un popuplov
				ReporteVacioAction win = (ReporteVacioAction)Executions.createComponents(
															"/iucomunes/reportevacio.zul"
															, null
															, null);
				win.setParametros( parametros );
				win.setPosition("center");				
				//mostramos el reporte en una ventana nueva
				win.doModal();		
				
			}
			
			super.setMaximizable(true);
			//--mostramos una ventana emergente
			super.doModal();
			
			//-- creamos la nueva venatana		
			controlIframe.setContent( pAMedia );
			controlIframe.invalidate();

			//--tomamos la hora de terminado 
			horaFin = Calendar.getInstance().getTime();
			log.info("horaFin : " + horaFin.getTime() + " - " + horaFin.toString());
			
			//-- mostramos el tiempo que dur�
			Toolbarbutton toolBarLabelTiempoEjecucion = (Toolbarbutton)this.getFellow("toolBarLabelTiempoEjecucion");
			SimpleDateFormat timedf = new SimpleDateFormat("mm' min' :ss' seg'");
			toolBarLabelTiempoEjecucion.setLabel("Tiempo de Ejecución: " + timedf.format( new Date(horaFin.getTime() - horaInicio.getTime())));
			
			//-- mostramos el numero de hojas
			Label numHojas = (Label)this.getFellow("labelNumeroHojas");
			numHojas.setValue(" " + pJasperReportEngineHelper.getNumeroHojas() + " ");
			
			((Listbox)this.getFellow("lbxFormato")).setSelectedIndex(pJasperReportEngineHelper.getFormato());
		} 
		catch (Exception e) {
//			this.detach();
			log.error(e.getMessage(),e);
	//		super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL,e));
		}
	}

	/**
	 * @type   M�todo de la clase PopupReporteAction
	 * @name   exportarReporte
	 * @return void
	 * @param formato
	 * @desc   este m�todo se encarga de exportar el preview del reporte al formato seleccioando
	 */
	public void exportarReporte (String formato){
	  log.info(" reporte exportado a " + formato);

	    try {
			AMedia pAMedia = null;
			
			//-- por bug tenemos que adquirir de nuevo una conexi�n
			this.conn = ReportesFacade.getFacade().obtenerConexionReporte();
			
			//--ejecutamos el reporte...
			pAMedia = pJasperReportEngineHelper.generarReporte(rutaReporte, conn, parametros, colsToDelete, formato);
			
			//-utilizamos el API de zk para bajar el reporte
			Filedownload.save(pAMedia);
			
		} 
	    catch (Exception e) {
	    	log.error(e.getMessage(),e);
	    	super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL,e));
		}

	}
	
	public void fijarFormato( ){
		try {
			Listbox lbxFormato = (Listbox)this.getFellow("lbxFormato");
			String formatoPagina = ((Listitem)lbxFormato.getSelectedItem()).getValue().toString(); 
			log.info("Formato: " + formatoPagina);

			if(formatoPagina.equals(IConstantes.REP_LEGAL)){// LEGAL
				this.parametros.put("PAGE_WIDTH", new Integer("612"));
				this.parametros.put("PAGE_HEIGHT", new Integer("1008"));
			}else if(formato.equals(IConstantes.REP_A3)){// A3
				this.parametros.put("PAGE_WIDTH", new Integer("842"));
				this.parametros.put("PAGE_HEIGHT", new Integer("1190"));			
			}else if(formato.equals(IConstantes.REP_A4)){// A4
				this.parametros.put("PAGE_WIDTH", new Integer("595"));
				this.parametros.put("PAGE_HEIGHT", new Integer("842"));			
			}else if(formato.equals(IConstantes.REP_A5)){// A5
				this.parametros.put("PAGE_WIDTH", new Integer("421"));
				this.parametros.put("PAGE_HEIGHT", new Integer("595"));			
			}else{// DEFAULT: LETTER
				this.parametros.put("PAGE_WIDTH", new Integer("612"));
				this.parametros.put("PAGE_HEIGHT", new Integer("792"));
			}
			
			this.parametros.put("COMPILER", true );
			this.conn = ReportesFacade.getFacade().obtenerConexionReporte();
			this.doOverlapped();
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_FORMATO_DE_NUMERO,e));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL,e));
		}
	}
	
	public void fijarZoom(){
		Listbox lbxZoom = (Listbox)this.getFellow("lbxZoom");
		Float zoom = new Float(((Listitem)lbxZoom.getSelectedItem()).getValue().toString());
		
		try {
			this.parametros.put(IConstantes.REP_ZOOM_PAGINA, zoom);
			this.parametros.put("COMPILER", null );
			this.doOverlapped();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL,e));
		}
	}
	
	/**
	 * @type   M�todo de la clase PopupReporteAction
	 * @name   cambiarRangoHojas
	 * @return void
	 * @desc   este m�todo se encarga de cambiar el rango de las hojas
	 */
	public void cambiarRangoHojas (Integer op){
		AMedia pAMedia = null;
		Iframe controlIframe = null;
		Intbox valorIndice = null;
		
		try {
			//-- obtenemos referencia al control que mostrar� el reporte
			controlIframe = (Iframe)this.getFellow("resultadoReporte");
			
			valorIndice = (Intbox)getFellow("repIndiceHoja");  //indexado en 1..2..n
			
			if(valorIndice.getValue() == null)
				valorIndice.setValue(new Integer(1));
			
			switch (op) {
			case 1:
				valorIndice.setValue(new Integer(1));
				break;
			case 2:
				valorIndice.setValue(valorIndice.getValue()+1);
				break;
			case 3:
				valorIndice.setValue(valorIndice.getValue()-1);
				break;
			case 4:
				valorIndice.setValue(pJasperReportEngineHelper.getNumeroHojas());
				break;
			}

			//---cambiamos las hojas del properties...
			
			Integer valorRealIndice = valorIndice.getValue()-1;       //indexado en 0..1..n
						
			//-- corregimos el valor
			if (valorRealIndice > pJasperReportEngineHelper.getNumeroHojas()-1){
				valorIndice.setValue(new Integer (pJasperReportEngineHelper.getNumeroHojas()));
				valorRealIndice = pJasperReportEngineHelper.getNumeroHojas()-1; 
			}
			
			if (valorRealIndice < 1){
				valorIndice.setValue(new Integer (1));
				valorRealIndice = 0;
			}
			
			//--actualizamos los par�metros
			parametros.put(IConstantes.REP_INDICE_PAGINA, valorRealIndice);
			this.fijarZoom();
			//--- mandamos renderizar el reporte de nuevo
			pAMedia = pJasperReportEngineHelper.generarReporte(rutaReporte, conn, parametros, colsToDelete, formato);
			
			//-- creamos la nueva venatana		
			controlIframe.setContent( pAMedia );
			controlIframe.invalidate();
			
		} 
		catch (Exception e) {	
			log.error(e.getMessage(),e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL,e));
		} 
	}
	
	public void refreshView(){
		this.doOverlapped();
	}
}
