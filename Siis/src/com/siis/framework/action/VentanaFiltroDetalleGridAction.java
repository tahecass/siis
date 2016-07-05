package com.siis.framework.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Group;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.casewaresa.framework.action.impl.IInicializarComponentes;

@SuppressWarnings("unchecked")
public class VentanaFiltroDetalleGridAction extends Window implements
	AfterCompose, IInicializarComponentes {

    private static final long serialVersionUID = -1472456942192422845L;
    protected static Logger log = Logger
	    .getLogger(VentanaFiltroDetalleAction.class);

    /* Componentes de la GUI */
    private Grid listaDetalles;
    private Listbox idVFDGZLbxListadoCriterios;
    private Listbox idVFDGZLbxListadoFunciones;
    private Textbox idVFDGZTbxValor;
    private Listbox idVFDGZLbxListadoFitros;
    private Combobox idVFDGZLbxListadoValores;
    private Map<String, Object> parametros;
    // Variable que permite idetentificar si la grilla de datos contiene detail
    // (detalle de detalle) por defecto es true en caso de que se quiera cambiar
    // ese valor se de bebe enviaro por par�metro
    private boolean aplicaDetail = true;

    @Override
    public void afterCompose() {
	log.info("Ejecutando metodo [ afterCompose ]");

	try {

	    cargarComponentesVista();

	    parametros = (Map<String, Object>) Executions.getCurrent().getArg();

	    listaDetalles = (Grid) parametros.get("listaDetalles");
	    if (parametros.get("aplicaDetail") != null)
		aplicaDetail = Boolean.parseBoolean(parametros.get(
			"aplicaDetail").toString());

	    cargarCriterios();

	    idVFDGZLbxListadoCriterios.setSelectedIndex(0);
	    idVFDGZLbxListadoFunciones.setSelectedIndex(0);

	    List<Component> list = null;
	    if (!listaDetalles.getRows().getChildren().isEmpty()) {
		list = listaDetalles.getRows().getChildren();
		addEventos((List<Component>) list);
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
		idVFDGZLbxListadoFitros.appendChild(filtros.get(0));
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
    private void cargarValores(List<Component> rows, int columna) {
	log.info("Ejecutando metodo [ cargarValores ]");
	List<String> valores = new LinkedList<String>();
	for (int i = 0; i < rows.size(); i++) {
	    if (!(rows.get(i) instanceof Group)) {
		Row item = (Row) rows.get(i);
		if (item.getChildren().get(columna) instanceof Label) {
		    Label cell = (Label) item.getChildren().get(columna);
		    if (cell.getValue().trim().isEmpty()) {
			if (cell.getValue() != null) {
			    if (!valores.contains(cell.getValue().toString()))
				valores.add(cell.getValue().toString());
			}
		    } else {
			if (!valores.contains(cell.getValue()))
			    valores.add(cell.getValue());
		    }
		} else {
		    if (item.getChildren().get(columna) instanceof Image) {
			Image cell = (Image) item.getChildren().get(columna);
			valores.add(cell.getTooltiptext());
		    }

		}
	    }

	}

	idVFDGZLbxListadoValores.setWidth("300px");
	idVFDGZLbxListadoValores.getItems().clear();
	idVFDGZLbxListadoValores.setAttribute("imagen", null);

	for (String valor : valores) {
	    idVFDGZLbxListadoValores.appendChild(new Comboitem(valor));
	}

	if (idVFDGZLbxListadoValores.getItems().size() > 0) {
	    idVFDGZLbxListadoValores.setSelectedIndex(0);

	    extraerValor();
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
	List<Component> rows = null;
	if (!listaDetalles.getRows().getChildren().isEmpty()) {
	    rows = listaDetalles.getRows().getChildren();
	    if (aplicaDetail)
		ocultarDetail(rows);
	    boolean[] aprovaciones = new boolean[rows.size()];

	    for (int i = 0; i < rows.size(); i++) {
		aprovaciones[i] = true;
	    }

	    if (idVFDGZLbxListadoFitros.getItems().size() == 0) {
		listarTodo(rows);
	    } else {
		List<Listitem> filtros = idVFDGZLbxListadoFitros.getItems();

		for (Listitem item : filtros) {
		    int criterio = Integer.parseInt(((Listcell) item
			    .getChildren().get(0)).getValue().toString());
		    int funcion = Integer.parseInt(((Listcell) item
			    .getChildren().get(1)).getValue().toString());

		    String valor = ((Listcell) item.getChildren().get(2))
			    .getLabel();

		    log.info("Criterio-->" + criterio + " funcion-->" + funcion
			    + "  valor-->" + valor);
		    switch (funcion) {
		    case 1:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarIgual((Row) rows.get(i), criterio,
					    valor);
			}

			break;
		    case 2:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarMayor((Row) rows.get(i), criterio,
					    valor);
			}
			break;
		    case 3:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarMenor((Row) rows.get(i), criterio,
					    valor);
			}
			break;
		    case 4:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarMayorIgual((Row) rows.get(i),
					    criterio, valor);
			}
			break;
		    case 5:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarMenorIgual((Row) rows.get(i),
					    criterio, valor);
			}
			break;
		    case 6:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarComience((Row) rows.get(i),
					    criterio, valor);
			}
			break;
		    case 7:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarTermine((Row) rows.get(i),
					    criterio, valor);
			}
			break;
		    case 8:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarContiene((Row) rows.get(i),
					    criterio, valor);
			}
			break;
		    case 9:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarDiferente((Row) rows.get(i),
					    criterio, valor);
			}
			break;
		    case 10:
			for (int i = 0; i < rows.size(); i++) {
			    aprovaciones[i] = aprovaciones[i]
				    && listarNoContiene((Row) rows.get(i),
					    criterio, valor);
			}

			break;
		    }
		}
		mostrarOcultarFilas(rows, aprovaciones);
		parametros.put("filtros", idVFDGZLbxListadoFitros.getItems());
	    }
	}
    }

    /**
     * @param List
     *            <Listitem>
     * @param boolean[]
     * @desc Este metodo es el que oculata y mustra las filas de acuerdo a las
     *       aprovaciones
     */
    private void mostrarOcultarFilas(List<Component> rows,
	    boolean[] aprovaciones) {
	log.info("Ejecutando metodo [ mostrarOcultarFilas ]");

	for (int i = 0; i < rows.size(); i++) {
	    if (aprovaciones[i]) {
		rows.get(i).setVisible(true);
	    } else {
		rows.get(i).setVisible(false);
	    }
	}
    }

    /**
     * @desc Este metodo agrega filtros a la lista, se activa cuando presionan
     *       el boton agregar
     */
    public void agregarFiltro() {
	log.info("Ejecutando metodo [ agregarFiltro ]");

	if (idVFDGZLbxListadoCriterios.getSelectedIndex() != 0) {
	    if (idVFDGZLbxListadoFunciones.getSelectedIndex() != 0) {
		if (!idVFDGZTbxValor.getValue().trim().equals("")) {
		    Listitem item = new Listitem();

		    if (idVFDGZLbxListadoValores.getAttribute("imagen") != null) {
			item.setAttribute("imagen", new Object());
		    }

		    Listcell criterio = new Listcell(idVFDGZLbxListadoCriterios
			    .getSelectedItem().getLabel());
		    criterio.setValue(idVFDGZLbxListadoCriterios
			    .getSelectedIndex() - 1);
		    item.appendChild(criterio);

		    Listcell funcion = new Listcell(idVFDGZLbxListadoFunciones
			    .getSelectedItem().getLabel());
		    funcion.setValue(idVFDGZLbxListadoFunciones
			    .getSelectedIndex());
		    item.appendChild(funcion);

		    if (idVFDGZLbxListadoValores.getAttribute("imagen") != null) {
			Listcell valor = new Listcell("",
				idVFDGZTbxValor.getValue());
			item.appendChild(valor);

			idVFDGZLbxListadoValores.setAttribute("imagen", null);
		    } else {
			Listcell valor = new Listcell(
				idVFDGZTbxValor.getValue());
			item.appendChild(valor);
		    }

		    idVFDGZLbxListadoFitros.appendChild(item);

		    parametros.put("filtros",
			    idVFDGZLbxListadoFitros.getItems());

		    idVFDGZLbxListadoCriterios.setSelectedIndex(0);

		    idVFDGZLbxListadoFunciones.setSelectedIndex(0);
		    idVFDGZLbxListadoFunciones.setDisabled(true);

		    idVFDGZLbxListadoValores.setVisible(false);
		    idVFDGZTbxValor.setVisible(true);
		    idVFDGZTbxValor.setValue("");
		    idVFDGZTbxValor.setDisabled(true);
		} else {
		    idVFDGZTbxValor.setFocus(true);
		}
	    } else {
		idVFDGZLbxListadoFunciones.setFocus(true);
	    }
	} else {
	    idVFDGZLbxListadoCriterios.setFocus(true);
	}
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion contiene
     */
    private boolean listarContiene(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarContiene ]");

	Label label = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().contains(valor))
			    return true;
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().contains(valor))
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().contains(valor)) {
			    return true;
			}

		} else {
		    log.info("Valor Colum image: ");
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().contains(valor))
				return true;
		    }
		}
	    }

	}
	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion No contiene
     */
    private boolean listarNoContiene(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarContiene ]");

	Label label = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			if (!label.getValue().toString().contains(valor))
			    return true;
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (!imagen.getTooltiptext().contains(valor))
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			if (!label.getValue().toString().contains(valor))
			    return true;
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (!imagen.getTooltiptext().contains(valor))
				return true;
		    }
		}
	    }
	}
	return false;
    }

    /**
     * @param row
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Comience por
     */
    private boolean listarComience(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarComience ]");

	Label label = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().startsWith(valor))
			    return true;
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().startsWith(valor))
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().startsWith(valor))
			    return true;
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().startsWith(valor))
				return true;
		    }
		}
	    }

	}
	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Termine en
     */
    private boolean listarTermine(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarTermine ]");

	Label label = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().endsWith(valor))
			    return true;
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().endsWith(valor))
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().endsWith(valor))
			    return true;
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().endsWith(valor))
				return true;
		    }
		}
	    }
	}
	return false;
    }

    /**
     * @param row
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Menor que
     */
    private boolean listarMenor(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMenor ]");
	Label label = null;

	String valorFiltro = valor.replace(".", "");
	String tipoDatoCriterio = obtenerTipoDato(valorFiltro);

	String valorCelda = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null) {
			valorCelda = label.getValue().replace(".", "");
			String tipoDatoCell = obtenerTipoDato(valorCelda);
			if (tipoDatoCriterio.equals("Numerico")
				&& tipoDatoCell.equals("Numerico")) {
			    Double valorNum = new Double(valorFiltro);
			    Double valorCell = new Double(valorCelda);

			    if (valorCell < valorNum)
				return true;
			} else if (tipoDatoCriterio.equals("Fecha")
				&& tipoDatoCell.equals("Fecha")) {

			    SimpleDateFormat formato = new SimpleDateFormat(
				    "dd/MM/yyyy");
			    Date fechaCriterio = null, fechaCell = null;
			    try {
				fechaCriterio = formato.parse(valor);
				fechaCell = formato.parse(label.getValue());

			    } catch (ParseException e) {
				e.printStackTrace();
			    }

			    if (fechaCell.before(fechaCriterio))
				return true;
			} else if (tipoDatoCriterio.equals("String")
				&& tipoDatoCell.equals("String")) {
			    if (label.getValue().toString().compareTo(valor) < 0)
				return true;

			    if (label.getValue() != null)
				if (label.getValue().toString()
					.compareTo(valor) < 0)
				    return true;
			}
		    }
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) < 0)
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			valorCelda = label.getValue().replace(".", "");
		    String tipoDatoCell = obtenerTipoDato(valorCelda);
		    if (tipoDatoCriterio.equals("Numerico")
			    && tipoDatoCell.equals("Numerico")) {
			Double valorNum = new Double(valorFiltro);
			Double valorCell = new Double(valorCelda);
			log.debug("valor ==>" + valorNum);
			log.debug("valor CELL ==> " + valorCell);

			if (valorCell < valorNum)
			    return true;
		    } else if (tipoDatoCriterio.equals("Fecha")
			    && tipoDatoCell.equals("Fecha")) {

			SimpleDateFormat formato = new SimpleDateFormat(
				"dd/MM/yyyy");
			Date fechaCriterio = null, fechaCell = null;
			try {
			    fechaCriterio = formato.parse(valor);
			    fechaCell = formato.parse(label.getValue());

			} catch (ParseException e) {
			    e.printStackTrace();
			}

			if (fechaCell.before(fechaCriterio))
			    return true;
		    } else if (tipoDatoCriterio.equals("String")
			    && tipoDatoCell.equals("String")) {
			if (label.getValue().toString().compareTo(valor) < 0)
			    return true;

			if (label.getValue() != null)
			    if (label.getValue().toString().compareTo(valor) < 0)
				return true;
		    }
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) < 0)
				return true;
		    }
		}
	    }
	}
	return false;
    }

    /**
     * @param row
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Mayor que
     */
    private boolean listarMayor(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMayor ]");
	Label label = null;

	String valorFiltro = valor.replace(".", "");
	String tipoDatoCriterio = obtenerTipoDato(valorFiltro);

	String valorCelda = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null) {
			valorCelda = label.getValue().replace(".", "");
			String tipoDatoCell = obtenerTipoDato(valorCelda);
			if (tipoDatoCriterio.equals("Numerico")
				&& tipoDatoCell.equals("Numerico")) {
			    Double valorNum = new Double(valorFiltro);
			    Double valorCell = new Double(valorCelda);

			    if (valorCell > valorNum)
				return true;
			} else if (tipoDatoCriterio.equals("Fecha")
				&& tipoDatoCell.equals("Fecha")) {

			    SimpleDateFormat formato = new SimpleDateFormat(
				    "dd/MM/yyyy");
			    Date fechaCriterio = null, fechaCell = null;
			    try {
				fechaCriterio = formato.parse(valor);
				fechaCell = formato.parse(label.getValue());

			    } catch (ParseException e) {
				e.printStackTrace();
			    }

			    if (fechaCell.after(fechaCriterio))
				return true;
			} else if (tipoDatoCriterio.equals("String")
				&& tipoDatoCell.equals("String")) {
			    if (label.getValue().toString().compareTo(valor) > 0)
				return true;
			    if (label.getValue() != null)
				if (label.getValue().toString()
					.compareTo(valor) > 0)
				    return true;
			}
		    }
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) > 0)
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null) {
			valorCelda = label.getValue().replace(".", "");
			String tipoDatoCell = obtenerTipoDato(valorCelda);
			if (tipoDatoCriterio.equals("Numerico")
				&& tipoDatoCell.equals("Numerico")) {
			    Double valorNum = new Double(valorFiltro);
			    Double valorCell = new Double(valorCelda);

			    if (valorCell > valorNum)
				return true;
			} else if (tipoDatoCriterio.equals("Fecha")
				&& tipoDatoCell.equals("Fecha")) {

			    SimpleDateFormat formato = new SimpleDateFormat(
				    "dd/MM/yyyy");
			    Date fechaCriterio = null, fechaCell = null;
			    try {
				fechaCriterio = formato.parse(valor);
				fechaCell = formato.parse(label.getValue());

			    } catch (ParseException e) {
				e.printStackTrace();
			    }

			    if (fechaCell.after(fechaCriterio))
				return true;
			} else if (tipoDatoCriterio.equals("String")
				&& tipoDatoCell.equals("String")) {
			    if (label.getValue().toString().compareTo(valor) > 0)
				return true;
			    if (label.getValue().toString().compareTo(valor) > 0)
				return true;
			}
		    }
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) > 0)
				return true;
		    }
		}
	    }

	}
	return false;
    }

    /**
     * @param item
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Menor o Igual que
     */

    private boolean listarMenorIgual(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMenorIgual ]");

	Label label = null;

	String valorFiltro = valor.replace(".", "");
	String tipoDatoCriterio = obtenerTipoDato(valorFiltro);

	String valorCelda = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			valorCelda = label.getValue().replace(".", "");
		    String tipoDatoCell = obtenerTipoDato(valorCelda);
		    if (tipoDatoCriterio.equals("Numerico")
			    && tipoDatoCell.equals("Numerico")) {
			Double valorNum = new Double(valorFiltro);
			Double valorCell = new Double(valorCelda);

			if (valorCell <= valorNum)
			    return true;
		    } else if (tipoDatoCriterio.equals("Fecha")
			    && tipoDatoCell.equals("Fecha")) {

			SimpleDateFormat formato = new SimpleDateFormat(
				"dd/MM/yyyy");
			Date fechaCriterio = null, fechaCell = null;
			try {
			    fechaCriterio = formato.parse(valor);
			    fechaCell = formato.parse(label.getValue());

			} catch (ParseException e) {
			    e.printStackTrace();
			}

			if (fechaCell.before(fechaCriterio)
				|| fechaCell.equals(fechaCriterio))
			    return true;
		    } else if (tipoDatoCriterio.equals("String")
			    && tipoDatoCell.equals("String")) {
			if (label.getValue().toString().compareTo(valor) < 1)
			    return true;
			if (label.getValue().toString().compareTo(valor) < 1)
			    return true;
		    }
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) < 1)
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			valorCelda = label.getValue().replace(".", "");
		    String tipoDatoCell = obtenerTipoDato(valorCelda);
		    if (tipoDatoCriterio.equals("Numerico")
			    && tipoDatoCell.equals("Numerico")) {
			Double valorNum = new Double(valorFiltro);
			Double valorCell = new Double(valorCelda);

			if (valorCell <= valorNum)
			    return true;
		    } else if (tipoDatoCriterio.equals("Fecha")
			    && tipoDatoCell.equals("Fecha")) {

			SimpleDateFormat formato = new SimpleDateFormat(
				"dd/MM/yyyy");
			Date fechaCriterio = null, fechaCell = null;
			try {
			    fechaCriterio = formato.parse(valor);
			    fechaCell = formato.parse(label.getValue());

			} catch (ParseException e) {
			    e.printStackTrace();
			}

			if (fechaCell.before(fechaCriterio)
				|| fechaCell.equals(fechaCriterio))
			    return true;
		    } else if (tipoDatoCriterio.equals("String")
			    && tipoDatoCell.equals("String")) {
			if (label.getValue().toString().compareTo(valor) < 1)
			    return true;
			if (label.getValue().toString().compareTo(valor) < 1)
			    return true;
		    }
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) < 1)
				return true;
		    }
		}
	    }

	}
	return false;
    }

    /**
     * @param row
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Mayor o Igual que
     */
    private boolean listarMayorIgual(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarMayorIgual ]");

	Label label = null;
	String valorFiltro = valor.replace(".", "");
	String tipoDatoCriterio = obtenerTipoDato(valorFiltro);

	String valorCelda = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			valorCelda = label.getValue().replace(".", "");
		    String tipoDatoCell = obtenerTipoDato(valorCelda);
		    if (tipoDatoCriterio.equals("Numerico")
			    && tipoDatoCell.equals("Numerico")) {
			Double valorNum = new Double(valorFiltro);
			Double valorCell = new Double(valorCelda);

			if (valorCell >= valorNum)
			    return true;
		    } else if (tipoDatoCriterio.equals("Fecha")
			    && tipoDatoCell.equals("Fecha")) {

			SimpleDateFormat formato = new SimpleDateFormat(
				"dd/MM/yyyy");
			Date fechaCriterio = null, fechaCell = null;
			try {
			    fechaCriterio = formato.parse(valor);
			    fechaCell = formato.parse(label.getValue());

			} catch (ParseException e) {
			    e.printStackTrace();
			}

			if (fechaCell.after(fechaCriterio)
				|| fechaCell.equals(fechaCriterio))
			    return true;
		    } else if (tipoDatoCriterio.equals("String")
			    && tipoDatoCell.equals("String")) {
			if (label.getValue().toString().compareTo(valor) > -1)
			    return true;
			if (label.getValue().toString().compareTo(valor) > -1)
			    return true;
		    }
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) > -1)
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			valorCelda = label.getValue().replace(".", "");
		    String tipoDatoCell = obtenerTipoDato(valorCelda);
		    if (tipoDatoCriterio.equals("Numerico")
			    && tipoDatoCell.equals("Numerico")) {
			Double valorNum = new Double(valorFiltro);
			Double valorCell = new Double(valorCelda);

			if (valorCell >= valorNum)
			    return true;
		    } else if (tipoDatoCriterio.equals("Fecha")
			    && tipoDatoCell.equals("Fecha")) {

			SimpleDateFormat formato = new SimpleDateFormat(
				"dd/MM/yyyy");
			Date fechaCriterio = null, fechaCell = null;
			try {
			    fechaCriterio = formato.parse(valor);
			    fechaCell = formato.parse(label.getValue());

			} catch (ParseException e) {
			    e.printStackTrace();
			}

			if (fechaCell.after(fechaCriterio)
				|| fechaCell.equals(fechaCriterio))
			    return true;
		    } else if (tipoDatoCriterio.equals("String")
			    && tipoDatoCell.equals("String")) {
			if (label.getValue().toString().compareTo(valor) > -1)
			    return true;
			if (label.getValue().toString().compareTo(valor) > -1)
			    return true;
		    }
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) > -1)
				return true;
		    }
		}
	    }

	}
	return false;
    }

    /**
     * @param row
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Igual
     */
    private boolean listarIgual(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarIgual ]");
	Label label = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().compareTo(valor) == 0)
			    return true;
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) == 0)
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			if (label.getValue().toString().compareTo(valor) == 0)
			    return true;
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (imagen.getTooltiptext().compareTo(valor) == 0)
				return true;
		    }
		}
	    }

	}

	return false;
    }

    /**
     * @param row
     * @param columna
     * @param valor
     * @return
     * @desc Opcion de comparacion Igual
     */
    private boolean listarDiferente(Row row, int columna, String valor) {
	log.info("Ejecutando metodo [ listarIgual ]");
	Label label = null;
	if (!(row instanceof Group)) {
	    if (aplicaDetail) {
		if (row.getChildren().get(columna + 1) instanceof Label) {
		    label = (Label) row.getChildren().get(columna + 1);
		    if (label != null && label.getValue() != null)
			if (!(label.getValue().toString().compareTo(valor) == 0))
			    return true;
		} else {
		    if (row.getChildren().get(columna + 1) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(
				columna + 1);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (!(imagen.getTooltiptext().compareTo(valor) == 0))
				return true;
		    }
		}
	    } else {
		if (row.getChildren().get(columna) instanceof Label) {
		    label = (Label) row.getChildren().get(columna);
		    if (label != null && label.getValue() != null)
			if (!(label.getValue().toString().compareTo(valor) == 0))
			    return true;
		} else {
		    if (row.getChildren().get(columna) instanceof Image) {
			Image imagen = (Image) row.getChildren().get(columna);
			if (imagen != null && imagen.getTooltiptext() != null)
			    if (!(imagen.getTooltiptext().compareTo(valor) == 0))
				return true;
		    }
		}
	    }

	}

	return false;
    }

    /**
     * @param rows
     * @desc Muestra todas las filas
     */
    private void listarTodo(List<Component> rows) {
	log.info("Ejecutando metodo [ listarTodo ]");

	for (Component row : rows) {
	    row.setVisible(true);
	}
    }

    /**
     * @param rows
     * @desc Muestra todas las filas
     */
    private void ocultarDetail(List<Component> rows) {
	log.info("Ejecutando metodo [ listarTodo ]");

	for (Component row : rows) {
	    if (row.getFirstChild() instanceof Detail) {
		((Row) row).getDetailChild().setOpen(false);
	    }
	}
    }

    /**
     * @desc devuelve el tipo de dato del valor pasado como parametro numerico o
     *       string
     * @param String
     *            valor
     * @return String tipo de datos del valor pasado como parametro
     */
    public String obtenerTipoDato(String valor) {
	if (this.esNumerico(valor)) {
	    return "Numerico";
	} else if (esFecha(valor)) {
	    return "Fecha";
	} else {
	    return "String";
	}
    }

    /**
     * @desc valida si la cadena pasada como parametro es un valor numerico
     * @param String
     *            cadena
     * @return boolean true si el valor es numerico sino false
     */
    public boolean esNumerico(String cadena) {
	if (cadena == null || cadena.isEmpty()) {
	    return false;
	}
	int i = 0;
	Character c = cadena.charAt(0);
	if (c.equals("-")) {
	    if (cadena.length() > 1) {
		i++;
	    } else {
		return false;
	    }
	}

	for (; i < cadena.length(); i++) {
	    if (!Character.isDigit(cadena.charAt(i))) {
		return false;
	    }
	}
	return true;
    }

    /**
     * @desc valida si la cadena pasada como parametro es una fecha
     * @param String
     *            cadena
     * @return boolean true si el valor es una fecha sino false
     */
    public boolean esFecha(String valor) {
	Pattern patron = null;
	Matcher encaja = null;

	if (valor == null || valor.isEmpty()) {
	    return false;
	}
	// Formato de fecha dd/MM/yyyy
	patron = Pattern
		.compile("^(?:(?:0?[1-9]|1\\d|2[0-8])(\\/|-)(?:0?[1-9]|1[0-2]))(\\/|-)"
			+ "(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])"
			+ "$|^(?:(?:31(\\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\\/|-)"
			+ "(?:0?[1,3-9]|1[0-2])))(\\/|-)"
			+ "(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])"
			+ "$|^(29(\\/|-)0?2)(\\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\\d\\d)"
			+ "?(?:0[48]|[2468][048]|[13579][26]))$");
	encaja = patron.matcher(valor);
	if (encaja.find()) {
	    return true;

	} else {
	    // Formato de fecha MM/dd/yyyy
	    patron = Pattern
		    .compile("^(?:(?:(?:0?[13578]|1[02])(\\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])"
			    + "(\\/|-)(?:29|30)))(\\/|-)(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])"
			    + "$|^(?:(?:0?[1-9]|1[0-2])(\\/|-)(?:0?[1-9]|1\\d|2[0-8]))"
			    + "(\\/|-)(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])"
			    + "$|^(0?2(\\/|-)29)(\\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)"
			    + "|(?:\\d\\d)?(?:0[48]|[2468][048]|[13579][26]))$");
	    encaja = patron.matcher(valor);
	    if (encaja.find()) {
		return true;

	    }
	}
	return false;
    }

    /**
     * @desc Carga los criterios de comparacion
     */
    private void cargarCriterios() {
	log.info("Ejecutando metodo [ cargarCriterios ]");

	List<Component> columnas = listaDetalles.getColumns().getChildren();

	idVFDGZLbxListadoCriterios.appendChild(new Listitem("Seleccione..."));
	if (aplicaDetail) {
	    for (int i = 1; i < columnas.size(); i++) {
		Column columna = (Column) columnas.get(i);
		if (columna.getLabel() != null
			&& !columna.getLabel().trim().isEmpty()) {

		    if (columna.getAttribute("isVisible") != null) {
			Listitem item = new Listitem(
				((Column) columna).getLabel());
			idVFDGZLbxListadoCriterios.appendChild(item);
			item.setVisible(false);
		    } else {
			idVFDGZLbxListadoCriterios.appendChild(new Listitem(
				((Column) columna).getLabel()));
		    }
		}
	    }
	} else {
	    for (Component column : columnas) {

		if (((Column) column).getLabel() != null
			&& !((Column) column).getLabel().trim().isEmpty()) {
		    if (column.getAttribute("isVisible") != null) {
			Listitem item = new Listitem(
				((Column) column).getLabel());
			idVFDGZLbxListadoCriterios.appendChild(item);
			item.setVisible(false);
		    } else {
			idVFDGZLbxListadoCriterios.appendChild(new Listitem(
				((Column) column).getLabel()));
		    }

		}
	    }

	}
    }

    @Override
    public void cargarComponentesVista() {
	log.info("Ejecutando metodo [ cargarComponentesVista ]");

	idVFDGZLbxListadoCriterios = (Listbox) this
		.getFellow("idVFDGZLbxListadoCriterios");
	idVFDGZLbxListadoFunciones = (Listbox) this
		.getFellow("idVFDGZLbxListadoFunciones");
	idVFDGZTbxValor = (Textbox) this.getFellow("idVFDGZTbxValor");
	idVFDGZLbxListadoFitros = (Listbox) this
		.getFellow("idVFDGZLbxListadoFitros");
	idVFDGZLbxListadoValores = (Combobox) this
		.getFellow("idVFDGZLbxListadoValores");
    }

    /**
     * @param rows
     * @desc Adiciona los eventos a los componentes de la vista
     */
    private void addEventos(final List<Component> rows) {
	log.info("Ejecutando metodo [ addEventos ]");

	idVFDGZLbxListadoCriterios.addEventListener(Events.ON_SELECT,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			if (idVFDGZLbxListadoCriterios.getSelectedIndex() == 0) {
			    idVFDGZLbxListadoFunciones.setSelectedIndex(0);
			    idVFDGZLbxListadoFunciones.setDisabled(true);

			    idVFDGZTbxValor.setValue("");
			    idVFDGZTbxValor.setDisabled(true);
			} else {
			    idVFDGZLbxListadoFunciones.setSelectedIndex(0);
			    idVFDGZLbxListadoFunciones.setDisabled(false);

			    idVFDGZLbxListadoValores.setVisible(false);
			    idVFDGZTbxValor.setVisible(true);
			    idVFDGZTbxValor.setValue("");
			    idVFDGZTbxValor.setDisabled(true);
			}
		    }
		});

	idVFDGZLbxListadoValores.addEventListener(Events.ON_SELECT,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			extraerValor();
		    }
		});

	idVFDGZLbxListadoFunciones.addEventListener(Events.ON_SELECT,
		new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0) throws Exception {
			if (idVFDGZLbxListadoFunciones.getSelectedIndex() == 0) {
			    idVFDGZTbxValor.setValue("");
			    idVFDGZTbxValor.setDisabled(true);
			} else {
			    idVFDGZTbxValor.setValue("");
			    if (idVFDGZLbxListadoFunciones.getSelectedIndex() == 1) {
				if (rows.size() > 0) {
				    if (aplicaDetail)
					cargarValores(rows,
						idVFDGZLbxListadoCriterios
							.getSelectedIndex());
				    else
					cargarValores(rows,
						idVFDGZLbxListadoCriterios
							.getSelectedIndex() - 1);
				}

				idVFDGZTbxValor.setVisible(false);
				idVFDGZLbxListadoValores.setVisible(true);
			    } else {
				if (idVFDGZLbxListadoFunciones
					.getSelectedIndex() == 9) {
				    if (aplicaDetail)
					cargarValores(rows,
						idVFDGZLbxListadoCriterios
							.getSelectedIndex());
				    else
					cargarValores(rows,
						idVFDGZLbxListadoCriterios
							.getSelectedIndex() - 1);

				    idVFDGZTbxValor.setVisible(false);
				    idVFDGZLbxListadoValores.setVisible(true);
				} else {

				    idVFDGZLbxListadoValores.setVisible(false);
				    idVFDGZTbxValor.setVisible(true);
				    idVFDGZTbxValor.setDisabled(false);
				}
			    }
			}
		    }
		});

	this.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
	    @Override
	    public void onEvent(Event arg0) throws Exception {
		parametros.put("filtros", idVFDGZLbxListadoFitros.getItems());
	    }
	});
    }

    /**
     * Extrae el valor seleccionado para la comparacion
     */
    private void extraerValor() {
	log.info("Ejecutando metodo [ extraerValor ]");

	if (idVFDGZLbxListadoValores.getAttribute("imagen") != null) {
	    idVFDGZTbxValor.setValue(idVFDGZLbxListadoValores.getSelectedItem()
		    .getImage());
	} else {
	    idVFDGZTbxValor.setValue(idVFDGZLbxListadoValores.getSelectedItem()
		    .getLabel());
	}
    }

    /**
     * @desc Elimina un filtro seleccionado
     */
    public void eliminarFila() {
	log.info("Ejecutando metodo [ eliminarFila ]");

	if (idVFDGZLbxListadoFitros.getSelectedIndex() < 0) {
	    Messagebox.show(
		    this.getAttribute("MSG_SELECCIONE_FILA").toString(), this
			    .getAttribute("MSG_INFORMACION").toString(),
		    Messagebox.OK, Messagebox.INFORMATION);
	} else {
	    idVFDGZLbxListadoFitros.getSelectedItem().detach();
	}
    }
}