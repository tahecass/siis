package com.siis.dto;

import java.util.Date;

public class DetalleProveedor {
	private Integer secuencia;
	private Proveedor proveedor;
	private String nroFactura;
	private Double valor1;
	private Double valor2;
	private Double valor3;
	private Double valor4;
	private Date vencimiento;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private String referencia;
	
	private Double totalProveedor;
	private Double totalProveedorVencida;
	private Double totalProveedorPorVencer;

	public DetalleProveedor() {
	}

	public DetalleProveedor(Proveedor proveedor, String nroFactura, Double valor1, Double valor2, Double valor3,
			Double valor4, Date vencimiento, String referencia) {
		super();
		this.proveedor = proveedor;
		this.nroFactura = nroFactura;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.valor3 = valor3;
		this.valor4 = valor4;
		this.vencimiento = vencimiento;
		this.referencia = referencia;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
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

	public Double getTotalProveedor() {
		return totalProveedor;
	}

	public void setTotalProveedor(Double totalProveedor) {
		this.totalProveedor = totalProveedor;
	}

	public Double getTotalProveedorVencida() {
		return totalProveedorVencida;
	}

	public void setTotalProveedorVencida(Double totalProveedorVencida) {
		this.totalProveedorVencida = totalProveedorVencida;
	}

	public Double getTotalProveedorPorVencer() {
		return totalProveedorPorVencer;
	}

	public void setTotalProveedorPorVencer(Double totalProveedorPorVencer) {
		this.totalProveedorPorVencer = totalProveedorPorVencer;
	}

}