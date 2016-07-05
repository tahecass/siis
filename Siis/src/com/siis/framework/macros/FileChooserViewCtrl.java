/**
 * 
 */
package com.casewaresa.framework.macros;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.zkforge.ckez.CKeditor;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.iceberg_ge.dto.GETArchivo;
import com.casewaresa.iceberg_ge.dto.GETMimeType;
import com.casewaresa.iceberg_ge.facade.ParametrizacionFac;

/**
 * @author FUTCO
 * 
 * @name FileChooserViewCtrl.java
 * @date 30/08/2011
 * @desc
 */

public class FileChooserViewCtrl extends Hlayout implements
	IInicializarComponentes, AfterCompose {

    private String msgTamanhoCero;
    private String msgNoExisteMimeType;
    private String[] soloExtensiones = null;

    /**
     * Serial de la Clase
     */
    private static final long serialVersionUID = -2101706038034216899L;
    /**
     * Log de La clase
     */
    protected static Logger log = Logger.getLogger(FileChooserViewCtrl.class);

    /*
     * Atributos del Componente Macro
     */
    /**
     * Label para mostrar el nombre del blob
     */
    private A idFCLblNombreArchivo;
    /**
     * Boton para seleccionar el Archivo
     */
    private Button idFCBtnSeleccionarArchivo;

    /**
     * Referencia global del {@Link GETArchivo}
     */
    private GETArchivo dtoGetArchivo = new GETArchivo();
    /**
     * Referencia Global del Boton Limpiar cuando no es obligatorio
     */

    private Button idFCBtnCambiarArchivo;
    private Window idFCHZPupFileChooser;
    private Button idFCHZBtnSeleccionarPop;
    private Groupbox idFCCZGbxDocumentacionFileChooser;
    private CKeditor idFCZCKDescripcionFileChooser;

    private Button idFCZBtnFileChooserGuardar;

    private Button idFCBtnBorrarActivo;
    private A idFCZANombreArchivo;
    private Boolean esNoObligatorio;
    private Caption idFCZCPTDescripcionFileChooser;

    /**
     * Constructor por defecto
     */
    public FileChooserViewCtrl() {

	log.debug("Ejecutando Constructor por Defecto [ FileChooserViewCtrl ]");

	this.addEventListener(Events.ON_CREATE, new EventListener<Event>() {

	    @Override
	    public void onEvent(Event arg0) throws Exception {

		// cargarComponentesVista();
		getParent().setAttribute("vflex", "true");
		getParent().setAttribute("hflex", "true");
		getParent().invalidate();
	    }
	});

    }

    public void cambiarEstado(char opcion) {

	switch (opcion) {
	case 'N':
	    dtoGetArchivo.setOpcionActualizar(null);
	    dtoGetArchivo.setOpcionEliminar(null);
	    dtoGetArchivo.setOpcionNuevo("N");

	    break;

	case 'D':
	    dtoGetArchivo.setOpcionActualizar(null);
	    dtoGetArchivo.setOpcionEliminar("D");
	    dtoGetArchivo.setOpcionNuevo(null);

	    break;
	case 'U':

	    dtoGetArchivo.setOpcionActualizar("U");
	    dtoGetArchivo.setOpcionEliminar(null);
	    dtoGetArchivo.setOpcionNuevo(null);

	    break;

	}

    }

    /**
     * Asigna los eventos del Componente Macro
     * 
     * @type Método de la clase FileChooserViewCtrl.java
     * @name parametrizarEventos
     */
    private void parametrizarEventos() {
	log.info("Ejecutando Metodo [ parametrizarEventos ]");

	idFCHZBtnSeleccionarPop.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {

		    @Override
		    public void onEvent(Event arg0) throws Exception {
			onMostrarPopup();
		    }
		});
	idFCZBtnFileChooserGuardar.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {

		    @Override
		    public void onEvent(Event arg0) throws Exception {
			onAceptar();
		    }
		});

	idFCBtnBorrarActivo.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {

		    @Override
		    public void onEvent(Event arg0) throws Exception {

			dtoGetArchivo.setNombreArchivo(null);
			dtoGetArchivo.setMimetype(null);
			dtoGetArchivo.setDescripcion(null);
			dtoGetArchivo.setContenidoBinario(null);
			cambiarEstado('D');

			idFCZANombreArchivo.setLabel("");
			idFCZCKDescripcionFileChooser.setValue("");
			idFCZCKDescripcionFileChooser.applyProperties();
			idFCZCKDescripcionFileChooser.invalidate();
			idFCBtnBorrarActivo.setVisible(false);

			// archivo = (GETArchivo) dtoGetArchivo.clone();

		    }
		});

	idFCZANombreArchivo.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {

		    @Override
		    public void onEvent(Event arg0) throws Exception {
			onDescargar();

		    }
		});

	idFCHZPupFileChooser.addEventListener(Events.ON_CLOSE,
		new EventListener<Event>() {

		    @Override
		    public void onEvent(Event arg0) throws Exception {
			idFCHZPupFileChooser.setVisible(false);
			arg0.stopPropagation();

		    }
		});

    }

    private void onAceptar() {
	log.info("Ejecutando metodo [ onGuardarArchivo ]");
	try {

	    if (dtoGetArchivo != null) {
		if (!idFCLblNombreArchivo.getLabel().equals("")) {

		    if (dtoGetArchivo.getOpcionNuevo() != null) {

			log.trace("idFCZCKDescripcionFileChooser.getValue() ==>"
				+ idFCZCKDescripcionFileChooser.getValue());
			dtoGetArchivo
				.setDescripcion(idFCZCKDescripcionFileChooser
					.getValue());

		    } else if (dtoGetArchivo.getOpcionActualizar() != null) {
			if (dtoGetArchivo.getSecArchivo() != null) {

			    dtoGetArchivo
				    .setDescripcion(idFCZCKDescripcionFileChooser
					    .getValue());

			}

		    }
		    idFCZANombreArchivo.setLabel(recortarNombre(dtoGetArchivo
			    .getNombreArchivo()));

		    if (esNoObligatorio) {
			idFCBtnBorrarActivo.setVisible(true);
		    } else {
			idFCBtnBorrarActivo.setVisible(false);
		    }
		    idFCHZPupFileChooser.setVisible(false);

		}
		// archivo = (GETArchivo) dtoGetArchivo.clone();
	    }

	} catch (Exception e) {

	    log.error(e.getCause(), e);
	}

    }

    private void onMostrarPopup() {
	// idFCHZPupFileChooser.setPosition("parent");
	log.trace("onMostrarPopup");
	idFCHZPupFileChooser.setPosition("center");
	idFCHZPupFileChooser.doHighlighted();
	idFCHZPupFileChooser.setWidth("460px");
	idFCCZGbxDocumentacionFileChooser.setOpen(true);
	if (dtoGetArchivo != null && dtoGetArchivo.getSecArchivo() != null
		&& dtoGetArchivo.getMD5() != null) {

	    cambiarEstado('U');
	    idFCLblNombreArchivo.setLabel(recortarNombre(idFCZANombreArchivo
		    .getLabel()));
	    idFCBtnSeleccionarArchivo.setVisible(false);
	    idFCBtnCambiarArchivo.setVisible(true);
	    idFCZCKDescripcionFileChooser.setValue(dtoGetArchivo
		    .getDescripcion());
	    idFCZCKDescripcionFileChooser.applyProperties();
	    idFCZCKDescripcionFileChooser.invalidate();

	} else {
	    this.dtoGetArchivo = new GETArchivo();
	    cambiarEstado('N');
	    idFCLblNombreArchivo.setLabel("");
	    idFCBtnSeleccionarArchivo.setVisible(true);
	    idFCBtnCambiarArchivo.setVisible(false);
	    idFCZCKDescripcionFileChooser.setValue("");
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.casewaresa.framework.action.impl.IInicializarComponentes#
     * cargarComponentesVista()
     */
    @Override
    public void cargarComponentesVista() {
	log.info("Ejecutando metodo [ cargarComponentesVista ]");
	idFCHZPupFileChooser = (Window) this.getFellow("idFCHZPupFileChooser");
	idFCBtnBorrarActivo = (Button) this.getFellow("idFCBtnBorrarActivo");

	idFCZANombreArchivo = (A) this.getFellow("idFCZANombreArchivo");

	idFCLblNombreArchivo = (A) idFCHZPupFileChooser
		.getFellow("idFCLblNombreArchivo");
	idFCBtnSeleccionarArchivo = (Button) idFCHZPupFileChooser
		.getFellow("idFCBtnSeleccionarArchivo");
	idFCBtnCambiarArchivo = (Button) idFCHZPupFileChooser
		.getFellow("idFCBtnCambiarArchivo");

	idFCHZBtnSeleccionarPop = (Button) this
		.getFellow("idFCHZBtnSeleccionarPop");

	idFCCZGbxDocumentacionFileChooser = (Groupbox) idFCHZPupFileChooser
		.getFellow("idFCCZGbxDocumentacionFileChooser");

	idFCZCKDescripcionFileChooser = (CKeditor) idFCHZPupFileChooser
		.getFellow("idFCZCKDescripcionFileChooser");

	idFCZBtnFileChooserGuardar = (Button) idFCHZPupFileChooser
		.getFellow("idFCZBtnFileChooserGuardar");

	idFCZCPTDescripcionFileChooser =(Caption) idFCHZPupFileChooser
			.getFellow("idFCZCPTDescripcionFileChooser");
    }

    public void subirArchivo(Media media, String op) {
	log.debug("Ejecutando Método [ subirArchivo ]");

	log.trace("blob ==>" + media);
	log.trace("MimeType ==>" + media.getContentType());
	log.trace("op ==>" + op);
	try {

	    if (!validarSoloExtensio(media.getContentType())) {
		Messagebox.show(
			getMsgNoExisteMimeType().concat(": ").concat(
				media.getContentType()), "", Messagebox.ABORT,
			Messagebox.ERROR);
		return;
	    }

	    byte[] blob;
	    cambiarEstado(op.charAt(0));
	    if (media.getContentType().contains("text/"))
		blob = media.getStringData().getBytes();
	    else
		blob = IOUtils.toByteArray(media.getStreamData());

	    if (blob.length > 0) {
		boolean sw = false;

		List<?> listaMimeType = (List<?>) ParametrizacionFac
			.getFacade().obtenerListado(
				"obtenerMimeTypesPorTipoContenido",
				media.getContentType());

		if (listaMimeType != null && !listaMimeType.isEmpty()) {
		    
		    for (Object object : listaMimeType) {
			GETMimeType mimeType = (GETMimeType) object;
			if (media
				.getContentType()
				.toUpperCase()
				.equals(mimeType.getTipoContenido().toUpperCase())) {
			    sw = true;
			    this.seleccionarArchivo(blob, mimeType, media);
			    break;
			}
		    }
		} else {
		    Messagebox.show(getMsgNoExisteMimeType().concat(": ")
			    .concat(media.getContentType()), "",
			    Messagebox.ABORT, Messagebox.ERROR);
		}

		if (sw == false) {
		    Messagebox.show(getMsgNoExisteMimeType().concat(": ")
			    .concat(media.getContentType()), "",
			    Messagebox.ABORT, Messagebox.ERROR);
		}

	    } else {
		Messagebox.show(getMsgTamanhoCero(), "", Messagebox.ABORT,
			Messagebox.ERROR);
	    }

	} catch (IOException e) {
	    log.error(e.getCause(), e);
	} catch (InterruptedException e) {
	    log.error(e.getCause(), e);
	} catch (Exception e) {
	    log.error(e.getCause(), e);
	}

    }

    private boolean validarSoloExtensio(String mimeType) {
	log.debug("Ejecutando M�todo [ validarSoloExtensio ]");
	if (soloExtensiones != null && soloExtensiones.length > 0) {
	    for (String extension : soloExtensiones) {
		if (mimeType.trim().toLowerCase().equals(extension)) {
		    return true;
		}
	    }
	} else {
	    return true;
	}
	return false;

    }

    /**
     * Descarga el archivo Seleccionado
     * 
     * @type Método de la clase FileChooserViewCtrl.java
     * @name onDescargar
     * @throws Exception
     */
    private void onDescargar() throws Exception {
	log.debug("Ejecutando método [ onDescargar ]");
	if (dtoGetArchivo != null) {

	    if (dtoGetArchivo.getContenidoBinario() == null) {
		ParametrizacionFac.getFacade().obtenerRegistro(
			"GE_obtenerArchivo", dtoGetArchivo);
		Filedownload.save(new AMedia(dtoGetArchivo.getNombreArchivo(),
			null, dtoGetArchivo.getMimetype().getTipoContenido(),
			dtoGetArchivo.getContenidoBinario()));
	    } else {
		Filedownload.save(new AMedia(dtoGetArchivo.getNombreArchivo(),
			null, dtoGetArchivo.getMimetype().getTipoContenido(),
			dtoGetArchivo.getContenidoBinario()));
	    }

	}
    }

    /**
     * Asigna el array de byte a la variable global y agrega los atributos del
     * objeto Global de GETArchivo
     * 
     * @type Método de la clase FileChooserViewCtrl.java
     * @name seleccionarArchivo
     * @param blob
     * @param media
     * @param object
     * @throws Exception
     */
    private void seleccionarArchivo(byte[] blob, GETMimeType mimetype,
	    Media media) throws Exception {
	log.debug("Ejecutando método [ seleccionarArchivo ]");
	log.debug("blob ==>" + blob);
	log.debug("mimetype ==>" + mimetype);
	log.debug("media ==>" + media);
	if (dtoGetArchivo.getOpcionNuevo() != null) {
	    log.info("dtoGetArchivo.getOpcionNuevo() != null");
	    dtoGetArchivo.setSecArchivo(null);
	    dtoGetArchivo.setMD5(null);
	    dtoGetArchivo.setNombreArchivo(media.getName());
	    dtoGetArchivo.setContenidoBinario(blob);
	    dtoGetArchivo.setMimetype(mimetype);

	} else if (dtoGetArchivo.getOpcionActualizar() != null) {
	    log.info("dtoGetArchivo.getOpcionActualizar() != null");
	    dtoGetArchivo.setNombreArchivo(media.getName());
	    dtoGetArchivo.setContenidoBinario(blob);
	    dtoGetArchivo.setMimetype(mimetype);

	}

	idFCLblNombreArchivo.setLabel(recortarNombre(media.getName()));
	idFCBtnSeleccionarArchivo.setVisible(false);
	idFCBtnCambiarArchivo.setVisible(true);
	log.trace("dtoGetArchivo ==>" + dtoGetArchivo);
    }

    /**
     * @type Método de la clase FileChooserViewCtrl.java
     * @name getDto
     * @return dto
     * @descp obtiene el valor de dto
     */
    public GETArchivo getDto() {
	log.trace("dto ==> " + dtoGetArchivo);
	return this.dtoGetArchivo;
    }

    /**
     * @type Método de la clase FileChooserViewCtrl.java
     * @name setDto
     * @return void
     * @param recibe
     *            el parametro dto
     * @throws Exception
     * @descp modifica el atributo dto
     */
    public void setDto(GETArchivo dto) throws Exception {
	log.debug("Ejecutando metodo [ setDto ]");
	// cargarComponentesVista();
	if (dto != null && dto.getSecArchivo() != null) {
	    dtoGetArchivo = dto;

	    ParametrizacionFac.getFacade().obtenerRegistro(
		    "GE_obtenerArchivoSinBinario", dtoGetArchivo);

	    cambiarEstado('U');
	    idFCZANombreArchivo
		    .setLabel(recortarNombre(dto.getNombreArchivo()));

	    if (esNoObligatorio()) {
		idFCBtnBorrarActivo.setVisible(true);
	    } else {
		idFCBtnBorrarActivo.setVisible(false);
	    }

	} else {
	    this.dtoGetArchivo = new GETArchivo();

	    idFCLblNombreArchivo.setLabel("");
	    idFCLblNombreArchivo.setLabel("");
	    idFCZANombreArchivo.setLabel("");
	    idFCBtnBorrarActivo.setVisible(false);
	    cambiarEstado('N');
	}
	log.trace("DTO ==> " + dtoGetArchivo != null ? dtoGetArchivo : "N/A");
    }

    /**
     * @type Método de la clase FileChooserViewCtrl.java
     * @name getEsNoObligatorio
     * @return esNoObligatorio
     * @descp obtiene el valor de esNoObligatorio
     */
    public Boolean esNoObligatorio() {
	return esNoObligatorio;
    }

    /**
     * @type Método de la clase FileChooserViewCtrl.java
     * @name setEsNoObligatorio
     * @return void
     * @param recibe
     *            el parametro esNoObligatorio
     * @descp modifica el atributo esNoObligatorio
     */
    public void esNoObligatorio(Boolean esNoObligatorio) {
	this.esNoObligatorio = esNoObligatorio;
    }

    /**
     * @type Método de la clase FileChooserViewCtrl.java
     * @name getIdFCZANombreArchivo
     * @return idFCZANombreArchivo
     * @descp obtiene el valor de idFCZANombreArchivo
     */
    public A getIdFCZANombreArchivo() {
	return idFCZANombreArchivo;
    }

    /**
     * @type Método de la clase FileChooserViewCtrl.java
     * @name getIdFCBtnBorrarActivo
     * @return idFCBtnBorrarActivo
     * @descp obtiene el valor de idFCBtnBorrarActivo
     */
    public Button getIdFCBtnBorrarActivo() {
	return idFCBtnBorrarActivo;
    }

    /**
     * @type Método de la clase FileChooserViewCtrl.java
     * @name getIdFCHZBtnSeleccionarPop
     * @return idFCHZBtnSeleccionarPop
     * @descp obtiene el valor de idFCHZBtnSeleccionarPop
     */
    public Button getIdFCHZBtnSeleccionarPop() {
	return idFCHZBtnSeleccionarPop;
    }

    private String recortarNombre(String nombre) {

	if (nombre != null && nombre.length() > 25) {
	    nombre = nombre.substring(0, 20).concat("...");

	}

	return nombre;

    }

    /**
     * @return the msgNoExisteMimeType
     */
    public String getMsgNoExisteMimeType() {
	return msgNoExisteMimeType;
    }

    /**
     * @param msgNoExisteMimeType
     *            the msgNoExisteMimeType to set
     */
    public void setMsgNoExisteMimeType(String msgNoExisteMimeType) {
	this.msgNoExisteMimeType = msgNoExisteMimeType;
    }

    /**
     * @return the msgTamanhoCero
     */
    public String getMsgTamanhoCero() {
	return msgTamanhoCero;
    }

    /**
     * @param msgTamanhoCero
     *            the msgTamanhoCero to set
     */
    public void setMsgTamanhoCero(String msgTamanhoCero) {
	this.msgTamanhoCero = msgTamanhoCero;
    }


    public Caption getIdFCZCPTDescripcionFileChooser() {
		return idFCZCPTDescripcionFileChooser;
	}

	public void setIdFCZCPTDescripcionFileChooser(
			Caption idFCZCPTDescripcionFileChooser) {
		this.idFCZCPTDescripcionFileChooser = idFCZCPTDescripcionFileChooser;
	}

	public void setSoloExtensiones(String... soloExtensiones) {
	this.soloExtensiones = soloExtensiones;
    }

    @Override
    public void afterCompose() {
	log.info("Ejecutando M�todo [ afterCompose ]");
	this.cargarComponentesVista();
	this.parametrizarEventos();

    }

    /* GET y SET */

    public A getIdFCLblNombreArchivo() {
	return idFCLblNombreArchivo;
    }

    public void setIdFCLblNombreArchivo(A idFCLblNombreArchivo) {
	this.idFCLblNombreArchivo = idFCLblNombreArchivo;
    }

    public Button getIdFCBtnSeleccionarArchivo() {
	return idFCBtnSeleccionarArchivo;
    }

    public void setIdFCBtnSeleccionarArchivo(Button idFCBtnSeleccionarArchivo) {
	this.idFCBtnSeleccionarArchivo = idFCBtnSeleccionarArchivo;
    }

    public Button getIdFCBtnCambiarArchivo() {
	return idFCBtnCambiarArchivo;
    }

    public void setIdFCBtnCambiarArchivo(Button idFCBtnCambiarArchivo) {
	this.idFCBtnCambiarArchivo = idFCBtnCambiarArchivo;
    }

    public Window getIdFCHZPupFileChooser() {
	return idFCHZPupFileChooser;
    }

    public void setIdFCHZPupFileChooser(Window idFCHZPupFileChooser) {
	this.idFCHZPupFileChooser = idFCHZPupFileChooser;
    }

    public Groupbox getIdFCCZGbxDocumentacionFileChooser() {
	return idFCCZGbxDocumentacionFileChooser;
    }

    public void setIdFCCZGbxDocumentacionFileChooser(
	    Groupbox idFCCZGbxDocumentacionFileChooser) {
	this.idFCCZGbxDocumentacionFileChooser = idFCCZGbxDocumentacionFileChooser;
    }

    public CKeditor getIdFCZCKDescripcionFileChooser() {
	return idFCZCKDescripcionFileChooser;
    }

    public void setIdFCZCKDescripcionFileChooser(
	    CKeditor idFCZCKDescripcionFileChooser) {
	this.idFCZCKDescripcionFileChooser = idFCZCKDescripcionFileChooser;
    }

    public Button getIdFCZBtnFileChooserGuardar() {
	return idFCZBtnFileChooserGuardar;
    }

    public void setIdFCZBtnFileChooserGuardar(Button idFCZBtnFileChooserGuardar) {
	this.idFCZBtnFileChooserGuardar = idFCZBtnFileChooserGuardar;
    }

    public Boolean getEsNoObligatorio() {
	return esNoObligatorio;
    }

    public void setEsNoObligatorio(Boolean esNoObligatorio) {
	this.esNoObligatorio = esNoObligatorio;
    }

    public void setIdFCHZBtnSeleccionarPop(Button idFCHZBtnSeleccionarPop) {
	this.idFCHZBtnSeleccionarPop = idFCHZBtnSeleccionarPop;
    }

    public void setIdFCBtnBorrarActivo(Button idFCBtnBorrarActivo) {
	this.idFCBtnBorrarActivo = idFCBtnBorrarActivo;
    }

    public void setIdFCZANombreArchivo(A idFCZANombreArchivo) {
	this.idFCZANombreArchivo = idFCZANombreArchivo;
    }

}
