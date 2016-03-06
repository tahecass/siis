package com.siis.viewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;

import com.siis.configuracion.Conexion;
import com.siis.dto.Banco;
import com.siis.viewModel.framework.Utilidades;

public class BancoViewModel {
	private Banco banco;
	private Banco bancoSeleccionada;
	private List listaBanco;
	private boolean habilitarCampo;
	private String valorBusqueda = "";

	@Wire
	private Tab idClieDatosgenerales;
	protected static Logger log = Logger.getLogger(BancoViewModel.class);
	private List listaTipoId;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		log.info("Ejecutando metodo.... afterCompose");
		Selectors.wireComponents(view, this, false);
		banco = new Banco();
		listaBanco = new ArrayList<Banco>();
		habilitarCampo = true;
	}

	public List getListaTipoId() {
		return listaTipoId;
	}

	public void setListaTipoId(List listaTipoId) {
		this.listaTipoId = listaTipoId;
	}

	@NotifyChange("*")
	@Command
	public void onBuscar() {
		log.info("Ejecutando metodo.... getListaBanco" + valorBusqueda);
		try {
			listaBanco = (List) Conexion.getConexion().listar(
					"listarBancos", "%" + valorBusqueda + "%");
		} catch (Exception e) {
			// TODO Auto-generated catcfh block
			e.printStackTrace();
		}
	}

	public List getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List listaBanco) {
		this.listaBanco = listaBanco;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	@NotifyChange("*")
	@Command
	public void onAgregar() {
		if (banco.getSec() == null) {
			Conexion.getConexion().guardar("guardarBanco", banco);
			Utilidades.mostrarNotificacion("Banco",
					"Se guardó correctamente la información", "INFO");
		} else {
			Conexion.getConexion().guardar("actualizarBanco", banco);
			Utilidades.mostrarNotificacion("Banco",
					"Se actualizó correctamente la información", "INFO");
		}
		banco = new Banco();
		onBuscar();

	}

	@NotifyChange("*")
	@Command
	public void onEliminar() {
		log.info("SECUENCIA: "+bancoSeleccionada.getSec());
		if ((Messagebox.show("¿Desea eliminar eliminar la fila seleccionada?",
				"Se eliminó correctamente la información", Messagebox.NO | Messagebox.YES,
				Messagebox.QUESTION)) == Messagebox.YES) {
			banco = bancoSeleccionada;
		log.info("sECUENCIA: "+banco.getSec());
			if (banco.getSec() != null) {
				Conexion.getConexion().guardar("eliminarBanco", banco);
				Utilidades.mostrarNotificacion("Banco",
						"Se elimino correctamente la información", "INFO");
				banco = new Banco();
				onBuscar();
			}
		}
	}

	@NotifyChange(".")
	@Command
	public void onEditar() {
		banco = bancoSeleccionada;
		System.out.print("Ejecutando metodo... onEditar");
		habilitarCampo = false;
	}

	@NotifyChange("*")
	@Command
	public void onNuevo() {
		System.out.println("seleccionar");
		habilitarCampo = false;
	}

	public boolean isHabilitarCampo() {
		return habilitarCampo;
	}

	public void setHabilitarCampo(boolean habilitarCampo) {
		this.habilitarCampo = habilitarCampo;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public Banco getBancoSeleccionada() {
		return bancoSeleccionada;
	}

	public void setBancoSeleccionada(Banco bancoSeleccionada) {
		this.bancoSeleccionada = bancoSeleccionada;
	}

}
