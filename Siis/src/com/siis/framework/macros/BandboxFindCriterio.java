package com.casewaresa.framework.macros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.assembler.AssemblerStandard;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.macros.contract.IBandboxAditionalEventBanboxFind;
import com.casewaresa.framework.util.Utilidades;

/**
 * @author Futco
 * @name BandboxFindCriterio.java
 * @date 7/03/2011
 * @desc Controlador del macro BandboxFindCriterio
 */
public class BandboxFindCriterio extends Bandbox implements
		IInicializarComponentes {

	private static final long serialVersionUID = 637998297163427511L;
	protected static Logger log = Logger.getLogger(BandboxFind.class);

	private Listbox idMCRZLbxCriterio;
	private Radiogroup idMCRZRgrCriterio;
	private Listbox idMCRZLbxlista;
	private Textbox idMCRZTbxCriterio;
	private String consulta;
	private String consultaObtener;
	private String msgNoHayRegistros;
	private AssemblerStandard assemblerStandard;
	private IBeanAbstracto objetoClase;
	private Textbox tSecuencia;
	private Button botonBorrar;
	private Map<String, Object> parametros = null;
	private Listheader idMCRZLhdCodigo;
	private Listheader idMCRZLhdNombre;
	private Component action;
	private IBandboxAditionalEventBanboxFind interfaz;
	private boolean isAsignado = false;
	private boolean usaColumnaCodigo = true;
	private boolean usaColumnaNombre = true;
	private boolean toLowerCase = false;
	private boolean isDinamic = false;
	private List<String[]> listaCriterios;
	private String ids = "";

	// protected boolean isOnOK = false;

	/**
	 * Metodo que se ejecuta cuando se presiona la tecla Enter sobre el bandbox
	 * o cuando se da click sobre el boton de busqueda
	 */
	public void onConsultarEvent() {
		try {
			onMostrarListboxConsulta();
			onConsultar(idMCRZTbxCriterio.getValue(), false);
		} catch (Exception e) {
			Utilidades.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	/**
	 * Metodo que se ejecuta cuando se presiona la tecla Enter sobre el bandbox.
	 * Se pone el foco en el textbox de criterio de busqueda y tambien se le
	 * pasa el valor que se ingres� al bandbox y por ultimo se llama al metodo
	 * {@link #onConsultar(String, boolean)}
	 */
	public void onOkBandboxEvent() {
		try {
			if (tSecuencia.getRawValue() == null) {
				cargarCriterios();
				onConfigurarCriterios();
				// cargarComponentesVista();
				idMCRZRgrCriterio.setSelectedIndex(0);
				seleccionarRadio();
				idMCRZTbxCriterio.setFocus(true);
				idMCRZTbxCriterio.setValue(this.getValue());
				idMCRZLbxlista.getItems().clear();
				tSecuencia.setRawValue(null);
				onConsultar(idMCRZTbxCriterio.getValue(), true);
			}
		} catch (Exception e) {
			Utilidades.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void onValueChangeEvent() {
		log.info("Ejecutando mEtodo [onValueChangeEvent()]...");
		
		try {
			if (!this.getRawText().isEmpty()
					&& tSecuencia.getRawValue() == null) {
				log.trace("Caso 1");
				cargarCriterios();
				onConfigurarCriterios();
				idMCRZTbxCriterio.setFocus(true);
				idMCRZTbxCriterio.setValue(this.getValue());
				idMCRZLbxlista.getItems().clear();
				onMostrarListboxConsulta();
				tSecuencia.setRawValue(null);
				onConsultar(this.getValue(), true);
			} else {
				log.trace("Caso 2");
				if (!this.getRawText().isEmpty()) {
					cargarCriterios();
					onConfigurarCriterios();
					idMCRZTbxCriterio.setFocus(true);
					idMCRZTbxCriterio.setValue(this.getValue());
					idMCRZLbxlista.getItems().clear();
					onMostrarListboxConsulta();

					tSecuencia.setRawValue(null);
					onConsultar(this.getValue(), true);
				} else {
					tSecuencia.setRawValue(null);
				}
			}
		} catch (Exception e) {
			Utilidades.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	/**
	 * Metodo que se ejecuta cuando se abre el bandbox. Se selecciona el radio
	 * visible que es el que indica el parametro o criterio de busqueda. Se
	 * limpian los demas componentes
	 */
	public void onOpenBandboxEvent() {
		try {
			// cargarComponentesVista();
			cargarCriterios();
			onConfigurarCriterios();
			idMCRZRgrCriterio.setSelectedIndex(0);
			seleccionarRadio();
			idMCRZTbxCriterio.setFocus(true);
			idMCRZTbxCriterio.setValue(null);
			idMCRZLbxlista.getItems().clear();
			onMostrarListboxConsulta();
		} catch (Exception e) {
			Utilidades.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	private void seleccionarRadio() {
		// seleccionar primer radio visible cuando se este usando un solo
		// parametro de busqueda
		int totalRadios = 0;
		Radio radioVisible = null;
		if (idMCRZRgrCriterio != null) {
			for (Radio componentRadio : idMCRZRgrCriterio.getItems()) {
				if (componentRadio.isVisible()) {
					radioVisible = componentRadio;
					totalRadios++;
				}
			}
		}
		if (totalRadios == 1) {
			radioVisible.setSelected(true);
		}
		// --------------------------------------------------------
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name isUsaColumnaCodigo
	 * @return usaColumnaCodigo
	 * @descp obtiene el valor de usaColumnaCodigo
	 */
	public boolean isUsaColumnaCodigo() {
		return usaColumnaCodigo;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name isUsaColumnaNombre
	 * @return usaColumnaNombre
	 * @descp obtiene el valor de usaColumnaNombre
	 */
	public boolean isUsaColumnaNombre() {
		return usaColumnaNombre;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
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
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name gettSecuencia
	 * @return tSecuencia
	 * @descp obtiene el valor de tSecuencia
	 */
	public Textbox getTextboxSecuencia() {
		return tSecuencia;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getListaCriterio
	 * @return listaCriterios
	 * @descp obtiene el valor de listaCriterios
	 */
	public List<String[]> getListaCriterios() {
		return listaCriterios;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name settSecuencia
	 * @return void
	 * @param recibe
	 *            el parametro tSecuencia
	 * @descp modifica el atributo tSecuencia
	 */
	public void setTextboxSecuencia(Textbox tSecuencia) {
		this.tSecuencia = tSecuencia;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name settSecuencia
	 * @return void
	 * @param recibe
	 *            el parametro tSecuencia
	 * @descp modifica el atributo tSecuencia
	 */
	public void setListaCriterios(List<String[]> criterios) {
		this.listaCriterios = criterios;
	}

	/**
	 * @type Metodo de la clase BandboxFindCriterio.java
	 * @name setMsgNoHayRegistros
	 * @return void
	 * @param recibe
	 *            por parametro el mensaje que se muestra cuando la consulta de
	 *            buscar no trae registros.
	 * */
	public void setMsgNoHayRegistros(String msgNoHayRegistros) {
		this.msgNoHayRegistros = msgNoHayRegistros;
	}

	/**
	 * @type Metodo de la clase BandboxFindCriterio.java
	 * @name getMsgNoHayRegistros
	 * @return void
	 * @param recibe
	 *            por parametro el mensaje que se muestra cuando la consulta de
	 *            buscar no trae registros.
	 * */
	public String getMsgNoHayRegistros() {
		return msgNoHayRegistros;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.casewaresa.framework.action.IInicializarComponentes#
	 * cargarComponentesVista()
	 */
	public void cargarComponentesVista() {
		idMCRZLbxCriterio = (Listbox) this.getFellow("idMCRZLbxCriterio");
		idMCRZRgrCriterio = (Radiogroup) this.getFellow("idMCRZRgrCriterio");
		idMCRZLbxlista = (Listbox) this.getFellow("idMCRZLbxlista");
		idMCRZTbxCriterio = (Textbox) this.getFellow("idMCRZTbxCriterio");
		idMCRZLhdCodigo = (Listheader) this.getFellow("idMCRZLhdCodigo");
		idMCRZLhdNombre = (Listheader) this.getFellow("idMCRZLhdNombre");

		this.addEventListener(Events.ON_OK, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {

				onOkBandboxEvent();

			}
		});

		this.addEventListener(Events.ON_OPEN, new EventListener<OpenEvent>() {

			@Override
			public void onEvent(OpenEvent arg0) throws Exception {

				if ((arg0.isOpen())) {
					onOpenBandboxEvent();

				}

			}
		});

		this.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {

				onValueChangeEvent();

			}
		});
		this.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				log.info("ON_FOCUS");

			}

		});

	}

	public void onMostrarListboxConsulta() {
		getIdMCRZLbxlista().setVisible(true);
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getConsultaObtener
	 * @return consultaObtener
	 * @descp obtiene el valor de consultaObtener
	 */
	public String getConsultaObtener() {
		return consultaObtener;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
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
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getIdMCRZRgrCriterio
	 * @return idMCRZRgrCriterio
	 * @descp obtiene el valor de idMCRZRgrCriterio
	 */
	public Radiogroup getIdMCRZRgrCriterio() {
		return idMCRZRgrCriterio;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getIdMCRZLbxlista
	 * @return idMCRZLbxlista
	 * @descp obtiene el valor de idMCRZLbxlista
	 */
	public Listbox getIdMCRZLbxlista() {
		return idMCRZLbxlista;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getIdMCRZTbxCriterio
	 * @return idMCRZTbxCriterio
	 * @descp obtiene el valor de idMCRZTbxCriterio
	 */
	public Textbox getIdMCRZTbxCriterio() {
		return idMCRZTbxCriterio;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getConsulta
	 * @return consulta
	 * @descp obtiene el valor de consulta
	 */
	public String getConsulta() {
		return consulta;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name setConsulta
	 * @return void
	 * @param recibe
	 *            el parametro consulta
	 * @descp modifica el atributo consulta
	 */
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getObjetoClase
	 * @return objetoClase
	 * @descp obtiene el valor de objetoClase
	 */
	public IBeanAbstracto getObjetoClase() {
		return objetoClase;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
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
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name getParametros
	 * @return parametros
	 * @descp obtiene el valor de parametros
	 */
	public Map<String, Object> getParametros() {
		return parametros;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name setParametros
	 * @return void
	 * @param recibe
	 *            el parametro parametros
	 * @descp modifica el atributo parametros
	 */
	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public Map<String, Object> setObjeto(String campo, String criterio,
			Map<String, Object> param) {
		if (criterio != null && !criterio.isEmpty()) {
			criterio = "%" + criterio.trim().toUpperCase() + "%";
		}
		if (campo.equals("codigo")) {
			param.put("CriterioCodigo", criterio);
			param.put("CriterioNombre", null);
		} else {
			if (campo.equals("nombre")) {
				param.put("CriterioNombre", criterio);
				param.put("CriterioCodigo", null);
			}
		}

		return param;
	}

	public Map<String, Object> setObjeto(Map<String, Object> param,
			String campo, String criterio) {
		if (criterio != null && !criterio.isEmpty()) {
			criterio = "%" + criterio.trim() + "%";
		}
		if (campo.equals("codigo")) {

			param.put("CriterioCodigo", criterio);
			param.put("CriterioNombre", null);
		} else {
			if (campo.equals("nombre")) {

				param.put("CriterioNombre", criterio);
				param.put("CriterioCodigo", null);
			}
		}

		return param;
	}

	private AssemblerStandard getAssemblerStandard() {
		if (assemblerStandard == null) {
			assemblerStandard = new AssemblerStandard();
		}
		return assemblerStandard;
	}

	public List<IBeanAbstracto> getListadoDinamic() {
		log.info("Ejecutando el mEtodo[getListadoDinamic()]...");
		
		List<IBeanAbstracto> listaDatos = null;
		try {

			listaDatos = ParametrizacionFac.getFacade().listadoDinamico(
					this.objetoClase, this.consulta, parametros);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return listaDatos;
	}

	@SuppressWarnings("unchecked")
	public void onConsultar(String criterio, boolean enterBandbox) {
		log.info("Ejecutando el mEtodo [onConsultar()]...");
		
		Radio element = null;

		if (action instanceof IBandboxAditionalEventBanboxFind)
			interfaz = (IBandboxAditionalEventBanboxFind) action;

		try {
			element = getIdMCRZRgrCriterio().getSelectedItem();
			String campo = (element.getValue() != null) ? element.getValue()
					.toString() : null;

			List<IBeanAbstracto> listaDatos = null;

			if (parametros == null) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("CRITERIO", idMCRZLbxCriterio.getSelectedItem()
						.getLabel());
				if (!isToLowerCase()) {

					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									setObjeto(campo, criterio, param));

				} else {

					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									setObjeto(param, campo, criterio));
				}

			} else {
				parametros.put("CRITERIO", idMCRZLbxCriterio.getSelectedItem()
						.getLabel());

				if (!isToLowerCase()) {
					log.info("PARAMETROS1--->" + parametros);
					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									setObjeto(campo, criterio, parametros));

				} else {
					log.info("PARAMETROS2--->" + parametros);
					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									setObjeto(parametros, campo, criterio));

				}

			}

			getObjetoClase().setCodigo(null);
			getObjetoClase().setNombre(null);

			getIdMCRZLbxlista().getItems().clear();

			if (listaDatos != null && !listaDatos.isEmpty()) {
				for (final IBeanAbstracto object : listaDatos) {
					final Listitem fila = getAssemblerStandard()
							.crearListitemDesdeDto(object);
					final BandboxFindCriterio padre = this;
					fila.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {

								public void onEvent(Event arg0)
										throws Exception {
									onSeleccionarMaestro(object, padre);
								}
							});
					if (interfaz != null) {
						fila.addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {
									public void onEvent(Event arg0)
											throws Exception {
										fila.setAttribute("ID", ids);
										interfaz.onValidateSeleccion(fila,
												object);

									}
								});
					}

					getIdMCRZLbxlista().appendChild(fila);

					if (enterBandbox) {
						if (listaDatos.size() == 1) {
							onSeleccionarMaestro(listaDatos.get(0), this);
							if (interfaz != null) {
								fila.setAttribute("ID", ids);
								interfaz.onValidateSeleccion(fila, object);
							}
						} else {
							this.setOpen(true);
						}
					}
				}
			} else {
				getIdMCRZLbxlista().getItems().clear();
				tSecuencia.setRawValue(null);
				this.setRawValue(null);
				this.setOpen(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name esNoObligatorio
	 * @param botonBorrar
	 * @desc Recibe el boton que borra la informacion del Bnadbox cuando este no
	 *       es obligatorio
	 */
	public void esNoObligatorio(Button botonBorrar) {
		this.botonBorrar = botonBorrar;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name setAsignado
	 * @return void
	 * @param recibe
	 *            el parametro isAsignado
	 * @descp modifica el atributo isAsignado
	 */
	public void setAsignado(boolean isAsignado) {
		this.isAsignado = isAsignado;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name isAsignado
	 * @return isAsignado
	 * @descp obtiene el valor de isAsignado
	 */
	public boolean getIsAsignado() {
		return isAsignado;
	}

	public boolean isDinamic() {
		return isDinamic;
	}

	public void setDinamic(boolean isDinamic) {
		this.isDinamic = isDinamic;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name onSeleccionarMaestro
	 * @param object
	 * @param padre
	 * @throws Exception
	 * @desc
	 */
	private void onSeleccionarMaestro(final IBeanAbstracto object,
			final BandboxFindCriterio padre) throws Exception {
		log.info("Ejecutando mEtodo [onSeleccionarMaestro()]...");
		
		if (!isDinamic) {
			if (isUsaColumnaCodigo() && isUsaColumnaNombre()) {
				Utilidades.onSeleccionar(object, getConsultaObtener(), padre,
						getTextboxSecuencia(), getIsAsignado());
			} else {
				Utilidades.onSeleccionar(object, getConsultaObtener(), padre,
						getTextboxSecuencia(), getIsAsignado(),
						isUsaColumnaCodigo(), isUsaColumnaNombre());
			}

		} else {

			String consultaObtener = getConsultaObtener()
					+ object.getPrimaryKey();

			Utilidades.onSeleccionarDinamic(object, consultaObtener, padre,
					getTextboxSecuencia(), isUsaColumnaCodigo(),
					isUsaColumnaNombre());

		}

		getIdMCRZLbxlista().getItems().clear();
		padre.setOpen(false);

		if (botonBorrar != null) {
			botonBorrar.setVisible(true);
			botonBorrar.setDisabled(false);
		}
	}

	/**
	 * @type Metodo de la clase BandboxFindCriterio.java
	 * @name onConfigurarCriterios
	 * @return void
	 * @param recibe
	 * @descp
	 */

	public void cargarCriterios() {
		idMCRZLbxCriterio.getChildren().clear();
		for (final String[] criterio : listaCriterios) {
			Listitem opcion = new Listitem(criterio[0]);
			opcion.setValue(criterio);
			idMCRZLbxCriterio.appendChild(opcion);
		}
		idMCRZLbxCriterio.setSelectedIndex(0);
	}

	public void onConfigurarCriterios() {
		String[] criterio = ((Listitem) idMCRZLbxCriterio.getSelectedItem())
				.getValue();
		idMCRZRgrCriterio.getItemAtIndex(0).setVisible(
				Boolean.parseBoolean(criterio[1]));
		idMCRZRgrCriterio.getItemAtIndex(0).setLabel(criterio[2]);
		idMCRZRgrCriterio.getItemAtIndex(1).setVisible(
				Boolean.parseBoolean(criterio[3]));
		idMCRZRgrCriterio.getItemAtIndex(1).setLabel(criterio[4]);

		if (!Boolean.parseBoolean(criterio[1]))
			idMCRZRgrCriterio.setSelectedIndex(1);
		else
			idMCRZRgrCriterio.setSelectedIndex(0);

		if (!Boolean.parseBoolean(criterio[5])) {
			idMCRZLhdCodigo.setVisible(false);
			usaColumnaCodigo = false;
		} else {
			usaColumnaCodigo = true;
			idMCRZLhdCodigo.setVisible(true);
		}
		if (!Boolean.parseBoolean(criterio[6])) {
			idMCRZLhdNombre.setVisible(false);
			usaColumnaNombre = false;
		} else {
			usaColumnaNombre = true;
			idMCRZLhdNombre.setVisible(true);
		}
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name setToLowerCase
	 * @return void
	 * @param recibe
	 *            el parametro toLowerCase
	 * @descp modifica el atributo toLowerCase
	 */
	public void setToLowerCase(boolean toLowerCase) {
		this.toLowerCase = toLowerCase;
	}

	/**
	 * @type Método de la clase BandboxFindCriterio.java
	 * @name isToLowerCase
	 * @return toLowerCase
	 * @descp obtiene el valor de toLowerCase
	 */
	public boolean isToLowerCase() {
		return toLowerCase;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
