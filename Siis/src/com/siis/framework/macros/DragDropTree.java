package com.casewaresa.framework.macros;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import com.casewaresa.framework.action.ActionStandardBorder;
import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.dto.IDetalleGrupo;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.TreeFacadeGruposPorDemanda;
import com.casewaresa.framework.macros.contract.ITreeFacadePorDemanda;
import com.casewaresa.framework.util.MyItemTree;
import com.casewaresa.iceberg_cg.facade.ParametrizacionFac;

/**
 * @author casewaredes02
 * @name DragDropTree.java
 * @date 26/04/2011
 * @desc
 */

public class DragDropTree extends ActionStandardBorder implements
	IInicializarComponentes {

    private static final long serialVersionUID = 9062045715775943397L;

    protected static Logger log = Logger.getLogger(DragDropTree.class);

    private String metodoLoadTree;
    private String metodoGuardar;
    private String metodoObtener;
    private String metodoObtenerDetalle;
    private String metodoActualizar;
    private String metodoEliminar;
    private String metodoListadoDetalle;
    private String metodoObtenerDetalleByComponente;
    private String metodoObtenerPadresIncluidos;
    private String metodoObtenerPadresExcluidos;
    private String msgNoExisteRegistro;
    private String msgNoSeleccionado;
    private String msgNoIncluido;
    private String msgInsercionErrorExcluido;
    private String msgInsercionErrorIncluido;
    private String msgInsercionOk;
    private String msgEdicionOk;
    private String msgBorrarOk;

    private Tree tree;
    private Listbox incluye;
    private Listbox excluye;
    private String id;
    private Image botonIncluir;
    private Image botonQuitarIncluir;
    private Image botonExcluir;
    private Image botonQuitarExcluir;
    private Window popup;
    private CKeditor editor;
    private Html html;
    private Menupopup menu;
    private Button boton;
    private Menuitem menuItemVer;
    private Menuitem menuItemEditar;
    private A cerrar;

    private String estadoActual;
    private Long secuenciaComponente;
    private Long secuenciaDetalle;
    private String operacion;

    private Class<?> clazz;
    private Class<?> objeto;
    private IBeanAbstracto grupo;
    private IBeanAbstracto dtoComponente;
    private ActionStandardBorder action;

    // private North norte;
    private String md5;
    private boolean sw = true;
    private boolean deshabilitarDragDropTree;
    private boolean editar;
    private Listheader encabezadoIncluye;
    private Listheader encabezadoExcluye;
    List<MyItemTree> listaComponentes = null;
    private int cont, cont2 = 0;
    private String metodoObtenerHijosDirectos;
    private ITreeFacadePorDemanda render = TreeFacadeGruposPorDemanda
	    .getFacade();
    private Treechildren treechildren;
    private boolean index;
    private String metodoObtenerPath;
    private Caption caption;
    private Tab tabIncluidos;

    private Tab tabExcluye;

    private Tab tabIncluye;

    private Tabbox tabboxComponenteSouth;
    private Tabbox tabboxComponenteCenter;
    private Menuitem menuItemIncluir;
    private Rows rowsDetalle;
    private Grid gridDetalle;
    private String metodoObtenerIncluidos;
    private IDetalleGrupo dtoEditar;

    /**
     * @type Método de la clase DragDropTree.java
     * @name agregarEventos
     * @desc agrega los eventos a los componentes del dragDropTree Component
     */
    public void agregarEventos() {
	log.info(" Ejecutando el metodo [ agregarEventos ]... ");

	tree.addEventListener(Events.ON_DROP, new EventListener<Event>() {

	    @Override
	    public void onEvent(Event arg0) throws Exception {
		DropEvent ev = (DropEvent) arg0;
		onQuitarComponente(ev.getDragged());
	    }
	});

	incluye.addEventListener(Events.ON_DROP, new EventListener<Event>() {

	    @Override
	    public void onEvent(Event arg0) throws Exception {
		DropEvent ev = (DropEvent) arg0;
		onMover(incluye, ev.getDragged());

	    }
	});
	excluye.addEventListener(Events.ON_DROP, new EventListener<Event>() {

	    @Override
	    public void onEvent(Event arg0) throws Exception {
		DropEvent ev = (DropEvent) arg0;
		onMover(excluye, ev.getDragged());

	    }
	});

	botonExcluir.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			if (!deshabilitarDragDropTree && editar) {
			    onAgregarComponente(excluye, tree.getSelectedItem());
			}

		    }
		});

	botonQuitarExcluir.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			if (!deshabilitarDragDropTree && editar) {
			    onQuitarComponente(excluye);
			}
		    }
		});

	botonIncluir.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			if (!deshabilitarDragDropTree && editar) {
			    onAgregarComponente(incluye, tree.getSelectedItem());
			}

		    }
		});

	botonQuitarIncluir.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			if (!deshabilitarDragDropTree && editar) {
			    onQuitarComponente(incluye);
			}
		    }
		});

	boton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
	    @Override
	    public void onEvent(Event arg0) throws Exception {
		onGuardarDetalleGrupo();
		popup.setVisible(false);

	    }
	});

	menuItemEditar.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			action.getWest().invalidate();
			onEditar(dtoEditar, 1);
			html.setVisible(false);
			editor.setVisible(true);
			boton.setVisible(true);
			popup.doPopup();
			popup.setVisible(true);

		    }
		});

	menuItemVer.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {

			onEditar(dtoEditar, 1);
			html.setVisible(true);
			editor.setVisible(false);
			boton.setVisible(false);
			popup.doPopup();
			popup.setVisible(true);
			cerrar.setDisabled(false);

		    }
		});

	cerrar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
	    @Override
	    public void onEvent(Event arg0) throws Exception {
		popup.setVisible(false);

	    }
	});

	tabboxComponenteSouth.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			tabboxComponenteCenter
				.setSelectedIndex(tabboxComponenteSouth
					.getSelectedIndex());

		    }
		});

	tabIncluidos.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			tabboxComponenteCenter.setSelectedIndex(tabIncluidos
				.getIndex());
			onListarDetalleGrupos();

		    }
		});

	menuItemIncluir.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			onMover(incluye, excluye.getSelectedItem());
			tabboxComponenteSouth.setSelectedIndex(0);
			tabboxComponenteCenter.setSelectedIndex(0);
		    }
		});

	tabExcluye.addEventListener(Events.ON_DROP, new EventListener<Event>() {
	    @Override
	    public void onEvent(Event arg0) throws Exception {
		DropEvent ev = (DropEvent) arg0;
		onMover(excluye, ev.getDragged());
		tabboxComponenteSouth.setSelectedIndex(1);
		tabboxComponenteCenter.setSelectedIndex(1);

	    }
	});
	tabIncluye.addEventListener(Events.ON_DROP, new EventListener<Event>() {

	    @Override
	    public void onEvent(Event arg0) throws Exception {
		DropEvent ev = (DropEvent) arg0;
		onMover(incluye, ev.getDragged());
		tabboxComponenteSouth.setSelectedIndex(0);
		tabboxComponenteCenter.setSelectedIndex(0);

	    }
	});

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.casewaresa.framework.action.IInicializarComponentes#
     * cargarComponentesVista()
     */
    @Override
    public void cargarComponentesVista() {
	log.info("Ejecutando el método [ cargarComponentesVista ]... ");

	tree = (Tree) action.getFellow("id" + id + "ZTreeComponente");
	incluye = (Listbox) action.getFellow("id" + id + "ZLbxIncluye");
	excluye = (Listbox) action.getFellow("id" + id + "ZLbxExcluye");
	botonIncluir = (Image) action.getFellow("id" + id + "ZImgIncluir");
	botonExcluir = (Image) action.getFellow("id" + id + "ZImgExcluir");
	botonQuitarExcluir = (Image) action.getFellow("id" + id
		+ "ZImgQuitarExcluir");
	botonQuitarIncluir = (Image) action.getFellow("id" + id
		+ "ZImgQuitarIncluir");
	caption = (Caption) action.getFellow("id" + id + "ZCaptionComponente");
	popup = (Window) action.getFellow("id" + id + "ZWinDescripcion");
	editor = (CKeditor) popup.getFellow("id" + id + "ZCKDescripcion");
	html = (Html) popup.getFellow("id" + id + "ZHtmlDescripcion");
	menu = (Menupopup) action.getFellow("id" + id + "ZMenuDescripcion");
	boton = (Button) popup.getFellow("id" + id + "ZBtnAgregarDescripcion");
	menuItemVer = (Menuitem) action.getFellow("id" + id
		+ "ZMenuItemVerDescripcion");
	menuItemEditar = (Menuitem) action.getFellow("id" + id
		+ "ZMenuItemEditarDescripcion");//
	cerrar = (A) popup.getFellow("id" + id + "ZACerrarDescripcion");
	encabezadoIncluye = (Listheader) action.getFellow("id" + id
		+ "ZLhdIncluye");
	encabezadoExcluye = (Listheader) action.getFellow("id" + id
		+ "ZLhdExcluye");
	treechildren = (Treechildren) action.getFellow("id" + id
		+ "ZTreeChildrenTree");

	tabIncluye = (Tab) action.getFellow("id" + id + "ZTabIncluye");
	tabExcluye = (Tab) action.getFellow("id" + id + "ZTabExcluye");
	tabIncluidos = (Tab) action.getFellow("id" + id + "ZTabIncluidos");
	tabboxComponenteSouth = (Tabbox) action.getFellow("id" + id
		+ "ZTabboxComponenteSouth");
	tabboxComponenteCenter = (Tabbox) action.getFellow("id" + id
		+ "ZTabboxComponenteCenter");
	menuItemIncluir = (Menuitem) action.getFellow("id" + id
		+ "ZMenuItemIncluir");
	gridDetalle = (Grid) action.getFellow("id" + id
		+ "ZGridDetalleComponente");
	rowsDetalle = (Rows) action.getFellow("id" + id
		+ "ZRowsDetalleComponente");

	incluye.getItems().clear();
	excluye.getItems().clear();
	tree.clear();
	tabboxComponenteSouth.setSelectedIndex(0);
	tabboxComponenteCenter.setSelectedIndex(tabboxComponenteSouth
		.getSelectedIndex());
	if (grupo != null) {
	    this.deshabilitarDragDropTree = grupo.getEstado().equals("A") ? false
		    : true;
	    if (deshabilitarDragDropTree || !editar) {
		botonIncluir.setSrc("/imagenes/flecha_der_peq_des.png");
		botonQuitarIncluir.setSrc("/imagenes/flecha_izq_peq_des.png");
		botonExcluir.setSrc("/imagenes/flecha_der_peq_des.png");
		botonQuitarExcluir.setSrc("/imagenes/flecha_izq_peq_des.png");
	    } else if (!deshabilitarDragDropTree && editar) {
		botonIncluir.setSrc("/imagenes/flecha_der_peq.png");
		botonQuitarIncluir.setSrc("/imagenes/flecha_izq_peq.png");
		botonExcluir.setSrc("/imagenes/flecha_der_peq.png");
		botonQuitarExcluir.setSrc("/imagenes/flecha_izq_peq.png");
	    }
	    onConsultarDetalle();

	    loadTree();

	} else {
	    deshabilitarDragDropTree = true;
	    botonIncluir.setSrc("/imagenes/flecha_der_peq_des.png");
	    botonQuitarIncluir.setSrc("/imagenes/flecha_izq_peq_des.png");
	    botonExcluir.setSrc("/imagenes/flecha_der_peq_des.png");
	    botonQuitarExcluir.setSrc("/imagenes/flecha_izq_peq_des.png");
	}

	if (sw) {
	    agregarEventos();
	    sw = false;
	}

    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name cargarDatosMovimiento
     * @return
     * @desc carga los datos de los hijos directos de un nodo con movimiento NO
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> cargarDatosMovimiento() {
	log.info(" Ejecutando el metodo [ cargarDatosMovimiento ]... ");
	List<Long> listaRama = null;
	List<IDetalleGrupo> listaDetalles = new LinkedList<IDetalleGrupo>();
	Map<String, Object> parametros = new HashMap<String, Object>();

	try {
	    listaRama = (List<Long>) ParametrizacionFac.getFacade()
		    .obtenerListado(metodoObtenerHijosDirectos, dtoComponente);
	    log.info("HIJOS: " + listaRama);
	    if (listaRama != null) {

		for (Long secu : listaRama) {
		    IDetalleGrupo dto = (IDetalleGrupo) clazz.newInstance();
		    IBeanAbstracto component = (IBeanAbstracto) objeto
			    .newInstance();
		    component.setPrimaryKey(secu);
		    dto.setComponente(component);
		    log.info("fondo : " + this.grupo.getPrimaryKey());
		    dto.setIncluyeExcluye(this.estadoActual);
		    dto.setGrupo(this.grupo);
		    dto.setMD5(this.md5);
		    dto.setDescripcion(editor.getValue());

		    listaDetalles.add(dto);// hijos directos objeto principal

		}
		parametros.put("LISTADO_DETALLES", listaDetalles);

	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}

	return parametros;

    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name crearListItem
     * @param dto
     * @return
     * @desc crea un Listitem
     */
    public Listitem crearListItem(final IDetalleGrupo dto) {

	log.info("Ejecutando el método [ crearListItem ]... ");

	incluye.invalidate();
	excluye.invalidate();

	encabezadoExcluye.setVisible(false);
	encabezadoIncluye.setVisible(false);

	final Listitem item = new Listitem();
	Listcell cell = null;

	item.appendChild(new Listcell());// checkbox

	cell = new Listcell(dto.getComponente().getCodigo());
	item.appendChild(cell);// codigo

	cell = new Listcell(dto.getComponente().getNombre());
	item.appendChild(cell);// nombre
	item.setTooltiptext("[ " + dto.getComponente().getCodigo() + " ] "
		+ dto.getComponente().getNombre());// codigo nombre
	item.setValue(dto);

	if (dto.getIncluyeExcluye().equals("I")) {
	    item.setStyle("background-color:#E3F6CE");
	} else if (dto.getIncluyeExcluye().equals("E")) {
	    item.setStyle("background-color:#F6CECE");
	}

	item.addEventListener(Events.ON_RIGHT_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			menu.open(item, "after_end");
			dtoEditar = dto;

			if (dto.getIncluyeExcluye().equals("I")) {
			    menuItemIncluir.setVisible(false);
			    // menuItemExcluir.setVisible(true);

			} else if (dto.getIncluyeExcluye().equals("E")) {
			    menuItemIncluir.setVisible(true);
			    // menuItemExcluir.setVisible(false);

			}

		    }
		});

	menuItemEditar.setDisabled(true);
	// menuItemExcluir.setDisabled(true);
	menuItemIncluir.setDisabled(true);

	if (!deshabilitarDragDropTree && editar) {
	    encabezadoExcluye.setVisible(true);
	    encabezadoIncluye.setVisible(true);
	    item.setDraggable("true");
	    item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		@Override
		public void onEvent(Event arg0) throws Exception {

		    incluye.invalidate();
		    excluye.invalidate();
		}
	    });
	    menuItemEditar.setDisabled(false);
	    // menuItemExcluir.setDisabled(false);
	    menuItemIncluir.setDisabled(false);
	}

	return item;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name getMetodoObtenerPath
     * @return metodoObtenerPath
     * @descp obtiene el valor de metodoObtenerPath
     */
    public String getMetodoObtenerPath() {
	return metodoObtenerPath;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name getRender
     * @return render
     * @descp obtiene el valor de render
     */
    public ITreeFacadePorDemanda getRender() {
	return render;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name getTreechildren
     * @return treechildren
     * @descp obtiene el valor de treechildren
     */
    public Treechildren getTreechildren() {
	return treechildren;
    }

    public void limpiarCampos() {
	log.info(" Ejecutando el metodo [ limpiarCampos ]... ");
	editor.setValue("");
	html.setContent("<div style='border-style"
		+ ":double;border:1px;width:100%;height:120px;overflow:auto"
		+ ";font-size:12px;background-color:#E9E9E9;cursor:text'></div>");
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name loadTree
     * @desc carga el arbol de los componentes que se agrupan
     */
    @SuppressWarnings("unchecked")
    public void loadTree() {
	log.info("Ejecutando el método [ loadTree ]... ");

	try {

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    Long padre = null;
	    parameter.put("PADRE", padre);
	    parameter.put("GRUPO", this.grupo.getPrimaryKey());

	    // consultamos el arbol
	    listaComponentes = (List<MyItemTree>) ParametrizacionFac
		    .getFacade().obtenerListado(metodoLoadTree, parameter);

	    index = true; // define si se desabilita o no el componente
	    if (!this.deshabilitarDragDropTree && this.editar) {
		index = false;
	    }
	    treechildren.setAttribute("grupo", this.grupo.getPrimaryKey());
	    this.render.buildTreePorDemanda(treechildren, listaComponentes,
		    this.caption.getLabel().toUpperCase(), this.metodoLoadTree,
		    this.id, index);

	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onAgregarComponente
     * @param self
     * @param item
     * @desc agrega un componente al grupo desde el arbol
     */
    public void onAgregarComponente(Component self, Treeitem item) {
	log.info(" Ejecutando el metodo [ onAgregarComponente ]... ");

	incluye.invalidate();
	excluye.invalidate();

	try {

	    if (item != null) {
		Integer nivel = new Integer(
			((Treecell) ((Treerow) item.getFirstChild())
				.getChildren().get(3)).getLabel());
		if (nivel != 0) {
		    onMover(self, item.getTreerow());
		}
	    } else {

		action.setMensajeHistoricoGritter(
			IConstantes.ESTADO_INSERCION_ERROR,
			this.msgNoSeleccionado, "", "");
	    }

	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}

    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onBorrarDetalleGrupo
     * @param lista
     * @desc permite borrar registros de la bd
     */
    public void onBorrarDetalleGrupo(List<IDetalleGrupo> lista) {
	log.info(" Ejecutando el metodo [ onBorrarDetalleGrupo ]... ");
	log.info(lista);

	try {
	    Map<String, Object> parametros = new HashMap<String, Object>();
	    parametros.put("LISTADO_DETALLES", lista);
	    String eliminados = "";

	    if (lista.size() > 1) {
		eliminados = "<br>";
	    }

	    for (IDetalleGrupo long1 : lista) {
		eliminados = eliminados + "[ "
			+ long1.getComponente().getCodigo() + " ] "
			+ long1.getComponente().getNombre() + "<br>";

	    }
	    ParametrizacionFac.getFacade().borrarRegistro(metodoEliminar,
		    parametros);

	    action.setMensajeHistoricoGritter(IConstantes.ESTADO_BORRAR_OK,
		    this.msgBorrarOk, "", eliminados);

	    loadTree();
	    onConsultarDetalle();

	} catch (Exception e) {
	    e.getStackTrace();
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}

    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onConsultarDetalle
     * @desc consulta los detalles en la bd para mostrarlos en las listas
     *       incluye y exluye
     */
    @SuppressWarnings("unchecked")
    public void onConsultarDetalle() {
	log.info("Ejecutando el método [ onConsultarDetalle ]... ");
	try {
	    List<IDetalleGrupo> listaDetalles = null;
	    listaDetalles = (List<IDetalleGrupo>) ParametrizacionFac
		    .getFacade().obtenerListado(metodoListadoDetalle,
			    this.grupo);

	    incluye.getItems().clear();
	    excluye.getItems().clear();

	    for (IDetalleGrupo detalle : listaDetalles) {

		if (detalle.getIncluyeExcluye().equals("I")) {
		    incluye.appendChild(crearListItem(detalle));
		} else if (detalle.getIncluyeExcluye().equals("E")) {
		    excluye.appendChild(crearListItem(detalle));
		}

	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onEditar
     * @param dto
     * @desc establece la operación actualizar detalle
     */
    public void onEditar(IDetalleGrupo dto, int op) {
	log.info(" Ejecutando el metodo [ onEditar ]... ");

	try {
	    limpiarCampos();
	    operacion = "U";
	    this.onObtenerDetalle(dto);
	    dtoComponente = dto.getComponente();

	    secuenciaDetalle = dto.getPrimaryKey();
	    this.grupo = dto.getGrupo();
	    md5 = dto.getMD5();
	    editor.setValue(dto.getDescripcion());
	    html.setContent("<div style='border-style"
		    + ":double;border:1px;width:100%;height:120px;overflow:auto"
		    + ";font-size:12px;background-color:#E9E9E9;cursor:text'>"
		    + (dto.getDescripcion() != null
			    && !dto.getDescripcion().isEmpty() ? dto
			    .getDescripcion() : " ").toString() + "</div>");

	    if (op == 0) {
		onGuardarDetalleGrupo(); // actualizamos el detalle con un nuevo
					 // estado
	    } else if (op == 1) {
		estadoActual = dto.getIncluyeExcluye();
	    }

	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onGuardarDetalleGrupo
     * @desc permite insertar los registros detalle en la bd: operaciones
     *       guardar y editar
     */
    public void onGuardarDetalleGrupo() {
	log.info(" Ejecutando el metodo [ onGuardarDetalleGrupo ]... ");

	incluye.invalidate();
	excluye.invalidate();

	try {
	    IDetalleGrupo dto = (IDetalleGrupo) clazz.newInstance();
	    dto.setComponente(this.dtoComponente);
	    dto.setIncluyeExcluye(this.estadoActual);
	    dto.setGrupo(this.grupo);
	    dto.setMD5(this.md5);
	    dto.setDescripcion(editor.getValue());

	    Integer padreEx = (Integer) ParametrizacionFac.getFacade()
		    .obtenerRegistro(metodoObtenerPadresExcluidos, dto);

	    if (padreEx > 0) {
		cont = 0;
		cont2 = 0;
		if (dto.getIncluyeExcluye().equals("I")) {
		    action.setMensajeHistoricoGritter(
			    IConstantes.ESTADO_INSERCION_ERROR,
			    this.msgNoIncluido, dtoComponente.getCodigo(),
			    dtoComponente.getNombre());

		} else if (dto.getIncluyeExcluye().equals("E")) {
		    action.setMensajeHistoricoGritter(
			    IConstantes.ESTADO_INSERCION_ERROR,
			    this.msgInsercionErrorExcluido,
			    dtoComponente.getCodigo(),
			    dtoComponente.getNombre());
		}

		return;
	    }

	    Integer padreIn = (Integer) ParametrizacionFac.getFacade()
		    .obtenerRegistro(metodoObtenerPadresIncluidos, dto);

	    if (padreIn > 0) {
		if (operacion.equals("N")
			&& dto.getIncluyeExcluye().equals("I")) {
		    cont = 0;
		    cont2 = 0;
		    action.setMensajeHistoricoGritter(
			    IConstantes.ESTADO_INSERCION_ERROR,
			    this.msgInsercionErrorIncluido,
			    dtoComponente.getCodigo(),
			    dtoComponente.getNombre());

		    return;
		}

	    }
	    if (dto.getIncluyeExcluye().equals("E") && padreIn == 0) {
		cont = 0;
		cont2 = 0;
		return;
	    }

	    if (operacion.equals("N")) {

		ParametrizacionFac.getFacade().guardarRegistro(metodoGuardar,
			dto);
		action.setMensajeHistoricoGritter(
			IConstantes.ESTADO_INSERCION_OK, this.msgInsercionOk,
			dtoComponente.getCodigo(), dtoComponente.getNombre());

	    } else if (operacion.equals("U")) {

		dto.setPrimaryKey(secuenciaDetalle);
		ParametrizacionFac.getFacade().actualizarRegistro(
			metodoActualizar, dto);
		action.setMensajeHistoricoGritter(
			IConstantes.ESTADO_EDICION_OK, this.msgEdicionOk,
			dtoComponente.getCodigo(), dtoComponente.getNombre());

	    }
	    if (cont == cont2) {
		loadTree();
		onConsultarDetalle();
		onSeleccionarComponente(this.dtoComponente.getPrimaryKey());
		cont = 0;
		cont2 = 0;
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}

    }

    @SuppressWarnings("unchecked")
    protected void onListarDetalleGrupos() {
	log.info("Ejecutando el método [ onListarDetalleGrupos ]... ");

	try {

	    List<IBeanAbstracto> listaGrupoObjDetalle = ((List<IBeanAbstracto>) ParametrizacionFac
		    .getFacade().obtenerListado(metodoObtenerIncluidos,
			    this.grupo));

	    rowsDetalle.getChildren().clear();
	    for (IBeanAbstracto beanAbstracto : listaGrupoObjDetalle) {
		Row row = new Row();
		row.appendChild(new Label(beanAbstracto.getCodigo()));
		row.appendChild(new Label(beanAbstracto.getNombre()));

		rowsDetalle.appendChild(row);
	    }

	    gridDetalle.setMold("paging");
	    gridDetalle.setPageSize(IConstantes.TAMANO_PAGINACION_DETALLE);
	    gridDetalle.applyProperties();

	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onMover
     * @param self
     * @param dragged
     * @throws Exception
     * @desc permite arrastrar un componente a un grupo en las listas incluye y
     *       excluye
     */
    @SuppressWarnings("unchecked")
    public void onMover(Component self, Component dragged) {
	log.info(" Ejecutando el metodo [ onMover ]... ");

	try {
	    if (self.getId().equals(incluye.getId())
		    || self.getId().equals(excluye.getId())) {

		if (self.getId().equals(incluye.getId())) {
		    estadoActual = "I";
		} else if (self.getId().equals(excluye.getId())) {
		    estadoActual = "E";
		}

		if (dragged instanceof Listitem) { // si el objeto que se
						   // arrasta es un listitem
		    Listitem item = (Listitem) dragged;
		    onEditar((IDetalleGrupo) item.getValue(), 0);

		} else if (dragged instanceof Treerow) { // si es un treerow
		    Treerow row = (Treerow) dragged;
		    secuenciaComponente = new Long(((Treecell) row
			    .getChildren().get(4)).getLabel());// secuencia
							       // objeto

		    String mov = ((Treecell) row.getChildren().get(5))
			    .getLabel();

		    if (!mov.isEmpty() && mov != null) {
			if (mov.equals("N")) {
			    List<Long> listaRama = (List<Long>) ParametrizacionFac
				    .getFacade().obtenerListado(
					    metodoObtenerHijosDirectos,
					    secuenciaComponente);

			    if (listaRama != null) {
				cont = listaRama.size();
				for (Long secHijo : listaRama) {
				    validarOperacion(secHijo);
				}
			    }
			} else {
			    cont = 1;
			    validarOperacion(secuenciaComponente);
			}
		    } else {
			cont = 1;
			validarOperacion(secuenciaComponente);
		    }

		}
	    }

	} catch (Exception e) {
	    e.getStackTrace();
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}

    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onObtenerComponente
     * @param sec
     * @return
     * @desc obtiene el componente a agrupar de la bd
     */
    public IBeanAbstracto onObtenerComponente(Long sec) {
	log.info(" Ejecutando el metodo [ onObtenerComponente ]... ");
	IBeanAbstracto dto = null;

	try {
	    dto = (IBeanAbstracto) ParametrizacionFac.getFacade()
		    .obtenerRegistro(metodoObtener, sec);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}
	return dto;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onObtenerDetalle
     * @param dto
     * @desc obtiene el registro detalle de la bd
     */
    public void onObtenerDetalle(IDetalleGrupo dto) {
	log.info(" Ejecutando el metodo [ onObtenerComponente ]... ");

	try {
	    ParametrizacionFac.getFacade().obtenerRegistro(
		    metodoObtenerDetalle, dto);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onQuitarComponente
     * @param self
     * @param op
     * @desc Elimina un componente de las listas incluye o excluye
     * */
    @SuppressWarnings("rawtypes")
    public void onQuitarComponente(Component self) {
	log.info(" Ejecutando el metodo [ onQuitarComponente ]... ");

	try {
	    if (self instanceof Listbox) {
		if (((Listbox) self).getItems().size() > 0) {
		    List<IDetalleGrupo> details = new LinkedList<IDetalleGrupo>();
		    Set items = ((Listbox) self).getSelectedItems();
		    log.info(items);
		    if (!items.isEmpty()) {
			for (Iterator iterator = items.iterator(); iterator
				.hasNext();) {
			    Listitem listitem = (Listitem) iterator.next();
			    IDetalleGrupo dto = (IDetalleGrupo) listitem
				    .getValue();
			    details.add(dto);
			}
			onBorrarDetalleGrupo(details);

		    } else {
			action.setMensajeHistoricoGritter(
				IConstantes.ESTADO_BORRAR_ERROR,
				this.msgNoSeleccionado, "", "");
		    }

		} else {
		    action.setMensajeHistoricoGritter(
			    IConstantes.ESTADO_BORRAR_ERROR,
			    this.msgNoExisteRegistro, "", "");
		}
	    } else if (self instanceof Listitem) {

		IDetalleGrupo dto = (IDetalleGrupo) ((Listitem) self)
			.getValue();
		List<IDetalleGrupo> details = new LinkedList<IDetalleGrupo>();
		details.add(dto);

		if (self.getParent() != null) {
		    onBorrarDetalleGrupo(details);
		}
	    }

	} catch (Exception e) {
	    e.getStackTrace();
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name onSeleccionarComponente
     * @param sec
     * @desc permite seleccionar el componente editado o registrado como un
     *       detalle
     */
    @SuppressWarnings("unchecked")
    public void onSeleccionarComponente(Long sec) {

	log.info("Ejecutando el método [ onSeleccionarComponente ]...");

	try {

	    List<String> lpath = (List<String>) ParametrizacionFac.getFacade()
		    .obtenerListado(this.metodoObtenerPath, sec);
	    String path = null;
	    String[] nsec = null;

	    if (lpath.size() > 0) {
		path = lpath.get(0);
		nsec = path.trim().split("/");
	    }

	    Treeitem itemSeleccionado = this.render.cargarHijosTreePorDemanda(
		    treechildren, nsec, this.id, this.index);

	    tree.setSelectedItem(itemSeleccionado);
	    itemSeleccionado.setFocus(true);
	    tree.setSelectedItem(null);
	} catch (Exception e) {
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	    log.error(e.getCause(), e);
	}
    }

    public void onSeleccionarDetalle(IDetalleGrupo dto) {
	log.info("Ejecutando el método [ onSeleccionarDetalle ]... ");
	grupo = dto.getGrupo();

    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setAction
     * @return void
     * @param recibe
     *            el parametro action
     * @descp modifica el atributo action
     */
    public void setAction(ActionStandardBorder action) {
	this.action = action;

    }

    /**
     * @type Método de la clase TreeItemRenderDetallesGrupos.java
     * @name setClazz
     * @return void
     * @param recibe
     *            el parametro clazz
     * @descp dto detalle que se va a guardar
     */
    public void setClazz(Class<?> clazz) {
	this.clazz = clazz;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setEditar
     * @return void
     * @param recibe
     *            el parametro editar
     * @descp modifica el atributo editar para indicar si el boton de edicion
     *        esta activo o no : false indica que no esta inactivo y true que
     *        esta inactivo ; cuando este esta inactivo, o inhabilitado se
     *        permite la edicion en el componente
     */
    public void setEditar(boolean editar) {
	// Se setea la variable editar en true, dado que ya no se debe tener en
	// cuenta el estado del bot�n editar
	// del maestro, si se requiere que se vuelva a tener en cuenta este
	// bot�n, se debe colocar esta l�nea
	this.editar = true;
    }

    /**
     * @type Método de la clase TreeItemRenderDetallesGrupos.java
     * @name setSecuenciaGrupo
     * @return void
     * @param recibe
     *            el parametro secuenciaGrupo
     * @descp objeto que agrupa los componentes
     */
    public void setGrupo(IBeanAbstracto grupo) {
	this.grupo = grupo;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setId
     * @return void
     * @param recibe
     *            el parametro id
     * @descp modifica el atributo id que representa las iniciales de la vista
     */
    @Override
    public void setId(String id) {
	this.id = id;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoActualizar
     * @return void
     * @param recibe
     *            el parametro metodoActualizar
     * @descp nombre del metodo para actualizar el estado del detalle en la bd
     */
    public void setMetodoActualizar(String metodoActualizar) {
	this.metodoActualizar = metodoActualizar;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoEliminar
     * @return void
     * @param recibe
     *            el parametro metodoEliminar
     * @descp metodo para eliminar el registro detalle
     */
    public void setMetodoEliminar(String metodoEliminar) {
	this.metodoEliminar = metodoEliminar;
    }

    /**
     * @type Método de la clase TreeItemRenderDetallesGrupos.java
     * @name setNombreMetodo
     * @return void
     * @param recibe
     *            el parametro nombreMetodo
     * @descp nombre del metodo para guardar el detalle en la bd
     */
    public void setMetodoGuardar(String metodoGuardar) {
	this.metodoGuardar = metodoGuardar;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoListadoDetalle
     * @return void
     * @param recibe
     *            el parametro metodoListadoDetalle
     * @descp metodo de todos los detalles registrados para mostrarlos en las
     *        listas incluye y excluye
     */
    public void setMetodoListadoDetalle(String metodoListadoDetalle) {
	this.metodoListadoDetalle = metodoListadoDetalle;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoLoadTree
     * @return void
     * @param recibe
     *            el parametro metodoLoadTree
     * @descp metodo del arbol a cargar
     */
    public void setMetodoLoadTree(String metodoLoadTree) {
	this.metodoLoadTree = metodoLoadTree;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoObtenerIncluidosExcluidos
     * @return void
     * @param recibe
     *            el parametro metodoObtenerIncluidosExcluidos
     * @descp metodo ue obtiene los incluidos y excluidos para pintar el estado
     *        en el arbol
     * 
     *        public void setMetodoObtenerIncluidosExcluidos( String
     *        metodoObtenerIncluidosExcluidos) {
     *        this.metodoObtenerIncluidosExcluidos =
     *        metodoObtenerIncluidosExcluidos; }
     * 
     *        /**
     * @type Método de la clase TreeItemRenderDetallesGrupos.java
     * @name setOnObtenerComponente
     * @return void
     * @param recibe
     *            el parametro onObtenerComponente
     * @descp nombre del metodo para obtener el objeto que se arrastra y que se
     *        agrupara en la bd
     */
    public void setMetodoObtener(String metodoObtener) {
	this.metodoObtener = metodoObtener;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoObtenerDetalle
     * @return void
     * @param recibe
     *            el parametro metodoObtenerDetalle
     * @descp metodo que obtiene un detalle
     */
    public void setMetodoObtenerDetalle(String metodoObtenerDetalle) {
	this.metodoObtenerDetalle = metodoObtenerDetalle;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoObtenerDetalleByComponente
     * @return void
     * @param recibe
     *            el parametro metodoObtenerDetalleByComponente
     * @descp metodo para obtener el detalle que se relacione a un componente
     *        especifico
     */
    public void setMetodoObtenerDetalleByComponente(
	    String metodoObtenerDetalleByComponente) {
	this.metodoObtenerDetalleByComponente = metodoObtenerDetalleByComponente;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoObtenerHijosDirectos
     * @return void
     * @param recibe
     *            el parametro metodoObtenerHijosDirectos
     * @descp modifica el atributo metodoObtenerHijosDirectos
     */
    public void setMetodoObtenerHijosDirectos(String metodoObtenerHijosDirectos) {
	this.metodoObtenerHijosDirectos = metodoObtenerHijosDirectos;
    }

    /**
     * @param metodoObtenerIncluidos
     *            Obtiene los componentes incluidos y sus hijos.
     */
    public void setMetodoObtenerIncluidos(String metodoObtenerIncluidos) {
	this.metodoObtenerIncluidos = metodoObtenerIncluidos;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoObtenerPadresExcluidos
     * @return void
     * @param recibe
     *            el parametro metodoObtenerPadresExcluidos
     * @descp modifica el atributo metodoObtenerPadresExcluidos
     */
    public void setMetodoObtenerPadresExcluidos(
	    String metodoObtenerPadresExcluidos) {
	this.metodoObtenerPadresExcluidos = metodoObtenerPadresExcluidos;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setMetodoObtenerPadresIncluidos
     * @return void
     * @param recibe
     *            el parametro metodoObtenerPadresIncluidos
     * @descp nombre del metodo que obtiene los padres con estado incluido
     */
    public void setMetodoObtenerPadresIncluidos(
	    String metodoObtenerPadresIncluidos) {
	this.metodoObtenerPadresIncluidos = metodoObtenerPadresIncluidos;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setConsultaPath
     * @return void
     * @param recibe
     *            el parametro consultaPath
     * @descp modifica el atributo consultaPath
     */
    public void setMetodoObtenerPath(String consultaPath) {
	this.metodoObtenerPath = consultaPath;
    }

    public void setMsgBorrarOk(String msgBorrarOk) {
	this.msgBorrarOk = msgBorrarOk;
    }

    public void setMsgEdicionOk(String msgEdicionOk) {
	this.msgEdicionOk = msgEdicionOk;
    }

    public void setMsgInsercionErrorExcluido(String msgInsercionErrorExcluido) {
	this.msgInsercionErrorExcluido = msgInsercionErrorExcluido;
    }

    public void setMsgInsercionErrorIncluido(String msgInsercionErrorIncluido) {
	this.msgInsercionErrorIncluido = msgInsercionErrorIncluido;
    }

    public void setMsgInsercionOk(String msgInsercionOk) {
	this.msgInsercionOk = msgInsercionOk;
    }

    public void setMsgNoExisteRegistro(String msgNoExisteRegistro) {
	this.msgNoExisteRegistro = msgNoExisteRegistro;
    }

    public void setMsgNoIncluido(String msgNoIncluido) {
	this.msgNoIncluido = msgNoIncluido;
    }

    public void setMsgNoSeleccionado(String msgNoSeleccionado) {
	this.msgNoSeleccionado = msgNoSeleccionado;
    }

    /**
     * @type Método de la clase TreeItemRenderDetallesGrupos.java
     * @name setObjeto
     * @return void
     * @param recibe
     *            el parametro objeto
     * @descp objeto que se agrupa
     */
    public void setObjeto(Class<?> objeto) {
	this.objeto = objeto;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setRender
     * @return void
     * @param recibe
     *            el parametro render
     * @descp modifica el atributo render
     */
    public void setRender(ITreeFacadePorDemanda render) {
	this.render = render;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setTree
     * @return void
     * @param recibe
     *            el parametro tree
     * @descp modifica el atributo tree
     */
    public void setTree(Tree tree) {
	this.tree = tree;
    }

    /**
     * @type Método de la clase DragDropTree.java
     * @name setTreechildren
     * @return void
     * @param recibe
     *            el parametro treechildren
     * @descp modifica el atributo treechildren
     */
    public void setTreechildren(Treechildren treechildren) {
	this.treechildren = treechildren;
    }

    public void validarOperacion(Long sec) {
	log.info(" Ejecutando el metodo [ validarOperacion ]... ");
	try {
	    cont2++;
	    IDetalleGrupo dto = (IDetalleGrupo) clazz.newInstance();
	    IBeanAbstracto comp = (IBeanAbstracto) objeto.newInstance();
	    comp.setPrimaryKey(sec);
	    dto.setComponente(comp);
	    dto.setGrupo(this.grupo);

	    dto = (IDetalleGrupo) ParametrizacionFac.getFacade()
		    .obtenerRegistro(metodoObtenerDetalleByComponente, dto);

	    if (dto != null && dto.getPrimaryKey() != null) {
		onEditar(dto, 0);
	    } else {
		limpiarCampos();
		operacion = "N";
		dtoComponente = this.onObtenerComponente(sec);
		md5 = null;
		onGuardarDetalleGrupo();// se guarda el detalle

	    }
	} catch (Exception e) {
	    e.getStackTrace();
	    super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
	}

    }

}
