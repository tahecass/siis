package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cartera;
import com.siis.dto.DetalleCartera;
import com.siis.dto.Usuario;
import com.siis.viewModel.framework.BandboxCliente;
import com.siis.viewModel.framework.Utilidades;

public class FormularioCarteraViewModel {
	public Conexion con;
	public List<DetalleCartera> listaDetalleCartera;
	public DetalleCartera detalleSeleccionado;
	public Cartera carteraSeleccionada;

	@Wire
	private Datebox idFORMCARTERAZDbxFechaHoraAct, idFORMCARTERAZDbxFechaPago;
	@Wire
	private BandboxCliente idFORMCARTERAZBbxCliente;
	@Wire
	public Borderlayout idWINFORMCARTERAZPrincipal;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		con = new Conexion();
		listaDetalleCartera = new ArrayList<DetalleCartera>();
		idFORMCARTERAZDbxFechaHoraAct.setValue(new Date());

	}

	@NotifyChange("listaDetalleCartera")
	@Command
	public void onAgregar() {
		System.out.println(" onAgregar ==> ");

		// ListModelList<DetalleCartera> lm = new ListModelList<DetalleCartera>(
		// Arrays.asList(new DetalleCartera(new Cartera(), "", 0.0, new Date(),
		// "")));
		//
		// listaDetalleCartera.addAll(lm);

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("CARTERA", carteraSeleccionada);
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

	@Command
	public void guardarCartera() {
		try {
			System.out.println("guardar Cartera");
			Cartera cartera = new Cartera();
			con = new Conexion();
			cartera.setCliente(idFORMCARTERAZBbxCliente.getValue());
			cartera.setUsuario(new Usuario(new Integer(1)));
			cartera.setFechaHoraActualizacion(idFORMCARTERAZDbxFechaHoraAct.getValue());
			cartera.setFechaPago(idFORMCARTERAZDbxFechaPago.getValue());
			System.out.println("CARTERA==> " + cartera.getCliente().getSecuencia());
			con.guardar("guardarCartera", cartera);
			System.out.println("CARTERA==> " + cartera.getSecuencia());

			seleccionarCartera(obtenerCartera(cartera));

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void seleccionarCartera(Cartera cartera) {
		System.out.println("seleccionarCartera");
		if (cartera != null) {
			if (cartera.getFechaHoraActualizacion() != null)
				idFORMCARTERAZDbxFechaHoraAct.setValue(cartera.getFechaHoraActualizacion());
			if (cartera.getFechaPago() != null)
				idFORMCARTERAZDbxFechaPago.setValue(cartera.getFechaPago());
			if (cartera.getCliente() != null)
				idFORMCARTERAZBbxCliente.setValue(cartera.getCliente());
		}
	}

	@SuppressWarnings("unchecked")
	private Cartera obtenerCartera(Cartera cartera) {
		System.out.println("obtenerCartera");
		con = new Conexion();
		Cartera dtoCartera = null;
		try {
			dtoCartera = (Cartera) ((List<Cartera>) con.obtenerListado("obtenerCartera", cartera)).get(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoCartera;
	}

	public List<DetalleCartera> getListaDetalleCartera() {
		return listaDetalleCartera;
	}

	// @Command
	// public void onMostrarVentanaConsulta() {
	// System.out.println("onMostrarVentanaConsulta");
	//
	// try {
	// Map<String, Object> parametros = new HashMap<String, Object>();
	// parametros.put("OBJETO", "");
	//
	// onCargarVentana(tabPanelConsultas, "//formas//vista_cartera.zul",
	// parametros);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public void setListaDetalleCartera(List<DetalleCartera> listaDetalleCartera) {
		this.listaDetalleCartera = listaDetalleCartera;
	}

	public DetalleCartera getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DetalleCartera detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

}
