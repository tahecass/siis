package com.siis.viewModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Textbox;

public class InicioViewModel extends Borderlayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3304889284731306698L;
	protected static Logger log = Logger.getLogger(InicioViewModel.class);
	@Wire
	Textbox tbxUsuario, tbxClave;

	@AfterCompose
	public void AfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		System.out.println("AfterCompose");
		Selectors.wireComponents(view, this, false);

	}

	@Command
	public void iniciar() {
		log.info("Ejecutando metodo iniciar");
		cargarSiguientePagina();
	}

	public void cargarSiguientePagina() {
		log.info("Ejecutando [ cargarSiguientePagina() ]...");
//		Component miPage = this.getParent();
		this.detach();
		String urlPagina = "", tipoUsuario = "FINANCIERO";

		if (tipoUsuario.equals("FINANCIERO")) {
			urlPagina = "/menu_financiero.zul";
		} else if (tipoUsuario.equals("RRHH")) {
		} else if (tipoUsuario.equals("HSEQ")) {

		} else if (tipoUsuario.equals("EXTERNO")) {

		} else if (tipoUsuario.equals("OPERATIVO")) {

		}

		Executions.sendRedirect(urlPagina);
		

	}
}
