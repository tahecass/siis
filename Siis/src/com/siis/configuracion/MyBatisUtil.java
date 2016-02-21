package com.siis.configuracion;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	/** objeto Singleton */
	private static MyBatisUtil myBatisUtil = new MyBatisUtil();
	private String resource = "mybatis-config.xml";
	private SqlSession session = null;

	public static MyBatisUtil getInstance() {
		return myBatisUtil;
	}

	public SqlSessionFactory getSessionFactory() {
		System.out.println("Ejecutando [getSession]... ");
		SqlSessionFactory sqlSessionFactory = null;
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			return new SqlSessionFactoryBuilder().build(reader);

		} catch (IOException e) {
			e.printStackTrace();
		} 
		return sqlSessionFactory;
	}
}
