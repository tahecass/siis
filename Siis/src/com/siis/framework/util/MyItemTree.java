/**
 * ItemListaSeleccion.java
 */
package com.casewaresa.framework.util;


/**
 * @author CaseWare Ingenieria
 * @date 27/04/2009
 * @name ItemTree.java
 * @desc --
 */


public class MyItemTree{
	private String  id           	= null; // Llave Sustituta osea la secuencia
	private String  padre 	        = null;
	private String  valor 	        = null; // Llave Natural osea el código 
	private String  etiqueta        = null; // Nombre
	private Integer nivel 	        = null;
	private String  estado			= null;
	private Integer ordenHermanos   = null;
	private Integer ordenEstructura = null;
    private Integer numeroDescendientes  = null;
    private Object otherValue 		= null;
    private String parametros 	= null;
    

	public MyItemTree() {
	
	}
	
	public MyItemTree(String padre, String valor, String etiqueta, Integer nivel) {
		this.padre 		= padre;
		this.valor 		= valor;
		this.etiqueta 	= etiqueta;
		this.nivel		= nivel;
	}

	public MyItemTree(String id, String padre, String valor, String etiqueta, Integer nivel) {
		this.id			= id;
		this.padre 		= padre;
		this.valor 		= valor;
		this.etiqueta 	= etiqueta;
		this.nivel		= nivel;
	}

	public MyItemTree(String padre, String valor, String etiqueta, Integer nivel,String estado) {
		this.padre 		= padre;
		this.valor 		= valor;
		this.etiqueta 	= etiqueta;
		this.nivel		= nivel;
		this.estado		= estado;
	}
	
	public MyItemTree(String id, String padre, String valor, String etiqueta, Integer nivel, 
			String estado, Integer ordenHermanos, Integer ordenEstructura, Integer numeroDescendientes) {
		this.id = id;
		this.padre = padre;
		this.valor = valor;
		this.etiqueta = etiqueta;
		this.nivel = nivel;
		this.estado = estado;
		this.ordenHermanos = ordenHermanos;
		this.ordenEstructura = ordenEstructura;
		this.numeroDescendientes = numeroDescendientes;
	}
	
	public MyItemTree(String id, String padre, String valor, String etiqueta, Integer nivel,Object otherValue) {
		this.id			= id;
		this.padre 		= padre;
		this.valor 		= valor;
		this.etiqueta 	= etiqueta;
		this.nivel		= nivel;
		this.otherValue = otherValue;
	}
	
	public MyItemTree(String id, String padre, String valor, String etiqueta, Integer nivel,String parametros) {
		this.id			= id;
		this.padre 		= padre;
		this.valor 		= valor;
		this.etiqueta 	= etiqueta;
		this.nivel		= nivel;
		this.parametros=parametros;
	}
	
	public MyItemTree(String id, String padre, String valor, String etiqueta, Integer nivel,Object otherValue,String parametros) {
		this.id			= id;
		this.padre 		= padre;
		this.valor 		= valor;
		this.etiqueta 	= etiqueta;
		this.nivel		= nivel;
		this.otherValue = otherValue;
		this.parametros=parametros;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @type   Método de la clase ItemTree
	 * @name   getPadre
	 * @return void
	 * @param padre
	 * @desc   obtiene el valor de la propiedad padre
	 */
	public String getPadre() {
		return padre;
	}

	/**
	 * @type   Método de la clase ItemTree
	 * @name   setPadre
	 * @return String
	 * @param padre
	 * @desc   Actualiza el valor de la propiedad padre
	 */
	public void setPadre(String padre) {
		this.padre = padre;
	}

	/**
	 * @type   Método de la clase ItemTree
	 * @name   getValor
	 * @return void
	 * @param valor
	 * @desc   obtiene el valor de la propiedad valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @type   Método de la clase ItemTree
	 * @name   setValor
	 * @return String
	 * @param valor
	 * @desc   Actualiza el valor de la propiedad valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @type   Método de la clase ItemTree
	 * @name   getEtiqueta
	 * @return void
	 * @param etiqueta
	 * @desc   obtiene el valor de la propiedad etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @type   Método de la clase ItemTree
	 * @name   setEtiqueta
	 * @return String
	 * @param etiqueta
	 * @desc   Actualiza el valor de la propiedad etiqueta
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @type   Método de la clase MyItemTree
	 * @name   getNivel
	 * @return void
	 * @param nivel
	 * @desc   obtiene el valor de la propiedad nivel
	 */
	public Integer getNivel() {
		return nivel;
	}

	/**
	 * @type   Método de la clase MyItemTree
	 * @name   setNivel
	 * @return Integer
	 * @param nivel
	 * @desc   Actualiza el valor de la propiedad nivel
	 */
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getOrdenHermanos() {
		return ordenHermanos;
	}

	public void setOrdenHermanos(Integer ordenHermanos) {
		this.ordenHermanos = ordenHermanos;
	}

	public Integer getOrdenEstructura() {
		return ordenEstructura;
	}

	public void setOrdenEstructura(Integer ordenEstructura) {
		this.ordenEstructura = ordenEstructura;
	}

	public Integer getNumeroDescendientes() {
		return numeroDescendientes;
	}

	public void setNumeroDescendientes(Integer numeroDescendientes) {
		this.numeroDescendientes = numeroDescendientes;
	}
	
	public Object getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(Object otherValue) {
		this.otherValue = otherValue;
	}
	
	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (valor != null ? "[" + valor + "] " : "") +
			   (etiqueta != null ? etiqueta : "");
	}
}
