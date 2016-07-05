package com.casewaresa.framework.macros.contract;

import com.casewaresa.framework.dto.TreeBeanAbstracto;
import com.casewaresa.framework.util.MyItemTree;

public interface ITreeAditionalEvents extends ITreeAditionalEvent
{
    
    /**
     * @type Método de la clase ITreeAditionalEvent.java
     * @name onValidateSeleccion
     * @param parametro
     * @return
     * @desc metodo que permite validar en la seleccion de un item del arbol,
     *       deacuerdo a lo definido en el action que implemente la interfaz
     */
    boolean onValidateSeleccion(MyItemTree myItemTree,TreeBeanAbstracto parametro);

}
