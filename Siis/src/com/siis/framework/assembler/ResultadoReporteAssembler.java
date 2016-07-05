/**
 * ReultadoReporteAssembler.java
 */
package com.casewaresa.framework.assembler;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.casewaresa.framework.action.PopupReporteAction;
import com.casewaresa.framework.contract.IConstantes;

/**
 * @author Fabio Baron
 * @date Oct 31, 2007
 * @name ReultadoReporteAssembler.java
 * @desc Este assembler se en carga de armar la ventana en la que semuestra el reporte
 */
public class ResultadoReporteAssembler {

	public static Window generarVentanaReporte(Page pagina,String rutaReporte, Connection conn, HashMap<String,Object> propiedades, List<Object> colsToDelete, String formato){
		
		final PopupReporteAction win = new PopupReporteAction();
		Iframe iframe = null;
		//win.addE
		///-- creamos el Iframe para mostrar el reporte
		iframe = new Iframe();
		iframe.setId("resultadoReporte");
		iframe.setHeight("90%");
		iframe.setWidth("100%");
		
		///---label Exportar
		Label labelExportar = new Label ();
		labelExportar.setValue("Exportar a: ");		
		
		///---botón exportación a pdf
		Button botonGenerarPdf = new Button();
		botonGenerarPdf.setId("botonGenerarPdf");
		botonGenerarPdf.setTooltip("Exportar a PDF");		
		botonGenerarPdf.setImage("/imagenes/reporte_pdf.gif");
		botonGenerarPdf.addEventListener(Events.ON_CLICK,
										  new EventListener<Event>(){  //--clase anónima para el manejo del evento
											public void onEvent(Event arg0) throws Exception {												
													win.exportarReporte( IConstantes.REP_FORMATO_SALIDA_PDF );
											}
										  }
										 );

		///---bot�n exportaci�n a xls		
		Button botonGenerarXls = new Button();
		botonGenerarXls.setId("botonGenerarXls");
		botonGenerarXls.setTooltip("Exportar a XLS");
		botonGenerarXls.setImage("/imagenes/reporte_xls.gif");
		botonGenerarXls.addEventListener(Events.ON_CLICK,
										  new EventListener<Event>(){  //--clase an�nima para el manejo del evento
											public void onEvent(Event arg0) throws Exception {												
													win.exportarReporte( IConstantes.REP_FORMATO_SALIDA_XLS );
											}
										  }
										 );		
		
		//---Label Formato
		Label labelFormato = new Label ();
		labelFormato.setValue(" Formato: ");
		//---Lista de Formatos
		Listbox lbxFormato = new Listbox();
		lbxFormato.setId("lbxFormato");
		lbxFormato.setMold("select");
		lbxFormato.setRows(1);
		lbxFormato.appendItem("Carta", IConstantes.REP_LETTER);
		lbxFormato.appendItem("Oficio", IConstantes.REP_LEGAL);
		lbxFormato.addEventListener(Events.ON_SELECT,
				  new EventListener<Event>(){  //--clase anonima para el manejo del evento
					public void onEvent(Event arg0) throws Exception {												
							win.fijarFormato();
					}
				  }
				 );		

		//---label set numero de hojas hoja
		Label labelHoja = new Label ();
		labelHoja.setValue( " Hoja: ");
		
		//--campos de texto
		Intbox getHojaInicial = new Intbox();
		getHojaInicial.setId("repIndiceHoja");
		getHojaInicial.setCols( 3 );
		getHojaInicial.setValue(new Integer(1));
		getHojaInicial.addEventListener(Events.ON_OK,
											new EventListener<Event>(){  //--clase an�nima para el manejo del evento
												public void onEvent(Event arg0) throws Exception {												
													win.cambiarRangoHojas(0);
												}
										  	}
										 );		

		Button botonFirst = new Button();
		botonFirst.setTooltiptext("Primera Hoja");
		botonFirst.setImage("/imagenes/resultset_first.png");
		botonFirst.addEventListener(Events.ON_CLICK,
										  new EventListener<Event>(){  //--clase an�nima para el manejo del evento
											public void onEvent(Event arg0) throws Exception {												
												win.cambiarRangoHojas(1); // Index 1 para primero
											}
										  }
										 );		

		Button botonBack = new Button();
		botonBack.setTooltiptext("Anterior Hoja");
		botonBack.setImage("/imagenes/resultset_previous.png");
		botonBack.addEventListener(Events.ON_CLICK,
										  new EventListener<Event>(){  //--clase an�nima para el manejo del evento
											public void onEvent(Event arg0) throws Exception {												
												win.cambiarRangoHojas(3); // Index 3 para anterior
											}
										  }
										 );
		
		Button botonNext = new Button();
		botonNext.setTooltiptext("Siguiente Hoja");
		botonNext.setImage("/imagenes/resultset_next.png");
		botonNext.addEventListener(Events.ON_CLICK,
										  new EventListener<Event>(){  //--clase an�nima para el manejo del evento
											public void onEvent(Event arg0) throws Exception {												
												win.cambiarRangoHojas(2); // Index 2 para siguiente
											}
										  }
										 );		

		Button botonLast = new Button();
		botonLast.setTooltiptext("Ultima Hoja");
		botonLast.setImage("/imagenes/resultset_last.png");
		botonLast.addEventListener(Events.ON_CLICK,
										  new EventListener<Event>(){  //--clase an�nima para el manejo del evento
											public void onEvent(Event arg0) throws Exception {												
												win.cambiarRangoHojas(4); // Index 4 para último
											}
										  }
										 );		
		
		//---Label Formato
		Label labelZoom = new Label ();
		labelZoom.setValue(" Zoom: ");
		//---Lista de Formatos
		Listbox lbxZoom = new Listbox();
		lbxZoom.setId("lbxZoom");
		lbxZoom.setMold("select");
		lbxZoom.setRows(1);
		lbxZoom.appendItem("50%","0.5");
		lbxZoom.appendItem("60%","0.6");
		lbxZoom.appendItem("70%","0.7");
		lbxZoom.appendItem("80%","0.8");
		lbxZoom.appendItem("90%","0.9");
		lbxZoom.appendItem("100%","1.0").setSelected(true);
		lbxZoom.appendItem("110%","1.1");
		lbxZoom.appendItem("120%","1.2");
		lbxZoom.appendItem("130%","1.3");
		lbxZoom.appendItem("140%","1.4");
		lbxZoom.appendItem("150%","1.5");
		lbxZoom.appendItem("160%","1.6");
		lbxZoom.appendItem("170%","1.7");
		lbxZoom.appendItem("180%","1.8");
		lbxZoom.appendItem("190%","1.9");
		lbxZoom.appendItem("200%","2.0");

		lbxZoom.addEventListener(Events.ON_SELECT,
				  new EventListener<Event>(){  //--clase an�nima para el manejo del evento
					public void onEvent(Event arg0) throws Exception {												
							win.fijarZoom();
					}
				  }
				 );	
		
		///---tiempo 
		Label labela = new Label ();
		labela.setValue( " de " );
		
		///---label del total de hojas
		Label labelNumeroHojas = new Label ();
		labelNumeroHojas.setId("labelNumeroHojas");
		labelNumeroHojas.setValue(" 0 ");		
				
		///---tiempo de ejecución
		Toolbar tbTiempoEjecucion = new Toolbar();
		tbTiempoEjecucion.setAlign("end");
		tbTiempoEjecucion.setHeight("15px");

		Toolbarbutton toolBarLabelTiempoEjecucion = new Toolbarbutton("");
		toolBarLabelTiempoEjecucion.setId("toolBarLabelTiempoEjecucion");
		
		tbTiempoEjecucion.appendChild(toolBarLabelTiempoEjecucion);
		
		///---separador 
		Separator separator = new Separator();
		separator.setBar(true);
		
		///---separador2 
		Separator separator2 = new Separator();
		separator2.setBar(true);

		//--configuramos la ventana 
		win.setPage     (pagina);
		win.setPosition("center");
		win.setHeight   ("600px");
		win.setWidth    ("800px");
		
		win.setParametros(rutaReporte, conn, propiedades, colsToDelete, formato);
		win.setSizable  (true);
		win.setClosable (true);
		win.setBorder   ("normal");
		win.setTitle    ("Vista Previa del Reporte");	
		
		//--agregamos el reporte
		win.appendChild( labelExportar );
		win.appendChild( botonGenerarPdf );
		win.appendChild( botonGenerarXls );
		win.appendChild( labelFormato );
		win.appendChild( lbxFormato );
		win.appendChild( labelHoja );
		win.appendChild( botonFirst );
		win.appendChild( botonBack );
		win.appendChild( getHojaInicial );
		win.appendChild( labela );	
		win.appendChild( labelNumeroHojas );
		win.appendChild( botonNext );
		win.appendChild( botonLast );
		win.appendChild( labelZoom );
		win.appendChild( lbxZoom );
		
		//--aqu� el reporte
		win.appendChild( separator );
		win.appendChild( iframe );	
		win.appendChild( separator2 );
		win.appendChild(tbTiempoEjecucion);
		
		win.addEventListener(Events.ON_CHANGE,
				  new EventListener<Event>(){  //--clase an�nima para el manejo del evento
			public void onEvent(Event arg0) throws Exception {												
					win.refreshView();
			}
		  }
		 );	

		//--aplicamos las propiedades
		win.applyProperties();		
		return win;
	}
}
