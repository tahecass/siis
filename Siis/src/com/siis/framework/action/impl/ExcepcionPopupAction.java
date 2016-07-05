package com.siis.framework.action.impl;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Style;
import org.zkoss.zul.Window;

import com.casewaresa.framework.action.ActionStandard;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.helper.StackTraceToolHelper;
import com.casewaresa.iceberg_ge.dto.GETExcepcion;
import com.casewaresa.iceberg_ir.dto.IRTExcepcionI18n;

/**
 * @author Caseware
 * @date 22/01/2007
 * @name ExcepcionPopupAction.java
 * @desc Provee una implementaciï¿½n para generar una ventana modal con
 *       informaciï¿½n de un error o excepciï¿½n
 */
public class ExcepcionPopupAction extends ActionStandard {

	private static final long serialVersionUID = 1L;

	protected static Logger log = Logger.getLogger(ExcepcionPopupAction.class);

	private String stackTrace = null;
	private String infoExcepcion = null;
	private String mensaje = null;
	private Excepcion excepcion = null;
	private IRTExcepcionI18n excepcionI18n = new IRTExcepcionI18n();
	private int origenExcepcion = 0;
	private Boolean isIR = false;
	private Boolean unknown = false;

	/**
	 * @type Constructor de la clase ExcepcionPopupAction
	 * @name ExcepcionPopupAction
	 * @desc Cosntrutor de la clase sin parï¿½metros
	 */
	public ExcepcionPopupAction() {

	}

	/**
	 * @type Mï¿½todo de la clase ExcepcionPopupAction
	 * @name getPException
	 * @return void
	 * @param exception
	 * @desc obtiene el valor de la propiedad pException
	 */
	public Exception getExcepcion() {
		return excepcion;
	}

	/**
	 * @type Mï¿½todo de la clase ExcepcionPopupAction
	 * @name setPException
	 * @return Exception
	 * @param pException
	 * @desc Actualiza el valor de la propiedad pException
	 */
	public void setException(Excepcion excepcion) {
		this.excepcion = excepcion;
	}

	/**
	 * @type Mï¿½todo de la clase ExcepcionPopupAction
	 * @name actualizarIconoFuente
	 * @return void
	 * @desc muestra un ï¿½cono que caracteriza la excepciï¿½n
	 */
	private void actualizarIconoFuente() {
		log.info("Ejecutando metodo: [ actualizarIconoFuente ]");

		Image imagen = (Image) this.getFellow("imgIcono");
		imagen.setSrc(this.excepcion.getPImagen());
	}

	private int getOrigenExcepcion() {
		log.info("Ejecutando metodo: [ getOrigenExcepcion ]");

		if (stackTrace.indexOf("ORA-20000:") >= 0) {
			log.info("Database Exception");
			// Elimino Encabezado de la excepcion
			stackTrace = stackTrace
					.substring(stackTrace.indexOf("ORA-20000:") + 11);
			this.excepcion
					.setPImagen(IExcepcion.EXCEPCION_IMAGEN_ORIGEN_DATABASE);

			return IExcepcion.EXCEPCION_ORIGEN_DATABASE;
		} else if (stackTrace.indexOf("Exception:") >= 0) {
			log.info("Application Exception");
			this.excepcion
					.setPImagen(IExcepcion.EXCEPCION_IMAGEN_ORIGEN_APLICACION);

			return IExcepcion.EXCEPCION_ORIGEN_APLICACION;
		} else {
			log.info("Unknown Exception");
			unknown = true;
			this.excepcion
					.setPImagen(IExcepcion.EXCEPCION_IMAGEN_ORIGEN_DESCONOCIDO);

			return IExcepcion.EXCEPCION_ORIGEN_DESCONOCIDO;
		}
	}

	private void getExcepcionDataBase() {
		log.info("Ejecutando metodo: [ getExcepcionDataBase ]");
		
		try {
			int posIniTipo = stackTrace.indexOf("{") + 1;
			int posFinTipo = stackTrace.indexOf("}", posIniTipo);
			String tipoExcepcion = stackTrace.substring(posIniTipo, posFinTipo);
			log.debug("tipoExcepcion: " + tipoExcepcion);

			int posIniCode = stackTrace.indexOf("[");
			int posFinCode = stackTrace.indexOf("]", posIniCode);
			String errCode = stackTrace.substring(posIniCode, posFinCode + 1);

			if (tipoExcepcion.equals("IR")) {
				isIR = true;
				int posIniMsg = stackTrace.indexOf("<MSJ>") + 5;
				int posFinMsg = stackTrace.indexOf("</MSJ>", posIniMsg);
				infoExcepcion = errCode + " "
						+ stackTrace.substring(posIniMsg, posFinMsg);
				mensaje = stackTrace.substring(posIniMsg, posFinMsg);
			} else {
				isIR = false;
				int posIniMsg2 = stackTrace.indexOf("{DB}");
				int posFinMsg2 = stackTrace.indexOf("<EJE>", posIniMsg2);
				infoExcepcion = stackTrace
						.substring(posIniMsg2 + 4, posFinMsg2);
				mensaje = stackTrace.substring(posIniMsg2 + 4, posFinMsg2);
			}

			log.debug("Id excepcion: " + infoExcepcion);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void getExcepcionAplicacion() {
		log.info("Ejecutando metodo: [ getExcepcionAplicacion ]");

		try {
			int posIniError = stackTrace.indexOf("Excepcion:");
			int posFinError = stackTrace.indexOf("at ", posIniError);

			log.info("posIniError: " + posIniError);
			log.info("posFinError: " + posFinError);

			mensaje = stackTrace.substring(posIniError + 10, posFinError)
					.trim();

			log.debug("Mensaje: " + mensaje);

			GETExcepcion excepcion = new GETExcepcion();
			excepcion
					.setExcepcion(new Long(IExcepcion.EXCEPCION_NO_REGISTRADA));
			excepcionI18n.setExcepcion(excepcion);

			ParametrizacionFac.getFacade().obtenerRegistro(
					"IR_obtenerExcepcionI18n2", excepcionI18n);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mensaje = "Error de aplicaci—n";
			log.info("Mensaje: " + mensaje);
		}
	}

	/**
	 * @type Mï¿½todo de la clase ExcepcionPopupAction
	 * @name actualizarTitulo
	 * @return void
	 * @desc coloca un mensaje con el titulo de la forma
	 */
	private void actualizarTitulo() {
		log.info("Ejecutando metodo: [ actualizarTitulo ]");

		try {
			Window window = (Window) this.getFellow("mensajeExcepcion");
			Style style = new Style();
			style.setContent("div.z-window-header {font-size: 0.9em; color: blue;}");
			style.setParent(window);

			Label lblMensaje = (Label) this.getFellow("labelMensaje");
			window.setClosable(true);

			String titulo = window.getTitle();

			if (origenExcepcion == IExcepcion.EXCEPCION_ORIGEN_DATABASE) {
				log.debug("isIR: " + isIR);

				int posIniTit = stackTrace.indexOf("<TIT>");
				int posFinTit = stackTrace.indexOf("</TIT>", posIniTit);

				if (posIniTit > 0)
					titulo = titulo
							+ stackTrace.substring(posIniTit + 5, posFinTit);

				int posIniEje = stackTrace.indexOf("<EJE>");
				int posFinEje = stackTrace.indexOf("</EJE>", posIniEje);

				if (posIniEje > 0)
					titulo = mensaje.trim();

				log.debug("Info. excepcion: " + infoExcepcion + " - "
						+ stackTrace.substring(posIniEje + 5, posFinEje));
			} else {
				titulo = this.getAttribute("MSG_EXCEPCION").toString();
				mensaje = titulo;
			}

			window.setTitle(titulo.trim());
			lblMensaje.setValue(mensaje.trim());
		} catch (ComponentNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @type Mï¿½todo de la clase ExcepcionPopupAction
	 * @name actualizarCausa
	 * @return void
	 * @desc coloca un mensaje con la posible causa del error
	 */
	private void actualizarCausa() {
		log.info("Ejecutando metodo: [ actualizarCausa ]");
		Label labelCausa = (Label) this.getFellow("labelCausa");
		try {
			if (isIR) {
				int posIniError = stackTrace.indexOf("<CAU>");
				int posFinError = stackTrace.indexOf("</CAU>", posIniError);
				if (unknown) {
					labelCausa.setValue(this.getAttribute(
							"MSG_OPERACION_NO_EXITOSA").toString());
					log.info("Excepcion Desconocida.");
				} else {
					labelCausa.setValue(stackTrace.substring(posIniError + 5,
							posFinError));
				}
			} else {
				int posIniCausa = stackTrace.indexOf("Caused by:");
				if (posIniCausa > 0) {
					int posFinCausa = stackTrace.indexOf("at ", posIniCausa);
					if (unknown) {
						labelCausa.setValue(this.getAttribute(
								"MSG_OPERACION_NO_EXITOSA").toString());
						log.info("Excepcion Desconocida.");
					} else {
						labelCausa.setValue(stackTrace.substring(
								posIniCausa + 10, posFinCausa));
					}
				} else {
					labelCausa.setValue("No hay sugerencias");
				}
			}
		} catch (ComponentNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @type Mï¿½todo de la clase ExcepcionPopupAction
	 * @name actualizarSolucion
	 * @return void
	 * @desc coloca un mensaje con la posible causa del error
	 */
	private void actualizarSolucion() {
		log.info("Ejecutando metodo: [ actualizarSolucion ]");

		try {
			Label labelSolucion = (Label) this.getFellow("labelSolucion");

			if (isIR) {
				int posIniError = stackTrace.indexOf("<SOL>");
				int posFinError = stackTrace.indexOf("</SOL>", posIniError);

				labelSolucion.setValue(stackTrace.substring(posIniError + 5,
						posFinError));

			} else {
				labelSolucion.setValue(this.getAttribute(
						"MSG_SUGERENCIA_CONTACTO_ADMIN").toString());
			}

		} catch (ComponentNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zul.Window#doModal()
	 */
	@Override
	public void doModal() {
		log.info("Ejecutando metodo: [ doModal ]");

		this.stackTrace = StackTraceToolHelper.getStack2String(this.excepcion);

		origenExcepcion = getOrigenExcepcion();

		if (origenExcepcion == IExcepcion.EXCEPCION_ORIGEN_DATABASE)
			getExcepcionDataBase();
		else
			getExcepcionAplicacion();

		// --ponemos la imagen correspondiente a la fuente de la excepcion
		actualizarIconoFuente();

		// --ponemos el titulo del error en i18n
		actualizarTitulo();

		// --ponemos la causa de el error en i18n
		actualizarCausa();

		// --ponemos la posible solucion de el error en i18n
		actualizarSolucion();

		// --finalmente mostramos la ventana
		super.doModal();
	}
}