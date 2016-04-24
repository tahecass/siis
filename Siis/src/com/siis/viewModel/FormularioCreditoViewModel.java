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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Credito; 
import com.siis.viewModel.framework.BandboxBancos;
import com.siis.viewModel.framework.BandboxCuentas;
import com.siis.viewModel.framework.Utilidades;

public class FormularioCreditoViewModel {
	protected static Logger log = Logger.getLogger(FormularioCreditoViewModel.class);

	public List<Credito> listaCredito;
	public Credito creditoSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar, desactivarTabDetalle;
	private String accion;

	@Wire
	private BandboxBancos idFORMCREDITOZBbxBanco;
	@Wire
	private BandboxCuentas idFORMDISPONIBLEZBbxCuenta;
	@Wire
	public Borderlayout idWINFORMCREDITOZPrincipal;
	@Wire
	private Tabpanel idDISPONIBLEZTpnDetalleCredito, idDISPONIBLEZTpnConsultaCredito;
	@Wire
	private Grid idWINFORMCREDITOZGridFormulario;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);
		listaCredito = new ArrayList<Credito>();
		creditoSeleccionada = new Credito();
		setDesactivarformulario(true);
		listarCredito();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("listaDetalleCredito")
	@Command
	public void onAgregar() {
		log.info(" onAgregar ==> ");

		// ListModelList<DetalleCredito> lm = new ListModelList<DetalleCredito>(
		// Arrays.asList(new DetalleCredito(new Credito(), "", 0.0, new Date(),
		// "")));
		//
		// listaDetalleCredito.addAll(lm);

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("DISPONIBLE", creditoSeleccionada);
			parametros.put("OPT", "N");
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_credito_detalle.zul",
					parametros);

			win.setHeight("auto");
			win.setPosition("center,top");
			win.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	@Command
	public void guardarCredito() {
		try {
			log.info("accion=>> " + accion);

			if (!Utilidades.validarFormulario(idWINFORMCREDITOZGridFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMCREDITOZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}

			creditoSeleccionada.setEntidad(idFORMCREDITOZBbxBanco.getValue());
			if (accion.equals("I")) {
				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "CREDITOS");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					creditoSeleccionada.setSecuencia(sigSec);
				else
					creditoSeleccionada.setSecuencia(1);

				Conexion.getConexion().guardar("guardarCredito", creditoSeleccionada);
				log.info("Creditoguardada");
				Utilidades.mostrarNotificacion(idWINFORMCREDITOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMCREDITOZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {
				Conexion.getConexion().actualizar("actualizarCredito", creditoSeleccionada);
				log.info("CreditoActualizada");
				Utilidades.mostrarNotificacion(idWINFORMCREDITOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMCREDITOZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarCredito();
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
	public void onEliminar(@BindingParam("seleccionado") final Credito credito) {
		log.info("onEliminar => " + credito.getSecuencia());
		try {
			Integer detalleAsociados = (Integer) Conexion.getConexion().obtenerRegistro("contarDetallesPorCredito",
					credito.getSecuencia());

			if (detalleAsociados > 0) {
				Utilidades.mostrarNotificacion(idWINFORMCREDITOZPrincipal.getAttribute("MSG_TITULO").toString(),
						"El registro que desea eliminar tiene detalles dependientes", "INFO");
				return;
			}
			Messagebox.show(idWINFORMCREDITOZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
					idWINFORMCREDITOZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {

						@Override
						public void onEvent(Event e) throws Exception {
							if (Messagebox.ON_OK.equals(e.getName())) {
								log.info("CLICK on OK");
								log.info("Messagebox.YES => " + credito.getSecuencia());
								Conexion.getConexion().eliminar("eliminarCredito", credito);
								Utilidades.mostrarNotificacion(
										idWINFORMCREDITOZPrincipal.getAttribute("MSG_TITULO").toString(),
										idWINFORMCREDITOZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
										"INFO");
								BindUtils.postNotifyChange(null, null, FormularioCreditoViewModel.this, "*");
								listarCredito();
								creditoSeleccionada = new Credito();
								setDesactivarBtnNuevo(false);
								setDesactivarBtnEditar(true);
								setDesactivarBtnGuardar(true);
								setDesactivarBtnEliminar(true);
								setDesactivarTabDetalle(true);
							}
						}

					});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Credito credito) {
		log.info("onSeleccionar==> " + credito.getSecuencia());
		setCreditoSeleccionada(credito);
		accion = "U";
		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(false);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(false);
		setDesactivarTabDetalle(false);
		setDesactivarformulario(true);
	}

	@NotifyChange("*")
	@Command
	public void onEditar() {
		log.info("onEditar");
		setDesactivarformulario(false);
		java.util.Date date = new java.util.Date();
		creditoSeleccionada.setFechaHoraActualizacion(new Timestamp(date.getTime()));
		accion = "U";
		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("*")
	@Command
	public void onCancelar() {

		if (!accion.equals("I")) {
			creditoSeleccionada = obtener(creditoSeleccionada);
			onSeleccionar(creditoSeleccionada);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	private Credito obtener(Credito credito) {
		log.info("Ejecutando el metodo [obtener]");
		Credito cred = null;
		try {
			cred = (Credito) Conexion.getConexion().obtenerRegistro("obtenerCredito", credito);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cred;
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		log.info("onNuevo");
		setDesactivarformulario(false);
		creditoSeleccionada = new Credito();
		java.util.Date date = new java.util.Date();
		creditoSeleccionada.setFechaHoraActualizacion(new Timestamp(date.getTime()));

		creditoSeleccionada.setFechaCreacion(new Date());
		creditoSeleccionada.setFecha(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarCredito() {
		log.info(" listarCredito ");
		listaCredito = new ArrayList<Credito>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		setDesactivarformulario(true);
		try {

			setListaCredito((List<Credito>) Conexion.getConexion().obtenerListado("listarCreditos", parametros));

			// setListaCredito((List<Credito>)
			// con.obtenerListado("listarCreditos", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@NotifyChange("*")
	@Command
	public void onMostrarVentanaDetalle() {
		log.info("onMostrarVentanaDetalle");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("Disponoble==> 1" + creditoSeleccionada.getSecuencia());
			parametros.put("CREDITO", creditoSeleccionada);
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_credito_detalle.zul",
					parametros);
			win.setPosition("center,top");
			win.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void onMostrarVentanaConsulta() {
		log.info("onMostrarVentanaConsulta");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("DISPONIBLE ENVIADA ==> " + creditoSeleccionada.getSecuencia());
			parametros.put("OBJETO", creditoSeleccionada);
			if (idDISPONIBLEZTpnConsultaCredito.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idDISPONIBLEZTpnConsultaCredito, "//formas//vista_credito.zul", parametros);
			} else {
				// FormularioCreditoDetalleViewModel.ca
				//// detalleCredito.listarDetalleCredito();
				// log.info("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Credito> getListaCredito() {
		return listaCredito;
	}

	public void setListaCredito(List<Credito> listaCredito) {
		this.listaCredito = listaCredito;
	}

	public Credito getCreditoSeleccionada() {
		return creditoSeleccionada;
	}

	public void setCreditoSeleccionada(Credito creditoSeleccionada) {
		this.creditoSeleccionada = creditoSeleccionada;
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

	public boolean isDesactivarTabDetalle() {
		return desactivarTabDetalle;
	}

	public void setDesactivarTabDetalle(boolean desactivarTabDetalle) {
		this.desactivarTabDetalle = desactivarTabDetalle;
	}

}