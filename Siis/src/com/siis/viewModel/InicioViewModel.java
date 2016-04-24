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
import org.zkoss.zul.Borderlayout;

import com.siis.configuracion.Conexion;
import com.siis.dto.Usuario;

public class InicioViewModel extends Borderlayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3304889284731306698L;
	protected static Logger log = Logger.getLogger(InicioViewModel.class);

	private Usuario usuario;
	private Session session;

	@AfterCompose
	public void AfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		System.out.println("AfterCompose");
		session = Executions.getCurrent().getDesktop().getSession();
		Selectors.wireComponents(view, this, false);
		if (session.getAttribute("usuario")!=null) {
			usuario = (Usuario) session
					.getAttribute("usuario");
			if (usuario != null) {
				cargarSiguientePagina();
			}
		}
		usuario = new Usuario();
		usuario.setClave("DEMO");
		usuario.setCuenta("DEMO");
	}

	@Command
	public void iniciar() {
		log.info("Ejecutando metodo iniciar");
		try {
			usuario = (Usuario) Conexion.getConexion().obtenerRegistro(
					"validarUsuario", usuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cargarSiguientePagina();
	}

	public void cargarSiguientePagina() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
