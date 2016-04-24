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

import com.siis.configuracion.Conexion;
import com.siis.dto.Disponible;
import com.siis.dto.DisponibleBanco;
import com.siis.viewModel.framework.Utilidades;

public class FormularioDisponibleDetalleViewModel {

	protected static Logger log = Logger.getLogger(FormularioDisponibleViewModel.class);
	public List<DisponibleBanco> listaDisponibleBanco;
	public DisponibleBanco disponibleBancoSeleccionado;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	private Disponible disponible;

	@Wire
	public Borderlayout idWINFORMDETDISPBCOZPrincipal;
	@Wire
	private Grid idWINFORMDETDISPBCOZGridFormulario;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);
		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		disponible = (Disponible) parametros.get("DISPONIBLE");
		init();
		listarDisponibleBanco();
	}

	 
	public void init() {
		log.info("INIT()");

		listaDisponibleBanco = new ArrayList<DisponibleBanco>();
		disponibleBancoSeleccionado = new DisponibleBanco();
		setDesactivarformulario(true);

		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarDisponibleBanco() {
		try {
			log.info("accion=>> " + accion);

			if (!Utilidades.validarFormulario(idWINFORMDETDISPBCOZGridFormulario)) {
				Utilidades.mostrarNotificacion(idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}
			disponibleBancoSeleccionado.setDisponible(disponible);
			log.info("DISPONIBLE ==> " + disponible.getSecuencia());
			if (accion.equals("I")) {

				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "DISPONIBLE_BANCO");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					disponibleBancoSeleccionado.setSecuencia(sigSec);
				else
					disponibleBancoSeleccionado.setSecuencia(1);

				Conexion.getConexion().guardar("guardarDisponibleBanco", disponibleBancoSeleccionado);
				log.info("Disponibleguardada");
				Utilidades.mostrarNotificacion(idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarDisponibleBanco", disponibleBancoSeleccionado);
				log.info("DisponibleActualizada");
				Utilidades.mostrarNotificacion(idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarDisponibleBanco();
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
	public void onEliminar(@BindingParam("seleccionado") final DisponibleBanco detalleDisponible) {
		log.info("onEliminar => " + detalleDisponible.getSecuencia());

		Messagebox.show(idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {

							log.info("Messagebox.YES => " + detalleDisponible.getSecuencia());
							Conexion.getConexion().eliminar("eliminarDisponibleBanco", detalleDisponible);
							Utilidades.mostrarNotificacion(
									idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							BindUtils.postNotifyChange(null, null, FormularioDisponibleDetalleViewModel.this, "*");
							listarDisponibleBanco();
							disponibleBancoSeleccionado = new DisponibleBanco();
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
	public void onSeleccionar(@BindingParam("seleccionado") DisponibleBanco detalleDisponible) {
		log.info("onSeleccionar==> ");
		setDisponibleBancoSeleccionado(detalleDisponible);
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
		disponibleBancoSeleccionado.setFechaHoraActualizacion(new Timestamp(date.getTime()));

		accion = "U";
		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void onCancelar() {

		if (!accion.equals("I")) {
			disponibleBancoSeleccionado = obtener(disponibleBancoSeleccionado);
			onSeleccionar(disponibleBancoSeleccionado);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	private DisponibleBanco obtener(DisponibleBanco dispCon) {
		log.info("Ejecutando el metodo [obtener]");
		DisponibleBanco dc = null;
		try {
			dc = (DisponibleBanco) Conexion.getConexion().obtenerRegistro("obtenerDisponibleBanco", dispCon);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		log.info("onNuevo");
		setDesactivarformulario(false);
		disponibleBancoSeleccionado = new DisponibleBanco();
		java.util.Date date = new java.util.Date();
		disponibleBancoSeleccionado.setFechaHoraActualizacion(new Timestamp(date.getTime()));

		disponibleBancoSeleccionado.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange
	@Command
	public void listarDisponibleBanco() {

		log.info(" listarDisponibleBanco " + disponible.getSecuencia());
		listaDisponibleBanco = new ArrayList<DisponibleBanco>();

		DisponibleBanco dispoBan = new DisponibleBanco();
		dispoBan.setDisponible(disponible);

		setDesactivarformulario(true);

		try {
			setListaDisponibleBanco(
					(List<DisponibleBanco>) Conexion.getConexion().obtenerListado("listarDisponibleBancos", dispoBan));
			log.info("==> " + getListaDisponibleBanco().get(0).getBeneficiario());
			BindUtils.postNotifyChange(null, null, FormularioDisponibleDetalleViewModel.this, "*");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<DisponibleBanco> getListaDisponibleBanco() {
		return listaDisponibleBanco;
	}

	@NotifyChange("listaDisponibleBanco")
	public void setListaDisponibleBanco(List<DisponibleBanco> listaDisponibleBanco) {
		this.listaDisponibleBanco = listaDisponibleBanco;
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

	public DisponibleBanco getDisponibleBancoSeleccionado() {
		return disponibleBancoSeleccionado;
	}

	public void setDisponibleBancoSeleccionado(DisponibleBanco disponibleBancoSeleccionado) {
		this.disponibleBancoSeleccionado = disponibleBancoSeleccionado;
	}

	public void setDisponible(Disponible disponible) {
		this.disponible = disponible;
		log.info("disponible.getSecuencia() ==> " + disponible.getSecuencia());

	}

	public Disponible getDisponible() {
		return disponible;
	}

}