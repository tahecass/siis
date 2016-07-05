package com.siis.framework.action;

import java.util.Map;

import com.casewaresa.framework.dto.IBeanAbstracto;

public abstract class IActionListItem extends ActionStandardBorder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8902520979033856671L;
	
	public void onBorrarMaestro(IBeanAbstracto beanAbstracto){}
	public void onEditarMaestro(IBeanAbstracto beanAbstracto){}
	public abstract void onListarDetalle(Map<String, Object> parametros);
}
