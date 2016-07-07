package com.siis.viewModel;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import com.siis.dto.Persona;
import com.siis.framework.macros.BandboxFindPaging;

public class VistaUsuarioViewModel extends Borderlayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BandboxFindPaging IDIUSUpersona;
	private Button IDIUSUBTNpersona;
	private Textbox IDIUSUTXTpersonaSec;
	protected static Logger log = Logger.getLogger(BancoViewModel.class);

	public void parametrizarBandbox() {
		log.info("Ejecutando metodo... parametrizarBandbox");

	}

	@org.zkoss.bind.annotation.AfterCompose
	public void afterCompose() {

		// TODO Auto-generated method stub
		parametrizarBandbox();
	}

	@Command
	@NotifyChange("*")
	public void cargarIDIUSUpersona(
			@BindingParam("IDIUSUpersona") HtmlMacroComponent IDIUSUpersona) {
		log.info("Ejecutando el mEtodo [ cargarIDIUSUpersona ]... ");
		this.IDIUSUpersona = (BandboxFindPaging) IDIUSUpersona.getChildren()
				.get(0);
		this.IDIUSUpersona.setConsultaPaginada("listaPersonasPaginada");
		this.IDIUSUpersona.setConsultaObtener("obtenerPersonas");
		this.IDIUSUpersona.setObjetoClase(new Persona());
		this.IDIUSUpersona.setTextboxSecuencia(IDIUSUTXTpersonaSec);
		this.IDIUSUpersona.setMsgNoHayRegistros("No Registros");
		this.IDIUSUpersona.esNoObligatorio(IDIUSUBTNpersona);
	}

}
