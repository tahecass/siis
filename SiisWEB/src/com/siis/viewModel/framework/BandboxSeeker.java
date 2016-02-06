package com.siis.viewModel.framework;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Bean;
import com.siis.dto.Indicador;

public class BandboxSeeker extends HtmlMacroComponent implements IdSpace {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1474207022607568658L;
	private org.zkoss.zul.Bandbox bandbox;
	public List<Bean> listado;
	public Bean objetoSeleccionado;

	@Wire
	private Textbox texboxCriterio;
	@Wire
	private Listbox listboxResultado;

	private String consulta;
	private Bean objeto;
	public Conexion con;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");
		con = new Conexion();
		listado = new ArrayList<Bean>();

	}

//	 public BandboxSeeker() {
//		compose();
//
//		Selectors.wireVariables(this, this, null);
//		Selectors.wireComponents(this, this, false);
//		Selectors.wireEventListeners(this, this);
//		con = new Conexion();
//		listado = new ArrayList<Bean>();
//	}
	@SuppressWarnings("unchecked")
	private void listar(String criterio) {

		try {
			Bean obj = getObjeto();

			if (!criterio.isEmpty())
				obj.setCodigo("%" + criterio + "%");

			setListado((List<Bean>) con.obtenerListado(this.getConsulta(), obj));
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public Bean getObjeto() {
		return objeto;
	}

	public void setObjeto(Bean objeto) {
		this.objeto = objeto;
	}

	@Command()
	public void onConsultar(@BindingParam("criterio") String criterio) {
		listar(criterio);
	}

	// private void consultar(String criterio) {
	//
	// try {
	// Conexion con = new Conexion();
	// Bean obj = getObjeto();
	// obj.setCodigo("%" + criterio + "%");
	// List<Bean> listaDatos = (List<Bean>) con.listar(getConsulta(), obj);
	// Utilidades util = new Utilidades();
	//
	// if (listboxResultado.getChildren() != null)
	// listboxResultado.getChildren().clear();
	//
	// System.out.println("Criterio de Busq: " + obj.getCodigo());
	// for (final Bean p : listaDatos) {
	// System.out.println(p.getCodigo() + " - " + p.getNombre());
	// Listitem listItem = util.crearListitem(p, p.getCodigo(), p.getNombre());
	//
	// listItem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
	// public void onEvent(Event event) throws Exception {
	//
	// onSeleccionar(p);
	//
	// }
	//
	// });
	// listboxResultado.appendChild(listItem);
	//
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// }
	// }

	@Command
	private void onSeleccionar(@BindingParam("seleccionado") Bean p) {
		System.out.println("Ejecutando [ onSeleccionar ]");

		bandbox.setValue(p.getCodigo().concat(" - ").concat(p.getNombre()));
		this.setAttribute("OBJETO_SELECCIONADO", p);
	}

	public List<Bean> getListado() {
		return listado;
	}

	public void setListado(List<Bean> listado) {
		this.listado = listado;
	}

	public Bean getObjetoSeleccionado() {
		return objetoSeleccionado;
	}

	public void setObjetoSeleccionado(Bean objetoSeleccionado) {
		this.objetoSeleccionado = objetoSeleccionado;
	}

}
