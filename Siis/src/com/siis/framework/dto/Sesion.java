package com.casewaresa.framework.dto;

import java.util.List;

import com.casewaresa.framework.dto.impl.LlaveNatural;
import com.casewaresa.iceberg_aa.dto.AATCuentaIceberg;
import com.casewaresa.iceberg_aa.dto.AATEjecutable;
import com.casewaresa.iceberg_aa.dto.AATGrupoRolIceberg;

/**
 * @author: Wilmar Calderón Torres
 * @date  : 31/03/2012
 * @name  : Autenticacion.java
 * @desc  : Clase utilizada para el manejo de Sesiones de ICEBERG
 */
public class Sesion extends BeanAbstracto {

	private static final long serialVersionUID = -1129629019029144623L;
	
    private Long idSesion;
    private AATCuentaIceberg cuenta;
    private AATGrupoRolIceberg grupoRolIceberg;
    private AATEjecutable ejecutable;
    private Integer nivelLog;
	
	public Sesion() {
	
	}
	
	public Sesion(Long idSesion, AATCuentaIceberg cuenta,
			AATGrupoRolIceberg grupoRolIceberg, AATEjecutable ejecutable,
			Integer nivelLog) {
		super();
		this.idSesion = idSesion;
		this.cuenta = cuenta;
		this.grupoRolIceberg = grupoRolIceberg;
		this.ejecutable = ejecutable;
		this.nivelLog = nivelLog;
	}

	public Long getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(Long idSesion) {
		this.idSesion = idSesion;
	}

	public AATCuentaIceberg getCuenta() {
		return cuenta;
	}

	public void setCuenta(AATCuentaIceberg cuenta) {
		this.cuenta = cuenta;
	}

	public AATGrupoRolIceberg getGrupoRolIceberg() {
		return grupoRolIceberg;
	}

	public void setGrupoRolIceberg(AATGrupoRolIceberg grupoRolIceberg) {
		this.grupoRolIceberg = grupoRolIceberg;
	}

	public AATEjecutable getEjecutable() {
		return ejecutable;
	}

	public void setEjecutable(AATEjecutable ejecutable) {
		this.ejecutable = ejecutable;
	}

	public Integer getNivelLog() {
		return nivelLog;
	}

	public void setNivelLog(Integer nivelLog) {
		this.nivelLog = nivelLog;
	}

	@Override
	public List<LlaveNatural> getLlaveNatural() {
		// TODO Auto-generated method stub
		return null;
	}	

}
