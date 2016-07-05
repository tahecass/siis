/**
 * 
 */
package com.casewaresa.framework.macros.contract;

import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.util.MyItemTree;


/**
 * @author ejulio
 * @name IGroupboxTreeAditionalEvent
 * @date 31/05/2012
 * @desc
 */

public interface IGroupboxTreeAditionalEvent {

	boolean onValidateSeleccion(MyItemTree myItemTree,IBeanAbstracto objeto);
	
}