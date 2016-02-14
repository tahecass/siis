package com.siis.configuracion;

import java.util.Collection;
import org.apache.ibatis.session.SqlSession; 

public class Conexion {
	SqlSession session;

	public Conexion() { 
		session = new MyBatisUtil().getSession();
	}

	 

	public Object obtenerListado(String qryName, Object objeto) throws Exception {
		return listar(qryName, objeto);
	}

	public Collection<Object> listar(String sqlName, Object object) throws Exception {

		try {
			return session.selectList(sqlName, object);
		} finally {
			session.close();
		}
	}

	

	public void guardar(String nombreConsulta, Object objeto) {
		if (session != null) {
			try {
				session.insert(nombreConsulta, objeto);
				session.commit();
			} finally {
				session.close();

			}
		}

	}
}
