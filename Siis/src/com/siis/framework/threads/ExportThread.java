package com.casewaresa.framework.threads;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import com.casewaresa.framework.action.PopupReporteAction;

public class ExportThread extends Thread{
	private final Desktop _desktop;
	private final PopupReporteAction win;
	public Logger log = Logger.getLogger(this.getClass());
	public ExportThread(Desktop desktop, PopupReporteAction win){
		this._desktop = desktop;
		this.win = win;
	}
	
    public void run(){
    	try{
    		if (_desktop.isServerPushEnabled()) {
	    		Executions.activate(_desktop);
	    		Clients.showBusy("Executing Report...");
	    		this.win.doOverlapped();
	    		Executions.deactivate(_desktop);
    		}
    		Executions.activate(_desktop);
            Clients.showBusy(null);
            Executions.deactivate(_desktop);
       }catch (Exception e){ 
           log.error(e.getMessage(),e);
    	   System.out.println("Mensaje de Error:" + e.getMessage());
       }finally {
           _desktop.enableServerPush(false);
       } 
    }   
}
