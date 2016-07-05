package com.casewaresa.framework.excepciones.impl;

import com.casewaresa.framework.contract.IExcepcion;

public class Excepcion extends Exception  {

	/** Propiedad idExcepcion de la clase [ ExcepcionGenericaStandar.java ] 
	 *  @desc: identifica de forma �nica esta excepci�n.. */
	public int idExcepcion = -1; //no tiene identificador v�lido ya que no se puede instanciar
	
	/** Propiedad serialVersionUID de la clase [ ExcepcionGlobalExt.java ] 
	 *  @desc: para evitar el warning */
	private static final long serialVersionUID = 1L;

	/** Propiedad pTituloExcepcionKey de la clase [ ExcepcionGlobalExt.java ] 
	 *  @desc: guarda el valor del key (i18n) para el titulo del error */
	private String pTituloExcepcionKey = null;
	
	/** Propiedad pCausaExcepcionKey de la clase [ ExcepcionGlobalExt.java ] 
	 *  @desc: guarda el valor del key (i18n) para la causa del error */
	private String pCausaExcepcionKey = null;
	
	/** Propiedad pSolucionExcepcionKey de la clase [ ExcepcionGlobalExt.java ] 
	 *  @desc: guarda el valor del key (i18n) para la causa del error */
	private String pSolucionExcepcionKey = null;

	/** Propiedad pImagen de la clase [ ExcepcionGlobalExt.java ] 
	 *  @desc: guarda el valoe de la ruta de la imagen asociada a esta clase */
	private String pImagen = IExcepcion.IMAGEN_EXCEPTION_GLOBAL_EXT;
	


	public Excepcion(int id,String message, Throwable cause) {
		super(message, cause);
		idExcepcion = id;
		// TODO Auto-generated constructor stub
	}


	public Excepcion(int id,String message) {
		super(message);
		idExcepcion = id;
		// TODO Auto-generated constructor stub
	}


	public Excepcion(int id,Throwable cause) {
		super(cause);
		idExcepcion = id;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @type   M�todo de la clase ExcepcionGlobalExt
	 * @name   crearLlaveInternacionalizacion
	 * @return void
	 * @desc   este m�todo permite el auto-hallazgo de los key para esta clase 
	 */
	private void crearLlaveTitulo(){
		
		String llave = "";
		
		/**primero hallamos la llave de la causa*/
		llave = IExcepcion.TAG_EXCEPCION_I18N + "." 
		         + idExcepcion + "." 
		         + "caption";  /*reutilizamos el tag caption del archivo de lenguaje.. para este prop�sito*/
		
		//--actualizamos el campo local
		pTituloExcepcionKey = llave;
	}
	
	
	/**
	 * @type   M�todo de la clase ExcepcionGlobalExt
	 * @name   crearLlaveInternacionalizacion
	 * @return void
	 * @desc   este m�todo permite el auto-hallazgo de los key para esta clase 
	 */
	private void crearLlaveCausa(){
		
		String llave = "";
		
		/**primero hallamos la llave de la causa*/
		llave = IExcepcion.TAG_EXCEPCION_I18N + "." 
		         + idExcepcion + "." 
		         + "prompt";  /*reutilizamos el tag prompt del archivo de lenguaje.. para este prop�sito*/
		
		//--actualizamos el campo local
		pCausaExcepcionKey = llave;
	}
	
	/**
	 * @type   M�todo de la clase ExcepcionGlobalExt
	 * @name   crearLlaveInternacionalizacion
	 * @return void
	 * @desc   este m�todo permite el auto-hallazgo de los key para esta clase 
	 */
	private void crearLlaveSolucion(){
		
		String llave = "";
		
		/**primero hallamos la llave de la soluci�n*/
		llave = IExcepcion.TAG_EXCEPCION_I18N + "." 
		         + idExcepcion + "." 
		         + "tooltip";/*reutilizamos el tag tooltip del archivo de lenguaje.. para este prop�sito*/
		
		//--actualizamos el campo local
		pSolucionExcepcionKey = llave;		
	}


	public String getPCausaExcepcionKey() {
		return pCausaExcepcionKey;
	}


	public void setPCausaExcepcionKey(String causaExcepcionKey) {
		pCausaExcepcionKey = causaExcepcionKey;
	}


	public String getPImagen() {
		return pImagen;
	}


	public void setPImagen(String imagen) {
		pImagen = imagen;
	}


	public String getPSolucionExcepcionKey() {
		return pSolucionExcepcionKey;
	}


	public void setPSolucionExcepcionKey(String solucionExcepcionKey) {
		pSolucionExcepcionKey = solucionExcepcionKey;
	}


	public String getPTituloExcepcionKey() {
		return pTituloExcepcionKey;
	}


	public void setPTituloExcepcionKey(String tituloExcepcionKey) {
		pTituloExcepcionKey = tituloExcepcionKey;
	}
	

	public void lanzarExcepcionConsola() {
		// TODO Auto-generated method stub
		this.printStackTrace();
	}

	
	/**
	 * @type   M�todo de la clase ExcepcionGlobalExt
	 * @name   getlabel
	 * @return String
	 * @param pLlave
	 * @return
	 * @desc   obtiene un valor internacionalizado para la pLlave correspondiente 
	 */
	private String getLabelException ( String pLlave ){
		
		/*String pValor = null;
		
		//--obtenemos la fachada para conseguir el valor		
		pValor =  IdiomaFacade.getLabel( pLlave );
		
		//--retornamos el valor*/
		return "";			
	}
	
	/* * @type   M�todo de la clase ExcepcionGlobalExt
	 * @name   getTituloInternacionalizado
	 * @return String
	 * @desc   Obtiene la cadena internacionaizada del campo Titulo   
	 */
	public String getTituloInternacionalizada (){

		String valor = null;

		//---generamos una llave a partir de la informaci�n por default
		crearLlaveTitulo();

		//---obtenemos la cadena internacionalizada para esta llave
		valor = getLabelException( this.pTituloExcepcionKey );
		
		return valor; 
	}
	

	/**
	 * @type   M�todo de la clase ExcepcionGlobalExt
	 * @name   getCausaInternacionalizada
	 * @return String
	 * @desc   Obtiene la cadena internacionaizada del campo Causa   
	 */
	public String getCausaInternacionalizada (){

		String valor = null;

		//---generamos una llave a partir de la informaci�n por default
		crearLlaveCausa();

		//---obtenemos la cadena internacionalizada para esta llave
		valor = getLabelException( this.pCausaExcepcionKey );
		
		return valor; 
	}
	
		
	/**
	 * @type   M�todo de la clase ExcepcionGlobalExt
	 * @name   getCausaInternacionalizada
	 * @return String
	 * @desc   Obtiene la cadena internacionaizada del campo Causa   
	 */
	public String getSolucionInternacionalizada (){

		String valor = null;
		
		//---generamos una llave a partir de la informaci�n por default
		crearLlaveSolucion();
		
		//---obtenemos la cadena internacionalizada para esta llave
		valor = getLabelException( pSolucionExcepcionKey );
		
		return valor; 
	}	
}