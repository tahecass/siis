package com.casewaresa.framework.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name FormatoNumeros.java
 * @desc Este helper ayuda a convertir y formatear numeros
 */
public class FormatoNumeros {
	
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

}