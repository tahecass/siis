/**
 * 
 */
package com.casewaresa.framework.Filter.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.casewaresa.framework.Filter.TipoDato;
import com.casewaresa.framework.Filter.dto.ColumnsMapper;
import com.casewaresa.framework.Filter.dto.CriterioWhere;
import com.casewaresa.framework.Filter.dto.Operador;
import com.casewaresa.framework.Filter.dto.OrderBy;
import com.casewaresa.framework.Filter.dto.SentenciaSQL;
import com.casewaresa.framework.Filter.manager.DTOMapper;
import com.casewaresa.framework.action.ActionStandard;
import com.casewaresa.framework.action.IActionListbox;
import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.assembler.AssemblerStandard;
import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
//import com.casewaresa.framework.util.FechasUtil.FORMATOS;
import com.casewaresa.framework.util.Utilidades;
import com.casewaresa.iceberg_ln.dto.LNTCnFiltro;

/**
 * @author casewaredes02
 * @name VentanaFiltroMaestroAction.java
 * @date 24/02/2012
 * @desc Filtro para el listado maestro de una forma...
 */

public class VentanaFiltroAction extends ActionStandard implements
		IInicializarComponentes {

	private static final long serialVersionUID = 5122420364382575368L;
	/**
	 * Aributo log de la clase
	 */
	protected static Logger log = Logger.getLogger(VentanaFiltroAction.class);
	/*
	 * Componentes de la vista
	 */

	private List<Operador> listaOperadores;
	private List<ColumnsMapper> listaColumns;
	private List<Object> campos;
	private List<CriterioWhere> filtro;
	private List<OrderBy> listaOrderBy;
	private SimpleDateFormat format;
	private StringBuilder build;
	private String view;
	private String idView;
	private int index = 0;
	// private Long secTipoNomina;

	private Listbox idFILZLbxColumnsMapper;
	private AssemblerStandard assemblerStandard;
	private Listbox idFILZLbxColumnas;
	private Listbox idFILZLbxOperadores;
	private Listbox idFILZLbxCondicionesWhere;
	private Listbox idFILZLbxConectorLogicos;
	private Intbox idFILZIbxValor1;
	private Intbox idFILZIbxValor2;
	private Textbox idFILZTbxValor1;
	private Textbox idFILZTbxValor2;
	private Label idFILZLblAND;
	private Datebox idFILZDbxFecha1;
	private Datebox idFILZDbxFecha2;
	private Hbox idFILZHbxCamposValores;
	private Longbox idFILZLgbxValor2;
	private Longbox idFILZLgbxValor1;
	private Textbox idFILZTbxListaValores;
	private Image idFILZImgAgregarLista;

	private Listbox idFILZLbxColumnasOrderBy;
	private Listbox idFILZLbxOrderBy;
	private Listbox idFILZLbxCondicionesOrderBy;

	private Button idFILZBtnAgregarCondicion;
	private Button idFILZBtnEditarCondicion;
	private Button idFILZBtnCancelar;
	private Listitem itemEdit;
	private String operacion = "N";
	private List<Component> listaHeaders;
	private ColumnsMapper columnSec;
	private IActionListbox action;
	private boolean cargarFiltros = false;
	private Label idFILZLblOperadorEntre;
	private Label idFILZLblOperadorNoIncluye;
	private Label idFILZLblOperadorIncluye;
	private Label idFILZLblOperadorMayorIgual;
	private Label idFILZLblOperadorMenorIgual;
	private Label idFILZLblOperadorMayor;
	private Label idFILZLblOperadorMenor;
	private Label idFILZLblOperadorTermina;
	private Label idFILZLblOperadorContenga;
	private Label idFILZLblOperadorComience;
	private Label idFILZLblOperadorDiferente;
	private Label idFILZLblOperadorIgual;
	private String filtroAdicional;

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name agregarCondicionOrderBy
	 * @desc agrega la condicion ingresada para determinar el orden de la
	 *       consulta
	 */
	public void agregarCondicionOrderBy() {
		log.info("Ejecutando el método [ agregarCondicionOrderBy ]...");

		ColumnsMapper column = (ColumnsMapper) idFILZLbxColumnasOrderBy
				.getSelectedItem().getValue();

		if (column != null) {

			OrderBy ordenBy = new OrderBy(column, idFILZLbxOrderBy
					.getSelectedItem().getValue().toString(), idFILZLbxOrderBy
					.getSelectedItem().getLabel());
			getListaOrderBy().add(ordenBy);
			listarCondicionesOrderBy();

			log.info("lista de ordenBy : " + getListaOrderBy());

		} else {
			Messagebox.show(this.getAttribute("MSG_SELECCIONE_COLUMNA")
					.toString());
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name agregarCondicion
	 * @desc permite agregar las condiciones WHERE especificadas a una lista
	 */

	public void agregarCondicionWhere() {
		log.info("Ejecutando el método [ agregarCondicionWhere ]...");

		ColumnsMapper column = (ColumnsMapper) idFILZLbxColumnas
				.getSelectedItem().getValue();

		if (column != null) {

			Operador operador = (Operador) idFILZLbxOperadores
					.getSelectedItem().getValue();
			String operadorLogico = (String) idFILZLbxConectorLogicos
					.getSelectedItem().getValue();

			if (!idFILZLbxConectorLogicos.isDisabled()) {
				if (operadorLogico == null) {
					Messagebox
							.show(this.getAttribute("MSG_SELECCIONE_CONECTOR")
									.toString());
					return;
				}
			}
			if (operador == null) {
				Messagebox.show(this.getAttribute("MSG_SELECCIONE_OPERADOR")
						.toString());
				return;
			}

			List<String> listaValoresCriterio = cargarValoresCriterioWhere();

			if (listaValoresCriterio.isEmpty()) {
				// Messagebox.show(this.getAttribute("MSG_INGRESE_VALOR")
				// .toString());
				return;
			}

			CriterioWhere criterio = new CriterioWhere(column, operador,
					new Operador(idFILZLbxConectorLogicos.getSelectedItem()
							.getLabel(), operadorLogico), listaValoresCriterio);

			if (operacion.equals("N")) {
				getFiltro().add(criterio);
			} else if (operacion.equals("E")) {
				getFiltro().set(itemEdit.getIndex(), criterio);
				onCancelar(1);
			}
			log.info("Tamaño getFiltro(): " + getFiltro().size());
			log.info("filtro : " + getFiltro());
			
			listarCondicionesWhere();
			operacion = "N";
		} else {
			Messagebox.show(this.getAttribute("MSG_SELECCIONE_COLUMNA")
					.toString());
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name agregarValores
	 * @desc permite agregar los valores de un campo a otro separados por comas
	 *       para luego ser enviados a la lista de criterios
	 */
	public void agregarValores() {
		log.info("Ejecutando el método [ agregarValores ]...");

		if (index > 0) {
			getBuild().append(", ");
		}

		if (idFILZIbxValor1.isVisible()) {
			getBuild().append(idFILZIbxValor1.getValue());
			idFILZTbxListaValores.setValue(getBuild().toString());
			idFILZIbxValor1.setRawValue("");
		}
		if (idFILZLgbxValor1.isVisible()) {
			getBuild().append(idFILZLgbxValor1.getValue());
			idFILZTbxListaValores.setValue(getBuild().toString());
			idFILZLgbxValor1.setRawValue(null);
		}
		if (idFILZDbxFecha1.isVisible()) {
			getBuild().append(getFormat().format(idFILZDbxFecha1.getValue()));
			idFILZTbxListaValores.setValue(getBuild().toString());
			idFILZDbxFecha1.setRawValue(null);
		}
		index++;
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name cargarColumnasForCondicion
	 * @param op
	 * @desc carga una lista con los nombres de las columnas para crear las
	 *       condiciones de una consulta
	 */
	public void cargarColumnasForCondicion(int op) {
		log.info("Ejecutando el método cargarColumnasForCondicion...");

		if (!listaColumns.isEmpty()) {

			if (op == 0) {

				idFILZLbxColumnas.getItems().clear();
				idFILZLbxColumnas.appendItem(this
						.getAttribute("MSG_SELECCIONE").toString(), null);

				for (ColumnsMapper column : listaColumns) {
					if (!column.getName().startsWith("SEC")
							&& !column.getType().equals("java.io.InputStream")) {
						Listitem item = new Listitem(column.getLabel(), column);
						idFILZLbxColumnas.appendChild(item);
					}
				}
				idFILZLbxColumnas.setSelectedIndex(0);
			}
			if (op == 1) {
				idFILZLbxColumnasOrderBy.getItems().clear();
				idFILZLbxColumnasOrderBy.appendItem(
						this.getAttribute("MSG_SELECCIONE").toString(), null);

				for (ColumnsMapper column : listaColumns) {
					if (!column.getName().startsWith("SEC")
							&& !column.getType().equals("java.io.InputStream")) {
						Listitem item = new Listitem(column.getLabel(), column);
						idFILZLbxColumnasOrderBy.appendChild(item);
					}
				}
				idFILZLbxColumnasOrderBy.setSelectedIndex(0);
			}

			if (op == 2) {

				idFILZLbxColumnasOrderBy.getItems().clear();
				idFILZLbxColumnasOrderBy.appendItem(
						this.getAttribute("MSG_SELECCIONE").toString(), null);
				boolean index = false;

				for (ColumnsMapper column : listaColumns) {
					if (!column.getName().startsWith("SEC")
							&& !column.getType().equals("java.io.InputStream")) {
						for (OrderBy orden : getListaOrderBy()) {
							if (column.getLabel().equals(
									orden.getColumna().getLabel())) {
								index = true;
							}
						}
						if (!index) {
							Listitem item = new Listitem(column.getLabel(),
									column);
							idFILZLbxColumnasOrderBy.appendChild(item);
						}
						index = false;
					}
				}
				idFILZLbxColumnasOrderBy.setSelectedIndex(0);
			}
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name cargarColumnsMap
	 * @param idMapper
	 * @desc carga los nombres de las columnas de la vista especificada que se
	 *       mostraran en el filtro.
	 * */
	public void cargarColumnsMap(String idMapper) {
		log.info("Ejecutando el método cargarColumnsMap...");

		try {
			idFILZLbxColumnsMapper.getItems().clear();
			List<Listitem> litems = new LinkedList<Listitem>();
			Listitem item = null;
			for (Component header : listaHeaders) {
				if (!((Listheader) header).hasAttribute("isVisible")) {
					if (((Listheader) header).getChildren().size() > 1) {
						for (Object comp : ((Listheader) header).getChildren()) {
							if (comp instanceof Label) {
								Label label = (Label) comp;
								item = new Listitem(label.getValue());
								item.setSelected(true);
								litems.add(item);
							}
						}
					} else if (((Listheader) header).getChildren().get(0) instanceof Toolbarbutton) {
						item = new Listitem(
								((Toolbarbutton) ((Listheader) header)
										.getLastChild()).getLabel());
						item.setSelected(true);
						litems.add(item);
					}
				}
			}

			obtenerColumnsMapper(idMapper, litems);

			for (ColumnsMapper column : listaColumns) {
				if (!column.getName().startsWith("SEC")) {
					final Listitem i = litems.get(column.getIndex());
					column.setLabel(i.getLabel());
					i.setValue(column);
					idFILZLbxColumnsMapper.appendChild(i);

				} else {
					columnSec = column;
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * 
	 * @name cargarComponentesVista
	 * @desc
	 */
	@Override
	public void cargarComponentesVista() {
		log.info("Ejecutando el método cargarComponentesVista...");

		idFILZLbxColumnsMapper = (Listbox) this
				.getFellow("idFILZLbxColumnsMapper");
		idFILZLbxColumnas = (Listbox) this.getFellow("idFILZLbxColumnas");
		idFILZLbxOperadores = (Listbox) this.getFellow("idFILZLbxOperadores");
		idFILZLbxCondicionesWhere = (Listbox) this
				.getFellow("idFILZLbxCondicionesWhere");
		idFILZLbxConectorLogicos = (Listbox) this
				.getFellow("idFILZLbxConectorLogicos");
		idFILZTbxValor1 = (Textbox) this.getFellow("idFILZTbxValor1");
		idFILZTbxValor2 = (Textbox) this.getFellow("idFILZTbxValor2");
		idFILZIbxValor1 = (Intbox) this.getFellow("idFILZIbxValor1");
		idFILZIbxValor2 = (Intbox) this.getFellow("idFILZIbxValor2");
		idFILZLblAND = (Label) this.getFellow("idFILZLblAND");
		idFILZDbxFecha1 = (Datebox) this.getFellow("idFILZDbxFecha1");
		idFILZDbxFecha2 = (Datebox) this.getFellow("idFILZDbxFecha2");
		idFILZHbxCamposValores = (Hbox) this
				.getFellow("idFILZHbxCamposValores");
		idFILZLgbxValor2 = (Longbox) this.getFellow("idFILZLgbxValor2");
		idFILZLgbxValor1 = (Longbox) this.getFellow("idFILZLgbxValor1");
		idFILZTbxListaValores = (Textbox) this
				.getFellow("idFILZTbxListaValores");
		idFILZImgAgregarLista = (Image) this.getFellow("idFILZImgAgregarLista");
		idFILZLbxColumnasOrderBy = (Listbox) this
				.getFellow("idFILZLbxColumnasOrderBy");
		idFILZLbxOrderBy = (Listbox) this.getFellow("idFILZLbxOrderBy");
		idFILZLbxCondicionesOrderBy = (Listbox) this
				.getFellow("idFILZLbxCondicionesOrderBy");
		idFILZBtnAgregarCondicion = (Button) this
				.getFellow("idFILZBtnAgregarCondicion");
		idFILZBtnEditarCondicion = (Button) this
				.getFellow("idFILZBtnEditarCondicion");
		idFILZBtnCancelar = (Button) this.getFellow("idFILZBtnCancelar");
		idFILZLblOperadorIgual = (Label) this
				.getFellow("idFILZLblOperadorIgual");
		idFILZLblOperadorDiferente = (Label) this
				.getFellow("idFILZLblOperadorDiferente");
		idFILZLblOperadorComience = (Label) this
				.getFellow("idFILZLblOperadorComience");
		idFILZLblOperadorContenga = (Label) this
				.getFellow("idFILZLblOperadorContenga");
		idFILZLblOperadorTermina = (Label) this
				.getFellow("idFILZLblOperadorTermina");
		idFILZLblOperadorMenor = (Label) this
				.getFellow("idFILZLblOperadorMenor");
		idFILZLblOperadorMayor = (Label) this
				.getFellow("idFILZLblOperadorMayor");
		idFILZLblOperadorMenorIgual = (Label) this
				.getFellow("idFILZLblOperadorMenorIgual");
		idFILZLblOperadorMayorIgual = (Label) this
				.getFellow("idFILZLblOperadorMayorIgual");
		idFILZLblOperadorIncluye = (Label) this
				.getFellow("idFILZLblOperadorIncluye");
		idFILZLblOperadorNoIncluye = (Label) this
				.getFellow("idFILZLblOperadorNoIncluye");
		idFILZLblOperadorEntre = (Label) this
				.getFellow("idFILZLblOperadorEntre");
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name cargarCondicionForEditar
	 * @param item
	 * @desc carga los valores de una condicion en los campos para la
	 *       ediciÔøΩÔøΩn
	 */
	public void cargarCondicionForEditar(Listitem item) {
		log.info("Ejecutando el método [ cargarCondicionForEditar ]...");

		itemEdit = item;

		idFILZBtnAgregarCondicion.setVisible(false);
		idFILZBtnEditarCondicion.setVisible(true);
		idFILZLbxColumnas.setDisabled(true);
		idFILZLbxConectorLogicos.setDisabled(true);
		idFILZLbxOperadores.setDisabled(true);
		idFILZBtnCancelar.setVisible(true);

		CriterioWhere cw = (CriterioWhere) item.getValue();

		log.info("CW FOR EDITAR :" + cw);

		if (cw.getOperadorLogico().getValor() == null) {
			idFILZLbxConectorLogicos.setSelectedIndex(0);
		} else if (cw.getOperadorLogico().getValor().equals("AND")) {
			idFILZLbxConectorLogicos.setSelectedIndex(1);
		} else if (cw.getOperadorLogico().getValor().equals("OR")) {
			idFILZLbxConectorLogicos.setSelectedIndex(2);
		}

		idFILZLbxColumnas.getItems().clear();
		idFILZLbxColumnas.appendItem(this.getAttribute("MSG_SELECCIONE")
				.toString(), null);
		int i = 1, pos = 0;
		for (ColumnsMapper column : listaColumns) {
			if (!column.getName().startsWith("SEC")
					&& !column.getType().equals("java.io.InputStream")) {
				Listitem itemColumn = new Listitem(column.getLabel(), column);
				idFILZLbxColumnas.appendChild(itemColumn);
				if (column.getLabel().equals(cw.getColumn().getLabel())) {
					pos = i;
				}

				i++;
			}

		}
		idFILZLbxColumnas.setSelectedIndex(pos);
		cargarTipoDatoMap(cw.getOperador().getLabel());
		mostrarCampos();
		setCampos(true);
		setCampos(cw);

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name cargarTipoDatoMap
	 * @param label
	 * @desccarga los nombres de los operadores, teniendo en cuenta el tipo de
	 *            dato que los soporta
	 */
	public void cargarTipoDatoMap(String label) {
		log.info("Ejecutando el método cargarTipoDatoMap...");

		try {

			index = 0;
			ColumnsMapper column = (ColumnsMapper) idFILZLbxColumnas
					.getSelectedItem().getValue();

			if (column != null) {
				listaOperadores.clear();
				String[] tipoDatos = null;

				if (column.getType().equals("java.lang.String")) {
					tipoDatos = TipoDato.String;
				}

				if (column.getType().equals("java.lang.Integer")) {
					tipoDatos = TipoDato.Integer;
				}

				if (column.getType().equals("java.lang.Long")) {
					tipoDatos = TipoDato.Long;
				}

				if (column.getType().equals("java.util.Date")) {
					tipoDatos = TipoDato.Date;
				}

				if (column.getType().equals("java.math.BigDecimal")) {
					tipoDatos = TipoDato.BigDecimal;
				}

				for (String string : tipoDatos) {
					Operador operador = new Operador(labelTipoDato(string),
							string);
					listaOperadores.add(operador);
				}

				idFILZLbxOperadores.getItems().clear();

				idFILZLbxOperadores.appendItem(
						this.getAttribute("MSG_SELECCIONE").toString(), null);
				int i = 1, pos = 0;

				for (Operador operador : listaOperadores) {
					Listitem item = new Listitem(operador.getLabel(), operador);
					idFILZLbxOperadores.appendChild(item);
					if (operador.getLabel().equals(label)) {
						pos = i;
					}

					i++;

				}
				idFILZLbxOperadores.setSelectedIndex(pos);

			} else {
				idFILZLbxOperadores.getItems().clear();
				idFILZLbxOperadores.appendItem(
						this.getAttribute("MSG_SELECCIONE").toString(), null);
				idFILZLbxOperadores.setSelectedIndex(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name ca rgarValoresCriterioWhere
	 * @desc carga los valores ingresados en la lista que serÔøΩÔøΩn parametros
	 *       para la condicion where
	 */
	public List<String> cargarValoresCriterioWhere() {
		log.info("Ejecutando el método [ cargarValoresCriterioWhere ]...");

		Operador operador = (Operador) idFILZLbxOperadores.getSelectedItem()
				.getValue();

		List<String> listaValoresCriterio = new LinkedList<String>();

		int i = 0;
		log.info("getCampos() ==> " + getCampos());
		while (i <= getCampos().size() - 1) {
			if (getCampos().get(i) instanceof Textbox) {
				Textbox valor = (Textbox) getCampos().get(i);
				if (!valor.getValue().isEmpty()) {
					criterio(operador, valor.getValue(), listaValoresCriterio);
				}
			}
			if (getCampos().get(i) instanceof Intbox) {
				Intbox valor = (Intbox) getCampos().get(i);
				if (valor.getValue() != null) {
					criterio(operador, valor.getValue().toString(),
							listaValoresCriterio);
				}
			}
			if (getCampos().get(i) instanceof Longbox) {
				Longbox valor = (Longbox) getCampos().get(i);
				if (valor.getValue() != null) {
					criterio(operador, valor.getValue().toString(),
							listaValoresCriterio);
				}
			}

			if (getCampos().get(i) instanceof Datebox) {
				Datebox valor = (Datebox) getCampos().get(i);
				if (valor.getValue() != null) {
					criterio(operador, getFormat().format(valor.getValue()),
							listaValoresCriterio);
				}
			}
			i++;
		}

		return listaValoresCriterio;
	}

	/**
	 * construye el SQL
	 */
	public void crearConsultaSQL() {
		log.info("Ejecutando el método [crearConsultaSQL]...");

		SentenciaSQL sentencia = this.setSenteciaSQL();
		log.info("SQL : " + sentencia.getColumnas());
		try {
			StringBuilder sql = new StringBuilder("SELECT ");
			for (ColumnsMapper columnMapper : sentencia.getColumnas()) {
				if (sql.length() == 7) {

					sql.append(columnMapper.getName());
					sql.append("   ");
					sql.append(columnMapper.getName());

				} else {
					sql.append(", ");

					sql.append(columnMapper.getName());
					sql.append("   ");
					sql.append(columnMapper.getName());

				}
			}
			sql.append(" FROM ");
			sql.append(sentencia.getView());

			if (filtroAdicional != null && !filtroAdicional.isEmpty()) {
				sql.append(" WHERE ");
				sql.append(filtroAdicional);
				// sql.append(columnSec.getName());
				// sql.append(" NOT IN ");
				// sql.append(" (SELECT ");
				// sql.append(columnaDetalle);
				// sql.append(" FROM ");
				// sql.append(filtroAdicional);
				// sql.append(" WHERE ");
				// sql.append(columnaMaestro);
				// sql.append(" = ");
				// sql.append(action.getObject());
				// sql.append(" )");
			}

			if ((sentencia.getFiltro() != null && !sentencia.getFiltro()
					.isEmpty())) {

				if (!sql.toString().contains("WHERE")) {
					sql.append(" WHERE ");
				} else {
					sql.append(" AND ");
				}

				for (CriterioWhere criterio : sentencia.getFiltro()) {

					if (criterio.getOperadorLogico().getValor() != null) {
						sql.append(" ");
						sql.append(criterio.getOperadorLogico().getValor());
						sql.append(" ");
					}

					if (criterio.getColumn().getType()
							.equals("java.lang.String")) {
						sql.append(criterio.getColumn().getName());
					} else {
						sql.append(criterio.getColumn().getName());
					}

					sql.append(" ");
					boolean flag = criterio.getColumn().getType()
							.equals("java.lang.String")
							|| criterio.getColumn().getType()
									.equals("java.util.Date");
					if (criterio.getOperador().getValor().equals("BETWEEN")) {
						sql.append(criterio.getOperador().getValor());
						sql.append(" ");

						if (esFecha(criterio.getLista().get(0))) {
							sql.append("TO_DATE('" + criterio.getLista().get(0)
									+ "','dd/MM/yyyy')");
						} else {
							if (flag) {
								sql.append("'");
							}
							sql.append(criterio.getLista().get(0));
							if (flag) {
								sql.append("'");
							}
						}

						log.info("::::::: 0" + criterio.getLista().get(0));

						sql.append(" AND ");
						if (flag) {
							sql.append("'");
						}
						sql.append(criterio.getLista().get(1));
						log.info("::::::: 1" + criterio.getLista().get(1));
						if (flag) {
							sql.append("'");
						}

					} else if (criterio.getOperador().getValor().equals("IN")
							|| criterio.getOperador().getValor()
									.equals("NOT IN")) {
						sql.append(criterio.getOperador().getValor());
						sql.append(" (");
						int i = 1;

						for (String valor : criterio.getLista()) {
							if (flag) {
								sql.append("'");
							}
							sql.append(valor);
							if (flag) {
								sql.append("'");
							}

							if (i < criterio.getLista().size()) {
								sql.append(", ");
							}
							i++;
						}
						sql.append(" ) ");
					} else {
						sql.append(criterio.getOperador().getValor()
								.replaceAll("%", ""));
						sql.append(" ");

						if (criterio.getLista().size() > 0)
							if (esFecha(criterio.getLista().get(0))) {
								sql.append("TO_DATE('"
										+ criterio.getLista().get(0)
										+ "','dd/MM/yyyy')");
							} else {
								if (flag) {
									sql.append("'");
								}
								sql.append(criterio.getLista().get(0));
								if (flag) {
									sql.append("'");
								}
							}
						log.info("Dato... " + criterio.getLista().get(0));

						sql.append(" ");
					}
				}
			}
			// sql.append("AND  ESTADO != 'R'");
			// String sqlAux;
			// if (sentencia.getOrderBy() != null
			// && !sentencia.getOrderBy().isEmpty()) {
			// sql.append(" ORDER BY ");
			//
			// for (OrderBy order : sentencia.getOrderBy()) {
			// sql.append(order.getColumna().getName());
			// sql.append(" ");
			// sql.append(order.getTipoOrden());
			// sql.append(", ");
			// }
			//
			// sqlAux = sql.toString().substring(0,
			// sql.toString().length() - 2);
			// } else {
			// sqlAux = sql.toString();
			//
			// }
			log.info("CONSULTA GENERADA: " + sql.toString());

			action.setSql(sql.toString());
			action.setObject(sentencia);
			action.setAttribute("listado_where", getFiltro());
			action.setAttribute("listado_orderBy", getListaOrderBy());
			action.onListarDetalle();

			onCancelar(0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void restablecer() {
		log.info("Ejecutando el método [ restablecer ]...");

		// Tab: Ordenar Consulta...
		listaOrderBy.clear();
		idFILZLbxCondicionesOrderBy.getItems().clear();

		idFILZLbxColumnasOrderBy.setSelectedIndex(0);
		idFILZLbxOrderBy.setSelectedIndex(0);

		// Tab: Filtrar Consulta...
		filtro.clear();
		idFILZLbxCondicionesWhere.getItems().clear();

		idFILZLbxConectorLogicos.setSelectedIndex(0);
		idFILZLbxConectorLogicos.setDisabled(true);

		idFILZLbxColumnas.setSelectedIndex(0);
		cargarTipoDatoMap(null);
		mostrarCampos();

		idFILZTbxValor1.setText("");
		idFILZDbxFecha1.setValue(null);
		idFILZIbxValor1.setText("");
		idFILZLgbxValor1.setText("");

		idFILZTbxValor2.setText("");
		idFILZDbxFecha2.setValue(null);
		idFILZIbxValor2.setText("");
		idFILZLgbxValor2.setText("");
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name crearListItem
	 * @param criterio
	 * @return
	 * @desc crea una fila en la lista con las condiciones ingresadas
	 */
	public Listitem crearListItem(Object objeto) {
		log.info("Ejecutando el método [ crearListItem ]...");

		final Listitem item = new Listitem();
		Listcell cell = null;

		if (objeto instanceof CriterioWhere) {

			CriterioWhere criterio = (CriterioWhere) objeto;

			if (criterio.getOperadorLogico().getValor() == null) {
				cell = new Listcell("");
			} else {
				cell = new Listcell(criterio.getOperadorLogico().getLabel());
			}

			item.appendChild(cell);

			cell = new Listcell(criterio.getColumn().getLabel());
			item.appendChild(cell);

			cell = new Listcell(criterio.getOperador().getLabel());
			item.appendChild(cell);

			int i = 0;
			String valores = "";
			while (i <= criterio.getLista().size() - 1) {
				valores = valores
						+ criterio.getLista().get(i).replaceAll("%", "");
				if (criterio.getLista().size() - 1 > i) {
					valores = valores + ", ";
				}
				i++;
			}
			cell = new Listcell(valores);
			item.appendChild(cell);

			cell = new Listcell("", IConstantes.IMAGEN_BORRAR);
			cell.setTooltiptext(this.getAttribute("MSG_ELIMINAR").toString());
			cell.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					eliminarCondicion(item, 0);
				}
			});
			item.appendChild(cell);
			item.setValue(criterio);
			item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					cargarCondicionForEditar(item);
				}
			});
		} else {
			OrderBy orden = (OrderBy) objeto;

			cell = new Listcell(orden.getColumna().getLabel());
			item.appendChild(cell);

			cell = new Listcell(orden.getLabel());
			item.appendChild(cell);

			cell = new Listcell("", IConstantes.IMAGEN_BORRAR);
			cell.setTooltiptext(this.getAttribute("MSG_ELIMINAR").toString());
			cell.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					eliminarCondicion(item, 1);
				}
			});
			item.appendChild(cell);
			item.setValue(orden);
		}

		return item;

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name valorValido
	 * @param criterio
	 * @desc valida que el valor pasado como criterio al filtro no contenga los
	 *       carateres especificados en el patron de busquedad
	 */
	public boolean valorValido(String criterio) {
		log.info("Ejecutando el método [ valorValido ] ..." + criterio);

		boolean criterioValido;
		// Pattern patron = Pattern
		// .compile("\\'|\\#|\\!|\\=|\\<|\\>|\\(|\\(|\\?|\\¿");
		Pattern patron = Pattern.compile("\\#|\\!|\\=|\\<|\\>|\\?|\\¿");
		Matcher encaja = patron.matcher(criterio);

		if (encaja.find()) {
			criterioValido = false;
		} else {
			criterioValido = true;
		}
		return criterioValido;

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name criterio
	 * @param operador
	 * @param criterio
	 * @param listaValoresCriterio
	 * @desc agrega los criterios de busqueda a la lista de criterios
	 */
	public void criterio(Operador operador, String criterio,
			List<String> listaValoresCriterio) {
		log.info("Ejecutando el método [ criterio ]... ");

		if (!criterio.isEmpty() && valorValido(criterio)) {
			if (operador.getValor().equals("LIKE%")) {
				criterio = criterio + "%";
			}
			if (operador.getValor().equals("%LIKE")) {
				criterio = "%" + criterio;
			}
			if (operador.getValor().equals("%LIKE%")) {
				criterio = "%" + criterio + "%";
			}
			if (operador.getValor().equals("NOT IN")
					|| operador.getValor().equals("IN")) {
				listaValoresCriterio = valores(criterio, listaValoresCriterio);
			} else {
				listaValoresCriterio.add(criterio.toUpperCase());
			}
			log.info("Tamaño listaValoresCriterio: "
					+ listaValoresCriterio.size());
			log.info("criterio : " + criterio);
		} else {
			this.setMensajeHistoricoGritter(IConstantes.ESTADO_ADVERTENCIA,
					this.getAttribute("MSG_VALOR_NO_VALIDO").toString(), "", "");
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name doModal
	 * @param parametros
	 * @desc permite que la vista para generar Filtros sea visible
	 */
	@SuppressWarnings("unchecked")
	public void doModal(Map<String, Object> parametros) {
		log.info("Ejecutando el método [doModal]...");

		try {
			cargarComponentesVista();
			idView = (String) parametros.get("id");
			action = (IActionListbox) parametros.get("action");
			listaHeaders = (List<Component>) parametros.get("lista_headers");
			cargarFiltros = (Boolean) parametros.get("cargarFiltros");
			filtroAdicional = (String) parametros.get("filtro_adicional");
			filtro = (List<CriterioWhere>) parametros.get("listado_where");
			listaOrderBy = (List<OrderBy>) parametros.get("listado_orderBy");

			listaColumns = new LinkedList<ColumnsMapper>();
			listaOperadores = new LinkedList<Operador>();
			cargarColumnsMap(idView);
			cargarColumnasForCondicion(0);
			cargarColumnasForCondicion(1);

			List<?> ordenamientoIncial = (List<?>) parametros
					.get("ORDENAMIENTO_DEFECTO");
			List<?> criterioWhereInicial = (List<?>) parametros
					.get("CRITERIO_WHERE_DEFECTO");

			log.info("ordenamientoIncial ==> " + ordenamientoIncial);
			if (ordenamientoIncial != null && !ordenamientoIncial.isEmpty()) {
				getListaOrderBy().addAll(
						(Collection<? extends OrderBy>) ordenamientoIncial);
				listarCondicionesOrderBy();
			}
			if (criterioWhereInicial != null && !criterioWhereInicial.isEmpty()) {
				getFiltro()
						.addAll((Collection<? extends CriterioWhere>) criterioWhereInicial);
				listarCondicionesWhere();
			}

			if (cargarFiltros) {
				onCargarCondicionesFiltroBD();
			}
			super.doModal();
		} catch (SuspendNotAllowedException e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_TIEMPO_DE_EJECUCION, e));
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name editar
	 * @desc establece la operaciÔøΩÔøΩn editar
	 */
	public void editar() {
		log.info("Ejecutando el método [ editar ]...");

		if (itemEdit.getIndex() == 0) {
			idFILZLbxConectorLogicos.setDisabled(true);
		} else {
			idFILZLbxConectorLogicos.setDisabled(false);
		}

		idFILZLbxColumnas.setDisabled(false);
		idFILZLbxOperadores.setDisabled(false);
		idFILZBtnAgregarCondicion.setVisible(true);
		idFILZBtnEditarCondicion.setVisible(false);
		idFILZIbxValor1.setReadonly(false);
		idFILZLgbxValor1.setReadonly(false);
		idFILZDbxFecha1.setDisabled(false);
		setCampos(false);
		operacion = "E";

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name eliminarCondicion
	 * @param item
	 * @desc eliminan una fila de la lista de condiciones
	 */
	public void eliminarCondicion(Listitem item, int op) {
		log.info("Ejecutando el método [ eliminarCondicion ]...");

		if (op == 0) {
			idFILZLbxCondicionesWhere.removeChild(item);
			getFiltro().remove(item.getValue());
			listarCondicionesWhere();
			onCancelar(1);
			inicializarListaConectorLogico();
		} else {
			idFILZLbxCondicionesOrderBy.removeChild(item);
			getListaOrderBy().remove(item.getValue());
			listarCondicionesOrderBy();
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name getAssemblerStandard
	 * @return
	 * @desc
	 */
	public AssemblerStandard getAssemblerStandard() {

		if (assemblerStandard == null) {
			assemblerStandard = new AssemblerStandard();
		}

		return assemblerStandard;
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name getBuild
	 * @return build
	 * @descp obtiene el valor de build
	 */
	public StringBuilder getBuild() {
		if (build == null) {
			build = new StringBuilder();
		}
		return build;
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name getCampos
	 * @return campos
	 * @descp obtiene el valor de campos
	 */
	public List<Object> getCampos() {
		if (campos == null) {
			campos = new LinkedList<Object>();
		}
		return campos;
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name getFiltro
	 * @return filtro
	 * @descp obtiene el valor de filtro
	 */
	public List<CriterioWhere> getFiltro() {
		if (filtro == null) {
			filtro = new LinkedList<CriterioWhere>();
		}
		return filtro;
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name getFormat
	 * @return format
	 * @descp obtiene el valor de format
	 */
	public SimpleDateFormat getFormat() {
		if (format == null) {
			format = new SimpleDateFormat("dd/MM/yyyy");
		}
		return format;
	}

	/**
	 * @type método de la clase
	 * 
	 * @name getListaOrderBy
	 * @return listaOrderBy
	 * @descp obtiene el valor de listaOrderBy
	 */
	public List<OrderBy> getListaOrderBy() {
		if (listaOrderBy == null) {
			listaOrderBy = new LinkedList<OrderBy>();
		}
		return listaOrderBy;
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name inicializarListaConectorLogico
	 * @desc inicializa la lista de conectores logicos
	 */
	public void inicializarListaConectorLogico() {
		log.info("Ejecutando el método [ inicializarListaConectorLogico ]...");

		if (idFILZLbxCondicionesWhere.getItemCount() > 0) {
			idFILZLbxConectorLogicos.setDisabled(false);
		} else {
			idFILZLbxConectorLogicos.setDisabled(true);
			idFILZLbxConectorLogicos.setSelectedIndex(0);
		}
	}

	public String labelTipoDato(String label) {

		if (label.equals("LIKE") || label.equals("=")) {
			return idFILZLblOperadorIgual.getValue();
		}

		if (label.equals("NOT LIKE") || label.equals("!=")) {
			return idFILZLblOperadorDiferente.getValue();
		}

		if (label.equals("LIKE%")) {
			return idFILZLblOperadorComience.getValue();
		}

		if (label.equals("%LIKE")) {
			return idFILZLblOperadorTermina.getValue();
		}

		if (label.equals("%LIKE%")) {
			return idFILZLblOperadorContenga.getValue();
		}

		if (label.equals("<")) {
			return idFILZLblOperadorMenor.getValue();
		}

		if (label.equals(">")) {
			return idFILZLblOperadorMayor.getValue();
		}

		if (label.equals("<=")) {
			return idFILZLblOperadorMenorIgual.getValue();
		}

		if (label.equals(">=")) {
			return idFILZLblOperadorMayorIgual.getValue();
		}

		if (label.equals("IN")) {
			return idFILZLblOperadorIncluye.getValue();
		}

		if (label.equals("NOT IN")) {
			return idFILZLblOperadorNoIncluye.getValue();
		}

		return idFILZLblOperadorEntre.getValue();
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name listarCondicionesOrderBy
	 * @desc lista las condiciones ingresadas para el orden por alguna columna
	 *       de la consulta
	 */
	public void listarCondicionesOrderBy() {
		log.info("Ejecutando el método [ listarCondicionesOrderBy ]...");

		try {

			idFILZLbxCondicionesOrderBy.getItems().clear();
			for (OrderBy orden : getListaOrderBy()) {
				idFILZLbxCondicionesOrderBy.appendChild(crearListItem(orden));
			}

			cargarColumnasForCondicion(2);

		} catch (Exception e) {
			e.getStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name listarCondiciones
	 * @desc lista las condiciones ingresadas
	 */
	public void listarCondicionesWhere() {
		log.info("Ejecutando el método [ listarCondicionesWhere ]...");

		try {
			idFILZLbxCondicionesWhere.getItems().clear();

			CriterioWhere criterio;
			String valorFiltro;
			if (getFiltro().size() > 0) {
				for (int i = 0; i < getFiltro().size(); i++) {
					criterio = (CriterioWhere) getFiltro().get(i);
					for (int j = 0; j < criterio.getLista().size(); j++) {
						log.info("criterio.getLista().get(j)==> "
								+ criterio.getLista().get(j) + " j " + j);
						valorFiltro = criterio.getLista().get(j);
						if (valorValido(valorFiltro)) {
							if (getFiltro().get(0).getOperadorLogico()
									.getValor() != null) {
								criterio.setOperadorLogico(new Operador(
										criterio.getOperadorLogico().getLabel(),
										null));
								getFiltro().set(0, criterio);
							}
						}
					}
					idFILZLbxCondicionesWhere
							.appendChild(crearListItem(criterio));
				}
			}

			if (idFILZLbxCondicionesWhere.getItemCount() > 0) {
				idFILZLbxConectorLogicos.setDisabled(false);
			}

			this.index = 0;
			getBuild().setLength(0);
			Utilidades.limpiarCampos(idFILZHbxCamposValores);

		} catch (Exception e) {
			e.getStackTrace();
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name mostrarCampos
	 * @desc permite mostrar los campos teniendo en cuanta el operador
	 *       seleccionado
	 */
	public void mostrarCampos() {
		log.info("Ejecutando el método [ mostrarCampos ]...");

		try {

			Utilidades.limpiarCampos(idFILZHbxCamposValores);
			getCampos().clear();
			Operador operador = (Operador) idFILZLbxOperadores
					.getSelectedItem().getValue();
			ColumnsMapper column = (ColumnsMapper) idFILZLbxColumnas
					.getSelectedItem().getValue();

			log.info("OPERADOR ==> "
					+ idFILZLbxOperadores.getSelectedItem().getValue());

			log.info("Columna ==> "
					+ idFILZLbxColumnas.getSelectedItem().getValue());

			if (operador != null) {
				this.setVisible();
				if (column.getType().equals("java.lang.String")) {
					idFILZTbxValor1.setVisible(true);
					getCampos().add(idFILZTbxValor1);
				}
				if (column.getType().equals("java.lang.Integer")) {
					if (operador.getValor().equals("NOT IN")
							|| operador.getValor().equals("IN")) {
						idFILZIbxValor1.setVisible(true);
						idFILZImgAgregarLista.setVisible(true);
						idFILZTbxListaValores.setVisible(true);
						getCampos().add(idFILZTbxListaValores);

					} else if (operador.getValor().equals("BETWEEN")) {
						idFILZIbxValor1.setVisible(true);
						idFILZIbxValor2.setVisible(true);
						idFILZLblAND.setVisible(true);
						getCampos().add(idFILZIbxValor1);
						getCampos().add(idFILZIbxValor2);
					} else {
						idFILZIbxValor1.setVisible(true);
						getCampos().add(idFILZIbxValor1);
					}
				}

				if (column.getType().equals("java.lang.Long")) {
					if (operador.getValor().equals("NOT IN")
							|| operador.getValor().equals("IN")) {
						idFILZLgbxValor1.setVisible(true);
						idFILZImgAgregarLista.setVisible(true);
						idFILZTbxListaValores.setVisible(true);
						getCampos().add(idFILZTbxListaValores);
					} else if (operador.getValor().equals("BETWEEN")) {
						idFILZLgbxValor1.setVisible(true);
						idFILZLgbxValor2.setVisible(true);
						idFILZLblAND.setVisible(true);
						getCampos().add(idFILZLgbxValor1);
						getCampos().add(idFILZLgbxValor2);
					} else {
						idFILZLgbxValor1.setVisible(true);
						getCampos().add(idFILZLgbxValor1);
					}
				}

				if (column.getType().equals("java.util.Date")) {

					if (operador.getValor().equals("BETWEEN")) {
						idFILZDbxFecha1.setVisible(true);
						idFILZDbxFecha2.setVisible(true);
						idFILZLblAND.setVisible(true);
						getCampos().add(idFILZDbxFecha1);
						getCampos().add(idFILZDbxFecha2);
					} else if (operador.getValor().equals("NOT IN")
							|| operador.getValor().equals("IN")) {
						idFILZDbxFecha1.setVisible(true);
						idFILZImgAgregarLista.setVisible(true);
						idFILZTbxListaValores.setVisible(true);
						getCampos().add(idFILZTbxListaValores);
					} else {
						idFILZDbxFecha1.setVisible(true);
						getCampos().add(idFILZDbxFecha1);
					}
				}

				if (column.getType().equals("java.math.BigDecimal")) {
					if (operador.getValor().equals("NOT IN")
							|| operador.getValor().equals("IN")) {
						idFILZIbxValor1.setVisible(true);
						idFILZImgAgregarLista.setVisible(true);
						idFILZTbxListaValores.setVisible(true);
						getCampos().add(idFILZTbxListaValores);

					} else if (operador.getValor().equals("BETWEEN")) {
						idFILZIbxValor1.setVisible(true);
						idFILZIbxValor2.setVisible(true);
						idFILZLblAND.setVisible(true);
						getCampos().add(idFILZIbxValor1);
						getCampos().add(idFILZIbxValor2);
					} else {
						idFILZIbxValor1.setVisible(true);
						getCampos().add(idFILZIbxValor1);
					}
				}
			} else {
				this.setVisible();
				idFILZTbxValor1.setVisible(true);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	/**
	 * @param idMapper
	 * @param litems
	 *            metÔøΩÔøΩÔøΩdo que permite obtener los columnsMapper de los
	 *            dto's
	 */
	public void obtenerColumnsMapper(String idMapper, List<Listitem> litems) {
		log.info("Ejecutando el método obtenerColumnsMapper...");

		try {
			view = DTOMapper.getInstance().getNameViewById(idMapper, "J");

			listaColumns = DTOMapper.getInstance().getListColumnsById(idMapper);
			for (int i = 0; i < listaColumns.size(); i++) {
				ColumnsMapper column = listaColumns.get(i);
				if (!column.getName().startsWith("SEC")
						&& column.getIndex() >= litems.size()) {
					listaColumns.remove(i);
					i = -1;
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}

	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name onCancelar
	 * @desc cierra la vista donde se generan los Filtros
	 */
	public void onCancelar(int op) {
		log.info("Ejecutando el método [ onCancelar ]...");

		if (op == 0) {
			this.detach();
		} else {
			idFILZLbxColumnas.setDisabled(false);
			idFILZLbxConectorLogicos.setDisabled(false);
			idFILZLbxOperadores.setDisabled(false);
			idFILZBtnAgregarCondicion.setVisible(true);
			idFILZBtnEditarCondicion.setVisible(false);
			setCampos(false);
			idFILZTbxValor1.setReadonly(false);
			idFILZBtnCancelar.setVisible(false);
			operacion = "N";
			this.cargarColumnasForCondicion(0);
			this.cargarTipoDatoMap(null);
			idFILZLbxConectorLogicos.setSelectedIndex(0);
			this.mostrarCampos();
		}
	}

	@SuppressWarnings("unchecked")
	public void onCargarCondicionesFiltroBD() {
		log.info("Ejecutando el método onCargarCondicionesFiltroBD...");

		try {
			List<String> listaValoresCriterio = null;

			CriterioWhere criterio = null;
			String con = null;
			cargarTipoDatoMap(null);

			List<LNTCnFiltro> filtros = (List<LNTCnFiltro>) ParametrizacionFac
					.getFacade().obtenerListado(
							"LN_obtenerListadoCnFiltroDinamico",
							action.getObject());

			for (int i = 0; i < filtros.size(); i++) {
				LNTCnFiltro lntCnFiltro = filtros.get(i);
				if (lntCnFiltro.getOrden() >= 1) {
					for (ColumnsMapper column : listaColumns) {
						if (column.getName().equals(
								lntCnFiltro.getNombreColumna())) {
							if (lntCnFiltro.getCondicion().equals("ASC")
									|| lntCnFiltro.getCondicion()
											.equals("DESC")) {
								OrderBy ordenBy = new OrderBy(column,
										lntCnFiltro.getCondicion(),
										setLabelOrderBy(lntCnFiltro
												.getCondicion()));
								getListaOrderBy().add(ordenBy);
							} else {
								Operador op = new Operador(
										labelTipoDato(lntCnFiltro
												.getCondicion()),
										lntCnFiltro.getCondicion());

								if (lntCnFiltro.getConector() != null) {
									con = lntCnFiltro.getConector().equals("Y") ? "AND"
											: "OR";
								}

								Operador conector = new Operador(
										setLabelConector(con), con);

								if (op.getValor().equals("BETWEEN")) {
									valores(lntCnFiltro.getValor(),
											listaValoresCriterio = new LinkedList<String>());
								} else {
									criterio(
											op,
											lntCnFiltro.getValor(),
											listaValoresCriterio = new LinkedList<String>());
								}
								criterio = new CriterioWhere(column, op,
										conector, listaValoresCriterio);
								getFiltro().add(criterio);
							}

						}
					}
				}
			}
			listarCondicionesWhere();
			listarCondicionesOrderBy();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name setCampos
	 * @param criterios
	 * @desc establece el estado activo y visible de los campos segÔøΩÔøΩn sea
	 *       la operaciÔøΩÔøΩn.
	 */
	public void setCampos(boolean estado) {
		log.info("Ejecutando el método [ setCampos ]  ESTABLECER ESTADO ACTIVO O VISIBLE...");

		log.info("Campos : " + campos);
		Operador operador = new Operador();

		if (idFILZLbxOperadores.getSelectedItem() != null)
			operador = (Operador) idFILZLbxOperadores.getSelectedItem()

			.getValue();
		ColumnsMapper column = (ColumnsMapper) idFILZLbxColumnas
				.getSelectedItem().getValue();
		if (operador != null && column != null) {
			if (operador.getValor().equals("NOT IN")
					|| operador.getValor().equals("IN")) {
				if (column.getType().equals("java.lang.Integer")) {
					idFILZIbxValor1.setVisible(!estado);
				}
				if (column.getType().equals("java.lang.Long")) {
					idFILZLgbxValor1.setVisible(!estado);
				}
				if (column.getType().equals("java.util.Date")) {
					idFILZDbxFecha1.setVisible(!estado);
				}

				idFILZImgAgregarLista.setVisible(!estado);
			}
		}
		if (campos != null) {
			for (Object camp : campos) {
				if (camp instanceof Textbox) {
					Textbox valor = (Textbox) camp;
					valor.setReadonly(estado);
				}
				if (camp instanceof Intbox) {
					Intbox valor = (Intbox) camp;
					valor.setReadonly(estado);
				}
				if (camp instanceof Longbox) {
					Longbox valor = (Longbox) camp;
					valor.setReadonly(estado);
				}

				if (camp instanceof Datebox) {
					Datebox valor = (Datebox) camp;
					valor.setDisabled(estado);
				}

			}
			idFILZTbxListaValores.setReadonly(true);
		}
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name setCampos
	 * @desc establece los valores de una condiciÔøΩÔøΩn que se mostrarÔøΩÔøΩn
	 *       en los campos.
	 */
	public void setCampos(CriterioWhere cw) {
		log.info("Ejecutando el método [ setCampos ] MOSTRAR VALORES EN EL CAMPO...");

		try {
			int i = 0;
			for (Object camp : campos) {

				if (camp instanceof Textbox) {
					Textbox valor = (Textbox) camp;
					valor.setValue(cw.getLista().toString()
							.replaceAll("[%|\\[|\\]]", ""));
				}
				if (camp instanceof Intbox) {
					Intbox valor = (Intbox) camp;
					valor.setValue(new Integer(cw.getLista().get(i)));
				}
				if (camp instanceof Longbox) {
					Longbox valor = (Longbox) camp;
					valor.setValue(new Long(cw.getLista().get(i)));
				}

				if (camp instanceof Datebox) {
					Datebox valor = (Datebox) camp;
					log.info(cw.getLista() + " " + cw.getLista().size());
					valor.setValue(getFormat().parse(cw.getLista().get(i)));

				}
				i++;
			}
		} catch (WrongValueException e) {
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_VALOR_INCORRRECTO, e));
			log.error(e.getMessage(), e);
		} catch (ParseException e) {
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_TIEMPO_DE_EJECUCION, e));
			log.error(e.getMessage(), e);
		}
	}

	public String setLabelConector(String value) {

		if (value == null) {
			return null;
		}

		if (value.equals("AND")) {
			return idFILZLbxConectorLogicos.getItemAtIndex(1).getLabel();
		}

		return idFILZLbxConectorLogicos.getItemAtIndex(2).getLabel();
	}

	public String setLabelOrderBy(String value) {

		if (value.equals("ASC")) {
			return idFILZLbxOrderBy.getItemAtIndex(0).getLabel();
		}

		return idFILZLbxOrderBy.getItemAtIndex(1).getLabel();
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name crearDTOReporte
	 * @return filtro
	 * @desc crea el DTO para el filtro
	 */

	public SentenciaSQL setSenteciaSQL() {
		log.info("Ejecutando el método setSenteciaSQL...");

		SentenciaSQL sentencia = null;

		Set<Listitem> listaSeleccionados = idFILZLbxColumnsMapper
				.getSelectedItems();
		listaColumns.clear();
		listaColumns.add(columnSec);
		for (Iterator<Listitem> iterator = listaSeleccionados.iterator(); iterator
				.hasNext();) {
			ColumnsMapper columnMapper = (ColumnsMapper) iterator.next()
					.getValue();
			listaColumns.add(columnMapper);
		}

		sentencia = new SentenciaSQL();
		sentencia.setColumnas(listaColumns);
		sentencia.setView(view);

		if (!getFiltro().isEmpty()) {
			sentencia.setFiltro(getFiltro());
		}

		log.info("filtro : " + getFiltro());

		if (!getListaOrderBy().isEmpty()) {
			sentencia.setOrderBy(listaOrderBy);
		}

		log.info("orderBy : " + getListaOrderBy());

		return sentencia;
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name setVisible
	 * @desc coloca todos los componentes de entrada de datos no visibles
	 */
	public void setVisible() {
		log.info("Ejecutando el método [ setVisible ]...");

		idFILZTbxValor1.setVisible(false);
		idFILZTbxValor2.setVisible(false);
		idFILZDbxFecha1.setVisible(false);
		idFILZDbxFecha2.setVisible(false);
		idFILZIbxValor1.setVisible(false);
		idFILZIbxValor2.setVisible(false);
		idFILZLgbxValor2.setVisible(false);
		idFILZLgbxValor1.setVisible(false);
		idFILZLblAND.setVisible(false);
		idFILZImgAgregarLista.setVisible(false);
		idFILZTbxListaValores.setVisible(false);
		getBuild().setLength(0);
	}

	/**
	 * @type método de la clase VentanaFiltroMaestroAction.java
	 * @name valores
	 * @param valor
	 * @param listaValoresCriterio
	 * @return
	 * @desc devuelve una lista con los valores ingresados sin los espacios
	 *       entre ellos
	 */
	public List<String> valores(String valor, List<String> listaValoresCriterio) {
		log.info("Ejecutando el método [ valores ]...");

		StringTokenizer token = new StringTokenizer(valor, ", ");

		while (token.hasMoreTokens()) {
			valor = token.nextToken();
			if (valorValido(valor)) {
				listaValoresCriterio.add(valor.toUpperCase());
			}
		}
		return listaValoresCriterio;
	}

	/**
	 * @desc valida si la cadena pasada como parametro es una fecha
	 * @param String
	 *            cadena
	 * @return boolean true si el valor es una fecha sino false
	 */
	public boolean esFecha(String valor) {
		log.info("Ejecutando el método [esFecha] " + valor);

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

}
