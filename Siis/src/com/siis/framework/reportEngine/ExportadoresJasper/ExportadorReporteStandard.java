/**
 * ExportadorReporteStandardImpl.java
 */
package com.casewaresa.framework.reportEngine.ExportadoresJasper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import com.casewaresa.framework.contract.IConstantes;
import com.casewaresa.framework.helper.ContextoAplicacion;

/**
 * @author Fabio Bar�n
 * @date Nov 8, 2007
 * @name ExportadorReporteStandardImpl.java
 * @desc Esta clase define y carga los par�metros comunes de todos los
 *       reportes que se generen en esta aplcaci�n
 */

public abstract class ExportadorReporteStandard implements
	IExportadorReporteHelper {

    /**
     * Propiedad jasperPrint de la clase [ ExportadorReporteStandard.java ]
     * 
     * @desc: esta propiedad me guarda el reporte en 'bruto'
     */
    protected JasperPrint jasperPrint = null;

    /**
     * Propiedad nombreReporte de la clase [ ExportadorReporteStandard.java ]
     * 
     * @desc: este par�metro me indica cual ser� el nombre del reporte al
     *        exportarlo
     */
    protected String nombreReporte = IConstantes.REP_NOMBRE_DEFAULT;

    /**
     * Propiedad out de la clase [ ExportadorReporteStandard.java ]
     * 
     * @desc: Esta propiedad se encarga de gusradar referencia al reporte
     */
    protected ByteArrayOutputStream out = null;

    /**
     * @type Constructor de la clase ExportadorReporteStandard
     * @name ExportadorReporteStandard
     * @desc este es el constructor sin par�metros
     */
    public ExportadorReporteStandard() {
	super();
    }

    /**
     * @type Constructor de la clase ExportadorReporteStandard
     * @name ExportadorReporteStandard
     * @param out
     * @param nombreReporte
     * @desc constructor con los par�metros de la clase
     */
    public ExportadorReporteStandard(JasperPrint jasperPrint,
	    String nombreReporte) {
	this.jasperPrint = jasperPrint;
	this.nombreReporte = nombreReporte;
    }

    /**
     * @type M�todo de la clase JasperReportEngineHelper
     * @name prepararParametros
     * @return HashMap
     * @param parametros
     * @desc de los action retornamos un listado de par�metro pero por un lado
     *       hay que agregar los par�metros prdefinidos y por otro hay que
     *       transformarlo en un hashmap que es lo que recibe jasperreport
     */

    public void configurarParametrosPredefinidos(Map <String, Object> parametros,
	    String formato) {

	// --agregamos los par�metros por defecto
	ContextoAplicacion pContextoAplicacion = ContextoAplicacion
		.getInstance();
	parametros.put(IConstantes.REP_EXPORT_TYPE_PARAM, formato);
	parametros.put(IConstantes.REP_IMAGE_DIR,
		new File(pContextoAplicacion.getRutaContexto()
			+ IConstantes.REP_RUTA_IMAGENES));
	parametros.put(IConstantes.REP_REPORT_DIR,
		new File(pContextoAplicacion.getRutaContexto()
			+ IConstantes.REP_RUTA_REPORTES));
	parametros.put(IConstantes.REP_USER_ID, "sin ID");
	parametros.put(IConstantes.REP_USER_NAME, "Reporte");

	// --configuramos el nombre del reporte
	this.generarNombreReporte(parametros);

	// ... los que hagan falta
    }

    /**
     * @type M�todo de la clase ExportadorReporteStandard
     * @name generarNombreReporte
     * @return void
     * @param parametros
     * @desc este m�todo se encarga de generar un nombre para un reporte el
     *       nombre base se sacar� del hashmap de par�metros
     */
    public void generarNombreReporte(Map<String, Object> parametros) {
	String nombre = "";

	// --extraemos el nombre
	nombre = IConstantes.REP_NOMBRE_DEFAULT;

	// --le reemplazamos los espacios por "_"
	nombre = nombre.replaceAll(" ", "_");

	// --lo pasamos a minusculas
	nombre = nombre.toLowerCase();

	// ------- si hay necesidad de agregarle fecha y hora..
	// --aqu� es el lugar

	if (nombre != null) {
	    // -- aqu� se pone la l�gica que se necesite para nombrar el
	    // reporte
	    nombre = nombre + "_irs";
	} else {
	    // -- si no existe ese par�metro en los par�metros le damos un
	    // nombre por defecto
	    nombre = IConstantes.REP_NOMBRE_DEFAULT + "_irs";
	}

	// -- actualizamos el valor local a la clase
	this.nombreReporte = nombre;

	// --filamente obtenemos un nombre de tipo
	// estado_financiero_irs

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.casewaresa.iceberg_con.helper.ExportadoresJasper.IExportadorReporteHelper
     * #cargarCompilarReporte(java.lang.String, java.sql.Connection,
     * java.util.HashMap)
     */
    public JasperDesign cargarReporte(String rutaReporte, Map<String, Object> propiedades)
	    throws Exception {

	JasperDesign jasperDesign = null;

	// -- cargamos y compilamos el reporte
//	jasperDesign = new CargarReporteJasperHelper()
//		.cargarReporte(rutaReporte);

	// if(jasperDesign.getOrientation() ==
	// JasperDesign.ORIENTATION_PORTRAIT){
	// jasperDesign.setPageWidth(new
	// Integer(propiedades.get("PAGE_WIDTH")!=null?propiedades.get("PAGE_WIDTH").toString():
	// new Integer(jasperDesign.getPageWidth()).toString())); // LEGAL(612)
	// - LETTER(612)
	// jasperDesign.setPageHeight(new
	// Integer(propiedades.get("PAGE_HEIGHT")!=null?propiedades.get("PAGE_HEIGHT").toString():new
	// Integer(jasperDesign.getPageHeight()).toString()));// LEGAL(1008) -
	// LETTER(792)
	// //jasperDesign.setColumnWidth(new
	// Integer(propiedades.get("PAGE_WIDTH")!=null?propiedades.get("PAGE_WIDTH").toString():new
	// Integer(jasperDesign.getPageWidth()).toString()) - 80);
	// jasperDesign.setOrientation(JasperDesign.ORIENTATION_PORTRAIT);
	// }else if(jasperDesign.getOrientation() ==
	// JasperDesign.ORIENTATION_LANDSCAPE) {
	// jasperDesign.setPageWidth(new
	// Integer(propiedades.get("PAGE_WIDTH")!=null?propiedades.get("PAGE_HEIGHT").toString():new
	// Integer(jasperDesign.getPageWidth()).toString()));// LEGAL(1008) -
	// LETTER(792)
	// jasperDesign.setPageHeight(new
	// Integer(propiedades.get("PAGE_HEIGHT")!=null?propiedades.get("PAGE_WIDTH").toString():new
	// Integer(jasperDesign.getPageHeight()).toString()));// LEGAL(612) -
	// LETTER(612)
	// //jasperDesign.setColumnWidth(new
	// Integer(propiedades.get("PAGE_WIDTH")!=null?propiedades.get("PAGE_HEIGHT").toString():new
	// Integer(jasperDesign.getPageHeight()).toString()) - 80);
	// jasperDesign.setOrientation(JasperDesign.ORIENTATION_LANDSCAPE);
	// }

	// -- lo retornamos
	return jasperDesign;
    }

    public JasperPrint compilarReporte(Connection conn, Map<String, Object> propiedades,
	    JasperDesign jasperDesign) throws Exception {

	JasperPrint jasperPrint = null;
	JasperReport jasperReport = null;

	// -- cargamos y compilamos el reporte
//	jasperReport = new CargarReporteJasperHelper()
//		.compilarReporte(jasperDesign);

	// -- lo retornamos

	// -- exportamos el reporte a formato en 'bruto'

	jasperPrint = JasperFillManager.fillReport(jasperReport, propiedades,
		conn);

	// -----------cerramos la conexion a la BD
	conn.close();

	// -- lo retornamos
	return jasperPrint;
    }

    /**
     * @type M�todo de la clase ExportadorReporteStandard
     * @name getNombreReporte
     * @return void
     * @param nombreReporte
     * @desc obtiene el valor de la propiedad nombreReporte
     */
    public String getNombreReporte() {
	return nombreReporte;
    }

    /**
     * @type M�todo de la clase ExportadorReporteStandard
     * @name setNombreReporte
     * @return String
     * @param nombreReporte
     * @desc Actualiza el valor de la propiedad nombreReporte
     */
    public void setNombreReporte(String nombreReporte) {
	this.nombreReporte = nombreReporte;
    }

    /**
     * @type M�todo de la clase ExportadorReporteStandard
     * @name getJasperPrint
     * @return void
     * @param jasperPrint
     * @desc obtiene el valor de la propiedad jasperPrint
     */
    public JasperPrint getJasperPrint() {
	return jasperPrint;
    }

    /**
     * @type M�todo de la clase ExportadorReporteStandard
     * @name setJasperPrint
     * @return JasperPrint
     * @param jasperPrint
     * @desc Actualiza el valor de la propiedad jasperPrint
     */
    public void setJasperPrint(JasperPrint jasperPrint) {
	this.jasperPrint = jasperPrint;
    }
}
