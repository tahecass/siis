package com.siis.viewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Tabpanel;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cliente;
import com.siis.dto.Disponible;
import com.siis.dto.Disponible;
import com.siis.viewModel.framework.BandboxBancos;

public class VistaDisponibleViewModel {
	public Conexion con;
	public List<Disponible> listaDisponible;
	public List<Cliente> listaCliente;
	@Wire
	public BandboxBancos idVISTADISPONIBLEZBbxBanco;
	public Disponible detalleSeleccionado;
	private Double totalCartera;
	private Double totalCarteraVencida;
	private Double totalCarteraPorVencer;

	@Wire
	public Combobox bandboxSeekerClientes;
	@Wire
	Tabpanel tabPanelConsultas;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		listaDisponible = new ArrayList<Disponible>();
		listaCliente = new ArrayList<Cliente>();

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDetalleDisponible() {
	Log.info(" listarDetalleDisponible ");

	 Disponible dispo = new Disponible();
	 dispo.setBanco(idVISTADISPONIBLEZBbxBanco.getValue());

		try {
			con = new Conexion();
			setListaDisponible(
					(List<Disponible>) con.obtenerListado("listarDisponibles", dispo )); 
			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@NotifyChange("*")
	@Command
	public void listarClientes() {
		System.out.println(" listarClientes ");
		Map<String, Object> parametros = new HashMap<String, Object>();
		con = new Conexion();
		try {
			setListaCliente((List<Cliente>) con.obtenerListado("listarClientes", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	 

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		System.out.println(" listaCliente.size() " + listaCliente.size());
		this.listaCliente = listaCliente;
	}

	 

	public Disponible getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(Disponible detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	 

	public Double getTotalCartera() {
		return totalCartera;
	}

	public void setTotalCartera(Double totalCartera) {
		this.totalCartera = totalCartera;
	}

	public Double getTotalCarteraVencida() {
		return totalCarteraVencida;
	}

	public void setTotalCarteraVencida(Double totalCarteraVencida) {
		this.totalCarteraVencida = totalCarteraVencida;
	}

	public Double getTotalCarteraPorVencer() {
		return totalCarteraPorVencer;
	}

	public void setTotalCarteraPorVencer(Double totalCarteraPorVencer) {
		this.totalCarteraPorVencer = totalCarteraPorVencer;
	}

	public List<Disponible> getListaDisponible() {
		return listaDisponible;
	}

	public void setListaDisponible(List<Disponible> listaDisponible) {
		this.listaDisponible = listaDisponible;
	}

}
