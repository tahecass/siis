/**
 * 
 */
package com.casewaresa.framework.util;

/**
 * @author casewaredes06
 * @name Nodos.java
 * @date 06/07/2011
 * @desc
 */


public class Nodos {
	
	    private String infor;
	    public Nodos izq;
	    public Nodos der;

	    public Nodos(){
	        infor="";
	        izq=der=null;
	    }

	    public String getInfor() {
	        return infor;
	    }

	    public void setInfor(String infor) {
	        this.infor = infor;
	    } 
}
