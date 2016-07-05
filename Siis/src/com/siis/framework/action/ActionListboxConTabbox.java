package com.siis.framework.action;

import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Toolbarbutton;

import com.casewaresa.framework.dto.IBeanAbstracto;

public abstract class ActionListboxConTabbox extends IActionListbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 369798945455650102L;

	private String[] nombreTabs;
	private List<String[]> nombreColumnas;
	private boolean aplicaEstado;
	private String widthColumnaEstado;

	public abstract void onListarDetalle(int index, Listbox listboxDetalle);

	public abstract void configurarComponentes();

	
	public void configurarImageDetail(final Listitem item,
			final Button botonNuevo, final String labelNuevo,
			final String labelAgregar) {
		if (item != null) {
			Listcell image = new Listcell();
			final Toolbarbutton imagen = new Toolbarbutton("",
					"/imagenes/detail-closed.png");
			imagen.setAttribute("isOpen", false);
			imagen.setWidth("15px");
			imagen.setHeight("15px");
			imagen.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event arg0) throws Exception {
					if (!Boolean.parseBoolean(imagen.getAttribute("isOpen")
							.toString())) {
						imagen.setImage("/imagenes/detail-open.png");
						imagen.setAttribute("isOpen", true);
						botonNuevo.setLabel(labelAgregar);
						botonNuevo.setAttribute("AGREGAR_DETALLE", true);
						botonNuevo.setAttribute("SEC_MAESTRO",
								((IBeanAbstracto) item.getValue())
										.getPrimaryKey());
						Listitem itemOculto = ((Listbox) item.getParent())
								.getItemAtIndex(item.getIndex() + 1);
						itemOculto.setVisible(true);
						itemOculto.setSelected(true);

						onListarDetalle(0,
								(Listbox) ((Tabbox) ((Listcell) itemOculto
										.getLastChild()).getFirstChild())
										.getTabpanels().getFirstChild()
										.getFirstChild());

					} else {
						imagen.setImage("/imagenes/detail-closed.png");
						imagen.setAttribute("isOpen", false);
						Listitem itemOculto = ((Listbox) item.getParent())
								.getItemAtIndex(item.getIndex() + 1);
						itemOculto.setVisible(false);
						itemOculto.setSelected(false);
						botonNuevo.setLabel(labelNuevo);
						botonNuevo.setAttribute("AGREGAR_DETALLE", false);
					}
				}
			});

			image.appendChild(imagen);
			item.getChildren().add(0, image);
		}

	}

	public Listitem agregarDetail(int span) {

		Listitem detail = new Listitem();
		Listcell cell1 = new Listcell();
		Listcell cell2 = new Listcell();
		cell2.setSpan(span);

		Tabbox tabbox_tappanel = new Tabbox();
		tabbox_tappanel.setStyle("overflow : auto");

		Div divTabs = new Div();
		Tabbox tabbox_taps = new Tabbox();

		crearTabsNoVisibles(tabbox_tappanel);
		crearTabsVisibles(tabbox_taps);

		divTabs.appendChild(tabbox_taps);

		cell2.appendChild(tabbox_tappanel);
		cell2.appendChild(divTabs);

		crearEventoTab(tabbox_taps);

		detail.appendChild(cell1);
		detail.appendChild(cell2);
		detail.setVisible(false);
		detail.setAttribute("detail", "detail");
		return detail;
	}

	public void crearTabsNoVisibles(Tabbox tabbox) {
		Tabs tabsNoVisibles = new Tabs();
		tabsNoVisibles.setVisible(false);
		Tabpanels tabpanels = new Tabpanels();
		for (int i = 0; i < nombreTabs.length; i++) {
			tabsNoVisibles.appendChild(new Tab());
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setStyle("overflow : auto");
			tabpanels.appendChild(tabpanel);
			tabpanel.appendChild(crearLisbox(i));
		}

		tabbox.appendChild(tabsNoVisibles);
		tabbox.appendChild(tabpanels);
	}

	public void crearTabsVisibles(Tabbox tabbox) {
		Tabs tabsVisibles = new Tabs();
		for (int i = 0; i < nombreTabs.length; i++) {
			tabsVisibles.appendChild(new Tab(nombreTabs[i]));
		}
		tabbox.appendChild(tabsVisibles);
	}

	public Listbox crearLisbox(int indexTab) {

		Listbox listbox = new Listbox();
		listbox.setMold("paging");
		listbox.setPageSize(3);
		listbox.setWidth("110%");
		Listhead listhead = new Listhead();
		for (String columna : nombreColumnas.get(indexTab)) {
			listhead.appendChild(new Listheader(columna));
		}
		if (isAplicaEstado()) {
			Listheader columnaEstado = (Listheader) listhead.getLastChild();
			columnaEstado.setAlign("center");
			if (getWidthColumnaEstado() != null
					&& !getWidthColumnaEstado().equals(""))
				columnaEstado.setWidth(getWidthColumnaEstado());
			else
				columnaEstado.setWidth("70px");
		}

		listbox.appendChild(listhead);

		return listbox;
	}

	
	public void crearEventoTab(Tabbox tabbox) {
		Tabs listTab = tabbox.getTabs();
		Div div = (Div) tabbox.getParent();
		final Tabbox tabb = (Tabbox) ((Listcell) div.getParent())
				.getFirstChild();

		for (final Component tab : (List<Component>) listTab.getChildren()) {
			tab.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					tabb.setSelectedIndex(((Tab)tab).getIndex());
					onListarDetalle(((Tab)tab).getIndex(), (Listbox) ((Tabpanel) tabb
							.getTabpanels().getChildren().get(((Tab)tab).getIndex()))
							.getFirstChild());
				}
			});
		}
	}

	public Tabbox getTabBox(Listitem item) {
		Listcell listCell = (Listcell) item.getChildren().get(1);
		if (listCell.getChildren().size() > 0) {
			return (Tabbox) listCell.getChildren().get(0);
		}
		return null;
	}

	public Listbox getListBoxTabSeleccionado(Listitem item) {
		Tabbox tabbox = getTabBox(item);
		if (tabbox != null) {
			return (Listbox) tabbox.getSelectedPanel().getChildren().get(0);
		}
		return null;
	}

	public void setColumnas(List<String[]> columnas) {
		this.nombreColumnas = columnas;
	}

	public void setNombreTabs(String... nombreTabs) {
		this.nombreTabs = nombreTabs;
	}

	public void crearListitemListaVacia(int sizeListaConsulta,
			Listbox listboxDetalle, String msg) {
		if (sizeListaConsulta == 0) {			
			listboxDetalle.setEmptyMessage(msg);

		}

	}
	
	
	@Override
	public void agregarEventoFiltrar() throws SuspendNotAllowedException,
	    InterruptedException {
	// 
	    
		if (parametros == null)
			parametros = new HashMap<String, Object>();

		parametros.put("listaDetalles", super.getListaDetalle());

		ActionStandard win = (ActionStandard) Executions
				.createComponents("/filtro_detalle_visual.zul", null,
						parametros);

		win.doModal();
	}
	
	/**
	 * @param aplicaEstado
	 *            the aplicaEstado to set
	 */
	public void setAplicaEstado(boolean aplicaEstado) {
		this.aplicaEstado = aplicaEstado;
	}

	/**
	 * @return the aplicaEstado
	 */
	public boolean isAplicaEstado() {
		return aplicaEstado;
	}

	/**
	 * @param widthColumnaEstado
	 *            the widthColumnaEstado to set
	 */
	public void setWidthColumnaEstado(String widthColumnaEstado) {
		this.widthColumnaEstado = widthColumnaEstado;
	}

	/**
	 * @return the widthColumnaEstado
	 */
	public String getWidthColumnaEstado() {
		return widthColumnaEstado;
	}

}
