package com.siis.framework.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.contract.IExcepcion;
import com.casewaresa.framework.dto.Autenticacion;
import com.casewaresa.framework.dto.MaquinaCliente;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.helper.PropiedadesHelper;
import com.casewaresa.framework.util.CryptUtil;
import com.casewaresa.framework.util.MyMessageBox;
import com.casewaresa.framework.util.ZkTemasUtil;
import com.casewaresa.iceberg_aa.dto.AATCuentaIceberg;
import com.casewaresa.iceberg_aa.dto.AATGrupoIceberg;

/**
 * @author Fabio Bar�n
 * @date 4/12/2008
 * @name AutenticacionAction.java
 * @desc Manejo de la autenticacion a la aplicai�n
 */

public class LoginAction extends ActionStandardBorder implements AfterCompose {

	private static final long serialVersionUID = -3046949251858035708L;

	/** @desc: provee un mecanismo para el manejo de mensajes */
	protected static Logger log = Logger.getLogger(LoginAction.class);

	private Autenticacion autenticacion;
	private MaquinaCliente maquinaCliente;
	private String tipoAutenticacion;
	private Button idZLOGBtnIngresar;

	public void afterCompose() {
		log.info("Ejecutando el metodo [afterCompose]...");

		try {
			// se habilita el serverPush
			this.getDesktop().enableServerPush(true);

			Center center = (Center) this.getFellow("idCenter");
			center.getChildren().clear();
			idZLOGBtnIngresar=(Button)this.getFellow("idZLOGBtnIngresar");
			idZLOGBtnIngresar.setWidgetListener("onClick", "capturar();");
			((Textbox)this.getFellow("idZLOGTbxContrasena")).setWidgetListener("onOK", "capturar();");
			Executions.createComponents(IConstantes.PANTALLA_INICIO, center,
					null);

			setAutenticacionContexto();

			// Se inicializa el esquema de Autenticacion
			setTipoAutenticacion();

			// Controlamos la opción de olvide contraseña
			if (tipoAutenticacion.equals(IConstantes.TIPO_AUTENTICACION_LDAP))
				this.getFellow("idLOGZToolOlvideContra").setVisible(false);
			else
				this.getFellow("idLOGZToolOlvideContra").setVisible(true);

			autenticacion = (Autenticacion) this.getDesktop().getSession()
					.getAttribute(IConstantes.USUARIO_SESSION);

			log.trace("Autenticacion Login: " + autenticacion);

			maquinaCliente = new MaquinaCliente(Executions.getCurrent()
					.getRemoteAddr(), Executions.getCurrent().getRemoteHost(),
					null);

			if (autenticacion != null
					&& autenticacion.getAutenticado().equals(
							IConstantes.USUARIO_AUTENTICADO)) {
				cargarZul();
			}
		} catch (ComponentNotFoundException e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_OBJ_NO_ENCONTRADO, e));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void setAutenticacionContexto() {
		// Seteamos en el contexto la secuencia del ejecutable de la forma de
		// login
		this.setAtributoContexto("TIPO_EJECUTABLE",
				IConstantes.PANTALLA_TIPO_FORMA);
		this.setAtributoContexto("SEC_EJECUTABLE", "1");
		this.setAtributoContexto("EJECUTABLE", "AA-00");
	}

	/**
	 * @type Metodo de la clase LoginAction
	 * @name obtenerTipoAutenticacion
	 * @return void
	 * @desc Este metodo encarga de obtener el tipo de autenticación.
	 * 
	 */
	public void setTipoAutenticacion() throws Exception {
		log.info("Ejecutando metodo [setTipoAutenticacion]...");

		// Consultamos el tipo de autenticación
		tipoAutenticacion = (String) ParametrizacionFac.getFacade()
				.obtenerRegistro("getTipoAutenticacion");
	}

	/**
	 * Metodo para cargar el zul de menu. Al cargar ese zul se agrega un
	 * atributo al desktop que indique que la pagina se tiene que refrescar para
	 * cargar el tema que trae el la cuenta por defecto. NOmbre del atributo
	 * "REINICIAR_PAGINA_TEMA"
	 */
	public void cargarZul() {
		log.info("Ejecutando [ cargarZul() ]...");
		Component miPage = this.getParent();
		this.detach();

		Executions.createComponents(IConstantes.PANTALLA_MENU, miPage, null);

	}

	public AATGrupoIceberg obtenerCuentaGrupoIceberg() throws Exception {

		log.info("Ejecutando el metodo [ obtenerCuentaGrupoIceberg ]...");
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("CUENTA_ICEBERG", autenticacion.getCuentaIceberg()
				.getCuentaIceberg());

		AATGrupoIceberg dtoGrupoIceberg = (AATGrupoIceberg) ParametrizacionFac
				.getFacade().obtenerRegistro(
						"obtenerGruposApartirCuentaIceberg", param);

		return dtoGrupoIceberg;

	}

	/**
	 * @type Metodo de la clase LogginAplicacion
	 * @name autenticar
	 * @return void
	 * @throws ScriptException 
	 * @desc Este metodo se metodo se encarga de autenticar un usuario a la
	 *       aplicaci�n
	 */
	public void autenticar() throws ScriptException {
		log.info("Ejecutando [ autenticar ]...");

		Textbox tbxUsr = (Textbox) this.getFellow("idZLOGTbxUsuario");
		Textbox tbxPwd = (Textbox) this.getFellow("idZLOGTbxContrasena");
		Grid grdLogin = (Grid) this.getFellow("idZLOGGrdLogin");
	

		try {
			this.getDesktop().getSession()
					.removeAttribute(IConstantes.USUARIO_SESSION);

			// Inicializamos el objeto autenticación
			autenticacion = new Autenticacion(tipoAutenticacion,
					new AATCuentaIceberg());

			if (!this.validarFormulario(grdLogin)) {
				MyMessageBox.show(getAttribute("MSG_FORMULARIO_VACIO")
						.toString(), getAttribute("MSG_ADVERTENCIA_TITTLE")
						.toString(), MyMessageBox.OK, MyMessageBox.INFORMATION,
						"500px", "120px");
			} else {
				autenticacion.getCuentaIceberg().setCuentaIceberg(
						tbxUsr.getValue().toUpperCase());

				if (tipoAutenticacion
						.equals(IConstantes.TIPO_AUTENTICACION_ICEBERG)) {
					autenticacion.getCuentaIceberg().setClave(
							CryptUtil.getMD5(tbxPwd.getValue()));
					
					log.info("[LoginAction][autenticar][188] [getCuentaIceberg().getClave()]"+autenticacion.getCuentaIceberg().getClave());
				} else {
					autenticacion.getCuentaIceberg()
							.setClave(tbxPwd.getValue());
					log.info("[LoginAction][autenticar][192]");

				}

				String causa = "";
				AATGrupoIceberg dto = obtenerCuentaGrupoIceberg();

				if (dto != null) {
					autenticacion.getCuentaIceberg().setGrupoIceberg(dto);
					autenticacion.getCuentaIceberg().getGrupoIceberg()
							.setSecGrupoIceberg(dto.getSecGrupoIceberg());
				}
				try {
					ParametrizacionFac.getFacade().obtenerRegistro(
							"obtenerUsuarioIniciando",
							autenticacion.getCuentaIceberg());
				} catch (Exception e) {
					log.error(e.getMessage(), e);

					causa = e.getCause().toString();
					log.info("Causa: " + causa);
				}

				if (autenticacion.getCuentaIceberg().getSecCuentaIceberg() != null) {
					autenticacion
							.setAutenticado(IConstantes.USUARIO_AUTENTICADO);

					if (autenticacion.getCuentaIceberg().getGrupoIceberg() != null) {
						autenticacion.setCuentaIceberg(autenticacion
								.getCuentaIceberg());

						if (!autenticacion.getTipoAutenticacion().equals(
								IConstantes.TIPO_AUTENTICACION_LDAP)) {
							if (autenticacion.getCuentaIceberg()
									.getObligaCambioClave().equals("S")) {
								autenticacion
										.setAutenticado(IConstantes.USUARIO_NO_AUTENTICADO);

								Map<String, Object> parametros = new HashMap<String, Object>();

								parametros.put("pAutenticacion", autenticacion);

								Window ventanaCambio = (Window) Executions
										.createComponents(
												IConstantes.PANTALLA_CAMBIO_CONTRASENA,
												null, parametros);

								ventanaCambio.doModal();
							}
						}

					} else {
						MyMessageBox.show(
								this.getAttribute(
										"MSG_USUARIO_NO_PERTENECE_GRUPO")
										.toString(),
								this.getAttribute("MSG_ADVERTENCIA_TITTLE")
										.toString(), MyMessageBox.OK,
								MyMessageBox.EXCLAMATION, "500px", "120px");
						return;
					}
				} else {
					autenticacion
							.setAutenticado(IConstantes.USUARIO_NO_AUTENTICADO);

					int resultado = causa.indexOf("{DB}[100]");

					if (resultado != -1) {
						MyMessageBox.show(
								getAttribute("MSG_USUARIO_NO_PERTENECE_GRUPO")
										.toString(),
								getAttribute("MSG_ADVERTENCIA_TITTLE")
										.toString(), MyMessageBox.OK,
								MyMessageBox.INFORMATION, "500px", "120px");

						// --reiniciamos el campo contrasena
						((Textbox) getFellow("idZLOGTbxContrasena"))
								.setRawValue("");
						((Textbox) getFellow("idZLOGTbxContrasena")).focus();

						return;
					}

				}

				// -- verificamos su información
				if (autenticacion.getAutenticado().equals(
						IConstantes.USUARIO_AUTENTICADO)) {
					log.debug("Autenticado OK! [ "
							+ autenticacion.getCuentaIceberg() + " ]");
					log.debug("Grupo ["
							+ autenticacion.getCuentaIceberg()
									.getGrupoIceberg().getSecGrupoIceberg()
							+ "]");

					autenticacion.getCuentaIceberg()
							.setFechaAutenticacionExitosa(new Date());

					ParametrizacionFac.getFacade().actualizarRegistro(
							"actualizarCuentaIceberg",
							autenticacion.getCuentaIceberg());

					// -- actualizamos el usuario en la session...
					this.getDesktop()
							.getSession()
							.setAttribute(IConstantes.USUARIO_SESSION,
									autenticacion);

					// --cerramos la pantalla del loggin...
					this.detach();

					// --Aplicamos la apariencia del sistema
					cargarThemeParameter();

					// ZkTemasUtil.setTheme(Executions.getCurrent(),
					// autenticacion
					// .getCuentaIceberg().getAparienciaIceberg()
					// .getAparienciaIceberg());

					// --mostramos la siguiente forma...
					this.cargarZul();

				} else {
					log.debug("Autenticado NO!");
					// -- anulamos cualquier información en la session
					Autenticacion temp = (Autenticacion) this.getDesktop()
							.getSession()
							.getAttribute(IConstantes.USUARIO_SESSION);
					if (temp != null) {
						this.getDesktop()
								.getSession()
								.setAttribute(IConstantes.USUARIO_SESSION,
										new Autenticacion());
					}
					MyMessageBox.show(
							getAttribute("MSG_VERIFICAR_USUARIO_CONTRASENIA")
									.toString(),
							getAttribute("MSG_ERROR_AUTENTICACION").toString(),
							MyMessageBox.OK, MyMessageBox.EXCLAMATION, "500px",
							"120px");

					// --reiniciamos el campo contrasena
					((Textbox) getFellow("idZLOGTbxContrasena"))
							.setRawValue("");
					((Textbox) getFellow("idZLOGTbxContrasena")).focus();

				}

			}

		} catch (WrongValueException e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_VALOR_INCORRRECTO, e));
		} catch (SuspendNotAllowedException e) {
			super.lanzarExcepcion(new Excepcion(
					IExcepcion.EXCEPCION_POR_INTERRUPCION, e));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}

	public void cargarThemeParameter() {
		log.info("Ejecutando el Metodo [ cargarThemeParameter ]");
		try {
			String cargarTheme = PropiedadesHelper.getInstance().getPropiedad(
					"ger.tema");

			log.info("Ambiente: ==> " + cargarTheme);

			if (cargarTheme != null && cargarTheme.equals("D")) {

				ZkTemasUtil.setTheme(Executions.getCurrent(), autenticacion
						.getCuentaIceberg().getAparienciaIceberg()
						.getAparienciaIceberg());

			} else if (cargarTheme != null && cargarTheme.equals("P")) {
				ZkTemasUtil.setTheme(Executions.getCurrent(), "sapphire");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetearContrasena() {
		log.info("Ejecutando el Metodo [ resetearContrasena ]");
		try {
			ResetearContrasenaAction win = (ResetearContrasenaAction) Executions
					.createComponents(IConstantes.PANTALLA_RESETEAR_CONTRASENA,
							null, null);

			win.setHeight("auto");
			win.setPosition("center,middle");
			win.doModal();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.lanzarExcepcion(new Excepcion(IExcepcion.EXCEPCION_GENERAL, e));
		}
	}
}