package com.casewaresa.framework.pivottable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.casewaresa.iceberg_pg.dto.PGTSaldoDetallado;
import com.casewaresa.iceberg_pg.facade.ParametrizacionFac;

public class PivotData {

    protected static Logger log = Logger.getLogger(PivotData.class);




    public static List<List<Object>> obtenerDatos() {
	log.info("Ejecutando método [obtenerDatos]");

	List<?> listaDatos = null;

	List<List<Object>> dataList = new ArrayList<List<Object>>();

	try {

	    listaDatos = (List<?>) ParametrizacionFac
		    .getFacade()
		    .obtenerListado("PG_obtenerListaSaldoDetallado");
	    PGTSaldoDetallado saldo = null;
	    for (Object object : listaDatos) {
		List<Object> row = new ArrayList<Object>();
		saldo = (PGTSaldoDetallado) object;
		row.add(saldo.getPeriodo().getPeriodo());
		row.add(saldo.getPeriodo().getNombre());
		row.add(saldo.getFondo().getFondo());
		row.add(saldo.getFondo().getNombre());
		row.add(saldo.getUnidadPresupuesto().getUnidad());
		row.add(saldo.getUnidadPresupuesto().getNombre());
		row.add(saldo.getUnidadEjecucion().getUnidad());
		row.add(saldo.getUnidadEjecucion().getNombre());
		row.add(saldo.getActividad().getActividad());
		row.add(saldo.getActividad().getNombre());
		row.add(saldo.getConcepto().getConcepto());
		row.add(saldo.getConcepto().getNombre());
		row.add(saldo.getClasificador().getClasificador());
		row.add(saldo.getClasificador().getNombre());
		row.add(saldo.getFuenteFuncion().getFuenteFuncion());
		row.add(saldo.getFuenteFuncion().getNombre());
		row.add(saldo.getTipoSaldo().getTipoSaldo());
		row.add(saldo.getTipoSaldo().getNombre());
		row.add(saldo.getAno());
		row.add(saldo.getRubro().getRubro());
		row.add(saldo.getRubro().getNombre());
		row.add(saldo.getNaturaleza());
		row.add(saldo.getEnero());
		row.add(saldo.getFebrero());
		row.add(saldo.getMarzo());
		row.add(saldo.getAbril());
		row.add(saldo.getMayo());
		row.add(saldo.getJunio());
		row.add(saldo.getJulio());
		row.add(saldo.getAgosto());
		row.add(saldo.getSeptiembre());
		row.add(saldo.getOctubre());
		row.add(saldo.getNoviembre());
		row.add(saldo.getDiciembre());

		dataList.add(row);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return dataList;
    }

    // private static List<String> obtenerColumnas() {
    // log.info("Ejecutando método [obtenerColumnas]");
    //
    // List<String> listaColumnas = null;
    //
    // try {
    // HashMap parameters = new HashMap();
    //
    // parameters.put("OWNER", "ICEBERG");
    // parameters.put("OBJECT_NAME", "COV_MOVIMIENTO_FLUJO_CAJA");
    //
    // listaColumnas = (List<String>) ParametrizacionFac.getFacade()
    // .obtenerListado("obtenerColumnas", parameters);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return listaColumnas;
    // }

    /**
     * Return column labels
     */
    public static List<String> getColumns() {
	return Arrays.asList(new String[] { "CODIGO_PERIODO", "NOMBRE_PERIODO",
		"CODIGO_FONDO", "NOMBRE_FONDO", "CODIGO_UNIDAD_PRESUPUESTO",
		"NOMBRE_UNIDAD_PRESUPUESTO", "CODIGO_UNIDAD_EJECUCION",
		"NOMBRE_UNIDAD_EJECUCION", "CODIGO_ACTIVIDAD",
		"NOMBRE_ACTIVIDAD", "CODIGO_CONCEPTO", "NOMBRE_CONCEPTO",
		"CODIGO_CLASIFICADOR", "NOMBRE_CLASIFICADOR",
		"CODIGO_FUENTE_FUNCION", "NOMBRE_FUENTE_FUNCION",
		"CODIGO_TIPO_SALDO", "NOMBRE_TIPO_SALDO", "ANO",
		"CODIGO_RUBRO", "NOMBRE_RUBRO", "NATURALEZA", "ENERO",
		"FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO",
		"AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" });
    }

   
}
