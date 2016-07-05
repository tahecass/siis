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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.facade.TreeFacadePorDemanda;
import com.casewaresa.framework.macros.contract.ITreeAditionalEventBanboxTree;
import com.casewaresa.framework.macros.contract.ITreeFacadePorDemanda;
import com.casewaresa.framework.util.MyItemTree;

/**
 * @author CASEWAREDES04
 * @name BandboxTree.java
 * @date 04/04/2011
 * @desc
 */

public class BandboxTree extends Bandbox implements IInicializarComponentes {

	private static final long serialVersionUID = 637998297163427511L;
	protected static Logger log = Logger.getLogger(BandboxTree.class);

	private Radiogroup idMCRZRgrCriterio;
	private Textbox idMCRZTbxCriterio;
	private Tree idMCRZTree;
	private Treechildren idMCRZTreeChildrens;
	private Caption idMCRZCptEtiqueta;
	private Label idMCRZLblMensajeRegistros;
	private Button idMCRBtnBuscar;

	private IBeanAbstracto objetoClase;
	private Textbox textSecuencia;
	private Button botonBorrar;
	private Map<String, Object> parametros = null;
	private boolean usaNombre = true;

	private MyItemTree myItemTree;
	private Treeitem itemSelected;

	private String consultaPath;
	private String consultaListado;
	private String consultaArbol;
	private String consultaObtener;
	private String consultaHijos;
	private String msgNoHayRegistros;
	private ITreeFacadePorDemanda render = TreeFacadePorDemanda.getFacade();
	private boolean treeItemRenderMovimiento = false;
	private Component action;
	private ITreeAditionalEventBanboxTree actionForMethod;
	private boolean cerrar = true;
	private boolean open = true;
	private String ids;
	private String dataAnterior = "";
	private Integer campoAnterior = -1;
	private boolean recibeParametros = false;

	MyItemTree item = null;
	private boolean usaCodigo = true;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setTreeItemRenderMovimiento(boolean treeItemRenderMovimiento) {
		this.treeItemRenderMovimiento = treeItemRenderMovimiento;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setTreeItemRender
	 * @return void
	 * @param recibe
	 *            el parametro treeItemRender
	 * @descp modifica el atributo treeItemRender
	 */
	public void setOtherRender(ITreeFacadePorDemanda treeItemRender) {
		this.render = treeItemRender;
	}

	/**
	 * @return
	 * @type Método de la clase BandboxTree.java
	 * @name getTreeItemRender
	 * @return treeItemRender
	 * @descp obtiene el valor de treeItemRender
	 */
	public ITreeFacadePorDemanda getOtherRender() {
		return this.render;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name gettSecuencia
	 * @return tSecuencia
	 * @descp obtiene el valor de tSecuencia
	 */
	public Textbox getTextboxSecuencia() {
		return textSecuencia;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name settSecuencia
	 * @return void
	 * @param recibe
	 *            el parametro tSecuencia
	 * @descp modifica el atributo tSecuencia
	 */
	public void setTextboxSecuencia(Textbox tSecuencia) {
		this.textSecuencia = tSecuencia;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getAction
	 * @return action
	 * @descp obtiene el valor de action
	 */
	public Component getActionObject() {
		return action;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setAction
	 * @return void
	 * @param recibe
	 *            el parametro action
	 * @descp modifica el atributo action
	 */
	public void setActionObject(Component action) {
		this.action = action;
	}

	/**
	 * @type Metodo de la clase BandboxFind.java
	 * @name setMsgNoHayRegistros
	 * @return void
	 * @param recibe
	 *            por parametro el mensaje que se muestra cuando la consulta de
	 *            buscar no trae registros.
	 * */
	public void setMsgNoHayRegistros(String msgNoHayRegistros) {
		this.msgNoHayRegistros = msgNoHayRegistros;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.casewaresa.framework.action.IInicializarComponentes#
	 * cargarComponentesVista()
	 */
	public void cargarComponentesVista() {
		idMCRZRgrCriterio = (Radiogroup) this.getFellow("idMCRZRgrCriterio");
		idMCRZTbxCriterio = (Textbox) this.getFellow("idMCRZTbxCriterio");
		idMCRZTree = (Tree) this.getFellow("idMCRZTree");
		idMCRZTreeChildrens = (Treechildren) this
				.getFellow("idMCRZTreeChildrens");
		idMCRZRgrCriterio.getItemAtIndex(1).setVisible(usaNombre);
		idMCRZCptEtiqueta = (Caption) this.getFellow("idMCRZCptEtiqueta");

		idMCRZLblMensajeRegistros = (Label) this
				.getFellow("idMCRZLblMensajeRegistros");
		idMCRBtnBuscar = (Button) this.getFellow("idMCRBtnBuscar");

		if (usaCodigo && usaNombre) {
			idMCRZRgrCriterio.setSelectedIndex(0);
		} else if (!usaCodigo) {
			idMCRZRgrCriterio.setSelectedIndex(1);
			idMCRZRgrCriterio.getItemAtIndex(0).setVisible(false);
		} else if (!usaNombre) {
			idMCRZRgrCriterio.setSelectedIndex(0);
			idMCRZRgrCriterio.getItemAtIndex(1).setVisible(false);
		}

	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getConsultaObtener
	 * @return consultaObtener
	 * @descp obtiene el valor de consultaObtener
	 */
	public String getConsultaObtener() {
		return consultaObtener;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setConsultaObtener
	 * @return void
	 * @param recibe
	 *            el parametro consultaObtener
	 * @descp modifica el atributo consultaObtener
	 */
	public void setConsultaObtener(String consultaObtener) {
		this.consultaObtener = consultaObtener;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getIdMCRZRgrCriterio
	 * @return idMCRZRgrCriterio
	 * @descp obtiene el valor de idMCRZRgrCriterio
	 */
	public Radiogroup getIdMCRZRgrCriterio() {
		return idMCRZRgrCriterio;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getIdMCRZTbxCriterio
	 * @return idMCRZTbxCriterio
	 * @descp obtiene el valor de idMCRZTbxCriterio
	 */
	public Textbox getIdMCRZTbxCriterio() {
		return idMCRZTbxCriterio;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getConsulta
	 * @return consulta
	 * @descp obtiene el valor de consulta
	 */
	public String getConsultaArbol() {
		return consultaArbol;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setConsulta
	 * @return void
	 * @param recibe
	 *            el parametro consulta
	 * @descp modifica el atributo consulta
	 */
	public void setConsultaArbol(String consulta) {
		this.consultaArbol = consulta;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getConsultaHijos() {
		return consultaHijos;
	}

	public void setConsultaHijos(String consultaHijos) {
		this.consultaHijos = consultaHijos;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getConsulta
	 * @return consulta
	 * @descp obtiene el valor de consulta
	 */
	public String getConsultaListado() {
		return consultaListado;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setConsulta
	 * @return void
	 * @param recibe
	 *            el parametro consulta
	 * @descp modifica el atributo consulta
	 */
	public void setConsultaListado(String consultaSec) {
		this.consultaListado = consultaSec;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getConsulta
	 * @return consulta
	 * @descp obtiene el valor de consulta
	 */
	public String getConsultaPath() {
		return consultaPath;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setConsulta
	 * @return void
	 * @param recibe
	 *            el parametro consulta
	 * @descp modifica el atributo consulta
	 */
	public void setConsultaPath(String consultaPathSec) {
		this.consultaPath = consultaPathSec;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getObjetoClase
	 * @return objetoClase
	 * @descp obtiene el valor de objetoClase
	 */
	public IBeanAbstracto getObjetoClase() {
		return objetoClase;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setObjetoClase
	 * @return void
	 * @param recibe
	 *            el parametro objetoClase
	 * @descp modifica el atributo objetoClase
	 */
	public void setObjetoClase(IBeanAbstracto objetoClase) {
		this.objetoClase = objetoClase;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name getParametros
	 * @return parametros
	 * @descp obtiene el valor de parametros
	 */
	public Map<String, Object> getParametros() {
		return parametros;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setParametros
	 * @return void
	 * @param recibe
	 *            el parametro parametros
	 * @descp modifica el atributo parametros
	 */
	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name isCerrar
	 * @return cerrar
	 * @descp obtiene el valor de cerrar
	 */
	public boolean getCerrar() {
		return this.cerrar;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name setCerrar
	 * @return void
	 * @param recibe
	 *            el parametro cerrar
	 * @descp modifica el atributo cerrar
	 */
	public void setCerrar(boolean cerrar) {
		this.cerrar = cerrar;
	}

	public IBeanAbstracto setObjeto(int campo, String criterio,
			IBeanAbstracto obj) {

		if (criterio != null && !criterio.isEmpty()) {
			criterio = "%" + criterio.trim().toUpperCase() + "%";
		}

		if (campo == 0) {
			obj.setCodigo(criterio);
		} else {
			if (campo == 1) {
				obj.setNombre(criterio);
			}
		}

		return obj;
	}

	/**
	 * @type Método de la clase BandboxTree.java
	 * @name esNoObligatorio
	 * @param botonBorrar
	 * @desc Recibe el boton que borra la informacion del Bandbox cuando este no
	 *       es obligatorio
	 */
	public void esNoObligatorio(Button botonBorrar) {
		this.botonBorrar = botonBorrar;
	}

	public void usaElNombre(boolean usaNombr) {
		usaNombre = usaNombr;
	}

	@SuppressWarnings("unchecked")
	public void loadTree() {
		log.info("Ejecutando metodo [ loadTree en BandboxTree ]...");
		// limpia historico de mensajes
		idMCRZTree.clearSelection();
		List<MyItemTree> listaObject = null;
		Long padre = null;
		try {
			if (open) {
				if (parametros != null) {
					parametros.put("PADRE", padre);
					listaObject = (List<MyItemTree>) ParametrizacionFac
							.getFacade().obtenerListado(this.consultaArbol,
									parametros);
					recibeParametros = true;

				} else {
					parametros = new HashMap<String, Object>();
					parametros.put("PADRE", padre);
					listaObject = (List<MyItemTree>) ParametrizacionFac
							.getFacade().obtenerListado(this.consultaArbol,
									parametros);
					recibeParametros = false;
				}

				if (listaObject.size() > 0) {
					idMCRBtnBuscar.setDisabled(false);
					idMCRZTbxCriterio.setDisabled(false);
					idMCRZTree.setVisible(true);
					idMCRZLblMensajeRegistros.setValue("");

					// enviamos el arbol y la lista a la interfaz web para que
					// sea
					// dibujado
					if (recibeParametros) {
						this.render.buildTreePorDemanda(idMCRZTreeChildrens,
								listaObject, this.idMCRZCptEtiqueta.getLabel()
										.toUpperCase(), this.consultaArbol,
								this.ids, this.treeItemRenderMovimiento,
								parametros);
					} else {
						idMCRZTreeChildrens.setAttribute("ORDEN",
								"ORDEN_ESTRUCTURA");// Para que no se genere
													// NULL al momento de
													// cargarse el arbol.
						this.render.buildTreePorDemanda(idMCRZTreeChildrens,
								listaObject, this.idMCRZCptEtiqueta.getLabel()
										.toUpperCase(), this.consultaArbol,
								this.ids, this.treeItemRenderMovimiento);
					}
					// Si el arbol de las unidades tiene datos
					List<Treeitem> listaItem = new LinkedList<Treeitem>(
							idMCRZTree.getItems());
					idMCRZTree.setSelectedItem(listaItem.get(0));

				} else {
					idMCRBtnBuscar.setDisabled(true);
					idMCRZTbxCriterio.setDisabled(true);
					idMCRZTree.setVisible(false);
					idMCRZLblMensajeRegistros.setValue(this.msgNoHayRegistros);
				}
				open = false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void setItemTree(Tree tree, String data, Integer campo,
			Long secBuscar) {

		log.info("Ejecutando método [setItemTree]...");

		try {

			/* Siempre Abierto el Treeitem de la etiqueta */
			if (tree != null
					&& tree.getChildren() != null
					&& !tree.getChildren().isEmpty()
					&& tree.getChildren().get(1) != null
					&& tree.getChildren().get(1).getChildren() != null
					&& !tree.getChildren().get(1).getChildren().isEmpty()
					&& tree.getChildren().get(1).getChildren().get(0) != null
					&& tree.getChildren().get(1).getChildren().get(0) instanceof Treeitem) {
				Treeitem treeitemEtiqueta = (Treeitem) tree.getChildren()
						.get(1).getChildren().get(0);
				treeitemEtiqueta.setOpen(true);
				treeitemEtiqueta.invalidate();
			}

			if (!idMCRZTree.isVisible())
				idMCRZTree.setVisible(true);

			List<String> lpath;
			String path = null;
			String[] nsec = null;
			if (data != null)
				if (data.trim().equals("")) {
					open = true;
					loadTree();
				}
			// Patrón de búsqueda
			String exp = null;

			if (data != null)
				exp = data.toUpperCase();
			else
				data = null;

			if (!dataAnterior.equals(data) || !campoAnterior.equals(campo)) {
				this.item = null;
			}

			if (secBuscar == 0) {

				if (!this.recibeParametros)
					parametros = new HashMap<String, Object>();

				if (campo == 0) {
					parametros.put("codigo", exp.isEmpty() ? null : "%" + exp
							+ "%");
					parametros.put("nombre", null);
				} else {
					parametros.put("codigo", null);
					parametros.put("nombre", exp.isEmpty() ? null : "%" + exp
							+ "%");
				}

				if (this.item == null) {
					parametros.put("OE", 0);
				} else {
					parametros.put("OE", item.getOrdenEstructura());
				}

				this.item = (MyItemTree) ParametrizacionFac.getFacade()
						.obtenerRegistro(this.consultaListado, parametros);

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
				} else {
					idMCRZTree.setVisible(false);
					idMCRZLblMensajeRegistros.setValue(this.msgNoHayRegistros);
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

			if (recibeParametros) {
				log.info("recibe Parametros");
				itemSeleccionado = this.render.cargarHijosTreePorDemanda(
						idMCRZTreeChildrens, nsec, this.ids,
						this.treeItemRenderMovimiento, parametros);
			} else {
				log.info("No recibe Parametros");
				itemSeleccionado = this.render.cargarHijosTreePorDemanda(
						idMCRZTreeChildrens, nsec, this.ids,
						this.treeItemRenderMovimiento);
			}

			this.onSeleccionar(itemSeleccionado);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onSeleccionar(Treeitem treeItem) {
		log.info("Ejecutando el método [ onSeleccionar ]...");

		// limpiamos el formulario

		IBeanAbstracto objeto = null;
		if (action instanceof ITreeAditionalEventBanboxTree)
			actionForMethod = (ITreeAditionalEventBanboxTree) action;
		try {

			this.itemSelected = treeItem;
			this.myItemTree = this.render
					.getDataItemSelected(this.itemSelected);

			if (!this.myItemTree.getId().equals("0")) {

				Long id = this.myItemTree.getValor() != null ? new Long(
						this.myItemTree.getId()) : null;

				objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
						.obtenerRegistro(getConsultaObtener(), id);

				if (actionForMethod != null
						&& actionForMethod.onValidateSeleccion(myItemTree,
								objeto, this.ids)) {

				} else {
					textSecuencia.setValue(objeto.getPrimaryKey().toString());
					textSecuencia.setAttribute("codigo", objeto.getCodigo());
					textSecuencia.setAttribute("nombre", objeto.getNombre());
					textSecuencia.setAttribute("objeto", objeto);

					StringBuffer buffer = new StringBuffer();

					if (objeto.getCodigo() != null) {
						buffer.append("[ ").append(objeto.getCodigo())
								.append(" ]");

					}

					if (objeto.getNombre() != null) {
						buffer.append(objeto.getNombre());
					}

					this.setValue(buffer.toString());

					if (botonBorrar != null) {
						botonBorrar.setVisible(true);
						botonBorrar.setDisabled(false);
					}

					this.cerrar = true;

					treeItem.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {

								public void onEvent(Event arg0)
										throws Exception {
									cerrar();
								}
							});
				}

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onSeleccionar(Long id) {
		log.info("Ejecutando el método [ onSeleccionar ]...");

		// limpiamos el formulario

		IBeanAbstracto objeto = null;
		if (action instanceof ITreeAditionalEventBanboxTree)
			actionForMethod = (ITreeAditionalEventBanboxTree) action;
		try {

			objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
					.obtenerRegistro(getConsultaObtener(), id);

			if (actionForMethod != null
					&& actionForMethod.onValidateSeleccion(null, objeto,
							this.ids)) {

			} else {
				textSecuencia.setValue(objeto.getPrimaryKey().toString());
				textSecuencia.setAttribute("codigo", objeto.getCodigo());
				textSecuencia.setAttribute("nombre", objeto.getNombre());
				textSecuencia.setAttribute("objeto", objeto);

				this.setValue("[ " + objeto.getCodigo() + " ] "
						+ objeto.getNombre());

				if (botonBorrar != null) {
					botonBorrar.setVisible(true);
					botonBorrar.setDisabled(false);
				}

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void cerrar() {

		this.itemSelected.removeEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					public void onEvent(Event arg0) throws Exception {
						cerrar();
					}
				});
		this.close();
	}

	public void onClose() {
		if (cerrar)
			this.close();
	}

	public void onLimpiarCriterio(String value) {
		if (value.length() == 0) {
			idMCRZTree.setVisible(true);
			idMCRZLblMensajeRegistros.setValue("");
		}
	}

	public void setEtiqueta(String etiqueta) {
		if (idMCRZCptEtiqueta != null) {
			idMCRZCptEtiqueta.setLabel(etiqueta);
		} else {
			cargarComponentesVista();
			idMCRZCptEtiqueta.setLabel(etiqueta);
		}

	}

	public void usaCodigo(boolean usaCodigo) {
		this.usaCodigo = usaCodigo;

	}
}