package com.casewaresa.framework.util;

import java.io.Serializable;
import org.apache.log4j.Logger;
public class SObservable implements Serializable {
	/**
	 * 
	 */
	protected static Logger log = Logger.getLogger(SObservable.class);
	private static final long serialVersionUID = 1L;
	private static ObservadorGenerico observador;
	private static SObservable sobservable;

	private SObservable() {
		super();
		// TODO Auto-generated constructor stub
		observador = new ObservadorGenerico();
	}

	public static SObservable getInstance() {
		log.info("Ejecutando el metodo[ getInstance ]");

		if (sobservable == null) {
			sobservable = new SObservable();
		}
		return sobservable;
	}

	public ObservadorGenerico getObservadorGenerico() {
		log.info("Ejecutando el metodo[ getObservadorGenerico ]");
		return observador;
	}

	public void setObservadorGenerico(ObservadorGenerico observador) {
		SObservable.observador = observador;
	}

}
