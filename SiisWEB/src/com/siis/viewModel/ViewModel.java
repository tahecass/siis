package com.siis.viewModel;


import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType; 
import org.zkoss.zk.ui.Component; 
import org.zkoss.zk.ui.select.Selectors; 

public class ViewModel {

 

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose viemodel");

	}
}