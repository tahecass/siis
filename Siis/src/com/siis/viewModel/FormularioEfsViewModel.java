package com.siis.viewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;
import com.siis.configuracion.Conexion;
import com.siis.dto.Efs;
import com.siis.viewModel.framework.Utilidades;

public class FormularioEfsViewModel {
	protected static Logger log = Logger.getLogger(FormularioEfsViewModel.class);
	public List<Efs> listaEfs;
	public Efs EfSeleccionado;
	private boolean desactivarformulario, desactivarBtnNuevo, desactivarBtnEditar, desactivarBtnEliminar,
			desactivarBtnGuardar, desactivarTabDetalle;
	private String accion;
	Conexion con;

	@Wire
	public Borderlayout idWINFORMEFSZPrincipal;
	@Wire
	Label labelNombreArchivo;
	@Wire
	private Tabpanel idCARTERAZTpnConsultaEfs;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		listaEfs = new ArrayList<Efs>();
		EfSeleccionado = new Efs();
		setDesactivarformulario(true);
		listarEfs();
		accion = new String();

		setDesactivarBtnNuevo(false);
		setDesactivarBtnEditar(true);
		setDesactivarBtnGuardar(true);
		setDesactivarBtnEliminar(true);
		setDesactivarTabDetalle(true);
	}

	@NotifyChange("listaDetalleEfs")
	@Command
	public void onAgregar() {
		log.info(" onAgregar ==> ");

		// ListModelList<DetalleEfs> lm = new
		// ListModelList<DetalleEfs>(
		// Arrays.asList(new DetalleEfs(new Efs(), "", 0.0, new
		// Date(),
		// "")));
		//
		// listaDetalleEfs.addAll(lm);

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("CARTERA", EfSeleccionado);
			parametros.put("OPT", "N");
			Window win = (Window) Utilidades.onCargarVentana(null, "//formas//formulario_cartera_detalle.zul",
					parametros);

			win.setHeight("auto");
			win.setPosition("center,top");
			win.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validarSoloExtensio(String mimeType) {
		log.debug("Ejecutando M�todo [ validarSoloExtensio ]");

		if (mimeType.trim().toLowerCase().equals("application/pdf")) {
			return true;
		}

		return false;

	}

	@Command
	public void subirArchivo(@BindingParam("media") Media media) {
		log.debug("Ejecutando Método [ subirArchivo ]");

		log.trace("blob ==>" + media);
		log.trace("MimeType ==>" + media.getContentType());

		try {

			if (!validarSoloExtensio(media.getContentType())) {
				Messagebox.show("El tipo de Archivo seleccionado, no corresponde a un archivo con formato: "
						.concat(media.getContentType()), "", Messagebox.ABORT, Messagebox.ERROR);
				return;
			}

			byte[] blob;
			// if (media.getContentType().contains("text/"))
			// blob = media.getStringData().getBytes();
			// else
			blob = IOUtils.toByteArray(media.getStreamData());

			if (blob.length > 0) {
				boolean sw = false;

				EfSeleccionado.setContenidoBinarioArchivo(blob);
				labelNombreArchivo.setValue(media.getName());

			}

		} catch (IOException e) {
			log.error(e.getCause(), e);
		} catch (Exception e) {
			log.error(e.getCause(), e);
		}

	}

	@NotifyChange("*")
	@Command
	public void guardarEfs() {
		try {
			log.info("accion=>> " + accion);

			EfSeleccionado.setSecuencia(10);
			if (accion.equals("I")) {

				Conexion.getConexion().guardar("guardarEfs", EfSeleccionado);
				log.info("Efsguardada");
				Utilidades.mostrarNotificacion(idWINFORMEFSZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMEFSZPrincipal.getAttribute("MSG_MENSAJE_GUARDAR").toString(), "INFO");

			} else if (accion.equals("U")) {
				Conexion.getConexion().actualizar("actualizarEfs", EfSeleccionado);
				log.info("EfsActualizada");
				Utilidades.mostrarNotificacion(idWINFORMEFSZPrincipal.getAttribute("MSG_TITULO").toString(),
						idWINFORMEFSZPrincipal.getAttribute("MSG_MENSAJE_ACTUALIZAR").toString(), "INFO");
			}

			listarEfs();
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
	public void onEliminar(@BindingParam("seleccionado") Efs cartera) {
		log.info("onEliminar => " + cartera.getSecuencia());
		if ((Messagebox.show(idWINFORMEFSZPrincipal.getAttribute("MSG_ELIMINAR_CARTERA").toString(),
				idWINFORMEFSZPrincipal.getAttribute("MSG_TITULO_ELIMINAR").toString(), Messagebox.NO | Messagebox.YES,
				Messagebox.QUESTION)) == Messagebox.YES) {

			log.info("Messagebox.YES => " + cartera.getSecuencia());
			Conexion.getConexion().eliminar("eliminarEfs", cartera);
			Utilidades.mostrarNotificacion(idWINFORMEFSZPrincipal.getAttribute("MSG_TITULO").toString(),
					idWINFORMEFSZPrincipal.getAttribute("MSG_MENSAJE_ELIMINAR").toString(), "INFO");
			listarEfs();
			setDesactivarBtnNuevo(false);
			setDesactivarBtnEditar(true);
			setDesactivarBtnGuardar(true);
			setDesactivarBtnEliminar(true);
			setDesactivarTabDetalle(true);
		}
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("seleccionado") Efs cartera) {
		log.info("onSeleccionar==> " + cartera.getSecuencia());
		setEfSeleccionado(cartera);
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
		EfSeleccionado = new Efs();

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
	public void listarEfs() {
		log.info(" listarEfs ");
		setDesactivarformulario(true);
		try {
			con = new Conexion();
			setListaEfs((List<Efs>) Conexion.getConexion().obtenerListado("listarEfs", null));

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Command
	public void onMostrarVentanaConsulta() {
		log.info("onMostrarVentanaConsulta");

		try {
			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("OBJETO", EfSeleccionado);
			if (idCARTERAZTpnConsultaEfs.getChildren().size() == 0) {
				Utilidades.onCargarVentana(idCARTERAZTpnConsultaEfs, "//formas//vista_indicador.zul", parametros);
			} else {
				// VistaEfsViewModel detalleEfs = new VistaEfsViewModel();
				// detalleEfs.listarEfs();
				// log.info("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Efs> getListaEfs() {
		return listaEfs;
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

	public Efs getEfSeleccionado() {
		return EfSeleccionado;
	}

	public void setEfSeleccionado(Efs efSeleccionado) {
		EfSeleccionado = efSeleccionado;
	}

	public void setListaEfs(List<Efs> listaEfs) {
		this.listaEfs = listaEfs;
	}

}