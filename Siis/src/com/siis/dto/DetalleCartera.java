package com.siis.dto;

import java.util.Date;

public class DetalleCartera {
	private Integer secuencia;
	private Cartera cartera;
	private String nroFactura;
	private Double valor1;
	private Double valor2;
	private Double valor3;
	private Double valor4;
	private Date vencimiento;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private String referencia;
	
	private Double totalCartera;
	private Double totalCarteraVencida;
	private Double totalCarteraPorVencer;

	public DetalleCartera() {
	}

	public DetalleCartera(Cartera cartera, String nroFactura, Double valor1, Double valor2, Double valor3,
			Double valor4, Date vencimiento, String referencia) {
		super();
		this.cartera = cartera;
		this.nroFactura = nroFactura;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.valor3 = valor3;
		this.valor4 = valor4;
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

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public Double getValor1() {
		return valor1;
	}

	public void setValor1(Double valor1) {
		this.valor1 = valor1;
	}

	public Double getValor2() {
		return valor2;
	}

	public void setValor2(Double valor2) {
		this.valor2 = valor2;
	}

	public Double getValor3() {
		return valor3;
	}

	public void setValor3(Double valor3) {
		this.valor3 = valor3;
	}

	public Double getValor4() {
		return valor4;
	}

	public void setValor4(Double valor4) {
		this.valor4 = valor4;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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