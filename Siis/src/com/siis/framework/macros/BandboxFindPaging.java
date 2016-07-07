package com.siis.framework.macros;

import java.util.ArrayList;
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
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.siis.framework.action.impl.IInicializarComponentes;
import com.siis.framework.assembler.AssemblerStandard;
import com.siis.framework.contract.IExcepcion;
import com.siis.framework.dto.IBeanAbstracto;
import com.siis.framework.excepciones.impl.Excepcion;
import com.siis.framework.macros.contract.IBandboxAditionalEventBanboxFind;
import com.siis.framework.renderer.PagingControlFilas;
import com.siis.framework.util.PagingControlColumnas;
import com.siis.framework.util.Utilidades;
import com.siis.framework.zk.notificaciones.Notificaciones;

/**
 * @author Futco
 * @name BandboxFindPaging.java
 * @date 7/03/2011
 * @desc Controlador del macro banboxFindPaging
 */
public class BandboxFindPaging extends Bandbox implements
		IInicializarComponentes {

	private static final long serialVersionUID = 637998297163427511L;
	protected static Logger log = Logger.getLogger(BandboxFindPaging.class);

	private Radiogroup idMCRZRgrCriterio;
	private Listbox idMCRZLbxlista;
	private Textbox idMCRZTbxCriterio;
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
	private Listfoot idMCRZLftPaging;
	private IBandboxAditionalEventBanboxFind interfaz;
	private boolean isAsignado = false;
	private boolean usaCodigo = true;
	private boolean usaColumnaCodigo = true;
	private boolean usaColumnaNombre = true;
	private boolean toLowerCase = false;
	private boolean isDinamic = false;
	private boolean permitirBuscar = true;
	private String mensajeExtra = "";
	private String tituloMensajeExtra = "";

	private String consultaPaginada;
	private String consultaDinamica;

	private PagingControl idMCRZPGCTRLMaestro;
	protected boolean isOnOK = false;
	private String tablaPadreFrom;

	private String ids;

	// metodos del bandboxPaging

	/**
	 * Metodo que se ejecuta cuando se presiona la tecla Enter sobre el bandbox.
	 * Se pone el foco en el textbox de criterio de busqueda y tambien se le
	 * pasa el valor que se ingres� al bandbox. se parametriza el control de
	 * paginacion con los nuevos parametros. y por ultimo se llama al metodo
	 * {@link #onConsultar(String, boolean)}
	 */
	public void onOkBandboxEvent() {
		log.info("Ejecutando el mEtodo [onOkBandboxEvent()]...");

		try {
			if (tSecuencia.getRawValue() == null) {
				visualizarColumnas();
				idMCRZTbxCriterio.setFocus(true);
				idMCRZTbxCriterio.setValue(this.getValue());
				idMCRZLbxlista.getItems().clear();
				tSecuencia.setRawValue(null);
				parametrizarPagingControl();
				onConsultar(idMCRZTbxCriterio.getValue(), true);

			}
		} catch (Exception e) {
			log.info("Eroor......................."+e);
//			Utilidades.lanzarExcepcion(new Excepcion(
//					IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	/**
	 * Metodo que se ejecuta cuando se abre el bandbox. Se selecciona el radio
	 * visible que es el que indica el parametro o criterio de busqueda. Se
	 * limpian los demas componentes y por ultimo se parametriza el control de
	 * paginacion
	 */
	public void onOpenBandboxEvent() {
		log.info("Ejecutando el mEtodo [onOpenBandboxEvent()]...");

		try {
			if (permitirBuscar) {
				visualizarColumnas();

				idMCRZRgrCriterio.setSelectedIndex(0);

				// seleccionar primer radio visible cuando se este usando un
				// solo
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

				idMCRZTbxCriterio.setFocus(true);
				idMCRZTbxCriterio.setValue(null);
				idMCRZLbxlista.getItems().clear();
				onMostrarListboxConsulta();
				parametrizarPagingControl();
			} else {
				Notificaciones.mostrarNotificacionAlerta(tituloMensajeExtra,
						mensajeExtra, 7000);
			}
		} catch (Exception e) {
			log.info("Eroor......................."+e);
		}
	}

	public void onValueChangeEvent() {
		log.info("Ejecutando mEtodo [onValueChangeEvent()]...");

		try {
			if (!this.getRawText().isEmpty()
					&& tSecuencia.getRawValue() == null) {

				visualizarColumnas();
				idMCRZTbxCriterio.setFocus(true);
				idMCRZTbxCriterio.setValue(this.getValue());
				idMCRZLbxlista.getItems().clear();
				parametrizarPagingControl();
				onConsultar(idMCRZTbxCriterio.getValue(), true);
			} else {

				if (!this.getRawText().isEmpty()) {
					visualizarColumnas();
					idMCRZTbxCriterio.setFocus(true);
					idMCRZTbxCriterio.setValue(this.getValue());
					idMCRZLbxlista.getItems().clear();
					parametrizarPagingControl();
					onConsultar(idMCRZTbxCriterio.getValue(), true);
				} else {
					tSecuencia.setRawValue(null);
				}
			}
		} catch (Exception e) {
			log.info("Eroor......................."+e);
		}
	}

	/**
	 * Metodo que se ejecuta cuando se presiona la tecla Enter sobre el bandbox
	 * o cuando se da click sobre el boton de busqueda
	 */
	public void onConsultarEvent() {
		log.info("Ejecutando el mEtodo [onConsultarEvent()]...");

		try {
			if (permitirBuscar) {
				log.info("Ejecutando el mEtodo [onConsultarEvent(1)]...");
				visualizarColumnas();
				log.info("Ejecutando el mEtodo [onConsultarEvent(2)]...");
				onMostrarListboxConsulta();
				log.info("Ejecutando el mEtodo [onConsultarEvent(3)]...");
				onConsultar(idMCRZTbxCriterio.getValue(), false);
				log.info("Ejecutando el mEtodo [onConsultarEvent(4)]...");
			} else {
				Notificaciones.mostrarNotificacionAlerta(tituloMensajeExtra,
						mensajeExtra, 7000);
			}
		} catch (Exception e) {
			log.info("Eroor......................."+e);
		}
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
	 * @name isUsaNombre
	 * @return usaNombre
	 * @descp obtiene el valor de usaNombre
	 */
	public boolean isUsaNombre() {
		return usaNombre;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Metodo de la clase BandboxFindPaging.java
	 * @name setTablaPadreFrom
	 * @return void
	 * @param recibe
	 *            el parametro tablaPadreFrom
	 * @descp modifica el atributo tablaPadreFrom
	 */
	public void setTablaPadreFrom(String tablaPadreFrom) {
		this.tablaPadreFrom = tablaPadreFrom;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
	 * @name isUsaColumnaCodigo
	 * @return usaColumnaCodigo
	 * @descp obtiene el valor de usaColumnaCodigo
	 */
	public boolean isUsaColumnaCodigo() {
		return usaColumnaCodigo;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
	 * @name isUsaColumnaNombre
	 * @return usaColumnaNombre
	 * @descp obtiene el valor de usaColumnaNombre
	 */
	public boolean isUsaColumnaNombre() {
		return usaColumnaNombre;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
	 * @name gettSecuencia
	 * @return tSecuencia
	 * @descp obtiene el valor de tSecuencia
	 */
	public Textbox getTextboxSecuencia() {
		return tSecuencia;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
	 * @name isUsaCodigo
	 * @return usaCodigo
	 * @descp obtiene el valor de usaCodigo
	 */
	public boolean isUsaCodigo() {
		return usaCodigo;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Metodo de la clase BandboxFindPaging.java
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
	 * @type Metodo de la clase BandboxFindPaging.java
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
	public void cargarComponentesVista() throws Exception {
		idMCRZRgrCriterio = (Radiogroup) this.getFellow("idMCRZRgrCriterio");
		idMCRZLbxlista = (Listbox) this.getFellow("idMCRZLbxlista");
		idMCRZTbxCriterio = (Textbox) this.getFellow("idMCRZTbxCriterio");
		idMCRZLhdCodigo = (Listheader) this.getFellow("idMCRZLhdCodigo");
		idMCRZLhdNombre = (Listheader) this.getFellow("idMCRZLhdNombre");

		idMCRZRgrCriterio.getItemAtIndex(1).setVisible(usaNombre);
		idMCRZRgrCriterio.getItemAtIndex(0).setVisible(usaCodigo);

		idMCRZPGCTRLMaestro = (PagingControl) this
				.getFellow("idMCRZPGCTRLMaestro").getChildren().get(0);

		idMCRZLftPaging = (Listfoot) this.getFellow("idMCRZLftPaging");

		this.addEventListener(Events.ON_OK, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// if (isOnOK) {
				visualizarRadios();
				onOkBandboxEvent();
				// }

			}
		});

		this.addEventListener(Events.ON_OPEN, new EventListener<OpenEvent>() {

			@Override
			public void onEvent(OpenEvent arg0) throws Exception {

				if (arg0.isOpen()) {
					onOpenBandboxEvent();
					idMCRZLftPaging.setVisible(false);
					visualizarRadios();
				}

			}
		});

		this.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				visualizarRadios();
				onValueChangeEvent();
				isOnOK = false;
			}
		});
		this.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				isOnOK = false;

			}

		});

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
			if (!usaCodigo) {
				Utilidades.seleccionarRadio(idMCRZRgrCriterio, "nombre");
			} else {
				Utilidades.seleccionarRadio(idMCRZRgrCriterio, "codigo");
			}
		} catch (Exception e) {
			log.info("error::::" + e);
		}
	}

	/**
	 * Hace visible el listbox de resultados y oculta la etiqueta de mensaje
	 * vacio
	 */
	public void onMostrarListboxConsulta() {
		getIdMCRZLbxlista().setVisible(true);
		getIdMCRZLbxlista().applyProperties();
		getIdMCRZLbxlista().invalidate();
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
	 * @name getConsultaObtener
	 * @return consultaObtener
	 * @descp obtiene el valor de consultaObtener
	 */
	public String getConsultaObtener() {
		return consultaObtener;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
	 * @name getIdMCRZRgrCriterio
	 * @return idMCRZRgrCriterio
	 * @descp obtiene el valor de idMCRZRgrCriterio
	 */
	public Radiogroup getIdMCRZRgrCriterio() {
		return idMCRZRgrCriterio;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
	 * @name getIdMCRZLbxlista
	 * @return idMCRZLbxlista
	 * @descp obtiene el valor de idMCRZLbxlista
	 */
	public Listbox getIdMCRZLbxlista() {
		return idMCRZLbxlista;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
	 * @name getIdMCRZTbxCriterio
	 * @return idMCRZTbxCriterio
	 * @descp obtiene el valor de idMCRZTbxCriterio
	 */
	public Textbox getIdMCRZTbxCriterio() {
		return idMCRZTbxCriterio;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
	 * @name getObjetoClase
	 * @return objetoClase
	 * @descp obtiene el valor de objetoClase
	 */
	public IBeanAbstracto getObjetoClase() {
		return objetoClase;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
	 * @name getParametros
	 * @return parametros
	 * @descp obtiene el valor de parametros
	 */
	public Map<String, Object> getParametros() {
		return parametros;
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * Devuelve una instancia de una entidad de negocio con criterios definidos.
	 * El valor del criterio va con UpperCase
	 * 
	 * @name setObjeto
	 * @param campo
	 * @param criterio
	 * @param obj
	 * 
	 */
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

	/**
	 * Devuelve una instancia de una entidad de negocio con criterios definidos
	 * El valor del criterio va normal (como se manda)
	 * 
	 * @name setObjeto
	 * @param obj
	 * @param campo
	 * @param criterio
	 * 
	 */
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

	/**
	 * Singleton de AssemblerStandard
	 * 
	 * @return
	 */
	private AssemblerStandard getAssemblerStandard() {
		if (assemblerStandard == null) {
			assemblerStandard = new AssemblerStandard();
		}
		return assemblerStandard;
	}

	/**
	 * Consulta por un criterio de busqueda. Los resultados los trabaja el
	 * control de paginacion a travez del metodo
	 * {@link PagingControl#listar(IBeanAbstracto)} pasandole como parametros un
	 * IBeanAbstracto que se crea a partir del valor que llega y del radio que
	 * esta seleccionado que contiene el parametro de busqueda Se hace una
	 * validacion si la consulta es hecha directamente del bandbox para que
	 * cuando solo se encuentre un resultado no se habra el popup con los
	 * resultados sino que lo seleccione automaticamente. Este metodo tambien
	 * valida de que tipo es la consulta ya que esta puede ser Dinamica o Normal
	 * (consulta descrita en un XML).
	 * 
	 * @param criterio
	 *            valor del criterio de busqueda
	 * @param enterBandbox
	 *            indica si la busqueda o la consulta se esta haciendo desde el
	 *            bandbox como tal o si se esta haciendo desde otra parte (ya
	 *            sea desde textbox de consulta o dandole click al boton buscar)
	 * 
	 */
	private void onConsultar(String criterio, boolean enterBandbox)
			throws Exception {
		log.info("Ejecutando el mEtodo [onConsultar()]...");

		if (getObjetoClase() != null) {
			getObjetoClase().setCodigo(null);
			getObjetoClase().setNombre(null);
		}

		Radio element = null;
		log.info("Ejecutando el mEtodo [onConsultar()]...");
		if (action instanceof IBandboxAditionalEventBanboxFind)
			interfaz = (IBandboxAditionalEventBanboxFind) action;

		element = getIdMCRZRgrCriterio().getSelectedItem();
		String campo = (element.getValue() != null) ? element.getValue()
				.toString() : null;

		List<IBeanAbstracto> listaDatos = null;
		log.info("Ejecutando el mEtodo [onConsultar()]...");
		if (!isDinamic) {
			if (parametros == null) {
				log.info("Ejecutando el mEtodo [onConsultar()]...");
				if (!isToLowerCase()) {
					log.info("Ejecutando el mEtodo [onConsultar()]...");
					log.info("Ejecutando el mEtodo [onConsultar()]..."+campo+"-"+"criterio"+"-"+getObjetoClase());
					listaDatos = idMCRZPGCTRLMaestro.listar(setObjeto(campo,
							criterio, getObjetoClase()));

				} else {
					log.info("Ejecutando el mEtodo [onConsultar()]...");
					listaDatos = idMCRZPGCTRLMaestro.listar(setObjeto(
							getObjetoClase(), campo, criterio));
				}

			} else {
				if (!isToLowerCase()) {
					parametros.put("OBJETO",
							setObjeto(campo, criterio, getObjetoClase()));
					idMCRZPGCTRLMaestro.setParametros(parametros);
					listaDatos = idMCRZPGCTRLMaestro.listar(null);
				} else {
					parametros.put("OBJETO",
							setObjeto(getObjetoClase(), campo, criterio));

					idMCRZPGCTRLMaestro.setParametros(parametros);

					listaDatos = idMCRZPGCTRLMaestro.listar(null);
				}
			}
		} else {
			log.info("Ejecutando el mEtodo [onConsultar()]...");
			if (parametros == null) {
				parametros = new HashMap<String, Object>();
			}
			if (!isToLowerCase()) {
				parametros.put("OBJETO",
						setObjeto(campo, criterio, getObjetoClase()));
			} else {
				parametros.put("OBJETO",
						setObjeto(getObjetoClase(), campo, criterio));
			}

			idMCRZPGCTRLMaestro.setParametros(parametros);

			listaDatos = idMCRZPGCTRLMaestro.listar(null);
		}

		if (listaDatos != null && !listaDatos.isEmpty()) {
			log.info("Ejecutando el mEtodo [onConsultar()]...");
			getIdMCRZLbxlista().setVisible(true);
			if (enterBandbox) {
				if (listaDatos.size() == 1) {
					onSeleccionarMaestro(listaDatos.get(0), this);
					if (interfaz != null) {
						interfaz.onValidateSeleccion(
								idMCRZLbxlista.getItemAtIndex(0),
								listaDatos.get(0));
					}
				} else {
					this.setOpen(true);
				}
			}
		} else {
			getIdMCRZLbxlista().getItems().clear();
			tSecuencia.setRawValue(null);
			this.setRawValue(null);
			this.setOpen(true);
		}
	}

	/**
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
	 * @name onSeleccionarMaestro
	 * @param object
	 * @param padre
	 * @throws Exception
	 * @desc
	 */
	private void onSeleccionarMaestro(final IBeanAbstracto object,
			final BandboxFindPaging padre) throws Exception {
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
	 * @type Método de la clase BandboxFindPaging.java
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
	 * @type Método de la clase BandboxFindPaging.java
	 * @name isToLowerCase
	 * @return toLowerCase
	 * @descp obtiene el valor de toLowerCase
	 */
	public boolean isToLowerCase() {
		return toLowerCase;
	}

	/**
	 * Esta es la parametrizacion del pagingControl (Control de paginacion).
	 * Creacion del render para los resultados
	 * 
	 * @throws Exception
	 */

	private void parametrizarPagingControl() throws Exception {
		log.info("Ejecutando metodo [parametrizarPagingControl()]...");

		idMCRZPGCTRLMaestro.setComponenteReferencia(idMCRZLbxlista);
		idMCRZPGCTRLMaestro.setStatementConsultaPaginada(getConsultaPaginada());
		idMCRZPGCTRLMaestro.setPageSize(5);
		idMCRZPGCTRLMaestro.setConsultaDinamica(isDinamic());
		idMCRZPGCTRLMaestro.setSqlConsultaDinamica(getConsultaDinamica());
		idMCRZPGCTRLMaestro.setTipoClase(objetoClase);
		idMCRZPGCTRLMaestro.setTablaPadreFrom(tablaPadreFrom);
		idMCRZLhdNombre.setVisible(usaColumnaNombre);
		// idAUTZCTRLControl.setMold("os");

		idMCRZPGCTRLMaestro.setPagingControlFilas(new PagingControlFilas() {

			@Override
			public void render(Component component,
					IBeanAbstracto iBeanAbstracto) {

				// final Listitem fila = getAssemblerStandard()
				// .crearListitemDesdeDto((Listitem) component,
				// iBeanAbstracto);

				final Listitem fila = getAssemblerStandard()
						.crearListitemDinamico(null, iBeanAbstracto.getMD5(),
								(Listitem) component,
								iBeanAbstracto.getCodigo(),
								iBeanAbstracto.getNombre());
				final BandboxFindPaging padre = BandboxFindPaging.this;
				final IBeanAbstracto object = iBeanAbstracto;
				fila.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							public void onEvent(Event arg0) throws Exception {
								onSeleccionarMaestro(object, padre);
							}
						});
				if (interfaz != null) {
					fila.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
								public void onEvent(Event arg0)
										throws Exception {
									if (ids != null)
										fila.setAttribute("ID", ids);
									interfaz.onValidateSeleccion(fila, object);
								}
							});
				}

			}
		});

		idMCRZPGCTRLMaestro
				.agregarAtributosColumnas(new ArrayList<PagingControlColumnas>());
		idMCRZPGCTRLMaestro.setComponenteContenedor(idMCRZLftPaging);

		if (usaColumnaCodigo && usaColumnaNombre) {
			log.info("@parametrizarPagingControl ===> usaColumnaCodigo="
					+ usaColumnaCodigo + ";usaColumnaNombre="
					+ usaColumnaNombre);
			((Listfooter) idMCRZLftPaging.getChildren().get(0)).setSpan(2);
			((Listfooter) idMCRZLftPaging.getChildren().get(0))
					.appendChild(idMCRZPGCTRLMaestro);
			((Listfooter) idMCRZLftPaging.getChildren().get(1))
					.setVisible(false);
		} else if (!usaColumnaCodigo && usaColumnaNombre) {
			log.info("@parametrizarPagingControl ===> usaColumnaCodigo="
					+ usaColumnaCodigo + ";usaColumnaNombre="
					+ usaColumnaNombre);
			((Listfooter) idMCRZLftPaging.getChildren().get(0))
					.setVisible(false);
			((Listfooter) idMCRZLftPaging.getChildren().get(1))
					.setVisible(true);
			((Listfooter) idMCRZLftPaging.getChildren().get(1))
					.appendChild(idMCRZPGCTRLMaestro);
		} else if (usaColumnaCodigo && !usaColumnaNombre) {
			log.info("@parametrizarPagingControl ===> usaColumnaCodigo="
					+ usaColumnaCodigo + ";usaColumnaNombre="
					+ usaColumnaNombre);
			((Listfooter) idMCRZLftPaging.getChildren().get(0)).setSpan(1);
			((Listfooter) idMCRZLftPaging.getChildren().get(0))
					.appendChild(idMCRZPGCTRLMaestro);
			((Listfooter) idMCRZLftPaging.getChildren().get(1))
					.setVisible(false);
			((Listhead) this.getFellow("idMCRZListHead"))
					.removeChild(idMCRZLhdNombre);
			idMCRZLhdNombre.setStyle("visibility: hidden");
		}

	}

	/**
	 * Modificador del id o nombre de la consulta en el mapper (Archivo XML) de
	 * la consulta paginada
	 * 
	 * @param consultaPaginada
	 */
	public void setConsultaPaginada(String consultaPaginada) {
		this.consultaPaginada = consultaPaginada;
	}

	/**
	 * Retorna la id o nombre de la consulta en el mapper (Archivo XML) de la
	 * consulta paginada
	 * 
	 * @return
	 */
	public String getConsultaPaginada() {
		return consultaPaginada;
	}

	/**
	 * Modificador de la consultaDinamica
	 * 
	 * @param consultaDinamica
	 */
	public void setConsultaDinamica(String consultaDinamica) {
		this.consultaDinamica = consultaDinamica;
	}

	/**
	 * Retorna la consulta dinamica con la que esta trabajando el macro
	 * 
	 * @return
	 */
	public String getConsultaDinamica() {
		return consultaDinamica;
	}

	public boolean isPermitirBuscar() {
		return permitirBuscar;
	}

	public void setPermitirBuscar(boolean permitirBuscar) {
		this.permitirBuscar = permitirBuscar;
	}

	public String getMensajeExtra() {
		return mensajeExtra;
	}

	public void setMensajeExtra(String mensajeExtra) {
		this.mensajeExtra = mensajeExtra;
	}

	public String getTituloMensajeExtra() {
		return tituloMensajeExtra;
	}

	public void setTituloMensajeExtra(String tituloMensajeExtra) {
		this.tituloMensajeExtra = tituloMensajeExtra;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
