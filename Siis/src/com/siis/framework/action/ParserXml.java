package com.siis.framework.action;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.casewaresa.framework.dto.DTOParserXml;

public class ParserXml {

	private DTOParserXml element;
	private List<DTOParserXml> elements;
	private static ParserXml parserXml;

	private ParserXml() {	

	}

	public static ParserXml getInstance() {
		if (parserXml == null)
			parserXml = new ParserXml();

		return parserXml;
	}

	/**
	 * *
	 * 
	 * @param xml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * @desc metodo que convierte un String a un element JDOM
	 */
	public Element stringToXML(String xml) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder(false);
		StringReader is = new StringReader(xml);
		return builder.build(is).getRootElement();
	}

	/***
	 * @param elemento
	 * @return
	 * @desc metodo que convierte un objeto Document en un string
	 */
	public String xmlToString(Document elemento) {
		XMLOutputter output = new XMLOutputter(new XMLOutputter().getFormat()
				.setEncoding("UTF-8"));
		String pintar = output.outputString(elemento);
		return pintar;
	}

	/**
	 * @param elemento
	 * @return
	 * @desc metodo que convierte un objeto element en un string
	 */
	public String xmlToString(Element elemento) {
		XMLOutputter output = new XMLOutputter(new XMLOutputter().getFormat()
				.setEncoding("UTF-8"));
		String pintar = output.outputString(elemento);
		return pintar;
	}

	@SuppressWarnings("unchecked")
	public Element onSearchXMLNodes(Element xml, String buscar,
			List<Element> listaDato, int index) throws JDOMException,
			IOException {
		if (index < 0) {
			return null;
		}

		if (listaDato != null) {
			xml = listaDato.get(index);

		}
		if (xml.getAttribute(buscar) != null) {
			DTOParserXml dtoParserXml = new DTOParserXml();
			dtoParserXml.setElement(xml);
			dtoParserXml.setNombre(xml.getName());
			Map<String, Object> mapAtributos = new HashMap<String, Object>();
			for (Attribute atributo : (List<Attribute>) xml.getAttributes()) {
				mapAtributos.put(atributo.getName(), atributo.getValue());
			}
			dtoParserXml.setAtributos(mapAtributos);
			List<DTOParserXml> listaDTOParserXml = new LinkedList<DTOParserXml>();
			List<Element> elementChildreList = xml.getChildren();
			for (Element element : elementChildreList) {
				DTOParserXml objeto = new DTOParserXml();
				objeto.setElement(element);
				objeto.setNombre(element.getName());
				Map<String, Object> mapAtributosHijo = new HashMap<String, Object>();
				for (Attribute atributo : (List<Attribute>) xml.getAttributes()) {
					mapAtributosHijo.put(atributo.getName(),
							atributo.getValue());
				}
				objeto.setAtributos(mapAtributosHijo);
				listaDTOParserXml.add(objeto);
			}
			dtoParserXml.setElementChildrens(listaDTOParserXml);
			elements.add(dtoParserXml);

		}

		if (xml.getChildren().size() != 0) {
			List<Element> listaDatos = (List<Element>) xml.getChildren();
			Element xmlSearch = this.onSearchXMLNodes(xml, buscar, listaDatos,
					listaDatos.size() - 1);
			if (xmlSearch != null)
				return xmlSearch;
		}

		return this.onSearchXMLNodes(xml, buscar, listaDato, index - 1);
	}

	@SuppressWarnings("unchecked")
	public Element onSearchXMLNode(Element xml, String buscar,
			List<Element> listaDato, int index) throws JDOMException,
			IOException {
		if (index < 0) {
			return null;
		}

		if (listaDato != null) {
			xml = listaDato.get(index);

		}
		if (xml.getAttribute(buscar) != null) {
			element = new DTOParserXml();
			element.setElement(xml);
			element.setNombre(xml.getName());
			Map<String, Object> mapAtributos = new HashMap<String, Object>();
			for (Attribute atributo : (List<Attribute>) xml.getAttributes()) {
				mapAtributos.put(atributo.getName(), atributo.getValue());
			}
			element.setAtributos(mapAtributos);
			List<DTOParserXml> listaDTOParserXml = new LinkedList<DTOParserXml>();
			List<Element> elementChildreList = xml.getChildren();
			for (Element element : elementChildreList) {
				DTOParserXml objeto = new DTOParserXml();
				objeto.setElement(element);
				objeto.setNombre(element.getName());
				Map<String, Object> mapAtributosHijo = new HashMap<String, Object>();
				for (Attribute atributo : (List<Attribute>) xml.getAttributes()) {
					mapAtributosHijo.put(atributo.getName(),
							atributo.getValue());
				}
				objeto.setAtributos(mapAtributosHijo);
				listaDTOParserXml.add(objeto);
			}
			element.setElementChildrens(listaDTOParserXml);

		}

		if (xml.getChildren().size() != 0) {
			List<Element> listaDatos = (List<Element>) xml.getChildren();
			Element xmlSearch = this.onSearchXMLNode(xml, buscar, listaDatos,
					listaDatos.size() - 1);
			if (xmlSearch != null)
				return xmlSearch;
		}

		return this.onSearchXMLNode(xml, buscar, listaDato, index - 1);
	}

	public List<DTOParserXml> getElements(Element root,
			String nombreElementBuscar) throws JDOMException, IOException {
		if (root != null) {
			elements = new LinkedList<DTOParserXml>();
			this.onSearchXMLNodes(root, nombreElementBuscar, null, 0);
		}

		return elements;
	}

	public List<DTOParserXml> getElements(String contenido,
			String nombreElementBuscar) throws JDOMException, IOException {
		if (contenido != null && !contenido.equals("")) {
			elements = new LinkedList<DTOParserXml>();
			this.onSearchXMLNodes(this.stringToXML(contenido),
					nombreElementBuscar, null, 0);
		}
		return elements;
	}

	public DTOParserXml getElement(Element root,
			String nombreElementBuscar) throws JDOMException, IOException {
		if (root != null) {
			this.onSearchXMLNode(root, nombreElementBuscar, null, 0);
		}

		return element;
	}

	public DTOParserXml getElement(String contenido,
			String nombreElementBuscar) throws JDOMException, IOException {
		if (contenido != null && !contenido.equals("")) {
			this.onSearchXMLNode(this.stringToXML(contenido),
					nombreElementBuscar, null, 0);
		}
		return element;
	}

}
