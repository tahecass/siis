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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;

import com.siis.configuracion.Conexion;
import com.siis.dto.Proyecto; 
import com.siis.viewModel.framework.BandboxBancos; 
import com.siis.viewModel.framework.Utilidades;

public class FormularioProyectoViewModel {
	protected static Logger log = Logger.getLogger(FormularioProyectoViewModel.class);
	
	public List<Proyecto> listaProyecto; 
	public Proyecto proyectoSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar,desactivarTabDetalle;
	private String accion;


	@Wire
	private BandboxBancos idFORMPROYECTOZBbxEntidad; 
	@Wire
	public Borderlayout idWINFORMPROYECTOZPrincipal;
	@Wire
	private Tabpanel idDISPONIBLEZTpnDetalleProyecto, idDISPONIBLEZTpnConsultaProyecto,idDISPONIBLEZTpnDetalleContrartos;

	 

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false); 
		listaProyecto = new ArrayList<Proyecto>();
		proyectoSeleccionada = new Proyecto();
		setDesactivarformulario(true);
		listarProyecto();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true); 
		setDesactivarTabDetalle(true);
	}


	@NotifyChange("*")
	@Command
	public void guardarProyecto() {
		try {
			log.info("accion=>> " + accion); 

			proyectoSeleccionada.setEntidad(idFORMPROYECTOZBbxEntidad.getValue()); 
			 

			if (accion.equals("I")) {
				proyectoSeleccionada.setSecuencia(10);
				Conexion.getConexion().guardar("guardarProyecto", proyectoSeleccionada);
				log.info("Proyectoguardada");
				Utilidades.mostrarNotificacion(idWINFORMPROYECTOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMPROYECTOZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {
				Conexion.getConexion().actualizar("actualizarProyecto", proyectoSeleccionada);
				log.info("ProyectoActualizada");
				Utilidades.mostrarNotificacion(idWINFORMPROYECTOZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMPROYECTOZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarProyecto();
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
	public void onEliminar(@BindingParam("seleccionado") Proyecto proyecto) {
		log.info("onEliminar => " + proyecto.getSecuencia());
		if ((Messagebox.show(idWINFORMPROYECTOZPrincipal.getAttribute("MSG_ELIMINAR_DISPONIBLE").toString(),
				idWINFORMPROYECTOZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.NO | Messagebox.YES, Messagebox.QUESTION)) == Messagebox.YES) {

			log.info("Messagebox.YES => " + proyecto.getSecuencia());
			Conexion.getConexion().eliminar("eliminarProyecto", proyecto);
			Utilidades.mostrarNotificacion(idWINFORMPROYECTOZPrincipal.getAttribute("MSG_TITULO").toString(),
					idWINFORMPROYECTOZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(), "INFO");
			listarProyecto();
			setDesactivarBtnNuevo(false);
			setDesactivarBtnEditar(true);
			setDesactivarBtnGuardar(true);
			setDesactivarBtnEliminar(true);
			setDesactivarTabDetalle(true);
		}
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Proyecto proyecto) {
		log.info("onSeleccionar==> " +proyecto.getSecuencia());
		setProyectoSeleccionada(proyecto);
		accion = "U";
		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(false);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(false);
		setDesactivarTabDetalle(false);
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
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		log.info("onNuevo");
		setDesactivarformulario(false);
		proyectoSeleccionada = new Proyecto();
		proyectoSeleccionada.setFechaHoraActualizacion(new Date());
		proyectoSeleccionada.setFechaCreacion(new Date());
		proyectoSeleccionada.setFecha(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarProyecto() {
		log.info(" listarProyecto ");
		listaProyecto = new ArrayList<Proyecto>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		setDesactivarformulario(true);
		try {
		
		setListaProyecto((List<Proyecto>) Conexion.getConexion().obtenerListado("listarProyectos", parametros));

//			setListaProyecto((List<Proyecto>) con.obtenerListado("listarProyectos", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	 

	@NotifyChange("*")
	@Command
	public void onMostrarVentanaDetalle() {
		log.info("onMostrarVentanaDetalle");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("Disponoble==> 1"+ proyectoSeleccionada.getSecuencia());
			parametros.put("PROYECTO", proyectoSeleccionada);
			if (idDISPONIBLEZTpnDetalleProyecto.getChildren().size() == 0) {
			Utilidades.onCargarVentana(idDISPONIBLEZTpnDetalleProyecto, "//formas//formulario_proyecto_valores_detalle.zul",
						parametros);
			} else {
				FormularioProyectoValoresDetalleViewModel detalleProyecto= new FormularioProyectoValoresDetalleViewModel();

				log.info("Disponoble==> 2"+ proyectoSeleccionada.getSecuencia());
				detalleProyecto.setProyecto(proyectoSeleccionada);
				detalleProyecto.listarProyectoValor(); 
				log.info("1");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	@Command
	public void onMostrarVentanaDetalleContratos() {
		log.info("onMostrarVentanaDetalleContratos");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("proyecto==> 1"+ proyectoSeleccionada.getSecuencia());
			parametros.put("PROYECTO", proyectoSeleccionada);
			if (idDISPONIBLEZTpnDetalleContrartos.getChildren().size() == 0) {
			Utilidades.onCargarVentana(idDISPONIBLEZTpnDetalleContrartos, "//formas//formulario_proyecto_contratos_detalle.zul",
						parametros);
			} else {
				FormularioProyectoContratosDetalleViewModel detalleProyectoContratos= new FormularioProyectoContratosDetalleViewModel();

				log.info("proyecto==> 2"+ proyectoSeleccionada.getSecuencia());
				detalleProyectoContratos.setProyecto(proyectoSeleccionada);
				detalleProyectoContratos.listarProyectoContrato(); 
				log.info("1");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Command
	public void onMostrarVentanaConsulta() {
		log.info("onMostrarVentanaConsulta");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("DISPONIBLE ENVIADA ==> "+ proyectoSeleccionada.getSecuencia());
			parametros.put("OBJETO", proyectoSeleccionada);
			 if (idDISPONIBLEZTpnConsultaProyecto.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idDISPONIBLEZTpnConsultaProyecto, "//formas//vista_proyecto.zul",
							parametros);
				} else {
//					FormularioProyectoDetalleViewModel.ca
////					detalleProyecto.listarDetalleProyecto();
//					log.info("1");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 

	public List<Proyecto> getListaProyecto() {
		return listaProyecto;
	}

	public void setListaProyecto(List<Proyecto> listaProyecto) {
		this.listaProyecto = listaProyecto;
	}

	public Proyecto getProyectoSeleccionada() {
		return proyectoSeleccionada;
	}

	public void setProyectoSeleccionada(Proyecto proyectoSeleccionada) {
		this.proyectoSeleccionada = proyectoSeleccionada;
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