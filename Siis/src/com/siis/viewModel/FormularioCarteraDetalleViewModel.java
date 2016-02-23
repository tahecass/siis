package com.siis.viewModel;

import java.util.ArrayList;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Messagebox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cartera;
import com.siis.dto.DetalleCartera;
import com.siis.viewModel.framework.Utilidades;

public class FormularioCarteraDetalleViewModel {
	protected static Logger log = Logger.getLogger(FormularioCarteraViewModel.class);
	public List<DetalleCartera> listaDetalleCartera;
	public DetalleCartera detalleCarteraSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	private Cartera cartera;

	@Wire
	public Borderlayout idWINFORMDETCARTERAZPrincipal;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		cartera = (Cartera) parametros.get("CARTERA");
		listaDetalleCartera = new ArrayList<DetalleCartera>();
		detalleCarteraSeleccionada = new DetalleCartera();
		setDesactivarformulario(true);
		listarDetalleCartera();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarDetalleCartera() {
		try {
			log.info("accion=>> " + accion);

			detalleCarteraSeleccionada.setCartera(cartera);

			if (accion.equals("I")) {
				detalleCarteraSeleccionada.setSecuencia(10);
				Conexion.getConexion().guardar("guardarDetalleCartera", detalleCarteraSeleccionada);
				log.info("Carteraguardada");
				Utilidades.mostrarNotificacion(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarCartera", detalleCarteraSeleccionada);
				log.info("CarteraActualizada");
				Utilidades.mostrarNotificacion(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarDetalleCartera();
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
	public void onEliminar(@BindingParam("seleccionado") Cartera cartera) {
		log.info("onEliminar => " + cartera.getSecuencia());
		if ((Messagebox.show(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_ELIMINAR_CARTERA").toString(),
				idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.NO | Messagebox.YES, Messagebox.QUESTION)) == Messagebox.YES) {

			log.info("Messagebox.YES => " + cartera.getSecuencia());
			Conexion.getConexion().eliminar("eliminarCartera", cartera);
			Utilidades.mostrarNotificacion(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
					idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(), "INFO");
			listarDetalleCartera();
			setDesactivarBtnNuevo(false);
			setDesactivarBtnEditar(true);
			setDesactivarBtnGuardar(true);
			setDesactivarBtnEliminar(true);
		}
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") DetalleCartera detalleCartera) {
		log.info("onSeleccionar==> ");
		setDetalleCarteraSeleccionada(detalleCartera);
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
		detalleCarteraSeleccionada = new DetalleCartera();
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDetalleCartera() {
		log.info(" listarDetalleCartera ");
		listaDetalleCartera = new ArrayList<DetalleCartera>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SEC_CARTERA", cartera.getSecuencia());
		setDesactivarformulario(true);
		try {
			setListaDetalleCartera(
					(List<DetalleCartera>) Conexion.getConexion().obtenerListado("listarDetalleCarterasPorClientes", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DetalleCartera> getListaDetalleCartera() {
		return listaDetalleCartera;
	}

	public void setListaDetalleCartera(List<DetalleCartera> listaDetalleCartera) {
		this.listaDetalleCartera = listaDetalleCartera;
	}

	public DetalleCartera getDetalleCarteraSeleccionada() {
		return detalleCarteraSeleccionada;
	}

	public void setDetalleCarteraSeleccionada(DetalleCartera detalleCarteraSeleccionada) {
		this.detalleCarteraSeleccionada = detalleCarteraSeleccionada;
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

}
