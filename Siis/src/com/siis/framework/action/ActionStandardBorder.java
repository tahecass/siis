package com.siis.framework.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import com.casewaresa.framework.action.impl.ExcepcionPopupAction;
import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.dto.impl.LlaveNatural;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.helper.ZKProcesosComunesHelper;
import com.casewaresa.framework.macros.DragDropTree;
//import com.casewaresa.framework.util.MyMessageBox;
import com.casewaresa.framework.util.Utilidades;
import com.casewaresa.framework.zk.notificaciones.Notificaciones;
import com.casewaresa.iceberg_aa.dto.AATEjecutable;

/**
 * 
 * @author Caseware
 * @date 23/01/2007
 * @name ActionStandard.java
 * @desc Define el comportamiento que debe tener todos los Actions del proyecto
 *       adem��s provee las funcionalidades de: - validarFormulario: valida los
 *       controles contenidos dentro de un control con ID=formulario -
 *       limpiarFormulario: limpia los controles contenidos dentro de un control
 *       con ID=formulario - setMensajeHistorico: Coloca un mensaje en la zona
 *       de mensajes hist��ricos de las ventanas - limpiarHistoricoMensajes:
 *       limpia el contenido de la zona de mensajes hist��ricos de las ventanas
 *       - persistirObjeto: ayuda a maneter persistido un objeto... hibernate -
 *       confirmarTransaccion: ayuda a manetner los datos sincronizados por
 *       medio de un flush (commit) - onConfirmacionPopup: muestra una pantalla
 *       de confirmaci��n de borrado - lanzarExcepcion: env��a una excepci��n
 *       ocurrida a una ventana modal.
 */
public abstract class ActionStandardBorder extends Borderlayout {

	private static final long serialVersionUID = -3731189029345315935L;
	/**
	 * desc esta es la variable [ log ] de la clase [ ActionStandar.java ]
	 */
	protected static Logger log = Logger.getLogger(ActionStandardBorder.class);
	public boolean iniciada = false;

	public ActionStandardBorder() {
		log.info("ActionStandardBorder....");
		iniciada = true;
	}

	/**
	 * Deshabilita un Contenedor de objetos ZK
	 * 
	 * @type Metodo de la clase ActionStandardBorder.java
	 * @name deshabilitarFormulario
	 * @param idFormulario
	 */
	public void deshabilitarFormulario(Component idFormulario) {
		Utilidades.deshabilitarCampos(idFormulario);
	}

	/**
	 * @type m�todo de la clase ActionStandardBorder.java
	 * @name generarReporte
	 * @param idMapper
	 *            representa el identificador del DTO en el mapa de reportes
	 *            disponibles
	 * @param titulo
	 *            representa el titulo del reporte en la plantilla definida.
	 * @param subtitulo
	 *            representa el subtitulo del reporte en la plantilla definida.
	 * @param list
	 *            debido los reportes pueden ser generados a partir de una lista
	 *            de objetos ya cargados en memoria es decir no hay que ejecutar
	 *            una consulta SQL a la base de datos, este par��metro
	 *            representa esa lista de objetos los cuales son procesados
	 *            internamente por el ReportBuilder que es el encargado de la
	 *            generaci��n de reportes.
	 * @param componente
	 *            especifica el nombre del componente o m��dulo al cual
	 *            pertenece la entidad (DTO) del reporte a generar
	 * @throws Exception
	 *             Cualquier Excepcion
	 * @desc Eencapsula la funcionalidad de generaci��n din��mica de reportes
	 *       para tablas b��sicas. Debe ser invocado en cada uno de los action
	 *       que necesiten tener esta opci��n
	 */
	public void generarReporte(String idMapper, String fuente, String modulo, Component component) throws Exception {
		log.info("Ejecutando m�todo [generarReporte]...");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id", idMapper);
		parametros.put("fuente", fuente);
		parametros.put("modulo", modulo);
		parametros.put("SEC_EJECUTABLE_PADRE", component.getAttribute("SEC_EJECUTABLE"));

		Component emergente = getEmergente("SRZ_REPORTE", parametros);
		if (emergente instanceof Window) {
			Window win = (Window) emergente;
			win.setClosable(true);
			emergente.setAttribute("TITULO_NOTIFICACIONES_GRITTER", ((Window) emergente).getTitle());
			win.doModal();
		}

	}

	public void generarSubReporte(String idMapper, String fuente, String modulo, Component component,
			String contieneSubreporte, Map<String, Object> parametros) throws Exception {
		log.info("Ejecutando m�todo [generarReporte]...");
		parametros.put("id", idMapper);
		parametros.put("fuente", fuente);
		parametros.put("modulo", modulo);
		parametros.put("contieneSubreporte", contieneSubreporte);
		parametros.put("SEC_EJECUTABLE_PADRE", component.getAttribute("SEC_EJECUTABLE"));

		Component emergente = getEmergente("SRZ_REPORTE", parametros);
		if (emergente instanceof Window) {
			Window win = (Window) emergente;
			win.setClosable(true);
			emergente.setAttribute("TITULO_NOTIFICACIONES_GRITTER", ((Window) emergente).getTitle());
			win.doModal();
		}

	}

	private Component getEmergente(String nombre, Map<String, Object> param) throws Exception {
		log.info("Ejecutando m�todo [ getEmergente().. ]");
		log.debug("nombre ==>" + nombre);
		log.debug("param ==>" + param);

		AATEjecutable detalle = Utilidades.obtenerDetalle(nombre);
		Component component = Executions.createComponentsDirectly(detalle.getFuente(), null, null, param);
		if (component instanceof Window) {
			// AGREGAMOS EL TITULO DE LAS NOTIFICACIONES COMO UN ATRIBUTO
			component.setAttribute("TITULO_NOTIFICACIONES_GRITTER", ((Window) component).getTitle());
		}

		return component;
	}

	/**
	 * @type Metodo de la clase ActionStandardBorder.java
	 * @name habiliarFormularo
	 * @param idFormulario
	 * @desc Este m�todo se encarga de deshabilitar los campos del formulario
	 *       que llega como par���metro
	 **/
	public void habilitarFormulario(Component idFormulario) {
		Utilidades.habilitarCampos(idFormulario);

		if (idFormulario instanceof Groupbox)
			idFormulario.invalidate();
	}

	/**
	 * Nuevo metodo para mostrar las notificaciones en el gritter informando que
	 * la lista de resultados esta vacia
	 * 
	 * @param size
	 *            Indica el tama���o de la lista
	 * @param msgListaVacia
	 *            Indica cual es el mensaje que sa���dra si la lista esta vacia
	 */
	public void isListConsultaEmptyGritter(int size, String msgListaVacia) {
		log.info("Ejecutando el metodo[isListConsultaEmptyGritter]");
		if (size == 0) {
			// Se obtiene el titulo de la forma para las notificaciones
			String titulo = this.obtenerTitulo();
			Notificaciones.mostrarNotificacionInformacion(titulo, msgListaVacia, 7000);
		}
	}

	/**
	 * @type m�todo de la clase ActionStandard
	 * @name mostrarExcepcion
	 * @return void
	 * @desc permite lanzar un popup modal con el error ocurrido
	 */
	public void lanzarExcepcion(Excepcion exception) {

		log.info("ejecutando [ lanzarExcepcion ]... ");

		ExcepcionPopupAction win = null;

		if (this.hasFellow("mensajeExcepcion")) {
			this.getFellow("mensajeExcepcion").detach();
		}

		win = (ExcepcionPopupAction) Executions.createComponents(IExcepcion.PANTALLA_EXCEPCIONPOPUP, null, null);

		// ---mandamos la excepcion
		win.setException(exception);

		// --mostramos la pantalla
		win.doModal();
	}

	public void limpiarFormulario(Component componente) { //
		log.info("ejecutando [ limpiarFormulario ]... ");
		Utilidades.limpiarCampos(componente);
	}

	/**
	 * Reinicia la configuracion de las Tablas
	 * 
	 * @param idGrid
	 *            Cualquier componente qeu herede de {@link Grid}
	 * @throws Excepcion
	 *             Cualquier Excepcion
	 */
	public void limpiarGridConsultas(Grid idGrid) throws Excepcion {

		ZKProcesosComunesHelper.limpiarGridConsultas(idGrid);
	}

	public void limpiarHistoricoMensajes(Component componentGrid, Component componentGrubox) {
		log.warn("Eliminar este metodo en el ZUL");
	}

	/**
	 * @Class ActionStandar
	 * @name limpiarHistoricoMensajes
	 * @return void
	 * @desc Este m�todo se encarga de limpiar la grilla que contiene el
	 *       historico de mensajes el parametro es utilizado en el metodo
	 *       setMensajehistorico para que limpie la grilla antes de escribir en
	 *       ella.
	 * 
	 */

	private void limpiarHistoricoMensajes(String idGridHistorico, String idGroupHistorico) {

		if (idGridHistorico != null && idGroupHistorico != null) {
			if (this.hasFellow(idGridHistorico)) {
				Grid grilla = (Grid) this.getFellow(idGridHistorico);
				// ocultamos el groupbox que contiene
				// la grilla
				Groupbox groupinformacion = (Groupbox) this.getFellow(idGroupHistorico);
				groupinformacion.setVisible(false);

				grilla.getRows().getChildren().clear();
			}
		}
	}

	/**
	 * @Class ActionStandar
	 * @name limpiarListboxConsultasBandbox
	 * @return void
	 * @desc Este m�todo se encarga de limpiar las listas de consultas
	 */
	public void limpiarListboxConsultas(Listbox idLista) {
		// --- obtenemos el contenedor del
		// formulario
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -limpiamos la lista
		ZKProcesosComunesHelper.limpiarListboxConsultas(idLista);
	}

	/**
	 * @Class ActionStandar
	 * @name limpiarFormulario
	 * @return boolean
	 * @desc Este m�todo se encarga de reiniciar los campos del formulario
	 * 
	 *       public void limpiarFormulario(String idFormulario) { // ---
	 *       obtenemos el contenedor del // formulario AbstractComponent
	 *       contenedorFormulario = (AbstractComponent) this
	 *       .getFellow(idFormulario);
	 * 
	 *       // instanciamos la clase que ayudar�� en // procesos comunes en los
	 *       formualrio como // la validaci��n ZKProcesosComunesHelper zkHelper
	 *       = new ZKProcesosComunesHelper();
	 * 
	 *       // -validamos el formulario
	 *       zkHelper.limpiarCampos(contenedorFormulario,
	 *       AbstractComponent.class); }
	 * 
	 *       /**
	 * @Class ActionStandar
	 * @name limpiarListboxConsultas
	 * @return void
	 * @desc Este m�todo se encarga de limpiar las listas de consultas
	 */
	public void limpiarListboxConsultas(String idLista) {
		// --- obtenemos el contenedor del
		// formulario
		AbstractComponent lista = (AbstractComponent) this.getFellow(idLista);

		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		if (lista instanceof Listbox) {
			// -limpiamos la lista
			ZKProcesosComunesHelper.limpiarListboxConsultas((Listbox) lista);
		}
	}

	/**
	 * @Class ActionStandar
	 * @name limpiarListboxConsultas
	 * @return void
	 * @desc Este m�todo se encarga de limpiar las listas de consultas
	 */
	public void limpiarListboxConsultasBandbox(Listbox idLista) {
		// --- obtenemos el contenedor del
		// formulario
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -limpiamos la lista
		ZKProcesosComunesHelper.limpiarListboxConsultasBandbox(idLista);
	}

	private String obtenerTitulo() {
		log.trace("obtenerTitulo ==========> obtenerTitulo");
		String titulo = "INFO";
		if (this instanceof IActionListbox || this instanceof IActionGrid2) {
			if (this.getParent() != null) {
				titulo = ((Tab) ((Tabpanel) (this.getParent())).getTabbox().getTabs().getChildren()
						.get(((Tabpanel) (this.getParent())).getIndex())).getLabel();
			} else {
				if (this.getAttribute("TITULO_NOTIFICACIONES_GRITTER") != null) {
					titulo = this.getAttribute("TITULO_NOTIFICACIONES_GRITTER").toString();
				}
			}
		} else if (this instanceof DragDropTree) {

		} else {
			if (this.getAttribute("TITULO_NOTIFICACIONES_GRITTER") != null) {
				titulo = this.getAttribute("TITULO_NOTIFICACIONES_GRITTER").toString();
			}
		}
		return titulo;
	}

	/**
	 * @type m�todo de la clase ActionStandardBorder.java
	 * @name onClickTack
	 * @param sigla
	 *            representa las inciales de la forma que lo est�� invocando
	 *            (esto es para saber cual es el nombre de los componentes va a
	 *            afectar)
	 * @desc este m�todo encapsula la funcionalidad del nuevo bot��n para
	 *       ocultar/mostrar la secci��n de b��squedas de la vista definida como
	 *       plantilla est��ndar para todas las formas de Iceberg.
	 */
	public void onClickTack(String sigla) {

		log.info("Ejecutando m�todo [ onClickTack ]...");

		limpiarHistoricoMensajes(sigla + "ZGridHistoricoMensajes", sigla + "ZGbxHistoricoMensajes");

		if (iniciada) {
			this.onClickTackInicial(sigla);
			iniciada = false;
		} else {

			if (((Checkbox) this.getFellow(sigla + "ZCbxTack")).isChecked()) {
				((Image) this.getFellow(sigla + "ZImgTack")).setSrc("imagenes/tackOff.png");
				((Image) this.getFellow(sigla + "ZImgTack")).setTooltiptext("Mostrar panel de consulta");
				((Checkbox) this.getFellow(sigla + "ZCbxTack")).setChecked(false);
				((West) this.getFellow(sigla + "ZWstWest")).setOpen(false);

				((West) this.getFellow(sigla + "ZWstWest")).invalidate();
			} else {
				((Image) this.getFellow(sigla + "ZImgTack")).setSrc("imagenes/tackOn.png");
				((Image) this.getFellow(sigla + "ZImgTack")).setTooltiptext("Ocultar panel consulta");
				((Checkbox) this.getFellow(sigla + "ZCbxTack")).setChecked(true);
				((West) this.getFellow(sigla + "ZWstWest")).invalidate();
				((West) this.getFellow(sigla + "ZWstWest")).setOpen(true);
			}

			((Image) this.getFellow(sigla + "ZImgTack")).invalidate();
		}
	}

	public void onClickTackInicial(String sigla) {

		((Image) this.getFellow(sigla + "ZImgTack")).setSrc("imagenes/tackOff.png");
		((Image) this.getFellow(sigla + "ZImgTack")).setTooltiptext("Ocultar panel de consulta");
		((Checkbox) this.getFellow(sigla + "ZCbxTack")).setChecked(false);
		((West) this.getFellow(sigla + "ZWstWest")).setOpen(true);

		((West) this.getFellow(sigla + "ZWstWest")).invalidate();

		((Image) this.getFellow(sigla + "ZImgTack")).invalidate();
	}

	/**
	 * @type m�todo de la clase ActionStandar
	 * @name onConfirmacionPopup
	 * @return int
	 * @desc muestra un popup de confirmaci��n para confirmar el borrado
	 */
	public Integer onConfirmacionPopup(String pantalla, String llave, String msgEstaSeguroBorrar,
			String msgEliminarRegistro) {

		Integer resultado = 0;

		try {
			resultado = Messagebox.show(msgEstaSeguroBorrar + pantalla + llave + " ?", msgEliminarRegistro,
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_POR_INTERRUPCION, "", e));
		}
		return resultado;
	}

	/**
	 * @type m�todo de la clase ActionStandardBorder.java
	 * @name onConfirmacionPopup
	 * @param pantalla
	 * @param llave
	 * @param nombre
	 * @return
	 * @desc muestra un popup de confirmaci��n para confirmar el borrado
	 */
	public Integer onConfirmacionPopup(String pantalla, String llave, String nombre, String msgEstaSeguroBorrar,
			String msgEliminarRegistro) {

		Integer resultado = 0;
		StringBuilder builder = new StringBuilder();

		try {
			builder.append(msgEstaSeguroBorrar);
			builder.append(pantalla);

			if (llave != null && !llave.equals("")) {
				builder.append("[ ");
				builder.append(llave);
				builder.append(" ] ");
			}
			if (nombre != null && !nombre.equals("")) {
				builder.append(nombre);
			}
			if (nombre != null)
				builder.append("?");
			resultado = Messagebox.show(builder.toString(), msgEliminarRegistro, Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_POR_INTERRUPCION, "", e));
		}
		return resultado;
	}

	public Integer onConfirmacionPopup(String pantalla, String llave, String width, String height,
			String msgEstaSeguroBorrar, String msgEliminarRegistro) {

		Integer resultado = 0;

		try {

			resultado = Messagebox.show(msgEstaSeguroBorrar + pantalla + llave + " ?", msgEliminarRegistro,
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_POR_INTERRUPCION, "", e));
		}
		return resultado;
	}

	/**
	 * Este m�todo se debe llamar en el
	 * {@link ActionStandardBorder.onSeleccionarMaestro(T dto)} Implementados en
	 * los actions
	 * 
	 * @author casewaredes03 @Fecha_Creaci���n 20/04/2012
	 * @Fecha_Modificacion 20/04/2012
	 * @since 0.1
	 * @param sigla
	 *            Sigla de los ids los componentes de la vista desde id...
	 */

	public void onSeleccionarClickTack(String sigla) {
		log.info("Ejecutando m�todo [ onSeleccionarClickTack ]");

		limpiarHistoricoMensajes(sigla + "ZGridHistoricoMensajes", sigla + "ZGbxHistoricoMensajes");

		if (!((Checkbox) this.getFellow(sigla + "ZCbxTack")).isChecked()) {
			((Checkbox) this.getFellow(sigla + "ZCbxTack")).setChecked(false);
			((West) this.getFellow(sigla + "ZWstWest")).setOpen(false);

			((West) this.getFellow(sigla + "ZWstWest")).invalidate();

		}
		((Image) this.getFellow(sigla + "ZImgTack")).invalidate();

	}

	/**
	 * Nuevo metodo para mostrar las notificaciones en el gritter ya que la zona
	 * norte de los mensajes en las formas va a desaparecer
	 * 
	 * @param estado
	 * @param label
	 * @param llavesNaturales
	 * @param mensajeExt
	 */
	public void setMensajeHistoricoGritter(int estado, String label, List<LlaveNatural> llavesNaturales,
			String mensajeExt) {

		String imagen = null;
		String llave = "";

		if (llavesNaturales != null) {
			llave = " <b>[ ";
			for (int i = 0; i < llavesNaturales.size(); i++) {
				LlaveNatural llaveNatural = llavesNaturales.get(i);

				llave = llave + llaveNatural.getLlave() + " " + llaveNatural.getValor();

				if (llavesNaturales.size() - 1 > i) {
					llave = llave + ", ";
				}
			}
			llave = llave + " ]</b> ";
		}

		if (mensajeExt == null) {
			mensajeExt = "";
		}

		try {
			switch (estado) {
			case IConstantes.ESTADO_INSERCION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_INSERCION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_EDICION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_EDICION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_BORRAR_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_BORRAR_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_DEFAULT_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_DEFAULT_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				llave = "";
				break;
			}

			case IConstantes.ESTADO_ADVERTENCIA: {
				imagen = IConstantes.ESTADO_IMAGEN_ADVERT;
				llave = "";
				break;
			}

			default: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
			}
			}// fin switch

		} catch (Exception e) {
			// --error del negocio...
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
			log.error(e.getMessage(), e);
		}

		Html lbl = new Html(label + llave + " " + mensajeExt);

		// Se obtiene el titulo de la forma para las notificaciones
		String titulo = this.obtenerTitulo();

		// Validamos que la que notificacion debemos mostrar segun el estado de
		// la imagen
		if (imagen.equals(IConstantes.ESTADO_IMAGEN_OK)) {
			Notificaciones.mostrarNotificacionInformacion(titulo, lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ERROR)) {
			Notificaciones.mostrarNotificacionError(titulo, lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ADVERT)) {
			Notificaciones.mostrarNotificacionAlerta(titulo, lbl.getContent(), 7000);
		} else {
			Notificaciones.mostrarNotificacion(titulo, lbl.getContent(), 7000);
		}
	}

	/* METODOS PARA LAS NOTIFICACIONES CON EL GRITTER */

	/**
	 * Nuevo metodo para mostrar las notificaciones en el gritter ya que la zona
	 * norte de los mensajes en las formas va a desaparecer
	 * 
	 * @param estado
	 * @param label
	 * @param llaveRegistro
	 * @param mensajeExt
	 */
	public void setMensajeHistoricoGritter(int estado, String label, String llaveRegistro, String mensajeExt) {
		String llave;

		if (mensajeExt == null || mensajeExt.isEmpty()) {
			mensajeExt = "";
		}

		if (llaveRegistro == null || llaveRegistro.trim().isEmpty()) {
			llave = " <b>" + mensajeExt + "<b>";
		} else {
			llave = " <b>[ " + llaveRegistro + " ] " + mensajeExt + "</b>";
		}

		String imagen = null;

		try {
			switch (estado) {
			case IConstantes.ESTADO_INSERCION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;

				break;
			}
			case IConstantes.ESTADO_INSERCION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_EDICION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_EDICION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_BORRAR_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_BORRAR_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_ADVERTENCIA: {
				imagen = IConstantes.ESTADO_IMAGEN_ADVERT;
				break;
			}
			case IConstantes.ESTADO_DEFAULT_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_DEFAULT_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;

				break;
			}
			default: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
			}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, ""));
		}

		Html lbl = new Html(label + llave);

		// Se obtiene el titulo de la forma para las notificaciones
		String titulo = this.obtenerTitulo();

		// Validamos que la que notificacion debemos mostrar segun el estado de
		// la imagen
		if (imagen.equals(IConstantes.ESTADO_IMAGEN_OK)) {
			Notificaciones.mostrarNotificacionInformacion(titulo, lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ERROR)) {
			Notificaciones.mostrarNotificacionError(titulo, lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ADVERT)) {
			Notificaciones.mostrarNotificacionAlerta(titulo, lbl.getContent(), 7000);
		} else {
			Notificaciones.mostrarNotificacion(titulo, lbl.getContent(), 7000);
		}
	}

	public void setMensajeHistoricoGritter(int estado, String label, String llaveRegistro, String mensajeExt,
			Integer tiempo) {
		String llave;

		if (mensajeExt == null || mensajeExt.isEmpty()) {
			mensajeExt = "";
		}

		if (llaveRegistro == null || llaveRegistro.trim().isEmpty()) {
			llave = " <b>" + mensajeExt + "<b>";
		} else {
			llave = " <b>[ " + llaveRegistro + " ] " + mensajeExt + "</b>";
		}

		String imagen = null;

		try {
			switch (estado) {
			case IConstantes.ESTADO_INSERCION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;

				break;
			}
			case IConstantes.ESTADO_INSERCION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_EDICION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_EDICION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_BORRAR_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_BORRAR_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_ADVERTENCIA: {
				imagen = IConstantes.ESTADO_IMAGEN_ADVERT;
				break;
			}
			case IConstantes.ESTADO_DEFAULT_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_DEFAULT_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;

				break;
			}
			default: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
			}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, ""));
		}

		Html lbl = new Html(label + llave);

		// Se obtiene el titulo de la forma para las notificaciones
		String titulo = this.obtenerTitulo();

		// Validamos que la que notificacion debemos mostrar segun el estado de
		// la imagen
		if (imagen.equals(IConstantes.ESTADO_IMAGEN_OK)) {
			Notificaciones.mostrarNotificacionInformacion(titulo, lbl.getContent(), tiempo);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ERROR)) {
			Notificaciones.mostrarNotificacionError(titulo, lbl.getContent(), tiempo);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ADVERT)) {
			Notificaciones.mostrarNotificacionAlerta(titulo, lbl.getContent(), tiempo);
		} else {
			Notificaciones.mostrarNotificacion(titulo, lbl.getContent(), tiempo);
		}
	}

	public static void setMensajeHistoricoGritterStatic(int estado, String label, String llaveRegistro,
			String mensajeExt) {
		String llave;

		if (mensajeExt == null || mensajeExt.isEmpty()) {
			mensajeExt = "";
		}

		if (llaveRegistro == null || llaveRegistro.trim().isEmpty()) {
			llave = " <b>" + mensajeExt + "<b>";
		} else {
			llave = " <b>[ " + llaveRegistro + " ] " + mensajeExt + "</b>";
		}

		String imagen = null;

		try {
			switch (estado) {
			case IConstantes.ESTADO_INSERCION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;

				break;
			}
			case IConstantes.ESTADO_INSERCION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_EDICION_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_EDICION_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_BORRAR_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				break;
			}
			case IConstantes.ESTADO_BORRAR_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}
			case IConstantes.ESTADO_ADVERTENCIA: {
				imagen = IConstantes.ESTADO_IMAGEN_ADVERT;
				break;
			}
			case IConstantes.ESTADO_DEFAULT_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_DEFAULT_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;

				break;
			}
			default: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
			}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		}

		Html lbl = new Html(label + llave);

		// Se obtiene el titulo de la forma para las notificaciones
		String titulo = "Notificacion";

		// Validamos que la que notificacion debemos mostrar segun el estado de
		// la imagen
		if (imagen.equals(IConstantes.ESTADO_IMAGEN_OK)) {
			Notificaciones.mostrarNotificacionInformacion(titulo, lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ERROR)) {
			Notificaciones.mostrarNotificacionError(titulo, lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ADVERT)) {
			Notificaciones.mostrarNotificacionAlerta(titulo, lbl.getContent(), 7000);
		} else {
			Notificaciones.mostrarNotificacion(titulo, lbl.getContent(), 7000);
		}
	}

	/**
	 * Devuelve una instancia de una entidad de negocio con criterios definidos
	 * 
	 * @type m�todo de la clase ActionStandardBorder.java
	 * @name setObjeto
	 * @param campo
	 * @param criterio
	 * @param obj
	 * @return esta es una implementaci��n generalizada del anterior m�todo
	 *         usado para las consultas en los action���s (setObjeto). Con esta
	 *         generalizaci��n se evita tener que escribir por cada action el
	 *         m�todo, por lo cual el costo del desarrollo es mucho menor
	 */
	public IBeanAbstracto setObjeto(String campo, String criterio, IBeanAbstracto obj) {
		log.info("Ejecutando m�todo [ setObjeto() ]");
		if (criterio != null && !criterio.isEmpty()) {
			criterio = "%" + criterio.toUpperCase() + "%";

			if (campo.equals("codigo")) {
				obj.setCodigo(criterio);
			} else {
				if (campo.equals("nombre")) {
					obj.setNombre(criterio);
				}
			}
		}

		return obj;
	}

	/**
	 * @Class ActionStandar
	 * @name validarFormulario
	 * @return boolean
	 * @desc Este m�todo se encarga de validar el formualario, este metodo
	 *       recibe el id del componente a validar
	 */
	public boolean validarFormulario(AbstractComponent idComponente) {
		// instanciamos la clase que ayudar�� en
		// procesos comunes en los formulario como
		// la validaci���n
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -validamos el formulario
		return ZKProcesosComunesHelper.validarCampos(idComponente, InputElement.class);
	}

	/**
	 * @Class ActionStandar
	 * @name validarFormulario
	 * @return boolean
	 * @desc Este m�todo se encarga de validar el formualario, este metodo
	 *       recibe el id del componente a validar
	 */
	public boolean validarFormulario(String idComponente) {

		// --- obtenemos el contenedor del
		// formulario
		AbstractComponent contenedorFormulario = (AbstractComponent) this.getFellow(idComponente);

		// instanciamos la clase que ayudar�� en
		// procesos comunes en los formulario como
		// la validaci���n
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -validamos el formulario
		return ZKProcesosComunesHelper.validarCampos(contenedorFormulario, InputElement.class);
	}

	public void onLimpiarMensaje() {
		log.warn("Eliminar este metodo del ZUl");
	}

	public void setAtributoContexto(String atributo, String valor) {
		log.info("Ejecutando el m�todo: [ setAtributoContexto ]");
		log.debug("setAtributoContexto(atributo => " + atributo + ", valor => " + valor + ")");

		try {
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("ATRIBUTO", atributo);
			parameters.put("VALOR", valor);

			ParametrizacionFac.getFacade().obtenerRegistro("AAP_setAtributoContexto", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}
}