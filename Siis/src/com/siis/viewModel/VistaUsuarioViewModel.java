package com.siis.viewModel;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import com.siis.framework.macros.BandboxFindPaging;
import com.siis.iceberg_gp.dto.GPTPersona;

public class VistaUsuarioViewModel extends Borderlayout implements AfterCompose{
	private BandboxFindPaging IDIUSUpersona;
	private Button IDIUSUBTNpersona;
	private Textbox IDIUSUTXTpersonaSec;
	public void parametrizarBandbox() {

		IDIUSUpersona
				.setConsultaPaginada("FA_obtenerListadoPaginadoTarifario");
		IDIUSUpersona.setConsultaObtener("FA_obtenerTarifario");
		IDIUSUpersona.setObjetoClase(new GPTPersona());
		IDIUSUpersona.setTextboxSecuencia(IDIUSUTXTpersonaSec);
		IDIUSUpersona.setMsgNoHayRegistros("No Registros");
		IDIUSUpersona.esNoObligatorio(IDIUSUBTNpersona);

	}
	public void cargarComponentesVista() {

		IDIUSUpersona = (BandboxFindPaging) this
				.getFellow("IDIUSUpersona").getChildren().get(0);
	}
	@Override
	public void afterCompose() {
		// TODO Auto-generated method stub
		cargarComponentesVista();
		parametrizarBandbox();
	}
}
