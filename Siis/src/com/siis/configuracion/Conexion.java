package com.siis.configuracion;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.siis.dto.Banco;
import com.siis.dto.Cliente;
import com.siis.dto.Cuenta;

public class Conexion {
	private SqlSession session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
	private static final Conexion conexion = new Conexion();

	public Object obtenerListado(String qryName, Object objeto) throws Exception {
		return listar(qryName, objeto);
	}

	public Object obtenerRegistro(String sqlName, Object object) throws Exception {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				return session.selectOne(sqlName, object);
			} finally {
				session.close();
			}
		}
		return null;
	}

	public static Conexion getConexion() {

		return conexion;
	}

	public Collection<Object> listar(String sqlName, Object object) throws Exception {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				return session.selectList(sqlName, object);
			} finally {
				session.close();
			}
		}
		return null;
	}
	public List<Cliente> listarClientes(String sqlName, Object object) throws Exception {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				return session.selectList(sqlName, object);
			} finally {
				session.close();
			}
		}
		return null;
	}
	public List<Banco> listarBancos(String sqlName, Object object) throws Exception {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				return session.selectList(sqlName, object);
			} finally {
				session.close();
			}
		}
		return null;
	}
	public List<Cuenta> listarCuentas(String sqlName, Object object) throws Exception {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				return session.selectList(sqlName, object);
			} finally {
				session.close();
			}
		}
		return null;
	}
	public void guardar(String nombreConsulta, Object objeto) {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				session.insert(nombreConsulta, objeto);
				session.commit();
			} finally {
				session.close();

			}
		}

	}
	public void actualizar(String nombreConsulta, Object objeto) {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				session.update(nombreConsulta, objeto);
				session.commit();
			} finally {
				session.close();

			}
		}
	}
	public void eliminar(String nombreConsulta, Object objeto) {
		session = new MyBatisUtil().getInstance().getSessionFactory().openSession(true);
		if (session != null) {
			try {
				session.delete(nombreConsulta, objeto);
				session.commit();
			} finally {
				session.close();

			}
		}

	}

}
