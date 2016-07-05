package com.casewaresa.framework.zk.control.comparator;

import java.util.Comparator;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Radio;
import org.zkoss.zul.impl.InputElement;

/**
 * Clase abstracta que implementa la interface {@link Comparator} para manejar
 * el ordenamiento de las columnas en los componentes {@link Grid} y
 * {@link Listbox}. Ademas se utilizan dos atributos, uno para saber si el
 * ordenamiento es ascendente o descendente y el otro para saber sobre que
 * columna se va a hacer dicho ordenamiento.
 * 
 * @see Comparator
 * 
 * @author CASEWAREDES05
 * 
 * @param <T>
 */

public abstract class ComparadorIceberg<T> implements Comparator<T> {

    protected static Logger log = Logger.getLogger(ComparadorIceberg.class);

    private Boolean ascendente = true;
    private Integer columna;

    public ComparadorIceberg(Boolean ascendente, Integer columna) {
	this.ascendente = ascendente;
	this.columna = columna;
    }

    /**
     * 
     * @return retorna verdadero si el ordenamiento es ascendente, retorna falso
     *         si el ordenamiento es descendente
     */
    public Boolean isAscendente() {
	return ascendente;
    }

    /**
     * 
     * @return Retorna la columna sobre la cual se va a hacer el ordenamiento
     */
    public Integer getColumna() {
	return columna;
    }

    /**
     * Metodo para obtener el valor en String de una celda. Este valor puede
     * venir contenido en un componente que este dentro de la celda , o en la
     * misma celda como tal. Los componentes admitidos son las extensiones de
     * {@link InputElement } , {@link Label} y {@link Checkbox}
     * 
     * @param tipoComponente
     *            Tipo de componente que se esta comparando. Ver
     *            {@link TipoComponente}
     * @param componenteCelda
     *            Componente que se esta comparando (Row para las grillas,
     *            Listitem para los listbox)
     */
    public String obtenerValorCelda(TipoComponente tipoComponente,
	    Component componenteCelda) {
	String valor = "";
	if (tipoComponente.equals(TipoComponente.ROW)) {
	    Component componente = componenteCelda.getChildren().get(
		    getColumna());
	    if (componente instanceof Checkbox) {
		valor = ((Checkbox) componente).getLabel();
	    } else if (componente instanceof Radio) {
		valor = ((Radio) componente).getLabel();
	    }
	    if (componente instanceof Label) {
		valor = ((Label) componente).getValue();
	    } else if (componente instanceof InputElement) {
		valor = ((InputElement) componente).getText();
	    } else if (componente instanceof HtmlMacroComponent) {
		Component componenteInterno = componente.getChildren().get(0);
		if (componenteInterno instanceof InputElement) {
		    valor = ((InputElement) componenteInterno).getText();
		} else if (componenteInterno instanceof Label) {
		    valor = ((Label) componenteInterno).getValue();
		}
	    } else if (componente instanceof Checkbox) {
		valor = ((Checkbox) componente).getLabel();
	    }
	} else if (tipoComponente.equals(TipoComponente.LISTITEM)) {
	    Listcell listcell = (Listcell) componenteCelda.getChildren().get(
		    getColumna());
	    if (listcell.getChildren().isEmpty()) {
		valor = listcell.getLabel();
	    } else {
		Component componente = listcell.getChildren().get(0);
		if (componente instanceof Checkbox) {
		    valor = ((Checkbox) componente).getLabel();
		} else if (componente instanceof Radio) {
		    valor = ((Radio) componente).getLabel();
		}if (componente instanceof Label) {
		    valor = ((Label) componente).getValue();
		} else if (componente instanceof InputElement) {
		    valor = ((InputElement) componente).getText();
		} else if (componente instanceof HtmlMacroComponent) {
		    Component componenteInterno = componente.getChildren().get(
			    0);
		    if (componenteInterno instanceof InputElement) {
			valor = ((InputElement) componenteInterno).getText();
		    } else if (componenteInterno instanceof Label) {
			valor = ((Label) componenteInterno).getValue();
		    }
		} else if (componente instanceof Checkbox) {
		    valor = ((Checkbox) componente).getLabel();
		}
	    }
	}

	return valor;
    }

    /**
     * Enumeracion que contiene los diferentes tipos de ordenamiento que se le
     * pueden hacer a una columna.
     * 
     * AMBOS ==> Le asigna un ordenamiento ascendente y uno descende ASCENDENTE
     * ==> Le asigna un ordenamiento ascendente DESCENDENTE ==> Le asigna un
     * ordenamiento descendente
     * 
     * @author CASEWAREDES05
     * 
     */
    public enum TipoOrdenamiento {
	AMBOS, ASCENDENTE, DESCENDENTE
    }

    /**
     * Enumeracion que contiene los diferentes tipos de componentes del
     * comparador.
     * 
     * ROW ==> Tipo Row, hace referencia cuando se esta trabajando con grillas
     * LISTITEM ==> Tipo Listitem, hace referencia cuando se esta trabajando con
     * listbox
     * 
     * @author CASEWAREDES05
     * 
     */
    public enum TipoComponente {
	ROW, LISTITEM
    }

}
