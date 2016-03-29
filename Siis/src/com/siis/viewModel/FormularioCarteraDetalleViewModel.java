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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
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
	public Cartera cartera;

	@Wire
	public Borderlayout idWINFORMDETCARTERAZPrincipal;
	@Wire
	public Grid idWINFORMCARTERAZGRidFormulario;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setCartera((Cartera) parametros.get("CARTERA"));
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
			if (!Utilidades.validarFormulario(idWINFORMCARTERAZGRidFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}

			if (accion.equals("I")) {
				
				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "DETALLE_CARTERA");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					detalleCarteraSeleccionada.setSecuencia(sigSec);
				else
					detalleCarteraSeleccionada.setSecuencia(1);
 
				Conexion.getConexion().guardar("guardarDetalleCartera", detalleCarteraSeleccionada);
				log.info("Carteraguardada");
				Utilidades.mostrarNotificacion(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarDetalleCartera", detalleCarteraSeleccionada);
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
	public void onEliminar(@BindingParam("seleccionado") DetalleCartera detalleCartera) {
		log.info("onEliminar => " + detalleCartera.getSecuencia());
		if ((Messagebox.show(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.NO | Messagebox.YES, Messagebox.QUESTION)) == Messagebox.YES) {

			log.info("Messagebox.YES => " + detalleCartera.getSecuencia());
			Conexion.getConexion().eliminar("eliminarDetalleCartera", detalleCartera);
			Utilidades.mostrarNotificacion(idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_TITULO").toString(),
					idWINFORMDETCARTERAZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(), "INFO");
			BindUtils.postNotifyChange(null, null, FormularioCarteraDetalleViewModel.this, "*");
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
		setDesactivarformulario(true);
	}

	@NotifyChange("*")
	@Command
	public void onCancelar() {
		log.info("onCancelar=> " + detalleCarteraSeleccionada.getSecuencia());
		if (!accion.equals("I")) {
			detalleCarteraSeleccionada = obtener(detalleCarteraSeleccionada);
			onSeleccionar(detalleCarteraSeleccionada);
			desactivarformulario=true;
		}else{
			onNuevo();
		}

	}

	private DetalleCartera obtener(DetalleCartera detCartera) {
		log.info("Ejecutando el metodo [obtener]");
		DetalleCartera car = null;
		try {
			car = (DetalleCartera) Conexion.getConexion().obtenerRegistro("obtenerDetalleCarteras", detCartera);

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
		detalleCarteraSeleccionada = new DetalleCartera();
		detalleCarteraSeleccionada.setFechaActualizacion(new Date());
		detalleCarteraSeleccionada.setFechaCreacion(new Date());
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

		log.info("SEC_CARTERA AL LISTAR ==>" + getCartera().getSecuencia());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SEC_CARTERA", getCartera().getSecuencia());

		listaDetalleCartera.clear();

		setDesactivarformulario(true);
		try {
			setListaDetalleCartera((List<DetalleCartera>) Conexion.getConexion()
					.obtenerListado("listarDetalleCarterasPorClientes", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DetalleCartera> getListaDetalleCartera() {
		return listaDetalleCartera;
	}

	@NotifyChange("listaDetalleCartera")
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

	public Cartera getCartera() {
		return cartera;
	}

	public void setCartera(Cartera cartera) {
		this.cartera = cartera;
	}

}