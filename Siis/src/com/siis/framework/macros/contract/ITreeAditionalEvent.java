/**
 * 
 */
package com.casewaresa.framework.macros.contract;

import java.util.Map;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.dto.TreeBeanAbstracto;

/**
 * @author msuevis
 * @name ITreeAditionalEvent.java
 * @date 3/05/2011
 * @desc
 */

public interface ITreeAditionalEvent extends IInicializarComponentes{

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onNuevoMaestro
	 * @desc metodo que permite cargar componentes por defecto del formulario
	 *       maestro.
	 */
	void onNuevoMaestro(int nivel);

	void onValidateSeleccionTreeInicial();

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onValidateSelecionTree
	 * @param parametro
	 * @return
	 * @desc metodo que permite validar en la seleccion de un item del arbol,
	 *       deacuerdo a lo definido en el action que implemente la interfaz
	 */
	boolean onValidateSelecionTree(TreeBeanAbstracto parametro);
	
	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onCargarDatosFormulario
	 * @param parametro
	 * @desc metodo que permite cargar los datos de del formulario apartir de
	 *       los datos resividos del action del componente
	 */
	void onCargarDatosFormulario(TreeBeanAbstracto parametro);

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onValidateSubirOrden
	 * @param parametro
	 * @return
	 * @desc metodo que permite validar si se puede o no realizar la accion de
	 *       acuerdo a la validacion definida en el action en el cual se
	 *       implementa esta interfaz
	 */
	boolean onValidateSubirOrden(Long objAct, Long objAnt);

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onValidateBajarOrden
	 * @param parametro
	 * @return
	 * @desc metodo que permite validar si se puede o no realizar la operacion
	 *       de acuerdo a la validacion definida en el action en el cual se
	 *       implementa esta interfaz.
	 */
	boolean onValidateBajarOrden(Long objAct, Long objAnt);

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onValidateSubirNivel
	 * @param parametro
	 * @return
	 * @desc metodo que permite validar si se puede o no realizar la operacion
	 *       de acuerdo a la validacion definida en el action en el cual se
	 *       implementa esta interfaz.
	 */
	boolean onValidateSubirNivel(Long objAct, Long objAnt);

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onValidateBajarNivel
	 * @param parametro
	 * @return
	 * @desc metodo que permite validar si se puede o no realizar la operacion
	 *       de acuerdo a la validacion definida en el action en el cual se
	 *       implementa esta interfaz.
	 */
	boolean onValidateBajarNivel(Long objAct, Long objAnt);

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onBorrarMaestro
	 * @param treeItem
	 * @desc metodo que permite borrar los elementos seleccionados en el arbol
	 */
	boolean onBorrarMaestro(Map<String, String> parametros) throws Exception;

	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onGuardarMaestro
	 * @param parametros
	 * @return
	 * @throws Exception
	 * @desc metodo que permite hacer la operacion de guardar
	 */
	TreeBeanAbstracto onGuardarMaestro(Long orderSiblings) throws Exception;

	/**
	 * Cancelar accion de creacion o edicion de registro
	 * 
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onCancelarMaestro
	 */
	void onCancelarMaestro();
	
	
	/**
	 * @type Método de la clase ITreeAditionalEvent.java
	 * @name onEditarMaestro
	 * @desc metodo para la edicion de maestros en el editar
	 */
	void onEditarMaestro();

}