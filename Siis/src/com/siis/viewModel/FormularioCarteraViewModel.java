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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cartera;
import com.siis.dto.DetalleCartera;
import com.siis.viewModel.framework.BandboxCliente;
import com.siis.viewModel.framework.Utilidades;

public class FormularioCarteraViewModel {
	protected static Logger log = Logger.getLogger(FormularioCarteraViewModel.class);
	public List<DetalleCartera> listaDetalleCartera;
	public List<Cartera> listaCartera;
	public DetalleCartera detalleSeleccionado;
	public Cartera carteraSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar, desactivarTabDetalle;
	private String accion;
	Conexion con;

	@Wire
	private BandboxCliente idFORMCARTERAZBbxCliente;
	@Wire
	public Borderlayout idWINFORMCARTERAZPrincipal;
	@Wire
	private Tabpanel idCARTERAZTpnDetalleCartera, idCARTERAZTpnConsultaCartera;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		listaDetalleCartera = new ArrayList<DetalleCartera>();
		listaCartera = new ArrayList<Cartera>();
		carteraSeleccionada = new Cartera();
		setDesactivarformulario(true);
		listarCartera();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("listaDetalleCartera")
	@Command
	public void onAgregar() {
		log.info(" onAgregar ==> ");

		// ListModelList<DetalleCartera> lm = new ListModelList<DetalleCartera>(
		// Arrays.asList(new DetalleCartera(new Cartera(), "", 0.0, new Date(),
		// "")));
		//
		// listaDetalleCartera.addAll(lm);

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("CARTERA", carteraSeleccionada);
			parametros.put("OPT", "N");
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_cartera_detalle.zul",
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
	public void guardarCartera() {
		try {
			log.info("accion=>> " + accion);

			carteraSeleccionada.setCliente(idFORMCARTERAZBbxCliente.getValue());
			if (esFormularioValido(carteraSeleccionada)) {

				if (accion.equals("I")) {
					carteraSeleccionada.setSecuencia(10);
					Conexion.getConexion().guardar("guardarCartera", carteraSeleccionada);
					log.info("Carteraguardada");
					Utilidades.mostrarNotificacion(idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
							idWINFORMCARTERAZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

				} else if (accion.equals("U")) {
					Conexion.getConexion().actualizar("actualizarCartera", carteraSeleccionada);
					log.info("CarteraActualizada");
					Utilidades.mostrarNotificacion(idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
							idWINFORMCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
				}

				listarCartera();
				setDesactivarBtnNuevo(false);
				setDesactivarBtnEditar(false);
				setDesactivarBtnGuardar(true);
				setDesactivarBtnEliminar(false);
			} else {

				Utilidades.mostrarNotificacion(idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean esFormularioValido(Cartera carteraSeleccionada2) {
		if (carteraSeleccionada2.getCliente() != null && carteraSeleccionada2.getFechaPago() != null) {
			return true;
		}

		return false;
	}

	@NotifyChange("*")
	@Command
	public void onEliminar(@BindingParam("seleccionado") final Cartera cartera) {
		log.info("onEliminar => " + cartera.getSecuencia());

		Messagebox.show(idWINFORMCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {
							log.info("CLICK on OK");
							Conexion.getConexion().eliminar("eliminarCartera", cartera);
							Utilidades.mostrarNotificacion(
									idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");

							listarCartera();
							setDesactivarBtnNuevo(false);
							setDesactivarBtnEditar(true);
							setDesactivarBtnGuardar(true);
							setDesactivarBtnEliminar(true);
							setDesactivarTabDetalle(true);
						}
					}

				});

	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Cartera cartera) {
		log.info("onSeleccionar==> " + cartera.getSecuencia());
		setCarteraSeleccionada(cartera);
		accion = "U";
		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(false);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(false);
		setDesactivarTabDetalle(false);
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
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		log.info("onNuevo");
		setDesactivarformulario(false);
		carteraSeleccionada = new Cartera();
		carteraSeleccionada.setFechaHoraActualizacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("listaCartera")
	@Command
	public void listarCartera() {
		log.info(" listarCartera ");
		listaCartera = new ArrayList<Cartera>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		setDesactivarformulario(true);
		try {
			con = new Conexion();
			// setListaCartera((List<Cartera>)
			// Conexion.getConexion().obtenerListado("listarCarteras",
			// parametros));

			setListaCartera((List<Cartera>) con.obtenerListado("listarCarteras", parametros));
			log.info("REGISTROS ==> " + listaCartera.size());

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DetalleCartera> getListaDetalleCartera() {
		return listaDetalleCartera;
	}

	@NotifyChange("*")
	@Command
	public void onMostrarVentanaDetalle() {
		log.info("onMostrarVentanaDetalle");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("Carteta==> 1" + carteraSeleccionada.getSecuencia());
			parametros.put("CARTERA", carteraSeleccionada);
			if (idCARTERAZTpnDetalleCartera.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idCARTERAZTpnDetalleCartera, "//formas//formulario_cartera_detalle.zul",
						parametros);
			} else {
				FormularioCarteraDetalleViewModel detalleCartera = new FormularioCarteraDetalleViewModel();

				log.info("Carteta==> 2" + carteraSeleccionada.getSecuencia());
				detalleCartera.setCartera(carteraSeleccionada);
				detalleCartera.listarDetalleCartera();
				log.info("1");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void onMostrarVentanaConsulta() {
		log.info("onMostrarVentanaConsulta");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("CARTERA ENVIADA ==> " + carteraSeleccionada.getSecuencia());
			parametros.put("OBJETO", carteraSeleccionada);
			if (idCARTERAZTpnConsultaCartera.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idCARTERAZTpnConsultaCartera, "//formas//vista_cartera.zul", parametros);
			} else {
				// FormularioCarteraDetalleViewModel.ca
				//// detalleCartera.listarDetalleCartera();
				// log.info("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setListaDetalleCartera(List<DetalleCartera> listaDetalleCartera) {
		this.listaDetalleCartera = listaDetalleCartera;
	}

	public DetalleCartera getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DetalleCartera detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public List<Cartera> getListaCartera() {
		return listaCartera;
	}

	public void setListaCartera(List<Cartera> listaCartera) {
		this.listaCartera = listaCartera;
	}

	public Cartera getCarteraSeleccionada() {
		return carteraSeleccionada;
	}

	public void setCarteraSeleccionada(Cartera carteraSeleccionada) {
		this.carteraSeleccionada = carteraSeleccionada;
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