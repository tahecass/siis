package com.casewaresa.framework.dto;

import java.util.ArrayList;
import java.util.List;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.dto.impl.LlaveNatural;
import com.casewaresa.iceberg_aa.dto.AATCuentaIceberg;

/**
 * @author: Wilmar Calder�n Torres
 * @date  : 31/03/2012
 * @name  : Autenticacion.java
 * @desc  : Clase utilizada para el proceso de autenticaci�n de ICEBERG
 */
public class Autenticacion extends BeanAbstracto {

	private static final long serialVersionUID = -1129629019029144623L;
	/** Propiedad usuario,contrasena de la clase [ Autenticacion.java ] 
	 *  @desc: Especifican los datos de un usuario */
	private AATCuentaIceberg cuentaIceberg ;
	private String tipoAutenticacion;
	private Integer autenticado  = IConstantes.USUARIO_NO_AUTENTICADO;
	

	public Autenticacion() {
	
	}	
	
	/**
	 * @type   Constructor de la clase Autenticacion
	 * @name   Autenticacion
	 * @param usuario
	 * @param contrasena
	 * @desc   Crea una instancia de esta clase
	 */	
	public Autenticacion(String tipoAutenticacion, AATCuentaIceberg cuentaIceberg) {
		super();
		this.tipoAutenticacion = tipoAutenticacion;
		this.cuentaIceberg = cuentaIceberg;
		autenticado  = IConstantes.USUARIO_NO_AUTENTICADO;
	}

	/**
	 * @type   Método de la clase Autenticacion
	 * @name   getAutenticado
	 * @return void
	 * @param autenticado
	 * @desc   obtiene el valor de la propiedad autenticado
	 */
	public Integer getAutenticado() {
		return autenticado;
	}

	/**
	 * @type   Método de la clase Autenticacion
	 * @name   setAutenticado
	 * @return Integer
	 * @param autenticado
	 * @desc   Actualiza el valor de la propiedad autenticado
	 */
	public void setAutenticado(Integer autenticado) {
		this.autenticado = autenticado;
	}

	public List<LlaveNatural> getLlaveNatural() {
	    List<LlaveNatural> list = new ArrayList<LlaveNatural>();
	    list.add(new LlaveNatural("Cuenta Iceberg", cuentaIceberg.getCuentaIceberg()));
	    return list;
	}

	public void setCuentaIceberg(AATCuentaIceberg cuentaIceberg) {
		this.cuentaIceberg = cuentaIceberg;
	}

	public AATCuentaIceberg getCuentaIceberg() {
		return cuentaIceberg;
	}

	public void setTipoAutenticacion(String tipoAutenticacion) {
		this.tipoAutenticacion = tipoAutenticacion;
	}

	public String getTipoAutenticacion() {
		return tipoAutenticacion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return this.getCuentaIceberg().toString();
	}

}
