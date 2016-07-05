/**
 * FactoryStandar.java
 */
package com.casewaresa.framework.factory;

import org.apache.log4j.Logger;

import com.casewaresa.framework.helper.ClassLoaderHelper;

/**
 * @author Fabio Bar�n
 * @date 23/01/2007
 * @name FactoryStandard.java
 * @desc define una fabrica de objetos, la cual puede instanciar objetos a partir de un nombre 
 * 	     v�lido de clase.
 */
public abstract class FactoryStandard 
{
	
    /** desc:  este helper ayuda a crear instancias de clases*/
    protected ClassLoaderHelper classLoader = new ClassLoaderHelper();
    public Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * @type   Constructor de la clase FactoryStandar
	 * @name   FactoryStandar 
	 * @return void
	 * @desc   Crea una nueva instancia de FactoryStandar 
	 */
	public FactoryStandard() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @type   M�todo de la clase DaoFactory
	 * @name   executeMetodoDAO 
	 * @return Object
	 * @desc   permite ejecutar el metodo pMetodo del Objeto pObject
	 *         el m�todo no tiene par�metros 
	 */
	public Object executeMetodo (Object pObjet,String pNombreMetodo)
	{
		//guardamos el resultado del m�todo
		Object   resultado       = null;
		Class<?>[]  pTipoParametros = null;
        Object[] pParametros     = null;		

		try 
		{
			//ejecuta un metodo de un objeto... que no tiene par�metros			
			resultado = classLoader.executeMethod(pObjet,pNombreMetodo,pTipoParametros,pParametros);
		} 
		catch (Exception e) 
		{		
			log.error(e.getMessage(),e);
		}
		return resultado;
	}
	
	/**
	 * @type   M�todo de la clase DaoFactory
	 * @name   executeMetodoDAO 
	 * @return Object
	 * @desc   permite ejecutar el metodo pMetodo de la clase pDao
	 *         el m�todo no tiene par�metros 
	 */
	public Object executeMetodo (String pClase,String pMetodo)
	{
	  //ejecuta un metodo de la clase... que no tiene par�mtros	
	  return executeMetodo (pClase,pMetodo,null,null);
	}
	
	/**
	 * @type   M�todo de la clase DaoFactory
	 * @name   executeMetodoDAO 
	 * @return Object
	 * @desc   permite ejecutar el metodo pMetodo de la clase pDao
	 *         con los parametros (Tipo = pTipoParametros, valor = pParametros) 
	 */
	public Object executeMetodo (   String pClase,
					                String pNombreMetodo,
					                Class<?>[] pTipoParametros,
					                Object[] pParametros)
	{		
		//guardamos el resultado del m�todo
		Object resultado = null;
		
		try 
		{
			resultado = classLoader.executeMethod(pClase,pNombreMetodo,pTipoParametros,pParametros);
		} 
		catch (Exception e) 
		{		
			log.error(e.getMessage(),e);
		}
		return resultado;
	}
	
	/**
	 * @type   M�todo de la clase DaoFactory
	 * @name   getDAO 
	 * @return Object
	 * @desc   permite obtener una instancia del DAO de tipo pDao
	 *          
	 */
	public Object getObject (String pObject)
	{
		/// guardamos el objeto
		Object ObjetoDAO = null;
		
		//obtenermos una instancia de la clase... del tipo pDao
		ObjetoDAO = classLoader.getObject(pObject);
		
		//retonamos el resultado
		return ObjetoDAO;
	}
}
