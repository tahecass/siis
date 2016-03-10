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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Messagebox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Proyecto;
import com.siis.dto.ProyectoValor;
import com.siis.viewModel.framework.Utilidades;

public class FormularioProyectoValoresDetalleViewModel {

	protected static Logger log = Logger.getLogger(FormularioCarteraViewModel.class);
	public List<ProyectoValor> listaProyectoValor;
	public ProyectoValor detalleCarteraSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	public Proyecto proyecto;

	@Wire
	public Borderlayout idWINFORMPROVALORZPrincipal;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setProyecto((Proyecto) parametros.get("PROYECTO"));
		log.info("proyecto ..... " + proyecto.getSecuencia());
		listaProyectoValor = new ArrayList<ProyectoValor>();
		detalleCarteraSeleccionada = new ProyectoValor();
		setDesactivarformulario(true);
		listarProyectoValor();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarProyectoValor() {
		try {
			log.info("accion=>> " + accion);

			detalleCarteraSeleccionada.setProyecto(getProyecto());
			;

			if (accion.equals("I")) {
				detalleCarteraSeleccionada.setSecuencia(10);
				Conexion.getConexion().guardar("guardarProyectoValor", detalleCarteraSeleccionada);
				log.info("Carteraguardada");
				Utilidades.mostrarNotificacion(idWINFORMPROVALORZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMPROVALORZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarProyectoValor", detalleCarteraSeleccionada);
				log.info("CarteraActualizada");
				Utilidades.mostrarNotificacion(idWINFORMPROVALORZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMPROVALORZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarProyectoValor();
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
	public void onEliminar(@BindingParam("seleccionado") final ProyectoValor detalleCartera) {
		log.info("onEliminar => " + detalleCartera.getSecuencia());

		Messagebox.show(idWINFORMPROVALORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMPROVALORZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {

							log.info("Messagebox.YES => " + detalleCartera.getSecuencia());
							Conexion.getConexion().eliminar("eliminarProyectoValor", detalleCartera);
							Utilidades.mostrarNotificacion(
									idWINFORMPROVALORZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMPROVALORZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							listarProyectoValor();
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
	public void onSeleccionar(@BindingParam("seleccionado") ProyectoValor detalleCartera) {
		log.info("onSeleccionar==> ");
		setProyectoValorSeleccionada(detalleCartera);
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
		detalleCarteraSeleccionada = new ProyectoValor();
		detalleCarteraSeleccionada.setFechaHoraActualizacion(new Date());
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
	public void listarProyectoValor() {
		log.info(" listarProyectoValor ");
		listaProyectoValor = new ArrayList<ProyectoValor>();

		log.info("SEC_CARTERA AL LISTAR ==>" + getProyecto().getSecuencia());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SEC_PROYECTO", getProyecto().getSecuencia());

		listaProyectoValor.clear();

		setDesactivarformulario(true);
		try {
			setListaProyectoValor(
					(List<ProyectoValor>) Conexion.getConexion().obtenerListado("listarProyectoValor", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<ProyectoValor> getListaProyectoValor() {
		return listaProyectoValor;
	}

	@NotifyChange("listaProyectoValor")
	public void setListaProyectoValor(List<ProyectoValor> listaProyectoValor) {
		this.listaProyectoValor = listaProyectoValor;
	}

	public ProyectoValor getProyectoValorSeleccionada() {
		return detalleCarteraSeleccionada;
	}

	public void setProyectoValorSeleccionada(ProyectoValor detalleCarteraSeleccionada) {
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

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

}