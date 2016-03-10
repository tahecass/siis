package com.siis.viewModel;

import java.util.ArrayList;
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
import org.zkoss.zul.Tabpanel;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cliente;
import com.siis.dto.DetalleCartera; 

public class VistaProveedorViewModel {
	public Conexion con;
	public List<DetalleCartera> listaDetalleCartera;
	public List<Cliente> listaCliente;
	public Cliente clienteSeleccionado;
	public DetalleCartera detalleSeleccionado;

	 
	@Wire
	Tabpanel tabPanelConsultas;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		listaDetalleCartera = new ArrayList<DetalleCartera>();
		listaCliente = new ArrayList<Cliente>();

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDetalleCartera() {
		System.out.println(" listarDetalleCartera ");
		 

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SECUENCIA_CLIENTE", clienteSeleccionado.getSecuencia());

		try {
			con = new Conexion();
			setListaDetalleCartera(
					(List<DetalleCartera>) con.obtenerListado("listarDetalleCarterasPorClientes", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@SuppressWarnings("unchecked")
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

	public List<DetalleCartera> getListaDetalleCartera() {
		return listaDetalleCartera;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		System.out.println(" listaCliente.size() " + listaCliente.size());
		this.listaCliente = listaCliente;
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

	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(Cliente clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

}
