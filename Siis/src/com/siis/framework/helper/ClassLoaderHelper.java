/**
 * ClassLoaderHelper.java
 */
package com.casewaresa.framework.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * @author Caseware
 * @date 23/01/2007
 * @name ClassLoaderHelper.java
 * @desc provee funcionalidades para el carga y ejecuci�n de clases y m�todos
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ClassLoaderHelper
{
	public Logger log = Logger.getLogger(this.getClass());
	/**
	 * @type   Constructor de la clase ClassLoaderHelper
	 * @name   ClassLoaderHelper 
	 * @return void
	 * @desc   Crea una nueva instancia de ClassLoaderHelper
	 */	
	public  ClassLoaderHelper(){
	}

	/**
	 * @type   M�todo de la clase ClassLoaderHelper
	 * @name   vmClassLoader 
	 * @return IVMClassLoader
	 * @throws ClassNotFoundException 
	 * @desc   Permite cargar una clase en tiempo de ejecuci�n con el nombre " cls "
	 */
	public Class loadClass(String cls) throws ClassNotFoundException{
		
		Class toRun = null;
		
		//System.out.println("Loading 1.2 VMClassLoader");
		Thread t = Thread.currentThread();
		ClassLoader cl = t.getContextClassLoader();

		toRun = cl.loadClass(cls);
		return toRun;  
	}
	
	/**
	 * @type   M�todo de la clase ClassLoaderHelper
	 * @name   executeMethod 
	 * @return Object
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @desc   Permite ejecutar el m�todo pNombre Metodo de la clase pClase
	 *         con los parametros (Tipo = pTipoParametros, valor = pParametros) 
	 */
	public Object executeMethod(String pClase, String pNombreMetodo,Class[] pTipoParametros,Object[] pParametros) 
	        throws ClassNotFoundException, 
	               SecurityException, 
	               NoSuchMethodException, 
	               InstantiationException, 
	               IllegalAccessException, 
	               IllegalArgumentException, 
	               InvocationTargetException
	{		
		Class toRun = null;
		Object oToRun = null;
		
		//cargamos la clase del parametro pClase
		toRun = loadClass(pClase);
		
		//obtenemos el m�todo que se iguale al nombre del par�mstro 
		Method _func_to_call = toRun.getMethod(pNombreMetodo, pTipoParametros);

		//instanciamos esta clase.. para poder.. ejecutar el m�todo
		oToRun = toRun.newInstance(); 
        
		//invocamos el mm�todo en el objeto recientemente creado
       return _func_to_call.invoke(oToRun, pParametros);
	}
	
	/**
	 * @type   M�todo de la clase ClassLoaderHelper
	 * @name   executeMethod 
	 * @return Object
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @desc   Permite ejecutar un m�todo pNombreMetodo sobre la clase dada pToRun
	 *         con los parametros (Tipo = pTipoParametros, valor = pParametros)
	 */
	public Object executeMethod(Class pToRun, String pNombreMetodo,Class[] pTipoParametros, Object[] pParametros)
			throws SecurityException, 
				   NoSuchMethodException,
				   InstantiationException, 
				   IllegalAccessException,
				   IllegalArgumentException, 
				   InvocationTargetException{		
		Class toRun = pToRun;
		Object oToRun = null;
		
		//obtenemos el m�todo que se iguale al nombre del par�mstro 
		Method _func_to_call = toRun.getMethod(pNombreMetodo, pTipoParametros);

		//instanciamos esta clase.. para poder.. ejecutar el m�todo
		oToRun = toRun.newInstance(); 
        
		//invocamos el mm�todo en el objeto recientemente creado
       return _func_to_call.invoke(oToRun, pParametros);
	}	
	
	/**
	 * @type   M�todo de la clase ClassLoaderHelper
	 * @name   executeMethod 
	 * @return Object
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @desc   Permite ejecutar un m�todo pNombreMetodo sobre el objeto dado pToRun
	 *         con los parametros (Tipo = pTipoParametros, valor = pParametros)
	 */
	public Object  executeMethod(Object pToRun, String pNombreMetodo,Class[] pTipoParametros,Object[] pParametros) 
		    throws SecurityException, 
		    	   NoSuchMethodException, 
		    	   IllegalArgumentException, 
		    	   IllegalAccessException, 
		    	   InvocationTargetException{	
		//asignamos el objeto
		Object oToRun = pToRun;
		
		//obtenemos la clase del objeto
		Class  toRun  = oToRun.getClass(); 
		
		//obtenemos el m�todo que se iguale al nombre del par�mstro 
		Method _func_to_call = toRun.getMethod(pNombreMetodo, pTipoParametros);

		//invocamos el mm�todo en el objeto recientemente creado
       return _func_to_call.invoke(oToRun, pParametros);
	}	
	
	/**
	 * @type   M�todo de la clase ClassLoaderHelper
	 * @name   getObject 
	 * @return Object
	 * @desc   Permite instanciar una clase pToRun 
	 *         
	 */
	public Object getObject (String pToRun){
		Class toRun   = null;
		Object oToRun = null;

		try {			
			//cargamos la clase del parametro pClase
			toRun = loadClass(pToRun);
			
			//instanciamos esta clase.. 
			oToRun = toRun.newInstance(); 

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(),e);
		} catch (InstantiationException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(),e);
		}	
		
		return oToRun;
	}
	
	/**
	 * @type   M�todo de la clase ClassLoaderHelper
	 * @name   getObject 
	 * @return Object
	 * @desc   Permite instanciar una clase pToRun 
	 *         
	 */
	public Object getObject (Class pToRun){
		Class toRun   = pToRun;
		Object oToRun = null;

		try {						
			//instanciamos esta clase.. 
			oToRun = toRun.newInstance(); 
		} catch (InstantiationException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(),e);
		}		
		return oToRun;
	}	
	
	/**
	 * @type   M�todo de la clase ClassLoaderHelper
	 * @name   getObject 
	 * @return Object
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @desc   Permite instanciar una clase pToRun por un constructor con parametros 
	 *         
	 */
	public Object getObject (String pNombreClase,Class[] pNombreParametros,Object[] pValoresParamtetros) 
	       throws IllegalArgumentException, 
	              InstantiationException, 
	              IllegalAccessException, 
	              InvocationTargetException, 
	              ClassNotFoundException, 
	              SecurityException, 
	              NoSuchMethodException
	{
		Class toRun   = null;
		Object oToRun = null;
		Constructor constructor = null;

			//cargamos la clase del parametro pClase
			toRun = loadClass( pNombreClase );
			
			//-obtenemos el constructor de la clase deseada por medio de los parametros enviados
			constructor = toRun.getConstructor( pNombreParametros );

			//instanciamos esta clase.. 
			oToRun = constructor.newInstance( pValoresParamtetros ); 

			//retornamos la clase instanciada e inicializada
			return oToRun;
	}	
}