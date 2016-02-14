package com.siis.configuracion;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	private String resource = "mybatis-config.xml";
	private SqlSession session = null;

	public SqlSession getSession() {
		System.out.println("Ejecutando [getSession]... ");

		try {
			Reader reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(reader);
			session = sqlSessionFactory.openSession();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("¡¡SqlSession configurado OK!!");
		return session;
	}
}
