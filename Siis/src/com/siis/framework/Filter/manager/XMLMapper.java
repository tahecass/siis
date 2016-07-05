/**
 * 
 */
package com.casewaresa.framework.Filter.manager;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.casewaresa.framework.helper.ContextoAplicacion;

/**
 * Class that manages the creation of queries to send statements to reports
 * 
 * @author caswaredes03
 * @name GenerarQueryReportesHelper.java
 * @date 15/12/2010
 */

public class XMLMapper {

	protected static Logger log = Logger.getLogger(XMLMapper.class);
	private static XMLMapper xmlMapper;
	private static Document document;

	/**
	 * Private Constructor
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 */
	private XMLMapper() throws JDOMException, IOException {
		log.info("Ejecutando metodo [ XMLMapper ]");
		this.loadXmlMapper();

	}

	/**
	 * @type Método de la clase GenerarQueryReportesHelper.java
	 * @name getInstance
	 * @return instance of XMLMapper
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static XMLMapper getInstance() throws Exception {
		if (xmlMapper == null)
			xmlMapper = new XMLMapper();

		return xmlMapper;
	}

	/**
	 * 
	 * @type Método de la clase GenerarQueryReportesHelper.java
	 * @name loadXmlMapper
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void loadXmlMapper() throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		document = saxBuilder.build(new File(
				ContextoAplicacion.getInstance().getRutaContexto()
						+ File.separator + "WEB-INF" + File.separator
						+ "config_report", "MapperReport.xml"));

	}


	/**
	 * @type Método de la clase GenerarQueryReportesHelper.java
	 * @name getReportWithColums
	 * @param id
	 * @return Lista de Columnas de el mapper filtrado
	 */
//	@SuppressWarnings("unchecked")
//	public List<ColumnsMapper> getListColumnsById(String id) {
//		log.info("Ejecutando metodo [ getListColumnsById ]");
//
//		List<ColumnsMapper> columnsMappers = new LinkedList<ColumnsMapper>();
//		Element elementMapper = document.getRootElement().getChild(id);
//
//		for (Element elementColumn : (List<Element>) elementMapper
//				.getChildren()) {
//			columnsMappers.add(new ColumnsMapper(elementColumn.getAttribute(
//					"name").getValue(), elementColumn.getAttribute("label")
//					.getValue(), elementColumn.getAttribute("type").getValue(),
//					elementColumn.getAttribute("property").getValue()));
//
//		}
//
//		return columnsMappers;
//	}



	/**
	 * @type Método de la clase XMLMapper.java
	 * @name getListIdMapperReport
	 * @return
	 * @desc retorna una lista con todos los id's que representan las
	 *       estructuras hijas del xml mapperReport, que contienen las columnas
	 *       de las vistas.
	 */
//	@SuppressWarnings("unchecked")
//	public Map<String, MapperReport> getListChildrenMapperReport() {
//		log.info("Ejecutando metodo [ getListIdMapperReport ]");
//
//		Map<String, MapperReport> childrenMapper = new HashMap<String, MapperReport>();
//		List<Element> elementMapper = document.getRootElement().getChildren();
//
//		for (Element element : elementMapper) {
//			childrenMapper.put(element.getName(), new MapperReport(element
//					.getAttributeValue("label"), element.getName(), element
//					.getAttributeValue("view"), element
//					.getAttributeValue("viewl")));
//
//		}
//
//		return childrenMapper;
//	}

	/**
	 * @type Método de la clase GenerarQueryReportesHelper.java
	 * @name getMapperById
	 * @param id
	 * @return
	 * @desc
	 */
	public String getNameViewById(String id) {
		log.info("Ejecutando metodo getNameViewById ");

		return document.getRootElement().getChild(id).getAttribute("view")
				.getValue();
	}
}
