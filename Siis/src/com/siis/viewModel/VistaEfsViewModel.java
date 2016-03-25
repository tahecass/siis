package com.siis.viewModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.siis.configuracion.Conexion;
import com.siis.dto.Efs;

public class VistaEfsViewModel {
	public Conexion con;
	public List<Efs> listaEfs;
	public Efs detalleSeleccionado;

	@Wire
	Tabpanel tabPanelConsultas;
	@Wire
	Intbox TextboxParamContrato;
	@Wire
	Window WinVisualizacion;
	@Wire
	Iframe iframeVisualizacion;
	@Wire
	Tab tabConsulta;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		listaEfs = new ArrayList<Efs>();

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command
	public void listarEfs() {

		try {
			Efs ef = new Efs();
			if (TextboxParamContrato.getValue() != null)
				ef.setContrato(TextboxParamContrato.getValue());

			con = new Conexion();
			setlistaEfs((List<Efs>) con.obtenerListado("listarEfs", ef));
			tabConsulta.getTabbox().setSelectedIndex(0);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Command
	public void visualizar(@BindingParam("seleccionado") Efs ef) {
		if (ef != null && ef.getContenidoBinarioArchivo() != null) {
			// prepare the AMedia for iframe
			final InputStream mediais = new ByteArrayInputStream(ef.getContenidoBinarioArchivo());
			final AMedia amedia = new AMedia(ef.getNombreArchivo(), "pdf", "application/pdf", mediais);

			// set iframe content
			iframeVisualizacion.setContent(amedia);

			tabConsulta.getTabbox().setSelectedIndex(1);
		}

	}

	public List<Efs> getlistaEfs() {
		return listaEfs;
	}

	public void setlistaEfs(List<Efs> listaEfs) {
		this.listaEfs = listaEfs;
	}

	public Efs getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(Efs detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

}
