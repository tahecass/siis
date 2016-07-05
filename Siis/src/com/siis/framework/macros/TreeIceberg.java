package com.casewaresa.framework.macros;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.casewaresa.framework.action.ActionStandardBorder;
import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.TreeBeanAbstracto;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.facade.TreeFacadePorDemanda;
import com.casewaresa.framework.macros.contract.ITreeAditionalEvent;
import com.casewaresa.framework.macros.contract.ITreeAditionalEvents;
import com.casewaresa.framework.macros.contract.ITreeEventPopup;
import com.casewaresa.framework.macros.contract.ITreeFacadePorDemanda;
import com.casewaresa.framework.util.MyItemTree;
import com.casewaresa.framework.util.Utilidades;

/**
 * @author msuevis
 * @name TreeFC.java
 * @date 11/04/2011
 * @desc
 */
@SuppressWarnings("unchecked")
public class TreeIceberg extends ActionStandardBorder implements
		IInicializarComponentes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tree tree;
	private Treechildren treechildren;
	private Treeitem itemSelected;
	private Long secItemSelected = null;
	private MyItemTree myItemTree;
	private Long ordenHermanos;
	private String md5;
	private Textbox idZTbxCodigoObjeto;
	private Textbox idZTbxNombreObjeto;
	private Radiogroup idZRgrEstadoObjeto;
	private Label idZLblEtiquetaPredecesor;
	private Label idZLblNombrePredecesor;
	private Textbox idZTbxMD5Obejto;
	private Button idZBtnEditar;
	private Button idZBtnBorrar;
	private Button idZBtnGuardar;
	private Radiogroup idZRgrpCampoBusquedaTreeIceberg;
	private Listbox idZLbxOrdenarTreeIceberg;
	private Textbox idZTbxCampoBusquedaTreeIceberg;
	private Button idZBtnBusquedaTreeIceberg;
	private Button idZBtnNuevoTreeIceberg;
	private Button idZBtnSubirOrdenTreeIceberg;
	private Button idZBtnBajarOrdenTreeIceberg;
	private Button idZBtnSubirNivelTreeIceberg;
	private Button idZBtnBajarNivelTreeIceberg;
	private Button idZBtnActualizarTreeIceberg;

	private Groupbox idGbxFormulario;

	// private North idZNorth;
	private Caption idZCaptionTreeIceberg;
	private ActionStandardBorder actionObjeto;
	private String ids;
	private String consultaObtenerArbol;
	private String consultaObtenerHijosArbol;
	private String consultaPath;
	private String consultaObtener;
	private String consultaobtenerListado;
	private String consultaintercambiarHermanos;
	private String consultaActualizarNivel;
	private String consultaActualizarTablasApoyo;

	private Popup idZPopBusquedaTreeIceberg;
	private Textbox idZTbxOpt;
	private Image idZImgTack;
	private Tabpanel idZTplFormulario;
	private Toolbar idZToolbarTreeIceberg;
	private Map<String, Object> parameter;
	private boolean treeItemRenderMovimiento = false;
	private ITreeFacadePorDemanda render = TreeFacadePorDemanda.getFacade();
	// por demanda
	private String dataAnterior = "";
	private Integer campoAnterior = -1;
	private String mensajeInsercionOk = "";
	private String mensajeEdicionOk = "";
	private String mensajeFormularioVacio = "";
	private String mensajeBorrarOk = "";
	private boolean busquedaTreeIce = true;
	private boolean isLoadTree = true;
	private boolean recibeParametros = false;

	private boolean contieneCodigo = true;
	private boolean contieneNombre = true;
	private boolean visibleToolbarTreeIceberg = true;

	MyItemTree item = null;

	public void afterCompose() {
		this.cargarComponentesVista();
		this.onAgregarEventos();
		this.loadTree();

		if (!contieneCodigo) {
			idZLbxOrdenarTreeIceberg.getItemAtIndex(1).setVisible(false);
			idZRgrpCampoBusquedaTreeIceberg.getItemAtIndex(0).setVisible(false);
		}
		if (!contieneNombre) {
			idZLbxOrdenarTreeIceberg.getItemAtIndex(2).setVisible(false);
			idZRgrpCampoBusquedaTreeIceberg.getItemAtIndex(1).setVisible(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.casewaresa.framework.action.IInicializarComponentes#
	 * cargarComponentesVista()
	 */
	@Override
	public void cargarComponentesVista() {
		tree = (Tree) actionObjeto.getFellow("id" + this.ids + "ZTreeIceberg");
		treechildren = (Treechildren) actionObjeto.getFellow("id" + this.ids
				+ "ZTreeChildrenTreeIceberg");
		idZCaptionTreeIceberg = (Caption) actionObjeto.getFellow("id"
				+ this.ids + "ZCaptionTreeIceberg");
		idZBtnSubirOrdenTreeIceberg = (Button) actionObjeto.getFellow("id"
				+ this.ids + "ZBtnSubirOrdenTreeIceberg");
		idZBtnBajarOrdenTreeIceberg = (Button) actionObjeto.getFellow("id"
				+ this.ids + "ZBtnBajarOrdenTreeIceberg");
		idZBtnSubirNivelTreeIceberg = (Button) actionObjeto.getFellow("id"
				+ this.ids + "ZBtnSubirNivelTreeIceberg");
		idZBtnBajarNivelTreeIceberg = (Button) actionObjeto.getFellow("id"
				+ this.ids + "ZBtnBajarNivelTreeIceberg");
		idZBtnActualizarTreeIceberg = (Button) actionObjeto.getFellow("id"
				+ this.ids + "ZBtnActualizarTreeIceberg");

		idZLbxOrdenarTreeIceberg = (Listbox) actionObjeto.getFellow("id"
				+ this.ids + "ZLbxOrdenarTreeIceberg");

		idZImgTack = (Image) actionObjeto.getFellow("id" + this.ids
				+ "ZImgTack");
		idZToolbarTreeIceberg = (Toolbar) actionObjeto.getFellow("id"
				+ this.ids + "ZToolbarTreeIceberg");

		if (busquedaTreeIce) {
			idZPopBusquedaTreeIceberg = (Popup) actionObjeto.getFellow("id"
					+ this.ids + "ZPopBusquedaTreeIceberg");
			idZRgrpCampoBusquedaTreeIceberg = (Radiogroup) actionObjeto
					.getFellow("id" + this.ids
							+ "ZRgrpCampoBusquedaTreeIceberg");
			idZTbxCampoBusquedaTreeIceberg = (Textbox) actionObjeto
					.getFellow("id" + this.ids + "ZTbxCampoBusquedaTreeIceberg");
			idZBtnBusquedaTreeIceberg = (Button) actionObjeto.getFellow("id"
					+ this.ids + "ZBtnBusquedaTreeIceberg");
		}
		if (!visibleToolbarTreeIceberg)
			idZToolbarTreeIceberg.setVisible(visibleToolbarTreeIceberg);

	}

	public void consultarPorCodigo(Boolean arg) {
		if (!arg) {
			idZRgrpCampoBusquedaTreeIceberg.getFirstChild().setVisible(false);
			idZRgrpCampoBusquedaTreeIceberg.getItemAtIndex(1).setSelected(true);
		}
	}

	public void consultarPorNombre(Boolean arg) {
		if (!arg) {
			idZRgrpCampoBusquedaTreeIceberg.getLastChild().setVisible(false);
			idZRgrpCampoBusquedaTreeIceberg.getItemAtIndex(0).setSelected(true);
		}
	}

	public void disableComponents(Boolean readOnly, Integer nivel) {
		log.info("Ejecutando el método [ disableComponents ]");
		log.debug("disableComponents(readOnly: " + readOnly + ", nivel: "
				+ nivel);
		try {
			// habilitamos los componentes segun la condicion resivida
			if (getGbxFormulario() != null) {
				if (readOnly) {
					Utilidades.deshabilitarCampos(this.getGbxFormulario());
				} else {
					Utilidades.habilitarCampos(this.getGbxFormulario());
				}
			}

			// Validamos que si el nivel es diferente de null; lo cual lo
			// hacemos
			// para las condiciones donde no
			// se necesita cambiarle el estado a los button
			if (nivel != null) {
				// Se evalua si el nivel del treeItem seleccionado es cero
				if (nivel == 0) {
					if (idZBtnEditar != null && idZBtnBorrar != null
							&& idZBtnGuardar != null) {

						idZBtnEditar.setDisabled(readOnly);
						idZBtnBorrar.setDisabled(readOnly);
						idZBtnGuardar.setDisabled(!readOnly);
					}
				} else {
					if (idZBtnEditar != null && idZBtnBorrar != null
							&& idZBtnGuardar != null) {

						idZBtnEditar.setDisabled(!readOnly);
						idZBtnBorrar.setDisabled(!readOnly);
						idZBtnGuardar.setDisabled(readOnly);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getActionObjeto
	 * @return actionObjeto
	 * @descp obtiene el valor de actionObjeto
	 */
	public ActionStandardBorder getActionObjeto() {
		return actionObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZBtnBorrar
	 * @return idZBtnBorrar
	 * @descp obtiene el valor de idZBtnBorrar
	 */
	public Button getBtnBorrar() {
		return idZBtnBorrar;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZBtnEditar
	 * @return idZBtnEditar
	 * @descp obtiene el valor de idZBtnEditar
	 */
	public Button getBtnEditar() {
		return idZBtnEditar;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZBtnGuardar
	 * @return idZBtnGuardar
	 * @descp obtiene el valor de idZBtnGuardar
	 */
	public Button getBtnGuardar() {
		return idZBtnGuardar;
	}

	public Caption getCaptionWest() {
		return idZCaptionTreeIceberg;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getConsultaActualizarNivel
	 * @return consultaActualizarNivel
	 * @descp obtiene el valor de consultaActualizarNivel
	 */
	public String getConsultaActualizarNivel() {
		return consultaActualizarNivel;
	}

	/**
	 * @type Método de la clase TreeIceberg.java
	 * @name getConsultaActualizarTablasApoyo
	 * @return consultaActualizarTablasApoyo
	 * @descp Actualiza tablas de apoyo
	 */
	public String getConsultaActualizarTablasApoyo() {
		return consultaActualizarTablasApoyo;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getConsultaintercambiarHermanos
	 * @return consultaintercambiarHermanos
	 * @descp obtiene el valor de consultaintercambiarHermanos
	 */
	public String getConsultaintercambiarHermanos() {
		return consultaintercambiarHermanos;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getConsultaObtener
	 * @return consultaObtener
	 * @descp obtiene el valor de consultaObtener
	 */
	public String getConsultaObtener() {
		return consultaObtener;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getConsultaObtenerArbol
	 * @return consultaObtenerArbol
	 * @descp obtiene el valor de consultaObtenerArbol
	 */
	public String getConsultaObtenerArbol() {
		return consultaObtenerArbol;
	}

	/**
	 * @type Método de la clase TreeIceberg.java
	 * @name getConsultaObtenerHijosArbol
	 * @return consultaObtenerHijosArbol
	 * @descp obtiene el valor de consultaObtenerHijosArbol
	 */
	public String getConsultaObtenerHijosArbol() {
		return consultaObtenerHijosArbol;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getConsultaobtenerListado
	 * @return consultaobtenerListado
	 * @descp obtiene el valor de consultaobtenerListado
	 */
	public String getConsultaobtenerListado() {
		return consultaobtenerListado;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getConsultaPath
	 * @return consultaPath
	 * @descp obtiene el valor de consultaPath
	 */
	public String getConsultaPath() {
		return consultaPath;
	}

	@Override
	public Component getFellow(String nameComponent) {
		return this.actionObjeto.getFellow(nameComponent);
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdGbxFormulario
	 * @return idGbxFormulario
	 * @descp obtiene el valor de idGbxFormulario
	 */
	public Groupbox getGbxFormulario() {
		return idGbxFormulario;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getId
	 * @return id
	 * @descp obtiene el valor de id
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZTplFormulario
	 * @return idZTplFormulario
	 * @descp obtiene el valor de idZTplFormulario
	 */
	public Tabpanel getIdZTplFormulario() {
		return idZTplFormulario;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZLblEtiquetaPredecesor
	 * @return idZLblEtiquetaPredecesor
	 * @descp obtiene el valor de idZLblEtiquetaPredecesor
	 */
	public Label getLblEtiquetaPredecesor() {
		return idZLblEtiquetaPredecesor;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZLblNombrePredecesor
	 * @return idZLblNombrePredecesor
	 * @descp obtiene el valor de idZLblNombrePredecesor
	 */
	public Label getLblNombrePredecesor() {
		return idZLblNombrePredecesor;
	}

	/**
	 * @return the mensajeBorrarOk
	 */
	public String getMensajeBorrarOk() {
		return mensajeBorrarOk;
	}

	public String getMensajeEdicionOk() {
		return mensajeEdicionOk;
	}

	public String getMensajeFormularioVacio() {
		return mensajeFormularioVacio;
	}

	public String getMensajeInsercionOk() {
		return mensajeInsercionOk;
	}

	public ITreeFacadePorDemanda getOtherRender() {
		return render;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZRgrEstadoObjeto
	 * @return idZRgrEstadoObjeto
	 * @descp obtiene el valor de idZRgrEstadoObjeto
	 */
	public Radiogroup getRgrEstadoObjeto() {
		return idZRgrEstadoObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZTbxCodigoObjeto
	 * @return idZTbxCodigoObjeto
	 * @descp obtiene el valor de idZTbxCodigoObjeto
	 */
	public Textbox getTbxCodigoObjeto() {
		return idZTbxCodigoObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZTbxMD5Obejto
	 * @return idZTbxMD5Obejto
	 * @descp obtiene el valor de idZTbxMD5Obejto
	 */
	public Textbox getTbxMD5Obejto() {
		return idZTbxMD5Obejto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZTbxNombreObjeto
	 * @return idZTbxNombreObjeto
	 * @descp obtiene el valor de idZTbxNombreObjeto
	 */
	public Textbox getTbxNombreObjeto() {
		return idZTbxNombreObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name getIdZTbxOpt
	 * @return idZTbxOpt
	 * @descp obtiene el valor de idZTbxOpt
	 */
	public Textbox getTbxOpcion() {
		return idZTbxOpt;
	}

	public Tree getTree() {
		return tree;
	}

	public boolean isLoadTree() {
		return isLoadTree;
	}

	public void loadTree() {
		log.info("Ejecutando el metodo[loadTree]");

		if (this.isLoadTree) {

			List<MyItemTree> lista = null;
			Long padre = null;
			try {
				// consultamos el arbol
				if (parameter == null) {
					parameter = new HashMap<String, Object>();
				}

				String orden = "ORDEN_ESTRUCTURA";
				if (idZLbxOrdenarTreeIceberg.getSelectedIndex() == 1)
					orden = "VALOR";
				else if (idZLbxOrdenarTreeIceberg.getSelectedIndex() == 2)
					orden = "NOMBRE";

				log.info("Orden: " + orden);

				parameter.put("ORDEN", orden);
				parameter.put("PADRE", padre);
				lista = (List<MyItemTree>) ParametrizacionFac.getFacade()
						.obtenerListado(this.consultaObtenerArbol, parameter);

				if (!busquedaTreeIce) {
					this.render.buildTreePorDemanda(treechildren, lista,
							idZCaptionTreeIceberg.getLabel().toUpperCase(),
							this.consultaObtenerHijosArbol, this.ids,
							this.treeItemRenderMovimiento, parameter);
				} else if (recibeParametros) {
					this.render.buildTreePorDemanda(treechildren, lista,
							idZCaptionTreeIceberg.getLabel().toUpperCase(),
							this.consultaObtenerHijosArbol, this.ids,
							this.treeItemRenderMovimiento, parameter);
				} else {
					treechildren.setAttribute("ORDEN", orden);
					this.render.buildTreePorDemanda(treechildren, lista,
							idZCaptionTreeIceberg.getLabel().toUpperCase(),
							this.consultaObtenerHijosArbol, this.ids,
							this.treeItemRenderMovimiento);
				}

				List<Treeitem> listaItem = new LinkedList<Treeitem>(
						tree.getItems());

				if (lista.size() > 0) {
					onSeleccionarMaestro(listaItem.get(1));
					tree.setSelectedItem(listaItem.get(1));
				} else {
					onSeleccionarMaestro(listaItem.get(0));
					tree.setSelectedItem(listaItem.get(0));
				}

			} catch (Exception e) {
				e.printStackTrace();
				super.lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			}

		}
	}

	public void onAgregarEventos() {
		log.info("Ejecutando el metodo [onAgregarEventos...]");

		idZCaptionTreeIceberg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						idZPopBusquedaTreeIceberg.open(idZCaptionTreeIceberg);
					}
				});

		idZImgTack.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						log.info("Click en el tack");
						actionObjeto.onClickTack("id" + ids);
						actionObjeto.invalidate();
					}
				});

		tree.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				onSeleccionarMaestro(tree.getSelectedItem());

			}
		});

		idZTbxCampoBusquedaTreeIceberg.addEventListener(Events.ON_OK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						setItemTree(tree, idZTbxCampoBusquedaTreeIceberg
								.getValue(), idZRgrpCampoBusquedaTreeIceberg
								.getSelectedIndex(), 0L);
						idZPopBusquedaTreeIceberg.close();

					}
				});

		idZBtnBusquedaTreeIceberg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						setItemTree(tree, idZTbxCampoBusquedaTreeIceberg
								.getValue(), idZRgrpCampoBusquedaTreeIceberg
								.getSelectedIndex(), 0L);
						idZPopBusquedaTreeIceberg.close();

					}
				});

		idZBtnSubirOrdenTreeIceberg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onSubirOrden();
					}
				});

		idZBtnBajarOrdenTreeIceberg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onBajarOrden();
					}
				});

		idZBtnSubirNivelTreeIceberg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onSubirNivel();
					}
				});

		idZBtnBajarNivelTreeIceberg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onBajarNivel();
					}
				});

		idZBtnActualizarTreeIceberg.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onActualizar();
					}
				});

		idZLbxOrdenarTreeIceberg.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onCambiarOrdenCheck();
					}
				});

		if (idZBtnNuevoTreeIceberg != null) {
			idZBtnNuevoTreeIceberg.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							onNuevoMaestro(tree.getSelectedItem());
						}
					});
		}

		if (idZBtnBorrar != null) {
			idZBtnBorrar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							onBorrarMaestro(tree.getSelectedItem());
						}
					});
		}

		if (idZBtnGuardar != null) {
			idZBtnGuardar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							onGuardarMaestro();
						}
					});
		}
		idZPopBusquedaTreeIceberg.addEventListener(Events.ON_OPEN,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						idZTbxCampoBusquedaTreeIceberg.setFocus(true);
					}
				});
		if (!busquedaTreeIce) {
			idZCaptionTreeIceberg.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							idZCaptionTreeIceberg
									.setPopup(idZPopBusquedaTreeIceberg);
						}
					});
		}

	}

	public void onBajarNivel() {
		log.info("Ejecutando el metodo [ onBajarNivel ]... ");
		ITreeAditionalEvent actionForMethod = null;
		try {

			if (treechildren.getChildren().size() > 0) {
				Treeitem itemAnterior = (Treeitem) this.itemSelected
						.getPreviousSibling();

				if (actionObjeto instanceof ITreeAditionalEvent) {
					actionForMethod = (ITreeAditionalEvent) actionObjeto;
				}

				// si itemAnterior es null indica que
				// es el primero y no se puede bajar
				// de nivel
				if (itemAnterior != null) {
					// si Treechildren() retorna null
					// indica que no tiene hijos
					Long objAct = new Long(
							((Treecell) ((Treerow) this.itemSelected
									.getFirstChild()).getChildren().get(4))
									.getLabel());

					Long objPred = new Long(
							((Treecell) ((Treerow) itemAnterior.getFirstChild())
									.getChildren().get(4)).getLabel());

					if (actionForMethod == null
							|| !actionForMethod.onValidateBajarNivel(objAct,
									objPred)) {

						HashMap<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("SECUENCIA", objAct);
						parametros.put("SEC_PREDECESOR", objPred);
						// null para que el package lo
						// tome como el √∫ltimo hermano
						parametros.put("ORDEN_HERMANOS", null);

						ParametrizacionFac.getFacade().ejecutarProcedimiento(
								this.getConsultaActualizarNivel(), parametros);

						loadTree();
						this.setItemTree(tree, "", 0, objAct);

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void onBajarOrden() {
		log.info("Ejecutando el método [ onBajarOrden ]... ");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ITreeAditionalEvent actionForMethod = null;
		try {

			if (treechildren.getChildren().size() > 0) {
				Treeitem itemSiguiente = (Treeitem) this.itemSelected
						.getNextSibling();

				if (actionObjeto instanceof ITreeAditionalEvent) {
					actionForMethod = (ITreeAditionalEvent) actionObjeto;
				}

				// si itemSiguiente es null indica que
				// no tiene hermano siguiente
				if (itemSiguiente != null) {
					Long objAct = new Long(
							((Treecell) ((Treerow) this.itemSelected
									.getFirstChild()).getChildren().get(4))
									.getLabel());
					Long objSig = new Long(
							((Treecell) ((Treerow) itemSiguiente
									.getFirstChild()).getChildren().get(4))
									.getLabel());

					if (actionForMethod == null
							|| !actionForMethod.onValidateBajarOrden(objAct,
									objSig)) {

						parameters.put("SECUENCIA_1", objAct);
						parameters.put("SECUENCIA_2", objSig);

						ParametrizacionFac.getFacade().ejecutarProcedimiento(
								this.consultaintercambiarHermanos, parameters);

						loadTree();
						this.setItemTree(tree, "", 0, objAct);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
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
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void onCambiarOrdenCheck() {
		log.info("Ejecutando el metodo [ onCambiarOrdenCheck ]... ");
		try {
			if (idZLbxOrdenarTreeIceberg.getSelectedIndex() != 0)
				habilitarBotones(true);
			else
				habilitarBotones(false);

			loadTree();
		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	private void onBorrarMaestro(Treeitem treeItem) {
		log.info("--------------------------------->onBorrarMaestro<<<");
		String llave = treeItem != null ? ((Treecell) (treeItem.getTreerow())
				.getChildren().get(0)).getLabel() : null;
		log.info("--------------------------------->" + llave);

		String[] llaveArray = llave.split("] ");
		log.info("--------------------------------->" + llave);
		String llaveNat = llave;
		String nombre = llave;
		if (llaveArray.length > 1) {
			llaveNat = llaveArray[0].substring(1);
			log.info("---------------------------SEC------>"
					+ ((Treecell) (treeItem.getTreerow()).getChildren().get(4))
							.getLabel());
			nombre = llaveArray[1];
		}

		try {

			this.itemSelected = treeItem;

			Treeitem treeItemPadre = this.itemSelected.getParentItem();

			this.myItemTree = this.render
					.getDataItemSelected(this.itemSelected);

			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("ID", this.myItemTree.getId());
			parametros.put("llaveNat", llaveNat);
			parametros.put("nombre", nombre);
			parametros.put("secItemSelected", this.secItemSelected.toString());
			parametros.put("md5", this.md5);

			if (actionObjeto instanceof ITreeAditionalEvent) {
				ITreeAditionalEvent actionForMethod = (ITreeAditionalEvent) actionObjeto;
				if (actionForMethod.onBorrarMaestro(parametros)) {

					this.itemSelected = treeItemPadre;
					this.secItemSelected = new Long(
							((Treecell) ((Treerow) treeItemPadre
									.getFirstChild()).getChildren().get(4))
									.getLabel());
					loadTree();
					actionObjeto.setMensajeHistoricoGritter(
							IConstantes.ESTADO_BORRAR_OK, getMensajeBorrarOk(),
							llaveNat, nombre);
				}
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_FORMATO_DE_NUMERO, e));
		} catch (RuntimeException e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_TIEMPO_DE_EJECUCION, e));
		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	public void onConsultarPopupPersonalizado() {
		log.info("Ejecutando el metodo[onConsultarPopup]...");
		if (actionObjeto instanceof ITreeEventPopup) {
			((ITreeEventPopup) actionObjeto).onParametrizarPopup();

			Map<String, Object> mapa = idZTbxCampoBusquedaTreeIceberg
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

			Map<String, Object> mapa = idZTbxCampoBusquedaTreeIceberg
					.getAttributes();
			for (Map.Entry<String, Object> entry : mapa.entrySet()) {
				parameters.put(entry.getKey(),
						entry.getValue().equals("") ? null : entry.getValue());
			}

		}
	}

	public void onGuardarMaestro() {
		log.info("Ejecutando el metodo[onGuardarMaestro]...");
		try {
			String operation = idZTbxOpt.getValue();
			Long orderSiblings = new Long(
					(this.itemSelected.getTreechildren() != null ? this.itemSelected
							.getTreechildren().getChildren().size()
							: 0) + 1);
			log.info("ordenHermanos: " + orderSiblings);

			if (super.validarFormulario(this.getGbxFormulario())) {
				log.info("Formulario Validado!!!");

				ITreeAditionalEvent actionForMethod = (ITreeAditionalEvent) actionObjeto;

				TreeBeanAbstracto beanAdstracto = actionForMethod
						.onGuardarMaestro(orderSiblings);

				if (beanAdstracto != null) {
					this.loadTree();
					this.setItemTree(tree, beanAdstracto.getCodigo(), 0,
							beanAdstracto.getPrimaryKey());

					// idZGbxTamanoMensaje.setHeight("auto");
					if (operation.equals(IConstantes.INSERTAR)) {
						actionObjeto.setMensajeHistoricoGritter(
								IConstantes.ESTADO_INSERCION_OK,
								this.mensajeInsercionOk,
								beanAdstracto.getCodigo(),
								beanAdstracto.getNombre());

					} else if (operation.equals(IConstantes.EDITAR)) {

						actionObjeto.setMensajeHistoricoGritter(
								IConstantes.ESTADO_EDICION_OK,
								this.mensajeEdicionOk,
								beanAdstracto.getCodigo(),
								beanAdstracto.getNombre());
					}

				}

			} else {
				actionObjeto.setMensajeHistoricoGritter(
						IConstantes.ESTADO_INSERCION_ERROR,
						this.mensajeFormularioVacio, "", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	public void onNuevoMaestro(Treeitem treeItem) {
		log.info("Ejecutando el metodo[onNuevoMaestro]");
		Utilidades.ocultarMostrarHijos(idZTplFormulario, true);

		Utilidades.limpiarCampos(this.getGbxFormulario());

		if (idZRgrEstadoObjeto != null) {
			this.idZRgrEstadoObjeto.setSelectedIndex(0);
		}

		idZBtnEditar.setDisabled(true);
		idZBtnBorrar.setDisabled(true);
		idZBtnGuardar.setDisabled(false);
		idZTbxOpt.setValue(IConstantes.INSERTAR);
		try {

			// Cargamos el lov seleccionada en el dto de tipo MyItemTree
			this.itemSelected = treeItem;

			this.myItemTree = this.render.getDataItemSelected(itemSelected);
			log.info("this.myItemTree.getNivel(): "
					+ this.myItemTree.getNivel());

			if (myItemTree.getId().equals("0")) {
				if (idZLblEtiquetaPredecesor != null) {
					idZLblEtiquetaPredecesor.setVisible(false);
				}
				if (idZLblNombrePredecesor != null) {
					idZLblNombrePredecesor.setValue(null);
				}
				if (idZTbxCodigoObjeto != null) {
					idZTbxCodigoObjeto.setAttribute("secuencia", null);
				}
				if (idZTbxMD5Obejto != null) {
					idZTbxMD5Obejto.setValue("");
				}
			} else {
				TreeBeanAbstracto objeto = (TreeBeanAbstracto) ParametrizacionFac
						.getFacade().obtenerRegistro(this.getConsultaObtener(),
								new Long(this.myItemTree.getId()));

				if (idZTbxCodigoObjeto != null) {
					idZTbxCodigoObjeto.setAttribute("predecesor",
							objeto.getPrimaryKey());
				}
				if (idZLblEtiquetaPredecesor != null) {
					idZLblEtiquetaPredecesor.setVisible(true);
				}

				String etiquetaPadre = "[" + objeto.getCodigo() + "] "
						+ objeto.getNombre();
				if (etiquetaPadre.length() > 20) {
					etiquetaPadre = etiquetaPadre.substring(0, 20) + "...";
				}
				if (idZLblNombrePredecesor != null) {
					idZLblNombrePredecesor.setTooltiptext(etiquetaPadre);
				}
				if (idZLblNombrePredecesor != null) {
					idZLblNombrePredecesor.setValue(etiquetaPadre);
				}

			}

			this.disableComponents(false, null);

			if (actionObjeto instanceof ITreeAditionalEvent) {
				ITreeAditionalEvent actionForMethod = (ITreeAditionalEvent) actionObjeto;
				actionForMethod.onNuevoMaestro(this.myItemTree.getNivel());
			}

			// Deshabilitamos los componentes del formulario teniendo en cuenta
			// el nivel del treItem seleccionado
			if (idZTbxCodigoObjeto != null) {
				idZTbxCodigoObjeto.setFocus(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	public void onSeleccionarItemTree(TreeBeanAbstracto treeBeans)
			throws Exception {
		log.info("Ejecutando el metodo[onSeleccionarItemTree]");

		if (treeBeans.getPrimaryKey() != null) {
			this.secItemSelected = treeBeans.getPrimaryKey();
			this.setItemTree(tree, "", 0, secItemSelected);
		} else {
			loadTree();
		}

	}

	public void onSeleccionarMaestro(Treeitem treeItem) {

		log.info("Ejecutando el método [ onSeleccionarMaestro ]...");
		// limpiamos el formulario

		if (this.getGbxFormulario() != null) {
			Utilidades.limpiarCampos(this.getGbxFormulario());
			Utilidades.deshabilitarCampos(this.getGbxFormulario());
		}

		String nombrePredecesor = null;
		TreeBeanAbstracto objeto = null;

		actionObjeto.onSeleccionarClickTack("id" + ids);

		try {
			this.itemSelected = treeItem;

			this.secItemSelected = new Long(
					((Treecell) ((Treerow) this.itemSelected.getFirstChild())
							.getChildren().get(4)).getLabel());
			log.info(secItemSelected);
			this.myItemTree = this.render
					.getDataItemSelected(this.itemSelected);

			if (myItemTree.getId().equals("0")) {

				if (idZLblNombrePredecesor != null) {
					idZLblNombrePredecesor.setValue(null);
				}

				if (idZLblEtiquetaPredecesor != null) {
					idZLblEtiquetaPredecesor.setVisible(false);
				}

				if (idZTbxCodigoObjeto != null) {
					idZTbxCodigoObjeto.setRawValue(null);
					idZTbxCodigoObjeto.setAttribute("secuencia", null);
					idZTbxCodigoObjeto.setAttribute("predecesor", null);
				}

				if (idZTplFormulario != null) {
					Utilidades.ocultarMostrarHijos(idZTplFormulario, false);
				}

				if (idZRgrEstadoObjeto != null) {
					idZRgrEstadoObjeto.setSelectedIndex(0);
				}

				if (idZBtnNuevoTreeIceberg != null) {
					idZBtnNuevoTreeIceberg.setDisabled(false);
				}

				if (idZBtnEditar != null && idZBtnBorrar != null
						& idZBtnGuardar != null) {
					idZBtnEditar.setDisabled(true);
					idZBtnBorrar.setDisabled(true);
					idZBtnGuardar.setDisabled(true);
				}
				if (actionObjeto instanceof ITreeAditionalEvent) {
					ITreeAditionalEvent actionForMethod = (ITreeAditionalEvent) actionObjeto;
					actionForMethod.onValidateSeleccionTreeInicial();
				}

				if (actionObjeto instanceof ITreeAditionalEvents) {
					ITreeAditionalEvents actionForMethod = (ITreeAditionalEvents) actionObjeto;
					actionForMethod.onValidateSeleccion(myItemTree, null);

				}

			} else {
				if (idZBtnNuevoTreeIceberg != null) {
					idZBtnNuevoTreeIceberg.setDisabled(false);
				}

				if (idZTplFormulario != null) {
					Utilidades.ocultarMostrarHijos(idZTplFormulario, true);
				}
				Long id = this.myItemTree.getValor() != null ? new Long(
						this.myItemTree.getId()) : null;

				objeto = (TreeBeanAbstracto) ParametrizacionFac.getFacade()
						.obtenerRegistro(this.getConsultaObtener(), id);
				log.info("--------------------------------->" + objeto);

				if (objeto != null) {

					if (idZTbxCodigoObjeto != null) {
						idZTbxCodigoObjeto.setValue(objeto.getCodigo());
						idZTbxCodigoObjeto.setAttribute("secuencia",
								objeto.getPrimaryKey());
						idZTbxCodigoObjeto.setAttribute("predecesor", objeto
								.getPredecesor() != null ? objeto
								.getPredecesor().getPrimaryKey() : null);
					}

					if (objeto.getPredecesor() != null) {
						nombrePredecesor = objeto.getPredecesor().getNombre();
					}

					if (idZLblEtiquetaPredecesor != null
							&& idZLblNombrePredecesor != null) {
						if (nombrePredecesor == null) {
							idZLblEtiquetaPredecesor.setVisible(false);
							idZLblNombrePredecesor.setValue(null);
						} else {
							idZLblEtiquetaPredecesor.setVisible(true);

							String etiquetaPadre = "["
									+ objeto.getPredecesor().getCodigo() + "] "
									+ nombrePredecesor;

							if (etiquetaPadre.length() > 20) {
								etiquetaPadre = etiquetaPadre.substring(0, 20)
										+ "...";
							}

							idZLblNombrePredecesor
									.setTooltiptext(etiquetaPadre);
							idZLblNombrePredecesor.setValue(etiquetaPadre);
						}
					}

					this.ordenHermanos = objeto.getOrdenHermanos();

					if (idZTbxCodigoObjeto != null) {
						idZTbxCodigoObjeto.setAttribute("ordenHermanos",
								this.ordenHermanos);
					}

					if (idZTbxNombreObjeto != null) {
						idZTbxNombreObjeto.setValue(objeto.getNombre());
					}

					if (idZRgrEstadoObjeto != null) {
						idZRgrEstadoObjeto.setSelectedIndex(objeto.getEstado()
								.equals("A") ? 0 : 1);
					}

					// Registro md5 del la unidad
					// seleccionada
					this.md5 = objeto.getMD5();
					if (idZTbxMD5Obejto != null) {
						idZTbxMD5Obejto.setValue(md5);
					}

					// Deshabilitamos los componentes del formulario teniendo en
					// cuenta
					// el nivel del treItem seleccionado
					this.disableComponents(true, this.myItemTree.getNivel());

					if (actionObjeto instanceof ITreeAditionalEvent) {
						ITreeAditionalEvent actionForMethod = (ITreeAditionalEvent) actionObjeto;

						actionForMethod.onValidateSelecionTree(objeto);
						log.info("----------------------------------------->"
								+ objeto.getPrimaryKey());
						actionForMethod.onCargarDatosFormulario(objeto);

					}

					if (actionObjeto instanceof ITreeAditionalEvents) {
						ITreeAditionalEvents actionForMethod = (ITreeAditionalEvents) actionObjeto;
						actionForMethod.onValidateSeleccion(myItemTree, objeto);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void onSubirNivel() {
		log.info("Ejecutando el método [ onSubirNivel ]... ");

		ITreeAditionalEvent actionForMethod = null;
		Treeitem item = null;
		Treeitem itemPadre = null;
		Treeitem itemAbuelo = null;

		if (treechildren.getChildren().size() > 0) {
			item = this.tree.getSelectedItem();
			itemPadre = item.getParentItem();
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			Long fondoAct = null;
			Long fondoAbu = null;

			if (actionObjeto instanceof ITreeAditionalEvent) {
				actionForMethod = (ITreeAditionalEvent) actionObjeto;
			}

			try {
				// si itemPadre es null indica que no
				// tiene padres la unidad
				if (itemPadre != null) {
					Long fondoPad = new Long(
							((Treecell) ((Treerow) itemPadre.getChildren().get(
									0)).getChildren().get(4)).getLabel());

					if (fondoPad != 0) {
						fondoAct = new Long(
								((Treecell) ((Treerow) item.getChildren()
										.get(0)).getChildren().get(4))
										.getLabel());

						parametros.put("SECUENCIA", fondoAct);
						itemAbuelo = itemPadre.getParentItem();

						if (itemAbuelo != null) {
							fondoAbu = new Long(
									((Treecell) ((Treerow) itemAbuelo
											.getChildren().get(0))
											.getChildren().get(4)).getLabel());
							parametros.put("SEC_PREDECESOR",
									fondoAbu != 0 ? fondoAbu : null);
						} else {
							parametros.put("SEC_PREDECESOR", null);
						}

						if (actionForMethod == null
								|| !actionForMethod.onValidateSubirNivel(
										fondoAct, fondoAbu)) {

							// null para que el package lo
							// tome como el √∫ltimo hermano
							parametros.put("ORDEN_HERMANOS", null);

							ParametrizacionFac.getFacade()
									.ejecutarProcedimiento(
											this.getConsultaActualizarNivel(),
											parametros);

							loadTree();
							this.setItemTree(tree, "", 0, fondoAct);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				super.lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			}
		}
	}

	public void onSubirOrden() {
		log.info("Ejecutando el método [ onSubirOrden ]... ");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		ITreeAditionalEvent actionForMethod = null;
		try {

			if (treechildren.getChildren().size() > 0) {

				Treeitem itemAnterior = (Treeitem) this.itemSelected
						.getPreviousSibling();

				if (actionObjeto instanceof ITreeAditionalEvent) {
					actionForMethod = (ITreeAditionalEvent) actionObjeto;
				}

				// si itemAnterior null indica que es
				// el primero
				if (itemAnterior != null) {
					Long objAct = new Long(
							((Treecell) ((Treerow) this.itemSelected
									.getFirstChild()).getChildren().get(4))
									.getLabel());
					Long objAnt = new Long(
							((Treecell) ((Treerow) itemAnterior.getFirstChild())
									.getChildren().get(4)).getLabel());

					if (actionForMethod == null
							|| !actionForMethod.onValidateSubirOrden(objAct,
									objAnt)) {

						parameters.put("SECUENCIA_1", objAct);
						parameters.put("SECUENCIA_2", objAnt);

						ParametrizacionFac.getFacade().actualizarRegistro(
								this.getConsultaintercambiarHermanos(),
								parameters);

						loadTree();
						this.setItemTree(tree, "", 0, objAct);

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void refreshItemSelected(TreeBeanAbstracto beansAdstracto)
			throws Exception {
		log.info("Ejecutando el método [ refreshItemSelected ]...");

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

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setActionObjeto
	 * @return void
	 * @param recibe
	 *            el parametro actionObjeto
	 * @descp modifica el atributo actionObjeto
	 */
	public void setActionObjeto(ActionStandardBorder actionObjeto) {
		this.actionObjeto = actionObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZBtnBorrar
	 * @return void
	 * @param recibe
	 *            el parametro idZBtnBorrar
	 * @descp modifica el atributo idZBtnBorrar
	 */
	public void setBtnBorrar(Button idZBtnBorrar) {
		this.idZBtnBorrar = idZBtnBorrar;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZBtnEditar
	 * @return void
	 * @param recibe
	 *            el parametro idZBtnEditar
	 * @descp modifica el atributo idZBtnEditar
	 */
	public void setBtnEditar(Button idZBtnEditar) {
		this.idZBtnEditar = idZBtnEditar;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZBtnGuardar
	 * @return void
	 * @param recibe
	 *            el parametro idZBtnGuardar
	 * @descp modifica el atributo idZBtnGuardar
	 */
	public void setBtnGuardar(Button idZBtnGuardar) {
		this.idZBtnGuardar = idZBtnGuardar;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZBtnEditar
	 * @return void
	 * @param recibe
	 *            el parametro idZBtnEditar
	 * @descp modifica el atributo idZBtnEditar
	 */
	public void setBtnNuevo(Button idZBtnNuevo) {
		this.idZBtnNuevoTreeIceberg = idZBtnNuevo;
	}

	public void setBusquedaMaestro(Popup popUpBusqueda,
			Radiogroup radioGroupBusqueda, Textbox textboxBusqueda,
			Button buttonBusqueda) {
		log.info("Ejecutando el metodo[setBusquedaMaestro]");
		idZPopBusquedaTreeIceberg = popUpBusqueda;
		idZRgrpCampoBusquedaTreeIceberg = radioGroupBusqueda;
		idZTbxCampoBusquedaTreeIceberg = textboxBusqueda;
		idZBtnBusquedaTreeIceberg = buttonBusqueda;

	}

	public void setBusquedaTreeIce(boolean busquedaTreeIce) {
		this.busquedaTreeIce = busquedaTreeIce;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setConsultaActualizarNivel
	 * @return void
	 * @param recibe
	 *            el parametro consultaActualizarNivel
	 * @descp modifica el atributo consultaActualizarNivel
	 */
	public void setConsultaActualizarNivel(String consultaActualizarNivel) {
		this.consultaActualizarNivel = consultaActualizarNivel;
	}

	/**
	 * @type Método de la clase TreeIceberg.java
	 * @name setConsultaActualizarTablasApoyo
	 * @return void
	 * @param recibe
	 *            el parametro consultaActualizarTablasApoyo
	 * @descp modifica el atributo consultaActualizarTablasApoyo
	 */
	public void setConsultaActualizarTablasApoyo(
			String consultaActualizarTablasApoyo) {
		this.consultaActualizarTablasApoyo = consultaActualizarTablasApoyo;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setConsultaintercambiarHermanos
	 * @return void
	 * @param recibe
	 *            el parametro consultaintercambiarHermanos
	 * @descp modifica el atributo consultaintercambiarHermanos
	 */
	public void setConsultaintercambiarHermanos(
			String consultaintercambiarHermanos) {
		this.consultaintercambiarHermanos = consultaintercambiarHermanos;
	}

	/**
	 * @type método de la clase TreeIceberg.java
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
	 * @type método de la clase TreeIceberg.java
	 * @name setConsultaObtenerArbol
	 * @return void
	 * @param recibe
	 *            el parametro consultaObtenerArbol
	 * @descp modifica el atributo consultaObtenerArbol
	 */
	public void setConsultaObtenerArbol(String consultaObtenerArbol) {
		this.consultaObtenerArbol = consultaObtenerArbol;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setConsultaObtenerHijosArbol
	 * @return void
	 * @param recibe
	 *            el parametro consultaObtenerHijosArbol
	 * @descp modifica el atributo consultaObtenerHijosArbol
	 */
	public void setConsultaObtenerHijosArbol(String consultaObtenerHijosArbol) {
		this.consultaObtenerHijosArbol = consultaObtenerHijosArbol;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setConsultaobtenerListado
	 * @return void
	 * @param recibe
	 *            el parametro consultaobtenerListado
	 * @descp modifica el atributo consultaobtenerListado
	 */
	public void setConsultaobtenerListado(String consultaobtenerListado) {
		this.consultaobtenerListado = consultaobtenerListado;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setConsultaPath
	 * @return void
	 * @param recibe
	 *            el parametro consultaPath
	 * @descp modifica el atributo consultaPath
	 */
	public void setConsultaPath(String consultaPath) {
		this.consultaPath = consultaPath;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setDisabledBtnBajarNivel
	 * @param disabled
	 * @desc metodo que permite habilitar o deshabilitar el boton bajar nivel
	 */
	public void setDisabledBtnBajarNivel(boolean disabled) {
		this.idZBtnBajarNivelTreeIceberg.setDisabled(disabled);
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setDisabledBtnBajarOrden
	 * @param disabled
	 * @desc metodo que permite habilitar o deshabilitar el boton bajar orden
	 */
	public void setDisabledBtnBajarOrden(boolean disabled) {
		this.idZBtnBajarOrdenTreeIceberg.setDisabled(disabled);
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setDisabledBtnSubirNivel
	 * @param disabled
	 * @desc metodo que permite habilitar o deshabilitar el boton subir nivel
	 */
	public void setDisabledBtnSubirNivel(boolean disabled) {
		this.idZBtnSubirNivelTreeIceberg.setDisabled(disabled);
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setDisabledBtnSubirOrden
	 * @param disabled
	 * @desc metodo que permite habilitar o deshabilitar el boton subir orden
	 */
	public void setDisabledBtnSubirOrden(boolean disabled) {
		this.idZBtnSubirOrdenTreeIceberg.setDisabled(disabled);
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdGbxFormulario
	 * @return void
	 * @param recibe
	 *            el parametro idGbxFormulario
	 * @descp modifica el atributo idGbxFormulario
	 */
	public void setGbxFormulario(Groupbox idGbxFormulario) {
		this.idGbxFormulario = idGbxFormulario;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setId
	 * @return void
	 * @param recibe
	 *            el parametro id
	 * @descp modifica el atributo id
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setItemTree(Tree tree, String data, Integer campo,
			Long secBuscar) {
		log.info("Ejecutando método [setItemTree]...");
		isLoadTree = true;

		try {
			if (treechildren.getChildren().size() > 0) {
				List<String> lpath;
				String path = null;
				String[] nsec = null;
				if (data != null) {
					if (data.trim().equals("")) {
						if (!busquedaTreeIce) {
							this.parameter = new HashMap<String, Object>();
							this.onConsultarPopupPersonalizado();
						}
						// loadTree( );
					}
				}

				// Patron de busqueda
				String exp = null;

				if (data != null) {
					exp = data.toUpperCase();
				} else {
					data = null;
				}

				if (!dataAnterior.equals(data) || !campoAnterior.equals(campo)) {
					this.item = null;
				}

				if (secBuscar == 0) {
					if (!this.recibeParametros) {
						parameter = new HashMap<String, Object>();
					}

					if (campo == 0) {
						parameter.put("codigo", exp.isEmpty() ? null : "%"
								+ exp + "%");
						parameter.put("nombre", null);
					} else {
						parameter.put("codigo", null);
						parameter.put("nombre", exp.isEmpty() ? null : "%"
								+ exp + "%");
					}

					if (this.item == null) {
						parameter.put("OE", 0);
					} else {
						parameter.put("OE", item.getOrdenEstructura());
					}

					if (!busquedaTreeIce) {
						onConsultarPopupPersonalizado(parameter);
					}

					this.item = (MyItemTree) ParametrizacionFac.getFacade()
							.obtenerRegistro(this.consultaobtenerListado,
									parameter);

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

				String orden = "ORDEN_ESTRUCTURA";
				if (idZLbxOrdenarTreeIceberg.getSelectedIndex() == 1)
					orden = "VALOR";
				else if (idZLbxOrdenarTreeIceberg.getSelectedIndex() == 2)
					orden = "NOMBRE";

				log.info("Orden: " + orden);

				parameter.put("ORDEN", orden);

				if (busquedaTreeIce) {
					if (recibeParametros) {
						itemSeleccionado = this.render
								.cargarHijosTreePorDemanda(treechildren, nsec,
										this.ids,
										this.treeItemRenderMovimiento,
										parameter);

					} else {
						treechildren.setAttribute("ORDEN", orden);
						itemSeleccionado = this.render
								.cargarHijosTreePorDemanda(treechildren, nsec,
										this.ids, this.treeItemRenderMovimiento);
					}
				} else {
					Map<String, Object> parameters = new HashMap<String, Object>();
					onConsultarPopupPersonalizado(parameters);
					itemSeleccionado = this.render.cargarHijosTreePorDemanda(
							treechildren, nsec, this.ids,
							this.treeItemRenderMovimiento, parameters);

				}

				if (itemSeleccionado != null) {
					this.onSeleccionarMaestro(itemSeleccionado);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZLblEtiquetaPredecesor
	 * @return void
	 * @param recibe
	 *            el parametro idZLblEtiquetaPredecesor
	 * @descp modifica el atributo idZLblEtiquetaPredecesor
	 */
	public void setLblEtiquetaPredecesor(Label idZLblEtiquetaPredecesor) {
		this.idZLblEtiquetaPredecesor = idZLblEtiquetaPredecesor;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZLblNombrePredecesor
	 * @return void
	 * @param recibe
	 *            el parametro idZLblNombrePredecesor
	 * @descp modifica el atributo idZLblNombrePredecesor
	 */
	public void setLblNombrePredecesor(Label idZLblNombrePredecesor) {
		this.idZLblNombrePredecesor = idZLblNombrePredecesor;
	}

	public void setLoadTree(boolean isLoadTree) {
		this.isLoadTree = isLoadTree;
	}

	/**
	 * @param mensajeBorrarOk
	 *            the mensajeBorrarOk to set
	 */
	public void setMensajeBorrarOk(String mensajeBorrarOk) {
		this.mensajeBorrarOk = mensajeBorrarOk;
	}

	public void setMensajeEdicionOk(String mensajeEdicionOk) {
		this.mensajeEdicionOk = mensajeEdicionOk;
	}

	public void setMensajeFormularioVacio(String mensajeFormularioVacio) {
		this.mensajeFormularioVacio = mensajeFormularioVacio;
	}

	public void setMensajeInsercionOk(String mensajeInsercionOk) {
		this.mensajeInsercionOk = mensajeInsercionOk;
	}

	public void setOtherRender(ITreeFacadePorDemanda render) {
		this.render = render;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setParameter
	 * @return void
	 * @param recibe
	 *            el parametro parameter
	 * @descp modifica el atributo parameter
	 */
	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

	public void setRecibeParametros(boolean recibeParametros) {
		this.recibeParametros = recibeParametros;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZRgrEstadoObjeto
	 * @return void
	 * @param recibe
	 *            el parametro idZRgrEstadoObjeto
	 * @descp modifica el atributo idZRgrEstadoObjeto
	 */
	public void setRgrEstadoObjeto(Radiogroup idZRgrEstadoObjeto) {
		this.idZRgrEstadoObjeto = idZRgrEstadoObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZTplFormulario
	 * @return void
	 * @param recibe
	 *            el parametro idZTplFormulario
	 * @descp modifica el atributo idZTplFormulario
	 */
	public void setTabFormulario(Tabpanel idZTplFormulario) {
		this.idZTplFormulario = idZTplFormulario;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZTbxCodigoObjeto
	 * @return void
	 * @param recibe
	 *            el parametro idZTbxCodigoObjeto
	 * @descp modifica el atributo idZTbxCodigoObjeto
	 */
	public void setTbxCodigoObjeto(Textbox idZTbxCodigoObjeto) {
		this.idZTbxCodigoObjeto = idZTbxCodigoObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZTbxMD5Obejto
	 * @return void
	 * @param recibe
	 *            el parametro idZTbxMD5Obejto
	 * @descp modifica el atributo idZTbxMD5Obejto
	 */
	public void setTbxMD5Obejto(Textbox idZTbxMD5Obejto) {
		this.idZTbxMD5Obejto = idZTbxMD5Obejto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZTbxNombreObjeto
	 * @return void
	 * @param recibe
	 *            el parametro idZTbxNombreObjeto
	 * @descp modifica el atributo idZTbxNombreObjeto
	 */
	public void setTbxNombreObjeto(Textbox idZTbxNombreObjeto) {
		this.idZTbxNombreObjeto = idZTbxNombreObjeto;
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setIdZTbxOpt
	 * @return void
	 * @param recibe
	 *            el parametro idZTbxOpt
	 * @descp modifica el atributo idZTbxOpt
	 */
	public void setTbxOpcion(Textbox idZTbxOpt) {
		this.idZTbxOpt = idZTbxOpt;
	}

	public void setToolbar() {
		log.info("Ejecutando el metodo [setToolbar...]");

		if (!idZBtnBajarNivelTreeIceberg.isVisible()
				&& !idZBtnBajarOrdenTreeIceberg.isVisible()
				&& (idZBtnNuevoTreeIceberg != null ? !idZBtnNuevoTreeIceberg
						.isVisible() : !idZBtnBajarOrdenTreeIceberg.isVisible())
				&& !idZBtnSubirNivelTreeIceberg.isVisible()
				&& !idZBtnSubirOrdenTreeIceberg.isVisible()) {
			idZToolbarTreeIceberg.setHeight("25px");
		}
	}

	private void habilitarBotones(boolean estado) {
		idZBtnSubirOrdenTreeIceberg.setDisabled(estado);
		idZBtnBajarOrdenTreeIceberg.setDisabled(estado);
		idZBtnSubirNivelTreeIceberg.setDisabled(estado);
		idZBtnBajarNivelTreeIceberg.setDisabled(estado);
	}

	/**
	 * @type método de la clase TreeIceberg.java
	 * @name setTreeItemRender
	 * @return void
	 * @param recibe
	 *            el parametro treeItemRender
	 * @descp modifica el atributo treeItemRender
	 */
	public void setTreeItemRenderMovimiento(boolean treeItemRenderMovimiento) {
		this.treeItemRenderMovimiento = treeItemRenderMovimiento;
	}

	/**
	 * 
	 * @type método de la clase TreeIceberg.java
	 * @name setVisibleBotonBajarNivel
	 * @param isVisible
	 * @desc Permtie hacer visible el visible o no el Boton Bajar Nivel de
	 *       acuerdo al parametro
	 */
	public void setVisibleBotonBajarNivel(boolean isVisible) {
		idZBtnBajarNivelTreeIceberg.setVisible(isVisible);
	}

	/**
	 * 
	 * @type método de la clase TreeIceberg.java
	 * @name setVisibleBotonBajarOrden
	 * @param isVisible
	 * @desc Permtie hacer visible el visible o no el Boton Bajar Orden de
	 *       acuerdo al parametro
	 */
	public void setVisibleBotonBajarOrden(boolean isVisible) {
		idZBtnBajarOrdenTreeIceberg.setVisible(isVisible);
	}

	/**
	 * 
	 * @type método de la clase TreeIceberg.java
	 * @name setVisibleBotonSubirNivel
	 * @param isVisible
	 * @desc Permtie hacer visible el visible o no el Boton Subir Nivel de
	 *       acuerdo al parametro
	 */
	public void setVisibleBotonSubirNivel(boolean isVisible) {
		idZBtnSubirNivelTreeIceberg.setVisible(isVisible);
	}

	/**
	 * 
	 * @type método de la clase TreeIceberg.java
	 * @name setVisibleBotonSubirOrden
	 * @param isVisivle
	 * @desc Permtie hacer visible el visible o no el Boton Subir Orden de
	 *       acuerdo al parametro
	 */
	public void setVisibleBotonSubirOrden(boolean isVisivle) {
		idZBtnSubirOrdenTreeIceberg.setVisible(isVisivle);
	}

	public boolean isContieneCodigo() {
		return contieneCodigo;
	}

	public void setContieneCodigo(boolean contieneCodigo) {
		this.contieneCodigo = contieneCodigo;
	}

	public boolean isContieneNombre() {
		return contieneNombre;
	}

	public void setContieneNombre(boolean contieneNombre) {
		this.contieneNombre = contieneNombre;
	}

	public boolean isVisibleToolbarTreeIceberg() {
		return visibleToolbarTreeIceberg;
	}

	public void setVisibleToolbarTreeIceberg(boolean visibleToolbarTreeIceberg) {
		this.visibleToolbarTreeIceberg = visibleToolbarTreeIceberg;
	}

}
