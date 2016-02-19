package com.siis.viewModel.framework;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.siis.configuracion.MyBatisUtil;
import com.siis.dto.Bean;
import com.siis.dto.Cliente;

public class Conexion {
	SqlSession session;

	public Conexion() {
		session = new MyBatisUtil().getSession();
	}

	public List<Bean> listar(String nombreConsulta, Bean objeto) {

		List<Bean> listaDatos = null;
		if (session != null) {
			try {
				listaDatos = session.selectList(nombreConsulta, objeto);

				System.out.println("Hay " + listaDatos.size() + " Registros encontrados.");

			} finally {
				session.close();

			}
		}
		return listaDatos;
	}
	public List<Cliente> listarClientes(String nombreConsulta, Cliente objeto) {

		List<Cliente> listaDatos = null;
		if (session != null) {
			try {
				listaDatos = session.selectList(nombreConsulta, objeto);

				System.out.println("Hay " + listaDatos.size() + " Registros encontrados.");

			} finally {
				session.close();

			}
		}
		return listaDatos;
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
