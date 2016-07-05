package com.casewaresa.framework.helper;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.casewaresa.framework.util.LegacyJasperInputStream;

/**
 * Clase que contiene los metodos para cargar archivos de reportes desde: Ruta,
 * Archivo, XML, Cadena
 * 
 * @author jmora
 * 
 */
public class CargarReporteJasperHelper {

    /**
     * Transforma un archivo {@link FileInputStream} con la configuracion JRXML
     * a un {@link JasperDesign}
     * 
     * @param reporte
     *            {@link FileInputStream}
     * @return {@link JasperDesign}
     * @throws JRException
     *             cualquier excepcion al momento de transformar el el JRXML a
     *             {@link JasperDesign}
     */
    public static JasperDesign cargaFuenteReporte(FileInputStream reporte)
	    throws JRException {
	JasperDesign design = null;

	// --cargamos el reporte de la ruta dada
	design = JRXmlLoader.load(new LegacyJasperInputStream(reporte));

	return design;
    }

    /**
     * Transforma un {@link String} con contenido en el formato .jrxml a un
     * {@link JasperDesign}
     * 
     * @param xml
     *            {@link String} con contenido en el formato .jrxml
     * @return {@link JasperDesign}
     * @throws JRException
     */
    public static JasperDesign cargaFuenteReporte(String xml)
	    throws JRException {
	JasperDesign design = null;
	/*
	 * Atributos que ponen problema en las versiones superiores del JRXM
	 * 4.5.X
	 */	
	design = JRXmlLoader.load(new LegacyJasperInputStream(
		new ByteArrayInputStream(xml.replaceAll("uuid=\"[^\"]*\"", "")
			.replaceAll("splitType=\"[^\"]*\"", "").getBytes())));
	return design;
    }

}
