package com.siis.viewModel;

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
import com.siis.dto.Indicador;
import com.siis.viewModel.framework.Utilidades;

public class FormularioIndicadorViewModel {
	protected static Logger log = Logger.getLogger(FormularioIndicadorViewModel.class);
	public List<Indicador> listaIndicadores;
	public Indicador indicadorSeleccionado;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar, desactivarTabDetalle;
	private String accion;
	Conexion con;

	@Wire
	public Borderlayout idWINFORMINDICADORZPrincipal;
	@Wire
	private Tabpanel idCARTERAZTpnConsultaIndicador;
	@Wire
	private Grid idWINFORMINDICADORZGridFormulario;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		listaIndicadores = new ArrayList<Indicador>();
		indicadorSeleccionado = new Indicador();
		setDesactivarformulario(true);
		listarIndicador();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("listaDetalleIndicador")
	@Command
	public void onAgregar() {
		log.info(" onAgregar ==> ");

		// ListModelList<DetalleIndicador> lm = new
		// ListModelList<DetalleIndicador>(
		// Arrays.asList(new DetalleIndicador(new Indicador(), "", 0.0, new
		// Date(),
		// "")));
		//
		// listaDetalleIndicador.addAll(lm);

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("CARTERA", indicadorSeleccionado);
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
	public void guardarIndicador() {
		try {
			log.info("accion=>> " + accion);

			if (!Utilidades.validarFormulario(idWINFORMINDICADORZGridFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMINDICADORZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}

			if (accion.equals("I")) {

				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "INDICADORES");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					indicadorSeleccionado.setSecuencia(sigSec);
				else
					indicadorSeleccionado.setSecuencia(1);

				Conexion.getConexion().guardar("guardarIndicador", indicadorSeleccionado);
				log.info("Indicadorguardada");
				Utilidades.mostrarNotificacion(idWINFORMINDICADORZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMINDICADORZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {
				Conexion.getConexion().actualizar("actualizarIndicador", indicadorSeleccionado);
				log.info("IndicadorActualizada");
				Utilidades.mostrarNotificacion(idWINFORMINDICADORZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMINDICADORZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarIndicador();
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
	public void onEliminar(@BindingParam("seleccionado") final Indicador cartera) {
		log.info("onEliminar => " + cartera.getSecuencia());

		Messagebox.show(idWINFORMINDICADORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMINDICADORZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {

							log.info("Messagebox.YES => " + cartera.getSecuencia());
							Conexion.getConexion().eliminar("eliminarIndicador", cartera);
							Utilidades.mostrarNotificacion(
									idWINFORMINDICADORZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMINDICADORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							BindUtils.postNotifyChange(null, null, FormularioIndicadorViewModel.this, "*");
							listarIndicador();
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
	public void onCancelar() {

		if (!accion.equals("I")) {
			indicadorSeleccionado = obtener(indicadorSeleccionado);
			onSeleccionar(indicadorSeleccionado);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	private Indicador obtener(Indicador form) {
		log.info("Ejecutando el metodo [obtener]");
		Indicador est = null;
		try {
			est = (Indicador) Conexion.getConexion().obtenerRegistro("obtenerIndicador", form);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return est;
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Indicador cartera) {
		log.info("onSeleccionar==> " + cartera.getSecuencia());
		setIndicadorSeleccionado(cartera);
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
		indicadorSeleccionado = new Indicador();
		indicadorSeleccionado.setFechaHoraActualizacion(new Date());
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
	public void listarIndicador() {
		log.info(" listarIndicador ");
		setDesactivarformulario(true);
		try {
			con = new Conexion();
			setListaIndicadores((List<Indicador>) Conexion.getConexion().obtenerListado("listarIndicadores", null));

			// setListaIndicadores((List<Indicador>)
			// con.obtenerListado("listarIndicadores", parametros));
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
			log.info("Carteta==> 1" + indicadorSeleccionado.getSecuencia());
			parametros.put("INDICADOR", indicadorSeleccionado);
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_indicador_formulacion.zul",
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

			parametros.put("OBJETO", indicadorSeleccionado);
			if (idCARTERAZTpnConsultaIndicador.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idCARTERAZTpnConsultaIndicador, "//formas//vista_indicador.zul", parametros);
			} else {
				VistaIndicadorViewModel detalleIndicador = new VistaIndicadorViewModel();
				detalleIndicador.listarIndicador();
				log.info("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Indicador> getListaIndicador() {
		return listaIndicadores;
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

	public List<Indicador> getListaIndicadores() {
		return listaIndicadores;
	}

	public void setListaIndicadores(List<Indicador> listaIndicadores) {
		this.listaIndicadores = listaIndicadores;
	}

	public Indicador getIndicadorSeleccionado() {
		return indicadorSeleccionado;
	}

	public void setIndicadorSeleccionado(Indicador indicadorSeleccionado) {
		this.indicadorSeleccionado = indicadorSeleccionado;
	}

}