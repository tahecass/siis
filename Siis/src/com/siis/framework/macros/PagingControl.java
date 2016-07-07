package com.siis.framework.macros;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.impl.HeaderElement;
import org.zkoss.zul.impl.HeadersElement;
import org.zkoss.zul.impl.MeshElement;

import com.siis.framework.contract.IExcepcion;
import com.siis.framework.dto.IBeanAbstracto;
import com.siis.framework.excepciones.impl.Excepcion;
import com.siis.framework.facade.ParametrizacionFac;
import com.siis.framework.renderer.PagingControlFilas;
import com.siis.framework.renderer.PagingSelectorFilas;
import com.siis.framework.util.PagingControlColumnas;
import com.siis.framework.util.Utilidades;
import com.siis.iceberg_sr.contract.ISentenciasSQL;

/**
 * Clase o action que controla al macrocomponente pagingControl Esta clase
 * extiende de {@link Paging} para que se comporte como un paginador de zk e
 * implementa la interface {@link EventListener} para la programacion del Evento
 * {@link PagingEvent} que es el que contrala las resultados en las paginas.
 * 
 * @author dperezc
 */
public class PagingControl extends Paging implements EventListener<PagingEvent> {

    private static final long serialVersionUID = 3590895646759546534L;

    private static Logger logger = Logger.getLogger(PagingControl.class);

    /**
     * Componente referencia al cual se le va a hacer la paginacion. Solo puede
     * una instancia de {@link MeshElement } como por ejemplo {@link Grid} o
     * {@link Listbox}
     */
    private MeshElement _componenteReferencia;

    /**
     * Variable que contiene el id de la instruccion en el xml que va a hacer la
     * consulta paginada a la base de datos
     */
    private String _statementCP;

    /**
     * Variable que contiene los parametros de busqueda para hacer la consulta
     * paginada
     */
    private Map<String, Object> _parametros = new HashMap<String, Object>();

    /**
     * Variable (render) que define como va a ser la presentacion de las filas
     * en el componente referencia
     */
    private PagingControlFilas _pagingControlFilas;

    /**
     * Variable para manejar la pagina activa o la pagina actual que esta
     * mostrando el controlador de paginacion
     */
    private int _paginaActiva;

    /**
     * Variable para almacenar la columna por la cual se estan ordenando los
     * datos en el componente referencia
     */
    private String _ordenColumna;

    /**
     * Variable para saber si se hizo el llamado al metodo
     * {@link #listar(IBeanAbstracto)}.
     */
    private boolean _iniciadaConsulta;

    /**
     * Variable para almacenar la columna por la cual se ordeno y el en que
     * orden se hizo para que el evento onSort no haga la repiticion nuevamente
     * del ordenamiento
     */
    private String _ordenColumnaSort = "";

    /**
     * Contenedor del componente PagingControl, el cual se utilizara para hacer
     * visible o no al componente. Ya que algunas veces dependiendo del
     * resultado de la consulta no es necesario mostrarlo; Entonces no ocultamos
     * como tal al componente , sino al componente que lo contiene o al que se
     * determina como contenedor.
     */
    private Component _componenteContenedor;

    /**
     * Variable para saber si la consulta a paginar que se le esta pasa al
     * PagingControl es dinamica o no. Inicializada en false...
     */
    private Boolean _esConsultaDinamica = false;

    /**
     * Variable que contiene la consulta que se va a ejecutar cuando se va a
     * trabajar de forma dinamica
     */
    private String _sqlConsultaDinamica;

    /**
     * Tipo de clase del Objeto para cuando se implemente el PagingControl con
     * consulta dinamica
     */
    private Object _tipoClase;

    /**
     * ListModel de datos que se van a mostrar
     */
    private ListModel<IBeanAbstracto> listModelDatos;
    
    /**
     * TODO Crear el validardor de la consulta select real 
     */
    private String tablaPadreFrom = null;

    /**
     * Hace la consulta a la base de datos recibiendo como parametro de busqueda
     * un iBeanAbstracto. Este metodo cambia la propiedad
     * {@link #_iniciadaConsulta } a TRUE para indicar que se esta haciendo el
     * llamado a este metodo. Agrega el iBeanAbstracto que recibe al mapa de
     * parametros de busqueda {@link #_parametros }. Limpia el ordenamiento de
     * las columnas dejando todo por defecto mediante el metodo
     * {@link #quitarSortingColumnas()}. Inicializa las columnas nuevamente
     * programandole los eventos de ordenamiento a travez del metodo
     * {@link #inicializarSortingColumnas() } Ejecuta el metodo
     * {@link #ejecutarConsultaTotalFilas()} para saber el total de filas que va
     * a controlar el componente Pone la primera pagina por defecto haciendo el
     * llamado al metodo {@link #setPaginaActiva(int) } pasandole 0 que esa la
     * primera pagina Hace el llamado al metodo
     * {@link #ejecutarConsultaPaginada(int)} para traer los resultados que se
     * estaran mostrando en el componente referencia Luego pinta los resultados
     * obtenidos en el componente referencia a travez del metodo
     * {@link #mostrarPaginaComponente(List)}
     * 
     * @param iBeanAbstracto
     *            Parametro que contiene los valores de busqueda para hacer la
     *            consulta
     * @return Retorna la lista de objetos que esta mostrando
     * @throws Exception
     */

    public List<IBeanAbstracto> listar(IBeanAbstracto iBeanAbstracto)
	    throws Exception {
	logger.info("[info] --> ejecutando el metodo @listar(iBeanAbstracto)");
	_iniciadaConsulta = false;
	_pagingControlFilas.setRegistroSeleccionado(null);
	if (iBeanAbstracto != null) {
	    _parametros.put("OBJETO", iBeanAbstracto);
	}
	quitarSortingColumnas();
	inicializarSortingColumnas();
	ejecutarConsultaTotalFilas();
	setPaginaActiva(0);
	List<IBeanAbstracto> listado = ejecutarConsultaPaginada(_paginaActiva);
	mostrarPaginaComponente(listado);
	if (_componenteContenedor != null) {
	    _componenteContenedor.setVisible((getTotalSize() > getPageSize()));
	}
	_iniciadaConsulta = true;
	return listado;
    }

    /**
     * Este metodo inicializa al macrocomponente, agregando el evento onPaging
     * al listener, y dandole la propiedad {@link #setDetailed(boolean)} TRUE
     * para que muestre el detalle de los registro que esta mostrando el
     * componente referencia
     * 
     */
    public void inicializarPagingControl() throws Exception {
	logger.info("[info] --> ejecutando el metodo @inicializarPagingControl()");
	// this.setDetailed(true);
	this.addEventListener("onPaging", this);
	if (_componenteContenedor != null) {
	    _componenteContenedor.setVisible(false);
	}
    }

    /**
     * Implementacion del metodo {@link EventListener#onEvent(Event)} para el
     * control del evento de paginacion.
     * 
     */
    @Override
    public void onEvent(PagingEvent event) throws Exception {
	try {
	    logger.info("[info] --> ejecutando el metodo @onEvent implementacion EventListener<PagingEvent>");
	    if (_paginaActiva != event.getActivePage()) {
		setPaginaActiva(event.getActivePage());
		ejecutarConsultaTotalFilas();
		List<IBeanAbstracto> listado = ejecutarConsultaPaginada(event
			.getActivePage());
		mostrarPaginaComponente(listado);
	    }
	} catch (Exception e) {
	  logger.info("Error:................"+e);
	}
    }

    /**
     * Metodo que pinta las filas en la grilla o el listbox con los resultados
     * que llegan en el listado. A partir de este listado se crea un
     * {@link ListModel} y este modelo se le pasa al
     * {@link #_componenteReferencia} . Ahora se le pasa el render que indica
     * como se van a crear las filas. Ya sea para Grid o Listbox.
     * {@link #_pagingControlFilas} se ajusta a cualquiera de los dos Render
     * soportados hasta el momento
     * 
     * @param listado
     *            Listado de objetos que se mostraran en el componente
     *            referencia
     */
    private void mostrarPaginaComponente(List<IBeanAbstracto> listado)
	    throws Exception {
	logger.info("[info] --> ejecutando el metodo @mostrarPaginaComponente");

	if (_pagingControlFilas instanceof PagingSelectorFilas) {
	    if (_componenteReferencia instanceof Listbox) {
		((Listbox) _componenteReferencia).getItems().clear();
		for (IBeanAbstracto iBeanAbstracto : listado) {
		    Listitem listitem = ((PagingSelectorFilas) _pagingControlFilas)
			    .renderListitem(iBeanAbstracto);
		    ((Listbox) _componenteReferencia).appendChild(listitem);
		}
		((PagingSelectorFilas) _pagingControlFilas)
			.inicializarTipoSeleccion(this);
	    }
	} else {

	    listModelDatos = new ListModelList<IBeanAbstracto>(listado);

	    if (_componenteReferencia instanceof Grid) {
		((Grid) _componenteReferencia).getRows().getChildren().clear();
		((Grid) _componenteReferencia).setModel(listModelDatos);
		((Grid) _componenteReferencia)
			.setRowRenderer(_pagingControlFilas);

	    } else if (_componenteReferencia instanceof Listbox) {

		((Listbox) _componenteReferencia).getItems().clear();
		((Listbox) _componenteReferencia).setModel(listModelDatos);
		((Listbox) _componenteReferencia)
			.setItemRenderer(_pagingControlFilas);
	    }
	}

	setDetailed(true);
    }

    /**
     * Metodo que ejecuta la consulta total filas y ese valor se lo pasa al
     * macrocomponente a traves del metodo {@link #setTotalSize(int)}
     */
    @SuppressWarnings("unchecked")
    private void ejecutarConsultaTotalFilas() throws Exception {
	logger.info("[info] --> ejecutando el metodo @ejecutarConsultaTotalFilas");

	String sqlTotalFilas = "";

	if (_esConsultaDinamica) {
		logger.info("[info] --> ejecutando el metodo @ejecutarConsultaTotalFilas");
	    if (_sqlConsultaDinamica != null && !_sqlConsultaDinamica.isEmpty()) {
	    	logger.info("[info] --> ejecutando el metodo @ejecutarConsultaTotalFilas");
		sqlTotalFilas = crearConsultaDinamicaTotalFilas(_sqlConsultaDinamica);
		logger.info("[info] --> ejecutando el metodo @ejecutarConsultaTotalFilas");
	    } else {
		throw new Exception(
			"[PagingControl] Error : No se han inicializado las variables para hacer la consulta del total de filas (Es Dinamica)");
	    }
	} else {
	    if (_statementCP != null && !_statementCP.isEmpty()) {
	    	logger.info("[info] --> ejecutando el metodo @ejecutarConsultaTotalFilas"+_statementCP);
	    	logger.info("[info] --> ejecutando el metodo @ejecutarConsultaTotalFilas"+_parametros);
		sqlTotalFilas = crearConsultaDinamicaTotalFilas(ParametrizacionFac
			.getFacade()
			.obtenerSqlMapper(_statementCP, _parametros));
		logger.info("[info] --> ejecutando el metodo @ejecutarConsultaTotalFilas");
	    } else {
		throw new Exception(
			"[PagingControl] Error : No se han inicializado las variables para hacer la consulta del total de filas (Es Dinamica)");
	    }
	}

	logger.trace("Consulta sqlTotalFilas ===> " + sqlTotalFilas);

	Map<String, Object> mapaValores = (Map<String, Object>) ParametrizacionFac
		.getFacade().obtenerDinamico(sqlTotalFilas, _parametros);
	Collection<Object> valoresMapa = mapaValores.values();
	BigDecimal totalFilas = new BigDecimal(0);
	if (!mapaValores.isEmpty()) {
	    totalFilas = (BigDecimal) valoresMapa.toArray()[0];
	}

	setTotalSize(totalFilas.intValue());

    }

    /**
     * Metodo que ejecuta la consulta paginada armando los rangos a patrir de la
     * pagina activa o actual y el total de filas por paginas retornando la
     * lista de objetos
     * 
     * @param pagina
     *            Pagina actual del componente que se quiere mostrar
     * @return Lista de objetos que trae de la base de datos en el rango
     *         establecido
     */

    @SuppressWarnings("unchecked")
    private List<IBeanAbstracto> ejecutarConsultaPaginada(int pagina)
	    throws Exception {
	logger.info("[info] --> ejecutando el metodo @ejecutarConsultaPaginada");
	logger.debug("pagina ===>" + pagina);

	Integer inicio = pagina * getPageSize();

	if (!_esConsultaDinamica) {
	    if (_statementCP != null && !_statementCP.isEmpty()) {

		if (!_iniciadaConsulta) {
		    HeadersElement columnas = null;
		    if (_componenteReferencia instanceof Grid) {
			columnas = ((Grid) _componenteReferencia).getColumns();
		    } else if (_componenteReferencia instanceof Listbox) {
			columnas = ((Listbox) _componenteReferencia)
				.getListhead();
		    }

		    if (columnas != null) {
			List<Component> listaColumnas = columnas.getChildren();
			StringBuffer stringBufferOrdenamiento = new StringBuffer();
			int contadorOrden = 0;
			for (Component component : listaColumnas) {
			    if (component instanceof HeaderElement) {
				final HeaderElement column = (HeaderElement) component;

				Boolean ordenamiento = (Boolean) column
					.getAttribute("ordenamiento");

				if (ordenamiento != null && ordenamiento) {
				    String nombreColumna = (String) column
					    .getAttribute("columna");
				    if (nombreColumna.contains("|")) {
					StringTokenizer tokensColumnas = new StringTokenizer(
						nombreColumna, "|");
					StringBuffer ordenamientoColumnas = new StringBuffer();
					while (tokensColumnas.hasMoreTokens()) {
					    String siguienteColumna = tokensColumnas
						    .nextToken();
					    if (!siguienteColumna.isEmpty()) {
						ordenamientoColumnas
							.append(siguienteColumna
								+ " ASC");
						if (tokensColumnas
							.hasMoreTokens()) {
						    ordenamientoColumnas
							    .append(", ");
						}
					    }
					}
					stringBufferOrdenamiento
						.append((contadorOrden != 0 ? ","
							: "")
							+ ordenamientoColumnas
								.toString());
				    } else {
				    	
					stringBufferOrdenamiento
						.append((contadorOrden != 0 ? ","
							: "")
							+ nombreColumna
							+ ((nombreColumna.contains(" DESC"))?"":" ASC "));
				    }
				    contadorOrden++;

				}
			    }
			}
			_parametros
				.put("NOMBRECOLUMNA_ORDEN",
					stringBufferOrdenamiento.toString()
						.isEmpty() ? null
						: stringBufferOrdenamiento
							.toString());
		    }
		}

		return (List<IBeanAbstracto>) ParametrizacionFac.getFacade()
			.obtenerListado(_statementCP, _parametros, inicio,
				getPageSize());
	    } else {
		throw new Exception(
			"[PagingControl] Error : Debe inicializar la variable statementConsultaPaginada para hacer la consulta paginada");
	    }
	} else {
	    if (_sqlConsultaDinamica != null && !_sqlConsultaDinamica.isEmpty()) {
		return ParametrizacionFac.getFacade().listadoDinamico(
			_tipoClase, _sqlConsultaDinamica, _parametros, inicio,
			getPageSize());
	    } else {
		throw new Exception(
			"[PagingControl] Error : Si la va a trabajar con consultas dinamicas. Debe inicializar la variable sqlConsultaDinamica para hacer la consulta paginada");
	    }
	}

    }

    /**
     * Agrega los atributos al las columnas para hacer el ordenamiento ya sea
     * ascendente o descente por cada una de ellas, de forma respectiva. Es
     * decir a la primera columa que encuentra le agrega el primer atributo
     * encontrado en la lista y asi sucesivamente. Si hay mas atributos que
     * columas solo se agregaran hasta que la lista de columnas llegue al final
     * Si hay mas columnas que atributos solo se agregaran hasta que la lista de
     * atributos llegue al final
     * 
     * @param listaAtributos
     *            {@link List} de {@link PagingControlColumnas } donde vienen los
     *            atributos nombre de columna y ordenamiento
     * @throws Exception
     */
    public void agregarAtributosColumnas(
	    List<PagingControlColumnas> listaAtributos) throws Exception {
	logger.info("[info] --> ejecutando el metodo @agregarAtributosColumnas");
	logger.info("listaAtributos ===>" + listaAtributos);
	if (_componenteReferencia != null) {
	    HeadersElement columnas = null;
	    if (_componenteReferencia instanceof Grid) {
		columnas = ((Grid) _componenteReferencia).getColumns();
	    } else if (_componenteReferencia instanceof Listbox) {
		columnas = ((Listbox) _componenteReferencia).getListhead();
	    }

	    if (columnas != null) {
		List<Component> listaColumnas = columnas.getChildren();
		for (int i = 0; i < listaAtributos.size(); i++) {
		    PagingControlColumnas pgcc = listaAtributos.get(i);
		    if (listaColumnas.size() > i) {
			Component componentColumna = listaColumnas.get(i);
			componentColumna.setAttribute("columna",
				pgcc.getNombreColumna());
			componentColumna.setAttribute("ordenamiento",
				pgcc.getOrdenamiento());
		    }
		}
	    } else {
		throw new Exception(
			"El componente de referencia al cual le va a hacer la paginacion no tiene columnas");
	    }
	} else {
	    throw new Exception(
		    "Debe setear el componente de referencia al cual le va a hacer la paginacion antes de agregarle los atricutos a las columnas");
	}
    }

    /**
     * metodo que reinicia el sort de todas las columnas y las deja como si
     * fuera la primera vez que se colsulta
     * 
     * @throws Exception
     */
    private void quitarSortingColumnas() throws Exception {
	logger.info("[info] --> ejecutando el metodo @quitarSortingColumnas");
	HeadersElement columnas = null;
	if (_componenteReferencia instanceof Grid) {
	    columnas = ((Grid) _componenteReferencia).getColumns();
	} else if (_componenteReferencia instanceof Listbox) {
	    columnas = ((Listbox) _componenteReferencia).getListhead();
	}

	if (columnas != null) {
	    List<Component> listaColumnas = columnas.getChildren();
	    for (Component component : listaColumnas) {
		Boolean ordenamiento = (Boolean) component
			.getAttribute("ordenamiento");
		if (ordenamiento != null && ordenamiento) {
		    if (component instanceof Column) {
			((Column) component).setSortDirection("natural");
			((Column) component).setSort("auto");

		    } else if (component instanceof Listheader) {
			((Listheader) component).setSortDirection("natural");
			((Listheader) component).setSort("auto");
		    }
		}
	    }
	}

	_ordenColumna = "";
	_ordenColumnaSort = "";
    }

    /**
     * Metodo para inicializar el ordenamiento por columnas ya sea ascendente o
     * descendente. Controlando el evento on_sort de las columnas. En ese evento
     * on_sort armara la consulta ordenada a la base de datos y luego se
     * pintaran los resultados ordenados que se obtienen
     */
    private void inicializarSortingColumnas() {
	logger.info("[info] --> ejecutando el metodo @inicializarSortingColumnas");
	HeadersElement columnas = null;
	if (_componenteReferencia instanceof Grid) {
	    columnas = ((Grid) _componenteReferencia).getColumns();
	} else if (_componenteReferencia instanceof Listbox) {
	    columnas = ((Listbox) _componenteReferencia).getListhead();
	}

	if (columnas != null) {
	    List<Component> listaColumnas = columnas.getChildren();
	    for (Component component : listaColumnas) {
		if (component instanceof HeaderElement) {
		    final HeaderElement column = (HeaderElement) component;

		    Boolean ordenamiento = (Boolean) column
			    .getAttribute("ordenamiento");

		    if (ordenamiento != null && ordenamiento) {
			if (column instanceof Column) {
			    ((Column) column)
				    .setSortAscending(new ComparadorPaging());
			    ((Column) column)
				    .setSortDescending(new ComparadorPaging());
			} else if (column instanceof Listheader) {
			    ((Listheader) column)
				    .setSortAscending(new ComparadorPaging());
			    ((Listheader) column)
				    .setSortDescending(new ComparadorPaging());
			}

			column.addEventListener(Events.ON_SORT,
				new EventListener<SortEvent>() {

				    @Override
				    public void onEvent(SortEvent sortEvent)
					    throws Exception {
					try {
					    logger.info("[info] --> ejecutando el metodo @onEvent(),Events.ON_SORT");
					    logger.debug("column.getLabel() ===> "
						    + column.getLabel());
					    if (_iniciadaConsulta) {
						String nombreColumna = (String) column
							.getAttribute("columna");
						nombreColumna=nombreColumna.replace(" DESC", "");
						_parametros
							.remove(_ordenColumna);
						if (sortEvent.isAscending()) {
						    if (!_ordenColumnaSort
							    .equals(nombreColumna
								    + "_ASC")) {
							_ordenColumna = nombreColumna;
							// _parametros.put("orderbyasc","orderbyasc");
							// _parametros.remove("orderbydesc");

							if (nombreColumna
								.contains("|")) {
							    StringTokenizer tokensColumnas = new StringTokenizer(
								    nombreColumna,
								    "|");
							    StringBuffer ordenamientoColumnas = new StringBuffer();
							    while (tokensColumnas
								    .hasMoreTokens()) {
								String siguienteColumna = tokensColumnas
									.nextToken();
								if (!siguienteColumna
									.isEmpty()) {
								    ordenamientoColumnas
									    .append(siguienteColumna
										    + " ASC");
								    if (tokensColumnas
									    .hasMoreTokens()) {
									ordenamientoColumnas
										.append(", ");
								    }
								}
							    }
							    _parametros
								    .put("NOMBRECOLUMNA_ORDEN",
									    ordenamientoColumnas
										    .toString());
							} else {
							    _parametros
								    .put("NOMBRECOLUMNA_ORDEN",
									    nombreColumna
										    + " ASC ");
							}

							ejecutarConsultaTotalFilas();
							List<IBeanAbstracto> listado = ejecutarConsultaPaginada(_paginaActiva);
							mostrarPaginaComponente(listado);
							_ordenColumnaSort = nombreColumna
								+ "_ASC";
						    }
						} else {
						    if (!_ordenColumnaSort
							    .equals(nombreColumna
								    + "_DESC")) {
							_ordenColumna = nombreColumna;
							// _parametros.put("orderbydesc","orderbydesc");
							// _parametros.remove("orderbyasc");

							if (nombreColumna
								.contains("|")) {
							    StringTokenizer tokensColumnas = new StringTokenizer(
								    nombreColumna,
								    "|");
							    StringBuffer ordenamientoColumnas = new StringBuffer();
							    while (tokensColumnas
								    .hasMoreTokens()) {
								String siguienteColumna = tokensColumnas
									.nextToken();
								if (!siguienteColumna
									.isEmpty()) {
								    ordenamientoColumnas
									    .append(siguienteColumna
										    + " DESC");
								    if (tokensColumnas
									    .hasMoreTokens()) {
									ordenamientoColumnas
										.append(", ");
								    }
								}
							    }
							    _parametros
								    .put("NOMBRECOLUMNA_ORDEN",
									    ordenamientoColumnas
										    .toString());
							} else {
							    _parametros
								    .put("NOMBRECOLUMNA_ORDEN",
									    nombreColumna
										    + " DESC ");
							}

							ejecutarConsultaTotalFilas();
							List<IBeanAbstracto> listado = ejecutarConsultaPaginada(_paginaActiva);
							mostrarPaginaComponente(listado);
							_ordenColumnaSort = nombreColumna
								+ "_DESC";
						    }

						}

					    }
					} catch (Exception e) {
						logger.info("Error:................"+e);
					}
				    }

				});
		    }

		}
	    }
	}
    }

    /**
     * Agregar evento onSelect cuando sea un listbox el que se esta manipulando
     */
    @SuppressWarnings("unused")
    private void onSelectComponenteReferencia() {
	if (_componenteReferencia != null) {
	    if (_componenteReferencia instanceof Listbox) {
		if (!(_pagingControlFilas instanceof PagingSelectorFilas)) {
		    logger.info("[info] --> ejecutando el metodo @onSelectComponenteReferencia");
		    ((Listbox) _componenteReferencia).addEventListener(
			    Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event)
					throws Exception {
				    logger.info("Seleccionado iBeanAbstracto del _componenteReferencia");
				    try {
					IBeanAbstracto iBeanAbstracto = ((Listbox) _componenteReferencia)
						.getSelectedItem().getValue();
					_pagingControlFilas
						.setRegistroSeleccionado(iBeanAbstracto);

				    } catch (Exception e) {
					logger.info("Error:................"+e);
				    }
				}

			    });
		}
	    }

	}
    }

    // metodos setter y getter de las variables

    public String getStatementConsultaPaginada() {
	return _statementCP;
    }

    /**
     * 
     * @param statementCP
     *            id de la instruccion en el xml que va a hacer la consulta
     *            paginada a la base de datos
     */
    public void setStatementConsultaPaginada(String statementCP) {
	_statementCP = statementCP;
    }

    public Map<String, Object> getParametros() {
	return _parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
	_parametros = parametros;
    }

    public PagingControlFilas getPagingControlFilas() {
	return _pagingControlFilas;
    }

    public void setPagingControlFilas(PagingControlFilas pagingControlFilas) {
	_pagingControlFilas = pagingControlFilas;
    }

    public int getPaginaActiva() {
	return _paginaActiva;
    }

    public void setPaginaActiva(int paginaActiva) {
	_paginaActiva = paginaActiva;
	setActivePage(_paginaActiva);
    }

    public String getOrdenColumna() {
	return _ordenColumna;
    }

    public void setComponenteContenedor(Component componenteContenedor) {
	_componenteContenedor = componenteContenedor;
    }

    public Component getComponenteContenedor() {
	return _componenteContenedor;
    }

    public Boolean esConsultaDinamica() {
	return _esConsultaDinamica;
    }

    public void setConsultaDinamica(Boolean consultaDinamica) {
	_esConsultaDinamica = consultaDinamica;
    }

    public String getSqlConsultaDinamica() {
	return _sqlConsultaDinamica;
    }

    public void setSqlConsultaDinamica(String sqlConsultaDinamica) {
	_sqlConsultaDinamica = sqlConsultaDinamica;
    }

    public void setTipoClase(Object tipoClase) {
	_tipoClase = tipoClase;
    }

    public Object getTipoClase() {
	return _tipoClase;
    }

    /**
     * 
     * @return Retorna el componente referencia al cual se le esta haciendo la
     *         paginacion
     */
    public MeshElement getComponenteReferencia() {
	return _componenteReferencia;
    }

    /**
     * 
     * @param componenteReferencia
     *            Componente referencia al cual se le va a hacer la paginacion
     */
    public void setComponenteReferencia(MeshElement componenteReferencia)
	    throws Exception {
	if ((componenteReferencia instanceof Grid)
		|| (componenteReferencia instanceof Listbox))
	    _componenteReferencia = componenteReferencia;
	else
	    throw new Exception(
		    "Error : El MeshElement debe ser instancia de Grid o de Listbox");
    }

    private String crearConsultaDinamicaTotalFilas(String sqlConsultaDinamica)
	    throws Exception {
	logger.info("[info] --> ejecutando el metodo @crearConsultaDinamicaTotalFilas");

	sqlConsultaDinamica = sqlConsultaDinamica.replaceAll("[\n\r\t]", " ");

	int indiceFrom = 0;
	
	if (getTablaPadreFrom() != null && !getTablaPadreFrom().isEmpty()) {

	    if (sqlConsultaDinamica.contains(getTablaPadreFrom())) {
		int indexTable = sqlConsultaDinamica
			.lastIndexOf(getTablaPadreFrom());

		String sqlTablaPadre = sqlConsultaDinamica.substring(0,
			indexTable);
		indiceFrom = sqlTablaPadre
			.lastIndexOf(ISentenciasSQL.SQL_FROM);
		
	    } else {
		throw new Exception(
			"[PagingControl] Error : La clausula FROM de la consulta paginada No se encuentra"
				+ getTablaPadreFrom());
	    }

	} else {
	    /*
	     * se pone lastIndexOf para obtener el ultimo from de la consulta
	     * por si tiene subselect
	     */
	    if (sqlConsultaDinamica.contains(ISentenciasSQL.SQL_FROM)) {
		indiceFrom = sqlConsultaDinamica
			.lastIndexOf(ISentenciasSQL.SQL_FROM);
	    } else if (sqlConsultaDinamica.contains(ISentenciasSQL.SQL_FROM
		    .toLowerCase())) {
		indiceFrom = sqlConsultaDinamica
			.lastIndexOf(ISentenciasSQL.SQL_FROM.toLowerCase());
	    } else {
		throw new Exception(
			"[PagingControl] Error : La clausula FROM de la consulta paginada No se encuentra");
	    }

	}
	
	 
	sqlConsultaDinamica = sqlConsultaDinamica.substring(indiceFrom,
		sqlConsultaDinamica.length());

	int indiceOrder = -1;

	if (sqlConsultaDinamica.contains(ISentenciasSQL.SQL_ORDER)) {
	    indiceOrder = sqlConsultaDinamica.indexOf(ISentenciasSQL.SQL_ORDER);
	} else if (sqlConsultaDinamica.contains(ISentenciasSQL.SQL_ORDER
		.toLowerCase())) {
	    indiceOrder = sqlConsultaDinamica.indexOf(ISentenciasSQL.SQL_ORDER
		    .toLowerCase());
	}

	if (indiceOrder != -1) {
	    sqlConsultaDinamica = sqlConsultaDinamica.substring(0, indiceOrder);
	}

	return "SELECT COUNT(1) " + sqlConsultaDinamica;
    }

    public String getTablaPadreFrom() {
	return tablaPadreFrom;
    }

    public void setTablaPadreFrom(String tablaPadreFrom) {
	this.tablaPadreFrom = tablaPadreFrom;
    }

    /**
     * Clase que implementa la interface {@link Comparator } para que esta
     * manipule el sortAscendig y el sortDescendig de las columnas del
     * componente referencia.
     * 
     * @author CASEWAREDES05
     * 
     */
    public class ComparadorPaging implements Comparator<Object> {
	@Override
	/**
	 * Metodo que hace la compracion entre un componente fila (ya sea Listitem o Row) y otro para ordenarlos en el componente referencia.
	 * En este caso no va a hacer nada haciendo que retorne cero ya que los resultados llegaran ordenados en la lista que se va pintar
	 */
	public int compare(Object arg0, Object arg1) {
	    return 0;
	}

    }

}
