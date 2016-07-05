/**
 * 
 */
package com.casewaresa.framework.macros;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import com.casewaresa.framework.action.ActionStandardBorder;
import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.util.MyItemTree;
import com.casewaresa.framework.util.MyMessageBox;
import com.casewaresa.iceberg_menu.action.MNZMenuAction;
import com.casewaresa.iceberg_menu.facade.TreeFacadeMenuPorDemanda;

public class TreeMenu extends Panel implements IInicializarComponentes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger log = Logger.getLogger(this.getClass());
	private String ids;
	private ActionStandardBorder actionObject;
	private String consultaArbol;
	private String consultaArbolHijos;
	private Tree idZTreeMenu;
	private Treechildren idZTreeChildren;
	private Menuitem idZMitAccesoDirecto;
	private Map<String, Object> parametros;
	private Textbox idZTbxCampoBusquedaTreeMenu;
	private Button idZBtnBusquedaTreeMenu;

	MyItemTree item = null;
	private String dataAnterior = "";
	private Integer campoAnterior = -1;

	private String consultaobtenerListado;
	private String consultaPath;

	public void afterCompose() {
		log.info("Ejecutando el metodo [ afterCompose() ]");
		this.cargarComponentesVista();
		this.onAgregarEventos();
		this.loadTree();
	}

	public void cargarComponentesVista() {
		log.info("Ejecutando el metodo [ cargarComponentesVista() ]");
		idZTreeMenu = (Tree) actionObject.getFellow("id" + this.ids
				+ "ZTreeMenu");
		idZTreeChildren = (Treechildren) actionObject.getFellow("id" + this.ids
				+ "ZTreeChildrenL");
		idZMitAccesoDirecto = (Menuitem) actionObject.getFellow("id" + this.ids
				+ "ZMitAccesoDirecto");
		idZTbxCampoBusquedaTreeMenu = (Textbox) actionObject.getFellow("id"
				+ this.ids + "ZTbxCampoBusquedaTreeMenu");
		idZBtnBusquedaTreeMenu = (Button) actionObject.getFellow("id"
				+ this.ids + "ZBtnBusquedaTreeMenu");
	}

	@SuppressWarnings("unchecked")
	public void loadTree() {
		log.info("Ejecutando el metodo [ loadTree() ]");
		List<MyItemTree> listaMenu = null;
		Long padre = null;
		try {
			if (parametros == null)
				parametros = new HashMap<String, Object>();

			parametros.put("PADRE", padre);
			listaMenu = (List<MyItemTree>) ParametrizacionFac.getFacade()
					.obtenerListado(this.consultaArbol, parametros);

			TreeFacadeMenuPorDemanda.getFacade().buildTreePorDemanda(
					idZTreeChildren, listaMenu, "MENU", this.consultaArbolHijos,
					this.ids, true, parametros);

			if (listaMenu.size() > 1) {
				List<Treeitem> listaItem = new LinkedList<Treeitem>(
						idZTreeMenu.getItems());

				idZTreeMenu.setSelectedItem(listaItem.get(1));
			} else {
				idZTreeMenu
						.setSelectedItem((Treeitem) ((Treechildren) idZTreeMenu
								.getChildren().get(1)).getFirstChild());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void setItemTree(Tree tree, String data, Integer campo,
			Long secBuscar) {
		log.info("Ejecutando el metodo [ setItemTree() ]");
		
		try {
			List<String> lpath;
			String path = null;
			String[] nsec = null;
			if (data != null)
				if (data.trim().equals(""))
					loadTree();

			String exp = null;

			if (data != null)
				exp = data.toUpperCase();
			else
				data = null;

			if (!dataAnterior.equals(data) || !campoAnterior.equals(campo)) {
				this.item = null;
			}

			if (secBuscar == 0) {

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				if (campo == 0) {
					parameters.put("codigo", exp.isEmpty() ? null : "%" + exp
							+ "%");
					parameters.put("nombre", null);
				} else {
					parameters.put("codigo", null);
					parameters.put("nombre", exp.isEmpty() ? null : "%" + exp
							+ "%");
				}

				if (this.item == null) {
					parameters.put("OE", 0);
				} else {
					parameters.put("OE", item.getOrdenEstructura());
				}

				this.item = (MyItemTree) ParametrizacionFac.getFacade()
						.obtenerRegistro(this.consultaobtenerListado,
								parameters);

				if (this.item != null) {

					lpath = (List<String>) ParametrizacionFac.getFacade()
							.obtenerListado(this.consultaPath,
									this.item.getId());

					if (lpath.size() > 0) {
						path = lpath.get(0);
						nsec = path.trim().split("/");
					}
					dataAnterior = data;
					campoAnterior = campo;
				}

			} else {

				lpath = (List<String>) ParametrizacionFac.getFacade()
						.obtenerListado(this.consultaPath, secBuscar);

				if (lpath.size() > 0) {
					path = lpath.get(0);
					nsec = path.trim().split("/");
				}
			}

			Treeitem itemSeleccionado = TreeFacadeMenuPorDemanda.getFacade()
					.cargarHijosTreePorDemanda(idZTreeChildren, nsec, this.ids,
							true, parametros);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onAgregarEventos() {

		idZTreeMenu.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					public void onEvent(Event arg0) throws Exception {
						MNZMenuAction c = (MNZMenuAction) actionObject;
						c.cargarVentana(idZTreeMenu.getSelectedItem());

					}
				});

		idZMitAccesoDirecto.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					public void onEvent(Event arg0) throws Exception {

						MNZMenuAction c = (MNZMenuAction) actionObject;
						if (idZTreeMenu.getSelectedItem() != null) {
							MyItemTree myItemTree = TreeFacadeMenuPorDemanda
									.getFacade().getDataItemSelected(
											idZTreeMenu.getSelectedItem());
							c.crearAccesoDirecto(myItemTree);
						} else {
							MyMessageBox
									.show("Debe seleccionar una opci—n de menœ");
						}

					}
				});

		idZTbxCampoBusquedaTreeMenu.addEventListener(Events.ON_OK,
				new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						setItemTree(idZTreeMenu,
								idZTbxCampoBusquedaTreeMenu.getValue(), 1, 0L);

					}
				});

		idZBtnBusquedaTreeMenu.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						setItemTree(idZTreeMenu,
								idZTbxCampoBusquedaTreeMenu.getValue(), 1, 0L);

					}
				});
	}

	public void limpiarSeleccion() {
		idZTreeMenu.clearSelection();
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setActionObject(ActionStandardBorder actionObject) {
		this.actionObject = actionObject;
	}

	public void setConsultaArbol(String consultaArbol) {
		this.consultaArbol = consultaArbol;
	}
	
	public void setConsultaArbolHijos(String consultaArbolHijos) {
		this.consultaArbolHijos = consultaArbolHijos;
	}

	public void setConsultaPath(String consultaPath) {
		this.consultaPath = consultaPath;
	}

	public void setConsultaListado(String consultaListado) {
		this.consultaobtenerListado = consultaListado;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

}
