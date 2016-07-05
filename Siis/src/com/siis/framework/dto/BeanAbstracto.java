/**
 * BeanAbstracto.java
 */
package com.siis.framework.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.siis.framework.dto.impl.LlaveNatural;

/**
 * @author Fabio Baron 
 * @date 23/01/2007
 * @name BeanAbstracto.java
 * @desc Define el comportamiento de todos los DTO del proyecto.
 */

public abstract class BeanAbstracto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4024256396660341646L;
	public Logger log = Logger.getLogger(this.getClass());
	private String MD5 = null;
	
	/**
	 * @type   Constructor de la clase BeanAbstracto
	 * @name   BeanAbstracto 
	 * @return void
	 * @desc   Crea una nueva instancia de BeanAbstracto
	 */
	public BeanAbstracto(){
		super();
	}
		
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * @type   Método de la clase BeanAbstracto
	 * @name   getMD5
	 * @return void
	 * @param MD5
	 * @desc   obtiene el valor de la propiedad mD5
	 */
	public String getMD5() {
		return MD5;
	}

	/**
	 * @type   Método de la clase BeanAbstracto
	 * @name   setMD5
	 * @return String
	 * @param MD5
	 * @desc   Actualiza el valor de la propiedad mD5
	 */
	public void setMD5(String md5) {
		MD5 = md5;
	}

	public abstract List<LlaveNatural> getLlaveNatural();
	
}
