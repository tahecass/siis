package com.casewaresa.framework.renderer;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.casewaresa.framework.dto.IDetalleGrupo;
import com.casewaresa.framework.util.MyItemTree;



public class TreeItemRenderDetallesGrupos implements ITreeItemRenderGrupos {

	protected static Logger log = Logger.getLogger(TreeItemRenderer.class);
	private List <IDetalleGrupo> lista;
	private boolean deshabilitar;
	

	public TreeItemRenderDetallesGrupos() {}
	

	public void render(Treeitem item, Object data) throws Exception {
        log.info("dibujando item en render");
		DefaultTreeNode<?> t = (DefaultTreeNode<?>) data;

		MyItemTree itemTree = (MyItemTree) t.getData();

		
		Treecell tcEtiqueta = new Treecell(itemTree.getEtiqueta());
		tcEtiqueta.setTooltiptext(itemTree.getEtiqueta());
		
		Treecell tcValor = new Treecell(itemTree.getValor() != null ? itemTree.getValor().trim() : null);
		Treecell tcPadre = new Treecell(itemTree.getPadre() != null ? itemTree.getPadre().trim() : null);
		Treecell tcNivel = new Treecell(itemTree.getNivel().toString());
		Treecell tcLlave = new Treecell(itemTree.getId() != null ? itemTree.getId().trim() : null);
		Treecell tcMovimiento = new Treecell(itemTree.getOtherValue() != null ? itemTree.getOtherValue().toString() : null);
	
		
		final Treerow tr = new Treerow();
		 tr.setParent(item);
		
		 
		if(itemTree.getNivel()!=0){
			if(!deshabilitar){
				tr.setDroppable("true");
				tr.setDraggable("true");
			}
			if(itemTree.getOtherValue() != null && itemTree.getOtherValue().toString().equals("N")){			
				tcEtiqueta.setStyle("color:#BDBDBD;font-weight: bold");
			}
			if(lista != null && !lista.isEmpty() ){
				for (IDetalleGrupo det: lista) {
					
					if(det.getComponente().getPrimaryKey().toString().equals(itemTree.getId())){
						log.info("entro... ");
						if(det.getIncluyeExcluye().equals("I")){
							tr.setStyle("background-color:#E3F6CE");
	
						}else if(det.getIncluyeExcluye().equals("E")){
							tr.setStyle("background-color:#F6CECE");
							//tr.setDroppable("false");
							//tr.setDraggable("false");
						}
					
				    }
				    	
				}
			}
		}

		tr.setAttribute("valor",itemTree.getValor());
		tr.setAttribute("padre",itemTree.getPadre());
		tr.setAttribute("nivel",itemTree.getNivel());
		tr.setAttribute("llave",itemTree.getId());
		tr.setAttribute("movimiento",itemTree.getOtherValue());
		
		tcEtiqueta.setParent(tr);
		tcValor.setParent(tr);
		tcPadre.setParent(tr);
		tcNivel.setParent(tr);
		tcLlave.setParent(tr);
		tcMovimiento.setParent(tr);
		
		if(itemTree.getNivel()>0)
        	item.setOpen(false);
        else
        	item.setOpen(true);
	}


	/* (non-Javadoc)
	 * @see com.casewaresa.framework.renderer.ITreeItemRenderGrupos#getList()
	 */
	public List<IDetalleGrupo> getList() {
		
		return this.lista;
	}


	/* (non-Javadoc)
	 * @see com.casewaresa.framework.renderer.ITreeItemRenderGrupos#setList(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void setList(@SuppressWarnings("rawtypes") List list) {
		lista = list;
		
	}


	/* (non-Javadoc)
	 * @see com.casewaresa.framework.renderer.ITreeItemRenderGrupos#getDeshabilitar()
	 */
	public boolean getDeshabilitar() {
		return this.deshabilitar;
	}


	/* (non-Javadoc)
	 * @see com.casewaresa.framework.renderer.ITreeItemRenderGrupos#setDeshabilitar(boolean)
	 */
	public void setDeshabilitar(boolean deshabilitar) {
		this.deshabilitar = deshabilitar;
		
	}


	@Override
	public void render(Treeitem item, Object data, int index) throws Exception {
	
		
	}


	
}