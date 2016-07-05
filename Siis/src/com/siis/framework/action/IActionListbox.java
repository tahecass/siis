package com.siis.framework.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menupopup;

import com.casewaresa.framework.Filter.action.VentanaFiltroAction;
import com.casewaresa.framework.Filter.dto.CriterioWhere;
import com.casewaresa.framework.Filter.dto.OrderBy;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.util.Utilidades;
import com.casewaresa.iceberg_aa.dto.AATEjecutable;

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
public abstract class IActionListbox extends ActionStandardBorder {

	private static final long serialVersionUID = 2480568341920509801L;

	private Button idZBtnAgregar;
	private Button idZBtnVerMas;
	private Button idZBtnEditar;
	private Button idZBtnGuardar;
	private Button idZBtnEliminar;
	private Listbox listaDetalle;

	private Button idZBtnFiltrar;
	protected Map<String, Object> parametros;
	protected boolean estadoEditar = false;
	protected boolean estadoAgregar = false;
	private boolean ismenupopupEnabled = false;
	private String idMapper;

	private List<Component> listaHeaders;

	private IActionListbox action;

	private String filtroAdicional;
	
	public List<CriterioWhere> listaWhere;
	public List<OrderBy> listaOrderBy;

	private Button idZBtnDuplicar;

	public abstract void cargarComponentesVista() throws Exception;

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

	public abstract void parametrizarEventos() throws Exception;

	public void setComponentes(Button agregar, Button verMas, Button editar,
			Button eliminar, Listbox listbox, Button filtrar) {
		this.idZBtnAgregar = agregar;
		this.idZBtnEditar = editar;
		this.idZBtnEliminar = eliminar;
		this.idZBtnVerMas = verMas;
		this.listaDetalle = listbox;
		this.idZBtnFiltrar = filtrar;

	}

	public void setComponentes(Button agregar, Button verMas, Button editar,
			Button eliminar, Listbox listbox, Button filtrar, Button guardar,
			Button duplicar) {
		this.idZBtnAgregar = agregar;
		this.idZBtnEditar = editar;
		this.idZBtnEliminar = eliminar;
		this.idZBtnVerMas = verMas;
		this.listaDetalle = listbox;
		this.idZBtnFiltrar = filtrar;
		this.idZBtnGuardar = guardar;
		this.idZBtnDuplicar = duplicar;
	}

	public void setFiltroDinamico(boolean ismenupopupEnabled, String idMapper,
			List<Component> lista, IActionListbox action, String filtroAdicional) {
		this.ismenupopupEnabled = ismenupopupEnabled;
		this.idMapper = idMapper;
		this.listaHeaders = lista;
		this.action = action;
		this.filtroAdicional = filtroAdicional;
	}

	public void setSql(String sql) {
	}

	public void setObject(Object obj) {
	}

	public Object getObject() {
		return null;
	}

	public void execute() {
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
			filtrar.setAutodisable(filtrar.getId());
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
								execute();
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
			agregar.setAutodisable(agregar.getId());
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
			editar.setAutodisable(editar.getId());
			editar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (listaDetalle.getSelectedItem() != null)
								onEditarMaestro((IBeanAbstracto) listaDetalle
										.getSelectedItem().getValue());
						}
					});
			editar.setAutodisable(editar.getId());
		}

		if (verMas != null) {
			verMas.setAutodisable(verMas.getId());
			verMas.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (listaDetalle.getSelectedItem() != null)
								onVerMasMaestro((IBeanAbstracto) listaDetalle
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
							if (listaDetalle.getSelectedItem() != null)
								onBorrarMaestro((IBeanAbstracto) listaDetalle
										.getSelectedItem().getValue());
						}
					});
			eliminar.setAutodisable(eliminar.getId());
		}
	}

	public void agregarEventoFiltrar() throws SuspendNotAllowedException,
			InterruptedException {

		if (parametros == null)
			parametros = new HashMap<String, Object>();

		parametros.put("listaDetalles", listaDetalle);

		VentanaFiltroDetalleAction win = (VentanaFiltroDetalleAction) Executions
				.createComponents("/ventana_filtro_detalle.zul", null,
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
		log.info("Ejecutando el mŽtodo [ parametrizarVentanaFiltroDinamico ]...");
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("id", idMapper);
			parametros.put("lista_headers", listaHeaders);
			parametros.put("action", action);
			parametros.put("cargarFiltros", ismenupopupEnabled);
			parametros.put("filtro_adicional", filtroAdicional);
			
			parametros.put("listado_where", listaWhere);
			parametros.put("listado_orderBy", listaOrderBy);

			if (this.getParametros() != null
					&& this.getParametros().containsKey("ORDENAMIENTO_DEFECTO")) {
				parametros.put("ORDENAMIENTO_DEFECTO", this.getParametros()
						.get("ORDENAMIENTO_DEFECTO"));
			}

			if (this.getParametros() != null
					&& this.getParametros().containsKey(
							"CRITERIO_WHERE_DEFECTO")) {
				parametros.put("CRITERIO_WHERE_DEFECTO", this.getParametros()
						.get("CRITERIO_WHERE_DEFECTO"));
			}

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
		log.info("Ejecutando el mŽtodo [ habilitarDeshabilitarBotones ]...");
		log.info("ESTADO AGREGAR: " + this.estadoAgregar + " ESTADO EDITAR: "
				+ isEditar);

		// Se coloca la variable isEditar = true, dado que no es necesario
		// validar que el maestro este
		// en ediciï¿½n para habilitar los botones, si se requiere que se valide
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
			// Se invierte la validaciï¿½n del isEditar, para que estï¿½ siempre
			// visible el botï¿½n ver Mï¿½s
			this.idZBtnVerMas.setVisible(isEditar);
			this.idZBtnVerMas.setDisabled(!isEditar);
		}

		if (this.idZBtnFiltrar != null)
			this.idZBtnFiltrar.setDisabled(false);

	}

	public void habilitarBotones() {
		log.info("Ejecutando el Methodo [ habilitarBotones ]");
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
			// Se coloca el verMas en visible tru, para que estï¿½ siempre
			// visible
			// el botï¿½n ver Mï¿½s
			this.idZBtnVerMas.setVisible(true);
		}

		if (this.idZBtnFiltrar != null)
			this.idZBtnFiltrar.setDisabled(false);

		estadoEditar = true;
	}

	public void habilitarTodosLosBotones() {
		log.info("Ejecutando el Methodo [ habilitarTodosLosBotones ]");
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
		log.info("Ejecutando el Methodo [ habilitarTodosLosBotones ]");
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
		log.info("Ejecutando el Methodo [ deshabilitarTodosLosBotones ]");
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

	// public Listitem agregarDetail(IBeanAbstracto dto, Listbox lista, int
	// span,
	// String widthEstado, boolean aplicaEstado, boolean aplicaDetail,
	// String lblcaption, String... nobreColumnas) {
	//
	// Listitem detail = new Listitem();
	// Listhead columnas = new Listhead();
	// Groupbox gbox = new Groupbox();
	// Caption caption = new Caption(lblcaption);
	// gbox.appendChild(caption);
	//
	// Listcell cell1 = new Listcell();
	// Listcell cell2 = new Listcell();
	// cell2.setSpan(span);
	// if (aplicaDetail) {
	// Listheader deta = new Listheader();
	// deta.setWidth("20px");
	// deta.setAlign("center");
	// columnas.appendChild(deta);
	// }
	//
	// for (String nombreColumna : nobreColumnas) {
	// columnas.appendChild(new Listheader(nombreColumna));
	// }
	// if (aplicaEstado) {
	// if (widthEstado != "" && widthEstado != null) {
	// Listheader estado = new Listheader("Estado");
	// estado.setAlign(CENTER);
	// estado.setWidth(widthEstado);
	// columnas.appendChild(estado);
	// } else {
	// Listheader estado = new Listheader("Estado");
	// estado.setAlign(CENTER);
	// estado.setWidth("70px");
	// columnas.appendChild(estado);
	// }
	//
	// }
	// lista.appendChild(columnas);
	// gbox.appendChild(lista);
	// gbox.setStyle("overflow:auto");
	// cell2.appendChild(gbox);
	// detail.appendChild(cell1);
	// detail.appendChild(cell2);
	// detail.setVisible(false);
	//
	// return detail;
	// }
	//
	@SuppressWarnings("deprecation")
	public Listitem agregarDetail(IBeanAbstracto dto, Listbox lista, int span,
			String widthEstado, String lblCaption, boolean aplicaEstado,
			boolean aplicaDetail, String... nobreColumnas) {

		Listitem detail = new Listitem();
		Listhead columnas = new Listhead();
		Groupbox gbox = new Groupbox();
		Caption caption = new Caption();
		if (lblCaption != null)
			caption.setLabel(lblCaption);

		Listcell cell1 = new Listcell();
		Listcell cell2 = new Listcell();
		cell2.setSpan(span);
		if (aplicaDetail) {
			Listheader deta = new Listheader();
			deta.setWidth("30px");
			deta.setAlign("center");
			columnas.appendChild(deta);
		}

		for (String nombreColumna : nobreColumnas) {
			columnas.appendChild(new Listheader(nombreColumna));
		}
		if (aplicaEstado) {
			if (widthEstado != "" && widthEstado != null) {
				Listheader estado = new Listheader("Estado");
				estado.setAlign(CENTER);
				estado.setWidth(widthEstado);
				columnas.appendChild(estado);
			} else {
				Listheader estado = new Listheader("Estado");
				estado.setAlign(CENTER);
				estado.setWidth("70px");
				columnas.appendChild(estado);
			}

		}
		lista.setFixedLayout(true);
		lista.appendChild(columnas);
		gbox.appendChild(caption);
		gbox.appendChild(lista);
		gbox.setHflex("true");
		gbox.setContentStyle("overflow-x:auto");
		cell2.appendChild(gbox);
		detail.appendChild(cell1);
		detail.appendChild(cell2);
		detail.setVisible(false);

		return detail;
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

	public void setMultiple(Listgroup group, boolean value) {
		for (Iterator<Listitem> it = group.getItems().iterator(); it.hasNext();) {
			Listitem item = it.next();
			if (((Listcell) item.getFirstChild()).getFirstChild() != null) {
				item.setSelected(value);
				((Checkbox) ((Listcell) item.getFirstChild()).getFirstChild())
						.setChecked(value);
				habilitarButtons(!value);
			}
		}

		if (idZBtnDuplicar != null) {
			if (((Checkbox) group.getFirstChild().getFirstChild()).isChecked())
				idZBtnDuplicar.setDisabled(true);
		}
	}

	public void setSelected(Listgroup group, Checkbox check) {
		int i = 0;
		for (Iterator<Listitem> it = group.getItems().iterator(); it.hasNext();) {
			Checkbox c = ((Checkbox) ((Listcell) (it.next()).getFirstChild())
					.getFirstChild());
			if (c.isChecked())
				i++;
		}
		if (i == group.getItems().size()) {
			check.setChecked(true);
			habilitarButtons(false);
		} else if (i < group.getItems().size() && i != 0) {
			check.setChecked(false);
			habilitarButtons(false);
		} else if (i == 0) {
			check.setChecked(false);
			habilitarButtons(true);
		}

		if (idZBtnDuplicar != null) {
			if (i == 1)
				idZBtnDuplicar.setDisabled(false);
			else
				idZBtnDuplicar.setDisabled(true);
		}
	}

	public Component getEmergente(String nombre, Map<String, Object> param)
			throws Exception {
		log.info("Ejecutando el metodo [getEmergente]");

		AATEjecutable detalle = Utilidades.obtenerDetalle(nombre);
		Component componente = Executions.createComponentsDirectly(
				detalle.getFuente(), null, null, param);
		Sessions.getCurrent().setAttribute("EMERGENTE", nombre);
		return componente;

	}

	public Listbox getListaDetalle() {
		return listaDetalle;
	}
}