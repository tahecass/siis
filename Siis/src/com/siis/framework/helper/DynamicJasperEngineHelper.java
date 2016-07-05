package com.casewaresa.framework.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRQueryChunk;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

import com.casewaresa.framework.util.LegacyJasperInputStream;


/**
 * @author Diego Rodriguez
 * @date Nov 1, 2007
 * @name DynamicJasperEngineHelper.java
 * @desc esta clase exporta un reporte dise�ado en dynamic Jasper
 */
@SuppressWarnings({"unchecked","rawtypes","deprecation"})
public class DynamicJasperEngineHelper {
	
	/** Propiedad log de la clase [ DynamicJasperEngineHelper.java ] 
	 *  @desc: provee un mecanismo para el manejo de mensajes */
	protected static Logger log = Logger.getLogger(DynamicJasperEngineHelper.class);
	private 	Map		parametersMap=null;
	private	    List 	parameterNames = new ArrayList();
	private		String 	queryString=null;
	
	/**
	 * @type   M�todo de la clase DynamicJasperEngineHelper
	 * @name   ejecutarReporte
	 * @return JasperPrint
	 * @param rutaReporte
	 * @param conn
	 * @param propiedades
	 * @desc   este m�todo ejecuta un reporte din�mico y devuelve el jasperprint
	 * 			el cual se le enviar� al exportador apropiado
	 */
	public JasperPrint ejecutarReporte(String rutaReporte, Connection conn, List propiedades){
		List fields = null;
		Statement stmt = null;
		ResultSet rs = null;
		List parametrosReporte       = null;
		
		DynamicReportBuilder constructorDinamicoReporte = new DynamicReportBuilder();;
		DynamicReport reporteDinamico = null;
		
		JasperPrint jp = null;
		
		try {
			//--creamos un nuevo diseño del reporte abriendo el JRXML
			//--JasperDesign objJasperDesign = JRXmlLoader.load( rutaReporte );
			
			JasperDesign objJasperDesign = JRXmlLoader.load(new LegacyJasperInputStream(new FileInputStream(rutaReporte)));

			objJasperDesign.setTitleNewPage(false);
			
			//---------
			parametrosReporte = objJasperDesign.getParametersList();
			log.info("-----------> " + parametrosReporte);
			//-------
			//--extraemos la consulta
			String queryText = objJasperDesign.getQuery().getText();
			
			//-- obtenemos el listado de los campos (colimnas del select)
			fields = objJasperDesign.getFieldsList();
			
			//-- pedimos una nueva conexión al pool de conexiones y ejecutamos el query
			stmt = (Statement) conn.createStatement();
			rs   = stmt.executeQuery( queryText );
			
			//-- le mandamos a la API de dynamic jasper el template del diseño
			constructorDinamicoReporte.setTemplateFile( rutaReporte );
			
			//--a partir de la información del jrxml enviamos agregamos las columnas  
			for(int i=0; i<fields.size();i++){
				JRDesignField obj = ( JRDesignField ) fields.get(i);
				constructorDinamicoReporte.addField(obj.getName(), String.class.getName());
			}
			
			//-- confiugramos la salida
			constructorDinamicoReporte.setIgnorePagination(true);
			reporteDinamico = constructorDinamicoReporte.build();
			
			//--obtemnemos el jasper print del reporte generado
			jp = DynamicJasperHelper
				  .generateJasperPrint(reporteDinamico ///-reporte din�mico
						               ,new ListLayoutManager()
				  					   ,rs);
			
			//--retornamos el reporte en "BRUTO" para ser exportado			
			return jp; 
		}
		catch (JRException e1) {
			log.info("Se daño " + e1);
		} 
		catch (SQLException e) {
			log.error(e.getMessage(),e);
		} catch (FileNotFoundException e) {
			log.error("Archivo jrxml no encontrado...", e);
			log.error(e.getMessage(),e);
		}
		
		//si hay errores retorna null
		return null;
	}
	
	public List removeColumns(List fields, List cols){
		List auxFields = new ArrayList();
		
		int i = 0; int j = 0;
		
		do {
			if(j >= cols.size() || ((Integer)cols.get(j)).intValue() != i){
				auxFields.add(fields.get(i));
				i++;
			}else{
				log.info("Column Deleted: " + ((JRDesignField)fields.get(i)).getName());
				i++;
				j++;
			}
		} while (i < fields.size());
		
		return auxFields;
	}
	
	
	
	public List removeAuxColumns(JasperDesign jd){
		List fields = jd.getFieldsList();
		List auxFields = new ArrayList();
		
		for (Object object : fields) {
			log.info("Column: " + ((JRDesignField)object).getName());
			if(!((JRDesignField)object).getName().contains("AUX_"))
				auxFields.add(object);
			else
				log.info("Column Deleted: " + ((JRDesignField)object).getName());
		}
		return auxFields;
	}

	/**
	 * @type   Método de la clase DynamicJasperEngineHelper
	 * @name   generarCoumnas
	 * @return JasperPrint
	 * @param rutaReporte
	 * @param conn
	 * @param propiedades
	 * @return
	 * @desc   carga xjxml, de este extrae el query y los field a partir de los cuales genera
	 * 		   el nuevo reporte dinámico
	 */
	public JasperPrint generarCoumnas(String rutaReporte, Connection conn, HashMap propiedades, List colsToDelete){
		log.info("[ Generando Columnas ]... ");

		List      fields     = null;
		Statement stmt       = null;
		ResultSet rs         = null;
		Map 	  params     = new Hashtable();
		List 	  valoresParams = new ArrayList();
		List 	  listaParametrosOrdenada = new ArrayList();
		List 	  cols = colsToDelete;
		
		DynamicReportBuilder constructorDinamicoReporte = new DynamicReportBuilder();
		DynamicReport reporteDinamico = null;

		JasperPrint jp = null;

		try {
			//--creamos un nuevo diseño del reporte abriendo el JRXML
			//--JasperDesign objJasperDesign = JRXmlLoader.load( rutaReporte );
			JasperDesign objJasperDesign = JRXmlLoader.load(
			        new LegacyJasperInputStream(new FileInputStream(rutaReporte)));

			//--extraemos la consulta de la plantilla JRXML
			queryString = objJasperDesign.getQuery().getText();
						
			//log.info("Query: " + queryString.substring(0, queryString.indexOf("order by")));
						
			//-- obtenemos el listado de los campos (columnas del select)
			fields = removeAuxColumns(objJasperDesign);
			fields = removeColumns(fields,cols);
			
			//--Extraemos los parametros de plantilla JRXML
			params= objJasperDesign.getParametersMap();

			/*for(int i=0; i<paramsList.size();i++){
				JRDesignParameter a=(JRDesignParameter)paramsList.get(i);
				System.out.println("[PARAMETRO-->]"+i+" "+a.toString());
			}*/

			if (params == null || params.isEmpty()) {
				queryString = objJasperDesign.getQuery().getText();
			}
			else {
				JRDesignQuery query = new JRDesignQuery();
				query.setText(objJasperDesign.getQuery().getText());
				JRDataset dataset=objJasperDesign.getMainDataset();

				//-- Reemplazamos en el query los parametros de la plantilla por un caracter de reemplazo
				parseQuery(dataset,params);
				//-- pedimos una nueva conexi�n al pool de conexiones y ejecutamos el query
				stmt = (Statement) conn.createStatement();
				
				listaParametrosOrdenada=getCollectedParameterNames();
				if(listaParametrosOrdenada!= null && listaParametrosOrdenada.size() > 0){
					for (int i=0; i<listaParametrosOrdenada.size();i++){
						valoresParams.add(propiedades.get(listaParametrosOrdenada.get(i)));
						String valorReemplazo="'"+valoresParams.get(i)+"'";
						queryString=queryString.replaceFirst("\\?", valorReemplazo);
					}
				}
			}		
			rs   = stmt.executeQuery( queryString );
			
			//-- Armamos el reporte plano con Dynamic Jasper
			for (int i=0; i<fields.size(); i++)
			{
				JRDesignField obj = ( JRDesignField ) fields.get(i);
				AbstractColumn columna= ColumnBuilder.getInstance()		
				.setColumnProperty(obj.getName(), obj.getValueClassName())
				.setTitle(obj.getName()).setWidth(180)	
				.build();
				constructorDinamicoReporte.addColumn(columna);
			}
						
			//-- configuramos la salida
			constructorDinamicoReporte.setIgnorePagination(true);
			constructorDinamicoReporte.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
			//constructorDinamicoReporte.setProperty("", "true");
			reporteDinamico = constructorDinamicoReporte.build();
			
			//--obtemnemos el jasper print del reporte generado
			jp = DynamicJasperHelper.generateJasperPrint(reporteDinamico,new ListLayoutManager(),rs);
			//--retornamos el reporte en "BRUTO" para ser exportado			
			return jp; 
		}
		catch (JRException e) {
			log.info("Problema al generar columnas -->" + e);
		} 
		catch (SQLException e) {
			log.error("Problema SQL -->"+e);
		} 
		catch (ColumnBuilderException e) {
			log.error("Problema al generar columnas -->"+e);
		} catch (FileNotFoundException e) {
			log.error("Archivo jrxml no encontrado...", e);
			log.error(e.getMessage(),e);
		}
		
		//si hay errores retorna null
		return null;
	}	
	
	//-- Toma un dataset y un mapa de parametros para entregar un query con un caracter de reemplazo
	//-- en donde estaba los parametros del query
	protected void parseQuery(JRDataset dataset, Map parameter)
	{
		JRQuery query = dataset.getQuery();
		
		if (query != null)
		{
			JRQueryChunk[] chunks = query.getChunks();
			if (chunks != null && chunks.length > 0)
			{
				StringBuffer sbuffer = new StringBuffer();
				JRQueryChunk chunk = null;
				for(int i = 0; i < chunks.length; i++)
				{
					chunk = chunks[i];
					switch (chunk.getType())
					{
						case JRQueryChunk.TYPE_PARAMETER_CLAUSE :
						{
							String parameterName = chunk.getText();
							Object parameterValue = getParameterValue(parameterName);
							sbuffer.append(String.valueOf(parameterValue));
							break;
						}
						case JRQueryChunk.TYPE_PARAMETER :
						{
							sbuffer.append(getParameterReplacement(chunk.getText()));
							parameterNames.add(chunk.getText());
							break;
						}
						case JRQueryChunk.TYPE_TEXT :
						default :
						{
							sbuffer.append(chunk.getText());
							break;
						}
					}
				}

				queryString = sbuffer.toString();
			}
		}
	}
	
	//--
	protected String getQueryString()
	{
		return queryString;
	}
	
	//--
	protected Object getParameterValue(String parameterName)
	{
		JRValueParameter parameter = getValueParameter(parameterName);
		return parameter.getValue();
	}
	
	//--
	protected JRValueParameter getValueParameter(String parameterName)
	{
		JRValueParameter parameter = (JRValueParameter) parametersMap.get(parameterName);
		
		if (parameter == null)
		{
			throw new JRRuntimeException("Parameter \"" + parameterName + "\" does not exist.");
		}
		
		return parameter;
	}
	
	//-- Define el caractaer que va a reempalzar los parametros en el Query
	protected String getParameterReplacement(String parameterName)
	{
		return "?";
	}
	
	//-- Retorna un List con los parametros ordenados de acuerdo asu aparicion en el query
	protected List getCollectedParameterNames()
	{
		return parameterNames;
	}

}
