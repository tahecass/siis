package com.siis.framework.action;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;

import com.casewaresa.framework.dto.IBeanAbstracto;

public abstract class IActionGridDetalle extends ActionStandard{
	private static final long serialVersionUID = 2480568341920509801L;

	private Button idZBtnAgregar;
	private Button idZBtnVerMas;
	private Button idZBtnEditar;
	private Button idZBtnEliminar;
	private Listbox listaDetalle;

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
			Button eliminar, Listbox listbox) {
		this.idZBtnAgregar = agregar;
		this.idZBtnEditar = editar;
		this.idZBtnEliminar = eliminar;
		this.idZBtnVerMas = verMas;
		this.listaDetalle = listbox;
	}

	public void inicializarEventos(Button agregar, Button verMas,
			Button editar, Button eliminar) {

		if (this.idZBtnAgregar != null) {
			
			this.idZBtnAgregar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							onAgregarDetalle();
						}
					});
			this.idZBtnAgregar.setAutodisable(idZBtnAgregar.getId());
		}

		if (this.idZBtnEditar != null) {
			
			this.idZBtnEditar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (listaDetalle.getSelectedItem() != null)
								onEditarMaestro((IBeanAbstracto) listaDetalle
										.getSelectedItem().getValue());
						}
					});
			this.idZBtnEditar.setAutodisable(idZBtnEditar.getId());
		}

		if (this.idZBtnVerMas != null) {
			
			this.idZBtnVerMas.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (listaDetalle.getSelectedItem() != null)
								onVerMasMaestro((IBeanAbstracto) listaDetalle
										.getSelectedItem().getValue());
						}
					});
			this.idZBtnVerMas.setAutodisable(idZBtnVerMas.getId());
		}

		if (this.idZBtnEliminar != null) {
			
			this.idZBtnEliminar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (listaDetalle.getSelectedItem() != null)
								onBorrarMaestro((IBeanAbstracto) listaDetalle
										.getSelectedItem().getValue());
						}
					});
			idZBtnEliminar.setAutodisable(idZBtnEliminar.getId());
		}
	}

	public void habilitarDeshabilitarBotones(boolean isEditar) {
		if (this.idZBtnEditar != null) {
			this.idZBtnEditar.setVisible(isEditar);
			this.idZBtnEditar.setDisabled(!isEditar);
		}

		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(!isEditar);

		if (this.idZBtnVerMas != null) {
			this.idZBtnVerMas.setVisible(!isEditar);
			this.idZBtnVerMas.setDisabled(isEditar);
		}
	}

	public void habilitarBotones() {
		if (this.idZBtnAgregar != null)
			this.idZBtnAgregar.setDisabled(false);

		if (this.idZBtnEditar != null)
			this.idZBtnEditar.setDisabled(false);

		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(false);

		if (this.idZBtnVerMas != null)
			this.idZBtnVerMas.setDisabled(false);
	}

	public void deshabilitarBotones() {
		if (this.idZBtnAgregar != null)
			this.idZBtnAgregar.setDisabled(true);

		if (this.idZBtnEditar != null)
			this.idZBtnEditar.setDisabled(true);

		if (this.idZBtnEliminar != null)
			this.idZBtnEliminar.setDisabled(true);

		if (this.idZBtnVerMas != null)
			this.idZBtnVerMas.setDisabled(true);
	}
	
}
