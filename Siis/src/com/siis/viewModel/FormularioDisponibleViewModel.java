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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Disponible;
import com.siis.dto.DisponibleBanco;
import com.siis.dto.Usuario;
import com.siis.viewModel.framework.BandboxBancos;
import com.siis.viewModel.framework.BandboxCuentas;
import com.siis.viewModel.framework.Utilidades;

public class FormularioDisponibleViewModel {
	protected static Logger log = Logger.getLogger(FormularioDisponibleViewModel.class);

	public List<Disponible> listaDisponible;
	public Disponible disponibleSeleccionada;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar, desactivarTabDetalle;
	private String accion;
	Conexion con;

	@Wire
	private BandboxBancos idFORMDISPONIBLEZBbxBanco;
	@Wire
	private BandboxCuentas idFORMDISPONIBLEZBbxCuenta;
	@Wire
	public Borderlayout idWINFORMDISPONIBLEZPrincipal;
	@Wire
	private Grid idWINFORMDISPONIBLEZFormularioPrincipal;
	@Wire
	private Tabpanel idDISPONIBLEZTpnDetalleDisponible, idDISPONIBLEZTpnConsultaDisponible,
			idDISPONIBLEZTpnDetalleDisponobleConcepto;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);
		listaDisponible = new ArrayList<Disponible>();
		disponibleSeleccionada = new Disponible();
		setDesactivarformulario(true);
		listarDisponible();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("listaDetalleDisponible")
	@Command
	public void onAgregar() {
		log.info(" onAgregar ==> ");

		// ListModelList<DetalleDisponible> lm = new
		// ListModelList<DetalleDisponible>(
		// Arrays.asList(new DetalleDisponible(new Disponible(), "", 0.0, new
		// Date(),
		// "")));
		//
		// listaDetalleDisponible.addAll(lm);

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("DISPONIBLE", disponibleSeleccionada);
			parametros.put("OPT", "N");
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_disponible_detalle.zul",
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
	public void guardarDisponible() {
		try {
			log.info("accion=>> " + accion);

			if (!Utilidades.validarFormulario(idWINFORMDISPONIBLEZFormularioPrincipal)) {
				Utilidades.mostrarNotificacion(idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_TITULO").toString(),
						"Por favor diligencie todos los campos requeridos (*)", "ADVERTENCIA");
				return;
			}

			disponibleSeleccionada.setBanco(idFORMDISPONIBLEZBbxBanco.getValue());
			disponibleSeleccionada.setUsuario(new Usuario(new Integer(1)));
			disponibleSeleccionada.setCuenta(idFORMDISPONIBLEZBbxCuenta.getValue());

			if (accion.equals("I")) {

				HashMap<String, Object> par = new HashMap<String, Object>();
				par.put("NOMBRE_TABLA", "DISPONIBLES");
				Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

				if (sigSec != null)
					disponibleSeleccionada.setSecuencia(sigSec);
				else
					disponibleSeleccionada.setSecuencia(1);

				Conexion.getConexion().guardar("guardarDisponible", disponibleSeleccionada);
				log.info("Disponibleguardada");
				Utilidades.mostrarNotificacion(idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {
				Conexion.getConexion().actualizar("actualizarDisponible", disponibleSeleccionada);
				log.info("DisponibleActualizada");
				Utilidades.mostrarNotificacion(idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarDisponible();
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
	public void onEliminar(@BindingParam("seleccionado") final Disponible disponible) {
		log.info("onEliminar => " + disponible.getSecuencia());

		Messagebox.show(idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(),
				idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {

					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_OK.equals(e.getName())) {
							Conexion.getConexion().eliminar("eliminarDisponible", disponible);
							Utilidades.mostrarNotificacion(
									idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_TITULO").toString(),
									idWINFORMDISPONIBLEZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR_OK").toString(),
									"INFO");
							BindUtils.postNotifyChange(null, null, FormularioDisponibleViewModel.this, "*");
							listarDisponible();
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
	public void onSeleccionar(@BindingParam("seleccionado") Disponible disponible) {
		log.info("onSeleccionar==> " + disponible.getSecuencia());
		setDisponibleSeleccionada(disponible);
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
		disponibleSeleccionada = new Disponible();
		disponibleSeleccionada.setFechaHoraActualizacion(new Date());
		disponibleSeleccionada.setFechaCreacion(new Date());
		accion = "I";

		setDesactivarBtnNuevo(true);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(false);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("*")
	@Command
	public void onCancelar() {

		if (!accion.equals("I")) {
			disponibleSeleccionada = obtener(disponibleSeleccionada);
			onSeleccionar(disponibleSeleccionada);
			desactivarformulario = true;
		} else {
			onNuevo();
		}

	}

	private Disponible obtener(Disponible dispCon) {
		log.info("Ejecutando el metodo [obtener]");
		Disponible dc = null;
		try {
			dc = (Disponible) Conexion.getConexion().obtenerRegistro("obtenerDisponible", dispCon);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDisponible() {
		log.info(" listarDisponible ");
		listaDisponible = new ArrayList<Disponible>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		setDesactivarformulario(true);
		try {
			con = new Conexion();
			// setListaDisponible((List<Disponible>)
			// Conexion.getConexion().obtenerListado("listarDisponibles",
			// parametros));

			setListaDisponible((List<Disponible>) con.obtenerListado("listarDisponibles", parametros));
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
			parametros.put("DISPONIBLE", disponibleSeleccionada);
			log.info("idDISPONIBLEZTpnDetalleDisponible.getChildren().size() ==> "
					+ idDISPONIBLEZTpnDetalleDisponible.getChildren().size());
			if (idDISPONIBLEZTpnDetalleDisponible.getChildren().size() == 0) {

				log.info("Disponoble==> primera: " + disponibleSeleccionada.getSecuencia());
				Utilidades.onCargarVentana(idDISPONIBLEZTpnDetalleDisponible,
						"//formas//formulario_disponible_detalle.zul", parametros);
			} else {
				FormularioDisponibleDetalleViewModel detalleDisponible = new FormularioDisponibleDetalleViewModel();

				log.info("Disponoble==> segunda: " + disponibleSeleccionada.getSecuencia());
				detalleDisponible.setDisponible(disponibleSeleccionada);
				detalleDisponible.listarDisponibleBanco();
				log.info("1");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

		@NotifyChange("*")
	@Command
	public void onMostrarVentanaDetalleConcepto() {
		log.info("onMostrarVentanaDetalle");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			log.info("Disponoble==> 1" + disponibleSeleccionada.getSecuencia());
			parametros.put("DISPONIBLE", disponibleSeleccionada);
			if (idDISPONIBLEZTpnDetalleDisponobleConcepto.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idDISPONIBLEZTpnDetalleDisponobleConcepto,
						"//formas//formulario_disponible_detalle_concepto.zul", parametros);
			} else {
				FormularioDisponibleDetalleConceptoViewModel detalleDisponible = new FormularioDisponibleDetalleConceptoViewModel();

				log.info("Disponoble==> 2" + disponibleSeleccionada.getSecuencia());
				detalleDisponible.setDisponible(disponibleSeleccionada);
				detalleDisponible.listarDisponibleConcepto();
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
			log.info("DISPONIBLE ENVIADA ==> " + disponibleSeleccionada.getSecuencia());
			parametros.put("OBJETO", disponibleSeleccionada);
			if (idDISPONIBLEZTpnConsultaDisponible.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idDISPONIBLEZTpnConsultaDisponible, "//formas//vista_disponible.zul",
						parametros);
			} else {
				// FormularioDisponibleDetalleViewModel.ca
				//// detalleDisponible.listarDetalleDisponible();
				// log.info("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Disponible> getListaDisponible() {
		return listaDisponible;
	}

	public void setListaDisponible(List<Disponible> listaDisponible) {
		this.listaDisponible = listaDisponible;
	}

	public Disponible getDisponibleSeleccionada() {
		return disponibleSeleccionada;
	}

	public void setDisponibleSeleccionada(Disponible disponibleSeleccionada) {
		this.disponibleSeleccionada = disponibleSeleccionada;
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