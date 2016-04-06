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
import org.zkoss.zul.Textbox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Proveedor;
import com.siis.dto.DetalleProveedor;

public class VistaProveedorViewModel {
	public List<DetalleProveedor> listaDetalleProveedor;
	public List<Proveedor> listaProveedor;
	public Proveedor proveedorSeleccionado;
	public DetalleProveedor detalleSeleccionado;
	private Double totalCartera;
	private Double totalCarteraVencida;
	private Double totalCarteraPorVencer;

	@Wire
	public Textbox CriterioBusqProveedor;
	@Wire
	Tabpanel tabPanelConsultas;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		listaDetalleProveedor = new ArrayList<DetalleProveedor>();
		listaProveedor = new ArrayList<Proveedor>();

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarDetalleProveedor() {
		System.out.println(" listarDetalleProveedor ");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SEC_PROVEEDOR", proveedorSeleccionado.getSecuencia());

		try {

			setListaDetalleProveedor((List<DetalleProveedor>) Conexion.getConexion()
					.obtenerListado("listarDetalleProveedorsPorProveedor", parametros));
			setTotalCartera((Double) Conexion.getConexion().obtenerRegistro("obtenerTotalProveedor",
					proveedorSeleccionado.getSecuencia()));
			setTotalCarteraVencida((Double) Conexion.getConexion().obtenerRegistro("obtenerTotalProveedorVencida",
					proveedorSeleccionado.getSecuencia()));
			setTotalCarteraPorVencer((Double) Conexion.getConexion().obtenerRegistro("obtenerTotalProveedorPorVencer",
					proveedorSeleccionado.getSecuencia()));

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarProveedor() {

		try {
			setListaProveedor(
					(List<Proveedor>) Conexion.getConexion().obtenerListado("listarProveedores", new Proveedor()));

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public List<DetalleProveedor> getListaDetalleProveedor() {
		return listaDetalleProveedor;
	}

	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}

	public void setListaProveedor(List<Proveedor> listaProveedor) {
		System.out.println(" listaProveedor.size() " + listaProveedor.size());
		this.listaProveedor = listaProveedor;
	}

	public void setListaDetalleProveedor(List<DetalleProveedor> listaDetalleProveedor) {
		this.listaDetalleProveedor = listaDetalleProveedor;
	}

	public DetalleProveedor getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DetalleProveedor detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public Proveedor getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
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

}
