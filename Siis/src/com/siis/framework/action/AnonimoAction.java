/**
 * InicioAction.java
 */
package com.siis.framework.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.iceberg_aa.dto.AATEjecutable;
import com.casewaresa.iceberg_fe.dto.FETFlujo;
import com.casewaresa.iceberg_gp.dto.GPTForma;

/**
 * @author	: WCalderon
 * @date 	: 18/05/2012
 * @name 	: AnominoAction.java
 * @desc 	: Forma para cargar las páginas de los usurios anónimos 
 */


public class AnonimoAction extends ActionStandardBorder implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8601893154837728127L;

	protected static Logger log = Logger.getLogger(AnonimoAction.class);
	
	private Tabbox tbxFormas = null;
	private AATEjecutable ejecutable = null;
	
	
	public void afterCompose() {
		log.info("Usuario Anónimo");
				
		tbxFormas = (Tabbox) this.getFellow("idTbxFormas");
		
		Map<String, String[]> parameters = (Map<String, String[]>)Executions.getCurrent().getParameterMap();
		Map<String, Object> reportParameters = new HashMap<String, Object>();
		
		for(String key: parameters.keySet()) {
			String value = parameters.get(key)[0];
			reportParameters.put(key, value);
			log.info("[" + key + "]: " + value);
		}
		
		if(parameters.containsKey(IConstantes.FORMA_EJECUTABLE)){
			this.cargarZul(parameters);
		}else if(parameters.containsKey(IConstantes.FORMA_REPORTE)){
			this.cargarRep(reportParameters);
		}
	}
					
	public void cargarZul(Map<String,String[]> parameters){		
		log.info("Ejecutando método [cargarZul]...");
		
		try {
			this.inicializarTabbox();
			
			if(parameters.containsKey(IConstantes.FORMA_EJECUTABLE)){
				log.info("Ejecutable recibido: " + parameters.get(IConstantes.FORMA_EJECUTABLE)[0]);
				
				
				ejecutable = new AATEjecutable(Long.parseLong(parameters.get(IConstantes.FORMA_EJECUTABLE)[0]));
				
				ParametrizacionFac.getFacade().obtenerRegistro("AA_obtenerEjecutable", ejecutable);
				
				inicializarTab();
				
				Map<String, Object> info = new HashMap<String, Object>();
				
				
				GPTForma forma = (GPTForma) ParametrizacionFac.getFacade()
						.obtenerRegistro(
								"obtenerFormaPorCodigo",
								parameters.get("form")[0]);
				forma.setForma(forma.getForma().replaceAll("-", ""));
				forma.setZkPreview(ejecutable.getFuente());
				
				info.put("forma", forma);

				info.put("persona", null);

				Long sec = (Long) ParametrizacionFac.getFacade().obtenerRegistro(
						"obtenerPrimerFlujo");

				if (sec != null) {
					FETFlujo flujo = new FETFlujo();
					flujo.setSecFlujo(sec);

					info.put("flujo", flujo);
				}

				Tabpanel areaTrabajo = (Tabpanel) this.getFellow(ejecutable.getSecEjecutable()	+ IConstantes.IDTAB_CONTENIDO);
				Executions.createComponentsDirectly(ejecutable.getFuente(), null, areaTrabajo, info);
			}else{
				log.info("Ejecutable No Recibido!!!");
			}
		} catch (ComponentNotFoundException e) {
			log.error(e.getMessage(),e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_OBJ_NO_ENCONTRADO, e));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void cargarRep(Map<String,Object> parameters){		
		log.info("Ejecutando método [cargarRep]...");
		
		try {
			this.inicializarTabbox();
			
			if(parameters.containsKey(IConstantes.FORMA_REPORTE)){
				log.info("Reporte recibido: " + parameters.get(IConstantes.FORMA_REPORTE));
				
				ejecutable = new AATEjecutable(Long.parseLong((String)parameters.get(IConstantes.FORMA_REPORTE)));
				
				ParametrizacionFac.getFacade().obtenerRegistro("AA_obtenerEjecutable", ejecutable);
				
				inicializarTab();
				
				Tabpanel areaTrabajo = (Tabpanel) this.getFellow(parameters.get(IConstantes.FORMA_REPORTE) + IConstantes.IDTAB_CONTENIDO);

				Executions.createComponents(IConstantes.PANTALLA_REPORT_PREVIEW, areaTrabajo, parameters);
			}else{
				log.info("Ejecutable No Recibido!!!");
			}
		} catch (ComponentNotFoundException e) {
			log.error(e.getMessage(),e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_OBJ_NO_ENCONTRADO, e));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	
	public void inicializarTabbox() {
		log.info("Ejecutando el método [inicializarTabbox...]");

		// --- creamos su componente <tabs>
		Tabs grupoCabecerasTabs = new Tabs();
		grupoCabecerasTabs.setId("idTabsHeaderVentanas");
		tbxFormas.appendChild(grupoCabecerasTabs);

		// --- creamos su componente <tabs>
		Tabpanels grupoTabPanels = new Tabpanels();
		grupoTabPanels.setId("idTabpanelsBodyVentanas");
		tbxFormas.appendChild(grupoTabPanels);
	}
	
	public void inicializarTab() {
		log.info("Ejecutando el método [inicializarTab...]" );
		// ---creamos un elemento <tab>
		Tabs grupoCabecerasTabs = (Tabs)this.getFellow("idTabsHeaderVentanas");

		final Tab tab = new Tab();
		tab.setAttribute("nombre", ejecutable.getNombre());
		
		tab.setLabel(ejecutable.getNombre());
		tab.setId(ejecutable.getSecEjecutable() + IConstantes.IDTAB_CABECERA);
		tab.setClosable(true);
		grupoCabecerasTabs.appendChild(tab);

		// -- creamos un elemento <tabpanel>
		Tabpanels grupoTabPanels = (Tabpanels)this.getFellow("idTabpanelsBodyVentanas");
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.setId(ejecutable.getSecEjecutable() + IConstantes.IDTAB_CONTENIDO);
		grupoTabPanels.appendChild(tabpanel);
	}	
}
