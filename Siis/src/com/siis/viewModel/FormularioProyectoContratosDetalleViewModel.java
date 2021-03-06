package com.siis.viewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Proyecto;
import com.siis.dto.ProyectoContrato;
import com.siis.viewModel.framework.Utilidades;

public class FormularioProyectoContratosDetalleViewModel extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2445398935612184189L;
	protected static Logger log = Logger.getLogger(FormularioCarteraViewModel.class);
	public List<ProyectoContrato> listaProyectoContrato;
	public ProyectoContrato detalleProyContSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	public Proyecto proyecto;

	@Wire
	public Borderlayout idWINFORMPROCONTZPrincipal;
	@Wire
	private Grid idWINFORMPROVALORZGridFormulario;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setProyecto((Proyecto) parametros.get("PROYECTO"));
		log.info("proyecto ..... " + proyecto.getSecuencia());
		listaProyectoContrato = new ArrayList<ProyectoContrato>();
		detalleProyContSeleccionada = new ProyectoContrato();
		setDesactivarformulario(true);
		listarProyectoContrato();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarProyectoContrato() {
		try {
			log.info("accion=>> " + accion);

			if (!Utilidades.validarFormulario(idWINFORMPROVALORZGridFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMPROCONTZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}
			detalleProyContSeleccionada.setProyecto(getProyecto());

			if (accion.equals("I")) {

				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "PROYECTO_CONTRATO");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					detalleProyContSeleccionada.setSecuencia(sigSec);
				else
					detalleProyContSeleccionada.setSecuencia(1);

				Conexion.getConexion().guardar("guardarProyectoContrato", detalleProyContSeleccionada);
				log.info("Carteraguardada");
				Utilidades.mostrarNotificacion(idWINFORMPROCONTZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMPROCONTZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarProyectoContrato", detalleProyContSeleccionada);
				log.info("CarteraActualizada");
				Utilidades.mostrarNotificacion(idWINFORMPROCONTZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMPROCONTZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarProyectoContrato();
			setDesactivarBtnNuevo(false);
			setDesactivarBtnEditar(false);
			setDesactivarBtnGuardar(true);
			setDesactivarBtnEliminar(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	@Command
	public void onEliminar(@BindingParam("seleccionado") final ProyectoContrato detalleCartera) {
		log.info("onEliminar => " + detalleCartera.getSecuencia());

		Messagebox.show(idWINFORMPROCONTZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMPROCONTZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {

							log.info("Messagebox.YES => " + detalleCartera.getSecuencia());
							Conexion.getConexion().eliminar("eliminarProyectoContrato", detalleCartera);
							Utilidades.mostrarNotificacion(
									idWINFORMPROCONTZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMPROCONTZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							BindUtils.postNotifyChange(null, null, FormularioProyectoContratosDetalleViewModel.this,
									"*");
							listarProyectoContrato();
							detalleProyContSeleccionada = new ProyectoContrato();
							setDesactivarBtnNuevo(false);
							setDesactivarBtnEditar(true);
							setDesactivarBtnGuardar(true);
							setDesactivarBtnEliminar(true);
						}
					}
				});
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") ProyectoContrato detalleCartera) {
		log.info("onSeleccionar==> ");
		setDetalleProyContSeleccionada(detalleCartera);
		accion = "U";
		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(false);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(false);
		setDesactivarformulario(true);
	}

	@NotifyChange("*")
	@Command
	public void onCancelar() {

		if (!accion.equals("I")) {
			detalleProyContSeleccionada = obtener(detalleProyContSeleccionada);
			onSeleccionar(detalleProyContSeleccionada);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	private ProyectoContrato obtener(ProyectoContrato form) {
		log.info("Ejecutando el metodo [obtener]");
		ProyectoContrato est = null;
		try {
			est = (ProyectoContrato) Conexion.getConexion().obtenerRegistro("obtenerProyectoContrato", form);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return est;
	}

	@NotifyChange("*")
	@Command
	public void onEditar() {
		log.info("onEditar");
		setDesactivarformulario(false);
		java.util.Date date = new java.util.Date();
		detalleProyContSeleccionada.setFechaHoraActualizacion(new Timestamp(date.getTime()));

		accion = "U";
		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		log.info("onNuevo");
		setDesactivarformulario(false);
		detalleProyContSeleccionada = new ProyectoContrato();
		java.util.Date date = new java.util.Date();
		detalleProyContSeleccionada.setFechaHoraActualizacion(new Timestamp(date.getTime()));

		detalleProyContSeleccionada.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarProyectoContrato() {
		log.info(" listarProyectoContrato ");
		listaProyectoContrato = new ArrayList<ProyectoContrato>();

		ProyectoContrato proyCont = new ProyectoContrato();
		proyCont.setProyecto(getProyecto());
		listaProyectoContrato.clear();

		setDesactivarformulario(true);
		try {
			setListaProyectoContrato(
					(List<ProyectoContrato>) Conexion.getConexion().obtenerListado("listarProyectoContrato", proyCont));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<ProyectoContrato> getListaProyectoContrato() {
		return listaProyectoContrato;
	}

	@NotifyChange("listaProyectoContrato")
	public void setListaProyectoContrato(List<ProyectoContrato> listaProyectoContrato) {
		this.listaProyectoContrato = listaProyectoContrato;
	}

	public boolean isDesactivarformulario() {
		return desactivarformulario;
	}

	public void setDesactivarformulario(boolean desactivarformulario) {
		this.desactivarformulario = desactivarformulario;
	}

	public boolean isDesactivarBtnNuevo() {
		return desactivarBtnNuevo;
	}

	public void setDesactivarBtnNuevo(boolean desactivarBtnNuevo) {
		this.desactivarBtnNuevo = desactivarBtnNuevo;
	}

	public boolean isDesactivarBtnEditar() {
		return desactivarBtnEditar;
	}

	public void setDesactivarBtnEditar(boolean desactivarBtnEditar) {
		this.desactivarBtnEditar = desactivarBtnEditar;
	}

	public boolean isDesactivarBtnEliminar() {
		return desactivarBtnEliminar;
	}

	public void setDesactivarBtnEliminar(boolean desactivarBtnEliminar) {
		this.desactivarBtnEliminar = desactivarBtnEliminar;
	}

	public boolean isDesactivarBtnGuardar() {
		return desactivarBtnGuardar;
	}

	public void setDesactivarBtnGuardar(boolean desactivarBtnGuardar) {
		this.desactivarBtnGuardar = desactivarBtnGuardar;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public ProyectoContrato getDetalleProyContSeleccionada() {
		return detalleProyContSeleccionada;
	}

	public void setDetalleProyContSeleccionada(ProyectoContrato detalleProyContSeleccionada) {
		this.detalleProyContSeleccionada = detalleProyContSeleccionada;
	}

}