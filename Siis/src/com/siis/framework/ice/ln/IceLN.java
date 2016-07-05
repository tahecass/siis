/**
 * 
 */
package com.casewaresa.framework.ice.ln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.exception.NOExisteCalendarioNominaAbierto;
import com.casewaresa.framework.exception.NOExisteTipoNominaSesion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.iceberg_ln.dto.LNTCalendarioNomina;
import com.casewaresa.iceberg_no.dto.NOTMiTipoNomina;

/**
 * Clase que contiene metodos estaticos que hacen referencia a los paquetes en
 * la base de datos que comiencan con el prefijo ICE_LN.nombre_metodo o que esten en LNP_..., estos
 * paquetes tienen metodos estaticos para uso en toda la aplicacion
 * 
 * @version 1.0
 * 
 * @author JMORA
 * 
 */
public class IceLN {

    private static Logger log = Logger.getLogger(IceLN.class);

    /**
     * 
     * 
     * @param filtro
     *            Lista de Keys habilitados NOP_PARAMETRO_VALOR parametro que se
     *            le envia al metodo
     *            nop_parametro.get_valor(#{NOP_PARAMETRO_VALOR
     *            ,jdbcType=VARCHAR,mode=INOUT})
     * 
     *            Parametros definidos (Estos parametros no se envian ya que se
     *            toman directo de su origne como una sesion):
     *            SEC_TIPO_NOMINA_SESION
     * 
     * @return {@link LNTCalendarioNomina} Abierto por el tipo Nómina en Sesión
     * @throws NOExisteCalendarioNominaAbierto
     * @throws NOExisteTipoNominaSesion
     */
    public static LNTCalendarioNomina obtenerCalendarioNominaAbierto(
	    Map<String, Object> map) throws NOExisteCalendarioNominaAbierto,
	    NOExisteTipoNominaSesion {
	log.info("Ejecutando Método [ obtenerCalendarioNominaAbierto() ]");
	log.debug("filtros ==>" + map);
	if (map == null) {
	    map = new HashMap<String, Object>();
	}
	NOTMiTipoNomina miTipoNomina = (NOTMiTipoNomina) Executions
		.getCurrent().getDesktop()
		.getAttribute(IConstantes.TIPO_NOMINA);
	LNTCalendarioNomina calendarioNominaAbierto = new LNTCalendarioNomina();
	if (miTipoNomina != null) {
	    map.put("SEC_TIPO_NOMINA", miTipoNomina.gettipoNomina()
		    .getSecTipoNomina());
	    map.put("CALENDARIO_NOMINA", calendarioNominaAbierto);
	    try {
		ParametrizacionFac.getFacade().obtenerRegistro(
			"ICE_LN_obtener_calendario_nomina_abierto", map);
	    } catch (Exception e) {

		log.warn(e);
		throw new NOExisteCalendarioNominaAbierto();
	    }
	    calendarioNominaAbierto = (LNTCalendarioNomina) map
		    .get("CALENDARIO_NOMINA");

	    if (calendarioNominaAbierto.getSecCalendarioNomina() == null) {
		throw new NOExisteCalendarioNominaAbierto();
	    }
	} else {
	    throw new NOExisteTipoNominaSesion();
	}
	log.trace("Retorno ==>" + calendarioNominaAbierto);
	return calendarioNominaAbierto;

    }

    /**
     * Borra el historial de la tabla LNT_liquidado por las {@link ArrayList} de
     * Long Secuencia de Calendario Nomina
     * 
     * @since 1.0
     * @param secuenciasCalendarioNomina
     * @throws Exception
     *             Cualquier Excepcion a nivel de Base de Datos
     * 
     */
    public static void borrarLiquidacionCalendarioNomina(
	    ArrayList<Long> secuenciasCalendarioNomina) throws Exception {
	log.info("Ejecutando Método [ borrarLiquidacionCalendarioNomina()... ]");
	if (secuenciasCalendarioNomina != null
		&& !secuenciasCalendarioNomina.isEmpty()) {
	    log.trace("secuenciasCalendarioNomina ==> "
		    + secuenciasCalendarioNomina);
	    Map<String, Object> parametros = new HashMap<String, Object>();
	    parametros.put("SECUENCIAS_CALENDARIO_NOMINA",
		    secuenciasCalendarioNomina.toArray());
	    ParametrizacionFac.getFacade().borrarRegistro(
		    "ICE_LN_borrar_liq_x_calendario", parametros);
	} else {
	    log.warn("Secuencias de Calendario Nomina a inicializar NUll-as o vacia");
	}

    }

}
