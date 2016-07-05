package com.casewaresa.framework.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.InputElement;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.excepciones.impl.Excepcion;
import com.casewaresa.framework.macros.BandboxFind;

/**
 * @author caseware
 * @date 23/01/2007
 * @name ZKProcesosComunesHelper.java
 * @desc Agrupa funcionalidades útiles y comunes den el manejode componentes de
 *       ZK - limpiarCampos: Limpia el valor de todos los compoenentes a partir
 *       de un componente padre - validarCampos: valida los compoenentes a
 *       partir de un componente padre
 */
public abstract class ZKProcesosComunesHelper {

    /** desc esta es la variable [ log ] de la clase [ ZKComponentsHelper.java ] */
    protected static Logger log = Logger
	    .getLogger(ZKProcesosComunesHelper.class);

    /**
     * @Class ZKComponentsHelper
     * @name resetAllChildren
     * @param componentePadre
     * @param filtro
     * @desc Este método se encarga de resetear los componentes tipo ingreso de
     *       datos util en la reiniciación de campos en los formularios de ZK
     */
    public static void limpiarCampos(AbstractComponent componentePadre,
	    Class<AbstractComponent> filtro) {
	// --obtenemos los componentes correspondientes al filtro
	List<Component> listaComponentes = getComponentes(componentePadre,
		filtro);

	for (Iterator<Component> iteradorComponentes = listaComponentes
		.iterator(); iteradorComponentes.hasNext();) {
	    Component componente = iteradorComponentes.next();

	    if (componente instanceof Datebox) {
		Datebox componenteOriginal = (Datebox) componente;
		componenteOriginal.setRawValue(null);
	    } else if (componente instanceof Timebox) {
		Timebox componenteOriginal = (Timebox) componente;
		componenteOriginal.setRawValue(null);
	    } else if (componente instanceof Decimalbox) {
		Decimalbox componenteOriginal = (Decimalbox) componente;
		componenteOriginal.setRawValue(null);
	    } else if (componente instanceof Doublebox) {
		Doublebox componenteOriginal = (Doublebox) componente;
		componenteOriginal.setRawValue(null);
	    } else if (componente instanceof Intbox) {
		Intbox componenteOriginal = (Intbox) componente;
		componenteOriginal.setRawValue(null);
	    } else if (componente instanceof Longbox) {
		Longbox componenteOriginal = (Longbox) componente;
		componenteOriginal.setRawValue(null);
	    } else if (componente instanceof Textbox) {
		Textbox componenteOriginal = (Textbox) componente;
		componenteOriginal.setRawValue(null);
	    } else if (componente instanceof Checkbox) {
		Checkbox componenteOriginal = (Checkbox) componente;
		componenteOriginal.setChecked(false); // esto debería estar en
						      // ICONSTANTES
	    } else if (componente instanceof Spinner) {
		Spinner componenteOriginal = (Spinner) componente;
		componenteOriginal.setRawValue(new Integer(0));
	    } else if (componente instanceof FCKeditor) {
		FCKeditor componenteOriginal = (FCKeditor) componente;
		componenteOriginal.setValue(null);
		componenteOriginal.invalidate();
		componenteOriginal.applyProperties();
	    } else if (componente instanceof Listbox
		    && ((Listbox) componente).getItems().size() > 1) {
		Listbox componenteOriginal = (Listbox) componente;
		componenteOriginal.setSelectedIndex(0);
	    } else if (componente instanceof Combobox
		    && ((Combobox) componente).getItems().size() > 1) {
		Combobox componenteOriginal = (Combobox) componente;
		componenteOriginal.setSelectedIndex(0);
	    } else if (componente instanceof BandboxFind) {
		BandboxFind componenteOriginal = (BandboxFind) componente;
		componenteOriginal.setRawValue(null);
		componenteOriginal.setTooltiptext(null);
	    }
	}// fin for
    }

    /**
     * @Class ZKComponentsHelper
     * @name resetAllChildren
     * @param componentePadre
     * @param filtro
     * @desc Este m�todo se encarga de resetear los componentes tipo ingreso
     *       de datos util en la reiniciaci�n de campos en los formualrios de
     *       ZK
     */
    public static boolean validarCampos(AbstractComponent componentePadre,
	    Class<InputElement> filtro) {
	// --obtenemos los componentes correspondientes al filtro
	List<Component> listaComponentes = getComponentes(componentePadre,
		filtro);

	for (Iterator<Component> iteradorComponentes = listaComponentes
		.iterator(); iteradorComponentes.hasNext();) {
	    Component componente = iteradorComponentes.next();
	    /*
	     * si es un componente de tipo entrada de datos entonces se valida
	     * para otra clase de componentes es necesario imlplementar sus
	     * respectivas validaociones
	     */
	    if (componente instanceof InputElement) {
		InputElement componenteValidar = (InputElement) componente;

		// se verifica su validez...
		if (!componenteValidar.isValid()) {
		    log.trace("Componente invalido -- "
			    + componenteValidar.getId());
		    componenteValidar.setFocus(true);
		    return false; // --> hubo un error en el formulario
		}
	    }
	}// fin for
	return true;
    }

    /**
     * @type Metodo de la clase ZKProcesosComunesHelper.java
     * @name deshabilitarCampos
     * @param componentePadre
     * @param filtro
     * @desc Este método se encarga de deshabilitar los componentes tipo
     *       ingreso de datos util en la deshabilitacion de campos en los
     *       formularios de ZK
     */
    public static void deshabilitarCampos(AbstractComponent componentePadre,
	    Class<AbstractComponent> filtro) {
	// --obtenemos los componentes correspondientes al filtro
	List<Component> listaComponentes = getComponentes(componentePadre,
		filtro);

	for (Iterator<Component> iteradorComponentes = listaComponentes
		.iterator(); iteradorComponentes.hasNext();) {
	    Component componente = iteradorComponentes.next();

	    if (componente instanceof Datebox) {
		Datebox componenteOriginal = (Datebox) componente;
		componenteOriginal.setDisabled(true);
	    } else if (componente instanceof Decimalbox) {
		Decimalbox componenteOriginal = (Decimalbox) componente;
		componenteOriginal.setReadonly(true);
	    } else if (componente instanceof Doublebox) {
		Doublebox componenteOriginal = (Doublebox) componente;
		componenteOriginal.setReadonly(true);
	    } else if (componente instanceof Intbox) {
		Intbox componenteOriginal = (Intbox) componente;
		componenteOriginal.setReadonly(true);
	    } else if (componente instanceof Longbox) {
		Longbox componenteOriginal = (Longbox) componente;
		componenteOriginal.setReadonly(true);
	    } else if (componente instanceof Textbox) {
		Textbox componenteOriginal = (Textbox) componente;
		componenteOriginal.setReadonly(true);
	    } else if (componente instanceof Checkbox) {
		Checkbox componenteOriginal = (Checkbox) componente;
		componenteOriginal.setDisabled(true);
	    } else if (componente instanceof Spinner) {
		Spinner componenteOriginal = (Spinner) componente;
		componenteOriginal.setReadonly(true);
	    } else if (componente instanceof Listbox) {
		Listbox componenteOriginal = (Listbox) componente;
		componenteOriginal.setDisabled(true);
	    } else if (componente instanceof Combobox
		    && ((Combobox) componente).getItems().size() > 1) {
		Combobox componenteOriginal = (Combobox) componente;
		componenteOriginal.setDisabled(true);
	    }
	}// fin for
    }

    /**
     * @type Metodo de la clase ZKProcesosComunesHelper.java
     * @name abilitarCampos
     * @param componentePadre
     * @param filtro
     * @desc Este método se encarga de habilitar los componentes tipo ingreso
     *       de datos util en la habilitación de campos en los formularios de
     *       ZK
     */
    public static void habilitarCampos(AbstractComponent componentePadre,
	    Class<AbstractComponent> filtro) {
	// --obtenemos los componentes correspondientes al filtro
	List<Component> listaComponentes = getComponentes(componentePadre,
		filtro);

	for (Iterator<Component> iteradorComponentes = listaComponentes
		.iterator(); iteradorComponentes.hasNext();) {
	    Component componente = iteradorComponentes.next();

	    if (componente instanceof Datebox) {
		Datebox componenteOriginal = (Datebox) componente;
		componenteOriginal.setDisabled(false);
	    } else if (componente instanceof Decimalbox) {
		Decimalbox componenteOriginal = (Decimalbox) componente;
		componenteOriginal.setReadonly(false);
	    } else if (componente instanceof Doublebox) {
		Doublebox componenteOriginal = (Doublebox) componente;
		componenteOriginal.setReadonly(false);
	    } else if (componente instanceof Intbox) {
		Intbox componenteOriginal = (Intbox) componente;
		componenteOriginal.setReadonly(false);
	    } else if (componente instanceof Longbox) {
		Longbox componenteOriginal = (Longbox) componente;
		componenteOriginal.setReadonly(false);
	    } else if (componente instanceof Textbox) {
		Textbox componenteOriginal = (Textbox) componente;
		componenteOriginal.setReadonly(false);
	    } else if (componente instanceof Checkbox) {
		Checkbox componenteOriginal = (Checkbox) componente;
		componenteOriginal.setDisabled(false);
	    } else if (componente instanceof Bandbox) {
		Bandbox componenteOriginal = (Bandbox) componente;
		componenteOriginal.setDisabled(false);
	    } else if (componente instanceof Spinner) {
		Spinner componenteOriginal = (Spinner) componente;
		componenteOriginal.setReadonly(false);
	    } else if (componente instanceof Listbox
		    && ((Listbox) componente).getItems().size() > 1) {
		Listbox componenteOriginal = (Listbox) componente;
		componenteOriginal.setDisabled(false);
	    } else if (componente instanceof Combobox
		    && ((Combobox) componente).getItems().size() > 1) {
		Combobox componenteOriginal = (Combobox) componente;
		componenteOriginal.setDisabled(false);
	    }
	}// fin for
    }

    /**
     * @type Metodo de la clase ZKProcesosComunesHelper.java
     * @name limpiarListboxConsultas
     * @param Componente
     * @param filtro
     * @desc
     */
    public static void limpiarListboxConsultas(Listbox componente) {

	if (componente != null) {

	    if (componente.getListhead() != null)
		for (Component listheader : componente.getListhead()
			.getChildren()) {
		    ((Listheader) listheader).setSortDirection("natural");
		}

	    if (componente.getItems() != null)
		componente.getItems().clear();

	    if (!componente.getMold().equals("paging")) {
		componente.setMold("paging");
		componente.setAutopaging(true);

	    }
	    componente.setVflex(true);
	}
    }

    /**
     * Limpia las filas del componente 
     * Reinicia el ordenamiento
     * 
     * @param componente Cualquier Componente que herede de {@link Grid}
     * @throws Excepcion Cualquier Excepcion
     */
    public static void limpiarGridConsultas(Grid componente) throws Excepcion {
	log.info("Ejecutando M�todo [ limpiarGridConsultas() ]");
	if (componente != null) {
	    if (componente.getRows() != null) {
		componente.getRows().getChildren().clear();
	    }
	    if (componente.getColumns() != null) {
		for (Component column : componente.getColumns().getChildren()) {
		    ((Column) column).setSortDirection("natural");
		}
	    }
	}
    }

    /**
     * @type Metodo de la clase ZKProcesosComunesHelper.java
     * @name limpiarListboxConsultasBandbox
     * @param Componente
     * @param filtro
     * @desc
     */
    public static void limpiarListboxConsultasBandbox(Listbox componente) {
	if (componente != null) {
	    componente.getItems().clear();
	    if (!componente.getMold().equals("paging")) {
		componente.setMold("paging");
		componente.setPageSize(IConstantes.TAMANO_PAGINACION_BANDBOX);
	    }
	}
    }

    /**
     * @Class ZKProcesosComunesHelper
     * @name getComponents
     * @param componentePadre
     * @param filtro
     * @param listaComponentesFiltrada
     * @return List
     * @desc Este m�todo se encarga de recorrer el arbol de componentes a
     *       partir de un componente padre y retorna los componentes hijos que
     *       cumplan con el filtro, el acceso directo a este m�todo esta
     *       restringido solo a llamados internos de la clase.
     */
    private static List<Component> getComponentes(
	    List<Component> listaComponentesFiltrada,
	    AbstractComponent componentePadre, Class<?> filtro) {

	List<Component> listaComponentesHijos = componentePadre.getChildren();
	AbstractComponent componenteHijo = null;

	// -- validamos que la lista no esta vacia
	if (listaComponentesFiltrada == null)
	    listaComponentesFiltrada = new ArrayList<Component>();

	for (int i = 0; i < listaComponentesHijos.size(); i++) {
	    componenteHijo = (AbstractComponent) listaComponentesHijos.get(i);

	    // --si es un componente del tipo del filtro...
	    if (filtro != null && filtro.isInstance(componenteHijo)) {
		// log.info("hijo " + i + " -> " + componenteHijo.getId() +
		// " clase: " + componenteHijo.getClass());
		listaComponentesFiltrada.add(componenteHijo);
	    } else if (filtro == null)// si no hay filtros entonces le mandamos
				      // todos...
	    {
		// log.info("hijo " + i + " -> " + componenteHijo.getId() +
		// " clase: " + componenteHijo.getClass());
		listaComponentesFiltrada.add(componenteHijo);
	    }

	    // --si el componente es un contenedor de m�s objetos entonces..
	    // los
	    // invocamos tambien a ellos
	    if (componenteHijo.getChildren().size() != 0) {
		// CUIDADO!!! recursividad a la vista..
		getComponentes(listaComponentesFiltrada, componenteHijo, filtro);
	    }
	}// fin for
	return listaComponentesFiltrada;
    }

    /**
     * @Class ZKProcesosComunesHelper
     * @name getComponents
     * @param componentePadre
     * @param filtro
     * @return List
     * @desc Este m�todo se encarga de recorrer el arbol de componentes a
     *       partir de un componente padre y retorna los componentes hijos que
     *       cumplan con el filtro.
     */
    public static List<Component> getComponentes(
	    AbstractComponent componentePadre, Class<?> filtro) {
	// --obtenemos los componentes correspondientes al filtro
	return getComponentes(new ArrayList<Component>(), componentePadre,
		filtro);
    }
}
