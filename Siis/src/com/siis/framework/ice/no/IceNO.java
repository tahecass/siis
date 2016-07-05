package com.casewaresa.framework.ice.no;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.util.Utilidades;
import com.casewaresa.iceberg_no.dto.NOTGrupoConcepto;

/**
 * 
 * @author JMORA
 * 
 */
public class IceNO {
	private static Logger log = Logger.getLogger(IceNO.class);

	public static String generarNovedadesMasivas(Date fechaGeneracion, Date fechaFinalGeneracion,
			NOTGrupoConcepto grupoConcepto, List<IBeanAbstracto> listContrato)
			throws Exception {
		log.info("Ejecutando mŽtodo [ generarNovedadesMasivas ]");
		log.debug("fechaGeneracion ==>" + fechaGeneracion);
		log.debug("fechaFinalGeneracion ==>" + fechaFinalGeneracion);
		log.debug("listContrato ==>" + listContrato);
		log.debug("grupoConcepto ==>" + grupoConcepto);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		ArrayList<Long> arrayList = new ArrayList<Long>();
		
		for (IBeanAbstracto iBeanAbstracto : listContrato) {
			arrayList.add(iBeanAbstracto.getPrimaryKey());
		}
		parametros.put("SECUENCIAS_CONTRATO", arrayList.toArray());
		parametros.put("FECHA_GENERACION", fechaGeneracion);
		parametros.put("FECHA_FINAL_GENERACION", fechaFinalGeneracion);
		parametros.put("SEC_GRUPO_CONCEPTO", grupoConcepto.getPrimaryKey());
		parametros.put("RETURN_CLOB_HTML", "");
		
		ParametrizacionFac.getFacade().ejecutarProcedimiento(
				"ICE_NO_cargue_novedades_masivas", parametros);
		
		log.trace("Retorno ==>" + parametros.get("RETURN_CLOB_HTML").toString());
		return parametros.get("RETURN_CLOB_HTML").toString();

	}

	public static boolean esCodigoContratoAutomatico() throws Exception {
		log.info("Ejecutando Mï¿½todo [ esCodigoContratoAutomatico ]");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("PARAMETRO", "CODIGO_CONTRATO_AUTOMATICO");
		parametros.put("VALOR", null);

		ParametrizacionFac.getFacade().ejecutarProcedimiento(
				"NO_PARAMETRO_get_valor", parametros);

		String valor = (String) parametros.get("VALOR");

		if (valor != null && valor.equals("S")) {
			log.trace("Retorno ===> true");
			return true;
		}
		log.trace("Retorno ===> false");
		return false;

	}

	public static String validarBandera(Long secTipoNomSesion,
			Long secCalenNomAbierto, String codigoParametro) throws Exception {
		log.info("Ejecutando Metodo [ validarBandera ]");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("CODIGO_NOT_PARAMETRO", codigoParametro);
		parametros.put("SEC_TIPO_NOMINA_SESION", secTipoNomSesion);
		parametros.put("SEC_CALENDARIO_NOMINA_ABIERTO", secCalenNomAbierto);
		parametros.put("VALOR_RETORNO", null);
		parametros.put("SEC_CUENTA_ICEBERG", Utilidades.getUsuarioEnSesion()
				.getSecCuentaIceberg());

		ParametrizacionFac.getFacade().ejecutarProcedimiento(
				"NOP_PARAMETRO_TIPO_NOM_CN_validar_bandera", parametros);

		String valorRetorno = (String) parametros.get("VALOR_RETORNO");
		log.trace("retorno ==>" + valorRetorno);
		return valorRetorno;

	}

}
