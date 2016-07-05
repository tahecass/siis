package com.casewaresa.framework.dto;

import java.util.List;
import java.util.Map;

import org.jdom.Element;

public class DTOParserXml {
	
	private String nombre;
	private Map<String,Object> atributos;
	private Element element;
	private List<DTOParserXml> elementChildrens;
	
	public DTOParserXml() {
		
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the atributos
	 */
	public Map<String, Object> getAtributos() {
		return atributos;
	}
	/**
	 * @param atributos the atributos to set
	 */
	public void setAtributos(Map<String, Object> atributos) {
		this.atributos = atributos;
	}
	/**
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}
	/**
	 * @param element the element to set
	 */
	public void setElement(Element element) {
		this.element = element;
	}
	/**
	 * @return the elementChildrens
	 */
	public List<DTOParserXml> getElementChildrens() {
		return elementChildrens;
	}
	/**
	 * @param elementChildrens the elementChildrens to set
	 */
	public void setElementChildrens(List<DTOParserXml> elementChildrens) {
		this.elementChildrens = elementChildrens;
	}
	
	
	
}
