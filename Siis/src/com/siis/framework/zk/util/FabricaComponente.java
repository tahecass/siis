package com.casewaresa.framework.zk.util;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.metainfo.ComponentDefinition;
import org.zkoss.zk.ui.metainfo.impl.MacroDefinition;
import org.zkoss.zul.Decimalbox;

import com.casewaresa.framework.macros.BandboxFindPaging;

public class FabricaComponente {
    
    private static final String MACRO_URI_BANDBOX_FIND_PAGING = "/WEB-INF/components/bandboxFindPaging.zul";
    private static final String MACRO_URI_DECIMALBOX_FC = "/WEB-INF/components/decimal_box.zul";
    
    public static <T extends Component> T getMacroComponente(Class<T> clase,Map<String,Object> parametros)throws Exception{
	
	String macroUri = getMacroUri(clase);
	
	 ComponentDefinition componentDefinition = 
		MacroDefinition.newMacroDefinition(null, null, 
			clase.getSimpleName(), HtmlMacroComponent.class, 
			macroUri, false);
 
	HtmlMacroComponent macroComponent = (HtmlMacroComponent)componentDefinition.newInstance(null, null);	
	
	for(String property : parametros.keySet()){
	    macroComponent.setDynamicProperty(property, parametros.get(property));
	}
	
	macroComponent.afterCompose();
	
	return clase.cast(macroComponent.getFirstChild());
    }
    
    private static <T extends Component> String getMacroUri(Class<T> clase){
	String macroUri = "";
	if(clase.getSimpleName().equals(BandboxFindPaging.class.getSimpleName())){
	    macroUri = MACRO_URI_BANDBOX_FIND_PAGING;
	}else if(clase.getSimpleName().equals(Decimalbox.class.getSimpleName())){
	    macroUri = MACRO_URI_DECIMALBOX_FC;
	}
	return macroUri;
    }
    
}
