package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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

import com.siis.configuracion.Conexion;
import com.siis.dto.Proyecto;
import com.siis.dto.ProyectoContrato;
import com.siis.viewModel.framework.Utilidades;

public class FormularioProyectoContratosDetalleViewModel {

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
				detalleProyContSeleccionada.setSecuencia(10);
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
							listarProyectoContrato();
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
	}

	@NotifyChange("*")
	@Command
	public void onEditar() {
		log.info("onEditar");
		setDesactivarformulario(false);
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
		detalleProyContSeleccionada.setFechaHoraActualizacion(new Date());
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

		log.info("SEC_CARTERA AL LISTAR ==>" + getProyecto().getSecuencia());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SEC_PROYECTO", getProyecto().getSecuencia());

		listaProyectoContrato.clear();

		setDesactivarformulario(true);
		try {
			setListaProyectoContrato((List<ProyectoContrato>) Conexion.getConexion()
					.obtenerListado("listarProyectoContrato", parametros));
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