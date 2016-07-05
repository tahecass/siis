package com.casewaresa.framework.macros;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Space;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.renderer.PagingSelectorFilas;
import com.casewaresa.framework.util.PagingControlColumnas;
import com.casewaresa.framework.util.Utilidades;

/**
 * Clase para personalizar a un listbox e integrarlo con el macrocomponente
 * {@link PagingControl} para que la paginacion sea real y para que sea
 * seleccionable dependiendo del tipo de seleccion {@link TiposSeleccion} que
 * utilice. Esta clase
 * 
 * @author dperezc
 * 
 */
public class PagingSelector extends Listbox implements AfterCompose,
	EventListener<SelectEvent<Listitem, Object>> {

    private static final long serialVersionUID = -7648514210701097240L;
    private static Logger logger = Logger.getLogger(PagingSelector.class);

    /**
     * Variable de tipo {@link PagingControl} para que controle la paginacion
     * real de los registros que se van mostrando
     */
    private final PagingControl pagingControl = new PagingControl();

   

    /**
     * Tipo de seleccion con la que va a trabajar el componente
     */
    private TiposSeleccion tipoSeleccion;

    /**
     * Variable (render) que define como va a ser la presentacion de las filas
     * en el listbox. Esta variable tendra el control de los items que van
     * seleccionados y de cuales no
     */
    private PagingSelectorFilas pagingSelectorFilas;

    /**
     * Lista de atributos de las columnas para hacer el ordenamiento ya sea
     * ascendente o descente por cada una de ellas, de forma respectiva. Es
     * decir a la primera columa que encuentra le agrega el primer atributo
     * encontrado en la lista y asi sucesivamente.
     */
    private List<PagingControlColumnas> listaAtributosColumnas;

    /**
     * Variable que contiene el id de la instruccion en el xml que va a hacer la
     * consulta paginada a la base de datos
     */
    private String consultaPaginada;

    /**
     * Variable para controlar la seleccion de todos los items del listbox de
     * resultados cuando el tipo de seleccion sea multiple
     */
    private final Checkbox checkboxSeleccionarTodos = new Checkbox();

    /**
     * tooltiptext que se le agrega al checkbox de seleccionar todos items
     */
    private String tooltiptextSeleccionar;

    /**
     * implementacion de la interfaz {@link AfterCompose}. Se hace el llamado al
     * metodo {@link #crearComponentesControl()} Se habilita el atributo
     * "org.zkoss.zul.listbox.rod" para que el listbox como tal, para que no
     * permita la seleccion de todos registros de forma automatica sino que se
     * haga de forma manual con a travez de la {@link #checkboxSeleccionarTodos}
     */
    @Override
    public void afterCompose() {
	try {
	    crearComponentesControl();
	    this.setAttribute("org.zkoss.zul.listbox.rod", true);
	} catch (Exception e) {
	    Utilidades.lanzarExcepcion(new Excepcion(
		    IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * Inicializa los componentes de control ( {@link #checkboxSeleccionarTodos}
     * y {@link #pagingControl}) y el evento onSelect del listbox como tal.
     */
    public void inicializarMacrocomponente() {
	logger.info("[info] --> ejecutando el metodo @inicializarMacrocomponente");
	try {

	    if (tooltiptextSeleccionar != null) {
		checkboxSeleccionarTodos.setTooltiptext(tooltiptextSeleccionar);
	    }

	    parametrizarPagingControl();

	    this.checkboxSeleccionarTodos.setVisible(false);
	    this.addEventListener(Events.ON_SELECT, this);

	} catch (Exception e) {
	    Utilidades.lanzarExcepcion(new Excepcion(
		    IExcepcion.EXCEPCION_GENERAL, e));
	}
    }

    /**
     * Este metodo agrega el paging control al listfoot del listbox. Agrega
     * tambien el checkbox de seleccionar todos los registros y su respectivo
     * evento onCkeck
     * 
     * @throws Exception
     */
    private void crearComponentesControl() throws Exception {
	logger.info("[info] --> ejecutando el metodo @crearComponentesControl");
	Listfoot listfoot = new Listfoot();
	Listfooter listfooter = new Listfooter();
	listfooter.setSpan(this.getListhead().getChildren().size());
	listfooter.appendChild(pagingControl);
	listfoot.appendChild(listfooter);
	this.appendChild(listfoot);
	listfoot.setVisible(false);
	pagingControl.inicializarPagingControl();

	if (!this.getListhead().getChildren().isEmpty()) {
	    Label label = new Label(((Listheader) this.getListhead()
		    .getChildren().get(0)).getLabel());
	    label.setStyle("font-weight: bold");

	    ((Listheader) this.getListhead().getChildren().get(0)).setLabel("");
	    ((Listheader) this.getListhead().getChildren().get(0))
		    .appendChild(checkboxSeleccionarTodos);
	    checkboxSeleccionarTodos.setStyle("cursor:pointer");
	    Space space = new Space();
	    space.setWidth("2px");
	    ((Listheader) this.getListhead().getChildren().get(0))
		    .appendChild(space);
	    ((Listheader) this.getListhead().getChildren().get(0))
		    .appendChild(label);
	    checkboxSeleccionarTodos.addEventListener(Events.ON_CHECK,
		    new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
			    if (checkboxSeleccionarTodos.isChecked()) {
				pagingSelectorFilas.setSeleccionarTodos(true);
				pagingSelectorFilas.getMapaSeleccionados()
					.clear();
				pagingSelectorFilas.getMapaNoSeleccionados()
					.clear();
				List<Listitem> listaItems = PagingSelector.this
					.getItems();
				for (Listitem listitem : listaItems) {
				    listitem.setSelected(true);
				    IBeanAbstracto iBeanAbstracto = (IBeanAbstracto) listitem
					    .getValue();
				    pagingSelectorFilas
					    .getMapaSeleccionados()
					    .put(iBeanAbstracto.getPrimaryKey(),
						    iBeanAbstracto);
				}

			    } else {
				List<Listitem> listaItems = PagingSelector.this
					.getItems();
				for (Listitem listitem : listaItems) {
				    listitem.setSelected(false);
				}
				pagingSelectorFilas.setSeleccionarTodos(false);
				pagingSelectorFilas.getMapaSeleccionados()
					.clear();
				pagingSelectorFilas.getMapaNoSeleccionados()
					.clear();
			    }
			}

		    });
	}
    }

    /**
     * Metodo para parametrizar al pagingControl que va a controlar la
     * paginacion real en el listbox
     * 
     * @throws Exception
     */
    private void parametrizarPagingControl() throws Exception {

	pagingControl.setComponenteReferencia(this);
	pagingControl.setStatementConsultaPaginada(getConsultaPaginada());
	pagingControl.setPageSize(IConstantes.TAMANO_PAGINACION);
	pagingControl.setComponenteContenedor(this.getListfoot());

	pagingSelectorFilas.setTipoSeleccion(tipoSeleccion);
	pagingControl.setPagingControlFilas(pagingSelectorFilas);

	// parametrizar las columnas agregandole el nombre de las columnas en la
	// tabla y si van a ser ordenadas
	if (listaAtributosColumnas == null)
	    listaAtributosColumnas = new ArrayList<PagingControlColumnas>();

	pagingControl.agregarAtributosColumnas(listaAtributosColumnas);

    }

    /**
     * Metodo para listar
     * 
     * @param parametros
     * @return
     * @throws Exception
     */
    public Integer onListar(Map<String, Object> parametros) throws Exception {
	limpiarSeleccion();
	pagingControl.setParametros(parametros);
	List<IBeanAbstracto> listado = pagingControl.listar(null);

	if (tipoSeleccion.equals(TiposSeleccion.MULTIPLE_TODAS_PAGINAS)) {
	    if (!listado.isEmpty()) {
		this.checkboxSeleccionarTodos.setVisible(true);
	    } else {
		this.checkboxSeleccionarTodos.setVisible(false);
	    }
	} else {
	    this.checkboxSeleccionarTodos.setVisible(false);
	}

	return pagingControl.getTotalSize();
    }

    /**
     * Limpia todos los items seleccionados
     */
    public void limpiarSeleccion() throws Exception{
	checkboxSeleccionarTodos.setChecked(false);
	pagingSelectorFilas.setSeleccionarTodos(false);
	pagingSelectorFilas.getMapaSeleccionados().clear();
	pagingSelectorFilas.getMapaNoSeleccionados().clear();
    }

    /**
     * Obtener los registros seleccionados
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<IBeanAbstracto> getListadoRegistrosSeleccionados()
	    throws Exception {
	List<IBeanAbstracto> listado = new ArrayList<IBeanAbstracto>();

	if (!pagingSelectorFilas.getSeleccionarTodos()) {
	    for (IBeanAbstracto iBeanAbstracto : pagingSelectorFilas
		    .getMapaSeleccionados().values()) {
		listado.add(iBeanAbstracto);
	    }
	} else {
	    List<IBeanAbstracto> listadoRegistros = (List<IBeanAbstracto>) ParametrizacionFac
		    .getFacade().obtenerListado(
			    pagingControl.getStatementConsultaPaginada(),
			    pagingControl.getParametros());
	    for (IBeanAbstracto iBeanAbstracto : listadoRegistros) {
		if (!pagingSelectorFilas.getMapaNoSeleccionados().containsKey(
			iBeanAbstracto.getPrimaryKey())) {
		    listado.add(iBeanAbstracto);
		}
	    }
	}

	return listado;
    }

    /**
     * Evento onSelect que se le agrega al listbox
     */
    @Override
    public void onEvent(SelectEvent<Listitem, Object> event) throws Exception {
	if (tipoSeleccion.equals(TiposSeleccion.NO_MULTIPLE)) {
	    pagingSelectorFilas.getMapaSeleccionados().clear();
	    IBeanAbstracto iBeanAbstracto = (IBeanAbstracto) event
		    .getReference().getValue();
	    pagingSelectorFilas.getMapaSeleccionados().put(
		    iBeanAbstracto.getPrimaryKey(), iBeanAbstracto);
	} else if (tipoSeleccion.equals(TiposSeleccion.MULTIPLE_TODAS_PAGINAS)) {
	    IBeanAbstracto iBeanAbstracto = (IBeanAbstracto) event
		    .getReference().getValue();

	    if (event.getReference().isSelected()) {
		if (!pagingSelectorFilas.getSeleccionarTodos()) {
		    pagingSelectorFilas.getMapaSeleccionados().put(
			    iBeanAbstracto.getPrimaryKey(), iBeanAbstracto);
		} else {
		    pagingSelectorFilas.getMapaNoSeleccionados().remove(
			    iBeanAbstracto.getPrimaryKey());
		}
		if (pagingSelectorFilas.getSeleccionarTodos()
			&& pagingSelectorFilas.getMapaNoSeleccionados()
				.isEmpty()) {
		    checkboxSeleccionarTodos.setChecked(true);

		} else {
		    checkboxSeleccionarTodos.setChecked(false);
		}

		if (!pagingSelectorFilas.getSeleccionarTodos()) {
		    if (pagingSelectorFilas.getMapaSeleccionados().size() == pagingControl
			    .getTotalSize()) {
			checkboxSeleccionarTodos.setChecked(true);
		    } else {
			checkboxSeleccionarTodos.setChecked(false);
		    }
		}

	    } else {
		if (!pagingSelectorFilas.getSeleccionarTodos()) {
		    pagingSelectorFilas.getMapaSeleccionados().remove(
			    iBeanAbstracto.getPrimaryKey());
		} else {
		    pagingSelectorFilas.getMapaNoSeleccionados().put(
			    iBeanAbstracto.getPrimaryKey(), iBeanAbstracto);
		}

		checkboxSeleccionarTodos.setChecked(false);
	    }
	}
    }

    public void setTipoSeleccion(TiposSeleccion tipoSeleccion) {
	this.tipoSeleccion = tipoSeleccion;
    }

    public TiposSeleccion getTipoSeleccion() {
	return tipoSeleccion;
    }

    public void setPagingSelectorFilas(PagingSelectorFilas pagingSelectorFilas) {
	this.pagingSelectorFilas = pagingSelectorFilas;
    }

    public PagingSelectorFilas getPagingSelectorFilas() {
	return pagingSelectorFilas;
    }

    public void setTooltiptextSeleccionar(String tooltiptextSeleccionar) {
	this.tooltiptextSeleccionar = tooltiptextSeleccionar;
    }

    public String getTooltiptextSeleccionar() {
	return tooltiptextSeleccionar;
    }

    public List<PagingControlColumnas> getListaAtributosColumnas() {
	return listaAtributosColumnas;
    }

    public void setListaAtributosColumnas(
	    List<PagingControlColumnas> listaAtributosColumnas) {
	this.listaAtributosColumnas = listaAtributosColumnas;
    }

    public void setConsultaPaginada(String consultaPaginada) {
	this.consultaPaginada = consultaPaginada;
    }

    public String getConsultaPaginada() {
	return consultaPaginada;
    }

    /**
     * Enumeracion que contiene los diferentes tipos de seleccion que puete
     * utilizar el macrocomponente {@link PagingSelector}
     * 
     * NO_MULTIPLE ===> Indica que la seleccion en el {@link Listbox} de
     * referencia no sera multiple. MULTIPLE_TODAS_PAGINAS ===> Indica que la
     * seleccion en el {@link Listbox} de referencia sera multiple. Y que al
     * momento de chequear la opcion de seleccionar todos los registros el
     * listbox seleccionara todos los registros de todas las paginas.
     * 
     * @author dperezc
     * 
     */
    public enum TiposSeleccion {
	NO_MULTIPLE, MULTIPLE_TODAS_PAGINAS
    }
    
    public PagingControl getPagingControl() {
        return pagingControl;
    }

}
