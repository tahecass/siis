package com.siis.framework.action;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.Messagebox;

import com.casewaresa.framework.action.impl.ExcepcionPopupAction;
import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.dto.impl.LlaveNatural;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.helper.ZKProcesosComunesHelper;
import com.casewaresa.framework.util.MyMessageBox;
import com.casewaresa.framework.util.Utilidades;
import com.casewaresa.framework.zk.notificaciones.Notificaciones;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name ActionStandard.java
 * @desc Define el comportamiento que debe tener todos los Actions del proyecto
 *       adem�s provee las funcionalidades de: - validarFormulario: valida los
 *       controles contenidos dentro de un control con ID=formulario -
 *       limpiarFormulario: limpia los controles contenidos dentro de un control
 *       con ID=formulario - setMensajeHistorico: Coloca un mensaje en la zona
 *       de mensajes hist�riocos de las ventanas - limpiarHistoricoMensajes:
 *       limpia el contenido de la zona de mensajes hist�riocos de las ventanas
 *       - persistirObjeto: ayuda a maneter persistido un objeto... hibernate -
 *       confirmarTransaccion: ayuda a manetner los datos sincronizados por
 *       medio de un flush (commit) - onConfirmacionPopup: muestra una pantalla
 *       de confirmaci�n de borrado - lanzarExcepcion: env�a una excepci�n
 *       ocurrida a una ventana modal.
 */
public abstract class ActionStandard extends Window {

	private static final long serialVersionUID = 939920614940786952L;
	/**
	 * desc esta es la variable [ log ] de la clase [ ActionStandar.java ]
	 */
	protected static Logger log = Logger.getLogger(ActionStandard.class);

	/**
	 * Deshabilita un Contenedor de objetos ZK
	 * 
	 * @type Metodo de la clase ActionStandardBorder.java
	 * @name deshabilitarFormulario
	 * @param idFormulario
	 * 
	 *            public void deshabilitarFormulario(String idFormulario) { //
	 *            --- obtenemos el contenedor del // formulario
	 *            AbstractComponent contenedorFormulario = (AbstractComponent)
	 *            this .getFellow(idFormulario);
	 * 
	 *            // instanciamos la clase que ayudará en // procesos comunes en
	 *            los formualrio como // la validación ZKProcesosComunesHelper
	 *            zkHelper = new ZKProcesosComunesHelper();
	 * 
	 *            // -validamos el formulario
	 *            zkHelper.deshabilitarCampos(contenedorFormulario,
	 *            AbstractComponent.class); }
	 * 
	 *            /** Deshabilita un Contenedor de objetos ZK
	 * 
	 * @type Metodo de la clase ActionStandardBorder.java
	 * @name deshabilitarFormulario
	 * @param idFormulario
	 */
	public void deshabilitarFormulario(Component idFormulario) {
		Utilidades.deshabilitarCampos(idFormulario);
	}

	/**
	 * @type Metodo de la clase ActionStandardBorder.java
	 * @name habiliarFormularo
	 * @param idFormulario
	 * @desc Este método se encarga de deshabilitar los campos del formulario
	 *       que llega como par�metro
	 */
	public void habilitarFormulario(Component idFormulario) {
		Utilidades.habilitarCampos(idFormulario);
	}

	/**
	 * Este metodo muestra una notificacion informando que la lista de
	 * resultados esta vacia
	 * 
	 * @param size
	 *            Indica el tama�o de la lista
	 * @param msgListaVacia
	 *            Indica cual es el mensaje que sa�dra si la lista esta vacia
	 */
	public void isListConsultaEmptyGritter(int size, String msgListaVacia) {
		log.info("Ejecutando el metodo[isListConsultaEmptyGritter]");
		if (size == 0) {
			// Se obtiene el titulo de la forma para las notificaciones
			String titulo = this.getTitle();
			Notificaciones.mostrarNotificacionInformacion(titulo,
					msgListaVacia, 7000);
		}
	}

	/**
	 * @type Método de la clase ActionStandard
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

		win = (ExcepcionPopupAction) Executions.createComponents(
				IExcepcion.PANTALLA_EXCEPCIONPOPUP, null, null);

		// ---mandamos la excepcion
		win.setException(exception);

		// --mostramos la pantalla
		win.doModal();
	}

	public void limpiarFormulario(Component component) { //
		Utilidades.limpiarCampos(component);
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

	/**
	 * @type Metodo de la clase ActionStandardBorder.java
	 * @name habiliarFormularo
	 * @param idFormulario
	 * @desc Este método se encarga de deshabilitar los campos del formulario
	 *       que llega como par�metro
	 * 
	 *       public void habilitarFormulario(String idFormulario) { // ---
	 *       obtenemos el contenedor del // formulario AbstractComponent
	 *       contenedorFormulario = (AbstractComponent) this
	 *       .getFellow(idFormulario);
	 * 
	 *       // instanciamos la clase que ayudará en // procesos comunes en los
	 *       formualrio como // la validación ZKProcesosComunesHelper zkHelper =
	 *       new ZKProcesosComunesHelper();
	 * 
	 *       // -validamos el formulario
	 *       zkHelper.habilitarCampos(contenedorFormulario,
	 *       AbstractComponent.class); }
	 * 
	 */

	/**
	 * @Class ActionStandar
	 * @name limpiarHistoricoMensajes
	 * @return void
	 * @desc Este m�todo se encarga de limpiar la grilla que contiene el
	 *       historico de mensajes el parametro es utilizado en el metodo
	 *       setMensajehistorico para que limpie la grilla antes de escribir en
	 *       ella.
	 * 
	 * 
	 */

	private void limpiarHistoricoMensajes(String idGridHistorico,
			String idGroupHistorico) {
		if (idGridHistorico != null && idGroupHistorico != null) {
			Grid grilla = (Grid) this.getFellow(idGridHistorico);
			// ocultamos el groupbox que contiene
			// la grilla
			Groupbox groupinformacion = (Groupbox) this
					.getFellow(idGroupHistorico);
			groupinformacion.setVisible(false);

			grilla.getRows().getChildren().clear();
		}
	}

	/**
	 * @Class ActionStandar
	 * @name limpiarListboxConsultas
	 * @return void
	 * @desc Este método se encarga de limpiar las listas de consultas
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
	 * @desc Este método se encarga de reiniciar los campos del formulario
	 * 
	 *       public void limpiarFormulario(String idFormulario) { // ---
	 *       obtenemos el contenedor del // formulario AbstractComponent
	 *       contenedorFormulario = (AbstractComponent) this
	 *       .getFellow(idFormulario);
	 * 
	 *       // instanciamos la clase que ayudará en // procesos comunes en los
	 *       formualrio como // la validación ZKProcesosComunesHelper zkHelper =
	 *       new ZKProcesosComunesHelper();
	 * 
	 *       // -validamos el formulario
	 *       zkHelper.limpiarCampos(contenedorFormulario,
	 *       AbstractComponent.class); }
	 * 
	 *       public void isListConsultaEmpty(int size,Grid
	 *       idGridHistoricoMensaje,Groupbox idGbxHistoricoMensajes){
	 *       log.info("Ejecutando el metodo[isListConsultaEmpty]"); if(size==0)
	 *       this.setMensajeHistorico(idGridHistoricoMensaje,
	 *       idGbxHistoricoMensajes, IConstantes.ESTADO_INSERCION_ERROR,
	 *       "No se encontraron Registros", "", null); }
	 * 
	 *       /**
	 * @Class ActionStandar
	 * @name limpiarListboxConsultas
	 * @return void
	 * @desc Este método se encarga de limpiar las listas de consultas
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
	 * @type Método de la clase ActionStandard.java
	 * @name limpiarListboxConsultasBandbox
	 * @param idLista
	 * @desc Paginación de 5 en los BandBox de Consultas
	 */
	public void limpiarListboxConsultasBandbox(Listbox idLista) {
		// --- obtenemos el contenedor del
		// formulario
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -limpiamos la lista
		ZKProcesosComunesHelper.limpiarListboxConsultasBandbox(idLista);
	}

	/**
	 * @type Método de la clase ActionStandard.java
	 * @name onClickTack
	 * @param sigla
	 *            representa las inciales de la forma que lo está invocando
	 *            (esto es para saber cual es el nombre de los componentes va a
	 *            afectar)
	 * @desc este método encapsula la funcionalidad del nuevo botón para
	 *       ocultar/mostrar la sección de búsquedas de la vista definida como
	 *       plantilla estándar para todas las formas de Iceberg.
	 */
	public void onClickTack(String sigla) {

		log.info("Ejecutando método [ onClickTack ]...");

		limpiarHistoricoMensajes(sigla + "ZGridHistoricoMensajes", sigla
				+ "ZGbxHistoricoMensajes");

		if (((Checkbox) this.getFellow(sigla + "ZCbxTack")).isChecked()) {
			((Image) this.getFellow(sigla + "ZImgTack"))
					.setSrc("imagenes/tackOff.png");
			((Image) this.getFellow(sigla + "ZImgTack"))
					.setTooltiptext("Mostrar panel de consulta");
			((Checkbox) this.getFellow(sigla + "ZCbxTack")).setChecked(false);
			((West) this.getFellow(sigla + "ZWstWest")).setOpen(false);

			((West) this.getFellow(sigla + "ZWstWest")).invalidate();
		} else {
			((Image) this.getFellow(sigla + "ZImgTack"))
					.setSrc("imagenes/tackOn.png");
			((Image) this.getFellow(sigla + "ZImgTack"))
					.setTooltiptext("Ocultar panel consulta");
			((Checkbox) this.getFellow(sigla + "ZCbxTack")).setChecked(true);
			((West) this.getFellow(sigla + "ZWstWest")).invalidate();
			((West) this.getFellow(sigla + "ZWstWest")).setOpen(true);
		}

		((Image) this.getFellow(sigla + "ZImgTack")).invalidate();
	}

	/**
	 * @type Método de la clase ActionStandar
	 * @name onConfirmacionPopup
	 * @return int
	 * @desc muestra un popup de confirmación para confirmar el borrado
	 */
	public Integer onConfirmacionPopup(String pantalla, String llave,
			String msgEstaSeguroBorrar, String msgEliminarRegistro) {

		Integer resultado = 0;

		try {
			resultado = MyMessageBox.show(msgEstaSeguroBorrar + pantalla
					+ "<br><b>" + llave + "</b>?", msgEliminarRegistro,
					MyMessageBox.YES | MyMessageBox.NO, MyMessageBox.QUESTION,
					null, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_POR_INTERRUPCION, "", e));
		}
		return resultado;
	}

	/**
	 * @type Método de la clase ActionStandard.java
	 * @name onConfirmacionPopup
	 * @param pantalla
	 * @param llave
	 * @param nombre
	 * @return
	 * @desc
	 */
	public Integer onConfirmacionPopup(String pantalla, String llave,
			String nombre, String msgEstaSeguroBorrar,
			String msgEliminarRegistro) {

		Integer resultado = 0;
		StringBuilder builder = new StringBuilder();

		try {
			builder.append(msgEstaSeguroBorrar);
			builder.append(pantalla);
			if (llave != null && !llave.isEmpty()) {
				builder.append("[ ");
				builder.append(llave);
				builder.append(" ] ");
			}

			if (nombre != null && !nombre.isEmpty())
				builder.append(nombre);

			resultado = Messagebox.show(builder.toString(),
					msgEliminarRegistro, Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_POR_INTERRUPCION, "", e));
		}
		return resultado;
	}

	public Integer onConfirmacionPopup(String pantalla, String llave,
			String width, String height, String msgEstaSeguroBorrar,
			String msgEliminarRegistro) {

		Integer resultado = 0;

		try {
			resultado = MyMessageBox.show(msgEstaSeguroBorrar + pantalla
					+ "<br><b>" + llave + "</b>?", msgEliminarRegistro,
					MyMessageBox.YES | MyMessageBox.NO, MyMessageBox.QUESTION,
					width, height);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_POR_INTERRUPCION, "", e));
		}
		return resultado;
	}

	/**
	 * 
	 * @param estado
	 * @param label
	 * @param llavesNaturales
	 * @param mensajeExt
	 */
	public void setMensajeHistoricoGritter(int estado, String label,
			List<LlaveNatural> llavesNaturales, String mensajeExt) {

		String imagen = null;
		String llave = "";

		if (llavesNaturales != null) {
			llave = " <b>[ ";
			for (int i = 0; i < llavesNaturales.size(); i++) {
				LlaveNatural llaveNatural = llavesNaturales.get(i);

				llave = llave + llaveNatural.getLlave() + " "
						+ llaveNatural.getValor();

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
		String titulo = this.getTitle();
		// Validamos que la que notificacion debemos mostrar segun el estado de
		// la imagen
		if (imagen.equals(IConstantes.ESTADO_IMAGEN_OK)) {
			Notificaciones.mostrarNotificacionInformacion(titulo,
					lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ERROR)) {
			Notificaciones.mostrarNotificacionError(titulo, lbl.getContent(),
					7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ADVERT)) {
			Notificaciones.mostrarNotificacionAlerta(titulo, lbl.getContent(),
					7000);
		} else {
			Notificaciones.mostrarNotificacion(titulo, lbl.getContent(), 7000);
		}
	}

	/**
	 * 
	 * @param estado
	 * @param label
	 * @param llaveRegistro
	 * @param mensajeExt
	 */
	public void setMensajeHistoricoGritter(int estado, String label,
			String llaveRegistro, String mensajeExt) {
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
			case IConstantes.ESTADO_DEFAULT_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_DEFAULT_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}

			case IConstantes.ESTADO_ADVERTENCIA: {
				imagen = IConstantes.ESTADO_IMAGEN_ADVERT;
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
		String titulo = this.getTitle();

		// Validamos que la que notificacion debemos mostrar segun el estado de
		// la imagen
		if (imagen.equals(IConstantes.ESTADO_IMAGEN_OK)) {
			Notificaciones.mostrarNotificacionInformacion(titulo,
					lbl.getContent(), 7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ERROR)) {
			Notificaciones.mostrarNotificacionError(titulo, lbl.getContent(),
					7000);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ADVERT)) {
			Notificaciones.mostrarNotificacionAlerta(titulo, lbl.getContent(),
					7000);
		} else {
			Notificaciones.mostrarNotificacion(titulo, lbl.getContent(), 7000);
		}
	}

	public void setMensajeHistoricoGritter(int estado, String label,
			String llaveRegistro, String mensajeExt, Integer tiempo) {
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
			case IConstantes.ESTADO_DEFAULT_OK: {
				imagen = IConstantes.ESTADO_IMAGEN_OK;
				llave = "";
				break;
			}
			case IConstantes.ESTADO_DEFAULT_ERROR: {
				imagen = IConstantes.ESTADO_IMAGEN_ERROR;
				break;
			}

			case IConstantes.ESTADO_ADVERTENCIA: {
				imagen = IConstantes.ESTADO_IMAGEN_ADVERT;
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
		String titulo = this.getTitle();

		// Validamos que la que notificacion debemos mostrar segun el estado de
		// la imagen
		if (imagen.equals(IConstantes.ESTADO_IMAGEN_OK)) {
			Notificaciones.mostrarNotificacionInformacion(titulo,
					lbl.getContent(), tiempo);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ERROR)) {
			Notificaciones.mostrarNotificacionError(titulo, lbl.getContent(),
					tiempo);
		} else if (imagen.equals(IConstantes.ESTADO_IMAGEN_ADVERT)) {
			Notificaciones.mostrarNotificacionAlerta(titulo, lbl.getContent(),
					tiempo);
		} else {
			Notificaciones
					.mostrarNotificacion(titulo, lbl.getContent(), tiempo);
		}
	}

	/* METODOS PARA LAS NOTIFICACIONES CON EL GRITTER */

	/**
	 * Devuelve una instancia de una entidad de negocio con criterios definidos
	 * 
	 * @type Método de la clase ActionStandard.java
	 * @name setObjeto
	 * @param campo
	 * @param criterio
	 * @param obj
	 * @return esta es una implementación generalizada del anterior método usado
	 *         para las consultas en los action’s (setObjeto). Con esta
	 *         generalización se evita tener que escribir por cada action el
	 *         método, por lo cual el costo del desarrollo es mucho menor
	 */
	public IBeanAbstracto setObjeto(String campo, String criterio,
			IBeanAbstracto obj) {

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
	 * @desc Este método se encarga de validar el formualario, este metodo
	 *       recibe el id del componente a validar
	 */
	public boolean validarFormulario(AbstractComponent idComponente) {
		// instanciamos la clase que ayudará en
		// procesos comunes en los formulario como
		// la validaci�n
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -validamos el formulario
		return ZKProcesosComunesHelper.validarCampos(idComponente,
				InputElement.class);
	}

	/**
	 * @Class ActionStandar
	 * @name validarFormulario
	 * @return boolean
	 * @desc Este método se encarga de validar el formualario, este metodo
	 *       recibe el id del componente a validar
	 */
	public boolean validarFormulario(String idComponente) {

		// --- obtenemos el contenedor del
		// formulario
		AbstractComponent contenedorFormulario = (AbstractComponent) this
				.getFellow(idComponente);

		// instanciamos la clase que ayudará en
		// procesos comunes en los formulario como
		// la validaci�n
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -validamos el formulario
		return ZKProcesosComunesHelper.validarCampos(contenedorFormulario,
				InputElement.class);
	}

	public void limpiarHistoricoMensajes(Component componentGrid,
			Component componentGrubox) {
		log.warn("Eliminar este metodo en el ZUL");
	}

	public void onLimpiarMensaje() {
		log.warn("Eliminar este metodo del ZUl");
	}

	public void setAtributoContexto(String atributo, String valor) {
		log.info("Ejecutando el método: [ setAtributoContexto ]");
		log.debug("setAtributoContexto(atributo => " + atributo + ", valor => "
				+ valor + ")");

		try {
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("ATRIBUTO", atributo);
			parameters.put("VALOR", valor);

			ParametrizacionFac.getFacade().obtenerRegistro(
					"AAP_setAtributoContexto", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

}
