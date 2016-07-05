/**
 * 
 */
package com.casewaresa.framework.facade;

/**
 * @author Casewaredes
 * @name TreeFacadeGruposPorDemanda.java
 * @date 1/11/2011
 * @desc
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.casewaresa.framework.macros.contract.ITreeFacadePorDemanda;
import com.casewaresa.framework.util.MyItemTree;
import com.casewaresa.iceberg_cg.facade.ParametrizacionFac;

@SuppressWarnings("unchecked")
public class TreeFacadeGruposPorDemanda implements ITreeFacadePorDemanda{

	/** desc: Esta clase es singlenton */
	private static final TreeFacadeGruposPorDemanda pTreeFacade = new TreeFacadeGruposPorDemanda();
	
	protected static Logger log = Logger.getLogger(TreeFacadePorDemanda.class);

	
	private TreeFacadeGruposPorDemanda() {
		super();
	}

	public static TreeFacadeGruposPorDemanda getFacade() {
		return pTreeFacade;
	}


	/****************************************************************************************/
	/** METODOS DE LA FACHADA **/
	/****************************************************************************************/
	
	public void buildTreePorDemanda(Treechildren tree, List<MyItemTree> listaDatos, 
			String etiqueta, String consulta, String ids, boolean treeItemRenderMovimiento) throws Exception {
		log.info("Ejecutando metodo [ buildTree(" + tree.getId() 
				+ ", No. Reg: " + listaDatos.size() + ") ]... ");
		
		Treechildren raiz = new Treechildren();
		tree.getChildren().clear();
		
		Treeitem ti = new Treeitem();
		Treerow tr = new Treerow();
		Treecell tcValor = new Treecell("");
		Treecell tcPadre = new Treecell("");
		Treecell tcNivel = new Treecell("0");
		Treecell tcLlave = new Treecell("0");
		Treecell tcEtiqueta = new Treecell(etiqueta);
		Treecell tcMovimiento = new Treecell("");
		Treecell tcIncluyeExcluye = new Treecell("");
		tcIncluyeExcluye.setVisible(false);
		tcMovimiento.setVisible(false);
		
		tr.setAttribute("valor","");
		tr.setAttribute("padre","");
		tr.setAttribute("nivel",0);
		tr.setAttribute("llave",0);
		tr.setAttribute("movimiento","");
		tr.setAttribute("parametros","");
	
		tr.appendChild(tcEtiqueta);
		tr.appendChild(tcValor);		
		tr.appendChild(tcPadre);
		tr.appendChild(tcNivel);
		tr.appendChild(tcLlave);
		tr.appendChild(tcMovimiento);
		tr.appendChild(tcIncluyeExcluye);
		
		ti.appendChild(tr);
		ti.appendChild(raiz);
		tree.appendChild(ti);
	
		log.info("EDITAR : "+treeItemRenderMovimiento);	
		
		
		for (final MyItemTree itemTree : listaDatos) {
			log.info("PARAMETROS : "+itemTree.getParametros());	
			
			tcEtiqueta = new Treecell(itemTree.getEtiqueta());
			tcEtiqueta.setTooltiptext(itemTree.getEtiqueta());
			
			if(itemTree.getOtherValue() != null && itemTree.getOtherValue().toString().equals("N")){			
				tcEtiqueta.setStyle("color:#BDBDBD;font-weight: bold");
			}
			
			tcValor = new Treecell(itemTree.getValor() != null ? itemTree.getValor().trim() : null);
			tcPadre = new Treecell(itemTree.getPadre() != null ? itemTree.getPadre().trim() : null);
			tcNivel = new Treecell(itemTree.getNivel().toString());
			tcLlave = new Treecell(itemTree.getId() != null ? itemTree.getId().trim() : null);
			tcMovimiento = new Treecell(itemTree.getOtherValue() != null ? itemTree.getOtherValue().toString() : null);
			tcMovimiento.setVisible(false);
			tcIncluyeExcluye = new Treecell(itemTree.getParametros()!=null? itemTree.getParametros():null);
			tcIncluyeExcluye.setVisible(false);
	
			tr = new Treerow();
			tr.setAttribute("valor",itemTree.getValor());
			tr.setAttribute("padre",itemTree.getPadre());
			tr.setAttribute("nivel",itemTree.getNivel());
			tr.setAttribute("llave",itemTree.getId());
			tr.setAttribute("movimiento",itemTree.getOtherValue());
			tr.setAttribute("parametros",itemTree.getParametros());
		     
			if(itemTree.getParametros()!= null){	
				if(itemTree.getParametros().equals("I")){
					tr.setStyle("background-color:#E3F6CE");
	
				}else if(itemTree.getParametros().equals("E")){
					tr.setStyle("background-color:#F6CECE");
			
				}				
			}
			
			if(!treeItemRenderMovimiento){// valida si se debe habilitar o no el componente dragdroptree
				tr.setDroppable("true");
			    tr.setDraggable("true");
			}
			
			tr.appendChild(tcEtiqueta);
			tr.appendChild(tcValor);
			tr.appendChild(tcPadre);
			tr.appendChild(tcNivel);
			tr.appendChild(tcLlave);
			tr.appendChild(tcMovimiento);
			tr.appendChild(tcIncluyeExcluye);
			
			ti = new Treeitem();
			ti.setAttribute("Consulta", consulta);
			ti.setAttribute("grupo", tree.getAttribute("grupo"));
			ti.setId(ids+itemTree.getId());
			ti.appendChild(tr);
			raiz.appendChild(ti);
			
			if(itemTree.getNumeroDescendientes() > 0){
				cargarHijosTreePorDemanda(ti, itemTree.getId(), ids, treeItemRenderMovimiento);
			}		
		}
	}
	
	public void cargarHijosTreePorDemanda(Treeitem ti, final String sec, 
			final String ids, final boolean treeItemRenderMovimiento){
		log.info("Ejecutando el metodo [void cargarHijosTreePorDemanda...]");
		
		final String consulta = ti.getAttribute("Consulta").toString();
		final Treechildren hijo = new Treechildren();
		final String grupo =  ti.getAttribute("grupo").toString();
		final Map<String, Object>parameter = new HashMap<String, Object>();
		parameter.put("GRUPO",grupo);
		parameter.put("PADRE", sec);
		ti.appendChild(hijo);
		ti.setOpen(false);
		ti.addEventListener("onOpen", new EventListener<Event>() {
			public void onEvent(Event arg0) throws Exception {
				OpenEvent oe = (OpenEvent) arg0;
				if(oe.isOpen()){
					List<MyItemTree> listaHijos = (List<MyItemTree>) ParametrizacionFac.getFacade()
					.obtenerListado(consulta, parameter);
					hijo.getChildren().clear();
					for (MyItemTree myItemTree : listaHijos) {
						Treeitem tiHijo = new Treeitem();
						Treerow trow = new Treerow();
						Treecell tcEti = new Treecell(myItemTree.getEtiqueta());
						
						if(!treeItemRenderMovimiento){// valida si se debe habilitar o no el componente dragdroptree
							trow.setDroppable("true");
							trow.setDraggable("true");
						}
						
						if(myItemTree.getParametros()!= null){
							if(myItemTree.getParametros().equals("I")){
								trow.setStyle("background-color:#E3F6CE");
	
							}else if(myItemTree.getParametros().equals("E")){
								trow.setStyle("background-color:#F6CECE");
						
							}
						}
						if(myItemTree.getOtherValue() != null && myItemTree.getOtherValue().toString().equals("N")){			
							tcEti.setStyle("color:#BDBDBD;font-weight: bold");
						}
						
						tcEti.setTooltiptext(myItemTree.getEtiqueta());
						
						trow.appendChild(tcEti);
						trow.appendChild(new Treecell(myItemTree.getValor() != null ? myItemTree.getValor().trim() : null));
						trow.appendChild(new Treecell(myItemTree.getPadre() != null ? myItemTree.getPadre().trim() : null));
						trow.appendChild(new Treecell(myItemTree.getNivel().toString()));
						trow.appendChild(new Treecell(myItemTree.getId() != null ? myItemTree.getId().trim() : null));
						trow.appendChild(new Treecell(myItemTree.getOtherValue() != null ? myItemTree.getOtherValue().toString().trim(): null));
						trow.appendChild(new Treecell(myItemTree.getParametros() != null ? myItemTree.getParametros().trim() : null));

						trow.setAttribute("valor",myItemTree.getValor());
						trow.setAttribute("padre",myItemTree.getPadre());
						trow.setAttribute("nivel",myItemTree.getNivel());
						trow.setAttribute("llave",myItemTree.getId());
						trow.setAttribute("movimiento",myItemTree.getOtherValue());
						trow.setAttribute("parametros",myItemTree.getParametros());
						
						tiHijo.appendChild(trow);
						tiHijo.setId(ids+myItemTree.getId());
						tiHijo.setAttribute("Consulta", consulta);
						tiHijo.setAttribute("grupo", grupo);
						hijo.appendChild(tiHijo);
						if(myItemTree.getNumeroDescendientes() > 0){
							cargarHijosTreePorDemanda(tiHijo, myItemTree.getId(),ids, treeItemRenderMovimiento);
						}
						
					}
				}
			}
		});
	}
	
	public Treeitem cargarHijosTreePorDemanda(Treechildren padre,String[] sec, 
			String ids, boolean treeItemRenderMovimiento) throws Exception{
		log.info("Ejecutando el metodo [Treeitem cargarHijosTreePorDemanda...]");
		
		Component itemSelected = null;
		List<Component> listItem = padre.getChildren();
		Map<String, Object>parameter = new HashMap<String, Object>();
		parameter.put("GRUPO", padre.getAttribute("grupo").toString());
		if(sec != null){
			List<Component> arbol = ((Treechildren)((Treeitem)listItem.get(0)).getChildren().get(1)).getChildren();
			for (int i = (sec.length - 1); i > 0; i--) {
				for (Component itemTree : arbol) {
					if(itemTree.getId().trim().equals(ids+sec[i])){
						if(i==1){
							((Treeitem)itemTree).setSelected(true);
							itemSelected = itemTree;
							break;
						}else{
							parameter.put("PADRE", sec[i]);
							List<MyItemTree> listaHijos = (List<MyItemTree>) ParametrizacionFac.getFacade()
							.obtenerListado(itemTree.getAttribute("Consulta").toString(), parameter);
							Treechildren tchil = (Treechildren) itemTree.getChildren().get(1);
							tchil.getChildren().clear();
							for (MyItemTree myItemTree : listaHijos) {
								Treeitem tiHijo = new Treeitem();
								Treerow trow = new Treerow();
								Treecell tcEti = new Treecell(myItemTree.getEtiqueta());
								
								if(!treeItemRenderMovimiento){// valida si se debe habilitar o no el componente dragdroptree
									trow.setDroppable("true");
									trow.setDraggable("true");
								}
								if(myItemTree.getParametros()!= null){
									if(myItemTree.getParametros().equals("I")){
										trow.setStyle("background-color:#E3F6CE");
	
									}else if(myItemTree.getParametros().equals("E")){
										trow.setStyle("background-color:#F6CECE");
								
									}
								}
								if(myItemTree.getOtherValue() != null && myItemTree.getOtherValue().toString().equals("N")){			
									tcEti.setStyle("color:#BDBDBD;font-weight: bold");
								}
								
								tcEti.setTooltiptext(myItemTree.getEtiqueta());
								trow.appendChild(tcEti);
								trow.appendChild(new Treecell(myItemTree.getValor() != null ? myItemTree.getValor().trim() : null));
								trow.appendChild(new Treecell(myItemTree.getPadre() != null ? myItemTree.getPadre().trim() : null));
								trow.appendChild(new Treecell(myItemTree.getNivel().toString()));
								trow.appendChild(new Treecell(myItemTree.getId() != null ? myItemTree.getId().trim() : null));
								trow.appendChild(new Treecell(myItemTree.getOtherValue() != null ? myItemTree.getOtherValue().toString().trim(): null));
								trow.appendChild(new Treecell(myItemTree.getParametros() != null ? myItemTree.getParametros().trim() : null));

								trow.setAttribute("valor",myItemTree.getValor());
								trow.setAttribute("padre",myItemTree.getPadre());
								trow.setAttribute("nivel",myItemTree.getNivel());
								trow.setAttribute("llave",myItemTree.getId());
								trow.setAttribute("movimiento",myItemTree.getOtherValue());
								trow.setAttribute("parametros",myItemTree.getParametros());

								tiHijo.appendChild(trow);
								tiHijo.setId(ids+myItemTree.getId());
								tiHijo.setAttribute("Consulta", itemTree.getAttribute("Consulta"));
								tiHijo.setAttribute("grupo", itemTree.getAttribute("grupo"));
								if(padre.hasFellow(ids+myItemTree.getId()))
									padre.getFellow(ids+myItemTree.getId());
								tchil.appendChild(tiHijo);
								if(myItemTree.getNumeroDescendientes() > 0){
									cargarHijosTreePorDemanda(tiHijo, myItemTree.getId(),ids,treeItemRenderMovimiento);
								}
								
							}
							((Treeitem)itemTree).setOpen(true);	
							arbol = ((Treechildren)itemTree.getChildren().get(1)).getChildren();
							break;
						}
					}
				}
			}
		}else{
			itemSelected = (Treeitem)listItem.get(0);
			((Treeitem)listItem.get(0)).setSelected(true);
		}
		return ((Treeitem)itemSelected);
	}
	
	public MyItemTree getDataItemSelected(Treeitem itemSelected){
		log.info("Ejecutando método [ getDataItemSelected ]...");
		MyItemTree myItemTree = new MyItemTree();
		myItemTree.setId(itemSelected.getFirstChild().getAttribute("llave").toString());
		myItemTree.setValor(itemSelected.getFirstChild().getAttribute("valor").toString());
		myItemTree.setEtiqueta(((Treecell) ((Treerow)itemSelected.getFirstChild())
				.getChildren().get(0)).getLabel());
		myItemTree.setNivel(Integer.valueOf(itemSelected.getFirstChild().getAttribute("nivel").toString()));
		
		return myItemTree;
	}

    @Override
    public void buildTreePorDemanda( Treechildren tree, List<MyItemTree> listaDatos, String etiqueta, String consulta, String ids, boolean treeItemRenderMovimiento, Map<String, Object> parameters ) throws Exception
    {
       
        
    }

    @Override
    public void cargarHijosTreePorDemanda( Treeitem ti, String sec, String ids, boolean treeItemRenderMovimiento, Map<String, Object> parameters )
    {
       
        
    }

    @Override
    public Treeitem cargarHijosTreePorDemanda( Treechildren padre, String[] sec, String ids, boolean treeItemRenderMovimiento, Map<String, Object> parameters ) throws Exception
    {
       
        return null;
    }

	@Override
	public void buildTreePorDemanda(Treechildren tree,
			List<MyItemTree> listaDatos, String etiqueta, String consulta,
			String ids, boolean treeItemRenderMovimiento, boolean isDisabled,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarHijosTreePorDemanda(Treeitem ti, String sec, String ids,
			boolean treeItemRenderMovimiento, boolean isDisabled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Treeitem cargarHijosTreePorDemanda(Treechildren padre, String[] sec,
			String ids, boolean treeItemRenderMovimiento, boolean isDisabled,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
