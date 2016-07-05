package com.casewaresa.framework.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.helper.ContextoAplicacion;
import com.casewaresa.framework.helper.PropiedadesHelper;
import com.casewaresa.framework.util.ConfiguradorContextoJasperReport;
import com.casewaresa.framework.util.ConfiguradorIbatis;

/**
 * Application Lifecycle Listener implementation class
 * ContextoAplicacionListener
 * 
 */
public class ContextoAplicacionListener implements ServletContextListener {

    protected static Logger log = Logger.getLogger(ContextoAplicacionListener.class);

    /**
     * Propiedad rutaContexto de la clase [ ContextoAplicacionListener.java ]
     * 
     * @desc: guarda ela ruta fisica del contexto de la aplicacion
     */
    private String rutaContexto = "";

    /**
     * Default constructor.
     */
    public ContextoAplicacionListener() {

    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
	try {
	    // --cargamos la configuracion de Ibatis
	    ConfiguradorIbatis.getInstance().configurar("desarrollo");

	    // hallamos la ruta del contexto de la aplicaci�n
	    this.rutaContexto = arg0.getServletContext().getRealPath("/");

	    // guardamos esta ruta para usarla en otros m�dulos
	    ContextoAplicacion contextoAplicacion = ContextoAplicacion.getInstance();
	    contextoAplicacion.setRutaContexto(this.rutaContexto);

	    ConfiguradorContextoJasperReport.getInstance().configurar();
	    
	    
	    Properties properties = new Properties();
	    InputStream inputStrean = new FileInputStream(ContextoAplicacion.getInstance().getRutaContexto() + IConstantes.PROPIEDADES_APLICACION);
	    properties.load(inputStrean);
	    log.trace("Propiedades Cargadas ==> " + properties );

	    PropiedadesHelper.getInstance().setPropiedades(properties);
	    
	} catch (IOException e) {
	    log.error(e);
	}

    }

    public void contextDestroyed(ServletContextEvent arg0) {

    }
}
