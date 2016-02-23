package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cartera;
import com.siis.dto.DetalleCartera;
import com.siis.dto.Usuario;
import com.siis.viewModel.framework.BandboxCliente;
import com.siis.viewModel.framework.Utilidades;

public class FormularioCarteraViewModel {

	public List<DetalleCartera> listaDetalleCartera;
	public List<Cartera> listaCartera;
	public DetalleCartera detalleSeleccionado;
	public Cartera carteraSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;

	@Wire
	private Datebox idFORMCARTERAZDbxFechaHoraAct;
	@Wire
	private Datebox idFORMCARTERAZDbxFechaPago;

	@Wire
	private BandboxCliente idFORMCARTERAZBbxCliente;
	@Wire
	public Borderlayout idWINFORMCARTERAZPrincipal;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		System.out.println("afterCompose.... ");
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		listaDetalleCartera = new ArrayList<DetalleCartera>();
		listaCartera = new ArrayList<Cartera>();
		carteraSeleccionada = new Cartera();
		idFORMCARTERAZDbxFechaHoraAct.setValue(new Date());
		setDesactivarformulario(true);
		listarCartera();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("listaDetalleCartera")
	@Command
	public void onAgregar() {
		System.out.println(" onAgregar ==> ");

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
			System.out.println("accion=>> " + accion);
			Cartera cartera = new Cartera();

			cartera.setCliente(idFORMCARTERAZBbxCliente.getValue());
			cartera.setUsuario(new Usuario(new Integer(1)));
			cartera.setFechaHoraActualizacion(idFORMCARTERAZDbxFechaHoraAct.getValue());
			cartera.setFechaPago(idFORMCARTERAZDbxFechaPago.getValue());
			cartera.setFechaCreacion(new Date());

			if (accion.equals("I")) {
				cartera.setSecuencia(10);
				Conexion.getConexion().guardar("guardarCartera", cartera);
				System.out.println("Carteraguardada");
				Utilidades.mostrarNotificacion(idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMCARTERAZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {
				cartera.setSecuencia(carteraSeleccionada.getSecuencia());
				Conexion.getConexion().actualizar("actualizarCartera", cartera);
				System.out.println("CarteraActualizada");
				Utilidades.mostrarNotificacion(idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarCartera();
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
		System.out.println("onEliminar");

		Conexion.getConexion().eliminar("eliminarCartera", cartera);
		Utilidades.mostrarNotificacion(idWINFORMCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
				idWINFORMCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(), "INFO");

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Cartera cartera) {
		System.out.println("onSeleccionar");
		setCarteraSeleccionada(cartera);
		accion = "U";
		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(false);
	}

	@NotifyChange("*")
	@Command
	public void onEditar() {
		System.out.println("onEditar");
		setDesactivarformulario(false);
		accion = "U";
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		System.out.println("seleccionar");
		setDesactivarformulario(false);
		carteraSeleccionada.setFechaHoraActualizacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarCartera() {
		System.out.println(" listarCartera ");
		listaCartera = new ArrayList<Cartera>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		setDesactivarformulario(true);
		try {
			setListaCartera((List<Cartera>) Conexion.getConexion().obtenerListado("listarCarteras", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DetalleCartera> getListaDetalleCartera() {
		return listaDetalleCartera;
	}

	// @Command
	// public void onMostrarVentanaConsulta() {
	// System.out.println("onMostrarVentanaConsulta");
	//
	// try {
	// Map<String, Object> parametros = new HashMap<String, Object>();
	// parametros.put("OBJETO", "");
	//
	// onCargarVentana(tabPanelConsultas, "//formas//vista_cartera.zul",
	// parametros);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

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

}
