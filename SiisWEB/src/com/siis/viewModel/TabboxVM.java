package com.siis.viewModel;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

/**
 * Simple Tabbox
 *
 * @author <a href="mailto:jmcp18@gmail.com">Jose Manuel Campechano P.</a>
 * @version 1.0
 */

public class TabboxVM {

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	//Componentes UI
	@Wire	
	Tabbox tb_tabbox;

	@Command
	public void window1(){
		agregarTab("Ventana 1", "v1", "v1.zul");
	}

	
	@Command
	public void window2(){
		agregarTab("Ventana 2", "v2", "v2.zul");
	}
	
	@Command
	public void window3(){
		agregarTab("Ventana 3", "v3", "v3.zul");
	}
		
	private void agregarTab(String titulo, String id, String zul) {
		Map<String, Object> arguments = new HashMap<String, Object>();
		Tabpanel tabpanel = new Tabpanel();
		Tab tab = new Tab(titulo);
		tab.setId(id);
		if(tb_tabbox.hasFellow(id)){
			Messagebox.show("Ya esta abierta la ventana!");
			return;
		}
		
		tab.setClosable(true);
		tab.setSelected(true);
		
		if (tb_tabbox.getTabs() == null){
			tb_tabbox.appendChild(new Tabs());			
			tb_tabbox.appendChild(new Tabpanels());			
		}

		tb_tabbox.getTabs().appendChild(tab);

		arguments.put("tabularIndex", tab.getIndex());

		tb_tabbox.getTabpanels().appendChild(tabpanel);
		tb_tabbox.invalidate();

		Executions.createComponents(zul, tabpanel, arguments);
	}

}
