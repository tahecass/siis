package com.siis.viewModel;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Usuario;
import com.siis.viewModel.framework.Utilidades;

public class InicioViewModel extends Borderlayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3304889284731306698L;
	protected static Logger log = Logger.getLogger(InicioViewModel.class);

	private Session session;

	@Wire
	private Borderlayout idWINFORMINICIOZPrincipal;
	@Wire
	private Textbox tbxUsuario;
	@Wire
	private Textbox tbxClave;
	@Wire
	private Button btnIniciar;

	@AfterCompose
	public void AfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		log.info("AfterCompose");
		session = Executions.getCurrent().getDesktop().getSession();
		Selectors.wireComponents(view, this, false);

		btnIniciar.setWidgetListener("onClick", "capturar();");
		tbxClave.setWidgetListener(
				"onOK", "capturar();");
	}

	@Command
	public void iniciar() {
		log.info("Ejecutando metodo iniciar CLAVE==> " + tbxClave.getRawValue()
				+ " USUARIO ==> " + tbxUsuario.getRawValue());
		try {
			Usuario usuario = new Usuario();
			usuario.setClave(Utilidades.getMD5(tbxClave.getRawValue()
					.toString()));
			log.info(usuario.getClave());

			usuario.setCuenta(tbxUsuario.getRawValue().toString().toUpperCase());
			usuario = (Usuario) Conexion.getConexion().obtenerRegistro(
					"validarUsuario", usuario);
			if (usuario != null) {
				cargarSiguientePagina(usuario);

			} else {
				Utilidades
						.mostrarNotificacion(
								idWINFORMINICIOZPrincipal.getAttribute(
										"MSG_TITULO").toString(),
								"Por favor verifique las credenciales suministradas, si el error persiste contacte al administrador del sistema",
								"ERROR");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cargarSiguientePagina(Usuario usuario) {
		log.info("Ejecutando metodo cargarSiguientePagina..."
				+ usuario.getCuenta());
		try {

			log.info("Ejecutando metodo..." + usuario.getTipo());
			if (usuario != null) {
				String urlPagina = "";
				if (usuario.getTipo().equals("FINANCIERO")) {
					urlPagina = "/menu_financiero.zul";
				} else if (usuario.getTipo().equals("RRHH")) {
				} else if (usuario.getTipo().equals("HSEQ")) {

				} else if (usuario.getTipo().equals("EXTERNO")) {

				} else if (usuario.getTipo().equals("OPERATIVO")) {

				}
				session.setAttribute("usuario", usuario);
				this.detach();
				Executions.sendRedirect(urlPagina);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
