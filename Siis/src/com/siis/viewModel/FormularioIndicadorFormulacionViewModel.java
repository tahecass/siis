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
import com.siis.dto.Formulacion;
import com.siis.dto.Indicador;
import com.siis.viewModel.framework.Utilidades;

public class FormularioIndicadorFormulacionViewModel extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6583555197788981555L;
	protected static Logger log = Logger.getLogger(FormularioDisponibleViewModel.class);
	public List<Formulacion> listaFormulacion;
	public Formulacion formulacionSeleccionado;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	public Indicador indicador;

	@Wire
	public Borderlayout idWINFORMFORMULACIONZPrincipal;
	@Wire
	private Grid idWINFORMFORMULACIONZGridFormulario;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setIndicador((Indicador) parametros.get("INDICADOR"));
		log.info("indicador ..... " + indicador.getSecuencia());
		listaFormulacion = new ArrayList<Formulacion>();
		formulacionSeleccionado = new Formulacion();
		setDesactivarformulario(true);
		listarFormulacion();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarFormulacion() {
		try {
			log.info("accion=>> " + accion);

			if (!Utilidades.validarFormulario(idWINFORMFORMULACIONZGridFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}

			formulacionSeleccionado.setIndicador(indicador);

			if (accion.equals("I")) {
				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "FORMULACIONES");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					formulacionSeleccionado.setSecuencia(sigSec);
				else
					formulacionSeleccionado.setSecuencia(1);

				Conexion.getConexion().guardar("guardarFormulacion", formulacionSeleccionado);
				log.info("Disponibleguardada");
				Utilidades.mostrarNotificacion(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarFormulacion", formulacionSeleccionado);
				log.info("DisponibleActualizada");
				Utilidades.mostrarNotificacion(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarFormulacion();
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
	public void onEliminar(@BindingParam("seleccionado") final Formulacion detalleDisponible) {
		log.info("onEliminar => " + detalleDisponible.getSecuencia());
		try {

			Messagebox.show(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
					idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {

						@Override
						public void onEvent(Event e) throws Exception {
							if (Messagebox.ON_OK.equals(e.getName())) {

								log.info("Messagebox.YES => " + detalleDisponible.getSecuencia());
								Conexion.getConexion().eliminar("eliminarFormulacion", detalleDisponible);
								Utilidades.mostrarNotificacion(
										idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO").toString(),
										idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK")
												.toString(),
										"INFO");
								BindUtils.postNotifyChange(null, null, FormularioIndicadorFormulacionViewModel.this,
										"*");
								listarFormulacion();
								formulacionSeleccionado = new Formulacion();
								setDesactivarBtnNuevo(false);
								setDesactivarBtnEditar(true);
								setDesactivarBtnGuardar(true);
								setDesactivarBtnEliminar(true);
							}
						}
					});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Formulacion detalleDisponible) {
		log.info("onSeleccionar==> ");
		setFormulacionSeleccionado(detalleDisponible);
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
			formulacionSeleccionado = obtener(formulacionSeleccionado);
			onSeleccionar(formulacionSeleccionado);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	private Formulacion obtener(Formulacion form) {
		log.info("Ejecutando el metodo [obtener]");
		Formulacion est = null;
		try {
			est = (Formulacion) Conexion.getConexion().obtenerRegistro("obtenerFormulacion", form);

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
		formulacionSeleccionado.setFechaHoraActualizacion(new Timestamp(date.getTime()));

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
		formulacionSeleccionado = new Formulacion();
		java.util.Date date = new java.util.Date();
		formulacionSeleccionado.setFechaHoraActualizacion(new Timestamp(date.getTime()));

		formulacionSeleccionado.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarFormulacion() {
		log.info(" listarFormulacion ");
		listaFormulacion = new ArrayList<Formulacion>();

		log.info("SEC_disponible AL LISTAR ==>" + getIndicador().getSecuencia());
		Formulacion dispoBan = new Formulacion();
		dispoBan.setIndicador(getIndicador());

		listaFormulacion.clear();

		setDesactivarformulario(true);
		try {
			setListaFormulacion(
					(List<Formulacion>) Conexion.getConexion().obtenerListado("listarFormulaciones", dispoBan));
		} catch (Exception e) {
			e.printStackTrace();

		}
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

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public List<Formulacion> getListaFormulacion() {
		return listaFormulacion;
	}

	public void setListaFormulacion(List<Formulacion> listaFormulacion) {
		this.listaFormulacion = listaFormulacion;
	}

	public Formulacion getFormulacionSeleccionado() {
		return formulacionSeleccionado;
	}

	public void setFormulacionSeleccionado(Formulacion formulacionSeleccionado) {
		this.formulacionSeleccionado = formulacionSeleccionado;
	}

}