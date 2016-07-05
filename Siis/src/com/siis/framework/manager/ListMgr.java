package com.casewaresa.framework.manager;

import java.util.ArrayList;
import java.util.List;
import com.casewaresa.framework.dto.ItemListaSeleccion;

/**
 * @author CaseWare Ingenieria
 * @date 27/04/2009
 * @name OperadorManager.java
 * @desc Crea una clase que define los operadores de búsqueda en una consulta
 */
public class ListMgr extends ManagerStandard {
	/**
	 * @type   Método de la clase OperadorManager
	 * @name   obtenerOperadores
	 * @return List
	 * @return
	 * @desc   Retorna la lista de operadores definidos para mostrarlos en una lista de selección
	 */	
//	public List obtenerOperadores(){
//		List listaOperadores = new ArrayList();
//		String [] valores = null;
//		ItemListaSeleccion temp = null;
//		
//		//--obtenemos los valores del Dominio (puede ser de un contrato o de una BD)
//		valores = IListaDominios.LD_OPERADORES;
//
//		//--armamos la lista con los valores parametrizados del dominio
//		for (int i = 0; i < valores.length; i+=2){
//			temp = new ItemListaSeleccion( valores[i] , valores[i+1] );
//			listaOperadores.add( temp );
//		}
//
//		
//		return listaOperadores;		
//	}
	
	// La variable public static final String[] LD_OPERADORES = { "=", "Igual", ">=",
	// "Mayor o Igual", "like", "Like" };
	// esta en IConstantes en comentarios
	
	/**
	 * @type   Método de la clase OperadorManager
	 * @name   obtenerOperadores
	 * @return List
	 * @return
	 * @desc   Retorna la lista de operadores definidos para mostrarlos en una lista de selección
	 */	
	public List<Object> obtenerCamposBusqueda(String[] valores){
		List<Object> listaCampos = new ArrayList<Object>();
		//String [] valores = null;
		ItemListaSeleccion temp = null;
		
		//--armamos la lista con los valores parametrizados del dominio
		for (int i = 0; i < valores.length; i+=2){
			temp = new ItemListaSeleccion( valores[i] , valores[i+1] );
			listaCampos.add( temp );
		}
		
		return listaCampos;		
	}	

}
