/* Copyright (C) 2008 Casper Bang (casper.bang@gmail.com)
 *
 * This library is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 */

package com.casewaresa.framework.util;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This is a decorator around an InputStream, which will convert a modern XSD
 * based Jasper design into the legacy DTD version. This is desirable since
 * modern tools i.e. the iReport plugin for NetBeans 6.5 generates designs based
 * on the modern XSD schema, whereas the Jasper engine itself that we use
 * apparently can not handle XSD but instead require DTD's.
 * 
 * All you have to do to use this converting decorator, is to pipe your Jasper
 * design InputStream through this one, and it will automatically take care of
 * adding a DTD docType and remove attributes which will typically cause a
 * legacy Jasper version to choke.
 * 
 * @author Casper Bang
 */
public class LegacyJasperInputStream extends FilterInputStream {
    protected static Logger log = Logger.getLogger(ConfiguradorIbatis.class);

    /**
     * @param is
     *            The InputStream with the modern XSD based Jasper design
     */
    public LegacyJasperInputStream(final InputStream is) {
	super(convertToLegacyFormat(is));
    }

    public static InputStream convertToLegacyFormat(final InputStream is) {
	Document document = convertInputStreamToDOM(is);
//	document.getDocumentElement().removeAttribute("xmlns");
//	document.getDocumentElement().removeAttribute("xmlns:xsi");
//	document.getDocumentElement().removeAttribute("xsi:schemaLocation");
	return convertStringToInputStream(addDocTypeAndConvertDOMToString(document));
    }

    public static Document convertInputStreamToDOM(InputStream is) {
	Document document = null;
	InputSource isou = null;
//	BufferedInputStream bis = new BufferedInputStream(is);
	DocumentBuilder builder = null;
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory
		    .newInstance();

	    Reader reader = new InputStreamReader(is);
	    isou = new InputSource(reader);
//	    isou.setEncoding("ISO-8859-1");
	    builder = factory.newDocumentBuilder();

	} catch (ParserConfigurationException ex) {
	    log.error(ex.getMessage(), ex);
	} 
//	catch (UnsupportedEncodingException e) {
//	    log.error(e);
//	}

	try {
	    document = builder.parse(isou);
	} catch (SAXException ex) {
	    log.error(ex.getMessage(), ex);
	} catch (IOException ex) {
	    log.error(ex.getMessage(), ex);
	}

	return document;
    }

    public static String addDocTypeAndConvertDOMToString(final Document document) {

	TransformerFactory transfac = TransformerFactory.newInstance();
	Transformer trans = null;
	try {
	    trans = transfac.newTransformer();
	} catch (TransformerConfigurationException ex) {
	    log.error(ex.getMessage(), ex);
	}

	trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	trans.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
		"//JasperReports//DTD Report Design//EN");
	trans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
		"http://jasperreports.sourceforge.net/dtds/jasperreport.dtd");

	StringWriter sw = new StringWriter();
	StreamResult result = new StreamResult(sw);
	DOMSource source = new DOMSource(document);
	try {
	    trans.transform(source, result);
	} catch (TransformerException ex) {
	    log.error(ex.getMessage(), ex);
	}

	return sw.toString();
    }

    public static InputStream convertStringToInputStream(final String template) {
	InputStream is = null;
	try {
	    
	    if(SystemUtils.IS_OS_WINDOWS){
		log.trace("### Cargando el objeto desde un ambiente WINDOWS");
		is = new ByteArrayInputStream(template.getBytes("UTF-8"));
	    }else if (SystemUtils.IS_OS_LINUX){
		log.trace("### Cargando el objeto desde un ambiente UNIX (LINUX)");
		is = new ByteArrayInputStream(template.getBytes());
	    }else if(SystemUtils.IS_OS_MAC){
		log.trace("### Cargando el objeto desde un ambiente MAC");
		is = new ByteArrayInputStream(template.getBytes("UTF-8"));
	    }
	    
	} catch (UnsupportedEncodingException ex) {
	    log.debug(ex.getMessage(), ex);
	}
	return is;
    }
}
