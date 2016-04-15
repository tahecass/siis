package com.siis.viewModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout; 
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.siis.dto.NavigationPage;

public class NavigationViewModel extends Borderlayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8557985725089774850L;
	NavigationPage currentPage;
	private Map<String, Map<String, NavigationPage>> pageMap;

	@Wire
	Tabbox tb_tabbox;

	// @Init
	// public void init() {
	//
	// }
	@AfterCompose
	public void AfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		System.out.println("AfterCompose");
		initPageMap();
		currentPage = pageMap.get("Cartera").get("Cartera");
		Selectors.wireComponents(view, this, false);

	}

	@Command
	public void navigatePage(@BindingParam("target") NavigationPage targetPage) {
		System.out.println("navigatePage ");

		agregarTab(targetPage.getTitle(), targetPage.getId(), targetPage.getIncludeUri());
	}

	@Command
	public void salir(){
		Executions.getCurrent().getDesktop().getSession()
		.removeAttribute("usuario");
		this.detach();
		Executions.sendRedirect("/inicio.zul");
	}
	
	private void agregarTab(String titulo, String id, String zul) {
		System.out.println("agregarTab ==> id " + id);
		Map<String, Object> arguments = new HashMap<String, Object>();
		Tabpanel tabpanel = new Tabpanel();
		Tab tab = new Tab(titulo);
		tab.setId(id);
		if (tb_tabbox.hasFellow(id)) {
			return;
		}

		tab.setClosable(true);
		tab.setSelected(true);

		if (tb_tabbox.getTabs() == null) {
			tb_tabbox.appendChild(new Tabs());
			tb_tabbox.appendChild(new Tabpanels());
		}

		tb_tabbox.getTabs().appendChild(tab);

		arguments.put("tabularIndex", tab.getIndex());

		tb_tabbox.getTabpanels().appendChild(tabpanel);

		tb_tabbox.invalidate();

		tb_tabbox.setVisible(true);

		Executions.createComponents(zul, tabpanel, arguments);
	}

	public NavigationPage getCurrentPage() {
		return currentPage;
	}

	public Map<String, Map<String, NavigationPage>> getPageMap() {
		return pageMap;
	}

	private void initPageMap() {
		pageMap = new LinkedHashMap<String, Map<String, NavigationPage>>();

		addPage("Cartera", "Cartera", "/formulario_cartera.zul", "form1");

		addPage("Proveedores", "Proveedores", "/formulario_proveedor.zul", "form2");

		addPage("Disponible", "Disponible", "/formulario_disponible.zul", "form3");

		addPage("Créditos", "Créditos", "/formulario_credito.zul", "form4");

		addPage("Proyectos", "Proyectos", "/formulario_proyecto.zul", "form5");

		addPage("Indicadores", "Indicadores", "/formulario_indicador.zul", "form6"); 
		addPage("EFS", "EFS", "/formulario_efs.zul", "form7");

		addPage("Calendario", "Calendario", "/zk_calendar.zul", "form8");
 
		addPage("General", "Banco", "/banco.zul", "form10");

	}

	private void addPage(String title, String subTitle, String includeUri, String id) {
		addPage(title, subTitle, includeUri, null, id);
	}

	private void addPage(String title, String subTitle, String includeUri, String data, String id) {
		String folder = "/formas";
		Map<String, NavigationPage> subPageMap = pageMap.get(title);
		if (subPageMap == null) {
			subPageMap = new LinkedHashMap<String, NavigationPage>();
			pageMap.put(title, subPageMap);
		}
		NavigationPage navigationPage = new NavigationPage(title, subTitle, folder + includeUri, data, id) {
			@Override
			public boolean isSelected() {
				return currentPage == this;
			}
		};
		subPageMap.put(subTitle, navigationPage);
	}

	@Command
	public void onSeleccionarItem(@BindingParam("target") NavigationPage targetPage) {
		System.out.println("onSeleccionarItem " + targetPage.getIncludeUri());
		agregarTab(targetPage.getSubTitle(), targetPage.getId(), targetPage.getIncludeUri());
	}
}
