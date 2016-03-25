package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Tab;

import com.siis.configuracion.Conexion;
import com.siis.dto.Indicador;
import com.siis.dto.Persona;
import com.siis.dto.TipoIdentificacion;
import com.siis.viewModel.framework.Utilidades;

public class PersonaViewModel {
	private Persona persona;
	private Persona personaSeleccionada;
	private List listaPersona;
	private boolean habilitarCampo;
	private int seleccionarTab;
	private String valorBusqueda = "";
	@Wire
	private Grid idFormPersona;
	@Wire
	private Tab idClieDatosgenerales;
	protected static Logger log = Logger.getLogger(PersonaViewModel.class);
	private List listaTipoId;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		log.info("Ejecutando metodo.... afterCompose");
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");
		persona = new Persona();
		TipoIdentificacion tpId = new TipoIdentificacion(null, "Seleccione....");
		persona.setTipoIdentificacion(tpId);
		persona.setSexo("S");
		listaPersona = new ArrayList<Persona>();
		habilitarCampo = true;
		listaTipoId = new ArrayList<TipoIdentificacion>();
		listaTipoId.add(tpId);
		listaTipoId.add(new TipoIdentificacion(1, "C.C."));
		listaTipoId.add(new TipoIdentificacion(2, "T.I."));
		listaTipoId.add(new TipoIdentificacion(3, "OTRO"));
		seleccionarTab = 1;
	}

	public List getListaTipoId() {
		return listaTipoId;
	}

	public void setListaTipoId(List listaTipoId) {
		this.listaTipoId = listaTipoId;
	}

	@NotifyChange("*")
	@Command
	public void onBuscar() {
		log.info("Ejecutando metodo.... getListaPersona" + valorBusqueda);
		try {
			listaPersona = (List) Conexion.getConexion().listar(
					"listaPersonas", "%" + valorBusqueda + "%");
		} catch (Exception e) {
			// TODO Auto-generated catcfh block
			e.printStackTrace();
		}
	}

	public List getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List listaPersona) {
		this.listaPersona = listaPersona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@NotifyChange("*")
	@Command
	public void onAgregar() {
		if (!Utilidades.validarFormulario(idFormPersona)) {
			Utilidades.mostrarNotificacion("Persona",
					"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
			return;
		}
		if (persona.getSec() == null) {
			Conexion.getConexion().guardar("guardarPersonas", persona);
			Utilidades.mostrarNotificacion("Persona",
					"Se guardó correctamente la información", "INFO");
		} else {
			Conexion.getConexion().guardar("actualizarPersonas", persona);
			Utilidades.mostrarNotificacion("Persona",
					"Se actualizó correctamente la información", "INFO");
		}
		persona = new Persona();
		onBuscar();

	}


	@Command
	public void onEliminar() {
		  Messagebox.show("Question is pressed. Are you sure?", 
				    "Question", Messagebox.OK | Messagebox.CANCEL,
				    Messagebox.QUESTION,
				        new org.zkoss.zk.ui.event.EventListener(){
				            public void onEvent(Event e){
				                if("onOK".equals(e.getName())){
				                	persona = personaSeleccionada;
				            		if (persona.getSec() != null) {
				            			Conexion.getConexion().guardar("eliminarPersonas", persona);
				            			Utilidades.mostrarNotificacion("Persona",
				            					"Se elimino correctamente la información", "INFO");
				            			persona = new Persona();
				            			onBuscar();
				            			BindUtils.postNotifyChange(null, null, PersonaViewModel.this, "*");
				            			
				            		}
				                }else if("onCancel".equals(e.getName())){
				                	Messagebox.show("nO");
				                }
				                 
				                /* Event Name Mapping list
				                Messagebox.YES = "onYes";
				                Messagebox.NO  = "onNo";
				                Messagebox.RETRY = "onRetry";
				                Messagebox.ABORT = "onAbort";
				                Messagebox.IGNORE = "onIgnore";
				                Messagebox.CANCEL = "onCancel";
				                Messagebox.OK = "onOK";
				                */
				            }
				            
				        }
				    );
		  
	}

	@NotifyChange(".")
	@Command
	public void onEditar() {
		persona = personaSeleccionada;
		System.out.print("Ejecutando metodo... onEditar");
		habilitarCampo = false;
		idClieDatosgenerales.setSelected(true);
		onBuscar();
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		System.out.println("seleccionar");
		habilitarCampo = false;
	}

	public boolean isHabilitarCampo() {
		return habilitarCampo;
	}

	public void setHabilitarCampo(boolean habilitarCampo) {
		this.habilitarCampo = habilitarCampo;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public int getSeleccionarTab() {
		return seleccionarTab;
	}

	public void setSeleccionarTab(int seleccionarTab) {
		this.seleccionarTab = seleccionarTab;
	}

}
