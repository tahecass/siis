package com.siis.viewModel;

 
import java.util.Map;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cartera;
import com.siis.dto.DetalleCartera;

public class FormularioCarteraDetalleViewModel extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3962117090651619485L;
	/**
	 * 
	 */
	private Conexion con;
	private Map<String, Object> parametros;
	private Cartera cartera;

	@Wire
	private Textbox idDETCARTERAZTbxNroFactura;
	@Wire
	private Datebox idDETCARTERAZTDbxVencimiento;
	@Wire
	private Doublebox idDETCARTERAZTDbxValor1, idDETCARTERAZTDbxValor2, idDETCARTERAZTDbxValor3,
			idDETCARTERAZTDbxValor4;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose Detalle Cartera");
		parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		cartera = (Cartera) parametros.get("CARTERA");
		con = new Conexion();

	}

	@Command
	public void guardarDetalleCartera() {
		try {
			System.out.println("guardar Cartera");
			con = new Conexion();
			DetalleCartera detalle = new DetalleCartera();
			detalle.setCartera(cartera);
			detalle.setNroFactura(idDETCARTERAZTbxNroFactura.getValue());
			detalle.setVencimiento(idDETCARTERAZTDbxVencimiento.getValue());

			detalle.setValor1(idDETCARTERAZTDbxValor1.getValue());
			detalle.setValor2(idDETCARTERAZTDbxValor2.getValue());
			detalle.setValor3(idDETCARTERAZTDbxValor3.getValue());
			detalle.setValor4(idDETCARTERAZTDbxValor4.getValue());

			con.guardar("guardarDetalleCartera", detalle);

			this.detach();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
