/**
 * ReporteVacioAction.java
 */
package com.siis.framework.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.casewaresa.framework.contract.IConstantes;

/**
 * @author Fabio Bar�n
 * @date Nov 16, 2007
 * @name ReporteVacioAction.java
 * @desc Este action me presenta la informaci�n del porque
 * 		 el reporte que acabaron de ejeuctar est� vacio 
 */
@SuppressWarnings("rawtypes")
public class ReporteVacioAction extends ActionStandard {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 364661269486448256L;

	/** Propiedad log de la clase [ ReporteVacioAction.java ] 
	 *  @desc: provee un mecanismo para el manejo de mensajes */
	protected static Logger log = Logger.getLogger(ReporteVacioAction.class);	
	
	/** Propiedad parametros de la clase [ ReporteVacioAction.java ] 
	 *  @desc: estos fueron los par�metros que se utilizaron para generar el reporte */
	private HashMap<String,Object> parametros = null;

	/**
	 * @type   M�todo de la clase ReporteVacioAction
	 * @name   getParametros
	 * @return void
	 * @param parametros
	 * @desc   obtiene el valor de la propiedad parametros
	 */
	public HashMap<String,Object> getParametros() {
		return parametros;
	}

	/**
	 * @type   M�todo de la clase ReporteVacioAction
	 * @name   setParametros
	 * @return HashMap
	 * @param parametros
	 * @desc   Actualiza el valor de la propiedad parametros
	 */
	public void setParametros(HashMap<String,Object> parametros) {
		this.parametros = parametros;
	}
	
	/**
	 * @type   M�todo de la clase ReporteVacioAction
	 * @name   mostrarInformaci�n
	 * @return void
	 * @desc   volcamos los par�metros en el zul
	 */
	public void mostrarInformacion(){
		log.info("ejecutando [ mostrarInformacion ]... ");
		
		Label tituloReporte   = (Label)this.getFellow("tituloReporte");
		Label nombreReporte   = (Label)this.getFellow("nombreReporte");
		Label labelMensaje    = (Label)this.getFellow("labelMensaje");
		Rows  filasParametros = (Rows)this.getFellow("filasParametros");
		Row   filaParametro   = null;
		Iterator it = this.parametros.entrySet().iterator();
		String llave = "";
		
		//--mostramos el mensaje de reporte vac�o
		labelMensaje.setValue( "Los parámetros suministrados no produjeron resultados.");
		nombreReporte.setValue(this.parametros.get(IConstantes.REP_NOMBRE_PLANTILLA).toString());
		
		//recorremos todos los parametros del reporte
		while (it.hasNext()) {
			Map.Entry item = (Map.Entry)it.next();			
			llave = item.getKey().toString();
			
			//-- si existe el par�metro titulo del reportes loactualizamos en la interfaz
			if ( llave.equals(IConstantes.REP_TITULO_REPORTE) ){
				tituloReporte.setValue( item.getValue().toString() );
			}			
			
			//--evaluamos si escondemos o no los par�metros ocultos de los reporte
			if (IConstantes.REP_OCULTAR_PARAMETROS.equals(IConstantes.GENERAL_SI)){
				//--verificamos que par�metros se esconden y cuales no 
				if ( !item.getKey().toString().startsWith( IConstantes.REP_OCULTAR_PARAMETROS_PREFIJO1 ) 
				  && !item.getKey().toString().startsWith( IConstantes.REP_OCULTAR_PARAMETROS_PREFIJO2)
				  && !item.getKey().toString().startsWith( IConstantes.REP_OCULTAR_PARAMETROS_PREFIJO3)
				  && !item.getKey().toString().startsWith( IConstantes.REP_OCULTAR_PARAMETROS_PREFIJO4)
				  && !item.getKey().toString().startsWith( IConstantes.REP_OCULTAR_PARAMETROS_PREFIJO5)
				  && !item.getKey().toString().startsWith( IConstantes.REP_OCULTAR_PARAMETROS_PREFIJO6)){
					
					//--si pasan hasta aqu� es que son par�metros definidos por el dise�ador de reportes
					//--generamos la nueva fila con el par�metro
					filaParametro = new Row();
					filaParametro.appendChild( new Label( item.getKey().toString() ));
					filaParametro.appendChild( new Label( item.getValue()!=null?item.getValue().toString():"" ));
					filasParametros.appendChild( filaParametro );
				}
			}
			//--por otro lado, si estan habilitados TODOS los par�metros, entoces se muestran todos
			else{
				
				//--generamos la nueva fila con el par�metro
				filaParametro = new Row();
				filaParametro.appendChild( new Label( item.getKey().toString() ));
				filaParametro.appendChild( new Label( item.getValue().toString() ));
				filasParametros.appendChild( filaParametro );
				
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.Window#doModal()
	 */
	@Override
	public void doModal() {
		mostrarInformacion();
		super.doModal();
	}	
}
