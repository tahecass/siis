package com.siis.framework.action.impl;

/**
 * @author Futco
 * @name IActionStandardBorder.java
 * @date 2/06/2011
 * @desc
 */
public interface IActionStandardMaster<T> extends IInicializarComponentes {

	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name onConsultarMaestro
	 * @desc Consulta los Maestros
	 */
	void onConsultarMaestro();

	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name onGuardarMaestro
	 * @desc Guarda o Actualiza el Maestro
	 */
	void onGuardarMaestro();

	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name onBorrarMaestro
	 * @desc Borra un Maestro
	 */
	void onBorrarMaestro();

	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name onEditarMaestro
	 * @desc Habilita el Formulario para Editar la Información del Maestro
	 */
	void onEditarMaestro();

	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name onNuevoMaestro
	 * @desc Habilita el Formulario para Crear un Nuevo Maestro
	 */
	void onNuevoMaestro();

	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name onSeleccionarMaestro
	 * @param dto
	 * @desc Se activa al seleccionar un Maestro
	 */
	void onSeleccionarMaestro(T dto);

	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name obtenerMaestro
	 * @param dto
	 * @return
	 * @throws Exception
	 * @desc Busca y Devuelve el maestro en la DB
	 */
	T obtenerMaestro(T dto) throws Exception;
	
	/**
	 * @type Método de la clase IActionStandardMaster.java
	 * @name onCancelarMaestro
	 * @desc Metodo para cancelar la creacion de un nuevo registro en el formulario
	 */
	void onCancelarMaestro();
	
}
