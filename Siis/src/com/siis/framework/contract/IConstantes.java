package com.casewaresa.framework.contract;

import java.io.File;

import com.casewaresa.framework.helper.ContextoAplicacion;

public interface IConstantes {

	/* Constantes para definir postfijos */

	/* Constantes para definir contsantes generales */
	final String REGISTRO_HABILITADO = "S";
	final String REGISTRO_NO_HABILITADO = "N";
	final String TIPO_AUTENTICACION_LDAP = "L";
	final String TIPO_AUTENTICACION_ICEBERG = "P";

	final String AES_FACTORY = "AES";
	final String AES_KEY = "yK8gr7dRg6So5dCW";

	final String MODO_MANTENIMIENTO = "M";
	final String MODO_NORMAL = "N";

	// Constantes para indicar las operaciones sobre un registro
	final String INSERTAR = "I";
	final String EDITAR = "U";
	final String SELECCIONAR = "S";
	final String DUPLICAR = "D";

	final String BTN_REFRESCAR = "Refrescar";
	final String BTN_GUARDAR = "Guardar";

	/* Constantes para definir contsantes generales */
	public static final String PERMISO_CONCEDIDO = "S";
	public static final String PERMISO_NO_CONCEDIDO = "N";

	public static final String LOGO_REPORTE = ContextoAplicacion.getInstance()
			.getRutaContexto()
			+ System.getProperty("file.separator")
			+ "imagenes"
			+ System.getProperty("file.separator")
			+ "logoCliente.jpg";

	public static final String dotForWindows = new StringBuffer().append("C:")
			.append(File.separator).append("Program Files (x86)")
			.append(File.separator).append("Graphviz2.38")
			.append(File.separator).append("bin").append(File.separator)
			.append("dot.exe").toString();

	public static final String tempDirForWindows = new StringBuffer()
			.append(ContextoAplicacion.getInstance().getRutaContexto())
			.append("imagenes").append(File.separator).append("plantillas")
			.toString();

	public static final String dotForLinux = new StringBuffer()

	.append(File.separator).append("usr").append(File.separator)

	.append(File.separator).append("bin").append(File.separator).append("dot")
			.toString();

	public static final String tempDirForLinux = new StringBuffer()
			.append(ContextoAplicacion.getInstance().getRutaContexto())
			.append("imagenes").append(File.separator).append("plantillas")
			.toString();

	public static final String tempDirForMacOSX = new StringBuffer()
			.append(File.separator).append("tmp").toString();
	public static final String dotForMacOSX = new StringBuffer()
			.append(File.separator).append("usr").append(File.separator)
			.append("local").append(File.separator).append("bin")
			.append(File.separator).append("dot").toString();

	/* constantes para definir el nombre de los ï¿½ï¿½ï¿½conos comï¿½ï¿½ï¿½nes */
	public static final String IMAGEN_EDITAR = "/imagenes/editar.gif";
	public static final String IMAGEN_BORRAR = "/imagenes/borrar.gif";
	public static final String IMAGEN_BORRAR_DISABLE = "/imagenes/borrarDisable.png";
	public static final String IMAGEN_ACTIVO = "/imagenes/activo.gif";
	public static final String IMAGEN_INACTIVO = "/imagenes/inactivo.gif";
	public static final String IMAGEN_SELECCIONAR = "/imagenes/seleccionar.gif";
	public static final String IMAGEN_LOVPOPUP = "/imagenes/popuplov.gif";
	public static final String IMAGEN_NUEVOHIJO = "/imagenes/nuevohijo.gif";
	public static final String IMAGEN_ADDGRUPOS = "/imagenes/addgrupo.gif";
	public static final String IMAGEN_ADDGRUPOS_INACTIVO = "/imagenes/addgrupod.gif";
	public static final String IMAGEN_COTIZAR = "/imagenes/gccotizar.jpg";
	public static final String IMAGEN_REFRESH = "/imagenes/refresh.jpg";
	public static final String IMAGEN_OK = "/imagenes/ok.jpg";
	public static final String IMAGEN_YES = "/imagenes/yes.jpg";
	public static final String IMAGEN_NO = "/imagenes/no.jpg";
	public static final String IMAGEN_CANCEL = "/imagenes/cancel.jpg";
	public static final String IMAGEN_QUESTION = "/imagenes/question.jpg";
	public static final String IMAGEN_MAS = "/imagenes/suma.gif";
	public static final String IMAGEN_MENOS = "/imagenes/resta.gif";
	public static final String IMAGEN_INC = "/imagenes/incluida.jpg";
	public static final String IMAGEN_EXC = "/imagenes/excluida.jpg";
	public static final String IMAGEN_EDIT_RECORD = "/imagenes/money.png";
	public static final String IMAGEN_GUARDAR = "/imagenes/downarrow.gif";
	public static final String IMAGEN_NO_ICON = "/imagenes/no_icon.gif";
	public static final String IMAGEN_USER = "/imagenes/FotoUser.png";
	/* constantes para definir la mï¿½ï¿½ï¿½scara de los numeros */
	public static final String FORMATO_NUMERO = "###,###,###,###,###.00000";
	public static final String IMAGEN_OPEN = "/imagenes/down_open.gif";

	/* constantes para definir el formato de las fechas */
	public static final String FORMATO_FECHA = "yyyy/MM/dd";

	public static final String FORMATO_FECHA_COMPLETO = "yyyy/MM/dd HH:mm:ss";

	public static final String FORMATO_FECHA_MES = "MMM dd 'de' yyyy";

	/*
	 * constantes para definir el tamaï¿½ï¿½ï¿½o de la paginaciï¿½ï¿½ï¿½n de los listados
	 * largos
	 */
	public static final int TAMANO_PAGINACION = 15;
	public static final int TAMANO_PAGINACION_DETALLE = 12;
	public static final int TAMANO_PAGINACION_BANDBOX = 5;

	/* constante para definir el archivo propiedades de la aplicaciï¿½ï¿½ï¿½n */
	public static final String PROPIEDADES_APLICACION = "/WEB-INF/classes/Aplicacion.properties";

	/* constante para definir el archivo propiedades del LDAP */
	public static final String PROPIEDADES_LDAP = "ldap.properties";

	/* constante para definir el archivo de propiedades de reporte dinamico */
	public static final String PROPIEDADES_REPORTE_DINAMICO = "/WEB-INF/config_report/reporteDinamico.properties";
	public static final String _PROPIEDADES_REPORTE_DINAMICO = "/WEB-INF/config_report/reporte_dinamico.properties";
	public static final String PLANTILLA_REPORTE_PRESENTACION = "/WEB-INF/config_report/plantilla.jrxml";
	public static final String PLANTILLA_REPORTE_DINAMICO_PRESENTACION = "/WEB-INF/config_report/plantilla_reporte_dinamico2.jrxml";
	public static final String PLANTILLA_REPORTE_DINAMICO_HEADER_FOOTER = "/WEB-INF/config_report/plantilla_reporte_dinamico_header_foorter.jrxml";
	public static final String PLANTILLA_DATOS_BASICOS = "/WEB-INF/config_report/plantilla_datos_basicos.jrxml";
	public static final String PLANTILLA_REPORTE_DETALLE = "/WEB-INF/config_report/plantilla_detalle.jrxml";
	public static final String PLANTILLA_REPORTE_PRESENTACION_HORIZONTAL = "/WEB-INF/config_report/plantilla_landscape.jrxml";
	public static final String PLANTILLA_REPORTE_DETALLE_HORIZONTAL = "/WEB-INF/config_report/plantilla_detalle_landscape.jrxml";
	public static final String FUENTE_SANS_SERIF= "/WEB-INF/config_report/SansSerif.ttf";
	
	/*
	 * constantes para definir los identificadores y los ï¿½ï¿½ï¿½conos que informan
	 * del estado del ï¿½ï¿½ï¿½ltimo proceso realizado
	 */
	public static final String ESTADO_IMAGEN_OK = "/imagenes/estado_ok.gif";
	public static final String ESTADO_IMAGEN_ERROR = "/imagenes/estado_error.gif";
	public static final String ESTADO_IMAGEN_ADVERT = "/imagenes/adverten_important.png";
	// --
	public static final int ESTADO_INSERCION_OK = 1;
	public static final int ESTADO_INSERCION_ERROR = 2;

	public static final int ESTADO_EDICION_OK = 3;
	public static final int ESTADO_EDICION_ERROR = 4;

	public static final int ESTADO_BORRAR_OK = 5;
	public static final int ESTADO_BORRAR_ERROR = 6;

	public static final int ESTADO_DEFAULT_OK = 7;
	public static final int ESTADO_DEFAULT_ERROR = 8;
	public static final int ESTADO_ADVERTENCIA = 9;

	/*
	 * constantes para definir la ruta de la ventana donde se encuentrala
	 * ventana de excepciones
	 */
	public static final String PANTALLA_EXCEPCION = "/iucomunes/excepcionmodal.zul";

	public static final String PANTALLA_INICIO = "/inicio.zul";
	public static final String PANTALLA_AUTENTICACION = "/login.zul";
	public static final String PANTALLA_MENU = "/iceberg/menu/mnz_menu.zul";
	public static final String PANTALLA_ANONIMO = "/anonimo.zul";
	public static final String PANTALLA_MANTENIMIENTO = "/mantenimiento.zul";
	public static final String PANTALLA_CAMBIO_CONTRASENA = "/cambiar_contrasena.zul";
	public static final String PANTALLA_RESETEAR_CONTRASENA = "/resetear_contrasena.zul";
	public static final String PANTALLA_REPORT_PREVIEW = "/iceberg/sr/srz_report_preview.zul";

	public static final String PANTALLA_TIPO_FORMA = "F";
	public static final String PANTALLA_TIPO_REPORTE = "R";
	public static final String PANTALLA_TIPO_EMERGENTE = "E";
	public static final String PANTALLA_TIPO_PIVOT = "P";

	public static final String FORMA_EJECUTABLE = "exe";
	public static final String FORMA_REPORTE = "rep";
	
	/*
	 * constantes para definir la ruta de la ventana donde se previsualiz el
	 * correo
	 */
	public static final String PANTALLA_REPORTE_VACIO = "/iucomunes/reportevacio.zul";

	/* constantes para definir si un usuario esta o no autenticado */
	public static final Integer USUARIO_AUTENTICADO = new Integer(1);
	public static final Integer USUARIO_NO_AUTENTICADO = new Integer(0);

	/* parï¿½ï¿½metros guardados en la session */
	public static final String USUARIO_SESSION = "usuario";
	public static final String TIPO_NOMINA = "tipoNomina";

	// standard report parameter names
	public static final String REP_USER_ID = "ICEBERGRS_USER_ID";
	public static final String REP_USER_NAME = "ICEBERGRS_USER_NAME";
	public static final String REP_IMAGE_DIR = "ICEBERGRS_IMAGE_DIR";
	public static final String REP_REPORT_DIR = "ICEBERGRS_REPORT_DIR";
	public static final String REP_EXPORT_TYPE_PARAM = "ICEBERGRS_EXPORT_TYPE";	
	
	// -llave por la cual se buscarï¿½ï¿½ el nombro de un reporte en el hasmap de
	// parï¿½ï¿½metros
	public static final String REP_TITULO_REPORTE = "TITULO_REPORTE";
	public static final String REP_SUBTITULO_REPORTE = "SUBTITULO_REPORTE";
	public static final String REP_NOMBRE_PLANTILLA = "NOMBRE_PLANTILLA";
	public static final String REP_COMPONENTE_ICEBERG = "COMPONENTE_ICEBERG";
	public static final String REP_CONVENCIONES_REPORTE = "CONVENCIONES_REPORTE";
	// -- para la previsualizaciï¿½ï¿½n se mostrarï¿½ï¿½n solo las n pï¿½ï¿½ginas
	// configuradas
	// para exportar html
	public static final String REP_INDICE_INICIAL_PAGINA = "INDICE_INICIAL_PAGINA";
	public static final String REP_INDICE_FINAL_PAGINA = "INDICE_FINAL_PAGINA";
	// -- para la previsualizaciï¿½ï¿½n se mostrarï¿½ï¿½n solo las n pï¿½ï¿½ginas
	// configuradas
	// para exportar imagen
	public static final String REP_INDICE_PAGINA = "INDICE_PAGINA";
	public static final String REP_ZOOM_PAGINA = "ZOOM_PAGINA";

	// -- para configurar el tipo de pï¿½ï¿½gina del reporte LETTER - LEGAL - A3 -
	// A4
	// - A5
	public static final String REP_LETTER = "LETTER";
	public static final String REP_LEGAL = "LEGAL";
	public static final String REP_A3 = "A3";
	public static final String REP_A4 = "A4";
	public static final String REP_A5 = "A5";

	// - rutas de los recursos
	public static final String REP_RUTA_IMAGENES = "/imagenes/";
	public static final String REP_RUTA_REPORTES = "/reportes/plantillas/";
	public static final String REP_RUTA_IMAGENES_TEMP = "/imagenes/temp/";
	public static final String REP_RUTA_REPORTES_TEMP = "/reportes/plantillas/temp/";
	
	public static final String REP_RUTA_PACKAGE_TEMP = "/repositorio/temporal/";

	// ruta donde se encuentran los zul
	public static final String RUTA_FUENTES_ZUL = "/iceberg/";

	// - se definen los tipos de formatos de exportaciï¿½ï¿½n de los reportes
	public static final String REP_FORMATO_SALIDA_RTF = "rtf";
	public static final String REP_FORMATO_SALIDA_PDF = "pdf";
	public static final String REP_FORMATO_SALIDA_XLS = "xls";
	public static final String REP_FORMATO_SALIDA_HTML = "html";
	public static final String REP_FORMATO_SALIDA_IMAGEN = "png";
	public static final String REP_FORMATO_SALIDA_INVALIDO = "XXX";

	// - se definen las extensiones de los archivos generados
	public static final String REP_EXTESION_ARCHIVO_RTF = "rtf";
	public static final String REP_EXTESION_ARCHIVO_PDF = "pdf";
	public static final String REP_EXTESION_ARCHIVO_XLS = "xls";
	public static final String REP_EXTESION_ARCHIVO_HTML = "html";
	public static final String REP_EXTESION_ARCHIVO_IMAGEN = "png";

	// -nombre standard de un reporte
	public static final String REP_NOMBRE_DEFAULT = "reporte";

	// -- Se definen las constantes S = SI y N = NO para utiliazar en
	// verificaciones
	public static final String GENERAL_SI = "S"; // -podrï¿½ï¿½a ser S = SI y N = NO
	public static final String GENERAL_NO = "N";

	// --los reportes en jasper tienen predefinidos una serie de parï¿½ï¿½metros
	// --los cuales son de uso interno de la librerï¿½ï¿½a estos no deberï¿½ï¿½an verse
	// --tales parï¿½ï¿½metros tienen prefijo REPORT_% y ICEBERGRS_%
	public static final String REP_OCULTAR_PARAMETROS = GENERAL_SI; // (S) = SI
																	// y (N) =
																	// NO
	public static final String REP_OCULTAR_PARAMETROS_PREFIJO1 = "REPORT_";
	public static final String REP_OCULTAR_PARAMETROS_PREFIJO2 = "ICEBERGRS_";
	public static final String REP_OCULTAR_PARAMETROS_PREFIJO3 = "LOCAL";
	public static final String REP_OCULTAR_PARAMETROS_PREFIJO4 = "IS";
	public static final String REP_OCULTAR_PARAMETROS_PREFIJO5 = "JASPER_";
	public static final String REP_OCULTAR_PARAMETROS_PREFIJO6 = "NOMBRE_PLANTILLA";

	// mensajes generales default
	public static final String MENSAJE_DEFAULT_OK = "OK";
	public static final String MENSAJE_DEFAULT_ERROR = "Error";

	// Constantes para la creaciï¿½ï¿½n de TABs en el menï¿½ï¿½.
	public static final String IDTAB_CABECERA = "cabeza";
	public static final String IDTAB_CONTENIDO = "cuerpo";

	public static final String ID_SALIR = "idMNZTitemSalir";
	public static final String ID_CERRAR_TODOS = "idMNZTitemCerrarTodos";
	public static final boolean AUTENTICACION_LDAP = true;

	public static final String EJECUTABLE_AUTENTICACION = "LOGIN";

	/* Nombres de los modulos de la aplicacion */
	final String COMPONENTE_AA = "Autenticaci—n";
	final String COMPONENTE_CM = "Compras y suministros";
	final String COMPONENTE_FE = "Flujo de Estados";
	final String COMPONENTE_GP = "Gesti—n Persona";
	final String COMPONENTE_GE = "General";
	final String COMPONENTE_LN = "Liquidaci—n de N—mina";
	final String COMPONENTE_NO = "N—mina";
	final String COMPONENTE_PA = "Parametrizaci—n Administrativa";
	final String COMPONENTE_CG = "Contabilidad General";
	final String COMPONENTE_PG = "Presupuesto General";
	final String COMPONENTE_EX = "Expedientes";
	final String COMPONENTE_TE = "Tesorer’a";
	final String COMPONENTE_TB = "Tesorer’a Banco";
	final String COMPONENTE_TC = "Tesorer’a Caja";
	final String COMPONENTE_EP = "Presupuesto";
	final String COMPONENTE_IC = "Investigaci—n y Consultar’a";
	final String COMPONENTE_BL = "Bienestar Laboral";
	final String COMPONENTE_IA = "Integraci—n AcadŽmico";
	final String COMPONENTE_CC = "Cartera y CrŽditos";
	final String COMPONENTE_CV = "Convenios";
	final String COMPONENTE_TA = "Tarifario";
	final String COMPONENTE_FA = "Facturaci—n";
	final String COMPONENTE_BE = "Becas Estudiantes";
	final String COMPONENTE_CA = "Control de Acceso";
	final String COMPONENTE_FT = "Flujo de Componentes";

	
	/* Mensaje General Violacion Llaves unica conversion */

	// Constantes para la creaciï¿½n de los objetos de pesentacion en contexto
	// tipo de dato.
	final String OBJETO_PREVIEW_CONTEXTO = "CT";
	final String OBJETO_PREVIEW_CONTEXTO_TIPO_DATO = "CTD";

	// Parï¿½ï¿½ï¿½metros de la Aplicaciï¿½n
	final String PARAMETRO_MONEDA = "MONEDA_ASUMIDA_TH";
	final String PARAMETRO_ORIGEN = "ORIGEN_DEFAULT";

}
