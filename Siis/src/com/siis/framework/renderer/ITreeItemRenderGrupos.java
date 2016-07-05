/**
 * 
 */
package com.casewaresa.framework.renderer;

import java.util.List;

import org.zkoss.zul.TreeitemRenderer;

/**
 * @author jhoanseve2
 * @name ITreeItemRenderGrupos.java
 * @date 3/05/2011
 * @desc
 */
@SuppressWarnings("rawtypes")
public interface ITreeItemRenderGrupos extends TreeitemRenderer {
	
	void setList(List list);
	void setDeshabilitar(boolean index);
	List getList();
	boolean getDeshabilitar();
	
	
}
