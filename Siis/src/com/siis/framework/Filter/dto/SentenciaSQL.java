package com.casewaresa.framework.Filter.dto;

import java.util.List;

public class SentenciaSQL {

	private List<ColumnsMapper> columnas;
	private String view;
	private List<CriterioWhere> filtro;
	private List<OrderBy> orderBy;
	
	public SentenciaSQL() {
	}
	
	public List<ColumnsMapper> getColumnas() {
		return columnas;
	}
	public void setColumnas(List<ColumnsMapper> columnas) {
		this.columnas = columnas;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public List<CriterioWhere> getFiltro() {
		return filtro;
	}
	public void setFiltro(List<CriterioWhere> filtro) {
		this.filtro = filtro;
	}
	public List<OrderBy> getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(List<OrderBy> orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public String toString() {
		return "SentenciaSQL [columnas=" + columnas + ", view=" + view
				+ ", filtro=" + filtro + ", orderBy=" + orderBy + "]";
	}
	

	
}
