package com.casewaresa.framework.zk.control.comparator.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Comparator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import com.casewaresa.framework.helper.PropiedadesHelper;
import com.casewaresa.framework.zk.control.comparator.ComparadorIceberg;

/**
 * Clase que extiende de {@link ComparadorIceberg} centrandose en el
 * ordenamiento exclusivo de numeros con un pattern establecido.
 * 
 * @author CASEWAREDES05
 * 
 * @see ComparadorIceberg
 * 
 */

public class ComparadorIcebergNumero extends ComparadorIceberg<Component> {

    /**
     * Patron utilizado para la conversion de los numeros.
     */
    private String pattern;

    public ComparadorIcebergNumero(Boolean ascendente, String pattern,
	    Integer columna) {
	super(ascendente, columna);
	this.pattern = pattern;
    }

    /**
     * Metodo para convertir una cadena a un bigdecimal(numero)
     * 
     * @param cadena
     * @return
     */

    public BigDecimal convertirCadenaToNumero(String cadena) {

	DecimalFormat formateador = pattern.isEmpty() ? new DecimalFormat()
		: new DecimalFormat(pattern);
	DecimalFormatSymbols symbols = new DecimalFormatSymbols();	
	
	String separadorDecimal = PropiedadesHelper.getInstance().getPropiedad("ger.signo_decimal");
	
	if (separadorDecimal != null && separadorDecimal.equals(".")) {
	    symbols.setDecimalSeparator('.');
	    symbols.setGroupingSeparator(',');
	    formateador.setDecimalFormatSymbols(symbols);
	} else if (separadorDecimal != null && separadorDecimal.equals(",")) {
	    symbols.setDecimalSeparator(',');
	    symbols.setGroupingSeparator('.');
	    formateador.setDecimalFormatSymbols(symbols);
	}
	formateador.setParseBigDecimal(true);
	BigDecimal numero = (BigDecimal) formateador.parseObject(cadena,
		new ParsePosition(0));

	return numero;
    }

    /**
     * Implementacion del metodo {@link Comparator#compare(Object, Object)} para
     * hacer las comparaciones, que este caso seran numeros. Se evalua si el
     * componente que llega es una instancia de {@link Row} o una instancia de
     * {@link Listitem} para hacer la respectiva conversion a {@link BigDecimal}
     * a partir de la cadena que viene incrustada y poder comparar a travez del
     * metodo {@link BigDecimal#compareTo(BigDecimal)}. Resaltando que cuando el
     * numero sea nulo o la cadena este vacia, se tomara cero (new
     * BigDecimal(0))
     * 
     * 
     */

    /**
     * Implementacion del metodo {@link Comparator#compare(Object, Object)} para
     * hacer las comparaciones, que este caso seran numeros. Se evalua si el
     * componente que llega es una instancia de {@link Row} o una instancia de
     * {@link Listitem}. Se obtiene la cadena que viene contenida en la
     * respectiva celda haciendo uso de
     * {@link ComparadorIceberg#obtenerValorCelda(com.casewaresa.framework.zk.control.comparator.ComparadorIceberg.TipoComponente, Component)}
     * Se hace la conversion a {@link BigDecimal} de la cadena obtenida haciendo
     * uso de del metodo {@link #convertirCadenaToNumero(String)} poder comparar
     * a travez del metodo {@link BigDecimal#compareTo(BigDecimal)}. Resaltando
     * que cuando el numero sea nulo o la cadena este vacia, se tomara cero (new
     * BigDecimal(0))
     */

    @Override
    public int compare(Component dato1, Component dato2) {
	int comparador = 0;
	try {

	    String valor1, valor2;
	    BigDecimal numero1 = null, numero2 = null;

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

	    numero1 = convertirCadenaToNumero(valor1);
	    numero2 = convertirCadenaToNumero(valor2);

	    if (!(numero1 != null && numero2 != null)) {
		if (numero1 == null && numero2 != null) {
		    numero1 = new BigDecimal(0);
		} else if (numero1 != null && numero2 == null) {
		    numero2 = new BigDecimal(0);
		} else {
		    numero1 = new BigDecimal(0);
		    numero2 = new BigDecimal(0);
		}
	    }

	    comparador = numero1.compareTo(numero2) * (isAscendente() ? 1 : -1);

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	return comparador;
    }

}
