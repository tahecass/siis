package com.siis.framework.action;

import java.util.HashMap;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;

public class ResetearContrasenaAction extends ActionStandard implements
		AfterCompose, IInicializarComponentes {

	private static final long serialVersionUID = -873567277890601103L;

	private Radiogroup idRESCONZRgpCriterio;
	private Textbox idRESCONZTbxValor;

	@Override
	public void afterCompose() {
		cargarComponentesVista();
	}

	@Override
	public void cargarComponentesVista() {
		idRESCONZRgpCriterio = (Radiogroup) this.getFellow("idRESCONZRgpCriterio");
		idRESCONZTbxValor = (Textbox) this.getFellow("idRESCONZTbxValor");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void resetear() {
		try {
			String valor = idRESCONZTbxValor.getValue();
			HashMap parameters = new HashMap();
			
			switch (idRESCONZRgpCriterio.getSelectedIndex()) {
				case 0:
					parameters.put("CUENTA", valor);
					parameters.put("CORREO", null);					
					break;
				case 1:
					parameters.put("CUENTA", null);
					parameters.put("CORREO", valor);					
					break;
			}
			ParametrizacionFac.getFacade().actualizarRegistro("AA_resetearContrasena", parameters);
			this.detach();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}
	
	public void cancelar(){
		this.detach();
	}
}
