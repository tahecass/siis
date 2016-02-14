package com.siis.dto;

import java.util.Date;

public class DetalleCartera {
	private Integer secuencia;
	private Cartera cartera;
	private String nroFactura;
	private Double valorFactura;
	private Date vencimiento;
	private String referencia;

	public DetalleCartera() { 
	}

	 
	public DetalleCartera(Cartera cartera,String nroFactura,Double valorFactura, Date vencimiento, String referencia) {
		super();
		this.cartera = cartera;
		this.nroFactura = nroFactura;
		this.valorFactura = valorFactura;
		this.vencimiento = vencimiento;
		this.referencia = referencia;
	}


	public Cartera getCartera() {
		return cartera;
	}

	public String getNroFactura() {
		return nroFactura;
	}


	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}


	public void setCartera(Cartera cartera) {
		this.cartera = cartera;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	public Double getValorFactura() {
		return valorFactura;
	}


	public void setValorFactura(Double valorFactura) {
		this.valorFactura = valorFactura;
	}


	public Integer getSecuencia() {
		return secuencia;
	}


	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}
	

}
