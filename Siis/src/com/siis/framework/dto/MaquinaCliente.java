package com.casewaresa.framework.dto;

import java.util.ArrayList;
import java.util.List;

import com.casewaresa.framework.dto.impl.LlaveNatural;

/**
 * @author: Wilmar Calder�n Torres
 * @date  : 31/03/2012
 * @name  : Autenticacion.java
 * @desc  : Clase utilizada para el proceso de autenticaci�n de ICEBERG
 */
public class MaquinaCliente extends BeanAbstracto {

	private static final long serialVersionUID = -1129629019029144623L;

	private String ipAdress  = null;
	private String hostName  = null;
	private String macAdress = null;
	
	public MaquinaCliente() {
	
	}

	public MaquinaCliente(String ipAdress, String hostName, String macAdress) {
		super();
		this.ipAdress = ipAdress;
		this.hostName = hostName;
		this.macAdress = macAdress;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getMacAdress() {
		return macAdress;
	}

	public void setMacAdress(String macAdress) {
		this.macAdress = macAdress;
	}

	@Override
	public List<LlaveNatural> getLlaveNatural() {
	    List<LlaveNatural> llaves = new ArrayList<LlaveNatural>();
	    llaves.add(new LlaveNatural("ipAdress",  this.ipAdress));
	    llaves.add(new LlaveNatural("hostName",  this.hostName));
	    llaves.add(new LlaveNatural("macAdress", this.macAdress));
	
	    return llaves;
	}	
	
}
