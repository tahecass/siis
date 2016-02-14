package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Bean;
import com.siis.dto.Indicador;
import com.siis.dto.Unidad;
import com.siis.dto.Usuario;
import com.siis.viewModel.framework.BandboxSeeker;

public class IndicadorViewModel {
	public List<Indicador> listaIndicador;
	public Conexion con;
	public Indicador indicadorSeleccionado;
	List<Bean> listado;
	 Bean objetoSeleccionado;

	@Wire
	Listbox idCCTPOCONVBECTABZLbxDetalleTipoConvenioBeca;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");

		con = new Conexion();
		listaIndicador = new ArrayList<Indicador>();
		listarIndicador();

	}

	@SuppressWarnings("unchecked")
	private void listarIndicador() {
		Map<String, Object> parametros = new HashMap<String, Object>();

		try {
			setListaIndicador((List<Indicador>) con.obtenerListado("listarIndicadores", parametros));
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Command
	public void parametrizarBandboxSeeker(@BindingParam("banbox") BandboxSeeker idUsBbxSecResponsable) {
		System.out.println("[ parametrizarBandboxSeeker] ");
//		idUsBbxSecResponsable.setObjeto(new Indicador());
//		idUsBbxSecResponsable.setConsulta("listarUsuarios");
	}

	@Command
	public void onSeleccionar(@BindingParam("objetoSeleccionado") Indicador objetoSeleccionado) {
		System.out.println(objetoSeleccionado.getNombre());
	}

	@NotifyChange("listaIndicador")
	@Command
	public void onAgregar() {

		ListModelList<Indicador> lm = new ListModelList<Indicador>(Arrays.asList(
				new Indicador(0, new Usuario(), "", new Long(0L), new Long(0L), new Long(0L), new Unidad(), "")));

		listaIndicador.addAll(lm);
	}

	public List<Indicador> getListaIndicador() {
		return listaIndicador;
	}

	public void setListaIndicador(List<Indicador> listaIndicador) {
		this.listaIndicador = listaIndicador;
	}

	public Indicador getIndicadorSeleccionado() {
		return indicadorSeleccionado;
	}

	public void setIndicadorSeleccionado(Indicador indicadorSeleccionado) {
		this.indicadorSeleccionado = indicadorSeleccionado;
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
