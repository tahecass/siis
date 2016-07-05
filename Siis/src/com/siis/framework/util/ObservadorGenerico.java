package com.casewaresa.framework.util;

import java.util.Observable;
import org.apache.log4j.Logger;
//import com.casewaresa.iceberg_gp.dto.*;
public class ObservadorGenerico extends Observable {
	protected static Logger log = Logger.getLogger(SObservable.class);
	public ObservadorGenerico() {
		super();
	}
	public void modificar(Object object){
		log.info("Ejecutando el metodo[ modificar ]");
		this.setChanged();
		this.notifyObservers(object);
		
	}

}


	
