/**
 * 
 */
package com.casewaresa.framework.macros.contract;

import java.util.List;
import java.util.Map;

import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import com.casewaresa.framework.util.MyItemTree;

public interface ITreeFacadePorDemanda
{

    void buildTreePorDemanda( Treechildren tree, List<MyItemTree> listaDatos, String etiqueta, String consulta, String ids, boolean treeItemRenderMovimiento ) throws Exception;
    
    void buildTreePorDemanda( Treechildren tree, List<MyItemTree> listaDatos, String etiqueta, String consulta, String ids, boolean treeItemRenderMovimiento,Map<String, Object> parameters) throws Exception;
    
    void buildTreePorDemanda( Treechildren tree, List<MyItemTree> listaDatos, String etiqueta, String consulta, String ids, boolean treeItemRenderMovimiento,boolean isDisabled,Map<String, Object> parameters) throws Exception;
    
    
    void cargarHijosTreePorDemanda( Treeitem ti, final String sec, final String ids, final boolean treeItemRenderMovimiento );

    void cargarHijosTreePorDemanda( Treeitem ti, final String sec, final String ids, final boolean treeItemRenderMovimiento, Map<String, Object> parameters );
    
    void cargarHijosTreePorDemanda( Treeitem ti, final String sec, final String ids, final boolean treeItemRenderMovimiento, boolean isDisabled);

   
    Treeitem cargarHijosTreePorDemanda( Treechildren padre, String[] sec, String ids, boolean treeItemRenderMovimiento ) throws Exception;

    Treeitem cargarHijosTreePorDemanda( Treechildren padre, String[] sec, String ids, boolean treeItemRenderMovimiento, Map<String, Object> parameters ) throws Exception;
    
    Treeitem cargarHijosTreePorDemanda( Treechildren padre, String[] sec, String ids, boolean treeItemRenderMovimiento, boolean isDisabled,Map<String, Object> parameters ) throws Exception;

    
    MyItemTree getDataItemSelected( Treeitem itemSelected );

}