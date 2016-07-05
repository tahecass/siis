/**
 * ListasSeleccionFacade.java
 */
package com.casewaresa.framework.facade;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.casewaresa.framework.dto.CellListaSeleccion;
import com.casewaresa.framework.dto.ItemListaSeleccion;
import com.casewaresa.framework.manager.ListMgr;

/**
 * @author Fabio BarÃ³n
 * @date 2/12/2008
 * @name ListasSeleccionFacade.java
 * @desc Esta fachada es la puerta de entrada a la logica del negocio para las
 *       listas de chequeo
 */
public class ListasSeleccionFacade {

	/** desc: Esta clase es singlenton */
	private static final ListasSeleccionFacade pListasSeleccionFacade = new ListasSeleccionFacade();
	/** desc esta es la variable [ log ] de la clase [ ActionStandar.java ] */
	protected static Logger log = Logger.getLogger(ListasSeleccionFacade.class);

	/**
	 * @type Constructor de la clase COTParametrizacionFacade
	 * @name LogginFacade
	 * @return void
	 * @desc Crea una instancia de COTParametrizacionFacade
	 */
	private ListasSeleccionFacade() {
		super();
	}

	/**
	 * @type Mï¿½todo de la clase COTParametrizacionFacade
	 * @name getCOTParametrizacionFacade
	 * @return LogginFacade
	 * @desc permite obtener la instancia del objeto COTParametrizacionFacade
	 *       (singlenton)
	 */
	public static ListasSeleccionFacade getFacade() {
		return pListasSeleccionFacade;
	}

	/****************************************************************************************/
	/** METODOS DE LA FACHADA **/
	/****************************************************************************************/

	public List<Object> getListaCampos(String[] dominio) throws Exception {

		ListMgr mMgr = new ListMgr();
		return (List<Object>) mMgr.obtenerCamposBusqueda(dominio);
	}

	public void loadLista(Object objeto, String[] dominio) {
		log.info("Ejecutando el método [ loadLista ]... ");
		Listbox lb = null;
		List<Object> listaCampos = null;

		lb = (Listbox) objeto;

		lb.getItems().clear();

		try {
			listaCampos = (List<Object>) this.getListaCampos(dominio);

			lb.appendItem("Seleccione...", null);
			for (Object dto : listaCampos) {
				ItemListaSeleccion item = (ItemListaSeleccion) dto;

				Listitem newItem = new Listitem();
				newItem.setValue(item.getValor());
				newItem.setLabel(item.getEtiqueta());
				lb.appendChild(newItem);
			}
			lb.setSelectedIndex(0);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void loadLista(Object objeto, List<?> listaDatos) {
		log.info("Ejecutando el método [ loadLista ]... ");
		Listbox lb = null;
		List<?> listaCampos = null;

		lb = (Listbox) objeto;
		lb.getItems().clear();

		try {
			listaCampos = listaDatos;

			lb.appendItem("Seleccione...", null);
			for (Object dto : listaCampos) {
				ItemListaSeleccion item = (ItemListaSeleccion) dto;

				Listitem newItem = new Listitem();
				newItem.setValue(item.getValor());
				newItem.setLabel(item.getEtiqueta());
				lb.appendChild(newItem);
			}
			lb.setSelectedIndex(0);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void loadListaCell(Object objeto, List<CellListaSeleccion> listaDatos) {
		log.info("Ejecutando el método [ loadLista ]... ");
		Listbox lb = null;
		List<CellListaSeleccion> listaCampos = null;

		lb = (Listbox) objeto;
		lb.getItems().clear();

		try {
			listaCampos = listaDatos;

			lb.appendItem("Seleccione...", null);
			for (CellListaSeleccion dto : listaCampos) {
				CellListaSeleccion cell = dto;

				Listcell lcSec = new Listcell(cell.getSecuencia().toString());
				Listcell lcCod = new Listcell(cell.getValor());
				Listcell lcNom = new Listcell(cell.getEtiqueta());

				Listitem item = new Listitem();
				item.appendChild(lcSec);
				item.appendChild(lcCod);
				item.appendChild(lcNom);

				lb.appendChild(item);
			}
			lb.setSelectedIndex(0);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void loadCombo(Object objeto, List<ItemListaSeleccion> listaDatos) {
		log.info("Ejecutando el método [ loadCombo ]... ");
		Combobox cb = null;
		Comboitem cbi = null;
		List<ItemListaSeleccion> listaCampos = null;

		cb = (Combobox) objeto;
		cb.getItems().clear();

		try {
			listaCampos = listaDatos;

			cbi = new Comboitem();
			cbi.setValue(null);
			cbi.setLabel("Seleccione...");
			cb.appendChild(cbi);

			for (ItemListaSeleccion dto : listaCampos) {
				ItemListaSeleccion item = dto;

				Comboitem newItem = new Comboitem();
				newItem.setValue(item.getValor());
				newItem.setLabel(item.getEtiqueta());
				newItem.invalidate();
				cb.appendChild(newItem);

			}
			cb.setSelectedIndex(0);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void setListIndex(Object objeto, String valorBuscado) {
		log.info("Ejecutando el método [ setListIndex ]... ");

		Listbox list = (Listbox) objeto;
		Listitem registro = null;
		for (int i = 0; i < list.getItemCount(); i++) {
			registro = list.getItemAtIndex(i);

			if (registro.getValue() != null) {
				if (registro.getValue().toString().equals(valorBuscado)) {
					list.setSelectedIndex(i);
					list.setTooltiptext(registro.getLabel());
					break;
				}
			}
		}
	}

	public void setComboboxIndex(Object objeto, String valorBuscado) {
		log.info("Ejecutando el método [ setComboboxIndex ]... ");
		
		Combobox list = (Combobox) objeto;
		Comboitem registro = null;

		for (int i = 0; i < list.getItemCount(); i++) {
			registro = list.getItemAtIndex(i);
			log.info("valor buscado: " +valorBuscado + "valor del item: "+registro.getValue());
			if (registro.getValue() != null) {
				if (registro.getValue().toString().trim()
						.equals(valorBuscado.toString().trim())) {
					list.setSelectedIndex(i);
					list.setTooltiptext(registro.getLabel());
					list.setValue(registro.getLabel());
					log.info("Ejecutando el método [ setComboboxIndex ]... "+i);
					break;
				}
			}
		}
	}

	public void setListIndex(Object objeto, String valorBuscado, int column) {
		log.info("Ejecutando el método [ setListIndex ]... ");

		Listbox list = (Listbox) objeto;
		Listitem registro = null;

		for (int i = 0; i < list.getItemCount(); i++) {
			registro = list.getItemAtIndex(i);
			String listValue = ((Listcell) registro.getChildren().get(column))
					.getLabel();

			if (listValue.equals(valorBuscado)) {
				list.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * Agregar un item a una lista de seleccion *
	 * 
	 * @type MÃ©todo de la clase ListasSeleccionFacade.java
	 * @name addListitem
	 * @param listbox
	 * @param label
	 * @param value
	 */
	public void addListitem(Listbox listbox, String label, String value) {
		/*
		 * Se crea el ListItem y se le pasan los valores de la etiqueta y el
		 * valor por debajo
		 */
		Listitem listitem = new Listitem(label, value);

		listitem.setSelected(true);/* Se aplica la seleccion */
		listbox.appendChild(listitem);/* Se agrega el item a la lista */
	}

	/**
	 * Agregar un item a una lista de seleccion *
	 * 
	 * @type MÃ©todo de la clase ListasSeleccionFacade.java
	 * @name addListitem
	 * @param listbox
	 * @param label
	 * @param value
	 */
	public Listitem addListitemReturn(Listbox listbox, String label,
			String value) {
		/*
		 * Se crea el ListItem y se le pasan los valores de la etiqueta y el
		 * valor por debajo
		 */
		Listitem listitem = new Listitem(label, value);

		listitem.setSelected(true);/* Se aplica la seleccion */
		listbox.appendChild(listitem);/* Se agrega el item a la lista */
		return listitem;
	}

	/**
	 * @type MÃ©todo de la clase ListasSeleccionFacade.java
	 * @name addComboItem
	 * @param combobox
	 * @param label
	 * @param value
	 * @return combobox
	 */
	public Combobox addComboItem(Combobox combobox, String label, String value) {
		Comboitem comboitem = new Comboitem();

		comboitem.setLabel(label);
		comboitem.setValue(value);
		combobox.appendChild(comboitem);
		combobox.setSelectedItem(comboitem);

		return combobox;
	}
}
