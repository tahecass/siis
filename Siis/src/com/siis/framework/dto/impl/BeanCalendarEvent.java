package com.casewaresa.framework.dto.impl;

import java.util.Date;

import org.zkoss.calendar.impl.SimpleCalendarEvent;

import com.casewaresa.framework.dto.IBeanCalendarEvent;

public class BeanCalendarEvent extends SimpleCalendarEvent {

	/**
	 * Serial de la calse
	 */
	private static final long serialVersionUID = 7404325082174529601L;

	private IBeanCalendarEvent beanCalendarEvent;


	public BeanCalendarEvent(IBeanCalendarEvent beanCalendarEvent) {
		this.beanCalendarEvent =beanCalendarEvent ;
		this.setBeginDate(beanCalendarEvent.getFechaInicio());
		this.setEndDate(beanCalendarEvent.getFechaFin());
	}
	
	

	public void setFechaInicio(Date fechaInicio) {
		this.setBeginDate(fechaInicio);
		beanCalendarEvent.setFechaInicio(fechaInicio);
	}

	public void setFechaFin(Date fechaFin) {
		this.setBeginDate(fechaFin);
		beanCalendarEvent.setFechaFin(fechaFin);
	}

	public IBeanCalendarEvent getBeanCalendarEvent() {
		return beanCalendarEvent;
	}

	public void setBeanCalendarEvent(IBeanCalendarEvent beanCalendarEvent) {
		this.beanCalendarEvent = beanCalendarEvent;
	}

}
