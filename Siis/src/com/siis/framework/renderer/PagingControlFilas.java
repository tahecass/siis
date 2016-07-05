package com.siis.framework.renderer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.siis.framework.dto.IBeanAbstracto;

/**
 * Clase de utilidad para la creacion de filas del PagingControl.
 * Es un render que se puede utilizar tanto para {@link Listbox} como para {@link Grid}, ya que 
 * implementa las interfaces {@link RowRenderer} que es el render para el componente {@link Grid} y 
 * {@link ListitemRenderer} que es el render para el componente {@link Listbox}.
 * 
 * @author dperezc
 *
 */
public abstract class PagingControlFilas implements RowRenderer<IBeanAbstracto>,ListitemRenderer<IBeanAbstracto>{
  
    private IBeanAbstracto _registroSeleccionado;
    
    /**
     * Este metodo es el que me indica como se crean las filas ya sea row para grillas o listitem para listbox.
     * El objeto es el dato que viene en la lista de resultados con el que voy a construir la fila. Claro esta en la implementacion de este metodo 
     * se debe hacer el casting al tipo de dato que se mandando a consultar
     * 
     * @param objeto
     * @return
     */
    public abstract void render(Component component,IBeanAbstracto iBeanAbstracto);

    /**
     * render para {@link Listbox}. 
     * Hace llamado al metodo abstracto {@link #render(Component, IBeanAbstracto)} el cual se debe implementar cuando se utilice esta clase
     */
    @Override
    public void render(Listitem item, IBeanAbstracto data, int index)
	    throws Exception {
	render(item, data);
	if(data != null && data.getPrimaryKey() != null && _registroSeleccionado != null && _registroSeleccionado.getPrimaryKey() != null){
	    if(data.getPrimaryKey().equals(_registroSeleccionado.getPrimaryKey())){
		item.setSelected(true);
	    }else{
		item.setSelected(false);
	    }
	}else{
	    item.setSelected(false);
	}
    }

    
    /**
     * render para {@link Grid }
     * Hace llamado al metodo abstracto {@link #render(Component, IBeanAbstracto)} el cual se debe implementar cuando se utilice esta clase
     */
    @Override
    public void render(Row row, IBeanAbstracto data, int index)
	    throws Exception {
	// TODO Auto-generated method stub
	render(row, data);
    }

    public void setRegistroSeleccionado(
	    IBeanAbstracto registroSeleccionado) {
	this._registroSeleccionado = registroSeleccionado;
    }

    public IBeanAbstracto getRegistroSeleccionado() {
	return _registroSeleccionado;
    }
    
    
    
}
