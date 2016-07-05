/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casewaresa.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
/**
 *
 * @author Admin
 */
public class FechasUtil {
    
    public enum FORMATOS {
        yyyyMMdd, ddMMyyyy, MMddyyyy, EEEEEddMMMMMyyyy, MMMMMddyyyy, ddMMyy, 
        MMMMMyy, ddMMMMMyyyy, ddMMyyyyhmma, ddMMyyyyHHmmss, ddMMyyyyHHmm, 
        EEE_ddMMMyyyyHHmmss, EEE_ddMMMyyyyHHmm, EEE_ddMMMMMyyyyhmmssa, hmm_a, HHmm
    };
 
    public  static String getFormatString(FORMATOS formato) {
        String stringFormat = "";
        
        switch (formato) {
            case ddMMyyyy:
                stringFormat = "dd/MM/yyyy";
                break;

            case yyyyMMdd:
                stringFormat = "yyyy/MM/dd";
                break;

            case EEE_ddMMMyyyyHHmm:
                stringFormat = "EEE, dd MMM yyyy HH:mm";
                break;

            case EEE_ddMMMyyyyHHmmss:
                stringFormat = "EEE, dd MMM yyyy HH:mm:ss";
                break;

            case ddMMyyyyHHmm:
                stringFormat = "dd/MM/yyyy HH:mm";
                break;

            case ddMMyyyyHHmmss:
                stringFormat = "dd/MM/yyyy HH:mm:ss";
                break;

            case ddMMyyyyhmma:
                stringFormat = "dd/MM/yyyy h:mm a";
                break;

            case ddMMMMMyyyy:
                stringFormat = "dd MMMMM yyyy";
                break;

            case MMMMMyy:
                stringFormat = "MMMMM yy";
                break;

            case MMddyyyy:
                stringFormat = "MM/dd/yyyy";
                break;

            case EEEEEddMMMMMyyyy:
                stringFormat = "EEEEE dd MMMMM yyyy";
                break;

            case MMMMMddyyyy:
                stringFormat = "MMMMM dd yyyy";
                break;

            case ddMMyy:
                stringFormat = "dd/MM/yy";
                break;

            case EEE_ddMMMMMyyyyhmmssa:
                stringFormat = "EEE, dd MMMMM yyyy h:mm:ss a";
                break;

            case hmm_a:
                stringFormat = "h:mm a";
                break;
                

            case HHmm:
                stringFormat = "HH:mm";
                break;
                
                
                
        }

        return stringFormat;
    }

    public static String formatFecha(Calendar c, FORMATOS formato) {
        return (c != null) ? FechasUtil.formatFecha(c.getTime(), formato) : "";
    }

    public static String formatFecha(Date date, FORMATOS formato) {
	SimpleDateFormat dateFormat = new SimpleDateFormat();
	dateFormat.applyPattern(getFormatString(formato));

        if (date != null) {
            return dateFormat.format(date);
        }

        return "";
    }
    
    public static Date formatString(String date, FORMATOS formato) throws ParseException{
	SimpleDateFormat dateFormat = new SimpleDateFormat();
	dateFormat.applyPattern(getFormatString(formato));
	if(date!=null && !date.isEmpty()){
	    return dateFormat.parse(date);
	}
	
	return null;
    }

    public static String formatFecha(Date date, String formato) {
	SimpleDateFormat dateFormat = new SimpleDateFormat();
	dateFormat.applyPattern(formato);

        if (date != null) {
            return dateFormat.format(date);
        }

        return "";
    }

    public static String formatFecha(Date date, String formato, Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato, locale != null?locale:Locale.getDefault());
    	
        if (date != null) {
            return dateFormat.format(date);
        }

        return "";
    }

    public static void clonarHora(Calendar origen, Calendar destino, int SegAdicionales) {
        destino.set(Calendar.HOUR_OF_DAY, origen.get(Calendar.HOUR_OF_DAY));
        destino.set(Calendar.MINUTE, origen.get(Calendar.MINUTE));
        destino.set(Calendar.SECOND, origen.get(Calendar.SECOND));
        destino.set(Calendar.MILLISECOND, origen.get(Calendar.MILLISECOND));

        destino.add(Calendar.SECOND, SegAdicionales);
    }

    public static void clonarHoraMinuto(Calendar origen, Calendar destino, int SegAdicionales) {
        destino.set(Calendar.YEAR, origen.get(Calendar.YEAR));
        destino.set(Calendar.HOUR_OF_DAY, origen.get(Calendar.HOUR_OF_DAY));
        destino.set(Calendar.MINUTE, origen.get(Calendar.MINUTE));
        destino.set(Calendar.SECOND, origen.get(Calendar.SECOND));
        destino.set(Calendar.MILLISECOND, origen.get(Calendar.MILLISECOND));

        destino.add(Calendar.MINUTE, SegAdicionales);
    }

    public static void toHoraMinimal(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, c.getMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getMinimum(Calendar.MILLISECOND));
    }

    public static Calendar toHoraMinimalCalendar(Calendar c) {
        Calendar calendar = (Calendar) c.clone();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));

        return calendar;
    }

    public static Calendar toHoraMaximunCalendar(Calendar c) {
        Calendar calendar = (Calendar) c.clone();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));

        return calendar;
    }

    public static Calendar toHoraMinimalCalendarAll(Calendar c) {
        Calendar calendar = (Calendar) c.clone();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.YEAR, calendar.getMaximum(Calendar.YEAR));

        return calendar;
    }

    public static Calendar toHoraMaximunCalendarAll(Calendar c) {
        Calendar calendar = (Calendar) c.clone();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
        calendar.set(Calendar.YEAR, calendar.getMaximum(Calendar.YEAR));

        return calendar;
    }

    public static Calendar getTimeMinimalSystem() {
        Calendar c = Calendar.getInstance();
        FechasUtil.toHoraMinimal(c);
        return c;
    }

    public static boolean betweenHour(Calendar fechaInicio, Calendar fechaFin, Calendar otherInicio, Calendar otherFin) {

        System.out.println("falta implementar");



        return true;
    }

    public static void toHoraMaximun(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, c.getMaximum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getMaximum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getMaximum(Calendar.MILLISECOND));
    }

    public static Calendar getTimeMaximunSystem() {
        Calendar c = Calendar.getInstance();
        FechasUtil.toHoraMinimal(c);
        return c;
    }

    public static boolean between(Calendar fecha, Calendar inicio, Calendar fin) {
        if (Days.daysBetween(new DateTime(fecha), new DateTime(inicio)).getDays() > 0) {
            return false;
        }
        if (Days.daysBetween(new DateTime(fecha), new DateTime(fin)).getDays() < 0) {
            return false;
        }

        return true;
    }

    /**
     * retorna true si la fecha 2 es mayor en dias que la fecha 1
     */
    public static boolean afterDay(Calendar fecha, Calendar other) {
        if (Days.daysBetween(new DateTime(fecha), new DateTime(other)).getDays() <= 0) {
            return false;
        }

        return true;
    }

    public static boolean afterDay(Date fecha, Date other) {
        if (Days.daysBetween(new DateTime(fecha), new DateTime(other)).getDays() <= 0) {
            return false;
        }

        return true;
    }

    public static boolean compareFecha(Calendar fecha, Calendar other) {
        if (Days.daysBetween(new DateTime(fecha), new DateTime(other)).getDays() < 0) {
            return false;
        }

        return true;
    }

    public static boolean compareHora(Calendar fechaInicio, Calendar fechaFin, Calendar otherInicio, Calendar otherFin) {

        fechaInicio.set(2009, 01, 01);
        fechaFin.set(2009, 01, 01);
        otherInicio.set(2009, 01, 01);
        otherFin.set(2009, 01, 01);

        Interval intervaloUno = new Interval(fechaInicio.getTimeInMillis(), fechaFin.getTimeInMillis());
        Interval intervaloDos = new Interval(otherInicio.getTimeInMillis(), otherFin.getTimeInMillis());

        if (intervaloUno.abuts(intervaloDos)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @type Método de la clase FechasUtil.java
     * @name compareHora
     * @param ignoreMillisecond indica si en la comparación se debn ignorar los milisegundos del time
     * @param ignoreSecond indica si en la comparación se deben ignorar los segundo del time 
     * @param hora1
     * @param hora2
     * @return Devuelve <code>int</code> dependiendo al resultado de una comparación realizada. 
     * Valor igual a 0: si las horas son iguales
     * Valor mayor a 0: si el primer argumento es mayor que el segundo.
     * Valor menor a 0: si el primer argumento es menor que el segundo.
     */
    public static int compareHora(Calendar hora1, Calendar hora2, boolean ignoreMillisecond, boolean ignoreSecond) {
        setStandarDate(hora1, ignoreMillisecond, ignoreSecond);
        setStandarDate(hora2, ignoreMillisecond, ignoreSecond);
        
        return hora1.compareTo(hora2);
    }
    
    
    /**
     * @type Método de la clase FechasUtil.java
     * @name compareHora
     * @param ignoreMillisecond indica si en la comparación se debn ignorar los milisegundos del time
     * @param ignoreSecond indica si en la comparación se deben ignorar los segundo del time 
     * @param hora1
     * @param hora2
     * @return Devuelve <code>int</code> dependiendo al resultado de una comparación realizada. 
     * Valor igual a 0: si las horas son iguales
     * Valor mayor a 0: si el primer argumento es mayor que el segundo.
     * Valor menor a 0: si el primer argumento es menor que el segundo.
     */
    public static int compareHora(Date hora1, Date hora2, boolean ignoreMillisecond, boolean ignoreSecond) {
    	Calendar calendar1 = GregorianCalendar.getInstance();
    	calendar1.setTime(hora1);
    	
    	Calendar calendar2 = GregorianCalendar.getInstance();
    	calendar2.setTime(hora2);
    	
        return compareHora(calendar1, calendar2, ignoreMillisecond, ignoreSecond);
    }
    
    /**
     * Devuelve <code>TRUE</code> en caso de que exista un cruce entre el rango de diferentes horas.
     * El rango de horas esta configurando por una pareja de horas. El primer par de horas debe poseer
     * un rango mucho mayor que el par siguiente, de lo contrario el resultado sera <code>FALSE</code>
     * por defecto.
     *
     * @param      fechaInicio   fecha que contiene la hora de inicio y que va a ser comparada.
     * @param      fechaFin   fecha que contiene la hora de finalización y que va a ser comparada.
     * @param      otherInicio fecha que contiene la hora de inicio con la que se verificara si hay un cruce.
     * @param      otherFin fecha que contiene la hora de finalización con la que se verificara si hay un cruce
     * @return TRUE en caso de que el segundo par de horas se encuentre dentro del primer par</code>.
     */
    public static boolean cruceHora(Calendar fechaInicio, Calendar fechaFin, Calendar otherInicio, Calendar otherFin) {
        fechaInicio.set(2009, 01, 01);
        fechaFin.set(2009, 01, 01);
        otherInicio.set(2009, 01, 01);
        otherFin.set(2009, 01, 01);
        
        boolean iniBetween = !otherInicio.before(fechaInicio)&&otherInicio.before(fechaFin);
        boolean finBetween = otherFin.after(fechaInicio)&&!otherFin.after(fechaFin);

        return iniBetween||finBetween;
    }


    /**
     * Devuelve un <code>int</code> resultado de restar los dias
     * entre dos objetos <code>Calendar</code>
     *
     * @param      minuendo   fecha a la que se le restara(la fecha mas remota o fecha mayor).
     * @param      sustraendo   fecha con la que se restaran los dias (la fecha menor).
     * @return número de dias representado en una variable <code>int</code>.
     */
    public static int restarFechas(Calendar minuendo, Calendar sustraendo) {
        return Days.daysBetween(new DateTime(sustraendo), new DateTime(minuendo)).getDays();
    }
    /**
     * suma un determinado número de dias a una fecha especifica
     *
     * @param      fecha   fecha a la que se le suman los dias
     * @param      dias   cantidad de dias a sumar.
     */
    public static void sumarDias(Calendar fecha,int dias){
        fecha.set(Calendar.DATE, fecha.get(Calendar.DATE)+dias);
    }
    /**
     * resta un determinado número de dias a una fecha especifica
     *
     * @param      minuendo   fecha a la que se le restaran los dias
     * @param      dias   cantidad de dias a restar.
     */
    public static void restarDias(Calendar minuendo,int dias){
        minuendo.set(Calendar.DATE, minuendo.get(Calendar.DATE)-dias);
    }
   
    /**
     * Metodo encargado de colocar una fecha con datos fijos para el Año, mes, dia
     * dentro de una fecha, con la intencion de trabajar con horas, minutos como campos variantes
     * ademas del estado del meridiano (AM,PM)
     *
     * @param calendar objeto Calendar con el cual se va a trabajar.
     * @param ignoreMillisecond indica si en la comparación se debn ignorar los milisegundos del time
     * @param ignoreSecond indica si en la comparación se deben ignorar los segundo del time 
     */
    public static void setStandarDate(Calendar calendar, boolean ignoreMillisecond, boolean ignoreSecond){
        calendar.set(1970, 0, 1);
        
        if(ignoreSecond)
        	calendar.set(Calendar.SECOND, 0);
        
        if (ignoreMillisecond)
        	calendar.set(Calendar.MILLISECOND, 0);
    }
}
