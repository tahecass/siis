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
import com.siis.dto.AmortizacionCredito;
import com.siis.dto.Credito;
import com.siis.viewModel.framework.Utilidades;

public class FormularioCreditoDetalleViewModel {

	protected static Logger log = Logger.getLogger(FormularioDisponibleViewModel.class);
	public List<AmortizacionCredito> listaAmortizacionCredito;
	public AmortizacionCredito amortizacionCreditoSeleccionado;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	public Credito credito;

	@Wire
	public Borderlayout idWINFORMDETDISPBCOZPrincipal;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setCredito((Credito) parametros.get("CREDITO"));
		log.info("disponible ..... " + credito.getSecuencia());
		listaAmortizacionCredito = new ArrayList<AmortizacionCredito>();
		amortizacionCreditoSeleccionado = new AmortizacionCredito();
		setDesactivarformulario(true);
		listarAmortizacionCredito();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarAmortizacionCredito() {
		try {
			log.info("accion=>> " + accion);

			amortizacionCreditoSeleccionado.setCredito(credito);

			if (accion.equals("I")) {
				amortizacionCreditoSeleccionado.setSecuencia(10);
				Conexion.getConexion().guardar("guardarAmortizacionCredito", amortizacionCreditoSeleccionado);
				log.info("Disponibleguardada");
				Utilidades.mostrarNotificacion(idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarAmortizacionCredito", amortizacionCreditoSeleccionado);
				log.info("DisponibleActualizada");
				Utilidades.mostrarNotificacion(idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarAmortizacionCredito();
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
	public void onEliminar(@BindingParam("seleccionado") final AmortizacionCredito detalleDisponible) {
		log.info("onEliminar => " + detalleDisponible.getSecuencia());

		Messagebox.show(idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {
							log.info("Messagebox.YES => " + detalleDisponible.getSecuencia());
							Conexion.getConexion().eliminar("eliminarAmortizacionCredito", detalleDisponible);
							Utilidades.mostrarNotificacion(
									idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMDETDISPBCOZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							listarAmortizacionCredito();
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
	public void onSeleccionar(@BindingParam("seleccionado") AmortizacionCredito detalleDisponible) {
		log.info("onSeleccionar==> ");
		setAmortizacionCreditoSeleccionado(detalleDisponible);
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
		amortizacionCreditoSeleccionado = new AmortizacionCredito();
		amortizacionCreditoSeleccionado.setFechaHoraActualizacion(new Date());
		amortizacionCreditoSeleccionado.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarAmortizacionCredito() {
		log.info(" listarAmortizacionCredito ");
		listaAmortizacionCredito = new ArrayList<AmortizacionCredito>();

		log.info("CREDITO AL LISTAR ==>" + getCredito().getSecuencia());
		AmortizacionCredito dispoBan = new AmortizacionCredito();
		dispoBan.setCredito(getCredito());

		listaAmortizacionCredito.clear();

		setDesactivarformulario(true);
		try {
			setListaAmortizacionCredito((List<AmortizacionCredito>) Conexion.getConexion()
					.obtenerListado("listarAmortizacionCreditos", dispoBan));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<AmortizacionCredito> getListaAmortizacionCredito() {
		return listaAmortizacionCredito;
	}

	@NotifyChange("listaAmortizacionCredito")
	public void setListaAmortizacionCredito(List<AmortizacionCredito> listaAmortizacionCredito) {
		this.listaAmortizacionCredito = listaAmortizacionCredito;
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

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public AmortizacionCredito getAmortizacionCreditoSeleccionado() {
		return amortizacionCreditoSeleccionado;
	}

	public void setAmortizacionCreditoSeleccionado(AmortizacionCredito amortizacionCreditoSeleccionado) {
		this.amortizacionCreditoSeleccionado = amortizacionCreditoSeleccionado;
	}

}