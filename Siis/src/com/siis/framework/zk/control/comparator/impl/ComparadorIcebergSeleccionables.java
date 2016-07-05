package com.casewaresa.framework.zk.control.comparator.impl;

import java.util.Comparator;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import com.casewaresa.framework.zk.control.comparator.ComparadorIceberg;

public class ComparadorIcebergSeleccionables extends ComparadorIceberg<Component> {

    public ComparadorIcebergSeleccionables(Boolean ascendente, Integer columna) {
	super(ascendente, columna);
    }

    /**
     * Implementacion del metodo {@link Comparator#compare(Object, Object)} para
     * hacer las comparaciones, que este caso seran fechas. Se evalua si el
     * componente que llega es una instancia de {@link Row} o una instancia de
     * {@link Listitem}. Se obtiene la cadena que viene contenida en la
     * respectiva celda haciendo uso de
     * {@link ComparadorIceberg#obtenerValorCelda(com.casewaresa.framework.zk.control.comparator.ComparadorIceberg.TipoComponente, Component)}
     * Se hace la conversion a {@link Date} de la cadena obtenida poder comparar
     * a travez del metodo {@link Date#compareTo(Date)}. Resaltando que cuando
     * la fecha sea nula o la cadena este vacia, se tomara la fecha minima de
     * java (new Date(0))
     */
    @Override
    public int compare(Component dato1, Component dato2) {
	int comparador = 0;
	try {
	    String valor1, valor2;

	    if (dato1 instanceof Row) {
		valor1 = obtenerValorCelda(TipoComponente.ROW, dato1);
		valor2 = obtenerValorCelda(TipoComponente.ROW, dato2);
	    } else if (dato1 instanceof Listitem) {
		valor1 = obtenerValorCelda(TipoComponente.LISTITEM, dato1);
		valor2 = obtenerValorCelda(TipoComponente.LISTITEM, dato2);
	    } else {
		throw new RuntimeException(
			"No existe una implememtacion para este tipo de objeto "
				+ dato1.getClass().getName());
	    }
	    
	    comparador = valor1.compareTo(valor2) * (isAscendente() ? 1 : -1);
	    

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	return comparador;
    }
    
    

}
