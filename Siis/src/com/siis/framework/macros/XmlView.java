package com.casewaresa.framework.macros;

import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.assembler.AssemblerStandard;

public class XmlView extends Window implements AfterCompose, IInicializarComponentes {

	private static final long serialVersionUID = 6257470411055794200L;
	public Logger log = Logger.getLogger(this.getClass());
	
	private Tree idXMLVZTreeXmlView;
	private Treechildren idXMLVZTreeChildrenXmlView;
	private Treecol idXMLVTreeColsEsttructura;
	private Listbox idXMLVZLbxAttribValue;
	private Listheader idXMLVZLhrAtributo;
	private Listheader idXMLVZLhrValor;
	private Map<String, Object> parametros;
	private Element xml;
	Treeitem root;
	Treechildren hijos;
	private AssemblerStandard assembler;
	@Override
	public void afterCompose() {
	    // TODO Auto-generated method stub
	    cargarComponentesVista();
	    initOtherParameter();
	    cargarArbol(xml);
	}

	
	public void cargarEventosArbol(Tree root){
		
		root.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onEvent(Event arg0) throws Exception {
				log.info("log info.......");
//				 onCargarAtributos((List<Attribute>)root.getSelectedItem().getValue()); 
					if((List<Attribute>)root.getSelectedItem().getValue()!=null){
						log.info(root.getSelectedItem().getValue().toString());
						onCargarAtributos((List<Attribute>)root.getSelectedItem().getValue()); 
					}

			}
		});
	}

	@Override
	public void cargarComponentesVista() {
	    
	    idXMLVZTreeXmlView = (Tree) this.getFellow("idXMLVZTreeXmlView");
		
	    idXMLVZTreeChildrenXmlView = (Treechildren) this.getFellow("idXMLVZTreeChildrenXmlView");
	    idXMLVTreeColsEsttructura = (Treecol) this.getFellow("idXMLVTreeColsEsttructura");
	    idXMLVZLbxAttribValue = (Listbox) this.getFellow("idXMLVZLbxAttribValue");
	    idXMLVZLhrAtributo = (Listheader) this.getFellow("idXMLVZLhrAtributo");
	    idXMLVZLhrValor = (Listheader) this.getFellow("idXMLVZLhrValor");
	}
	
	@SuppressWarnings("unchecked")
	public void initOtherParameter(){
		log.info("Ejecutando el metodo[initOtherParameter]");
		
		this.parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		if(this.parametros.get("CAPTION")!=null){
			this.setTitle(this.parametros.get("CAPTION").toString());
		}else {
			
			this.setTitle("Descripciòn Acumulado");
		}
		idXMLVZLhrAtributo.setLabel(this.parametros.get("LABEL_ATRIBUTO").toString());
		idXMLVZLhrValor.setLabel(this.parametros.get("LABEL_VALOR").toString());
		idXMLVTreeColsEsttructura.setLabel(this.parametros.get("LABEL_ESTRUCTURA").toString());
		
		xml = (Element) this.parametros.get("XML");
		
		root = new Treeitem(parametros.get("RAIZ").toString());
		
		root.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		    @Override
		    public void onEvent(Event arg0)throws Exception {
			idXMLVZLbxAttribValue.getItems().clear(); 
		    }
		});
		
		hijos = new Treechildren();
		root.appendChild(hijos);
		idXMLVZTreeChildrenXmlView.appendChild(root);
		
		idXMLVZTreeXmlView.appendChild(idXMLVZTreeChildrenXmlView);
		cargarEventosArbol(idXMLVZTreeXmlView);
	}
	
	@SuppressWarnings("unchecked")
	public void cargarArbol(Element object){
	    log.info("Ejecutando el metodo [cargarArbol...]");
		try {	
		    if(object.getChildren().size()>0){
			Treeitem ti = new Treeitem(object.getName(),object.getAttributes());
			agregarEvent(ti);
			hijos.appendChild(ti);
			Treechildren tcHijos = new Treechildren();
			ti.appendChild(tcHijos);
			recorrerElements(object.getChildren(),tcHijos);
		    }    
		} catch (Exception e) {
		    log.error(e.getMessage(),e);
		}
	}
	

	@SuppressWarnings("unchecked")
	public void recorrerElements(List<Element> listElements,Treechildren hijos){
	    for(Element element : listElements){
		if(element.getChildren().size()>0){
		    Treeitem ti = new Treeitem(element.getName(),element.getAttributes());
		    agregarEvent(ti);
		    hijos.appendChild(ti);
		    Treechildren tcHijos = new Treechildren();
		    ti.appendChild(tcHijos);
		    recorrerElements(element.getChildren(), tcHijos);
		}else{
		    Treeitem ti = new Treeitem(element.getName(), element.getAttributes());
		    agregarEvent(ti);
		    hijos.appendChild(ti);
		}    
	    }
	}
	
	public void agregarEvent(final Treeitem item){
		log.info("metodo agregarEvent");
	
	    item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		@SuppressWarnings("unchecked")
		@Override
		public void onEvent(Event arg0)throws Exception {
			log.info("metodo agregarEvent");
		   onCargarAtributos((List<Attribute>)item.getValue()); 
		}
	    });
	    
		
	
	    
	}
	public AssemblerStandard getAssemblerStandard(){
	    if(assembler == null){
		assembler = new AssemblerStandard();
	    }
	    return assembler;
	}
	
	public void onCargarAtributos(List<Attribute> listAtributos){
		log.info("metodo onCargarAtributos");
	    idXMLVZLbxAttribValue.getItems().clear();
	    for (Attribute attribute : listAtributos) {
		Listitem item = getAssemblerStandard().crearItemListaDesdeDto(
			null, attribute.getName(),attribute.getValue());
		
	
		
		idXMLVZLbxAttribValue.appendChild(item);
	    }
	}
}