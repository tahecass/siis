package com.siis.viewModel;

import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listgroupfoot;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.siis.configuracion.Conexion;
import com.siis.dto.ProyectoContrato;

public class ProyectoContratoRenderer implements ListitemRenderer<Object> {

	@Override
	public void render(Listitem listitem, Object obj, int index) throws Exception {

		if (listitem instanceof Listgroup) {
			List<ProyectoContrato> listaProyectoContrato = (List<ProyectoContrato>) Conexion.getConexion()
					.obtenerListado("listarProyectoContrato", null);
			ProyectoContrato food = listaProyectoContrato.get(0);
			String groupTxt = food.getRetegarantia() != null ? "FACTURADOS" : "Amortizacion";
			listitem.appendChild(new Listcell(groupTxt));
			listitem.setValue(obj);
		} else if (listitem instanceof Listgroupfoot) {
			Listcell cell = new Listcell();
			cell.setSclass("foodFooter");
			cell.setSpan(6);
			cell.appendChild(new Label("Total " + obj + " Items"));
			listitem.appendChild(cell);
		} else {
			ProyectoContrato data = (ProyectoContrato) obj;
			listitem.appendChild(new Listcell(data.getFactura()));
			listitem.appendChild(new Listcell(data.getValor().toString()));
			listitem.appendChild(new Listcell(data.getRetegarantia().toString()));
			listitem.setValue(data);
		}

	}

}
