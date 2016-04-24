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
import com.siis.dto.Disponible;
import com.siis.dto.DisponibleConcepto;
import com.siis.viewModel.framework.Utilidades;

public class FormularioDisponibleDetalleConceptoViewModel extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5456428346149507721L;
	protected static Logger log = Logger.getLogger(FormularioDisponibleViewModel.class);
	public List<DisponibleConcepto> listaDisponibleConcepto;
	public DisponibleConcepto disponibleConceptoSeleccionado;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	public Disponible disponible;

	@Wire
	public Borderlayout idWINFORMDETDISPCONZPrincipal;
	@Wire
	private Grid idWINFORMDETDISPCONZGridFormulario;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setDisponible((Disponible) parametros.get("DISPONIBLE"));
		log.info("disponible ..... " + disponible.getSecuencia());
		listaDisponibleConcepto = new ArrayList<DisponibleConcepto>();
		disponibleConceptoSeleccionado = new DisponibleConcepto();
		setDesactivarformulario(true);
		listarDisponibleConcepto();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarDisponibleConcepto() {
		try {
			log.info("accion=>> " + accion);

			if (!Utilidades.validarFormulario(idWINFORMDETDISPCONZGridFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}

			disponibleConceptoSeleccionado.setDisponible(disponible);

			if (accion.equals("I")) {

				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "DISPONIBLE_CONCEPTO");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					disponibleConceptoSeleccionado.setSecuencia(sigSec);
				else
					disponibleConceptoSeleccionado.setSecuencia(1);

				Conexion.getConexion().guardar("guardarDisponibleConcepto", disponibleConceptoSeleccionado);
				log.info("Disponibleguardada");
				Utilidades.mostrarNotificacion(idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarDisponibleConcepto", disponibleConceptoSeleccionado);
				log.info("DisponibleActualizada");
				Utilidades.mostrarNotificacion(idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarDisponibleConcepto();
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
	public void onCancelar() {

		if (!accion.equals("I")) {
			disponibleConceptoSeleccionado = obtener(disponibleConceptoSeleccionado);
			onSeleccionar(disponibleConceptoSeleccionado);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	private DisponibleConcepto obtener(DisponibleConcepto dispCon) {
		log.info("Ejecutando el metodo [obtener]");
		DisponibleConcepto dc = null;
		try {
			dc = (DisponibleConcepto) Conexion.getConexion().obtenerRegistro("obtenerDisponibleConcepto", dispCon);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	@NotifyChange("*")
	@Command
	public void onEliminar(@BindingParam("seleccionado") final DisponibleConcepto disponible) {
		log.info("onEliminar => " + disponible.getSecuencia());

		Messagebox.show(idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {

							Conexion.getConexion().eliminar("eliminarDisponibleConcepto", disponible);
							Utilidades.mostrarNotificacion(
									idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMDETDISPCONZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							BindUtils.postNotifyChange(null, null, FormularioDisponibleDetalleConceptoViewModel.this,
									"*");
							listarDisponibleConcepto();
							disponibleConceptoSeleccionado = new DisponibleConcepto();
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
	public void onSeleccionar(@BindingParam("seleccionado") DisponibleConcepto detalleDisponible) {
		log.info("onSeleccionar==> ");
		setDisponibleConceptoSeleccionado(detalleDisponible);
		accion = "U";
		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(false);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(false);
		setDesactivarformulario(true);
	}

	@NotifyChange("*")
	@Command
	public void onEditar() {
		log.info("onEditar");
		setDesactivarformulario(false);
		java.util.Date date = new java.util.Date();
		disponibleConceptoSeleccionado.setFechaHoraActualizacion(new Timestamp(date.getTime()));

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
		disponibleConceptoSeleccionado = new DisponibleConcepto();
		java.util.Date date = new java.util.Date();
		disponibleConceptoSeleccionado.setFechaHoraActualizacion(new Timestamp(date.getTime()));

		disponibleConceptoSeleccionado.setFechaCreacion(new Date());
		disponibleConceptoSeleccionado.setTipoNota("C");
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDisponibleConcepto() {
		log.info(" listarDisponibleConcepto ");
		listaDisponibleConcepto = new ArrayList<DisponibleConcepto>();

		log.info("SEC_disponible AL LISTAR ==>" + getDisponible().getSecuencia());
		DisponibleConcepto dispoBan = new DisponibleConcepto();
		dispoBan.setDisponible(getDisponible());

		listaDisponibleConcepto.clear();

		setDesactivarformulario(true);
		try {
			setListaDisponibleConcepto((List<DisponibleConcepto>) Conexion.getConexion()
					.obtenerListado("listarDisponibleConceptos", dispoBan));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DisponibleConcepto> getListaDisponibleConcepto() {
		return listaDisponibleConcepto;
	}

	@NotifyChange("listaDisponibleConcepto")
	public void setListaDisponibleConcepto(List<DisponibleConcepto> listaDisponibleConcepto) {
		this.listaDisponibleConcepto = listaDisponibleConcepto;
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

	public Disponible getDisponible() {
		return disponible;
	}

	public void setDisponible(Disponible disponible) {
		this.disponible = disponible;
	}

	public DisponibleConcepto getDisponibleConceptoSeleccionado() {
		return disponibleConceptoSeleccionado;
	}

	public void setDisponibleConceptoSeleccionado(DisponibleConcepto disponibleConceptoSeleccionado) {
		this.disponibleConceptoSeleccionado = disponibleConceptoSeleccionado;
	}

}