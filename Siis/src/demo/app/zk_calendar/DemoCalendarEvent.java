package demo.app.zk_calendar;

import java.util.Date;

import org.zkoss.bind.annotation.Immutable;
import org.zkoss.calendar.impl.SimpleCalendarEvent;

public class DemoCalendarEvent extends SimpleCalendarEvent {
	private static final long serialVersionUID = 1L;
	private Long sec;

	public DemoCalendarEvent(Long sec, Date beginDate, Date endDate,
			String headerColor, String contentColor, String content) {
		setHeaderColor(headerColor);
		setContentColor(contentColor);
		setContent(content);
		setBeginDate(beginDate);
		setEndDate(endDate);
		this.sec = sec;
	}

	public DemoCalendarEvent(Date beginDate, Date endDate, String headerColor,
			String contentColor, String content, String title) {
		setHeaderColor(headerColor);
		setContentColor(contentColor);
		setContent(content);
		setTitle(title);
		setBeginDate(beginDate);
		setEndDate(endDate);
	}

	public DemoCalendarEvent(Date beginDate, Date endDate, String headerColor,
			String contentColor, String content, String title, boolean locked) {
		setHeaderColor(headerColor);
		setContentColor(contentColor);
		setContent(content);
		setTitle(title);
		setBeginDate(beginDate);
		setEndDate(endDate);
		setLocked(locked);
	}

	public DemoCalendarEvent() {
		setHeaderColor("#FFFFFF");
		setContentColor("#000000");
	}

	@Override
	public Date getBeginDate() {
		return super.getBeginDate();
	}

	@Override
	public Date getEndDate() {
		return super.getEndDate();
	}

	public Long getSec() {
		return sec;
	}

	public void setSec(Long sec) {
		this.sec = sec;
	}

}
