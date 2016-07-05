package com.siis.framework.renderer;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.siis.framework.dto.IBeanAbstracto;
import com.siis.framework.macros.PagingControl;
import com.siis.framework.macros.PagingSelector;
import com.siis.framework.macros.PagingSelector.TiposSeleccion;

/**
 * Clase de utilidad para la creacion de filas del {@link PagingSelector} y que
 * extiende de {@link PagingControlFilas} ya que es necesario que dicha clase
 * sea un render aceptado por el {@link PagingControl}
 * 
 * @author dperezc
 * 
 */
public abstract class PagingSelectorFilas extends PagingControlFilas {

    /**
     * Variable para saber si se ha hecho click en el checkbox seleccionar
     * todos. Inicialmente es falsa.
     */
    private Boolean seleccionarTodos = new Boolean(false);

    /**
     * Mapa para guardar todos los objetos de tipo {@link IBeanAbstracto} que se
     * van seleccionado en el {@link Listbox} de referencia, siempre y cuando la
     * variable {@link #seleccionarTodos} sea falsa.
     */
    private Map<Long, IBeanAbstracto> mapaSeleccionados = new HashMap<Long, IBeanAbstracto>();

    /**
     * Mapa para guardar todos los objetos de tipo {@link IBeanAbstracto} que se
     * van desmarcando en el {@link Listbox} de referencia, siempre y cuando la
     * variable {@link #seleccionarTodos} sea verdadera
     */
    private Map<Long, IBeanAbstracto> mapaNoSeleccionados = new HashMap<Long, IBeanAbstracto>();

    /**
     * Tipo de seleccion con el que esta trabajando el {@link PagingSelector}
     */
    private TiposSeleccion tipoSeleccion;

    /**
     * Implementacion del metodo
     * {@link PagingControlFilas#render(Component, IBeanAbstracto)}, que este
     * caso no quiero que haga nada.
     */
    @Override
    public void render(Component component, IBeanAbstracto iBeanAbstracto) {
    }

    /**
     * Metodo abstracto que define la forma como se van a crear los listitems
     * apartir de un objeto {@link IBeanAbstracto}
     * 
     * @param iBeanAbstracto
     * @return
     */
    protected abstract Listitem renderModelListitem(
	    IBeanAbstracto iBeanAbstracto);

    /**
     * Metodo que selecciona o no al listitem que se va a agregar al listbox de
     * referencia. Se hace el llamado al metodo que tiene el modelo de creacion
     * de los listitems {@link #renderModelListitem(IBeanAbstracto)}. Se valida
     * por medio de la llave primaria del {@link IBeanAbstracto} si el objeto
     * esta seleccionado o no dependiendo de la configuracion que se tenga en el
     * momento
     * 
     * @param iBeanAbstracto
     * @return Retorna el listitem listo para agregar al listbox de referencia
     */
    public Listitem renderListitem(IBeanAbstracto iBeanAbstracto) {
	Listitem listitem = renderModelListitem(iBeanAbstracto);
	if (iBeanAbstracto != null && iBeanAbstracto.getPrimaryKey() != null) {
	    if (seleccionarTodos) {
		if (mapaNoSeleccionados.containsKey(iBeanAbstracto
			.getPrimaryKey())) {
		    listitem.setSelected(false);
		} else {
		    listitem.setSelected(true);
		}
	    } else {
		if (mapaSeleccionados.containsKey(iBeanAbstracto
			.getPrimaryKey())) {
		    listitem.setSelected(true);
		} else {
		    listitem.setSelected(false);
		}
	    }

	}
	return listitem;
    }

    /**
     * Metodo para actualizar las propiedades del componente referencia que se
     * esta utilizando un {@link PagingControl} dependiendo del tipo de
     * seleccion que se este utilizando
     * 
     * @param pagingControl
     */
    public void inicializarTipoSeleccion(PagingControl pagingControl) {
	Listbox listboxCurrent = (Listbox) pagingControl
		.getComponenteReferencia();

	listboxCurrent.setCheckmark(true);
	switch (tipoSeleccion) {

	case NO_MULTIPLE:
	    listboxCurrent.setMultiple(false);
	    break;

	case MULTIPLE_TODAS_PAGINAS:
	    listboxCurrent.setMultiple(true);
	    break;

	default:
	    break;

	}
	listboxCurrent.applyProperties();
    }

    /**
     * Metodo modificador de la variable {@link #mapaSeleccionados}
     */
    public void setMapaSeleccionados(Map<Long, IBeanAbstracto> mapaSeleccionados) {
	this.mapaSeleccionados = mapaSeleccionados;
    }

    /**
     * Metodo analizador de la variable {@link #mapaSeleccionados}
     */
    public Map<Long, IBeanAbstracto> getMapaSeleccionados() {
	return mapaSeleccionados;
    }

    /**
     * Metodo modificador de la variable {@link #seleccionarTodos}
     */
    public void setSeleccionarTodos(Boolean seleccionarTodos) {
	this.seleccionarTodos = seleccionarTodos;
    }

    /**
     * Metodo analizador de la variable {@link #seleccionarTodos}
     */
    public Boolean getSeleccionarTodos() {
	return seleccionarTodos;
    }

    /**
     * Metodo modificador de la variable {@link #mapaNoSeleccionados}
     */
    public void setMapaNoSeleccionados(
	    Map<Long, IBeanAbstracto> mapaNoSeleccionados) {
	this.mapaNoSeleccionados = mapaNoSeleccionados;
    }

    /**
     * Metodo analizador de la variable {@link #mapaNoSeleccionados}
     */
    public Map<Long, IBeanAbstracto> getMapaNoSeleccionados() {
	return mapaNoSeleccionados;
    }

    /**
     * Metodo modificador de la variable {@link #tipoSeleccion}
     */
    public void setTipoSeleccion(TiposSeleccion tipoSeleccion) {
	this.tipoSeleccion = tipoSeleccion;
    }

    /**
     * Metodo analizador de la variable {@link #tipoSeleccion}
     */
    public TiposSeleccion getTipoSeleccion() {
	return tipoSeleccion;
    }
}
