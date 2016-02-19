package com.siis.viewModel.framework;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

public class Utilidades {

	public static Component onCargarVentana(Component contenedor, String rutaForma, Map<String, Object> arg)
			throws Exception {

		return Executions.createComponents(rutaForma, contenedor, arg);
	}
}
