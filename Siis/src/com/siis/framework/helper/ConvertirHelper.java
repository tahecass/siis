package com.casewaresa.framework.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * @author caseware
 * @date 23/01/2007
 * @name ConvertirHelper.java
 * @desc Agrupa funcionalidades comunes
 */
public class ConvertirHelper 
{
	/** desc: generamos una instancia del logger con la configuracion del archivo de propiedades */
	static Logger log = Logger.getLogger(ConvertirHelper.class);

	/**
	 * @type   Constructor de la clase ConvertirHelper
	 * @name   ConvertirHelper
	 * @desc   Crea una nueva instancia de Convertir
	 */
	public ConvertirHelper() {
		super();
	}
	
	/**
	 * @type   Método de la clase Convertir
	 * @name   strToGregorianCalendar 
	 * @return GregorianCalendar
	 * @param  String pFecha
	 * @param  String Formato
	 * @param  Locale loc
	 * @desc   Método que convierte de un String a un GregorianCalendar
	 */
    public static GregorianCalendar strToGregorianCalendar (String pFecha,String pFormato,Locale loc)
    {
    	/*
    	 * "dd-MMM-yyyy", Locale.getDefault()
    	 * */
    	GregorianCalendar gc = null;
    	 try {
	    		 //--obtenemos una instancia de date format
	    		 SimpleDateFormat formatter = new SimpleDateFormat(pFormato, Locale.getDefault());
	    		 
	    		 //--creamos un objetoGeregorian Calendar ap artir del parametro pfecha
	    	     gc = new GregorianCalendar();
	    	     Date d = formatter.parse(pFecha);
	    	     gc.setTime(d);
	    	     
	    	     //--aumentamos en 1 el numero del mes ya que GregorianCalendar toma como Enero=0...
	    	     //gc.set(GregorianCalendar.MONTH,gc.get(GregorianCalendar.MONTH));
	    	     //--mostramos
	    	     log.info("fecha = [ " + formatter.format(gc.getTime()) + " ]");
	    	     
    	     }
    	    catch (Exception e) 
    	     {    	      
    	      log.info("ERROR: al intentar hacer parse String->GregotianCalendar ",e);
    	     }    	
		
      //retornamos el nuevo objeto creado   
      return gc;
    }
    
	/**
	 * @type   Método de la clase Convertir
	 * @name   strToGregorianCalendar 
	 * @return GregorianCalendar
	 * @param  String pFecha
	 * @param  String Formato
	 * @param  Locale loc
	 * @desc   Método que convierte de un String a un GregorianCalendar
	 */
   public static String intToStringMonth (int pNumMes,boolean pMayusculas)
   {
	 String cadena = "";
     switch (pNumMes)
     {
      case 1:{cadena  = "Enero";break;}
      case 2:{cadena  = "Febrero";break;}
      case 3:{cadena  = "Marzo";break;}
      case 4:{cadena  = "Abril";break;}
      case 5:{cadena  = "Mayo";break;}
      case 6:{cadena  = "Junio";break;}
      case 7:{cadena  = "Julio";break;}
      case 8:{cadena  = "Agosto";break;}
      case 9:{cadena  = "Septiembre";break;}
      case 10:{cadena = "Octubre";break;}
      case 11:{cadena = "Noviembre";break;}
      case 12:{cadena = "Diciembre";break;}
     }
     if(pMayusculas)
    	  return cadena.toUpperCase();
     else 
    	  return cadena;	   
   }
   
	/**
	 * @type   Método de la clase Convertir
	 * @name   strToGregorianCalendar 
	 * @return GregorianCalendar
	 * @param  String pFecha
	 * @param  String Formato
	 * @param  Locale loc
	 * @desc   Método que convierte de un String a un GregorianCalendar
	 */
  public static String intToStringShortMonth (int pNumMes,boolean pMayusculas)
  {
	 String cadena = "";
    switch (pNumMes)
    {
     case 1:{cadena  = "Ene";break;}
     case 2:{cadena  = "Feb";break;}
     case 3:{cadena  = "Mar";break;}
     case 4:{cadena  = "Abr";break;}
     case 5:{cadena  = "May";break;}
     case 6:{cadena  = "Jun";break;}
     case 7:{cadena  = "Jul";break;}
     case 8:{cadena  = "Ago";break;}
     case 9:{cadena  = "Sep";break;}
     case 10:{cadena = "Oct";break;}
     case 11:{cadena = "Nov";break;}
     case 12:{cadena = "Dic";break;}
    }
    if(pMayusculas)
   	  return cadena.toUpperCase();
    else 
   	  return cadena;	   
  }   
  
	/**
	 * @type   Método de la clase Convertir
	 * @name   dateToString 
	 * @return String
	 * @param  int formato
	 * @desc   Método que convierte la fecha actual a un string formateado
	 *         el formato es dato por las constantes son,:
	 *         - SHORT, totalmente numerico (18/11/02 O 9:01)
     * 		   - MEDIUM, formato largo (18-nov-02 O 9:00:20)
     *  	   - LONG, otro formato largo (18 de noviembre de 2002 O 9:02:12 GMT+01:00)
     * 		   - FULL, con la especificación completa (lunes 18 de noviembre de 2002 O 09H03' GMT+01:00) 
	 */
	public static String dateToString (int formato)
	  {
		//- obtenemos la instancia del gregorian calendar
		GregorianCalendar calendario = (GregorianCalendar) GregorianCalendar.getInstance();
		//- obtenemos la fecha actual
		Date fecha = calendario.getTime();
		//- obtenemos la instancia del formateador de las fechas
		DateFormat formateador = DateFormat.getDateInstance(formato);
		//- retornamos la fecha formateada..
		return formateador.format(fecha);	  
	  }

	/**
	 * @type   Método de la clase Convertir
	 * @name   timeToString 
	 * @return String
	 * @param  int formato
	 * @desc   Método que convierte la fecha actual a un string formateado
	 *         el formato es dato por las constantes son,:
	 *         - SHORT, totalmente numerico (18/11/02 O 9:01)
     * 		   - MEDIUM, formato largo (18-nov-02 O 9:00:20)
     *  	   - LONG, otro formato largo (18 de noviembre de 2002 O 9:02:12 GMT+01:00)
     * 		   - FULL, con la especificación completa (lunes 18 de noviembre de 2002 O 09H03' GMT+01:00) 
	 */
	public static String timeToString (int formato)
	  {
		//- obtenemos la instancia del gregorian calendar
		GregorianCalendar calendario = (GregorianCalendar) GregorianCalendar.getInstance();
		//- obtenemos la fecha actual
		Date fecha = calendario.getTime();
		//- obtenemos la instancia del formateador de las fechas
		DateFormat formateador = DateFormat.getTimeInstance(formato);
		//- retornamos la fecha formateada..
		return formateador.format(fecha);	  
	  }
	
	/**
	 * @type   Método de la clase Convertir
	 * @name   dateToString
	 * @return String
	 * @param  String formato
	 * @param  Date fecha
	 * @desc   Método que convierte una fecha  a un string formateado segúne el formato
	 *         registrao en  el contrato de constantes.
	 */
	public static String dateToString (Date pFecha ,String  pFormato)
	  {
		//--Variable para almacenar la fecha formateada
		String fechaFormateada;
		//--Obtenemos una instania de la simpleDateFormat
		SimpleDateFormat formatter = new SimpleDateFormat(pFormato, Locale.getDefault());
		//--Formatea la fecha y la retorna como String
		fechaFormateada = formatter.format(pFecha);
		
		return fechaFormateada;	  
	  }
	
	/**
	 * @type   Método de la clase ConvertirHelper
	 * @name   retornarSysdate
	 * @return Date
	 * @return
	 * @desc   --
	 */
	public static java.sql.Date retornarSysdate(){
		
		Calendar cal = Calendar.getInstance();
		java.sql.Date fechaHoy = new java.sql.Date(cal.getTimeInMillis()); 
		
		return fechaHoy;
	} 
}
