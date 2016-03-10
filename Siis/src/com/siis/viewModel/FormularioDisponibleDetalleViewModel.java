package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Date; 
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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
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
	public Disponible disponible;

	@Wire
	public Borderlayout idWINFORMDETDISPBCOZPrincipal;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setDisponible((Disponible) parametros.get("DISPONIBLE"));
		log.info("disponible ..... " + disponible.getSecuencia());
		listaDisponibleBanco = new ArrayList<DisponibleBanco>();
		disponibleBancoSeleccionado = new DisponibleBanco();
		setDesactivarformulario(true);
		listarDisponibleBanco();
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

			disponibleBancoSeleccionado.setDisponible(disponible);

			if (accion.equals("I")) {
				disponibleBancoSeleccionado.setSecuencia(10);
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
								listarDisponibleBanco();
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
		disponibleBancoSeleccionado = new DisponibleBanco();
		disponibleBancoSeleccionado.setFechaHoraActualizacion(new Date());
		disponibleBancoSeleccionado.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDisponibleBanco() {
		log.info(" listarDisponibleBanco ");
		listaDisponibleBanco = new ArrayList<DisponibleBanco>();

		log.info("SEC_disponible AL LISTAR ==>" + getDisponible().getSecuencia());
		DisponibleBanco dispoBan = new DisponibleBanco();
		dispoBan.setDisponible(getDisponible());

		listaDisponibleBanco.clear();

		setDesactivarformulario(true);
		try {
			setListaDisponibleBanco(
					(List<DisponibleBanco>) Conexion.getConexion().obtenerListado("listarDisponibleBancos", dispoBan));
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

	public Disponible getDisponible() {
		return disponible;
	}

	public void setDisponible(Disponible disponible) {
		this.disponible = disponible;
	}

	public DisponibleBanco getDisponibleBancoSeleccionado() {
		return disponibleBancoSeleccionado;
	}

	public void setDisponibleBancoSeleccionado(DisponibleBanco disponibleBancoSeleccionado) {
		this.disponibleBancoSeleccionado = disponibleBancoSeleccionado;
	}

}