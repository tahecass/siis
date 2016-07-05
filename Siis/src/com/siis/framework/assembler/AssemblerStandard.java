package com.casewaresa.framework.assembler;

import java.util.List;

import org.jfree.util.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Toolbarbutton;

import com.casewaresa.framework.action.IActionGridDetalle;
import com.casewaresa.framework.action.IActionListItem;
import com.casewaresa.framework.action.IActionListbox;
import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.macros.GridDetail;

/**
 * @author Futco
 * @name AssemblerStandard.java
 * @date 26/10/2010
 * @desc
 */
public class AssemblerStandard {

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDesdeDto
	 * @param obj
	 * @return
	 * @desc permite crear un Listitem basico a partir de una referencia de
	 *       IBeanAbstracto, este podra contener hasta 3 celdas (Listcell):
	 *       codigo (en mayusculas), nombre, estado (opcional, si es null lo
	 *       omite. Sino tiene dos valores dos posibles valores �������A��������: activo,
	 *       �������I��������: inactivo).
	 */
	public Listitem crearListitemDesdeDto(IBeanAbstracto obj) {

		Listitem item = new Listitem();

		Listcell celda = new Listcell(obj.getCodigo());
		celda.setValue(obj.getCodigo());
		item.appendChild(celda);

		if (obj.getNombre() != null)
			celda = new Listcell(obj.getNombre());
		else
			celda = new Listcell();
		celda.setValue(obj.getPrimaryKey());
		item.appendChild(celda);

		if (obj.getEstado() != null) {
			if (obj.getEstado().equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(obj.getMD5());
			item.appendChild(celda);
		}

		return item;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDesdeDto
	 * @param obj
	 * @return
	 * @desc permite crear un Listitem b��sico a partir de una referencia de
	 *       IBeanAbstracto, este podr�� contener hasta 3 o mas celdas
	 *       (Listcell): c����digo (en may��sculas), nombre, estado (opcional, si
	 *       es null lo omite. Sino tiene dos valores dos posibles valores
	 *       �������A��������: activo, �������I��������: inactivo).
	 */
	public Listitem crearListitemDesdeDto(IBeanAbstracto obj, String... adds) {

		Listitem item = new Listitem();

		Listcell celda = new Listcell(obj.getCodigo());

		item.setValue(obj);
		if (obj.getCodigo() != null && !obj.getCodigo().isEmpty()) {

			celda.setValue(obj.getCodigo());
			item.appendChild(celda);
		}
		if (obj.getNombre() != null && !obj.getNombre().isEmpty()) {
			celda = new Listcell(obj.getNombre());
			celda.setValue(obj.getPrimaryKey());
			item.appendChild(celda);
		}

		if (adds != null)
			for (String value : adds) {
				celda = new Listcell(value);
				celda.setValue(value);
				item.appendChild(celda);
			}

		if (obj.getEstado() != null) {
			if (obj.getEstado().equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(obj.getMD5());
			item.appendChild(celda);
		}

		return item;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDesdeDto
	 * @param obj
	 *            representa al objeto IBeanAbstracto que tiene las propiedades
	 *            a renderizar
	 * @param colEmpty
	 *            si el p��rametro colEmpty es true agregar�� un Listcell en
	 *            blanco, para que posteriormente pueda ser utilizado por un
	 *            campo de selecci����n.
	 * @return
	 * @desc
	 */
	public Listitem crearListitemDesdeDto(IBeanAbstracto obj, boolean colEmpty) {
		Listitem item = crearListitemDesdeDto(obj);

		if (colEmpty)
			item.getChildren().add(0, new Listcell());

		return item;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDesdeDtoForSelect
	 * @param obj
	 * @return
	 * @desc permite crear un Listitem con una sola celda (Listcell) a partir de
	 *       una referencia hacia un IBeanAbstracto, estos son usados en las
	 *       listas de selecci����n de las b��squedas de las vistas estadares.
	 *       Tienen el siguiente formato: [ codigo ] nombre
	 */
	public Listitem crearListitemDesdeDtoForSelect(IBeanAbstracto obj) {

		StringBuilder stringBuilder = new StringBuilder("[");
		stringBuilder.append(obj.getCodigo());
		stringBuilder.append("] ");
		stringBuilder.append(obj.getNombre());

		Listitem item = new Listitem(stringBuilder.toString(),
				obj.getPrimaryKey());

		return item;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDinamico
	 * @param estado
	 * @param MD5
	 * @param values
	 * @return
	 * @desc permite crear un Listitem a partir del estado, MD5 y cualquier
	 *       cantidad de par��metros variables que se especifiquen en la
	 *       invocaci����n del m��todo, estos par��metros variables se deben separar
	 *       por comas(,) y ser��n renderizados como celdas (Listcell) antes del
	 *       estado. Nota: el MD5 se asocia a la celda del estado en el atributo
	 *       value.
	 */
	public Listitem crearListitemDinamico(String estado, String MD5,
			String... values) {

		Listitem item = new Listitem();
		Listcell celda = null;

		// agregando los valores dinamicos
		for (String value : values) {
			celda = new Listcell(value);
			celda.setValue(value);
			item.appendChild(celda);
		}

		// agregando el estado
		if (estado != null) {
			if (estado.equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(MD5);
			item.appendChild(celda);
		}

		return item;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDinamico
	 * @param values
	 * @param estado
	 * @param MD5
	 * @param values
	 * @return
	 * @desc permite crear un Listitem a partir del estado, MD5 y cualquier
	 *       cantidad de par��metros variables que se especifiquen en la
	 *       invocaci����n del m��todo, estos par��metros variables se deben separar
	 *       por comas(,) y ser��n renderizados como celdas (Listcell) antes del
	 *       estado. Nota: el MD5 se asocia a la celda del estado en el atributo
	 *       value.
	 */
	public Listitem crearListitemDinamico(String estado, String MD5,
			Listitem item, String... values) {
		Listcell celda = null;

		// agregando los valores dinamicos
		for (String value : values) {
			celda = new Listcell(value);
			celda.setValue(value);
			item.appendChild(celda);
		}

		// agregando el estado
		if (estado != null) {
			if (estado.equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(MD5);
			item.appendChild(celda);
		}

		return item;
	}

	public Listitem crearListitemDinamico(String estado, String MD5,
			final Listitem item, final IActionListItem action,
			boolean isEditar, boolean isEliminar, String... values) {
		Listcell celda = null;

		// agregando los valores dinamicos
		for (String value : values) {
			celda = new Listcell();

			if (value.contains("#text-align:right;")) {
				celda.setLabel(value.substring(0, value.indexOf("#")));
				celda.setStyle(value.substring((value.indexOf("#") + 1),
						value.indexOf(";")));
			} else {
				celda.setValue(value);
				celda.setLabel(value);
			}

			item.appendChild(celda);
		}

		// agregando el estado
		if (estado != null) {
			if (estado.equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(MD5);
			item.appendChild(celda);
		}
		if (isEditar) {
			Image imagen = new Image();
			imagen.setSrc(IConstantes.IMAGEN_EDITAR);
			imagen.setStyle("cursor: pointer; padding-right: 12px;");
			imagen.setTooltiptext("Editar");
			imagen.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						public void onEvent(Event arg0) throws Exception {

							action.onEditarMaestro((IBeanAbstracto) item
									.getValue());
						}
					});
			celda = new Listcell();
			celda.appendChild(imagen);
			item.appendChild(celda);
		}
		if (isEliminar) {
			Image imagen = new Image();
			imagen.setSrc(isEditar ? IConstantes.IMAGEN_BORRAR
					: IConstantes.IMAGEN_BORRAR_DISABLE);
			imagen.setStyle("cursor: pointer; padding-right: 12px;");
			imagen.setTooltiptext("Eliminar");

			if (isEditar) {
				imagen.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							public void onEvent(Event arg0) throws Exception {

								action.onBorrarMaestro((IBeanAbstracto) item
										.getValue());
							}
						});
			}
			celda.appendChild(imagen);
			item.appendChild(celda);
		}

		return item;
	}

	public Listitem crearListitemDinamico2(String estado, String MD5,
			Listitem item, Long a, Long b, String... values) {
		Listcell celda = null;

		// agregando los valores dinamicos
		for (String value : values) {
			celda = new Listcell(value);
			celda.setValue(value);
			item.appendChild(celda);
		}

		Listcell celdaI = new Listcell("0.0");
		celdaI.setValue("0.0");
		item.appendChild(celdaI);

		Listcell celdaE = new Listcell("0.0");
		celdaE.setValue("0.0");
		item.appendChild(celdaE);

		Listcell celda1 = new Listcell(a + "");
		celda1.setValue(a + "");
		item.appendChild(celda1);

		Listcell celda2 = new Listcell(b + "");
		celda2.setValue(b + "");
		item.appendChild(celda2);

		// agregando el estado
		if (estado != null) {
			if (estado.equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(MD5);
			item.appendChild(celda);
		}

		return item;
	}

	/**
	 * @param estado
	 * @param index
	 * @param MD5
	 * @param values
	 * @return Listitem ; crea un Listitem con el estado en la posici���n deseada.
	 */
	public Listitem crearListitemDinamicoT(String estado, int index,
			String MD5, String... values) {

		Listitem item = new Listitem();
		Listcell celda = null;
		int i = 1;
		// agregando los valores dinamicos
		for (String value : values) {
			if (i == index) {
				if (estado != null) {
					if (estado.equals("A")) {
						celda = new Listcell("", IConstantes.IMAGEN_OK);
						celda.setTooltiptext("Activo");
					} else {
						celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
						celda.setTooltiptext("Inactivo");
					}

					celda.setValue(MD5);
					item.appendChild(celda);
				}
			}
			celda = new Listcell(value);
			celda.setValue(value);
			item.appendChild(celda);

			i++;
		}

		return item;
	}

	/**
	 * @param MD5
	 * @param values
	 * @return Listitem; crea el Listitem con solo strings y el objeto es
	 *         agregado en el valor de cada listitem, el estado es diferente a
	 *         activo e inactivo. el valor del estado es un string
	 */
	public Listitem crearListitemDinamicoT(IBeanAbstracto obj, String... values) {

		Listitem item = new Listitem();
		Listcell celda = null;

		// agregando los valores dinamicos
		for (String value : values) {
			celda = new Listcell(value);
			celda.setValue(value);
			item.appendChild(celda);
		}

		item.setValue(obj);

		return item;
	}

	/**
	 * @param MD5
	 * @param values
	 * @return Listitem; crea el Listitem con solo strings y el objeto es
	 *         agregado en el valor de cada listitem, si el estado es diferente
	 *         a activo e inactivo se manda false si no true para colocar la
	 *         imagen del respectivo estado. el valor del estado es un string
	 */
	public Listitem crearListitemDinamicoT(IBeanAbstracto obj,
			boolean aplicaEstado, String... values) {

		Listitem item = new Listitem();
		Listcell celda = null;

		// agregando los valores dinamicos
		for (String value : values) {
			celda = new Listcell(value);
			celda.setValue(value);
			item.appendChild(celda);
		}
		if (aplicaEstado) {
			if (obj.getEstado() != null) {
				if (obj.getEstado().equals("A")) {
					celda = new Listcell("", IConstantes.IMAGEN_OK);
					celda.setTooltiptext("Activo");
				} else {
					celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
					celda.setTooltiptext("Inactivo");
				}

				celda.setValue(obj.getMD5());
				item.appendChild(celda);
			}

		}

		item.setValue(obj);

		return item;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearRowDesdeDto
	 * @param beanAbstracto
	 *            representa el objeto que contiene los datos generales a
	 *            agregar, tales como c����digo, nombre y estado
	 * @param verMas
	 *            indica si el row debe renderizar una columna con la opci����n
	 *            "ver mas", la cual debe ser implementada en el action a trav��s
	 *            de la interfaz IActionGrid
	 * @param editar
	 *            indica si el row debe renderizar una columna con la opci����n
	 *            "editar", la cual debe ser implementada en el action a trav��s
	 *            de la interfaz IActionGrid
	 * @param borrar
	 *            indica si el row debe renderizar una columna con la opci����n
	 *            "borrar", la cual debe ser implementada en el action a trav��s
	 *            de la interfaz IActionGrid.
	 * @param colEmpty
	 *            indica si el Row debe renderizar una columna vac��a al inicio
	 *            del mismo, para que posteriormente el Grid agregue un check
	 * @param actionGrid
	 *            representa el Action que tiene la implementaci����n para los
	 *            m��todos de las opciones (ver mas, editar, borrar). Sino es
	 *            necesaria la implementaci����n de ni uno de estos se puede pasar
	 *            como null
	 * @param adds
	 *            representa todas aquellas columnas adicionales que se le
	 *            quieran agregar al row
	 * @return
	 * @desc permite crear un objeto org.zkoss.zul.Row apartir de un
	 *       IBeanAbstracto y los demas par��metros descritos
	 */
	public Row crearRowDesdeDto(final IBeanAbstracto beanAbstracto,
	/* boolean verMas, boolean editar, */boolean borrar,
			final IActionListbox actionGrid, Boolean isEditar, String... adds) {

		Row row = new Row();
		if (beanAbstracto != null) {
			if (beanAbstracto.getCodigo() != null)
				row.appendChild(new Label(beanAbstracto.getCodigo()));
			if (beanAbstracto.getNombre() != null)
				row.appendChild(new Label(beanAbstracto.getNombre()));
		}
		// adicionando los agregados
		for (String add : adds) {
			row.appendChild(new Label(add));
		}

		// - Se configura la columna [ Estado ]
		if (beanAbstracto != null) {
			if (beanAbstracto.getEstado() != null) {
				Image imagen = new Image();

				if (beanAbstracto.getEstado().equals("A")) {
					imagen.setSrc(IConstantes.IMAGEN_OK);
					imagen.setTooltiptext("Activo");
				} else {
					imagen.setSrc(IConstantes.IMAGEN_CANCEL);
					imagen.setTooltiptext("Inactivo");
				}

				row.appendChild(imagen);
			}
		}

		// - Se configura la opci����n [ Seleccionar
		// ]
		if (isEditar != null) {
			if (isEditar) {
				Image imagen = new Image();
				imagen.setSrc(IConstantes.IMAGEN_EDITAR);
				imagen.setStyle("cursor: pointer");
				imagen.setTooltiptext("Editar");
				imagen.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							public void onEvent(Event arg0) throws Exception {

								actionGrid.onEditarMaestro(beanAbstracto);
							}
						});
				row.appendChild(imagen);

			} else

			// - Se configura la opci����n [ Editar ]
			/* if (isEditar) */{
				Image imagen = new Image();
				imagen.setSrc(IConstantes.IMAGEN_SELECCIONAR);
				imagen.setStyle("cursor: pointer");
				imagen.setTooltiptext("Ver m��s");
				imagen.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							public void onEvent(Event arg0) throws Exception {

								actionGrid.onVerMasMaestro(beanAbstracto);
							}
						});
				row.appendChild(imagen);
			}

			// - Se configura la opci����n [ Borrar ]
			if (borrar) {
				Image imagen = new Image();
				imagen.setSrc(isEditar ? IConstantes.IMAGEN_BORRAR
						: IConstantes.IMAGEN_BORRAR_DISABLE);
				imagen.setStyle("cursor: pointer");
				imagen.setTooltiptext("Eliminar");

				if (isEditar) {
					imagen.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {

								public void onEvent(Event arg0)
										throws Exception {

									actionGrid.onBorrarMaestro(beanAbstracto);
								}
							});
				}

				row.appendChild(imagen);
			}
		}

		return row;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearRowDesdeDto
	 * @param beanAbstracto
	 *            representa el objeto que contiene los datos generales a
	 *            agregar, tales como c����digo, nombre y estado
	 * @param verMas
	 *            indica si el row debe renderizar una columna con la opci����n
	 *            "ver mas", la cual debe ser implementada en el action a trav��s
	 *            de la interfaz IActionGridDetalle
	 * @param editar
	 *            indica si el row debe renderizar una columna con la opci����n
	 *            "editar", la cual debe ser implementada en el action a trav��s
	 *            de la interfaz IActionGridDetalle
	 * @param borrar
	 *            indica si el row debe renderizar una columna con la opci����n
	 *            "borrar", la cual debe ser implementada en el action a trav��s
	 *            de la interfaz IActionGridDetalle.
	 * @param colEmpty
	 *            indica si el Row debe renderizar una columna vac��a al inicio
	 *            del mismo, para que posteriormente el Grid agregue un check
	 * @param actionGrid
	 *            representa el Action que tiene la implementaci����n para los
	 *            m��todos de las opciones (ver mas, editar, borrar). Sino es
	 *            necesaria la implementaci����n de ni uno de estos se puede pasar
	 *            como null
	 * @param adds
	 *            representa todas aquellas columnas adicionales que se le
	 *            quieran agregar al row
	 * @return
	 * @desc permite crear un objeto org.zkoss.zul.Row apartir de un
	 *       IBeanAbstracto y los demas par��metros descritos
	 */
	public Row crearRowDesdeDtoDetalle(final IBeanAbstracto beanAbstracto,
	/* boolean verMas, boolean editar, */boolean borrar,
			final IActionGridDetalle actionGrid, Boolean isEditar,
			String... adds) {

		Row row = new Row();
		if (beanAbstracto != null) {
			if (beanAbstracto.getCodigo() != null)
				row.appendChild(new Label(beanAbstracto.getCodigo()));
			if (beanAbstracto.getNombre() != null)
				row.appendChild(new Label(beanAbstracto.getNombre()));
		}
		// adicionando los agregados
		for (String add : adds) {
			row.appendChild(new Label(add));
		}

		// - Se configura la columna [ Estado ]
		if (beanAbstracto != null) {
			if (beanAbstracto.getEstado() != null) {
				Image imagen = new Image();

				if (beanAbstracto.getEstado().equals("A")) {
					imagen.setSrc(IConstantes.IMAGEN_OK);
					imagen.setTooltiptext("Activo");
				} else {
					imagen.setSrc(IConstantes.IMAGEN_CANCEL);
					imagen.setTooltiptext("Inactivo");
				}

				row.appendChild(imagen);
			}
		}

		// - Se configura la opci����n [ Seleccionar
		// ]
		if (isEditar != null) {
			if (isEditar) {
				Image imagen = new Image();
				imagen.setSrc(IConstantes.IMAGEN_EDITAR);
				imagen.setStyle("cursor: pointer");
				imagen.setTooltiptext("Editar");
				imagen.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							public void onEvent(Event arg0) throws Exception {

								actionGrid.onEditarMaestro(beanAbstracto);
							}
						});
				row.appendChild(imagen);

			} else

			// - Se configura la opci����n [ Editar ]
			/* if (isEditar) */{
				Image imagen = new Image();
				imagen.setSrc(IConstantes.IMAGEN_SELECCIONAR);
				imagen.setStyle("cursor: pointer");
				imagen.setTooltiptext("Ver m��s");
				imagen.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							public void onEvent(Event arg0) throws Exception {

								actionGrid.onVerMasMaestro(beanAbstracto);
							}
						});
				row.appendChild(imagen);
			}

			// - Se configura la opci����n [ Borrar ]
			if (borrar) {
				Image imagen = new Image();
				imagen.setSrc(isEditar ? IConstantes.IMAGEN_BORRAR
						: IConstantes.IMAGEN_BORRAR_DISABLE);
				imagen.setStyle("cursor: pointer");
				imagen.setTooltiptext("Eliminar");

				if (isEditar) {
					imagen.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {

								public void onEvent(Event arg0)
										throws Exception {

									actionGrid.onBorrarMaestro(beanAbstracto);
								}
							});
				}

				row.appendChild(imagen);
			}
		}

		return row;
	}

	public Row crearRowDesdeDto(final IBeanAbstracto beanAbstracto,
	/* boolean verMas, boolean editar, */boolean borrar, boolean colEmpty,
			final IActionListbox actionGrid, Boolean isEditar, String... adds) {

		Row row = crearRowDesdeDto(beanAbstracto, /* verMas, editar, */borrar,
				actionGrid, isEditar, adds);
		if (colEmpty)
			row.getChildren().add(0, new Detail());

		return row;
	}

	public Row crearRowDesdeDtoDetalle(final IBeanAbstracto beanAbstracto,
	/* boolean verMas, boolean editar, */boolean borrar, boolean colEmpty,
			final IActionGridDetalle actionGrid, Boolean isEditar,
			String... adds) {

		Row row = crearRowDesdeDtoDetalle(beanAbstracto, borrar, actionGrid,
				isEditar, adds);
		if (colEmpty)
			row.getChildren().add(0, new Detail());

		return row;
	}

	/**
	 * @param beanAbstracto
	 * @param adds
	 * @return
	 * @desc Crea los listitem que iran a formar parte de los detalles
	 */
	public Listitem crearItemListaDesdeDto(final IBeanAbstracto beanAbstracto,
			String... adds) {

		Listitem itemLista = new Listitem();

		itemLista.setValue(beanAbstracto);

		if (beanAbstracto != null) {
			if (beanAbstracto.getCodigo() != null)
				itemLista.appendChild(new Listcell(beanAbstracto.getCodigo()));
			if (beanAbstracto.getNombre() != null)
				itemLista.appendChild(new Listcell(beanAbstracto.getNombre()));
		}
		// adicionando los agregados
		for (String add : adds) {
			itemLista.appendChild(new Listcell(add));
		}

		// - Se configura la columna [ Estado ]
		if (beanAbstracto != null) {
			if (beanAbstracto.getEstado() != null) {
				if (beanAbstracto.getEstado().equals("A")) {
					Listcell cell = new Listcell("", IConstantes.IMAGEN_OK);
					cell.setTooltiptext("Activo");
					itemLista.appendChild(cell);
				} else {
					Listcell cell = new Listcell("", IConstantes.IMAGEN_CANCEL);
					cell.setTooltiptext("Inactivo");
					itemLista.appendChild(cell);
				}
			}
		}

		return itemLista;
	}

	/**
	 * Crea {@link Listitem} apartir de un {@link IBeanAbstracto}
	 * 
	 * @param beanAbstracto
	 *            {@link IBeanAbstracto} a renderizar en un
	 * @param unirCodigoNombre
	 *            true para mostrar el {@link IBeanAbstracto#getCodigo()} y
	 *            {@link IBeanAbstracto#getNombre()} [CODIGO] NOMBRE, false para
	 *            mostrar el los campos en {@link Listcell} separadas
	 * @param adds
	 *            otros campos a mostrar
	 * @return {@link Listitem} con las informacion y valores de
	 *         {@link IBeanAbstracto}
	 */
	public Listitem crearItemListaDesdeDto(final IBeanAbstracto beanAbstracto,
			boolean unirCodigoNombre, String... adds) {

		Listitem itemLista = new Listitem();

		itemLista.setValue(beanAbstracto);

		if (beanAbstracto != null) {

			if (!unirCodigoNombre) {
				if (beanAbstracto.getCodigo() != null)
					itemLista.appendChild(new Listcell(beanAbstracto
							.getCodigo()));

				if (beanAbstracto.getNombre() != null)
					itemLista.appendChild(new Listcell(beanAbstracto
							.getNombre()));
			} else {

				StringBuffer buffer = new StringBuffer();

				if (beanAbstracto.getCodigo() != null)
					buffer.append("[").append(beanAbstracto.getCodigo())
							.append("] ");
				if (beanAbstracto.getNombre() != null)
					buffer.append(beanAbstracto.getNombre());

				itemLista.appendChild(new Listcell(buffer.toString()));
			}

		}
		// adicionando los agregados
		for (String add : adds) {
			itemLista.appendChild(new Listcell(add));
		}

		// - Se configura la columna [ Estado ]
		if (beanAbstracto != null) {
			if (beanAbstracto.getEstado() != null) {
				if (beanAbstracto.getEstado().equals("A")) {
					Listcell cell = new Listcell("", IConstantes.IMAGEN_OK);
					cell.setTooltiptext("Activo");
					itemLista.appendChild(cell);
				} else {
					Listcell cell = new Listcell("", IConstantes.IMAGEN_CANCEL);
					cell.setTooltiptext("Inactivo");
					itemLista.appendChild(cell);
				}
			}
		}

		return itemLista;
	}

	public Row crearRowGridDesdeDto(final IBeanAbstracto beanAbstracto,
			String... adds) {

		Row rowGrid = new Row();

		rowGrid.setValue(beanAbstracto);

		if (beanAbstracto != null) {
			if (beanAbstracto.getCodigo() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getCodigo()));
			if (beanAbstracto.getNombre() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getNombre()));
		}
		// adicionando los agregados
		for (String add : adds) {
			rowGrid.appendChild(new Label(add));
		}

		// - Se configura la columna [ Estado ]
		if (beanAbstracto != null) {
			if (beanAbstracto.getEstado() != null) {
				if (beanAbstracto.getEstado().equals("A")) {
					Image image = new Image(IConstantes.IMAGEN_OK);
					image.setTooltiptext("Activo");
					rowGrid.appendChild(image);
				} else {
					Image image = new Image(IConstantes.IMAGEN_CANCEL);
					image.setTooltiptext("Inactivo");
					rowGrid.appendChild(image);
				}
			}
		}

		return rowGrid;
	}

	public Row crearRowGridDesdeDto(Row rowGrid,
			final IBeanAbstracto beanAbstracto, String... adds) {

		rowGrid.setValue(beanAbstracto);

		if (beanAbstracto != null) {
			if (beanAbstracto.getCodigo() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getCodigo()));
			if (beanAbstracto.getNombre() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getNombre()));
		}
		// adicionando los agregados
		for (String add : adds) {
			rowGrid.appendChild(new Label(add));
		}

		// - Se configura la columna [ Estado ]
		if (beanAbstracto != null) {
			if (beanAbstracto.getEstado() != null) {
				if (beanAbstracto.getEstado().equals("A")) {
					Image image = new Image(IConstantes.IMAGEN_OK);
					image.setTooltiptext("Activo");
					rowGrid.appendChild(image);
				} else {
					Image image = new Image(IConstantes.IMAGEN_CANCEL);
					image.setTooltiptext("Inactivo");
					rowGrid.appendChild(image);
				}
			}
		}

		return rowGrid;
	}

	public Row crearRowGridDesdeDto(Row rowGrid, boolean mostrarEstado,
			final IBeanAbstracto beanAbstracto, String... adds) {

		rowGrid.setValue(beanAbstracto);

		if (beanAbstracto != null) {
			if (beanAbstracto.getCodigo() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getCodigo()));
			if (beanAbstracto.getNombre() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getNombre()));
		}
		// adicionando los agregados
		for (String add : adds) {
			rowGrid.appendChild(new Label(add));
		}

		// - Se configura la columna [ Estado ]
		if (mostrarEstado) {
			if (beanAbstracto.getEstado() != null) {
				if (beanAbstracto.getEstado().equals("A")) {
					Image image = new Image(IConstantes.IMAGEN_OK);
					image.setTooltiptext("Activo");
					rowGrid.appendChild(image);
				} else {
					Image image = new Image(IConstantes.IMAGEN_CANCEL);
					image.setTooltiptext("Inactivo");
					rowGrid.appendChild(image);
				}
			}
		}
		return rowGrid;
	}

	public Row crearRowGridDesdeDto(final IBeanAbstracto beanAbstracto,
			boolean aplicaEstadoActivoInactivo, String... adds) {

		Row rowGrid = new Row();

		rowGrid.setValue(beanAbstracto);

		if (beanAbstracto != null) {
			if (beanAbstracto.getCodigo() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getCodigo()));
			if (beanAbstracto.getNombre() != null)
				rowGrid.appendChild(new Label(beanAbstracto.getNombre()));
		}
		// adicionando los agregados
		for (String add : adds) {
			Log.info("VALOR--> " + add);
			if (add.contains("#align:right")) {
				Cell cell = new Cell();
				cell.setAlign("right");
				cell.setHflex("min");
				cell.appendChild(new Label(add.substring(0, add.indexOf("#"))));
				rowGrid.appendChild(cell);
			} else {
				rowGrid.appendChild(new Label(add));
			}
		}

		if (aplicaEstadoActivoInactivo) {
			// - Se configura la columna [ Estado ]
			if (beanAbstracto != null) {
				if (beanAbstracto.getEstado() != null) {
					if (beanAbstracto.getEstado().equals("A")) {
						Image image = new Image(IConstantes.IMAGEN_OK);
						image.setTooltiptext("Activo");
						rowGrid.appendChild(image);
					} else {
						Image image = new Image(IConstantes.IMAGEN_CANCEL);
						image.setTooltiptext("Inactivo");
						rowGrid.appendChild(image);
					}
				}
			}
		}

		return rowGrid;
	}

	public Row crearRowGridDesdeDtoDinamico(final IBeanAbstracto beanAbstracto,
			boolean aplicaEstadoActivoInactivo, String... adds) {

		Row rowGrid = new Row();

		rowGrid.setValue(beanAbstracto);

		// adicionando los agregados
		for (String add : adds) {
			Log.info("VALOR--> " + add);
			if (add.contains("#align:right")) {
				Cell cell = new Cell();
				cell.setAlign("right");
				cell.setHflex("min");
				cell.appendChild(new Label(add.substring(0, add.indexOf("#"))));
				rowGrid.appendChild(cell);
			} else {
				rowGrid.appendChild(new Label(add));
			}
		}

		if (aplicaEstadoActivoInactivo) {
			// - Se configura la columna [ Estado ]
			if (beanAbstracto != null) {
				if (beanAbstracto.getEstado() != null) {
					if (beanAbstracto.getEstado().equals("A")) {
						Image image = new Image(IConstantes.IMAGEN_OK);
						image.setTooltiptext("Activo");
						rowGrid.appendChild(image);
					} else {
						Image image = new Image(IConstantes.IMAGEN_CANCEL);
						image.setTooltiptext("Inactivo");
						rowGrid.appendChild(image);
					}
				}
			}
		}

		return rowGrid;
	}
	public void agregarMenuPopupColumnas(Listbox lista, Menupopup menuPopup) {
		List<Component> listheaders = ((Listhead) lista.getChildren().get(0))
				.getChildren();
		Toolbarbutton button = null;

		((Listhead) lista.getChildren().get(0)).setAttribute(
				"columnasVisibles", listheaders.size());
		final Listhead encabezado = ((Listhead) lista.getChildren().get(0));

		for (final Component listheader : listheaders) {
			Object isVisible = listheader.getAttribute("isVisible");
			if (isVisible != null) {
				if (Boolean.parseBoolean(isVisible.toString()) != false) {
					Menuitem menuitem = new Menuitem(
							((Listheader) listheader).getLabel());

					menuitem.setCheckmark(true);
					menuitem.setAutocheck(true);
					menuitem.setChecked(true);
					final Menuitem mi = menuitem;

					menuitem.addEventListener(Events.ON_CHECK,
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									int i = (Integer) encabezado
											.getAttribute("columnasVisibles");
									if (mi.isChecked()) {
										listheader.setVisible(true);
										i = i + 1;
										encabezado.setAttribute(
												"columnasVisibles", i);
									} else {
										if (i > 1) {
											listheader.setVisible(false);
											i = i - 1;
											encabezado.setAttribute(
													"columnasVisibles", i);
										} else
											mi.setChecked(true);
									}
								}

							});

					menuPopup.appendChild(menuitem);
				}
			} else {
				Menuitem menuitem = new Menuitem(
						((Listheader) listheader).getLabel());

				menuitem.setCheckmark(true);
				menuitem.setAutocheck(true);
				menuitem.setChecked(true);
				final Menuitem mi = menuitem;

				menuitem.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								int i = (Integer) encabezado
										.getAttribute("columnasVisibles");
								if (mi.isChecked()) {
									listheader.setVisible(true);
									i = i + 1;
									encabezado.setAttribute("columnasVisibles",
											i);
								} else {
									if (i > 1) {
										listheader.setVisible(false);
										i = i - 1;
										encabezado.setAttribute(
												"columnasVisibles", i);
									} else
										mi.setChecked(true);
								}
							}

						});

				menuPopup.appendChild(menuitem);
			}

		}

		for (Component listheader : listheaders) {
			Object isVisible = listheader.getAttribute("isVisible");
			if (isVisible != null) {
				if (Boolean.parseBoolean(isVisible.toString()) != false) {
					button = new Toolbarbutton(
							((Listheader) listheader).getLabel(),
							IConstantes.IMAGEN_OPEN);
					((Listheader) listheader).setLabel(null);
					button.setDir("reverse");
					button.setPopup(menuPopup);
					listheader.appendChild(button);
				}
			} else {
				button = new Toolbarbutton(
						((Listheader) listheader).getLabel(),
						IConstantes.IMAGEN_OPEN);
				((Listheader) listheader).setLabel(null);
				button.setDir("reverse");
				button.setPopup(menuPopup);
				listheader.appendChild(button);
			}
		}

	}
	public void agregarMenuPopupColumnasWithAuxHeader(Listbox lista, Menupopup menuPopup) {
		List<Component> listheaders = ((Listhead) lista.getChildren().get(1))
				.getChildren();
		Toolbarbutton button = null;

		((Listhead) lista.getChildren().get(1)).setAttribute(
				"columnasVisibles", listheaders.size());
		final Listhead encabezado = ((Listhead) lista.getChildren().get(1));

		for (final Component listheader : listheaders) {
			Object isVisible = listheader.getAttribute("isVisible");
			if (isVisible != null) {
				if (Boolean.parseBoolean(isVisible.toString()) != false) {
					Menuitem menuitem = new Menuitem(
							((Listheader) listheader).getLabel());

					menuitem.setCheckmark(true);
					menuitem.setAutocheck(true);
					menuitem.setChecked(true);
					final Menuitem mi = menuitem;

					menuitem.addEventListener(Events.ON_CHECK,
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									int i = (Integer) encabezado
											.getAttribute("columnasVisibles");
									if (mi.isChecked()) {
										listheader.setVisible(true);
										i = i + 1;
										encabezado.setAttribute(
												"columnasVisibles", i);
									} else {
										if (i > 1) {
											listheader.setVisible(false);
											i = i - 1;
											encabezado.setAttribute(
													"columnasVisibles", i);
										} else
											mi.setChecked(true);
									}
								}

							});

					menuPopup.appendChild(menuitem);
				}
			} else {
				Menuitem menuitem = new Menuitem(
						((Listheader) listheader).getLabel());

				menuitem.setCheckmark(true);
				menuitem.setAutocheck(true);
				menuitem.setChecked(true);
				final Menuitem mi = menuitem;

				menuitem.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								int i = (Integer) encabezado
										.getAttribute("columnasVisibles");
								if (mi.isChecked()) {
									listheader.setVisible(true);
									i = i + 1;
									encabezado.setAttribute("columnasVisibles",
											i);
								} else {
									if (i > 1) {
										listheader.setVisible(false);
										i = i - 1;
										encabezado.setAttribute(
												"columnasVisibles", i);
									} else
										mi.setChecked(true);
								}
							}

						});

				menuPopup.appendChild(menuitem);
			}

		}

		for (Component listheader : listheaders) {
			Object isVisible = listheader.getAttribute("isVisible");
			if (isVisible != null) {
				if (Boolean.parseBoolean(isVisible.toString()) != false) {
					button = new Toolbarbutton(
							((Listheader) listheader).getLabel(),
							IConstantes.IMAGEN_OPEN);
					((Listheader) listheader).setLabel(null);
					button.setDir("reverse");
					button.setPopup(menuPopup);
					listheader.appendChild(button);
				}
			} else {
				button = new Toolbarbutton(
						((Listheader) listheader).getLabel(),
						IConstantes.IMAGEN_OPEN);
				((Listheader) listheader).setLabel(null);
				button.setDir("reverse");
				button.setPopup(menuPopup);
				listheader.appendChild(button);
			}
		}

	}

	public void agregarMenuPopupColumnas(GridDetail gridDetail,
			Menupopup menuPopup) {
		List<Component> listColumn = ((Columns) gridDetail.getChildren().get(0))
				.getChildren();
		Toolbarbutton button = null;

		((Columns) gridDetail.getChildren().get(0)).setAttribute(
				"columnasVisibles", listColumn.size());
		final Columns encabezado = ((Columns) gridDetail.getChildren().get(0));

		for (final Component column : listColumn) {
			Object isVisible = column.getAttribute("isVisible");
			if (isVisible != null) {
				System.out.println("ATRIBUTO != null: " + isVisible.toString());
				if (Boolean.parseBoolean(isVisible.toString()) != false) {
					Menuitem menuitem = new Menuitem(
							((Column) column).getLabel());

					menuitem.setCheckmark(true);
					menuitem.setAutocheck(true);
					menuitem.setChecked(true);
					final Menuitem mi = menuitem;

					menuitem.addEventListener(Events.ON_CHECK,
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									int i = (Integer) encabezado
											.getAttribute("columnasVisibles");
									if (mi.isChecked()) {
										column.setVisible(true);
										i = i + 1;
										encabezado.setAttribute(
												"columnasVisibles", i);
									} else {
										if (i > 1) {
											column.setVisible(false);
											i = i - 1;
											encabezado.setAttribute(
													"columnasVisibles", i);
										} else
											mi.setChecked(true);
									}
								}

							});

					menuPopup.appendChild(menuitem);
				}
			} else {
				Menuitem menuitem = new Menuitem(((Column) column).getLabel());

				menuitem.setCheckmark(true);
				menuitem.setAutocheck(true);
				menuitem.setChecked(true);
				final Menuitem mi = menuitem;

				menuitem.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								int i = (Integer) encabezado
										.getAttribute("columnasVisibles");
								if (mi.isChecked()) {
									column.setVisible(true);
									i = i + 1;
									encabezado.setAttribute("columnasVisibles",
											i);
								} else {
									if (i > 1) {
										column.setVisible(false);
										i = i - 1;
										encabezado.setAttribute(
												"columnasVisibles", i);
									} else
										mi.setChecked(true);
								}
							}

						});

				menuPopup.appendChild(menuitem);
			}

		}

		for (Component column : listColumn) {
			Object isVisible = column.getAttribute("isVisible");
			if (isVisible != null) {
				if (Boolean.parseBoolean(isVisible.toString()) != false) {
					button = new Toolbarbutton(((Column) column).getLabel(),
							IConstantes.IMAGEN_OPEN);
					((Column) column).setLabel(null);
					button.setDir("reverse");
					button.setPopup(menuPopup);
					column.appendChild(button);
				}
			} else {
				button = new Toolbarbutton(((Column) column).getLabel(),
						IConstantes.IMAGEN_OPEN);
				((Column) column).setLabel(null);
				button.setDir("reverse");
				button.setPopup(menuPopup);
				column.appendChild(button);
			}
		}

	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDesdeDto
	 * @param obj
	 * @param item
	 * @return
	 * @desc permite modificar un Listitem basico a partir de una referencia de
	 *       IBeanAbstracto, este podra contener hasta 3 celdas (Listcell):
	 *       codigo (en mayusculas), nombre, estado (opcional, si es null lo
	 *       omite. Sino tiene dos valores dos posibles valores �������A��������: activo,
	 *       �������I��������: inactivo).
	 */
	public Listitem crearListitemDesdeDto(Listitem item, IBeanAbstracto obj) {

		Listcell celda = new Listcell(obj.getCodigo());
		celda.setValue(obj.getCodigo());
		item.appendChild(celda);

		if (obj.getNombre() != null)
			celda = new Listcell(obj.getNombre());
		else
			celda = new Listcell();
		celda.setValue(obj.getPrimaryKey());
		item.appendChild(celda);

		if (obj.getEstado() != null) {
			if (obj.getEstado().equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(obj.getMD5());
			item.appendChild(celda);
		}

		item.setValue(obj);

		return item;
	}

	/**
	 * @type Metodo de la clase AssemblerStandard.java
	 * @name crearListitemDinamico
	 * @param item
	 * @param objeto
	 * @param estado
	 * @param MD5
	 * @param values
	 * @return
	 * @desc permite modificar un Listitem a partir del item, objeto, estado,
	 *       MD5 y cualquier cantidad de par��metros variables que se
	 *       especifiquen en la invocaci����n del m��todo, estos par��metros
	 *       variables se deben separar por comas(,) y ser��n renderizados como
	 *       celdas (Listcell) antes del estado. Nota: el MD5 se asocia a la
	 *       celda del estado en el atributo value.
	 */
	public Listitem crearListitemDinamicoItem(Listitem item,
			IBeanAbstracto objeto, String estado, String MD5, String... values) {

		Listcell celda = null;
		item.setValue(objeto);

		// agregando los valores dinamicos
		for (String value : values) {
			celda = new Listcell(value);
			celda.setValue(value);
			item.appendChild(celda);
		}

		// agregando el estado
		if (estado != null) {
			if (estado.equals("A")) {
				celda = new Listcell("", IConstantes.IMAGEN_OK);
				celda.setTooltiptext("Activo");
			} else {
				celda = new Listcell("", IConstantes.IMAGEN_CANCEL);
				celda.setTooltiptext("Inactivo");
			}

			celda.setValue(MD5);
			item.appendChild(celda);
		}

		return item;
	}

}