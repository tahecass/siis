package com.siis.viewModel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Indicador;


public class VistaIndicadorViewModel {
	public Conexion con;
	public List<Indicador> listaDetalleIndicador;
	public Indicador detalleSeleccionado;

	@Wire
	Tabpanel tabPanelConsultas;
	@Wire
	Textbox TextboxParamIndicador;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		listaDetalleIndicador = new ArrayList<Indicador>();

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarIndicador() {

		try {
			Indicador ind = new Indicador();
			if (!TextboxParamIndicador.getValue().isEmpty())
				ind.setNombre("%" + TextboxParamIndicador.getValue() + "%");

			con = new Conexion();
			setListaDetalleIndicador((List<Indicador>) con.obtenerListado("listarIndicadores", ind));

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public List<Indicador> getListaDetalleIndicador() {
		return listaDetalleIndicador;
	}

	public void setListaDetalleIndicador(List<Indicador> listaDetalleIndicador) {
		this.listaDetalleIndicador = listaDetalleIndicador;
	}

	public Indicador getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(Indicador detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

}
