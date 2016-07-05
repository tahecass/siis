package com.casewaresa.framework.pivottable;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.zkoss.pivot.Calculator;
import org.zkoss.pivot.PivotField;
import org.zkoss.pivot.PivotHeaderContext;
import org.zkoss.pivot.PivotRenderer;
import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.impl.SimplePivotRenderer;
import org.zkoss.pivot.impl.StandardCalculator;
import org.zkoss.pivot.impl.TabularPivotModel;

import com.casewaresa.framework.util.Utilidades;

public class PrivotModelSaldoDetallado {

    public static final PrivotModelSaldoDetallado INSTANCE = new PrivotModelSaldoDetallado();

    private PrivotModelSaldoDetallado() {
    }

    public TabularPivotModel build() {
	return new TabularPivotModel(PivotData.obtenerDatos(),
		PivotData.getColumns());
    }

    // configurator //

    public PivotConfigurator getDefaultConfigurator() {
	return CONFIG_PERIODO;
    }

    public PivotConfigurator[] getConfigurators() {
	return new PivotConfigurator[] { CONFIG_PERIODO };
    }

    public final PivotConfigurator CONFIG_PERIODO = new PivotConfigurator(
	    "Saldos x Período") {
	public void configure(TabularPivotModel model) {
	    model.setFieldType("NOMBRE_PERIODO", PivotField.Type.COLUMN);
	    model.setFieldType("NOMBRE_FONDO", PivotField.Type.COLUMN);
	    model.setFieldType("NOMBRE_UNIDAD_PRESUPUESTO", PivotField.Type.ROW);
	    model.setFieldType("NOMBRE_UNIDAD_EJECUCION", PivotField.Type.ROW);
	    model.setFieldType("NOMBRE_ACTIVIDAD", PivotField.Type.ROW);
	    model.setFieldType("NOMBRE_CONCEPTO", PivotField.Type.ROW);
	    model.setFieldType("ENERO", PivotField.Type.DATA);
	    model.setFieldType("FEBRERO", PivotField.Type.DATA);
	    model.setFieldType("MARZO", PivotField.Type.DATA);
	    model.setFieldType("ABRIL", PivotField.Type.DATA);
	    model.setFieldType("MAYO", PivotField.Type.DATA);
	    model.setFieldType("JUNIO", PivotField.Type.DATA);
	    model.setFieldType("JULIO", PivotField.Type.DATA);
	    model.setFieldType("AGOSTO", PivotField.Type.DATA);
	    model.setFieldType("SEPTIEMBRE", PivotField.Type.DATA);
	    model.setFieldType("OCTUBRE", PivotField.Type.DATA);
	    model.setFieldType("NOVIEMBRE", PivotField.Type.DATA);
	    model.setFieldType("DICIEMBRE", PivotField.Type.DATA);

	    model.setFieldSubtotals("CODIGO_PERIODO", new StandardCalculator[] {
		    StandardCalculator.AVERAGE, StandardCalculator.COUNT });
	    model.setFieldSubtotals("CODIGO_FONDO", new StandardCalculator[] {
		    StandardCalculator.AVERAGE, StandardCalculator.COUNT });
	    model.setFieldSubtotals("CODIGO_UNIDAD_PRESUPUESTO",
		    new StandardCalculator[] { StandardCalculator.AVERAGE,
			    StandardCalculator.COUNT });
	    model.setFieldSubtotals("CODIGO_UNIDAD_EJECUCION",
		    new StandardCalculator[] { StandardCalculator.AVERAGE,
			    StandardCalculator.COUNT });
	   
	}

	public void configure(Pivottable table) {
	    table.setDataFieldOrient("column");

	}

	public PivotRenderer getRenderer() {
	    return new TipoSaldoPivotRender();
	}
    };

    public class TipoSaldoPivotRender extends SimplePivotRenderer {
	protected Logger log = Logger.getLogger(TipoSaldoPivotRender.class);

	@Override
	public int getColumnSize(Pivottable pivottable,
		PivotHeaderContext pivotHeaderContext, PivotField pivotField) {
	    return super.getColumnSize(pivottable, pivotHeaderContext,
		    pivotField);
	}

	@Override
	public int getRowSize(Pivottable pivottable,
		PivotHeaderContext pivotHeaderContext, PivotField pivotField) {
	    return 30;
	}

	@Override
	public String renderCell(Number data, Pivottable table,
		PivotHeaderContext rowContext,
		PivotHeaderContext columnContext, PivotField dataField) {
	    return Utilidades.getStringDecimal(new BigDecimal(data
		    .doubleValue()));
	}

	@Override
	public String renderCellStyle(Number data, Pivottable table,
		PivotHeaderContext rowContext,
		PivotHeaderContext columnContext, PivotField dataField) {
	    return super.renderCellStyle(data, table, rowContext,
		    columnContext, dataField);
	}

	@Override
	public String renderCellSClass(Number data, Pivottable table,
		PivotHeaderContext rowContext,
		PivotHeaderContext columnContext, PivotField dataField) {
	    return super.renderCellSClass(data, table, rowContext,
		    columnContext, dataField);
	}

	@Override
	public String renderField(Object data, Pivottable table,
		PivotField field) {
	    String renderField = null;
	    
	    if(field.getType() == PivotField.Type.DATA ){
		renderField= field.getTitle();
	    }else{
		if(field.getFieldName().equals("NATURALEZA")){
		    if(data.equals("I")){
			renderField = "INGRESO";
		    }else if(data.equals("E")){
			renderField = "EGRESO";
		    }
		}else{
		    renderField = data == null ? "(null)" : String.valueOf(data);
		}
	    }
	    
	  
			    
			    
	    return renderField;
	}

	@Override
	public String renderGrandTotalField(Pivottable table, PivotField field) {
	    if (field == null)
		return "Total General";
	    return "Total general de " + field.getTitle();
	}

	@Override
	public String renderSubtotalField(Object data, Pivottable table,
		PivotField field, Calculator calculator) {
	    String calLabel = calculator.getLabel();
	    String dataLabel = data == null ? " " : data.toString();
	    return dataLabel + " " + calLabel;
	}

    }

}
