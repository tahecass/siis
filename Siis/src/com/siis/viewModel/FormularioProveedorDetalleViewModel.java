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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Proveedor;
import com.siis.dto.Cartera;
import com.siis.dto.DetalleProveedor;
import com.siis.viewModel.framework.Utilidades;

public class FormularioProveedorDetalleViewModel extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4125198133545825322L;
	protected static Logger log = Logger.getLogger(FormularioProveedorViewModel.class);
	public List<DetalleProveedor> listaDetalleProveedor;
	public DetalleProveedor detalleProveedorSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	public Proveedor proveedor;

	@Wire
	public Borderlayout idWINFORMDETPROVEEDORZPrincipal;
	@Wire
	public Grid idWINFORMDETPROVEEDORZGRidFormulario;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setProveedor((Proveedor) parametros.get("PROVEEDOR"));
		listaDetalleProveedor = new ArrayList<DetalleProveedor>();
		detalleProveedorSeleccionada = new DetalleProveedor();
		setDesactivarformulario(true);
		listarDetalleProveedor();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarDetalleProveedor() {
		try {
			log.info("accion=>> " + accion);

			detalleProveedorSeleccionada.setProveedor(proveedor);
			if (!Utilidades.validarFormulario(idWINFORMDETPROVEEDORZGRidFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}

			if (accion.equals("I")) {
				
				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "DETALLE_PROVEEDOR");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					detalleProveedorSeleccionada.setSecuencia(sigSec);
				else
					detalleProveedorSeleccionada.setSecuencia(1);
 
				Conexion.getConexion().guardar("guardarDetalleProveedor", detalleProveedorSeleccionada);
				log.info("Proveedorguardada");
				Utilidades.mostrarNotificacion(idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarDetalleProveedor", detalleProveedorSeleccionada);
				log.info("ProveedorActualizada");
				Utilidades.mostrarNotificacion(idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarDetalleProveedor();
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
	public void onEliminar(@BindingParam("seleccionado") final DetalleProveedor cartera) {
		log.info("onEliminar => " + cartera.getSecuencia());

		Messagebox.show(idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {
							log.info("CLICK on OK");
							Conexion.getConexion().eliminar("eliminarDetalleProveedor", cartera);
							Utilidades.mostrarNotificacion(
									idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMDETPROVEEDORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							BindUtils.postNotifyChange(null, null, FormularioProveedorDetalleViewModel.this, "*");
							listarDetalleProveedor();;
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
	public void onSeleccionar(@BindingParam("seleccionado") DetalleProveedor detalleProveedor) {
		log.info("onSeleccionar==> ");
		setDetalleProveedorSeleccionada(detalleProveedor);
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
		log.info("onCancelar=> " + detalleProveedorSeleccionada.getSecuencia());
		if (!accion.equals("I")) {
			detalleProveedorSeleccionada = obtener(detalleProveedorSeleccionada);
			onSeleccionar(detalleProveedorSeleccionada);
			desactivarformulario=true;
		}else{
			onNuevo();
		}

	}

	private DetalleProveedor obtener(DetalleProveedor detProveedor) {
		log.info("Ejecutando el metodo [obtener]");
		DetalleProveedor car = null;
		try {
			car = (DetalleProveedor) Conexion.getConexion().obtenerRegistro("obtenerDetalleProveedors", detProveedor);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return car;
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
		detalleProveedorSeleccionada = new DetalleProveedor();
		detalleProveedorSeleccionada.setFechaActualizacion(new Date());
		detalleProveedorSeleccionada.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDetalleProveedor() {
		log.info(" listarDetalleProveedor ");
		listaDetalleProveedor = new ArrayList<DetalleProveedor>();

		log.info("SEC_CARTERA AL LISTAR ==>" + getProveedor().getSecuencia());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SEC_PROVEEDOR", getProveedor().getSecuencia());

		listaDetalleProveedor.clear();

		setDesactivarformulario(true);
		try {
			setListaDetalleProveedor((List<DetalleProveedor>) Conexion.getConexion()
					.obtenerListado("listarDetalleProveedorsPorClientes", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DetalleProveedor> getListaDetalleProveedor() {
		return listaDetalleProveedor;
	}

	@NotifyChange("listaDetalleProveedor")
	public void setListaDetalleProveedor(List<DetalleProveedor> listaDetalleProveedor) {
		this.listaDetalleProveedor = listaDetalleProveedor;
	}

	public DetalleProveedor getDetalleProveedorSeleccionada() {
		return detalleProveedorSeleccionada;
	}

	public void setDetalleProveedorSeleccionada(DetalleProveedor detalleProveedorSeleccionada) {
		this.detalleProveedorSeleccionada = detalleProveedorSeleccionada;
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

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

}