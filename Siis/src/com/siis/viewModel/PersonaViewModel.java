package com.siis.viewModel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

import com.siis.configuracion.Conexion;
import com.siis.dto.Indicador;
import com.siis.dto.Persona;
import com.siis.dto.TipoIdentificacion;

public class PersonaViewModel {
	private Persona persona;
	private List listaPersona;
	private boolean habilitarCampo;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println(" afterCompose 1");
		persona = new Persona();
		persona.setTipoIdentificacion(new TipoIdentificacion());
		listaPersona = new ArrayList<Persona>();
	}

	@NotifyChange("*")
	public List getListaPersona() {
		System.out.println("Ejecutando metodo.... getListaPersona");
		try {
			listaPersona = (List) Conexion.getConexion().listar(
					"listaPersonas", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaPersona;
	}

	public void setListaPersona(List listaPersona) {
		this.listaPersona = listaPersona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@NotifyChange("*")
	@Command
	public void onAgregar() {
		if (persona.getSec() == null)
			Conexion.getConexion().guardar("guardarPersonas", persona);
		else
			Conexion.getConexion().guardar("actualizarPersonas", persona);
		
		persona = new Persona();
		getListaPersona(); 
		

	}

	@Command
	public void onEliminar() {
		if (persona.getSec() != null)
			Conexion.getConexion().guardar("eliminarPersonas", persona);

	}

	public boolean isHabilitarCampo() {
		return habilitarCampo;
	}

	public void setHabilitarCampo(boolean habilitarCampo) {
		this.habilitarCampo = habilitarCampo;
	}

}
