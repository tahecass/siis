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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Proveedor;
import com.siis.dto.DetalleProveedor; 
import com.siis.viewModel.framework.Utilidades;

public class FormularioProveedorViewModel {
	protected static Logger log = Logger.getLogger(FormularioProveedorViewModel.class);
	public List<DetalleProveedor> listaDetalleProveedor;
	public List<Proveedor> listaProveedor;
	public DetalleProveedor detalleSeleccionado;
	public Proveedor proveedorSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar, desactivarTabDetalle;
	private String accion;
	Conexion con;

	@Wire
	private Textbox idFORMPROVEEDORZBbxCliente;
	@Wire
	public Borderlayout idWINFORMPROVEEDORZPrincipal;
	@Wire
	private Tabpanel  idPROVEEDORZTpnConsultaProveedor;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		listaDetalleProveedor = new ArrayList<DetalleProveedor>();
		listaProveedor = new ArrayList<Proveedor>();
		proveedorSeleccionada = new Proveedor();
		setDesactivarformulario(true);
		listarProveedor();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("listaDetalleProveedor")
	@Command
	public void onAgregar() {
		log.info(" onAgregar ==> ");

		// ListModelList<DetalleProveedor> lm = new
		// ListModelList<DetalleProveedor>(
		// Arrays.asList(new DetalleProveedor(new Proveedor(), "", 0.0, new
		// Date(),
		// "")));
		//
		// listaDetalleProveedor.addAll(lm);

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("PROVEEDOR", proveedorSeleccionada);
			parametros.put("OPT", "N");
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_proveedor_detalle.zul",
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
	public void guardarProveedor() {
		try {
			log.info("accion=>> " + accion);

			proveedorSeleccionada.setProveedor(idFORMPROVEEDORZBbxCliente.getValue());
			if (esFormularioValido(proveedorSeleccionada)) {

				if (accion.equals("I")) {
					HashMap<String, Object> par = new HashMap<String, Object>();
					par.put("NOMBRE_TABLA", "PROVEEDORES");
					Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

					if (sigSec != null)
						proveedorSeleccionada.setSecuencia(sigSec);
					else
						proveedorSeleccionada.setSecuencia(1);

					Conexion.getConexion().guardar("guardarProveedor", proveedorSeleccionada);
					log.info("Proveedorguardada");
					Utilidades.mostrarNotificacion(idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
							idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

				} else if (accion.equals("U")) {
					Conexion.getConexion().actualizar("actualizarProveedor", proveedorSeleccionada);
					log.info("ProveedorActualizada");
					Utilidades.mostrarNotificacion(idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
							idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
				}

				listarProveedor();
				setDesactivarBtnNuevo(false);
				setDesactivarBtnEditar(false);
				setDesactivarBtnGuardar(true);
				setDesactivarBtnEliminar(false);
			} else {

				Utilidades.mostrarNotificacion(idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean esFormularioValido(Proveedor proveedorSeleccionada2) {
		if (proveedorSeleccionada2.getProveedor() != null && proveedorSeleccionada2.getFechaPago() != null) {
			return true;
		}

		return false;
	}

	@NotifyChange("*")
	@Command
	public void onEliminar(@BindingParam("seleccionado") final Proveedor proveedor) {
		log.info("onEliminar => " + proveedor.getSecuencia());

		Messagebox.show(idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {
							log.info("CLICK on OK");
							Conexion.getConexion().eliminar("eliminarProveedor", proveedor);
							Utilidades.mostrarNotificacion(
									idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							BindUtils.postNotifyChange(null, null, FormularioProveedorViewModel.this, "*");
							listarProveedor();
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
	public void onSeleccionar(@BindingParam("seleccionado") Proveedor proveedor) {
		log.info("onSeleccionar==> " + proveedor.getSecuencia());
		setProveedorSeleccionada(proveedor);
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
		proveedorSeleccionada.setFechaHoraActualizacion(new Timestamp(date.getTime()));
		
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
		log.info("onCancelar");
		if (!accion.equals("I")) {
			proveedorSeleccionada = obtener(proveedorSeleccionada);
			onSeleccionar(proveedorSeleccionada);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	@NotifyChange("*")
	@Command
	public void onCargaEmergente() {
		log.info("onCargarDetalle");

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			Window wind = (Window) Utilidades.onCargarVentana(null, "//formas//carga_masiva_proveedor.zul", parametros);
			wind.setPosition("center");
			wind.doModal();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Proveedor obtener(Proveedor proveedor) {
		log.info("Ejecutando el metodo []");
		Proveedor car = null;
		try {
			car = (Proveedor) Conexion.getConexion().obtenerRegistro("obtenerProveedor", proveedor);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return car;
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		log.info("onNuevo");
		setDesactivarformulario(false);
		proveedorSeleccionada = new Proveedor();
		java.util.Date date = new java.util.Date();
		proveedorSeleccionada.setFechaHoraActualizacion(new Timestamp(date.getTime()));
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("listaProveedor")
	@Command
	public void listarProveedor() {
		log.info(" listarProveedor ");
		listaProveedor = new ArrayList<Proveedor>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		setDesactivarformulario(true);
		try {
			con = new Conexion();
			setListaProveedor((List<Proveedor>) con.obtenerListado("listarProveedores", parametros));
			log.info("REGISTROS ==> " + listaProveedor.size());

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DetalleProveedor> getListaDetalleProveedor() {
		return listaDetalleProveedor;
	}

	@NotifyChange("*")
	@Command
	public void onMostrarVentanaDetalle() {
		log.info("onMostrarVentanaDetalle");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("pROVEEDOR ==> " + proveedorSeleccionada.getSecuencia());
			parametros.put("PROVEEDOR", proveedorSeleccionada);
		 
			
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_proveedor_detalle.zul",
					parametros);
//			win.setHeight("auto");
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
			log.info("PROVEEDOR ENVIADA ==> " + proveedorSeleccionada.getSecuencia());
			parametros.put("OBJETO", proveedorSeleccionada);
			if (idPROVEEDORZTpnConsultaProveedor.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idPROVEEDORZTpnConsultaProveedor, "//formas//vista_proveedor.zul",
						parametros);
			} else {
				// FormularioProveedorDetalleViewModel.ca
				//// detalleProveedor.listarDetalleProveedor();
				// log.info("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setListaDetalleProveedor(List<DetalleProveedor> listaDetalleProveedor) {
		this.listaDetalleProveedor = listaDetalleProveedor;
	}

	public DetalleProveedor getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DetalleProveedor detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}

	public void setListaProveedor(List<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}

	public Proveedor getProveedorSeleccionada() {
		return proveedorSeleccionada;
	}

	public void setProveedorSeleccionada(Proveedor proveedorSeleccionada) {
		this.proveedorSeleccionada = proveedorSeleccionada;
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