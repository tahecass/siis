package com.casewaresa.framework.dto;

import java.util.List;

import com.casewaresa.framework.dto.impl.LlaveNatural;
import com.casewaresa.iceberg_aa.dto.AATEjecutable;

/**
 * @author: Wilmar Calderón Torres
 * @date  : 31/03/2012
 * @name  : Autenticacion.java
 * @desc  : Clase utilizada para el proceso de autenticación de ICEBERG
 */
public class Autenticacion2 extends BeanAbstracto {

	private static final long serialVersionUID = -1129629019029144623L;

	private String cuentaIceberg = null;
	private String clave = null;
	private MaquinaCliente maquinaCliente = null;
	private AATEjecutable ejecutable = null;
	private Sesion sesion = null;
	
	public Autenticacion2() {
	
	}

	public Autenticacion2(String cuentaIceberg, String clave,
			MaquinaCliente maquinaCliente, AATEjecutable ejecutable) {
		super();
		this.cuentaIceberg = cuentaIceberg;
		this.clave = clave;
		this.maquinaCliente = maquinaCliente;
		this.ejecutable = ejecutable;
		this.sesion = null;
	}

	public String getCuentaIceberg() {
		return cuentaIceberg;
	}

	public void setCuentaIceberg(String cuentaIceberg) {
		this.cuentaIceberg = cuentaIceberg;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public MaquinaCliente getMaquinaCliente() {
		return maquinaCliente;
	}

	public void setMaquinaCliente(MaquinaCliente maquinaCliente) {
		this.maquinaCliente = maquinaCliente;
	}

	public AATEjecutable getEjecutable() {
		return ejecutable;
	}

	public void setEjecutable(AATEjecutable ejecutable) {
		this.ejecutable = ejecutable;
	}

	public Sesion getSesion() {
		return sesion;
	}

	@Override
	public List<LlaveNatural> getLlaveNatural() {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
