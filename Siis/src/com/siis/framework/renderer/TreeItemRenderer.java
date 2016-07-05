package com.casewaresa.framework.renderer;

import org.apache.log4j.Logger;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.casewaresa.framework.util.MyItemTree;

public class TreeItemRenderer implements TreeitemRenderer<Object> {

	protected static Logger log = Logger.getLogger(TreeItemRenderer.class);

	public TreeItemRenderer() {

	}

	public void render(Treeitem item, Object data) throws Exception {

		DefaultTreeNode<?> t = (DefaultTreeNode<?>) data;

		MyItemTree itemTree = (MyItemTree) t.getData();

		
		Treecell tcEtiqueta = new Treecell(itemTree.getEtiqueta());
		tcEtiqueta.setTooltiptext(itemTree.getEtiqueta());
		
		
		
		Treecell tcValor = new Treecell(itemTree.getValor() != null ? itemTree.getValor().trim() : null);
		Treecell tcPadre = new Treecell(itemTree.getPadre() != null ? itemTree.getPadre().trim() : null);
		Treecell tcNivel = new Treecell(itemTree.getNivel().toString());
		Treecell tcLlave = new Treecell(itemTree.getId() != null ? itemTree.getId().trim() : null);
		
		 Treerow tr = null;
		if (item.getTreerow() == null) {
			tr = new Treerow();
			tr.setParent(item);
		} else {
			tr = item.getTreerow();
			tr.getChildren().clear();
		}
		
		tr.setAttribute("valor",itemTree.getValor());
		tr.setAttribute("padre",itemTree.getPadre());
		tr.setAttribute("nivel",itemTree.getNivel());
		tr.setAttribute("llave",itemTree.getId());
	
	
		
		tcEtiqueta.setParent(tr);
		tcValor.setParent(tr);
		tcPadre.setParent(tr);
		tcNivel.setParent(tr);
		tcLlave.setParent(tr);
		
		if(itemTree.getNivel()>0)
        	item.setOpen(false);
        else
        	item.setOpen(true);
	}

	@Override
	public void render(Treeitem item, Object data, int index) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}