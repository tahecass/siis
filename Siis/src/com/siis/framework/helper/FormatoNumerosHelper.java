package com.casewaresa.framework.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name FormatoNumeros.java
 * @desc Este helper ayuda a convertir y formatear numeros
 */
public class FormatoNumerosHelper {
	
	/**
	 * @type   Método de la clase FormatoNumeros
	 * @name   formatearNumero
	 * @return String
	 * @param patron
	 * @param valor
	 * @desc   Formatea un número a partir de un patron
	 */
	public static String formatearNumero(String patron, Object valor)
	{
		DecimalFormat formateador= new DecimalFormat(patron);
		String salida = formateador.format(valor);
		return salida;
	}

	
	/**
	 * @type   Método de la clase FormatoNumeros
	 * @name   cadenaANumero
	 * @return BigDecimal
	 * @param cadena
	 * @desc   convierte una cadena a un numero...
	 */
	public static BigDecimal cadenaANumero(String cadena )
	{
		DecimalFormat formateador= new DecimalFormat();
		BigDecimal numero=null;
	
			formateador.setParseBigDecimal(true);
			numero = (BigDecimal) formateador.parseObject( cadena,new ParsePosition (0));
			
		return numero;
	}


	/**
	 * @type   Método de la clase FormatoNumeros
	 * @name   formatearBytes
	 * @return String
	 * @param bytes
	 * @param format
	 * @desc   Este método se encarga de fortmatear el tamaño del reporte
	 */
	public static String formatearBytes(long bytes, NumberFormat format){  
	     String[] arrPosForm = {  
	             "Bytes", "KB", "MB", "GB",   
	             "Terabyte", "Petabyte", "Exabyte", "Zettabyte", "Yottabyte"  
	     };  
	           
	     for(int i = arrPosForm.length-1; i>0; i--)  
	     {  
	         if(bytes > Math.pow(1024, i))  
	         {  
	             bytes /= Math.pow(1024, i);  
	             return format.format(bytes) + arrPosForm[i];  
	         }  
	     }  
	   
	     return format.format(bytes) + " Bytes";  
	 }	
}