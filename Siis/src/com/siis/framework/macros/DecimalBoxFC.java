package com.casewaresa.framework.macros;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Decimalbox;

import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.helper.PropiedadesHelper;
import com.casewaresa.framework.util.Utilidades;

public class DecimalBoxFC extends Decimalbox implements AfterCompose {

    /**
     * 
     */
    private static final long serialVersionUID = 465850465266983132L;

    private static Logger log = Logger.getLogger(DecimalBoxFC.class);

    @Override
    public void afterCompose() {
	log.info("Ejecutando el metodo [afterCompose()...]");

	try {
	    String separadorDecimal = PropiedadesHelper.getInstance().getPropiedad("ger.signo_decimal");
	    log.debug("separadorDecimal: " + separadorDecimal);
	    
	    if(separadorDecimal !=null && separadorDecimal.equals(".")){
		this.setLocale("zh_TW");
	    }else if(separadorDecimal !=null && separadorDecimal.equals(",")){
		this.setLocale("");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    Utilidades.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}

    }

}
