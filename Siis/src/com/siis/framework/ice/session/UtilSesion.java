package com.casewaresa.framework.ice.session;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.exception.NOExisteTipoNominaSesion;
import com.casewaresa.iceberg_no.dto.NOTMiTipoNomina;

public class UtilSesion {
    private static Logger log = Logger.getLogger(UtilSesion.class);

    public static NOTMiTipoNomina obtenerMiTipoNomina()
	    throws NOExisteTipoNominaSesion {
	log.info("Ejecutando Método [ obtenerMiTipoNomina ]");
	NOTMiTipoNomina miTipoNomina = (NOTMiTipoNomina) Executions
		.getCurrent().getDesktop()
		.getAttribute(IConstantes.TIPO_NOMINA);

	if (miTipoNomina == null) {
	    throw new NOExisteTipoNominaSesion();
	}
	log.trace("Retorno ==> " + miTipoNomina);
	return miTipoNomina;
    }
}
