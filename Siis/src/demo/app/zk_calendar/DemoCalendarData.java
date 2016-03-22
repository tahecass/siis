package demo.app.zk_calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.calendar.api.CalendarEvent;

import com.siis.configuracion.Conexion;
import com.siis.dto.Calendario;
import com.siis.dto.Cartera;

public class DemoCalendarData {

	private List<CalendarEvent> calendarEvents = new LinkedList<CalendarEvent>();
	private final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm");
	private Calendar cal = Calendar.getInstance();

	public DemoCalendarData() {
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		try {
			List<Calendario> listCalendario = (List<Calendario>) Conexion
					.getConexion().obtenerListado("listarCalendario", null);
			for (Calendario calendario : listCalendario)			
				calendarEvents.add(new DemoCalendarEvent(calendario.getSec(),calendario.getFecha_inicio(), calendario.getFecha_fin(), calendario.getColor_borde(), calendario.getColor_content(), calendario.getTitulo()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// int mod = cal.get(Calendar.MONTH) + 1;
		// int year = cal.get(Calendar.YEAR);
		// String date2 = mod > 9 ? year + "/" + mod + "" : year + "/" + "0" +
		// mod;
		// String date1 = --mod > 9 ? year + "/" + mod + "" : year + "/" + "0" +
		// mod;
		// ++mod;
		// String date3 = ++mod > 9 ? year + "/" + mod + "" : year + "/" + "0" +
		// mod;
		// // Red Events
		// calendarEvents.add(new DemoCalendarEvent(getDate(date1 +
		// "/28 00:00"), getDate(date1 + "/29 00:00"), "#A32929", "#D96666",
		// "ZK Jet Released"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date1 +
		// "/04 02:00"), getDate(date1 + "/05 03:00"), "#A32929", "#D96666",
		// "Experience ZK SpreadSheet Live Demo!"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/21 05:00"), getDate(date2 + "/21 07:00"), "#A32929", "#D96666",
		// "New Features of ZK Spreadsheet"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/08 00:00"), getDate(date2 + "/09 00:00"), "#A32929", "#D96666",
		// "ZK Spreadsheet Released"));
		// // Blue Events
		// calendarEvents.add(new DemoCalendarEvent(getDate(date1 +
		// "/29 03:00"), getDate(date2 + "/02 06:00"), "#3467CE", "#668CD9",
		// "ZK Released"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/02 10:00"), getDate(date2 + "/02 12:30"), "#3467CE", "#668CD9",
		// "New Feature of ZK "));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/17 14:00"), getDate(date2 + "/18 16:00"), "#3467CE", "#668CD9",
		// "Case Study - Mecatena"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date3 +
		// "/01 14:30"), getDate(date3 + "/01 17:30"), "#3467CE", "#668CD9",
		// "ZK Unit Testing Project - zunit"));
		// // Purple Events
		// calendarEvents.add(new DemoCalendarEvent(getDate(date1 +
		// "/29 08:00"), getDate(date2 + "/03 12:00"), "#7A367A", "#B373B3",
		// "ZK Studio released"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/07 08:00"), getDate(date2 + "/07 12:00"), "#7A367A", "#B373B3",
		// "Tutorial : Reading from the DB with Netbeans and ZK"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/13 11:00"), getDate(date2 + "/13 14:30"), "#7A367A", "#B373B3",
		// "Small talk - ZK Charts"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/16 14:00"), getDate(date2 + "/18 16:00"), "#7A367A", "#B373B3",
		// "Style Guide for ZK released !"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date3 +
		// "/02 12:00"), getDate(date3 + "/02 17:00"), "#7A367A", "#B373B3",
		// "Small talk -- Simple Database Access From ZK"));
		// // Khaki Events
		// calendarEvents.add(new DemoCalendarEvent(getDate(date1 +
		// "/03 00:00"), getDate(date1 + "/04 00:00"), "#88880E", "#BFBF4D",
		// "ZK UK User Group"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/13 05:00"), getDate(date2 + "/13 07:00"), "#88880E", "#BFBF4D",
		// "How to Test ZK Application with Selenium"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/24 19:30"), getDate(date2 + "/24 20:00"), "#88880E", "#BFBF4D",
		// "ZK Alfresco Talk"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date3 +
		// "/03 00:00"), getDate(date3 + "/04 00:00"), "#88880E", "#BFBF4D",
		// "ZK selected as SourceForge.net Project of the Month"));
		// // Green Events
		// calendarEvents.add(new DemoCalendarEvent(getDate(date1 +
		// "/28 10:00"), getDate(date1 + "/28 12:30"), "#0D7813", "#4CB052",
		// "ZK Mobile Released"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/03 00:00"), getDate(date2 + "/03 05:30"), "#0D7813", "#4CB052",
		// "ZK Gmaps released"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/05 20:30"), getDate(date2 + "/06 00:00"), "#0D7813", "#4CB052",
		// "Refresh with Five New ZK Themes!"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date2 +
		// "/23 00:00"), getDate(date2 + "/25 16:30"), "#0D7813", "#4CB052",
		// "ZK Roadmap Announced"));
		// calendarEvents.add(new DemoCalendarEvent(getDate(date3 +
		// "/01 08:30"), getDate(date3 + "/01 19:30"), "#0D7813", "#4CB052",
		// "Build Database CRUD Application in 6 Steps"));
	}

	private Date getDate(String dateText) {
		try {
			return DATA_FORMAT.parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<CalendarEvent> getCalendarEvents() {
		return calendarEvents;
	}
}
