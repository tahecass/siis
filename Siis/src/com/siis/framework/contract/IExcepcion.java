package com.casewaresa.framework.contract;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name IExcepcionesProyecto.java
 * @desc define las constantes que tienen que ver con el proyecto.
 */
public interface IExcepcion {
	
	 /*constantes para definir el Id de la excepciones */
	  public static final int EXCEPTION_GLOBAL  = 1000;

	/*constantes para definir la ruta de la ventana donde se encuentrala ventana de popuplov */
  public static final String PANTALLA_EXCEPCIONPOPUP  = "/iucomunes/excepcionmodal.zul";

  /*constantes para definir el prefijo utilizado en el archivo de lenguajes */
  public static final String TAG_EXCEPCION_I18N  = "global:Excepcion";

  /*constantes para definir las rutas de las imï¿½genes de las excepciones*/
  public static final String IMAGEN_EXCEPTION_GLOBAL_EXT   = "/images/excepcionalert.gif";
  public static final String IMAGEN_EXCEPTION_API          = "/images/excepcionapi.gif";
  public static final String IMAGEN_EXCEPTION_VISTA        = "/images/excepcionvista.gif";
  public static final String IMAGEN_EXCEPTION_NEGOCIO      = "/images/excepcionnegocio.gif";
  public static final String IMAGEN_EXCEPTION_DATOS        = "/images/excepciondatos.gif";

  /*constantes para definir el origen de la excepcion */
  final int EXCEPCION_ORIGEN_DESCONOCIDO = 0;
  final int EXCEPCION_ORIGEN_APLICACION = 1;
  final int EXCEPCION_ORIGEN_DATABASE = 2;

  /*constantes para definir el origen de la excepcion */
  final String EXCEPCION_IMAGEN_ORIGEN_DESCONOCIDO = "/imagenes/errorUN.png";
  final String EXCEPCION_IMAGEN_ORIGEN_APLICACION = "/imagenes/errorAP.png";
  final String EXCEPCION_IMAGEN_ORIGEN_DATABASE = "/imagenes/errorDB.png";

  /*constantes para definir los identificadores de las excepciones específicas*/
  public static final int EXCEPCION_NO_REGISTRADA		  	= 0;
  public static final int EXCEPCION_GENERAL	   			  	= 1000;
  public static final int EXCEPCION_POR_INTERRUPCION		= 2001;
  public static final int EXCEPCION_METODO_NO_EXISTENTE   	= 2002;
  public static final int EXCEPCION_TIEMPO_DE_EJECUCION		= 2003;
  public static final int EXCEPCION_ACCESO_ILEGAL			= 2004;
  public static final int EXCEPCION_INVOCANDO_OBJETIVO		= 2005;
  public static final int EXCEPCION_PUNTERO_NULO           	= 3001;
  public static final int EXCEPCION_CONVERSION_DE_CLASE     = 3002;
  public static final int EXCEPCION_OBJ_NO_ENCONTRADO      	= 5001;
  public static final int EXCEPCION_OBJ_NO_SELECCIONABLE   	= 5002;
  public static final int EXCEPCION_OBJ_NO_SELECCIONADO    	= 5003;
  public static final int EXCEPCION_CONSTRAINT_GUARDAR	   	= 6001;
  public static final int EXCEPCION_CONSTRAINT_BORRAR	   	= 6002;
  public static final int EXCEPCION_VALOR_INCORRRECTO		= 6003;
  public static final int EXCEPCION_FORMATO_DE_NUMERO		= 6004;
  public static final int EXCEPCION_CONSTAINT_VIOLADO		= 6005;
  
  /* Excepciones Personalizadas... */
  public static final int EXCEPCION_DATO_NO_ENCONTRADO		= -20001;
  
  
  /*constantes para definir las rutas de las imï¿½genes de las excepciones especï¿½ficas*/
  public static final String IMAGEN_EXCEPCION_PUNTERO_NULO  = "/images/excepcionpersonalizada.gif";
  public static final String IMAGEN_EXCEPCION_CONVERSION_DE_CLASE   = "/images/exceptionpersonalizada2.gif";
  public static final String IMAGEN_EXCEPCION_OBJ_NO_ENCONTRADO  = "/images/exceptionpersonalizada0.gif";
  public static final String IMAGEN_EXCEPCION_OBJ_NO_SELECCIONABLE  = "/images/exceptionpersonalizada2.gif";
  
  
}
