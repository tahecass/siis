package com.siis.framework.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Rows;

import com.casewaresa.framework.Filter.action.VentanaFiltroAction;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.macros.GridDetail;

/**
 * @author Futco
 * @name IActionGrid.java
 * @date 3/11/2010
 * @desc es una interfaz que complementa la funcionalidad de algunos de los
 *       metodos crearRowDesdeDto(en sus dos formas) proporcionados por la clase
 *       AssemblerStandard, debido a que define las 3 acciones que se deben
 *       implementar y opcionalmente usar en los action's que utilicen dichos
 *       metodos de esa clase.
 */
public abstract class IActionGrid2 extends ActionStandardBorder {

	private static final long serialVersionUID = 2480568341920509801L;

	private Button idZBtnAgregar;
	private Button idZBtnVerMas;
	private Button idZBtnEditar;
	private Button idZBtnGuardar;
	private Button idZBtnEliminar;
	private GridDetail gridDetalle;
	private Button idZBtnFiltrar;
	protected Map<String, Object> parametros;
	protected boolean estadoEditar = false;
	protected boolean estadoAgregar = false;
	private boolean ismenupopupEnabled = false;
	private String idMapper;
	private List<Listheader> listaHeaders;
	private IActionGrid2 action;
	private String[] detalle;

	private Button idZBtnDuplicar;

	public abstract void cargarComponentesVista();

	public abstract void initOtherParameter();

	/**
	 * @type Metodo de la clase IActionGrid.java
	 * @name getParameterMap
	 * @desc este metodo debe definir en su implementacion la funcionalidad
	 *       requerida para mostrar los detalles de un DTO
	 */
	@SuppressWarnings("unchecked")
	public void getParameterMap() {
		log.info("Ejecutando el metodo[getParameterMap]");
		if (Executions.getCurrent().getArg().size() != 0)
			this.parametros = (Map<String, Object>) Executions.getCurrent()
					.getArg();

		if (parametros.containsKey("ESTADO_EDICION"))
			this.estadoEditar = (Boolean) this.parametros.get("ESTADO_EDICION");

		if (parametros.containsKey("ESTADO_REGISTRO"))
			this.estadoAgregar = (Boolean) this.parametros
					.get("ESTADO_REGISTRO");
	}

	/**
	 * @type Metodo de la clase IActionGrid.java
	 * @name onListarDetalle
	 * @desc este metodo debe definir en su implementacion la funcionalidad
	 *       requerida para mostrar los detalles de un DTO
	 */
	public abstract void onListarDetalle();

	/**
	 * @type Metodo de la clase IActionGrid.java
	 * @name onVerMasMaestro
	 * @param beanAbstracto
	 * @desc este metodo debe definir en su implementacion la funcionalidad
	 *       requerida para mostrar los detalles de un DTO que no fueron
	 *       mostrados en el Row retornado por el medoto crearRowDesdeDto de la
	 *       clase AssemblerStandar
	 */
	public abstract void onVerMasMaestro(IBeanAbstracto beanAbstracto);

	/**
	 * @type Metodo de la clase IActionGrid.java
	 * @name onEditarMaestro
	 * @param beanAbstracto
	 * @desc este metodo debe definir en su implementacion la funcionalidad
	 *       requerida para editar los detalles del DTO manejado en el action
	 *       que implementa dicha interfaz
	 */
	public abstract void onEditarMaestro(IBeanAbstracto beanAbstracto);

	/**
	 * @type Metodo de la clase IActionGrid.java
	 * @name onBorrarMaestro
	 * @param beanAbstracto
	 * @desc este metodo debe definir en su implementacion la funcionalidad
	 *       requerida para eliminar un detalle del DTO manejado por el action
	 *       que implemento la interfaz
	 */
	public abstract void onBorrarMaestro(IBeanAbstracto beanAbstracto);

	public abstract void onAgregarDetalle();

	public abstract void parametrizarEventos();

	public void setComponentes(Button agregar, Button verMas, Button editar,
			Button eliminar, GridDetail gridDetalle, Button filtrar) {
		this.idZBtnAgregar = agregar;
		this.idZBtnEditar = editar;
		this.idZBtnEliminar = eliminar;
		this.idZBtnVerMas = verMas;
		this.gridDetalle = gridDetalle;
		this.idZBtnFiltrar = filtrar;

	}

	public void setComponentes(Button agregar, Button verMas, Button editar,
			Button eliminar, GridDetail gridDetalle, Button filtrar,
			Button guardar, Button duplicar) {
		this.idZBtnAgregar = agregar;
		this.idZBtnEditar = editar;
		this.idZBtnEliminar = eliminar;
		this.idZBtnVerMas = verMas;
		this.gridDetalle = gridDetalle;
		this.idZBtnFiltrar = filtrar;
		this.idZBtnGuardar = guardar;
		this.idZBtnDuplicar = duplicar;
	}

	public void setFiltroDinamico(boolean ismenupopupEnabled, String idMapper,
			List<Listheader> lista, IActionGrid2 action, String... values) {
		this.ismenupopupEnabled = ismenupopupEnabled;
		this.idMapper = idMapper;
		this.listaHeaders = lista;
		this.action = action;
		this.detalle = values;

	}

	public void setSql(String sql) {
	}

	public void setObject(Object obj) {
	}

	public Object getObject() {
		return null;
	}

	public void perform() {
	}

	/**
	 * @type Metodo de la clase IActionGrid.java
	 * @name onGuardarDetalle
	 * @param
	 * @desc este metodo no es obligatorio, debe definir en su implementacion la
	 *       funcionalidad requerida para guardar un detalle del DTO manejado
	 *       por el action que implemento la interfaz
	 */
	public void onGuardarDetalle() {
	}

	public void inicializarEventos(Button agregar, Button verMas,
			Button editar, Button eliminar, final Button filtrar,
			final Component... component) {

		if (filtrar != null) {

			filtrar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							if (ismenupopupEnabled) {
								if (component[2] != null) {
									((Menupopup) component[2]).open(filtrar);
									agreagarEventoMenupopUp(filtrar,
											((Menupopup) component[2]));
								}
							} else {
								agregarEventoFiltrar();
							}

						}
					});

			filtrar.setAutodisable(filtrar.getId());
		}

		if (component != null && component.length > 0) {

			if (component[0] != null) {
				component[0].addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								perform();
							}
						});
			}

			if (component[1] != null) {
				component[1].addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								onGuardarDetalle();
							}
						});
			}
		}

		if (agregar != null) {

			agregar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							onAgregarDetalle();
						}
					});
			agregar.setAutodisable(agregar.getId());
		}

		if (editar != null) {

			editar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (gridDetalle.getSelectedItem() != null)
								onEditarMaestro((IBeanAbstracto) gridDetalle
										.getSelectedItem().getValue());
						}
					});
			editar.setAutodisable(editar.getId());
		}

		if (verMas != null) {

			verMas.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (gridDetalle.getSelectedItem() != null)
								onVerMasMaestro((IBeanAbstracto) gridDetalle
										.getSelectedItem().getValue());
						}
					});
			verMas.setAutodisable(verMas.getId());
		}

		if (eliminar != null) {

			eliminar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (gridDetalle.getSelectedItem() != null)
								onBorrarMaestro((IBeanAbstracto) gridDetalle
										.getSelectedItem().getValue());
						}
					});
		}
	}

	public void agregarEventoFiltrar() throws SuspendNotAllowedException,
			InterruptedException {

		if (parametros == null)
			parametros = new HashMap<String, Object>();

		parametros.put("listaDetalles", gridDetalle);

		VentanaFiltroDetalleGridAction win = (VentanaFiltroDetalleGridAction) Executions
				.createComponents("/ventana_filtro_detalle_grid.zul", null,
						parametros);

		win.doModal();

	}

	public void agreagarEventoMenupopUp(Button filtrar,
			final Menupopup menupopup) {

		if (!menupopup.getFirstChild().isListenerAvailable(Events.ON_CLICK,
				true))
			menupopup.getFirstChild().addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							parametrizarVentanaFiltroDinamico();
						}
					});

		if (!menupopup.getLastChild()
				.isListenerAvailable(Events.ON_CLICK, true))
			menupopup.getLastChild().addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							action.setAttribute("optFiltroAplicado", "N");
							onListarDetalle();
							agregarEventoFiltrar();
						}
					});
	}

	public void parametrizarVentanaFiltroDinamico() {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("id", idMapper);
			parametros.put("lista_headers", listaHeaders);
			parametros.put("action", action);
			parametros.put("cargarFiltros", ismenupopupEnabled);
			parametros.put("detalle", detalle);

			VentanaFiltroAction w = (VentanaFiltroAction) Executions
					.createComponents("/ventana_filtro.zul", null, null);

			action.setAttribute("optFiltroAplicado", "D");
			action.setAttribute("CnFiltro", "I");
			w.doModal(parametros);

		} catch (Exception e) {
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
			log.error(e.getMessage(), e);
		}

	}

	public void habilitarDeshabilitarBotones(boolean isEditar) {
		log.info("ESTADO AGREGAR: " + this.estadoAgregar + " ESTADO EDITAR: "
				+ isEditar);

		// Se coloca la variable isEditar = true, dado que no es necesario
		// validar que el maestro este
		// en edici�n para habilitar los botones, si se requiere que se valide
		// nuevamente el estado del editar del maestro
		// eliminar la linea isEditar=true;

		isEditar = true;
		if (this.idZBtnAgregar != null) {
			if (isEditar) {
				this.idZBtnAgregar.setDisabled(this.estadoAgregar);
			} else {
				this.idZBtnAgregar.setDisabled(!isEditar);
			}
		}

		if (this.idZBtnEditar != null) {
			this.idZBtnEditar.setVisible(isEditar);
			this.idZBtnEditar.setDisabled(!isEditar);
		}

		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(!isEditar);

		if (this.idZBtnVerMas != null) {
			// Se invierte la validaci�n del isEditar, para que est� siempre
			// visible el bot�n ver M�s
			this.idZBtnVerMas.setVisible(isEditar);
			this.idZBtnVerMas.setDisabled(!isEditar);
		}

		if (this.idZBtnFiltrar != null)
			this.idZBtnFiltrar.setDisabled(false);

	}

	public void habilitarBotones() {

		if (this.idZBtnAgregar != null)
			this.idZBtnAgregar.setDisabled(this.estadoAgregar);

		if (this.idZBtnEditar != null) {
			this.idZBtnEditar.setDisabled(false);
			this.idZBtnEditar.setVisible(true);
		}

		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(false);

		if (this.idZBtnVerMas != null) {
			this.idZBtnVerMas.setDisabled(false);
			// Se coloca el verMas en visible tru, para que est� siempre visible
			// el bot�n ver M�s
			this.idZBtnVerMas.setVisible(true);
		}

		if (this.idZBtnFiltrar != null)
			this.idZBtnFiltrar.setDisabled(false);

		estadoEditar = true;
	}

	public void habilitarTodosLosBotones() {

		if (this.idZBtnAgregar != null)
			this.idZBtnAgregar.setDisabled(false);

		if (this.idZBtnEditar != null) {
			this.idZBtnEditar.setDisabled(false);
		}

		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(false);

		if (this.idZBtnVerMas != null) {
			this.idZBtnVerMas.setDisabled(false);
		}

		if (this.idZBtnFiltrar != null)
			this.idZBtnFiltrar.setDisabled(false);
	}

	public void deshabilitarBotones() {
		if (this.idZBtnAgregar != null)
			this.idZBtnAgregar.setDisabled(this.estadoAgregar);

		if (this.idZBtnEditar != null) {
			this.idZBtnEditar.setDisabled(true);
			this.idZBtnEditar.setVisible(false);
		}
		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(true);

		if (this.idZBtnVerMas != null) {
			this.idZBtnVerMas.setDisabled(true);
			this.idZBtnVerMas.setVisible(true);
		}
		if (this.idZBtnFiltrar != null)
			this.idZBtnFiltrar.setDisabled(false);

		estadoEditar = false;
	}

	public void deshabilitarTodosLosBotones() {
		if (this.idZBtnAgregar != null)
			this.idZBtnAgregar.setDisabled(true);

		if (this.idZBtnEditar != null) {
			this.idZBtnEditar.setDisabled(true);
		}
		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(true);

		if (this.idZBtnVerMas != null) {
			this.idZBtnVerMas.setDisabled(true);
		}
		if (this.idZBtnFiltrar != null)
			this.idZBtnFiltrar.setDisabled(true);
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public boolean isEstadoEditar() {
		return estadoEditar;
	}

	public void setEstadoEditar(boolean estadoEditar) {
		this.estadoEditar = estadoEditar;
	}

	public boolean isEstadoAgregar() {
		return estadoAgregar;
	}

	public void setEstadoAgregar(boolean estadoAgregar) {
		this.estadoAgregar = estadoAgregar;
	}

	public void habilitarButtons(boolean value) {
		idZBtnEditar.setDisabled(value);
		idZBtnGuardar.setDisabled(value);
		idZBtnEliminar.setDisabled(value);
		if (idZBtnDuplicar != null)
			idZBtnDuplicar.setDisabled(value);
	}

	@SuppressWarnings("deprecation")
	public Detail agregarDetail(GridDetail gridDetail, String... columnas)
			throws Exception {
		log.info("Ejecutando el metodo [agregarDetail...]");

		
		Detail detail = new Detail();
		Groupbox groupbox = new Groupbox();
		groupbox.setContentStyle("overflow:auto");
		groupbox.setHflex("true");
		
		if(gridDetail.getAttribute("CAPTION")!=null){
			Caption caption= new Caption(gridDetail.getAttribute("CAPTION").toString());
			groupbox.appendChild(caption);
			groupbox.setMold("3d");
			groupbox.setClosable(false);
		}
		
		gridDetail.setFixedLayout(true);
		detail.appendChild(groupbox);
		detail.getFirstChild().appendChild(gridDetail);

		gridDetail.appendChild(agregarColumns(columnas));
		gridDetail.appendChild(new Rows());

		return detail;
	}

	private Columns agregarColumns(String... columnas) throws Exception {
		Columns columns = new Columns();
		columns.setMenupopup("auto");
		for (String string : columnas) {
			Column column = new Column();
			column.setSort("auto");
			if (string.contains("#")) {
				column.setLabel(string.substring(0, string.indexOf("#")));
				agregarTamanoAlign(column,
						string.substring(string.indexOf("#") + 1));
			} else
				column.setLabel(string);

			columns.appendChild(column);
		}

		return columns;
	}

	private void agregarTamanoAlign(Column column, String attribute) {
		if (attribute.contains("#")) {
			column.setWidth(attribute.substring(0, attribute.indexOf("#")));
			column.setAlign(attribute.substring(attribute.indexOf("#") + 1));
		} else
			column.setWidth(attribute);
	}

	/**
	 * @return the gridDetalle
	 */
	public GridDetail getGridDetalle() {
		return gridDetalle;
	}

}