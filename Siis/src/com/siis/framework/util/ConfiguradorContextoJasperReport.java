package com.casewaresa.framework.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.casewaresa.iceberg_sr.contract.IReporteConstantes;

public class ConfiguradorContextoJasperReport {
    /** Creates a new instance of Configuracion */
    public static final ConfiguradorContextoJasperReport pConfiguradorJasperRepor = new ConfiguradorContextoJasperReport();

    private static Logger log = Logger
	    .getLogger(ConfiguradorContextoJasperReport.class);

    private ConfiguradorContextoJasperReport() {
	super();

    }

    /**
     * @return
     */
    public static ConfiguradorContextoJasperReport getInstance() {
	return pConfiguradorJasperRepor;
    }

    /**
     * configura ibatis para el acceso a la BD
     */
    public void configurar() throws IOException {
	File file = new java.io.File(
		IReporteConstantes.REPORTE_RUTA_TMP_VIRTUALIZACION);
	log.info("Folder Virtualizacion ==>" + file.getAbsolutePath());
	if (!file.exists()) {
	    log.debug("No existe el folder ==>" + file.getAbsolutePath());
	    file.mkdirs();
	    log.debug("Se creo el folder ==>" + file.getAbsolutePath());
	}
	file.setWritable(true);
	file.setReadable(true);
	log.debug("Se aplican valores permisos Writable(true), Readable(true)");

    }

}
