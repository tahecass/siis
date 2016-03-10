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

import com.siis.configuracion.Conexion;
import com.siis.dto.AmortizacionCredito;
import com.siis.dto.Banco;
import com.siis.dto.Credito;
import com.siis.viewModel.framework.BandboxBancos;

public class VistaCreditoViewModel {
	public Conexion con;
	public List<AmortizacionCredito> listaDetalleAmortizacionCredito;
	public List<Banco> listaEntidad;
	public Banco entidadSeleccionado;
	public AmortizacionCredito detalleSeleccionado;
	private Double totalCredito;
	private Double totalCreditoVencida;
	private Double totalCreditoPorVencer;

	@Wire
	public BandboxBancos  idVISTACREDITOZBbxEntidad;
	@Wire
	Tabpanel tabPanelConsultas;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		listaDetalleAmortizacionCredito = new ArrayList<AmortizacionCredito>();
		listaEntidad = new ArrayList<Banco>();

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarAmortizacionCredito() {
		System.out.println(" listarAmortizacionCredito "+idVISTACREDITOZBbxEntidad.getValue().getSec());

		AmortizacionCredito  amorCred=  new AmortizacionCredito();
		Credito credito = new Credito();
		credito.setEntidad(idVISTACREDITOZBbxEntidad.getValue());
		amorCred.setCredito(credito);

		try {
			con = new Conexion();
			setListaDetalleAmortizacionCredito(
					(List<AmortizacionCredito>) con.obtenerListado("listarAmortizacionCreditos", amorCred));
//			setTotalCredito((Double) con.obtenerRegistro("obtenerTotalCredito", entidadSeleccionado.getSecuencia()));
//			setTotalCreditoVencida(
//					(Double) con.obtenerRegistro("obtenerTotalCreditoVencida", entidadSeleccionado.getSecuencia()));
//			setTotalCreditoPorVencer(
//					(Double) con.obtenerRegistro("obtenerTotalCreditoPorVencer", entidadSeleccionado.getSecuencia()));
//			
//			
			
			

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

 
	public List<AmortizacionCredito> getListaDetalleAmortizacionCredito() {
		return listaDetalleAmortizacionCredito;
	}

	public void setListaDetalleAmortizacionCredito(
			List<AmortizacionCredito> listaDetalleAmortizacionCredito) {
		this.listaDetalleAmortizacionCredito = listaDetalleAmortizacionCredito;
	}

	public List<Banco> getListaEntidad() {
		return listaEntidad;
	}

	public void setListaEntidad(List<Banco> listaEntidad) {
		this.listaEntidad = listaEntidad;
	}

	public Banco getEntidadSeleccionado() {
		return entidadSeleccionado;
	}

	public void setEntidadSeleccionado(Banco entidadSeleccionado) {
		this.entidadSeleccionado = entidadSeleccionado;
	}

	public AmortizacionCredito getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(AmortizacionCredito detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

 
 
 
	 
 

}
