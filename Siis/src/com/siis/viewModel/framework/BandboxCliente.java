package com.siis.viewModel.framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.siis.configuracion.Conexion;
import com.siis.dto.Cliente;

public class BandboxCliente extends HtmlMacroComponent implements IdSpace {
	/**
	 * Serial de la Clase
	 */
	private static final long serialVersionUID = 7005539625436311729L;
	@Wire
	private Bandbox bandboxClienteComponent;
	@Wire
	private Combobox listboxCriterio;
	@Wire
	private Textbox textboxBuscar;
	@Wire
	private Button botonConsultar;
	@Wire
	private Listbox listboxCliente;
	@Wire
	private Hlayout contenedor;
	@Wire
	private Image botonLimpiar;
	private List<Cliente> listaCLientes;
	private Cliente clienteSeleccionado;
	private Map<String, Object> parametros = new HashMap<String, Object>();

	/**
	 * Constructor del componente
	 */
	public BandboxCliente() {
		compose();

		Selectors.wireVariables(this, this, null);
		Selectors.wireComponents(this, this, false);
		Selectors.wireEventListeners(this, this);

	}

	@Listen("onClick = image#botonLimpiar")
	public void limpiarSeleccion() {
		System.out.println("limpiarSeleccion");
		try {
			this.setValue(null);

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	@Listen("onOpen = #bandboxClienteComponent")
	public void open() {
		System.out.println("open");
		listboxCliente.getItems().clear();
		textboxBuscar.setRawValue("");
		textboxBuscar.setFocus(true);
		listboxCriterio.setSelectedIndex(0);
	}

	@Listen("onClick = button#botonConsultar;  onOK = textbox#textboxBuscar")
	public void buscar() {
		System.out.println("buscar");
		try {
			if (textboxBuscar.isValid()) {
				clienteSeleccionado = (Cliente) setObjeto(new Cliente());

				pintarItems();

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Listen("onOK = bandbox#bandboxClienteComponent")
	public void buscarAtajo() {
		System.out.println("buscarAtajo");
		try {
			if (bandboxClienteComponent.isValid()) {
				parametros.put("OBJETO", setObjetoAtajo(new Cliente()));

				pintarItems();

				if (listboxCliente.getItems().size() == 1) {
					setValue((Cliente) listboxCliente.getItems().get(0).getValue());
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @throws Exception
	 */
	private void pintarItems() throws Exception {

		listaCLientes = (List<Cliente>) Conexion.getConexion().listarClientes("listarClientes", clienteSeleccionado);

		listboxCliente.getItems().clear();
		for (Cliente cliente : listaCLientes) {
			final Listitem listitem = new Listitem();
			listitem.setValue(cliente);
			listitem.appendChild(new Listcell(cliente.getNit()));
			listitem.appendChild(new Listcell(cliente.getNombreRazonSocial()));
			listitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {

					seleccionar(listitem);
				}
			});
			listboxCliente.appendChild(listitem);
		}
	}

	/**
	 * @param persona
	 * @return
	 */
	private Object setObjeto(Cliente persona) {
		System.out.println("setObjeto");
		if (listboxCriterio.getSelectedItem() != null) {
			String criterio = listboxCriterio.getSelectedItem().getValue();
			String filtro = "%" + textboxBuscar.getValue() + "%";
			if (criterio.equals("nit")) {
				persona.setNit(filtro);
			} else if (criterio.equals("nombreRazonSocial")) {
				persona.setNombreRazonSocial(filtro);
			}
		}
		return persona;
	}

	private Object setObjetoAtajo(Cliente persona) {
		System.out.println("setObjetoAtajo");
		if (listboxCriterio.getSelectedItem() != null) {
			String criterio = listboxCriterio.getSelectedItem().getValue();
			String filtro = "%" + bandboxClienteComponent.getValue() + "%";
			if (criterio.equals("nit")) {
				persona.setNit(filtro);
			} else if (criterio.equals("nombreRazonSocial")) {
				persona.setNombreRazonSocial(filtro);
			}
		}
		return persona;
	}

	public Cliente getValue() {
		System.out.println("getValue");
		return clienteSeleccionado;
	}

	public void setValue(Cliente persona) {
		System.out.println("setValue");
		this.clienteSeleccionado = persona;
		if (persona != null) {
			StringBuffer buffer = new StringBuffer();
			// if (persona.getIdentificacion() != null
			// && !persona.getIdentificacion().isEmpty()) {
			// buffer.append("[").append(persona.getIdentificacion())
			// .append("]");
			// }
			if (persona.getNombreRazonSocial() != null && !persona.getNombreRazonSocial().isEmpty()) {
				buffer.append(" ");
				buffer.append(persona.getNombreRazonSocial());
			}

			// if (persona.getPrimerApellido() != null
			// && !persona.getPrimerApellido().isEmpty()) {
			// buffer.append(" ");
			// buffer.append(persona.getPrimerApellido());
			// }
			// if (persona.getSegundoApellido() != null
			// && !persona.getSegundoApellido().isEmpty()) {
			// buffer.append(" ");
			// buffer.append(persona.getSegundoApellido());
			// }
			bandboxClienteComponent.setRawValue(buffer.toString());

		} else {
			bandboxClienteComponent.setRawValue("");
		}

	}

	/**
	 * @param constraint
	 */
	public void setConstraint(String constraint) {

		bandboxClienteComponent.setConstraint(constraint);
	}

	/**
	 * @param listitem
	 */
	private void seleccionar(final Listitem listitem) {
		setValue((Cliente) listitem.getValue());
		bandboxClienteComponent.close();
		listboxCriterio.setSelectedIndex(0);
	}

	/**
	 * @param disabled
	 */
	public void setDisabled(Boolean disabled) {
		System.out.println("setDisabled");

		// if (!disabled)
		// Utilidades.habilitarCampos(contenedor);
		// else
		// Utilidades.deshabilitarCampos(contenedor);

		botonLimpiar.setVisible(!disabled);
		bandboxClienteComponent.setButtonVisible(!disabled);

	}

}
