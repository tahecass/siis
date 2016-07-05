package com.casewaresa.framework.macros;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class GridDetail extends Grid implements IdSpace, AfterCompose {

	private static final long serialVersionUID = 6257470411055794200L;
	public Logger log = Logger.getLogger(this.getClass());
	private Row itemSelected;

	/* Componentes Vista */

	public GridDetail() {}

	private void configurarFilas() {
		if (this.getRows() != null) {
			List<Component> rows = this.getRows().getChildren();

			for (Component row : rows) {
				eventoFila((Row) row);
			}
		}
	}

	public Row getSelectedItem() {
		return itemSelected;
	}

	public void setItemSelected(Row itemSelected) {
		this.itemSelected = itemSelected;
	}

	@Override
	public boolean appendChild(Component componente) {

		if (componente instanceof Row) {
			try {
				eventoFila((Row) componente);
				if (this.getRows() == null)
					this.appendChild(new Rows());

				this.getRows().appendChild(componente);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				return false;
			}
		} else {
			super.appendChild(componente);
		}

		return true;
	}

	private void eventoFila(final Row row) {

		row.setStyle("background-color: #ffffff");

		row.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event arg0) throws Exception {
				if (itemSelected != null)
					itemSelected.setStyle("background-color: #ffffff");

				itemSelected = (Row) row;

				itemSelected.setStyle("background-color: #eeeeee");
			}
		});
	}

	@Override
	public void afterCompose() {
		configurarFilas();
	}
}