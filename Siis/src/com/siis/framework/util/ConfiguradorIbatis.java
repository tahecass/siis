package com.casewaresa.framework.util;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 * @author iceberg
 *
 */
public class ConfiguradorIbatis {

	/** Creates a new instance of Configuracion */
    public static final ConfiguradorIbatis pConfiguradorIbatis = new ConfiguradorIbatis();

	/** Propiedad log de la clase [ BalanceAction.java ] 
	 *  @desc: provee un mecanismo para el manejo de mensajes */
	protected static Logger log = Logger.getLogger(ConfiguradorIbatis.class);
    
    /**
     * ibatis 
     */
    private SqlSessionFactory sqlSessionFactory ;
    
    
	private ConfiguradorIbatis() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    /**
     * @return
     */
    public static ConfiguradorIbatis getInstance(){
    	return pConfiguradorIbatis;
    }
    
    /**
     * configura ibatis para el acceso a la BD
     */
    public void configurar(String ambiente){
		try {
			 String resource = "configuration.xml";
			 Reader reader = Resources.getResourceAsReader(resource);
			 			 
			 sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,ambiente);
			 
			 log.info("SqlSession configurado OK!! ");
		} 
		catch (IOException e) {
			log.error(e.getMessage(),e);
		} 
    }
    	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
