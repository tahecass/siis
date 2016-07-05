package com.casewaresa.framework.pivottable;


import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.ui.PivotFieldControl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Vlayout;

public class PivotDemoBaseController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	private Pivottable pivot;
	@Wire
	private Groupbox gbxFieldControl;
	@Wire
	private PivotFieldControl pfc;
	@Wire
	private Checkbox colGrandTotal, rowGrandTotal;
	@Wire
	private Radio colOrient, rowOrient;
	@Wire
	private Vlayout rawDataLayout;
	@Wire
	private Hlayout preDef;

	private TabularPivotModel pivotModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		PrivotModelSaldoDetallado pmf = PrivotModelSaldoDetallado.INSTANCE;
		pivotModel = pmf.build();
		pivot.setModel(pivotModel);
		pfc.setModel(pivotModel);
		loadConfiguration(pmf.getDefaultConfigurator());

		// load predefined scenario
		for (PivotConfigurator conf : pmf.getConfigurators())
			preDef.appendChild(getPreDefDiv(conf));
	}

	@Listen("onCheck = #colGrandTotal")
	public void enableColumnGrandTotal(CheckEvent event) {
		pivot.setGrandTotalForColumns(event.isChecked());
	}

	@Listen("onCheck = #rowGrandTotal")
	public void enableRowGrandTotal(CheckEvent event) {
		pivot.setGrandTotalForRows(event.isChecked());
	}

	@Listen("onCheck = #dataOrient")
	public void enableDataOrient(CheckEvent event) {
		pivot.setDataFieldOrient(((Radio) event.getTarget()).getValue().toString());
	}

	private void initControls() {
		// grand totals
		colGrandTotal.setChecked(pivot.isGrandTotalForColumns());
		rowGrandTotal.setChecked(pivot.isGrandTotalForRows());

		// data orientation
		("column".equals(pivot.getDataFieldOrient()) ? colOrient : rowOrient).setChecked(true);

		pfc.syncModel(); // field control
	}

//	private String renderRawData(Object object, String fname) {
//		if ("Agent".equals(fname) || "Customer".equals(fname)) {
//			String[] names = ((String) object).split(" ", 2);
//			return Character.toUpperCase(names[0].charAt(0)) + ". " + names[1];
//		} else if ("Date".equals(fname)) {
//			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//			return format.format((Date) object);
//		}
//		return object == null ? "(null)" : object.toString();
//	}

	private Component getPreDefDiv(final PivotConfigurator conf) {
		Button scenarioBtn = new Button(conf.getTitle());
		scenarioBtn.setSclass("predef");
		scenarioBtn.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				loadConfiguration(conf);
			}
		});
		return scenarioBtn;
	}

	private void loadConfiguration(PivotConfigurator conf) {
		pivotModel.clearAllFields(true);
		conf.configure(pivotModel);
		conf.configure(pivot);
		pivot.setPivotRenderer(conf.getRenderer());
		initControls();
	}

}
