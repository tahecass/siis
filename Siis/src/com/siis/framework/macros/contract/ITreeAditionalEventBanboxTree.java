/**
 * 
 */
package com.casewaresa.framework.macros.contract;

import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.util.MyItemTree;

/**
 * @author msuevis
 * @name ITreeAditionalEventBanboxTree.java
 * @date 25/05/2011
 * @desc
 */

public interface ITreeAditionalEventBanboxTree {

	  boolean onValidateSeleccion(MyItemTree myItemTree,IBeanAbstracto objeto,String ids);
	  
}
