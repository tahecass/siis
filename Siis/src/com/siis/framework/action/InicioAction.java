/**
 * InicioAction.java
 */
package com.siis.framework.action;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Iframe;

import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;

/**
 * @author CaseWare Ingenieria
 * @date 20/04/2009
 * @name InicioAction.java
 * @desc --
 */

public class InicioAction extends ActionStandard implements AfterCompose {

    private static final long serialVersionUID = -2492780664885641602L;

    protected static Logger log = Logger.getLogger(InicioAction.class);

    public void afterCompose() {
    	log.info("Ejecutando Método [afterCompose]...");
	
		this.setFrameCliente();			
    }
    

    public void setFrameCliente(){
	log.info("Ejecutando método [setFrameCliente]...");
	
	try {
	    Iframe idFrameCliente = (Iframe)this.getFellow("idFrameCliente");
			
	    String pathFrameClient = (String)ParametrizacionFac.getFacade().obtenerRegistro("getPathFrameCliente");
	    if(pathFrameClient != null){
	    	idFrameCliente.setSrc(pathFrameClient);
	    	idFrameCliente.applyProperties();
	    }
	} catch (ComponentNotFoundException e) {
	    e.printStackTrace();
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_OBJ_NO_ENCONTRADO,e));
	} catch (Exception e) {
	    e.printStackTrace();
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL,e));
	}
    }    
}
