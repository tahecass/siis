package com.casewaresa.framework.macros;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.casewaresa.framework.action.ActionStandard;
import com.casewaresa.framework.action.ActionStandardBorder;
import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.TreeBeanAbstracto;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.GroupboxTreeFacadePorDemanda;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.macros.contract.IGroupboxTreeAditionalEvent;
import com.casewaresa.framework.macros.contract.ITreeEventPopup;
import com.casewaresa.framework.macros.contract.ITreeFacadePorDemanda;
import com.casewaresa.framework.util.MyItemTree;

/**
 * @author ejulio
 * @name GroupboxTree.java
 * @date 11/04/2011
 * @desc
 */

@SuppressWarnings("unchecked")
public class GroupboxTree extends Groupbox implements IInicializarComponentes {

	/**
	 * 
	 */
	public Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	private Tree tree;
	private Treechildren treechildren;
	private Treeitem itemSelected;
	private Long secItemSelected = null;
	private MyItemTree myItemTree;
	private Radiogroup idZRgrpCampoBusquedaGroupboxTree;
	private Textbox idZTbxCampoBusquedaGroupboxTree;
	private Button idZBtnBusquedaGroupboxTree;

	private Caption idZCaptionGroupboxTree;
	private Component actionObjeto;
	private String ids;
	private String consultaObtenerArbol;
	private String consultaObtenerHijosArbol;
	private String consultaPath;
	private String consultaObtener;
	private String consultaobtenerListado;
	private String consultaActualizarTablasApoyo;

	private Popup idZPopBusquedaGroupboxTree;
	private Map<String, Object> parameter;
	private boolean treeItemRenderMovimiento = false;
	private ITreeFacadePorDemanda render = GroupboxTreeFacadePorDemanda
			.getFacade();
	private List<MyItemTree> listaObjetosRemover = null;
	// por demanda
	private String dataAnterior = "";
	private Integer campoAnterior = -1;
	private TreeBeanAbstracto objetoSeleccionado;
	private boolean isDisable = false;
	private boolean recibeParametros = false;
	private boolean aplicaPopupConsulta = true;
	private boolean busquedaTreeIce = true;
	private boolean aplicaDisabled = false;

	private Button idZBtnActualizarGroupboxTree;
	private Listbox idZLbxOrdenarGroupboxTree;

	public void afterCompose() {
		this.cargarComponentesVista();
		this.onAgregarEventos();
		this.loadTree();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.casewaresa.framework.action.IInicializarComponentes#
	 * cargarComponentesVista()
	 */
	public void cargarComponentesVista() {
		tree = (Tree) actionObjeto.getFellow("id" + this.ids + "ZGroupboxTree");
		treechildren = (Treechildren) actionObjeto.getFellow("id" + this.ids
				+ "ZTreeChildrenGroupboxTree");
		idZCaptionGroupboxTree = (Caption) actionObjeto.getFellow("id"
				+ this.ids + "ZCaptionGroupboxTree");

		if (busquedaTreeIce) {
			idZPopBusquedaGroupboxTree = (Popup) actionObjeto.getFellow("id"
					+ this.ids + "ZPopBusquedaGroupboxTree");
			idZRgrpCampoBusquedaGroupboxTree = (Radiogroup) actionObjeto
					.getFellow("id" + this.ids
							+ "ZRgrpCampoBusquedaGroupboxTree");
			idZTbxCampoBusquedaGroupboxTree = (Textbox) actionObjeto
					.getFellow("id" + this.ids
							+ "ZTbxCampoBusquedaGroupboxTree");
			idZBtnBusquedaGroupboxTree = (Button) actionObjeto.getFellow("id"
					+ this.ids + "ZBtnBusquedaGroupboxTree");
		}

		idZBtnActualizarGroupboxTree = (Button) actionObjeto.getFellow("id"
				+ this.ids + "ZBtnActualizarGroupboxTree");

		idZLbxOrdenarGroupboxTree = (Listbox) actionObjeto.getFellow("id"
				+ this.ids + "ZLbxOrdenarGroupboxTree");
	}

	public void setBusquedaMaestro(Popup popUpBusqueda,
			Radiogroup radioGroupBusqueda, Textbox textboxBusqueda,
			Button buttonBusqueda) {
		log.info("Ejecutando el metodo[setBusquedaMaestro]");

		idZPopBusquedaGroupboxTree = popUpBusqueda;
		idZRgrpCampoBusquedaGroupboxTree = radioGroupBusqueda;
		idZTbxCampoBusquedaGroupboxTree = textboxBusqueda;
		idZBtnBusquedaGroupboxTree = buttonBusqueda;

	}

	public void setBusquedaTreeIce(boolean busquedaTreeIce) {
		this.busquedaTreeIce = busquedaTreeIce;
	}

	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

	public void setRecibeParametros(boolean recibeParametros) {
		this.recibeParametros = recibeParametros;
	}

	public Component getActionObjeto() {
		return actionObjeto;
	}

	public void setTreeItemRenderMovimiento(boolean treeItemRenderMovimiento) {
		this.treeItemRenderMovimiento = treeItemRenderMovimiento;
	}

	public void setActionObjeto(Component actionObjeto) {
		this.actionObjeto = actionObjeto;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * Zona de mensajes Grid
	 * 
	 * @param idZGridHistoricoMensajes
	 * @deprecated Por implementacion de Gritter, borrar metodo
	 */
	@Deprecated
	public void setGridHistoricoMensajes(Grid idZGridHistoricoMensajes) {

	}

	/**
	 * @param idZGbxHistoricoMensajes
	 * @deprecated Por implementacion de Gritter, borrar metodo
	 */
	@Deprecated
	public void setGbxHistoricoMensajes(Groupbox idZGbxHistoricoMensajes) {

	}

	public TreeBeanAbstracto getObjetoSeleccionado() {
		return this.objetoSeleccionado;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisable = isDisabled;
	}

	public Tree getTree() {
		return tree;
	}

	public String getConsultaObtenerArbol() {
		return consultaObtenerArbol;
	}

	public void setConsultaObtenerArbol(String consultaObtenerArbol) {
		this.consultaObtenerArbol = consultaObtenerArbol;
	}

	public String getConsultaObtenerHijosArbol() {
		return consultaObtenerHijosArbol;
	}

	public void setConsultaObtenerHijosArbol(String consultaObtenerHijosArbol) {
		this.consultaObtenerHijosArbol = consultaObtenerHijosArbol;
	}

	public String getConsultaPath() {
		return consultaPath;
	}

	public void setConsultaPath(String consultaPath) {
		this.consultaPath = consultaPath;
	}

	public String getConsultaObtener() {
		return consultaObtener;
	}

	public void setConsultaObtener(String consultaObtener) {
		this.consultaObtener = consultaObtener;
	}

	public String getConsultaActualizarTablasApoyo() {
		return consultaActualizarTablasApoyo;
	}

	public void setConsultaActualizarTablasApoyo(
			String consultaActualizarTablasApoyo) {
		this.consultaActualizarTablasApoyo = consultaActualizarTablasApoyo;
	}

	public void setAplicaDisabled(boolean aplicaDisabled) {
		this.aplicaDisabled = aplicaDisabled;
	}

	public String getConsultaobtenerListado() {
		return consultaobtenerListado;
	}

	public void setConsultaobtenerListado(String consultaobtenerListado) {
		this.consultaobtenerListado = consultaobtenerListado;
	}

	public void setOtherRender(ITreeFacadePorDemanda render) {
		this.render = render;
	}

	public ITreeFacadePorDemanda getOtherRender() {
		return render;
	}

	public void onAgregarEventos() {

		if (aplicaPopupConsulta) {
			idZCaptionGroupboxTree.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							idZPopBusquedaGroupboxTree
									.open(idZCaptionGroupboxTree);
						}
					});
		} else {
			idZCaptionGroupboxTree.setImage("");
			idZCaptionGroupboxTree.setStyle("font-weight: bold");
		}

		tree.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

			public void onEvent(Event arg0) throws Exception {
				onSeleccionarMaestro(tree.getSelectedItem());

			}
		});

		idZTbxCampoBusquedaGroupboxTree.addEventListener(Events.ON_OK,
				new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						setItemTree(tree, idZTbxCampoBusquedaGroupboxTree
								.getValue(), idZRgrpCampoBusquedaGroupboxTree
								.getSelectedIndex(), 0L);
						idZPopBusquedaGroupboxTree.close();
					}
				});

		idZBtnBusquedaGroupboxTree.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						setItemTree(tree, idZTbxCampoBusquedaGroupboxTree
								.getValue(), idZRgrpCampoBusquedaGroupboxTree
								.getSelectedIndex(), 0L);
						idZPopBusquedaGroupboxTree.close();

					}
				});

		idZPopBusquedaGroupboxTree.addEventListener(Events.ON_OPEN,
				new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						idZTbxCampoBusquedaGroupboxTree.setFocus(true);
					}
				});
		if (!busquedaTreeIce) {
			idZCaptionGroupboxTree.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						public void onEvent(Event arg0) throws Exception {
							idZCaptionGroupboxTree
									.setPopup(idZPopBusquedaGroupboxTree);
						}
					});
		}

		idZBtnActualizarGroupboxTree.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onActualizar();
					}
				});

		idZLbxOrdenarGroupboxTree.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onCambiarOrdenCheck();
					}
				});

	}

	public void loadTree() {
		log.info("Ejecutando el metodo[loadTree]");

		List<MyItemTree> lista = null;
		Long padre = null;
		try {
			// consultamos el arbol
			if (parameter == null)
				parameter = new HashMap<String, Object>();

			String orden = "ORDEN_ESTRUCTURA";
			if (idZLbxOrdenarGroupboxTree.getSelectedIndex() == 1)
				orden = "VALOR";
			else if (idZLbxOrdenarGroupboxTree.getSelectedIndex() == 2)
				orden = "NOMBRE";

			log.info("Orden: " + orden);

			parameter.put("ORDEN", orden);
			parameter.put("PADRE", padre);
			lista = (List<MyItemTree>) ParametrizacionFac.getFacade()
					.obtenerListado(this.consultaObtenerArbol, parameter);

			if (listaObjetosRemover != null)
				removerOjetosLista(lista);

			if (!busquedaTreeIce)
				this.render.buildTreePorDemanda(treechildren, lista,
						idZCaptionGroupboxTree.getLabel().toUpperCase(),
						this.consultaObtenerHijosArbol, this.ids,
						this.treeItemRenderMovimiento, parameter);
			else if (recibeParametros) {
				if (aplicaDisabled)
					this.render
							.buildTreePorDemanda(treechildren, lista,
									idZCaptionGroupboxTree.getLabel()
											.toUpperCase(),
									this.consultaObtenerHijosArbol, this.ids,
									this.treeItemRenderMovimiento, isDisable,
									parameter);
				else
					this.render.buildTreePorDemanda(treechildren, lista,
							idZCaptionGroupboxTree.getLabel().toUpperCase(),
							this.consultaObtenerHijosArbol, this.ids,
							this.treeItemRenderMovimiento, parameter);
			} else {
				treechildren.setAttribute("ORDEN", orden);
				this.render.buildTreePorDemanda(treechildren, lista,
						idZCaptionGroupboxTree.getLabel().toUpperCase(),
						this.consultaObtenerHijosArbol, this.ids,
						this.treeItemRenderMovimiento);
			}
			List<Treeitem> listaItem = new LinkedList<Treeitem>(tree.getItems());

			if (lista.size() > 0) {
				onSeleccionarMaestro(listaItem.get(1));
				tree.setSelectedItem(listaItem.get(1));
			} else {
				onSeleccionarMaestro(listaItem.get(0));
				tree.setSelectedItem(listaItem.get(0));
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (actionObjeto instanceof ActionStandard)
				((ActionStandard) actionObjeto).lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			else
				((ActionStandardBorder) actionObjeto)
						.lanzarExcepcion(new Excepcion(
								IExcepcion.EXCEPCION_GENERAL, e));

		}

	}

	public void onConsultarPopupPersonalizado() {
		log.info("Ejecutando el metodo[onConsultarPopup]...");
		if (actionObjeto instanceof ITreeEventPopup) {
			((ITreeEventPopup) actionObjeto).onParametrizarPopup();

			Map<String, Object> mapa = idZTbxCampoBusquedaGroupboxTree
					.getAttributes();
			for (Map.Entry<String, Object> entry : mapa.entrySet()) {
				this.parameter.put(entry.getKey(),
						entry.getValue().equals("") ? null : entry.getValue());
			}

		}
	}

	public void onConsultarPopupPersonalizado(Map<String, Object> parameters) {
		log.info("Ejecutando el metodo[onConsultarPopupPersonalizado]");
		if (actionObjeto instanceof ITreeEventPopup) {
			((ITreeEventPopup) actionObjeto).onParametrizarPopup();

			Map<String, Object> mapa = idZTbxCampoBusquedaGroupboxTree
					.getAttributes();
			for (Map.Entry<String, Object> entry : mapa.entrySet()) {
				parameters.put(entry.getKey(),
						entry.getValue().equals("") ? null : entry.getValue());
			}

		}
	}

	public void onActualizar() {
		log.info("Ejecutando el metodo [ onActualizar ]... ");
		try {

			ParametrizacionFac.getFacade().ejecutarProcedimiento(
					this.getConsultaActualizarTablasApoyo());

			loadTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCambiarOrdenCheck() {
		log.info("Ejecutando el metodo [ onCambiarOrdenCheck ]... ");
		try {

			loadTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onSeleccionarMaestro(Treeitem treeItem) {

		// limpiamos el formulario
		TreeBeanAbstracto objeto = null;

		try {
			this.itemSelected = treeItem;
			this.secItemSelected = new Long(
					((Treecell) ((Treerow) this.itemSelected.getFirstChild())
							.getChildren().get(4)).getLabel());
			this.myItemTree = this.render
					.getDataItemSelected(this.itemSelected);

			if (myItemTree.getId().equals("0")) {
				this.objetoSeleccionado = null;
				if (actionObjeto instanceof IGroupboxTreeAditionalEvent) {
					IGroupboxTreeAditionalEvent actionForMethod = (IGroupboxTreeAditionalEvent) actionObjeto;

					if (actionForMethod.onValidateSeleccion(myItemTree, objeto))
						objetoSeleccionado = null;

				}

			} else {

				Long id = this.myItemTree.getValor() != null ? new Long(
						this.myItemTree.getId()) : null;
				objeto = (TreeBeanAbstracto) ParametrizacionFac.getFacade()
						.obtenerRegistro(this.getConsultaObtener(), id);

				if (objeto != null) {
					objetoSeleccionado = objeto;

					if (actionObjeto instanceof IGroupboxTreeAditionalEvent) {
						IGroupboxTreeAditionalEvent actionForMethod = (IGroupboxTreeAditionalEvent) actionObjeto;

						if (actionForMethod.onValidateSeleccion(myItemTree,
								objeto))
							objetoSeleccionado = null;

					}
				}

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (actionObjeto instanceof ActionStandard)
				((ActionStandard) actionObjeto).lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			else
				((ActionStandardBorder) actionObjeto)
						.lanzarExcepcion(new Excepcion(
								IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	MyItemTree item = null;

	public void setItemTree(Tree tree, String data, Integer campo,
			Long secBuscar) {
		log.info("Ejecutando método [setItemTree]...");

		try {
			List<String> lpath;
			String path = null;
			String[] nsec = null;
			if (data != null)
				if (data.trim().equals("")) {
					if (!busquedaTreeIce) {
						this.parameter = new HashMap<String, Object>();
						this.onConsultarPopupPersonalizado();
					}

					loadTree();
				}

			// Patron de busqueda
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

				if (!busquedaTreeIce) {
					onConsultarPopupPersonalizado(parameters);
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
			Treeitem itemSeleccionado;

			if (busquedaTreeIce) {
				if (recibeParametros) {
					if (aplicaDisabled)
						itemSeleccionado = this.render
								.cargarHijosTreePorDemanda(treechildren, nsec,
										this.ids,
										this.treeItemRenderMovimiento,
										isDisable, parameter);
					else
						itemSeleccionado = this.render
								.cargarHijosTreePorDemanda(treechildren, nsec,
										this.ids,
										this.treeItemRenderMovimiento,
										parameter);

				} else
					itemSeleccionado = this.render.cargarHijosTreePorDemanda(
							treechildren, nsec, this.ids,
							this.treeItemRenderMovimiento);

			} else {

				Map<String, Object> parameters = new HashMap<String, Object>();
				onConsultarPopupPersonalizado(parameters);
				itemSeleccionado = this.render.cargarHijosTreePorDemanda(
						treechildren, nsec, this.ids,
						this.treeItemRenderMovimiento, parameters);

			}

			if (itemSeleccionado != null)
				this.onSeleccionarMaestro(itemSeleccionado);

		} catch (Exception e) {
			e.printStackTrace();
			if (actionObjeto instanceof ActionStandard)
				((ActionStandard) actionObjeto).lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			else
				((ActionStandardBorder) actionObjeto)
						.lanzarExcepcion(new Excepcion(
								IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void refreshItemSelected(TreeBeanAbstracto beansAdstracto)
			throws Exception {

		Treecell tcRenglon = (Treecell) ((Treerow) this.itemSelected
				.getFirstChild()).getChildren().get(0);
		Treecell tcSec = (Treecell) ((Treerow) this.itemSelected
				.getFirstChild()).getChildren().get(4);

		tcRenglon.setLabel(beansAdstracto.toString());
		tcSec.setLabel(beansAdstracto.getPrimaryKey().toString());

		this.itemSelected.invalidate();
		this.itemSelected.applyProperties();
		this.tree.invalidate();
		this.tree.applyProperties();
	}

	public void onSeleccionarItemTree(TreeBeanAbstracto treeBeans)
			throws Exception {

		if (treeBeans.getPrimaryKey() != null) {
			this.secItemSelected = treeBeans.getPrimaryKey();
			this.setItemTree(tree, "", 0, secItemSelected);
		} else {
			loadTree();
		}

	}

	private void removerOjetosLista(List<MyItemTree> listaDatos) {
		for (MyItemTree myItemTree : listaObjetosRemover) {
			listaDatos.remove(myItemTree);
		}
	}

	public Component getFellow(String nameComponent) {
		return this.actionObjeto.getFellow(nameComponent);
	}

	/**
	 * @param listaObjetosRemover
	 *            the listaObjetosRemover to set
	 */
	public void setListaObjetosRemover(List<MyItemTree> listaObjetosRemover) {
		this.listaObjetosRemover = listaObjetosRemover;
	}

	/**
	 * @return the listaObjetosRemover
	 */
	public List<MyItemTree> getListaObjetosRemover() {
		return listaObjetosRemover;
	}

	/**
	 * @param aplicaPopupConsulta
	 *            the aplicaPopupConsulta to set
	 */
	public void setAplicaPopupConsulta(boolean aplicaPopupConsulta) {
		this.aplicaPopupConsulta = aplicaPopupConsulta;
	}

}
