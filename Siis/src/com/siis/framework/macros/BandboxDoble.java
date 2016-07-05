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
import org.zkoss.zk.ui.ext.AfterCompose;
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
 * @name BandboxDoble.java
 * @date 7/03/2011
 * @desc Controlador del macro BandboxDoble
 */
public class BandboxDoble extends Bandbox implements IInicializarComponentes,
		IBandboxAditionalEventBanboxFind, AfterCompose {

	private static final long serialVersionUID = 637998297163427511L;
	protected static Logger log = Logger.getLogger(BandboxDoble.class);

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
	private boolean usaNombre = true;
	private Listheader idMCRZLhdCodigo;
	private Listheader idMCRZLhdNombre;
	private Component action;
	private IBandboxAditionalEventBanboxFind interfaz;
	private boolean isAsignado = false;
	private boolean usaCodigo = true;
	private boolean usaColumnaCodigo = true;
	private boolean usaColumnaNombre = true;
	private boolean toLowerCase = false;
	private boolean isDinamic = false;
	private BandboxFind idMCRZBbxCriterio;
	private Textbox idMCRZTbxSecCriterio;
	private Button idMCRZBtnLimpiarCriterio;
	// protected boolean isOnOK = false;
	private boolean isCreate = false;

	/**
	 * Metodo que se ejecuta cuando se presiona la tecla Enter sobre el bandbox.
	 * Se pone el foco en el textbox de criterio de busqueda y tambien se le
	 * pasa el valor que se ingres� al bandbox y por ultimo se llama al metodo
	 * {@link #onConsultar(String, boolean)}
	 */
	public void onOkBandboxEvent() {
		log.info("Ejecutando mEtodo [onOkBandboxEvent()]...");
		
		try {

			if (tSecuencia.getRawValue() == null) {
				idMCRZRgrCriterio.setSelectedIndex(0);
				idMCRZTbxCriterio.setFocus(true);
				idMCRZTbxCriterio.setValue(this.getValue());
				idMCRZLbxlista.getItems().clear();
				onConsultar(this.getValue(), true);
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
				visualizarColumnas();
				idMCRZTbxCriterio.setFocus(true);
				idMCRZTbxCriterio.setValue(this.getValue());
				idMCRZLbxlista.getItems().clear();
				onMostrarListboxConsulta();
				onConsultar(this.getValue(), true);
			} else {
				log.trace("Caso 2");
				if (!this.getRawText().isEmpty()) {
					visualizarColumnas();
					idMCRZTbxCriterio.setFocus(true);
					idMCRZTbxCriterio.setValue(this.getValue());
					idMCRZLbxlista.getItems().clear();
					onMostrarListboxConsulta();
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
	 * Visualizar las columnas de codigo y nombre en el listbox de resultados
	 */
	public void visualizarColumnas() throws Exception {
		if (!usaColumnaCodigo) {
			idMCRZLhdCodigo.setVisible(false);
		}
		if (!usaColumnaNombre) {
			idMCRZLhdNombre.setVisible(false);
		}
	}

	public void visualizarRadios() {
		try {
			idMCRZRgrCriterio.getItemAtIndex(1).setVisible(usaNombre);
			idMCRZRgrCriterio.getItemAtIndex(0).setVisible(usaCodigo);

			if (!usaCodigo) {
				Utilidades.seleccionarRadio(idMCRZRgrCriterio, "nombre");
			} else {
				Utilidades.seleccionarRadio(idMCRZRgrCriterio, "codigo");
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
			idMCRZRgrCriterio.setSelectedIndex(0);

			idMCRZTbxCriterio.setFocus(true);
			idMCRZTbxCriterio.setValue(null);
			idMCRZLbxlista.getItems().clear();
			onMostrarListboxConsulta();
		} catch (Exception e) {
			Utilidades.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

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
	 * @type Método de la clase BandboxDoble.java
	 * @name isUsaNombre
	 * @return usaNombre
	 * @descp obtiene el valor de usaNombre
	 */
	public boolean isUsaNombre() {
		return usaNombre;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name setUsaNombre
	 * @return void
	 * @param recibe
	 *            el parametro usaNombre
	 * @descp modifica el atributo usaNombre
	 */
	public void setUsaNombre(boolean usaNombre) {
		this.usaNombre = usaNombre;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name isUsaColumnaCodigo
	 * @return usaColumnaCodigo
	 * @descp obtiene el valor de usaColumnaCodigo
	 */
	public boolean isUsaColumnaCodigo() {
		return usaColumnaCodigo;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name setUsaColumnaCodigo
	 * @return void
	 * @param recibe
	 *            el parametro usaColumnaCodigo
	 * @descp modifica el atributo usaColumnaCodigo
	 */
	public void setUsaColumnaCodigo(boolean usaColumnaCodigo) {
		this.usaColumnaCodigo = usaColumnaCodigo;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name isUsaColumnaNombre
	 * @return usaColumnaNombre
	 * @descp obtiene el valor de usaColumnaNombre
	 */
	public boolean isUsaColumnaNombre() {
		return usaColumnaNombre;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name setUsaColumnaNombre
	 * @return void
	 * @param recibe
	 *            el parametro usaColumnaNombre
	 * @descp modifica el atributo usaColumnaNombre
	 */
	public void setUsaColumnaNombre(boolean usaColumnaNombre) {
		this.usaColumnaNombre = usaColumnaNombre;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
	 * @name gettSecuencia
	 * @return tSecuencia
	 * @descp obtiene el valor de tSecuencia
	 */
	public Textbox getTextboxSecuencia() {
		return tSecuencia;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
	 * @name isUsaCodigo
	 * @return usaCodigo
	 * @descp obtiene el valor de usaCodigo
	 */
	public boolean isUsaCodigo() {
		return usaCodigo;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name setUsaCodigo
	 * @return void
	 * @param recibe
	 *            el parametro usaCodigo
	 * @descp modifica el atributo usaCodigo
	 */
	public void setUsaCodigo(boolean usaCodigo) {
		this.usaCodigo = usaCodigo;
	}

	/**
	 * @type Metodo de la clase BandboxDoble.java
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
	 * @type Metodo de la clase BandboxDoble.java
	 * @name getMsgNoHayRegistros
	 * @return void
	 * @param recibe
	 *            por parametro el mensaje que se muestra cuando la consulta de
	 *            buscar no trae registros.
	 * */
	public String getMsgNoHayRegistros() {
		return msgNoHayRegistros;
	}

	public BandboxFind getBandboxCriterio() {
		return idMCRZBbxCriterio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.casewaresa.framework.action.IInicializarComponentes#
	 * cargarComponentesVista()
	 */
	public void cargarComponentesVista() {
		log.info("Ejecutando mEtodo [cargarComponentesVista()...]");
		
		if (!isCreate) {
			idMCRZBbxCriterio = (BandboxFind) this
					.getFellow("idMCRZBbxCriterio").getChildren().get(0);
			idMCRZTbxSecCriterio = (Textbox) this
					.getFellow("idMCRZTbxSecCriterio");
			idMCRZBtnLimpiarCriterio = (Button) this
					.getFellow("idMCRZBtnLimpiarCriterio");

			idMCRZBbxCriterio.setTextboxSecuencia(idMCRZTbxSecCriterio);
			idMCRZBbxCriterio.esNoObligatorio(idMCRZBtnLimpiarCriterio);
			idMCRZBbxCriterio.setActionObject(this);

			idMCRZRgrCriterio = (Radiogroup) this
					.getFellow("idMCRZRgrCriterio");
			idMCRZLbxlista = (Listbox) this.getFellow("idMCRZLbxlista");
			idMCRZTbxCriterio = (Textbox) this.getFellow("idMCRZTbxCriterio");
			idMCRZLhdCodigo = (Listheader) this.getFellow("idMCRZLhdCodigo");
			idMCRZLhdNombre = (Listheader) this.getFellow("idMCRZLhdNombre");

			this.addEventListener(Events.ON_OK, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {

					// if (isOnOK) {
					visualizarRadios();
					onOkBandboxEvent();
					// }

				}
			});

			this.addEventListener(Events.ON_OPEN,
					new EventListener<OpenEvent>() {

						@Override
						public void onEvent(OpenEvent arg0) throws Exception {

							if ((arg0.isOpen())) {
								onOpenBandboxEvent();
								visualizarRadios();
							}

						}
					});

			this.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {

					visualizarRadios();
					onValueChangeEvent();
					// isOnOK = false;
				}
			});
			this.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {

					// isOnOK = false;

				}

			});
			isCreate = true;
		}

	}

	public void onMostrarListboxConsulta() {
		getIdMCRZLbxlista().setVisible(true);
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name getConsultaObtener
	 * @return consultaObtener
	 * @descp obtiene el valor de consultaObtener
	 */
	public String getConsultaObtener() {
		return consultaObtener;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
	 * @name getIdMCRZRgrCriterio
	 * @return idMCRZRgrCriterio
	 * @descp obtiene el valor de idMCRZRgrCriterio
	 */
	public Radiogroup getIdMCRZRgrCriterio() {
		return idMCRZRgrCriterio;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name getIdMCRZLbxlista
	 * @return idMCRZLbxlista
	 * @descp obtiene el valor de idMCRZLbxlista
	 */
	public Listbox getIdMCRZLbxlista() {
		return idMCRZLbxlista;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name getIdMCRZTbxCriterio
	 * @return idMCRZTbxCriterio
	 * @descp obtiene el valor de idMCRZTbxCriterio
	 */
	public Textbox getIdMCRZTbxCriterio() {
		return idMCRZTbxCriterio;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name getConsulta
	 * @return consulta
	 * @descp obtiene el valor de consulta
	 */
	public String getConsulta() {
		return consulta;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
	 * @name getObjetoClase
	 * @return objetoClase
	 * @descp obtiene el valor de objetoClase
	 */
	public IBeanAbstracto getObjetoClase() {
		return objetoClase;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
	 * @name getParametros
	 * @return parametros
	 * @descp obtiene el valor de parametros
	 */
	public Map<String, Object> getParametros() {
		return parametros;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name setParametros
	 * @return void
	 * @param recibe
	 *            el parametro parametros
	 * @descp modifica el atributo parametros
	 */
	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public IBeanAbstracto setObjeto(String campo, String criterio,
			IBeanAbstracto obj) {
		if (criterio != null && !criterio.isEmpty()) {
			criterio = "%" + criterio.trim().toUpperCase() + "%";
		}
		if (campo.equals("codigo")) {

			obj.setCodigo(criterio);
		} else {
			if (campo.equals("nombre")) {

				obj.setNombre(criterio);
			}
		}

		return obj;
	}

	public IBeanAbstracto setObjeto(IBeanAbstracto obj, String campo,
			String criterio) {
		if (criterio != null && !criterio.isEmpty()) {
			criterio = "%" + criterio.trim() + "%";
		}
		if (campo.equals("codigo")) {

			obj.setCodigo(criterio);
		} else {
			if (campo.equals("nombre")) {

				obj.setNombre(criterio);
			}
		}

		return obj;
	}

	private AssemblerStandard getAssemblerStandard() {
		if (assemblerStandard == null) {
			assemblerStandard = new AssemblerStandard();
		}
		return assemblerStandard;
	}

	public List<IBeanAbstracto> getListadoDinamic() {
		log.info("Ejecutando el mEtodo [getListadoDinamic()]...");
		
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
				parametros = new HashMap<String, Object>();
				parametros.put("CRITERIO", idMCRZTbxSecCriterio.getValue());
				if (!isToLowerCase()) {
					parametros.put("OBJETO",
							setObjeto(campo, criterio, getObjetoClase()));
					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									getParametros());

				} else {
					parametros.put("OBJETO",
							setObjeto(getObjetoClase(), campo, criterio));
					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									getParametros());
				}
			} else {
				parametros.put("CRITERIO", idMCRZTbxSecCriterio.getValue());
				if (!isToLowerCase()) {

					parametros.put("OBJETO",
							setObjeto(campo, criterio, getObjetoClase()));
					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									getParametros());

				} else {

					parametros.put("OBJETO",
							setObjeto(getObjetoClase(), campo, criterio));
					listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
							.getFacade().obtenerListado(getConsulta(),
									getParametros());

				}
			}

			getObjetoClase().setCodigo(null);
			getObjetoClase().setNombre(null);

			getIdMCRZLbxlista().getItems().clear();

			if (listaDatos != null && !listaDatos.isEmpty()) {
				for (final IBeanAbstracto object : listaDatos) {
					final Listitem fila = getAssemblerStandard()
							.crearListitemDesdeDto(object);
					final BandboxDoble padre = this;
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
			log.error(ex.getMessage(), ex);
		}
	}

	/**
	 * @type Método de la clase BandboxDoble.java
	 * @name esNoObligatorio
	 * @param botonBorrar
	 * @desc Recibe el boton que borra la informacion del Bnadbox cuando este no
	 *       es obligatorio
	 */
	public void esNoObligatorio(Button botonBorrar) {
		this.botonBorrar = botonBorrar;
	}

	public void usaElNombre(boolean usaNombr) {
		usaNombre = usaNombr;
	}

	/**
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
	 * @name onSeleccionarMaestro
	 * @param object
	 * @param padre
	 * @throws Exception
	 * @desc
	 */
	private void onSeleccionarMaestro(final IBeanAbstracto object,
			final BandboxDoble padre) throws Exception {
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
	 * @type Método de la clase BandboxDoble.java
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
	 * @type Método de la clase BandboxDoble.java
	 * @name isToLowerCase
	 * @return toLowerCase
	 * @descp obtiene el valor de toLowerCase
	 */
	public boolean isToLowerCase() {
		return toLowerCase;
	}

	public void limpiarBandbox(Textbox secB, String ban, Button boton) {
		log.info("Ejecutando mEtodo [limpiarBandbox()]...");

		secB.setValue("");
		((Bandbox) this.getFellow(ban).getChildren().get(0)).setValue("");
		((Bandbox) this.getFellow(ban).getChildren().get(0)).setTooltiptext(""); 

		if (boton != null)
			boton.setVisible(false);
		if (parametros != null)
			parametros = null;
	}

	@Override
	public boolean onValidateSeleccion(Listitem item, IBeanAbstracto objeto) {
		idMCRZLbxlista.getItems().clear();
		return false;
	}

	@Override
	public void afterCompose() {
		log.info("Ejecutando AfterComposer");

		this.cargarComponentesVista();
	}
}
