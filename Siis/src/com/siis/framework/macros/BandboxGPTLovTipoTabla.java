package com.casewaresa.framework.macros;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.util.Utilidades;
import com.casewaresa.iceberg_gp.dto.GPTLov;
import com.casewaresa.iceberg_gp.dto.GPTLovDetalle;
import com.casewaresa.iceberg_no.componet.BandboxNOTLovTabla;

public class BandboxGPTLovTipoTabla extends Bandbox {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4712920055013486772L;

		private Logger log = Logger.getLogger(BandboxNOTLovTabla.class);
		/**
		 * Contenedor del Formulario interno flotante
		 */
		private Bandpopup bandpopup = new Bandpopup();
		/**
		 * Contenedor del Formulario interno
		 */
		private Groupbox groupbox = new Groupbox();
		/**
		 * Label de Consulta
		 */
		private Caption caption = new Caption();
		/**
		 * Contenedor de formulario de Filtro
		 */
		private Grid grid = new Grid();
		/**
		 * Filas del contenedor de formulario
		 */
		private Rows rows = new Rows();
		/**
		 * Label del Criterio de Busqueda
		 */
		private Label labelCriterio = new Label();
		/**
		 * Zona de Criterio
		 */
		private Row zonaCriterio = new Row();
		/**
		 * Lista desplegable para seleccionar Criterio de Busqueda
		 */
		private Listbox listCriterio = new Listbox();
		/**
		 * Zona para ingresar el texto a filtrar por criterio seleccionado
		 */
		private Row zonaCriterioText = new Row();
		/**
		 * Label de Buscar
		 */
		private Label labelBuscar = new Label();
		/**
		 * Componente agrupador
		 */
		private Hbox hbox = new Hbox();
		/**
		 * Componente que contiene el texto a filtrar
		 */
		private Textbox textboxCriterio = new Textbox();
		/**
		 * Boton para ejecutar la accion de busqueda
		 */
		private Button buttonBuscar = new Button();
		/**
		 * Columnas del detalle
		 */
		private Columns columns = new Columns();
		private Column column = new Column();
		/**
		 * Contenedor del listado
		 */
		private Groupbox groupboxListado = new Groupbox();
		private Caption captionEtiqueta = new Caption();
		/**
		 * Componente qeu muestra el listado
		 */
		private Listbox listboxMaestro = new Listbox();
		private Listhead listhead = new Listhead();
		/**
		 * Componente oculto que guarda el valor seleccionado en la lista
		 * desplegable
		 */
		private Textbox textboxSecuenciaObjetoSeleccionado = new Textbox();

		/**
		 * 
		 */
		private GPTLov lov;

		public BandboxGPTLovTipoTabla(GPTLov lov) {
			log.info("Ejecutando M�todo [ BandboxGPTLovTipoTabla ] ");

			try {
				/* Construcci�n del componente interno */
				textboxSecuenciaObjetoSeleccionado.setVisible(false);

				this.setHflex("true");
				this.setStyle("text-transform:uppercase;");

				bandpopup.setWidth("400px");
				bandpopup.appendChild(textboxSecuenciaObjetoSeleccionado);

				groupbox.setClosable(false);

				groupbox.appendChild(caption);

				column.setWidth("60px");
				columns.appendChild(column);
				grid.appendChild(columns);

				labelCriterio.setValue("Criterio ");

				zonaCriterio.appendChild(labelCriterio);

				listCriterio.setMold("select");

				zonaCriterio.appendChild(listCriterio);

				rows.appendChild(zonaCriterio);

				labelBuscar = new Label("Buscar");

				zonaCriterioText.appendChild(labelBuscar);

				textboxCriterio = new Textbox();
				// textboxCriterio.setStyle("text-transform:uppercase;");
				textboxCriterio.setWidth("200px");

				buttonBuscar.setImage("imagenes/buscar.gif");

				hbox.appendChild(textboxCriterio);

				hbox.appendChild(buttonBuscar);

				zonaCriterioText.appendChild(hbox);

				rows.appendChild(zonaCriterioText);

				grid.appendChild(rows);

				groupbox.appendChild(grid);

				bandpopup.appendChild(groupbox);

				bandpopup.appendChild(new Separator());

				groupboxListado.setClosable(false);

				groupboxListado.appendChild(captionEtiqueta);

				listboxMaestro.setMold("paging");
				listboxMaestro
						.setPageSize(IConstantes.TAMANO_PAGINACION_BANDBOX);

				listhead.setSizable(false);
				listboxMaestro.appendChild(listhead);

				groupboxListado.appendChild(listboxMaestro);
				bandpopup.appendChild(groupboxListado);

				bandpopup.setHflex("min");

				this.appendChild(bandpopup);

				this.parametrizarEventosVista();

				this.lov = lov;
				this.setReadonly(true);
				this.setConstraint("no empty");

				this.configurar();
			} catch (Exception e) {
				Utilidades.lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			}
		}

		private void parametrizarEventosVista() throws Exception {
			log.info("parametrizarEventosVista");
			buttonBuscar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {

							onClickButtonBuscar();
						}
					});

			listboxMaestro.addEventListener(Events.ON_SELECT,
					new EventListener<SelectEvent<Listitem, Object>>() {

						@Override
						public void onEvent(SelectEvent<Listitem, Object> arg0)
								throws Exception {

							seleccionarValor(arg0.getReference());

						}
					});

		}

		protected void seleccionarValor(Listitem reference) {
			log.info("seleccionarValor");
			log.debug("reference ==>" + reference);
			try {
				StringBuffer value = new StringBuffer();

				for (Component component : reference.getChildren()) {

					value.append(((Listcell) component).getLabel()).append(" ");
				}
				this.setText(value.toString());
				this.close();
			} catch (Exception e) {
				Utilidades.lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			}

		}

		public Object getValorSeleccionado() throws Exception {
			log.info("getValorSeleccionado");
			if (listboxMaestro.getSelectedItem() != null)
				return listboxMaestro.getSelectedItem().getValue();
			else
				return null;

		}

		protected void onClickButtonBuscar() {
			log.info("onClickButtonBuscar");
			try {
				listboxMaestro.getItems().clear();
				PreparedStatement preparedStatement = construirSentenciaSQL();
				if (preparedStatement.execute()) {
					pintarItemsMaestro(preparedStatement);
				}
			} catch (Exception e) {
				Utilidades.lanzarExcepcion(new Excepcion(
						IExcepcion.EXCEPCION_GENERAL, e));
			}

		}

		/**
		 * @param preparedStatement
		 * @throws SQLException
		 */
		private void pintarItemsMaestro(PreparedStatement preparedStatement)
				throws SQLException {
			log.info("pintarItemsMaestro");
			log.debug("preparedStatement ==>" + preparedStatement);
			ResultSet resultSet = preparedStatement.getResultSet();

			while (resultSet.next()) {
				Listitem listitem = new Listitem();
				for (GPTLovDetalle lovDetalle : lov.getListaDetalles()) {

					listitem.appendChild(new Listcell(resultSet.getObject(
							lovDetalle.getCodigo()).toString()));

					if (lovDetalle.getRetorno().equals("S")) {
						listitem.setValue(resultSet.getObject(
								lovDetalle.getCodigo()).toString());
					}
				}
				listboxMaestro.appendChild(listitem);
			}
		}

		/**
		 * @return
		 * @throws WrongValueException
		 * @throws Exception
		 */
		private PreparedStatement construirSentenciaSQL()
				throws WrongValueException, Exception {
			log.info("construirSentenciaSQL");
			String sql= new SQL() {{			
			
			
			for (GPTLovDetalle lovDetalle : lov.getListaDetalles()) {
				 
				SELECT(lovDetalle.getCodigo());
			}

			FROM(lov.getTablaBase());

			if (lov.getFiltroTabla() != null && !lov.getFiltroTabla().isEmpty()) {
				WHERE(lov.getFiltroTabla());
			}

			if (listCriterio.getSelectedItem() != null) {
				AND();
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer
						.append("TO_CHAR(")
						.append(listCriterio.getSelectedItem().getValue()
								.toString()).append(") ");
				stringBuffer.append("LIKE ");

				stringBuffer.append("'%").append(textboxCriterio.getValue())
						.append("%'");
				WHERE(stringBuffer.toString());
			}
			}}.toString();

			PreparedStatement preparedStatement = (PreparedStatement) ParametrizacionFac
					.getFacade().validarSQL(sql);
			return preparedStatement;
		}

		private void configurar() throws Exception {
			log.info("configurar");
			listCriterio.getItems().clear();
			listhead.getChildren().clear();
			for (GPTLovDetalle lovDetalle : lov.getListaDetalles()) {
				Listitem listitem = new Listitem(lovDetalle.getEtiqueta(),
						lovDetalle.getCodigo());

				listCriterio.appendChild(listitem);

				Listheader listheader = new Listheader(lovDetalle.getEtiqueta());

				listhead.appendChild(listheader);
				if (lovDetalle.getColumnaOculta().equals("S")) {
					listitem.setVisible(false);
					listheader.setVisible(false);
				}
			}
		}

		public GPTLov getLov() {
			return lov;
		}

		public void setLov(GPTLov lov) {
			this.lov = lov;
		}

	}