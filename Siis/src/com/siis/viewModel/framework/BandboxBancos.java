package com.siis.viewModel.framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.siis.dto.Banco;

public class BandboxBancos extends HtmlMacroComponent implements IdSpace {
	protected static Logger log = Logger.getLogger(BandboxBancos.class);
	/**
	 * Serial de la Clase
	 */
	private static final long serialVersionUID = 7005539625436311729L;
	@Wire
	private Bandbox bandboxBancoComponent;
	@Wire
	private Combobox listboxCriterio;
	@Wire
	private Textbox textboxBuscar;
	@Wire
	private Button botonConsultar;
	@Wire
	private Listbox listboxBanco;
	@Wire
	private Hlayout contenedor;
	@Wire
	private Image botonLimpiar;

	String tabla, tipo;
	private List<Banco> listaCLientes;
	private Banco clienteSeleccionado;
	private Map<String, Object> parametros = new HashMap<String, Object>();
	String valorBusqueda;

	/**
	 * Constructor del componente
	 */
	public BandboxBancos() {
		compose();

		Selectors.wireVariables(this, this, null);
		Selectors.wireComponents(this, this, false);
		Selectors.wireEventListeners(this, this);

	}

	@Listen("onClick = image#botonLimpiar")
	public void limpiarSeleccion() {
		log.info("limpiarSeleccion");
		try {
			this.setValue(null);

		} catch (Exception e) {
			log.info(e);

		}
	}

	@Listen("onOpen = #bandboxBancoComponent")
	public void open() {
		log.info("open");
		listboxBanco.getItems().clear();
		textboxBuscar.setRawValue("");
		textboxBuscar.setFocus(true);
		listboxCriterio.setSelectedIndex(0);
	}

	@Listen("onClick = button#botonConsultar;  onOK = textbox#textboxBuscar")
	public void buscar() {
		log.info("buscar");
		try {
			setObjeto(new Banco());
			pintarItems();

		} catch (Exception e) {
			log.info(e);
		}
	}

	@Listen("onOK = bandbox#bandboxBancoComponent")
	public void buscarAtajo() {
		log.info("buscarAtajo");
		try {
			if (bandboxBancoComponent.isValid()) {
				parametros.put("OBJETO", setObjetoAtajo(new Banco()));

				pintarItems();

				if (listboxBanco.getItems().size() == 1) {
					setValue((Banco) listboxBanco.getItems().get(0).getValue());
				}

			}
		} catch (Exception e) {
			log.info(e);
		}
	}

	/**
	 * @throws Exception
	 */
	private void pintarItems() throws Exception {

		parametros.put("valorBusqueda", valorBusqueda);
		parametros.put("tabla", getTabla());
		parametros.put("tipo", getTipo());
		log.info("TABLA ==> " + getTabla() + " valorBusqueda " + valorBusqueda);
		listaCLientes = (List<Banco>) Conexion.getConexion().listarBancos("listarBancosNotIn", parametros);

		listboxBanco.getItems().clear();
		for (Banco cliente : listaCLientes) {
			final Listitem listitem = new Listitem();
			listitem.setValue(cliente);
			listitem.appendChild(new Listcell(cliente.getCodigo()));
			listitem.appendChild(new Listcell(cliente.getNombre()));
			listitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {

					seleccionar(listitem);
				}
			});
			listboxBanco.appendChild(listitem);
		}
	}

	/**
	 * @param persona
	 * @return
	 */
	private Object setObjeto(Banco persona) {
		log.info("setObjeto");
		if (textboxBuscar.getValue() != null && !textboxBuscar.getValue().isEmpty()) {
			valorBusqueda = "%" + textboxBuscar.getValue().toUpperCase() + "%";
			log.info("SETEANDO::::");
		} else {
			valorBusqueda = null;
		}
		return persona;
	}

	private Object setObjetoAtajo(Banco persona) {
		log.info("setObjetoAtajo");
		if (listboxCriterio.getSelectedItem() != null) {
			String criterio = listboxCriterio.getSelectedItem().getValue();
			String filtro = "%" + bandboxBancoComponent.getValue() + "%";
			// if (criterio.equals("identificacion")) {
			// persona.setIdentificacion(filtro);
			// } else if (criterio.equals("nombreRazonSocial")) {
			// persona.setNombreRazonSocial(filtro);
			// }
		}
		return persona;
	}

	public Banco getValue() {
		log.info("getValue");
		return clienteSeleccionado;
	}

	public void setValue(Banco persona) {
		log.info("setValue");
		this.clienteSeleccionado = persona;
		if (persona != null) {
			StringBuffer buffer = new StringBuffer();

			if (persona.getNombre() != null && !persona.getNombre().isEmpty()) {
				buffer.append(" ");
				buffer.append(persona.getNombre());
			}
			bandboxBancoComponent.setRawValue(buffer.toString());

		} else {
			bandboxBancoComponent.setRawValue("");
		}

	}

	/**
	 * @param constraint
	 */
	public void setConstraint(String constraint) {

		bandboxBancoComponent.setConstraint(constraint);
	}

	/**
	 * @param listitem
	 */
	private void seleccionar(final Listitem listitem) {
		setValue((Banco) listitem.getValue());
		bandboxBancoComponent.close();
		listboxCriterio.setSelectedIndex(0);
	}

	/**
	 * @param disabled
	 */
	public void setDisabled(Boolean disabled) {
		log.info("setDisabled");
		botonLimpiar.setVisible(!disabled);
		bandboxBancoComponent.setButtonVisible(!disabled);

	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
