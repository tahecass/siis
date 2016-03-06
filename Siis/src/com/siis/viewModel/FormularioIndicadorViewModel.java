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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
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
	private Tabpanel idCARTERAZTpnDetalleIndicador, idCARTERAZTpnConsultaIndicador;

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

			indicadorSeleccionado.setSecuencia(10);
			if (accion.equals("I")) {

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
	public void onEliminar(@BindingParam("seleccionado") Indicador cartera) {
		log.info("onEliminar => " + cartera.getSecuencia());
		if ((Messagebox.show(idWINFORMINDICADORZPrincipal.getAttribute("MSG_ELIMINAR_CARTERA").toString(),
				idWINFORMINDICADORZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.NO | Messagebox.YES, Messagebox.QUESTION)) == Messagebox.YES) {

			log.info("Messagebox.YES => " + cartera.getSecuencia());
			Conexion.getConexion().eliminar("eliminarIndicador", cartera);
			Utilidades.mostrarNotificacion(idWINFORMINDICADORZPrincipal.getAttribute("MSG_TITULO").toString(),
					idWINFORMINDICADORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(), "INFO");
			listarIndicador();
			setDesactivarBtnNuevo(false);
			setDesactivarBtnEditar(true);
			setDesactivarBtnGuardar(true);
			setDesactivarBtnEliminar(true);
			setDesactivarTabDetalle(true);
		}
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
			if (idCARTERAZTpnDetalleIndicador.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idCARTERAZTpnDetalleIndicador,
						"//formas//formulario_indicador_formulacion.zul", parametros);
			} else {
				FormularioIndicadorFormulacionViewModel detalleIndicador = new FormularioIndicadorFormulacionViewModel();

				log.info("Carteta==> 2" + indicadorSeleccionado.getSecuencia());
				detalleIndicador.setIndicador(indicadorSeleccionado);
				detalleIndicador.listarFormulacion();
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