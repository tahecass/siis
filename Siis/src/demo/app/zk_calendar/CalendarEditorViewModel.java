package demo.app.zk_calendar;

import java.util.Date;
import java.util.Map;

import org.jfree.util.Log;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.event.EventListener;

import com.siis.configuracion.Conexion;
import com.siis.dto.Calendario;

public class CalendarEditorViewModel {

	private DemoCalendarEvent calendarEventData = new DemoCalendarEvent();

	private boolean visible = false;

	private Date fechaInicio;
	private Date fechaFin;
	
	public DemoCalendarEvent getCalendarEvent() {
		return calendarEventData;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Init
	public void init() {
		// subscribe a queue, listen to other controller
		QueueUtil.lookupQueue().subscribe(new QueueListener());
	}

	private void startEditing(DemoCalendarEvent calendarEventData) {
		this.calendarEventData = calendarEventData;
		visible = true;

		// reload entire view-model data when going to edit
		BindUtils.postNotifyChange(null, null, this, "*");
	}

	@Command
	public void setterCalendarI(@BindingParam("fecha") Date fecha) {
		fechaInicio = (fecha);
		System.out.println("F.I" + fechaInicio);
	}

	@Command
	public void setterCalendarF(@BindingParam("fecha") Date fecha) {
		fechaFin = (fecha);
		System.out.println("F.F" + fechaFin);
	}

	public boolean isAllDay(Date beginDate, Date endDate) {
		int M_IN_DAY = 1000 * 60 * 60 * 24;
		boolean allDay = false;

		if (beginDate != null && endDate != null) {
			long between = endDate.getTime() - beginDate.getTime();
			allDay = between > M_IN_DAY;
		}
		return allDay;
	}

	public Validator getDateValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {
				Map<String, Property> formData = ctx.getProperties(ctx
						.getProperty().getValue());
//				Date beginDate = (Date) formData.get("beginDate").getValue();
				Date beginDate = fechaInicio;
//				Date endDate = (Date) formData.get("endDate").getValue();
				Date endDate = fechaFin;
				System.out.println("F.I: " + beginDate);
				System.out.println("F.F: " + endDate);
				if (beginDate == null) {
					addInvalidMessage(ctx, "beginDate", "Begin date is empty");
				}
				if (endDate == null) {
					addInvalidMessage(ctx, "endDate", "End date is empty");
				}
				if (beginDate != null && endDate != null
						&& beginDate.compareTo(endDate) >= 0) {
					addInvalidMessage(ctx, "endDate",
							"End date is before begin date");
				}
			}
		};
	}

	@Command
	@NotifyChange("visible")
	public void cancel() {
		QueueMessage message = new QueueMessage(QueueMessage.Type.CANCEL);
		QueueUtil.lookupQueue().publish(message);
		this.visible = false;
	}

	@Command
	@NotifyChange("visible")
	public void delete() {
		QueueMessage message = new QueueMessage(QueueMessage.Type.DELETE,
				calendarEventData);
		QueueUtil.lookupQueue().publish(message);
		this.visible = false;
	}

	// 827F4B501D945ED7

	@Command
	@NotifyChange("visible")
	public void ok() {
		Calendario calendario = new Calendario(null,
				fechaInicio,
				fechaFin,
				calendarEventData.getHeaderColor(),
				calendarEventData.getContentColor(),
				calendarEventData.getContent());
		System.out.println("F.I: " + calendario.getFecha_inicio());
		System.out.println("F.F: " + calendario.getFecha_fin());
		if (calendarEventData.getSec() != null) {
			System.out.print("VAlor...." + calendarEventData.getSec());
			Conexion.getConexion().actualizar("actualizarCalendario",
					calendario);
		} else {

			Conexion.getConexion().guardar("guardarCalendario", calendario);
		}
		QueueMessage message = new QueueMessage(QueueMessage.Type.OK,
				calendarEventData);
		QueueUtil.lookupQueue().publish(message);
		this.visible = false;
	}

	private class QueueListener implements EventListener<QueueMessage> {
		@Override
		public void onEvent(QueueMessage message) throws Exception {
			if (QueueMessage.Type.EDIT.equals(message.getType())) {
				CalendarEditorViewModel.this
						.startEditing((DemoCalendarEvent) message.getData());
			}
		}
	}
}
