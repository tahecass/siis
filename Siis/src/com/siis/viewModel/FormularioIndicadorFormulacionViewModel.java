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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Messagebox;
import com.siis.configuracion.Conexion; 
import com.siis.dto.Formulacion; 
import com.siis.dto.Indicador;
import com.siis.viewModel.framework.Utilidades;

public class FormularioIndicadorFormulacionViewModel {

	protected static Logger log = Logger.getLogger(FormularioDisponibleViewModel.class);
	public List<Formulacion> listaFormulacion;
	public Formulacion formulacionSeleccionado;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar;
	private String accion;
	private Map<String, Object> parametros;
	public Indicador indicador;

	@Wire
	public Borderlayout idWINFORMFORMULACIONZPrincipal;

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		setIndicador((Indicador) parametros.get("INDICADOR"));
		log.info("indicador ..... " + indicador.getSecuencia());
		listaFormulacion = new ArrayList<Formulacion>();
		formulacionSeleccionado = new  Formulacion();
		setDesactivarformulario(true);
		listarFormulacion();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
	}

	@NotifyChange("*")
	@Command
	public void guardarFormulacion() {
		try {
			log.info("accion=>> " + accion);

			formulacionSeleccionado.setIndicador(indicador);

			if (accion.equals("I")) {
				formulacionSeleccionado.setSecuencia(10);
				Conexion.getConexion().guardar("guardarFormulacion", formulacionSeleccionado);
				log.info("Disponibleguardada");
				Utilidades.mostrarNotificacion(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {

				Conexion.getConexion().actualizar("actualizarFormulacion", formulacionSeleccionado);
				log.info("DisponibleActualizada");
				Utilidades.mostrarNotificacion(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarFormulacion();
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
	public void onEliminar(@BindingParam("seleccionado") Formulacion detalleDisponible) {
		log.info("onEliminar => " + detalleDisponible.getSecuencia());
		if ((Messagebox.show(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_ELIMINAR_CARTERA").toString(),
				idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.NO | Messagebox.YES, Messagebox.QUESTION)) == Messagebox.YES) {

			log.info("Messagebox.YES => " + detalleDisponible.getSecuencia());
			Conexion.getConexion().eliminar("eliminarFormulacion", detalleDisponible);
			Utilidades.mostrarNotificacion(idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_TITULO").toString(),
					idWINFORMFORMULACIONZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(), "INFO");
			listarFormulacion();
			setDesactivarBtnNuevo(false);
			setDesactivarBtnEditar(true);
			setDesactivarBtnGuardar(true);
			setDesactivarBtnEliminar(true);
		}
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Formulacion detalleDisponible) {
		log.info("onSeleccionar==> ");
		setFormulacionSeleccionado(detalleDisponible);
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
		formulacionSeleccionado = new Formulacion();
		formulacionSeleccionado.setFechaHoraActualizacion(new Date());
		formulacionSeleccionado.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarFormulacion() {
		log.info(" listarFormulacion ");
		listaFormulacion = new ArrayList<Formulacion>();

		log.info("SEC_disponible AL LISTAR ==>" + getIndicador().getSecuencia());
		Formulacion dispoBan = new Formulacion();
		dispoBan.setIndicador(getIndicador());

		listaFormulacion.clear();

		setDesactivarformulario(true);
		try {
			setListaFormulacion(
					(List<Formulacion>) Conexion.getConexion().obtenerListado("listarFormulaciones", dispoBan));
		} catch (Exception e) {
			e.printStackTrace();

		}
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

	 

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public List<Formulacion> getListaFormulacion() {
		return listaFormulacion;
	}

	public void setListaFormulacion(List<Formulacion> listaFormulacion) {
		this.listaFormulacion = listaFormulacion;
	}

	public Formulacion getFormulacionSeleccionado() {
		return formulacionSeleccionado;
	}

	public void setFormulacionSeleccionado(Formulacion formulacionSeleccionado) {
		this.formulacionSeleccionado = formulacionSeleccionado;
	}

	 

	 

}