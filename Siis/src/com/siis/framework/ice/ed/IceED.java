package com.casewaresa.framework.ice.ed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.iceberg_ed.dto.EDTPlantilla;
import com.casewaresa.iceberg_ed.dto.EDTPlantillaParametro;
import com.casewaresa.iceberg_ed.dto.EDTVista;
import com.casewaresa.iceberg_ed.factory.GeneradorXML;

/**
 * Clase que contiene metodos estaticos que hacen referencia a los paqeutes en
 * la base de datos que comienzan con el prefijo ICE_ED.<nombre_metodo>, estos
 * paqeutes tienen metodos globales para uso en toda la aplicacion
 * 
 * @version 1.0
 * 
 * @author JMORA
 * 
 */
public class IceED {
	private static Logger log = Logger.getLogger(IceED.class);

	/**
	 * 
	 * Valida una sentencia SQL
	 * 
	 * 
	 * 
	 * @param sentencia
	 *            {@link String} de la sentencia
	 * @return <code>true</code> si la sentencia es permitida y valida
	 *         <code>false</code> si la sentencia no es permitida y no valida
	 * @throws Exception
	 */
	public static Boolean validarSentenciaSQL(String sentencia)
			throws Exception {
		log.info("Ejecutando m�todo  [ validarSentenciaSQL  ]  ");
		log.debug("Sentencia ==>" + sentencia);
		String valida = "false";
		Map<String, Object> parametro = new HashMap<String, Object>();
		parametro.put("SENTENECIA", sentencia);
		parametro.put("VALIDA", valida);
		if (sentencia != null && !sentencia.isEmpty()) {
			ParametrizacionFac.getFacade().ejecutarProcedimiento(
					"ICE_ED_validar_sentencia_SQL", parametro);

			if (parametro.get("VALIDA") != null) {
				valida = parametro.get("VALIDA").toString();
			} else {
				valida = "false";
			}

		} else {
			return Boolean.parseBoolean(valida);
		}
		log.trace("Retorno ==> " + valida);
		return Boolean.parseBoolean(valida);
	}

	/**
	 * Ejecuta el metod en base de datos ICE_ED.EXTRAER_COLUMNAS(
	 * #{SECUENCIA_EDT_VISTA,jdbcType=NUMERIC}} )
	 * 
	 * @param vista
	 *            {@link EDTVista}
	 * @return {@link List} de {@link String}
	 * @throws Exception
	 */
	public static List<String> extraerColumnas(EDTVista vista) throws Exception {
		log.info("Ejecutando m�todo  [ extraerColumnas  ]  ");
		log.debug("EDTVista ==>" + vista);
		List<String> list = new ArrayList<String>();
		Map<String, Object> parametro = new HashMap<String, Object>();
		parametro.put("OBJETO", vista);
		Collection<?> resultado = (Collection<?>) ParametrizacionFac
				.getFacade().obtenerListado("ICE_ED_extraer_columnas",
						parametro);
		for (Object object : resultado) {
			Map<?, ?> map = (Map<?, ?>) object;
			list.add(map.get("COLUMN_VALUE").toString());
		}
		return list;
	}

	/**
	 * @param plantilla
	 *            Plantilla seleccionada para generar el documento
	 * @param parametros
	 *            {@link List} de {@link EDTPlantillaParametro} para generar el
	 *            documento
	 * @throws Exception
	 * 
	 */

	public static List<Long> generarDocumento(EDTPlantilla plantilla,
			List<EDTPlantillaParametro> parametros) throws Exception {
		log.info("Ejecutando m�todo  [ generarDocumento  ]  ");
		List<Long> secuenciasContratoDocumento = new LinkedList<Long>();

		Map<String, Object> parameterType = new HashMap<String, Object>();
		parameterType.put("PLANTILLA", plantilla);
		parameterType.put("LISTA_PARAMETRO", parametros);
		String secuencias = new String();
		parameterType.put("SECUENCIAS_CONTRATO_DOCUMENTO", secuencias);

		ParametrizacionFac.getFacade().ejecutarProcedimiento(
				"ICE_generar_documento", parameterType);

		if (parameterType.get("SECUENCIAS_CONTRATO_DOCUMENTO") != null
				&& !parameterType.get("SECUENCIAS_CONTRATO_DOCUMENTO")
						.toString().isEmpty()) {
			StringTokenizer tokens = new StringTokenizer(parameterType.get(
					"SECUENCIAS_CONTRATO_DOCUMENTO").toString(), ",");
			while (tokens.hasMoreTokens()) {
				String sec = tokens.nextToken();
				if (sec != null && !sec.isEmpty()) {
					secuenciasContratoDocumento.add(Long.parseLong(sec));
				}
			}
		}
		if (plantilla.getFirmaDigital().equals("S")) {
			String firmas = GeneradorXML.generarXMLFirmas(plantilla);
			parameterType.put("FIRMAS", firmas);
			parameterType.put("SECUENCIAS", secuenciasContratoDocumento);
			ParametrizacionFac.getFacade().ejecutarProcedimiento(
					"agregar_Firmas_a_documento", parameterType);
		}
		return secuenciasContratoDocumento;
	}
}
