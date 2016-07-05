package com.siis.framework.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.casewaresa.framework.action.impl.IInicializarComponentes;

@SuppressWarnings("unchecked")
public class FiltroDetalleVisualAction extends ActionStandard implements
	AfterCompose, IInicializarComponentes {

    private static final long serialVersionUID = -1472456942192422845L;
    /* Componentes de la GUI */
    private Listbox listaDetalles;
    private Listbox idVFDZLbxListadoCriterios;
    private Listbox idVFDZLbxListadoFunciones;
    private Textbox idVFDZTbxValor;
    private Listbox idVFDZLbxListadoFitros;
    private Combobox idVFDZLbxListadoValores;
    private Map<String, Object> parametros;

    @Override
    public void afterCompose() {
	log.info("Ejecutando metodo [ afterCompose ]");

	try {

	    cargarComponentesVista();

	    parametros = (Map<String, Object>) Executions.getCurrent().getArg();

	    listaDetalles = (Listbox) parametros.get("listaDetalles");
	    cargarCriterios();

	    idVFDZLbxListadoCriterios.setSelectedIndex(0);
	    idVFDZLbxListadoFunciones.setSelectedIndex(0);

	    List<Listitem> list = null;
	    if (!listaDetalles.getItems().isEmpty()) {
		if (listaDetalles.getItems().get(0) instanceof Listgroup) {
		    list = ((Listgroup) listaDetalles.getItems().get(0))
			    .getItems();
		} else
		    list = listaDetalles.getItems();

		addEventos(list);
		cargarFiltrosAnteriores((List<Listitem>) parametros
			.get("filtros"));
	    }

	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
    }

    /**
     * @param List
     *            <Listitem>
     * @desc En este metodo se cargan los filtros que anteriormente se habian
     *       configurado.
     */
    private void cargarFiltrosAnteriores(List<Listitem> filtros) {
	log.info("Ejecutando metodo [ cargarFiltrosAnteriores ]");

	if (filtros != null) {
	    while (filtros.size() > 0) {
		idVFDZLbxListadoFitros.appendChild(filtros.get(0));
	    }
	}
    }

    /**
     * @param List
     *            <Listitem>
     * @param int
     * @desc Este metodo carga los posibles valores a comparar en caso de que la
     *       funcion de comparacion sea igual, esto con el fin de evitar que el
     *       usuario necesite adivinar cual es la fila que esta buscando
     */
    private void cargarValores(List<Listitem> items,
	    Listitem criterioSeleccionado) {
	log.info("Ejecutando metodo [ cargarValores ]");
	log.debug("items ==>" + items);
	log.debug("columna ==>" + criterioSeleccionado);

	int columna = ((Listheader) criterioSeleccionado.getValue())
		.getListbox().getListhead().getChildren()
		.indexOf(criterioSeleccionado.getValue());
	log.debug("columna ==>" + columna);
	Listcell cell = (Listcell) items.get(0).getChildren().get(columna);

	List<String> valores = new LinkedList<String>();
	List<String> tooltip = new LinkedList<String>();

	if (cell.getImage() != null && !cell.getImage().equals("")) {
	    log.trace("La columna es Imagenes!");
	    for (int i = 0; i < items.size(); i++) {
		if (items.get(i).getAttribute("detail") == null)
		    cell = (Listcell) items.get(i).getChildren().get(columna);
		if (!valores.contains(cell.getImage())) {
		    cell = (Listcell) items.get(i).getChildren().get(columna);
		    valores.add(cell.getImage());
		    tooltip.add(cell.getTooltiptext());
		}
	    }

	    idVFDZLbxListadoValores.setWidth("70px");
	    idVFDZLbxListadoValores.setValue("");
	    idVFDZLbxListadoValores.getItems().clear();
	    idVFDZLbxListadoValores.setAttribute("imagen", new Object());

	    for (int i = 0; i < valores.size(); i++) {
		Comboitem item = new Comboitem(tooltip.get(i), valores.get(i));

		idVFDZLbxListadoValores.appendChild(item);
	    }

	    idVFDZLbxListadoValores.setSelectedIndex(0);
	    extraerValor();
	} else {
	    log.trace("La columna no es Imagenes!");
	    for (int i = 0; i < items.size(); i++) {
		if (items.get(i).getAttribute("detail") == null) {
		    cell = (Listcell) items.get(i).getChildren().get(columna);
		    if (cell.getLabel().trim().isEmpty()) {
			if (cell.getValue() != null) {
			    if (!valores.contains(cell.getValue().toString()))
				valores.add(cell.getValue().toString());
			}
		    } else {
			if (!valores.contains(cell.getLabel()))
			    valores.add(cell.getLabel());
		    }
		}
	    }

	    idVFDZLbxListadoValores.setWidth("300px");
	    idVFDZLbxListadoValores.getItems().clear();
	    idVFDZLbxListadoValores.setAttribute("imagen", null);

	    for (String valor : valores) {
		idVFDZLbxListadoValores.appendChild(new Comboitem(valor));
	    }

	    if (idVFDZLbxListadoValores.getItems().size() > 0) {
		idVFDZLbxListadoValores.setSelectedIndex(0);

		extraerValor();
	    }
	}
    }

    /**
     * @desc Este metodo se activa al presionar el boton filtrar, y su funcion
     *       es mostrar las filas que coinciden con los filtros y ocultar las
     *       que no. Para esto crea un listado donde dice que filas cumplen las
     *       caracteristicas y se apoya en el metodo mostrarOcultarFilas
     */
    public void onFiltrar() {
	log.info("Ejecutando metodo [ onFiltrar ]");
	List<Listitem> items = null;
	if (!listaDetalles.getItems().isEmpty()) {

	    if (listaDetalles.getItems().get(0) instanceof Listgroup) {
		items = ((Listgroup) listaDetalles.getItems().get(0))
			.getItems();
	    } else
		items = listaDetalles.getItems();

	    boolean[] aprovaciones = new boolean[items.size()];

	    for (int i = 0; i < items.size(); i++) {
		aprovaciones[i] = true;
	    }

	    if (idVFDZLbxListadoFitros.getItems().size() == 0) {
		listarTodo(items);
	    } else {
		List<Listitem> filtros = idVFDZLbxListadoFitros.getItems();

		for (Listitem item : filtros) {

		    int criterio = Integer.parseInt(item.getValue().toString());

		    int funcion = Integer.parseInt(((Listcell) item
			    .getChildren().get(1)).getValue().toString());

		    String valor = ((Listcell) item.getChildren().get(2))
			    .getLabel();

		    if (item.getAttribute("imagen") != null) {
			valor = ((Listcell) item.getChildren().get(2))
				.getImage();
		    }

		    switch (funcion) {
		    case 1:
			if (item.getAttribute("imagen") != null) {
			    for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getAttribute("detail") == null)
				    aprovaciones[i] = aprovaciones[i]
					    && listarIgualImagen(items.get(i),
						    criterio, valor);
			    }
			} else {
			    for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getAttribute("detail") == null)
				    aprovaciones[i] = aprovaciones[i]
					    && listarIgual(items.get(i),
						    criterio, valor);
			    }
			}
			break;
		    case 2:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarMayor(items.get(i), criterio,
						valor);
			}
			break;
		    case 3:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarMenor(items.get(i), criterio,
						valor);
			}
			break;
		    case 4:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarMayorIgual(items.get(i),
						criterio, valor);
			}
			break;
		    case 5:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarMenorIgual(items.get(i),
						criterio, valor);
			}
			break;
		    case 6:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarComience(items.get(i),
						criterio, valor);
			}
			break;
		    case 7:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarTermine(items.get(i),
						criterio, valor);
			}
			break;
		    case 8:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarContiene(items.get(i),
						criterio, valor);
			}
			break;
		    case 9:
			if (item.getAttribute("imagen") != null) {

			    for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getAttribute("detail") == null)
				    aprovaciones[i] = aprovaciones[i]
					    && listarDiferenteImagen(
						    items.get(i), criterio,
						    valor);
			    }
			} else {
			    for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getAttribute("detail") == null)
				    aprovaciones[i] = aprovaciones[i]
					    && listarDiferente(items.get(i),
						    criterio, valor);
			    }
			}
			break;
		    case 10:
			for (int i = 0; i < items.size(); i++) {
			    if (items.get(i).getAttribute("detail") == null)
				aprovaciones[i] = aprovaciones[i]
					&& listarNoContiene(items.get(i),
						criterio, valor);
			}
			break;
		    }

		}
	    }
	    mostrarOcultarFilas(items, aprovaciones);

	    parametros.put("filtros", idVFDZLbxListadoFitros.getItems());
	}
    }

    /**
     * @param List
     *            <Listitem>
     * @param boolean[]
     * @desc Este metodo es el que oculata y mustra las filas de acuerdo a las
     *       aprovaciones
     */
    private void mostrarOcultarFilas(List<Listitem> items,
	    boolean[] aprovaciones) {
	log.info("Ejecutando metodo [ mostrarOcultarFilas ]");
	log.debug("items ==>" + items);
	log.debug("aprovaciones ==>" + aprovaciones);

	for (int i = 0; i < items.size(); i++) {

	    if (items.get(i).getFirstChild() != null
		    && items.get(i).getFirstChild().getChildren() != null
		    && !items.get(i).getFirstChild().getChildren().isEmpty()) {
		if (items.get(i).getFirstChild().getChildren().get(0) instanceof Toolbarbutton) {
		    Toolbarbutton detail = (Toolbarbutton) items.get(i)
			    .getFirstChild().getChildren().get(0);
		    detail.setAttribute("isOpen", false);
		    detail.setImage("/imagenes/detail-closed.png");
		}
	    }

	    if (!(items.get(i).getAttribute("detail") != null && !items.get(i)
		    .getAttribute("detail").toString().isEmpty()))
		if (aprovaciones[i]) {
		    items.get(i).setVisible(true);
		} else {
		    items.get(i).setVisible(false);
		}
	    else
		items.get(i).setVisible(false);
	}
    }

    /**
     * @desc Este metodo agrega filtros a la lista, se activa cuando presionan
     *       el boton agregar
     */
    public void agregarFiltro() {
	log.info("Ejecutando metodo [ agregarFiltro ]");

	if (idVFDZLbxListadoCriterios.getSelectedIndex() != 0) {
	    if (idVFDZLbxListadoFunciones.getSelectedIndex() != 0) {
		if (!idVFDZTbxValor.getValue().trim().equals("")) {
		    Listitem item = new Listitem();

		    if (idVFDZLbxListadoValores.getAttribute("imagen") != null) {
			item.setAttribute("imagen", new Object());
		    }

		    Listcell criterio = new Listcell(idVFDZLbxListadoCriterios
			    .getSelectedItem().getLabel());
		    criterio.setValue(((Listheader) idVFDZLbxListadoCriterios
			    .getSelectedItem().getValue())
			    .getListbox()
			    .getListhead()
			    .getChildren()
			    .indexOf(
				    (Listheader) idVFDZLbxListadoCriterios
					    .getSelectedItem().getValue()));

		    item.appendChild(criterio);

		    Listcell funcion = new Listcell(idVFDZLbxListadoFunciones
			    .getSelectedItem().getLabel());
		    funcion.setValue(idVFDZLbxListadoFunciones
			    .getSelectedIndex());
		    item.appendChild(funcion);

		    if (idVFDZLbxListadoValores.getAttribute("imagen") != null) {
			Listcell valor = new Listcell("",
				idVFDZTbxValor.getValue());
			item.appendChild(valor);

			idVFDZLbxListadoValores.setAttribute("imagen", null);
		    } else {
			Listcell valor = new Listcell(idVFDZTbxValor.getValue());
			item.appendChild(valor);
		    }
		    item.setValue(((Listheader) idVFDZLbxListadoCriterios
			    .getSelectedItem().getValue())
			    .getListbox()
			    .getListhead()
			    .getChildren()
			    .indexOf(
				    (Listheader) idVFDZLbxListadoCriterios
					    .getSelectedItem().getValue()));
		    idVFDZLbxListadoFitros.appendChild(item);

		    parametros
			    .put("filtros", idVFDZLbxListadoFitros.getItems());

		    idVFDZLbxListadoCriterios.setSelectedIndex(0);

		    idVFDZLbxListadoFunciones.setSelectedIndex(0);
		    idVFDZLbxListadoFunciones.setDisabled(true);

		    idVFDZLbxListadoValores.setVisible(false);
		    idVFDZTbxValor.setVisible(true);
		    idVFDZTbxValor.setValue("");
		    idVFDZTbxValor.setDisabled(true);
		} else {
		    idVFDZTbxValor.setFocus(true);
		}
	    } else {
		idVFDZLbxListadoFunciones.setFocus(true);
	    }
	} else {
	    idVFDZLbxListadoCriterios.setFocus(true);
	}
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion contiene
     */
    private boolean listarContiene(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarContiene ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);
	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().contains(valor))
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().contains(valor))
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion no contiene
     */
    private boolean listarNoContiene(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarNoContiene ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);
	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (!(cell.getLabel().contains(valor)))
	    return true;

	if (cell.getValue() != null)
	    if (!(cell.getValue().toString().contains(valor)))
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Comience por
     */
    private boolean listarComience(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarComience ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().startsWith(valor))
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().startsWith(valor))
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Termine en
     */
    private boolean listarTermine(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarTermine ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().endsWith(valor))
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().endsWith(valor))
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Menor que
     */
    private boolean listarMenor(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMenor ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().compareTo(valor) == -1)
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().compareTo(valor) == -1)
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Mayor que
     */
    private boolean listarMayor(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMayor ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().compareTo(valor) == 1)
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().compareTo(valor) == 1)
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Menor o Igual que
     */
    private boolean listarMenorIgual(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMenorIgual ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().compareTo(valor) < 1)
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().compareTo(valor) < 1)
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Mayor o Igual que
     */
    private boolean listarMayorIgual(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMayorIgual ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().compareTo(valor) > -1)
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().compareTo(valor) > -1)
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Igual
     */
    private boolean listarIgual(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarIgual ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getLabel().compareTo(valor) == 0)
	    return true;

	if (cell.getValue() != null)
	    if (cell.getValue().toString().compareTo(valor) == 0)
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Igual
     */
    private boolean listarDiferente(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarDiferente ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (!(cell.getLabel().compareTo(valor) == 0))
	    return true;

	if (cell.getValue() != null)
	    if (!(cell.getValue().toString().compareTo(valor) == 0))
		return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Igual, aplica cuando son imagenes
     */
    private boolean listarIgualImagen(Listitem item, int columna, String valor) {
	log.info("Ejecutando metodo [ listarIgual ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (cell.getImage().compareTo(valor) == 0)
	    return true;

	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Igual, aplica cuando son imagenes
     */
    private boolean listarDiferenteImagen(Listitem item, int columna,
	    String valor) {
	log.info("Ejecutando metodo [ listarIgual ]");
	log.debug("item ==>" + item);
	log.debug("columna ==>" + columna);
	log.debug("valor ==>" + valor);

	Listcell cell = (Listcell) item.getChildren().get(columna);

	if (!(cell.getImage().compareTo(valor) == 0))
	    return true;

	return false;
    }

    /**
     * @param items
     * @desc Muestra todas las filas
     */
    private void listarTodo(List<Listitem> items) {
	log.info("Ejecutando metodo [ listarTodo ]");
	log.debug("items ==>" + items);

	for (Listitem item : items) {

	    if (item.getFirstChild() != null
		    && item.getFirstChild().getChildren() != null
		    && !item.getFirstChild().getChildren().isEmpty()) {
		if (item.getFirstChild().getChildren().get(0) instanceof Toolbarbutton) {
		    Toolbarbutton detail = (Toolbarbutton) item.getFirstChild()
			    .getChildren().get(0);
		    detail.setAttribute("isOpen", false);
		    detail.setImage("/imagenes/detail-closed.png");
		}
	    }

	    if (!(item.getAttribute("detail") != null && !item
		    .getAttribute("detail").toString().isEmpty()))
		item.setVisible(true);
	    else
		item.setVisible(false);
	}
    }

    /**
     * @desc Carga los criterios de comparacion
     */
    private void cargarCriterios() {
	log.info("Ejecutando metodo [ cargarCriterios ]");

	List<Component> headers = listaDetalles.getListhead().getChildren();

	idVFDZLbxListadoCriterios.appendChild(new Listitem("Seleccione..."));

	for (Component header : headers) {

	    Listheader listheader = (Listheader) header;
	    if (listheader.getAttribute("isVisible") != null
		    && listheader.getAttribute("isVisible").toString()
			    .equals("false")) {
		log.trace("Headar No aplica" + listheader);
	    } else {
		idVFDZLbxListadoCriterios.appendChild(new Listitem(listheader
			.getLabel(), listheader));
	    }

	}

    }

    @Override
    public void cargarComponentesVista() {
	log.info("Ejecutando metodo [ cargarComponentesVista ]");

	idVFDZLbxListadoCriterios = (Listbox) this
		.getFellow("idVFDZLbxListadoCriterios");
	idVFDZLbxListadoFunciones = (Listbox) this
		.getFellow("idVFDZLbxListadoFunciones");
	idVFDZTbxValor = (Textbox) this.getFellow("idVFDZTbxValor");
	idVFDZLbxListadoFitros = (Listbox) this
		.getFellow("idVFDZLbxListadoFitros");
	idVFDZLbxListadoValores = (Combobox) this
		.getFellow("idVFDZLbxListadoValores");
    }

    /**
     * @param items
     * @desc Adiciona los eventos a los componentes de la vista
     */
    private void addEventos(final List<Listitem> items) {
	log.info("Ejecutando metodo [ addEventos ]");
	log.debug("items ==>" + items);

	idVFDZLbxListadoCriterios.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			log.trace("idVFDZLbxListadoCriterios Events.ON_CLICK");
			if (idVFDZLbxListadoCriterios.getSelectedIndex() == 0) {
			    idVFDZLbxListadoFunciones.setSelectedIndex(0);
			    idVFDZLbxListadoFunciones.setDisabled(true);

			    idVFDZTbxValor.setValue("");
			    idVFDZTbxValor.setDisabled(true);
			} else {
			    idVFDZLbxListadoFunciones.setSelectedIndex(0);
			    idVFDZLbxListadoFunciones.setDisabled(false);

			    idVFDZLbxListadoValores.setVisible(false);
			    idVFDZTbxValor.setVisible(true);
			    idVFDZTbxValor.setValue("");
			    idVFDZTbxValor.setDisabled(true);
			}
		    }
		});

	idVFDZLbxListadoValores.addEventListener(Events.ON_SELECT,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			log.trace("idVFDZLbxListadoValores Events.ON_SELECT");
			extraerValor();
		    }
		});

	idVFDZLbxListadoFunciones.addEventListener(Events.ON_CLICK,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			log.trace("idVFDZLbxListadoFunciones Events.ON_CLICK");
			if (idVFDZLbxListadoFunciones.getSelectedIndex() == 0) {
			    idVFDZTbxValor.setValue("");
			    idVFDZTbxValor.setDisabled(true);
			} else {
			    idVFDZTbxValor.setValue("");
			    if (idVFDZLbxListadoFunciones.getSelectedIndex() == 1) {
				if (items.size() > 0)
				    cargarValores(items,
					    idVFDZLbxListadoCriterios
						    .getSelectedItem());

				idVFDZTbxValor.setVisible(false);
				idVFDZLbxListadoValores.setVisible(true);
			    } else if (idVFDZLbxListadoFunciones
				    .getSelectedIndex() == 9) {
				if (items.size() > 0)
				    cargarValores(items,
					    idVFDZLbxListadoCriterios
						    .getSelectedItem());

				idVFDZTbxValor.setVisible(false);
				idVFDZLbxListadoValores.setVisible(true);
			    } else {
				idVFDZLbxListadoValores.setVisible(false);
				idVFDZTbxValor.setVisible(true);
				idVFDZTbxValor.setDisabled(false);
			    }
			}
		    }
		});

	this.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
	    @Override
	    public void onEvent(Event arg0) throws Exception {
		log.trace("this Events.ON_CLOSE");
		parametros.put("filtros", idVFDZLbxListadoFitros.getItems());
	    }
	});
    }

    /**
     * Extrae el valor seleccionado para la comparacion
     */
    private void extraerValor() {
	log.info("Ejecutando metodo [ extraerValor ]");

	if (idVFDZLbxListadoValores.getAttribute("imagen") != null) {
	    idVFDZTbxValor.setValue(idVFDZLbxListadoValores.getSelectedItem()
		    .getImage());
	} else {
	    idVFDZTbxValor.setValue(idVFDZLbxListadoValores.getSelectedItem()
		    .getLabel());
	}
    }

    /**
     * @desc Elimina un filtro seleccionado
     */
    public void eliminarFila() {
	log.info("Ejecutando metodo [ eliminarFila ]");

	if (idVFDZLbxListadoFitros.getSelectedIndex() < 0) {
	    Messagebox.show(this.getAttribute("MSG_SELECCIONE_FILA")
		    .toString(), this.getAttribute("MSG_INFORMACION")
		    .toString(),
		    Messagebox.OK, Messagebox.INFORMATION);
	} else {
	    idVFDZLbxListadoFitros.getSelectedItem().detach();
	}
    }
}