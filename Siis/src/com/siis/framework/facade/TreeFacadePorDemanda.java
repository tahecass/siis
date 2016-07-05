package com.casewaresa.framework.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.casewaresa.framework.macros.contract.ITreeFacadePorDemanda;
import com.casewaresa.framework.util.MyItemTree;
import com.casewaresa.iceberg_cg.facade.ParametrizacionFac;

@SuppressWarnings("unchecked")
public class TreeFacadePorDemanda implements ITreeFacadePorDemanda {

	/** desc: Esta clase es singlenton */
	private static final TreeFacadePorDemanda pTreeFacade = new TreeFacadePorDemanda();

	protected static Logger log = Logger.getLogger(TreeFacadePorDemanda.class);

	private TreeFacadePorDemanda() {
		super();
	}

	public static TreeFacadePorDemanda getFacade() {
		return pTreeFacade;
	}

	/****************************************************************************************/
	/** METODOS DE LA FACHADA **/
	/****************************************************************************************/

	public void buildTreePorDemanda(Treechildren tree,
			List<MyItemTree> listaDatos, String etiqueta, String consulta,
			String ids, boolean treeItemRenderMovimiento) throws Exception {

		Treechildren raiz = new Treechildren();
		tree.getChildren().clear();

		Treeitem ti = new Treeitem();
		Treerow tr = new Treerow();
		Treecell tcValor = new Treecell("");
		Treecell tcPadre = new Treecell("");
		Treecell tcNivel = new Treecell("0");
		Treecell tcLlave = new Treecell("0");
		Treecell tcEtiqueta = new Treecell(etiqueta);

		tr.setAttribute("valor", "");
		tr.setAttribute("padre", "");
		tr.setAttribute("nivel", 0);
		tr.setAttribute("llave", 0);

		tr.appendChild(tcEtiqueta);
		tr.appendChild(tcValor);
		tr.appendChild(tcPadre);
		tr.appendChild(tcNivel);
		tr.appendChild(tcLlave);

		ti.appendChild(tr);
		ti.appendChild(raiz);
		tree.appendChild(ti);

		for (final MyItemTree itemTree : listaDatos) {

			tcEtiqueta = new Treecell(itemTree.getEtiqueta());
			tcEtiqueta.setTooltiptext(itemTree.getEtiqueta());

			if (treeItemRenderMovimiento) {
				if (String.valueOf(itemTree.getOtherValue()).equals("N")) {
					tcEtiqueta.setStyle("color:#BDBDBD;font-weight: bold");
				}
			}

			tcValor = new Treecell(itemTree.getValor() != null ? itemTree
					.getValor().trim() : null);
			tcPadre = new Treecell(itemTree.getPadre() != null ? itemTree
					.getPadre().trim() : null);
			tcNivel = new Treecell(itemTree.getNivel().toString());
			tcLlave = new Treecell(itemTree.getId() != null ? itemTree.getId()
					.trim() : null);

			tr = new Treerow();
			tr.setAttribute("valor", itemTree.getValor());
			tr.setAttribute("padre", itemTree.getPadre());
			tr.setAttribute("nivel", itemTree.getNivel());
			tr.setAttribute("llave", itemTree.getId());
			tr.setAttribute("otherValue", itemTree.getOtherValue());
			tr.setAttribute("numeroDecendientes",
					itemTree.getNumeroDescendientes());

			tr.appendChild(tcEtiqueta);
			tr.appendChild(tcValor);
			tr.appendChild(tcPadre);
			tr.appendChild(tcNivel);
			tr.appendChild(tcLlave);

			ti = new Treeitem();
			ti.setAttribute("Consulta", consulta);
			ti.setAttribute("ORDEN", tree.getAttribute("ORDEN"));
			ti.setId(ids + itemTree.getId());
			ti.appendChild(tr);
			raiz.appendChild(ti);

			if (itemTree.getNumeroDescendientes() > 0) {
				cargarHijosTreePorDemanda(ti, itemTree.getId(), ids,
						treeItemRenderMovimiento);
			}
		}
	}

	public void buildTreePorDemanda(Treechildren tree,
			List<MyItemTree> listaDatos, String etiqueta, String consulta,
			String ids, boolean treeItemRenderMovimiento,
			Map<String, Object> parameter) throws Exception {
		log.info("Ejecutando método [ buildTree(" + tree.getId()
				+ ", No. Reg: " + listaDatos.size() + ") ]... ");

		Treechildren raiz = new Treechildren();
		tree.getChildren().clear();

		Treeitem ti = new Treeitem();
		Treerow tr = new Treerow();
		Treecell tcValor = new Treecell("");
		Treecell tcPadre = new Treecell("");
		Treecell tcNivel = new Treecell("0");
		Treecell tcLlave = new Treecell("0");
		Treecell tcEtiqueta = new Treecell(etiqueta);

		tr.setAttribute("valor", "");
		tr.setAttribute("padre", "");
		tr.setAttribute("nivel", 0);
		tr.setAttribute("llave", 0);

		tr.appendChild(tcEtiqueta);
		tr.appendChild(tcValor);
		tr.appendChild(tcPadre);
		tr.appendChild(tcNivel);
		tr.appendChild(tcLlave);

		ti.appendChild(tr);
		ti.appendChild(raiz);
		tree.appendChild(ti);

		for (final MyItemTree itemTree : listaDatos) {

			tcEtiqueta = new Treecell(itemTree.getEtiqueta());
			tcEtiqueta.setTooltiptext(itemTree.getEtiqueta());

			if (treeItemRenderMovimiento) {
				if (String.valueOf(itemTree.getOtherValue()).equals("N")) {
					tcEtiqueta.setStyle("color:#BDBDBD;font-weight: bold");
				}
			}

			tcValor = new Treecell(itemTree.getValor() != null ? itemTree
					.getValor().trim() : null);
			tcPadre = new Treecell(itemTree.getPadre() != null ? itemTree
					.getPadre().trim() : null);
			tcNivel = new Treecell(itemTree.getNivel().toString());
			tcLlave = new Treecell(itemTree.getId() != null ? itemTree.getId()
					.trim() : null);

			tr = new Treerow();
			tr.setAttribute("valor", itemTree.getValor());
			tr.setAttribute("padre", itemTree.getPadre());
			tr.setAttribute("nivel", itemTree.getNivel());
			tr.setAttribute("llave", itemTree.getId());
			tr.setAttribute("otherValue", itemTree.getOtherValue());
			tr.setAttribute("numeroDecendientes",
					itemTree.getNumeroDescendientes());

			tr.appendChild(tcEtiqueta);
			tr.appendChild(tcValor);
			tr.appendChild(tcPadre);
			tr.appendChild(tcNivel);
			tr.appendChild(tcLlave);

			ti = new Treeitem();
			ti.setAttribute("Consulta", consulta);
			ti.setId(ids + itemTree.getId());
			ti.appendChild(tr);
			raiz.appendChild(ti);

			if (itemTree.getNumeroDescendientes() > 0) {
				ti.setAttribute("OBJETO", parameter.get("OBJETO"));
				cargarHijosTreePorDemanda(ti, itemTree.getId(), ids,
						treeItemRenderMovimiento, parameter);
			}
		}
	}

	public void cargarHijosTreePorDemanda(Treeitem ti, final String sec,
			final String ids, final boolean treeItemRenderMovimiento) {
		final String orden = ti.getAttribute("ORDEN").toString();
		final String consulta = ti.getAttribute("Consulta").toString();
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("ORDEN", orden);
		param.put("PADRE", sec);

		final Treechildren hijo = new Treechildren();
		ti.appendChild(hijo);
		ti.setOpen(false);
		ti.addEventListener("onOpen", new EventListener<Event>() {
			public void onEvent(Event arg0) throws Exception {

				OpenEvent oe = (OpenEvent) arg0;
				if (oe.isOpen()) {
					List<MyItemTree> listaHijos = (List<MyItemTree>) ParametrizacionFac
							.getFacade().obtenerListado(consulta, param);
					hijo.getChildren().clear();
					for (MyItemTree myItemTree : listaHijos) {
						Treeitem tiHijo = new Treeitem();
						Treerow trow = new Treerow();
						Treecell tcEti = new Treecell(myItemTree.getEtiqueta());

						if (treeItemRenderMovimiento) {
							if (String.valueOf(myItemTree.getOtherValue())
									.equals("N")) {
								tcEti.setStyle("color:#BDBDBD;font-weight: bold");
							}
						}

						tcEti.setTooltiptext(myItemTree.getEtiqueta());
						trow.appendChild(tcEti);
						trow.appendChild(new Treecell(
								myItemTree.getValor() != null ? myItemTree
										.getValor().trim() : null));
						trow.appendChild(new Treecell(
								myItemTree.getPadre() != null ? myItemTree
										.getPadre().trim() : null));
						trow.appendChild(new Treecell(myItemTree.getNivel()
								.toString()));
						trow.appendChild(new Treecell(
								myItemTree.getId() != null ? myItemTree.getId()
										.trim() : null));

						trow.setAttribute("valor", myItemTree.getValor());
						trow.setAttribute("padre", myItemTree.getPadre());
						trow.setAttribute("nivel", myItemTree.getNivel());
						trow.setAttribute("llave", myItemTree.getId());
						trow.setAttribute("otherValue",
								myItemTree.getOtherValue());
						trow.setAttribute("numeroDecendientes",
								myItemTree.getNumeroDescendientes());

						tiHijo.appendChild(trow);
						tiHijo.setId(ids + myItemTree.getId());
						tiHijo.setAttribute("Consulta", consulta);
						tiHijo.setAttribute("ORDEN", orden);
						hijo.appendChild(tiHijo);
						if (myItemTree.getNumeroDescendientes() > 0) {
							cargarHijosTreePorDemanda(tiHijo,
									myItemTree.getId(), ids,
									treeItemRenderMovimiento);
						}

					}
				}
			}
		});
	}

	public Treeitem cargarHijosTreePorDemanda(Treechildren padre, String[] sec,
			String ids, boolean treeItemRenderMovimiento) throws Exception {
		log.info("Ejecutando el Metodo cargarHijosTreePorDemanda... ");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ORDEN", padre.getAttribute("ORDEN"));
		Component itemSelected = null;
		List<Component> listItem = padre.getChildren();
		if (sec != null) {
			List<Component> arbol = ((Treechildren) ((Treeitem) listItem.get(0))
					.getChildren().get(1)).getChildren();
			for (int i = (sec.length - 1); i > 0; i--) {
				for (Component itemTree : arbol) {
					if (itemTree.getId().trim().equals(ids + sec[i])) {
						if (i == 1) {
							((Treeitem) itemTree).setSelected(true);
							itemSelected = itemTree;
							break;
						} else {
							param.put("PADRE", sec[i]);
							List<MyItemTree> listaHijos = (List<MyItemTree>) ParametrizacionFac
									.getFacade().obtenerListado(
											((Treeitem) itemTree).getAttribute(
													"Consulta").toString(),
											param);
							Treechildren tchil = (Treechildren) itemTree
									.getChildren().get(1);
							tchil.getChildren().clear();
							for (MyItemTree myItemTree : listaHijos) {
								Treeitem tiHijo = new Treeitem();
								Treerow trow = new Treerow();
								Treecell tcEti = new Treecell(
										myItemTree.getEtiqueta());

								if (treeItemRenderMovimiento) {
									if (String.valueOf(
											myItemTree.getOtherValue()).equals(
											"N")) {
										tcEti.setStyle("color:#BDBDBD;font-weight: bold");
									}
								}

								tcEti.setTooltiptext(myItemTree.getEtiqueta());
								trow.appendChild(tcEti);
								trow.appendChild(new Treecell(myItemTree
										.getValor() != null ? myItemTree
										.getValor().trim() : null));
								trow.appendChild(new Treecell(myItemTree
										.getPadre() != null ? myItemTree
										.getPadre().trim() : null));
								trow.appendChild(new Treecell(myItemTree
										.getNivel().toString()));
								trow.appendChild(new Treecell(myItemTree
										.getId() != null ? myItemTree.getId()
										.trim() : null));

								trow.setAttribute("valor",
										myItemTree.getValor());
								trow.setAttribute("padre",
										myItemTree.getPadre());
								trow.setAttribute("nivel",
										myItemTree.getNivel());
								trow.setAttribute("llave", myItemTree.getId());
								trow.setAttribute("otherValue",
										myItemTree.getOtherValue());
								trow.setAttribute("numeroDecendientes",
										myItemTree.getNumeroDescendientes());

								tiHijo.appendChild(trow);
								tiHijo.setId(ids + myItemTree.getId());
								tiHijo.setAttribute("Consulta",
										itemTree.getAttribute("Consulta"));
								tiHijo.setAttribute("ORDEN", padre.getAttribute("ORDEN"));
								if (padre.hasFellow(ids + myItemTree.getId()))
									padre.getFellow(ids + myItemTree.getId());
								tchil.appendChild(tiHijo);
								if (myItemTree.getNumeroDescendientes() > 0) {
									cargarHijosTreePorDemanda(tiHijo,
											myItemTree.getId(), ids,
											treeItemRenderMovimiento);
								}

							}
							((Treeitem) itemTree).setOpen(true);
							arbol = ((Treechildren) itemTree.getChildren().get(
									1)).getChildren();
							break;
						}
					}
				}
			}
		} else {
			itemSelected = (Treeitem) listItem.get(0);
			((Treeitem) listItem.get(0)).setSelected(true);
		}
		return ((Treeitem) itemSelected);
	}

	public Treeitem cargarHijosTreePorDemanda(Treechildren padre, String[] sec,
			String ids, boolean treeItemRenderMovimiento,
			Map<String, Object> parameter) throws Exception {
		log.info("Ejecutando el Metodo cargarHijosTreePorDemandaParametros... ");
		Component itemSelected = null;
		List<Component> listItem = padre.getChildren();
		if (sec != null) {
			List<Component> arbol = ((Treechildren) ((Treeitem) listItem.get(0))
					.getChildren().get(1)).getChildren();
			for (int i = (sec.length - 1); i > 0; i--) {
				for (Component itemTree : arbol) {
					if (itemTree.getId().trim().equals(ids + sec[i])) {
						if (i == 1) {
							((Treeitem) itemTree).setSelected(true);
							itemSelected = itemTree;
							break;
						} else {
							parameter.put("PADRE", sec[i]);
							List<MyItemTree> listaHijos = (List<MyItemTree>) ParametrizacionFac
									.getFacade().obtenerListado(
											((Treeitem) itemTree).getAttribute(
													"Consulta").toString(),
											parameter);
							Treechildren tchil = (Treechildren) itemTree
									.getChildren().get(1);
							tchil.getChildren().clear();
							for (MyItemTree myItemTree : listaHijos) {
								Treeitem tiHijo = new Treeitem();
								Treerow trow = new Treerow();
								Treecell tcEti = new Treecell(
										myItemTree.getEtiqueta());

								if (treeItemRenderMovimiento) {
									if (String.valueOf(
											myItemTree.getOtherValue()).equals(
											"N")) {
										tcEti.setStyle("color:#BDBDBD;font-weight: bold");
									}
								}

								tcEti.setTooltiptext(myItemTree.getEtiqueta());
								trow.appendChild(tcEti);
								trow.appendChild(new Treecell(myItemTree
										.getValor() != null ? myItemTree
										.getValor().trim() : null));
								trow.appendChild(new Treecell(myItemTree
										.getPadre() != null ? myItemTree
										.getPadre().trim() : null));
								trow.appendChild(new Treecell(myItemTree
										.getNivel().toString()));
								trow.appendChild(new Treecell(myItemTree
										.getId() != null ? myItemTree.getId()
										.trim() : null));

								trow.setAttribute("valor",
										myItemTree.getValor());
								trow.setAttribute("padre",
										myItemTree.getPadre());
								trow.setAttribute("nivel",
										myItemTree.getNivel());
								trow.setAttribute("llave", myItemTree.getId());
								trow.setAttribute("otherValue",
										myItemTree.getOtherValue());
								trow.setAttribute("numeroDecendientes",
										myItemTree.getNumeroDescendientes());

								tiHijo.appendChild(trow);
								tiHijo.setId(ids + myItemTree.getId());
								tiHijo.setAttribute("Consulta",
										itemTree.getAttribute("Consulta"));
								if (padre.hasFellow(ids + myItemTree.getId()))
									padre.getFellow(ids + myItemTree.getId());
								tchil.appendChild(tiHijo);
								if (myItemTree.getNumeroDescendientes() > 0) {
									tiHijo.setAttribute("OBJETO",
											parameter.get("OBJETO"));
									cargarHijosTreePorDemanda(tiHijo,
											myItemTree.getId(), ids,
											treeItemRenderMovimiento, parameter);
								}

							}
							((Treeitem) itemTree).setOpen(true);
							arbol = ((Treechildren) itemTree.getChildren().get(
									1)).getChildren();
							break;
						}
					}
				}
			}
		} else {
			itemSelected = (Treeitem) listItem.get(0);
			((Treeitem) listItem.get(0)).setSelected(true);
		}
		return ((Treeitem) itemSelected);
	}

	public void cargarHijosTreePorDemanda(Treeitem ti, final String sec,
			final String ids, final boolean treeItemRenderMovimiento,
			Map<String, Object> parameter) {
		log.info("Ejecutando el metodo[cargarHijosTreePorDemandaParametros]");
		log.trace("ti ==> " + ti);
		log.trace("sec ==>" + sec);
		log.trace("ids ==> " + ids);
		log.trace("treeItemRenderMovimiento ==>" + treeItemRenderMovimiento);
		log.trace("parameter ==>" + parameter);
		final String consulta = ti.getAttribute("Consulta").toString();
		final Treechildren hijo = new Treechildren();
		ti.appendChild(hijo);
		ti.setOpen(false);
		parameter.put("OBJETO", ti.getAttribute("OBJETO"));
		parameter.put("PADRE", sec);
		final Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(parameter);
		ti.addEventListener("onOpen", new EventListener<Event>() {
			public void onEvent(Event arg0) throws Exception {
				OpenEvent oe = (OpenEvent) arg0;
				if (oe.isOpen()) {
					List<MyItemTree> listaHijos = (List<MyItemTree>) ParametrizacionFac
							.getFacade().obtenerListado(consulta, map);
					hijo.getChildren().clear();
					for (MyItemTree myItemTree : listaHijos) {
						Treeitem tiHijo = new Treeitem();
						Treerow trow = new Treerow();
						Treecell tcEti = new Treecell(myItemTree.getEtiqueta());

						if (treeItemRenderMovimiento) {
							if (String.valueOf(myItemTree.getOtherValue())
									.equals("N")) {
								tcEti.setStyle("color:#BDBDBD;font-weight: bold");
							}
						}

						tcEti.setTooltiptext(myItemTree.getEtiqueta());
						trow.appendChild(tcEti);
						trow.appendChild(new Treecell(
								myItemTree.getValor() != null ? myItemTree
										.getValor().trim() : null));
						trow.appendChild(new Treecell(
								myItemTree.getPadre() != null ? myItemTree
										.getPadre().trim() : null));
						trow.appendChild(new Treecell(myItemTree.getNivel()
								.toString()));
						trow.appendChild(new Treecell(
								myItemTree.getId() != null ? myItemTree.getId()
										.trim() : null));

						trow.setAttribute("valor", myItemTree.getValor());
						trow.setAttribute("padre", myItemTree.getPadre());
						trow.setAttribute("nivel", myItemTree.getNivel());
						trow.setAttribute("llave", myItemTree.getId());
						trow.setAttribute("otherValue",
								myItemTree.getOtherValue());
						trow.setAttribute("numeroDecendientes",
								myItemTree.getNumeroDescendientes());

						tiHijo.appendChild(trow);

						log.info("cargarHijosTreePorDemanda  ids =====> " + ids);
						log.info("cargarHijosTreePorDemanda  myItemTree.getId() =====> "
								+ myItemTree.getId());

						tiHijo.setId(ids + myItemTree.getId());
						tiHijo.setAttribute("Consulta", consulta);
						hijo.appendChild(tiHijo);
						if (myItemTree.getNumeroDescendientes() > 0) {
							tiHijo.setAttribute("OBJETO", map.get("OBJETO"));
							cargarHijosTreePorDemanda(tiHijo,
									myItemTree.getId(), ids,
									treeItemRenderMovimiento, map);
						}

					}
				}
			}
		});
	}

	public MyItemTree getDataItemSelected(Treeitem itemSelected) {
		log.info("Ejecutando método [ getDataItemSelected ]...");
		
		
		
		
		MyItemTree myItemTree = new MyItemTree();
		
		log.info("itemSelected.getFirstChild().getAttribute(llave): "+itemSelected.getFirstChild().getAttribute("llave"));
		log.info("itemSelected.getFirstChild().getAttribute(valor): "+itemSelected.getFirstChild().getAttribute("valor"));
		
		myItemTree.setId(itemSelected.getFirstChild().getAttribute("llave")
				.toString());
		myItemTree.setValor(itemSelected.getFirstChild().getAttribute("valor")
				.toString());
		myItemTree.setEtiqueta(((Treecell) ((Treerow) itemSelected
				.getFirstChild()).getChildren().get(0)).getLabel());
		myItemTree.setNivel(Integer.valueOf(itemSelected.getFirstChild()
				.getAttribute("nivel").toString()));

		if (itemSelected.getFirstChild().getAttribute("otherValue") != null)
			myItemTree.setOtherValue(itemSelected.getFirstChild()
					.getAttribute("otherValue").toString());

		if (itemSelected.getFirstChild().getAttribute("numeroDecendientes") != null)
			myItemTree.setNumeroDescendientes((Integer) itemSelected
					.getFirstChild().getAttribute("numeroDecendientes"));

		return myItemTree;
	}

	@Override
	public void buildTreePorDemanda(Treechildren tree,
			List<MyItemTree> listaDatos, String etiqueta, String consulta,
			String ids, boolean treeItemRenderMovimiento, boolean isDisabled,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void cargarHijosTreePorDemanda(Treeitem ti, String sec, String ids,
			boolean treeItemRenderMovimiento, boolean isDisabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public Treeitem cargarHijosTreePorDemanda(Treechildren padre, String[] sec,
			String ids, boolean treeItemRenderMovimiento, boolean isDisabled,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}