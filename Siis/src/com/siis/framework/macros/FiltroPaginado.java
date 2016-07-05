package com.casewaresa.framework.macros;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.assembler.AssemblerStandard;
import com.casewaresa.framework.dto.RowFiltro;
import com.casewaresa.iceberg_fa.action.FAZTarifarioVigenciaVersion2Action;

public class FiltroPaginado extends Window implements AfterCompose,
		IInicializarComponentes {

	private static final long serialVersionUID = 6257470411055794200L;
	public Logger log = Logger.getLogger(this.getClass());

	private Map<String, Object> parametros;

	private AssemblerStandard assembler;
	private Rows idFiltroPagingZRowsFormulario;

	List<RowFiltro> label = new LinkedList<RowFiltro>();

	FAZTarifarioVigenciaVersion2Action padre;

	@Override
	public void afterCompose() {
		// TODO Auto-generated method stub
		cargarComponentesVista();
		initOtherParameter();
		cargarForma();
	}

	@Override
	public void cargarComponentesVista() {

		idFiltroPagingZRowsFormulario = (Rows) this
				.getFellow("idFiltroPagingZRowsFormulario");

	}

	@SuppressWarnings("unchecked")
	public void initOtherParameter() {
		log.info("Ejecutando el metodo[initOtherParameter]");

		this.parametros = (Map<String, Object>) Executions.getCurrent()
				.getArg();
		padre = (FAZTarifarioVigenciaVersion2Action) this.parametros
				.get("PADRE");
		label = (List<RowFiltro>) this.parametros.get("CAMPOS");
	}

	public void cargarForma() {
		log.info("Ejecutando el metodo [cargarForma...]");
		try {

			for (RowFiltro r : label) {

				Row row = new Row();
				row.appendChild(new Label(r.getLabel()));
				if (r.getTipoDato().equals("fecha")) {
					Datebox datebox = new Datebox();
					datebox.setId(r.getId());
					row.appendChild(datebox);
				} else if (r.getTipoDato().equals("numerico")) {
					Intbox doubleBox = new Intbox();
					doubleBox.setId(r.getId());
					row.appendChild(doubleBox);
				} else {
					Textbox text = new Textbox();
					text.setId(r.getId());
					text.setStyle("text-transform:uppercase");
					row.appendChild(text);
				}

				idFiltroPagingZRowsFormulario.appendChild(row);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public AssemblerStandard getAssemblerStandard() {
		if (assembler == null) {
			assembler = new AssemblerStandard();
		}
		return assembler;
	}

	public void onFiltrar() {
		log.info("Ejecutando el metodo onFiltrar...");

		Map<String, Object> parametrosFiltro = new HashMap<String, Object>();
		List<Component> componentes = idFiltroPagingZRowsFormulario
				.getChildren();
		String key = "", value = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		for (Component comp : componentes) {

			if (comp instanceof Row) {
				List<Component> hijos = comp.getChildren();
				for (Component hijo : hijos) {
					value = "";
					if (hijo instanceof Datebox) {
						key = ((Datebox) hijo).getId();
						if (((Datebox) hijo).getValue() != null)
							value = format
									.format((((Datebox) hijo).getValue()));

					} else if (hijo instanceof Intbox) {

						key = ((Intbox) hijo).getId();
						if (((Intbox) hijo).getValue() != null
								&& !((Intbox) hijo).getValue().toString()
										.isEmpty())
							value = (((Intbox) hijo).getValue()).toString();

					} else if (hijo instanceof Textbox) {
						key = ((Textbox) hijo).getId();
						if (((Textbox) hijo).getValue() != null
								&& !((Textbox) hijo).getValue().toString()
										.isEmpty())
							value = "%" + (((Textbox) hijo).getValue()).toUpperCase() + "%";

					}
				}
				log.info("Key==> " + key + " Value=>> " + value);
				parametrosFiltro.put(key, value);
			}
		}
		this.detach();
		padre.onListarDetalle(parametrosFiltro);
	}
}