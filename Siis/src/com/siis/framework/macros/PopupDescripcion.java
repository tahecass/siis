package com.casewaresa.framework.macros;

import org.apache.log4j.Logger;
import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Popup;

import com.casewaresa.framework.macros.contract.IPopupDescripcionAditionalEvent;

public class PopupDescripcion extends Popup implements IdSpace, AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2159791553324026304L;
	public Logger log = Logger.getLogger(this.getClass());
	private String labelCaption;
	private String labelGuardar;
	private boolean isEditar = false;
	private static boolean isVisibleBtnMaximizar = false;
	private String descripcion;
	private A guardar;
	private Html htmlDescripcion;
	private CKeditor ckEditorDescripcion;
	private Button btnMaximizar;
	private IPopupDescripcionAditionalEvent actionObject;
	private static Component componenteReferencia;
	private static String posicion;

	@Override
	public void afterCompose() {
		log.info("Ejecutando el metodo [ afterCompose ]");
		try {
			this.getChildren().clear();
			crearComponentes();
			if (!isEditar)
				htmlDescripcion
						.setContent("<div style='border-style:"
								+ "double;border:1px;width:100%;height:120px;overflow:auto;"
								+ "font-size:12px;background-color:#E9E9E9;cursor:text'>"
								+ (this.descripcion != null ? this.descripcion
										: " ").toString() + "</div>");
			else
				ckEditorDescripcion.setValue(descripcion);

			parametrizarEventos();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void parametrizarEventos() {
		log.info("Ejecutando el metodo [ parametrizarEventos ]");
		btnMaximizar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					public void onEvent(Event arg0) throws Exception {

						setOpen(getComponenteReferencia(), getPosicion(), "350");
					}
				});

	}

	/**
	 * 
	 */
	public PopupDescripcion() {
	}

	private void crearComponentes() {
		log.info("Ejecutando el metodo [ crearComponentes ]");
		Groupbox groubox = new Groupbox();
		btnMaximizar = new Button("Ver más");
		org.zkoss.zul.Caption caption = new org.zkoss.zul.Caption();
		caption.setLabel(labelCaption != null ? labelCaption : "Descripción");
		groubox.appendChild(caption);

		if (!isEditar) {
			htmlDescripcion = new Html();
			groubox.appendChild(htmlDescripcion);
			groubox.setWidth("350px");
		} else {
			ckEditorDescripcion = new CKeditor();
			ckEditorDescripcion.setToolbar("Basic");
			groubox.appendChild(ckEditorDescripcion);
			guardar = new A(labelGuardar != null ? labelGuardar : "Guardar");
			guardar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							descripcion = ckEditorDescripcion.getValue();
							if (actionObject != null)
								actionObject.obtenerDescripcion(descripcion);
							close();
						}
					});
			groubox.appendChild(guardar);
		}
		
		if (isVisibleBtnMaximizar)
			groubox.appendChild(btnMaximizar);

		this.appendChild(groubox);
	}

	public boolean isVisibleBtnMaximizar() {
		return isVisibleBtnMaximizar;
	}

	public static void setVisibleBtnMaximizar(boolean isVisibleBtnMax) {
	isVisibleBtnMaximizar = isVisibleBtnMax;
	}

	/**
	 * @return the labelCaption
	 */
	public String getLabelCaption() {
		return labelCaption;
	}

	/**
	 * @param labelCaption
	 *            the labelCaption to set
	 */
	public void setLabelCaption(String labelCaption) {
		this.labelCaption = labelCaption;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Component getComponenteReferencia() {
		return componenteReferencia;
	}

	public static void setComponenteReferencia(Component componenteRefe) {
		componenteReferencia = componenteRefe;
	}

	public String getPosicion() {
		return posicion;
	}

	public static void setPosicion(String pos) {
		posicion = pos;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param ckEditorDescripcion
	 *            the ckEditorDescripcion to set
	 */
	public void setCkEditorDescripcion(CKeditor ckEditorDescripcion) {
		this.ckEditorDescripcion = ckEditorDescripcion;
	}

	/**
	 * @return the ckEditorDescripcion
	 */
	public CKeditor getCkEditorDescripcion() {
		return ckEditorDescripcion;
	}

	/**
	 * @param isEditar
	 *            the isEditar to set
	 */
	public void setEditar(boolean isEditar) {
		this.isEditar = isEditar;
	}

	/**
	 * @return the isEditar
	 */
	public boolean isEditar() {
		return isEditar;
	}

	/**
	 * @param actionObject
	 *            the actionObject to set
	 */
	public void setActionObject(IPopupDescripcionAditionalEvent actionObject) {
		this.actionObject = actionObject;
	}

	/**
	 * @return the labelGuardar
	 */
	public String getLabelGuardar() {
		return labelGuardar;
	}

	/**
	 * @param labelGuardar
	 *            the labelGuardar to set
	 */
	public void setLabelGuardar(String labelGuardar) {
		this.labelGuardar = labelGuardar;
	}

	public void setOpen(Component componenteReferencia, String pocision) {
		log.info("Ejecutando el Metodo[setOpen...]");
		try {
			super.getChildren().clear();
			crearComponentes();

			if (!isEditar)
				htmlDescripcion
						.setContent("<div style='border-style:"
								+ "double;border:1px;width:100%;height:120px;overflow:auto;"
								+ "font-size:12px;background-color:#E9E9E9;cursor:text'>"
								+ (this.descripcion != null ? this.descripcion
										: " ").toString() + "</div>");
			else
				ckEditorDescripcion.setValue(descripcion);

			if (pocision != null && !pocision.isEmpty()) {
				super.open(pocision, pocision);
			} else {
				super.open(null);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void setOpen(Component componenteReferencia, String pocision,
			String height) {
		log.info("Ejecutando el Metodo[setSisePopup...] "
				+ componenteReferencia);
		try {
			super.getChildren().clear();
			crearComponentes();

			if (!isEditar)
				htmlDescripcion
						.setContent("<div style='border-style:"
								+ "double;border:1px;width:100%;height:"
								+ height
								+ "px;overflow:auto;"
								+ "font-size:12px;background-color:#E9E9E9;cursor:text'>"
								+ (this.descripcion != null ? this.descripcion
										: " ").toString() + "</div>");
			else
				ckEditorDescripcion.setValue(descripcion);

			if (pocision != null && !pocision.isEmpty()) {
				super.open(getComponenteReferencia(), pocision);
			} else {
				super.open(null);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
