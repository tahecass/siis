package com.siis.viewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.siis.configuracion.Conexion;
import com.siis.dto.Menu;
import com.siis.viewModel.framework.BandboxSeeker;

public class MenuViewModel {
	public Conexion con;

	@Wire
//	Listbox listboxOpcionMenu;
	List<Menu> listaMenu;
	Menu objetoSeleccionado;
	@Wire
	Tabbox tb_tabbox;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		listaMenu = new ArrayList<Menu>();
		crearMenu();
	}

	@NotifyChange("*")
	@Command
	public void onSeleccionar(@BindingParam("objetoSeleccionado") Menu menuSeleccionado) {
		System.out.println(" onSeleccionar ==> ");
		agregarTab(menuSeleccionado.getNombre(),menuSeleccionado.getId(), menuSeleccionado.getFormaAsociada());

	}

	@NotifyChange("listaMenu")
	public void crearMenu() {
		System.out.println(" crearMenu ==> ");

		listaMenu.add(new Menu("Cartera", "//formas//formuario_cartera.zul", "men1"));
		listaMenu.add(new Menu("Proveedores", "", "men2"));
		listaMenu.add(new Menu("Créditos", "", "men3"));
		listaMenu.add(new Menu("Proyectos", "", "men4"));
		listaMenu.add(new Menu("Indicadores", "//formas//formulario_indicador.zul", "men5"));
		listaMenu.add(new Menu("EFS", "", "men6"));
		listaMenu.add(new Menu("Calendario", "", "men7"));
		listaMenu.add(new Menu("Notificaciones", "", "men8"));
		setListaMenu(listaMenu);

	}

	private void agregarTab(String titulo, String id, String zul) {
		Map<String, Object> arguments = new HashMap<String, Object>();
		Tabpanel tabpanel = new Tabpanel();
		Tab tab = new Tab(titulo);
		tab.setId(id);
		if (tb_tabbox.hasFellow(id)) {
			Messagebox.show("Ya esta abierta la ventana!");
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

	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public Menu getObjetoSeleccionado() {
		return objetoSeleccionado;
	}

	public void setObjetoSeleccionado(Menu objetoSeleccionado) {
		this.objetoSeleccionado = objetoSeleccionado;
	}

}
