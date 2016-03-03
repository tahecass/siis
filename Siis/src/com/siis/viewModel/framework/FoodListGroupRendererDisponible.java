package com.siis.viewModel.framework;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listgroupfoot;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.siis.dto.Disponible;

public class FoodListGroupRendererDisponible  implements ListitemRenderer<Object> {
		 
	    @Override
	    public void render(Listitem listitem, Object obj, int index) throws Exception {
	 
	        if (listitem instanceof Listgroup) {
	            FoodGroupsModel.FoodGroupInfo groupInfo = (FoodGroupsModel.FoodGroupInfo) obj;
	            Food food = groupInfo.getFirstChild();
	            String groupTxt;
	            switch (groupInfo.getColIndex()) {
	            case 0:
	                groupTxt = food.getCategory();
	                break;
	            case 1:
	                groupTxt = food.getName();
	                break;
	            case 2:
	                groupTxt = food.getTopNutrients();
	                break;
	            case 3:
	                groupTxt = food.getDailyPercent().toString();
	                break;
	            case 4:
	                groupTxt = food.getCalories().toString();
	                break;
	            case 5:
	                groupTxt = food.getQuantity();
	                break;
	            default:
	                groupTxt = food.getCategory();
	            }
	            listitem.appendChild(new Listcell(groupTxt));
	            listitem.setValue(obj);
	        } else if (listitem instanceof Listgroupfoot) {
	            Listcell cell = new Listcell();
	            cell.setSclass("foodFooter");
	            cell.setSpan(6);
	            cell.appendChild(new Label("Total " + obj + " Items"));
	            listitem.appendChild(cell);
	        } else {
	            Disponible data = (Disponible) obj;
	            listitem.appendChild(new Listcell(data.getCuenta().getNumeroCuenta()));
	            listitem.appendChild(new Listcell(data.getCuenta().getTipo()));
	            listitem.appendChild(new Listcell( )); 
	            listitem.setValue(data);
	        }
	 
	    }
	 
	}