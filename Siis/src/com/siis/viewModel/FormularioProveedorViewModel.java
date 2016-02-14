package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tabpanel;

import com.siis.configuracion.Conexion;
import com.siis.dto.Bean;
import com.siis.dto.Cartera;
import com.siis.dto.DetalleCartera;

public class FormularioProveedorViewModel {
	public Conexion con;
	public List<DetalleCartera> listaDetalleCartera;
	public DetalleCartera detalleSeleccionado;
	List<Bean> listado;
	public Bean objetoSeleccionado;

	@Wire
	public Combobox bandboxSeekerProveedor;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		con = new Conexion();
		listaDetalleCartera = new ArrayList<DetalleCartera>();
		listado = new ArrayList<Bean>();
		this.parametrizarBandboxSeeker();

	}

	@NotifyChange("listaDetalleCartera")
	@Command
	public void onAgregar() {
		System.out.println(" onAgregar ==> ");

		ListModelList<DetalleCartera> lm = new ListModelList<DetalleCartera>(
				Arrays.asList(new DetalleCartera(new Cartera(), "", 0.0, new Date(), "")));

		listaDetalleCartera.addAll(lm);
	}

	public void parametrizarBandboxSeeker() {
		System.out.println("[ parametrizarBandboxSeeker] ");
		// bandboxSeekerClientes.setObjeto(new Cliente());
		// bandboxSeekerClientes.setConsulta("listaClientes");

		bandboxSeekerProveedor.setDisabled(true);

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

	public static void onCargarVentana(Tabpanel contenedor, String rutaForma, Map<String, Object> arg)
			throws Exception {
		System.out.println("onCargarVentana " + contenedor.getId());
		Executions.createComponents(rutaForma, contenedor, arg);
	}

	public void setListaDetalleCartera(List<DetalleCartera> listaDetalleCartera) {
		this.listaDetalleCartera = listaDetalleCartera;
	}

	public DetalleCartera getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DetalleCartera detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public List<Bean> getListado() {
		return listado;
	}

	public void setListado(List<Bean> listado) {
		this.listado = listado;
	}

	public Bean getObjetoSeleccionado() {
		return objetoSeleccionado;
	}

	public void setObjetoSeleccionado(Bean objetoSeleccionado) {
		this.objetoSeleccionado = objetoSeleccionado;
	}

}
