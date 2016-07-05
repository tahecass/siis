package com.casewaresa.framework.Filter;

/**
 * @author Casewaredes
 * 23/03/2012
 */
public class TipoDato {
	//Tipos de Datos manejados para la validaci�n en las condiciones de la consulta SQL.
 public static final String[] String={"LIKE","NOT LIKE","LIKE%","%LIKE%","%LIKE"};
 public static final String[] Integer={"<",">","=","!=","<=",">=","IN","NOT IN","BETWEEN"};
 public static final String[] Long={"<",">","=","!=","<=",">=","IN","NOT IN","BETWEEN"};
 public static final String[] Date={"<",">","=","!=","<=",">=","IN","NOT IN","BETWEEN"};
 public static final String[] BigDecimal={"<",">","=","!=","<=",">=","IN","NOT IN","BETWEEN"};
}
