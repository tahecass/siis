package com.siis.framework.action;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Textbox;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.Autenticacion;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.util.CryptUtil;
import com.casewaresa.framework.util.MyMessageBox;
import com.casewaresa.iceberg_aa.facade.ParametrizacionFac;

@SuppressWarnings("unchecked")
public class CambiarContrasenaAction extends ActionStandard implements
		AfterCompose, IInicializarComponentes {

	private static final long serialVersionUID = -873567277890601103L;
	private Autenticacion autenticacion;

	private Textbox idCAMCONZTbxClaveCuentaIceberg;
	private Textbox idCAMCONZTbxClaveCuentaIcebergRep;

	@Override
	public void afterCompose() {
		Map<String, Object> parametros = (Map<String, Object>) Executions.getCurrent().getArg();

		
		this.autenticacion = (Autenticacion) parametros
				.get("pAutenticacion");

		cargarComponentesVista();
	}

	@Override
	public void cargarComponentesVista() {
		idCAMCONZTbxClaveCuentaIceberg = (Textbox) this
				.getFellow("idCAMCONZTbxClaveCuentaIceberg");
		idCAMCONZTbxClaveCuentaIcebergRep = (Textbox) this
				.getFellow("idCAMCONZTbxClaveCuentaIcebergRep");
	}

	public void cambiar() {
		try {
			if (idCAMCONZTbxClaveCuentaIceberg.getValue().equals(
					idCAMCONZTbxClaveCuentaIcebergRep.getValue())) {
				ParametrizacionFac.getFacade().obtenerRegistro(
						"obtenerCuentaIceberg", this.autenticacion.getCuentaIceberg());

				this.autenticacion.getCuentaIceberg().setClave(CryptUtil.getMD5(idCAMCONZTbxClaveCuentaIceberg
						.getValue().toString()));
				this.autenticacion.getCuentaIceberg().setObligaCambioClave("N");

				ParametrizacionFac.getFacade().actualizarRegistro(
						"actualizarCuentaIceberg", this.autenticacion.getCuentaIceberg());
				
				ParametrizacionFac.getFacade().obtenerRegistro("obtenerCuentaIceberg",this.autenticacion.getCuentaIceberg());
				

				autenticacion.setAutenticado(IConstantes.USUARIO_AUTENTICADO);

				this.detach();
			}else{
				MyMessageBox.show(this.getAttribute("MSG_CONTRASENAS_NO_CONCUERDAN").toString(),
						this.getAttribute("MSG_ERROR_CAMBIO_CONTRASENA").toString(),
						MyMessageBox.OK,
						MyMessageBox.ERROR, "500px",	"120px");
			}
		} catch (WrongValueException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}
}
