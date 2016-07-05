package com.casewaresa.framework.factory.impl;

import com.casewaresa.framework.factory.FactoryStandard;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name ProviderFactory.java
 * @desc  Esta fabrica de objetos, se encarga de solo instanciar clases de tipo provider
 */
public class ProviderFactory extends FactoryStandard {
	
	/** Propiedad TIPO_CLASE de la clase [ ProviderFactory.java ] 
	 *  @desc: define el postfijo utilizado para filtrar las clases que puede instanciar */
	final static String TIPO_CLASE="Provider"; 

	@Override
	public Object getObject(String pObject) {
		if(pObject.endsWith(TIPO_CLASE))
		return super.getObject(pObject);
		else 
		{
			try {
				throw new Exception("Esta clase solo puede instanciar Providers");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			} 
		}
		
		return null;
	}
}
