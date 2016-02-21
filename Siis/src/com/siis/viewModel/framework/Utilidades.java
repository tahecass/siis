package com.siis.viewModel.framework;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import fi.jawsy.jawwa.zk.gritter.Gritter;

public class Utilidades {

	public static Component onCargarVentana(Component contenedor, String rutaForma, Map<String, Object> arg)
			throws Exception {

		return Executions.createComponents(rutaForma, contenedor, arg);
	}

	public static void mostrarNotificacion(String titulo, String msg, String tipoNotificacion) {
		String urlImage = "";
		if (tipoNotificacion.equals("INFO")) {
			urlImage = "imagenes/informacion.png";
		} else if (tipoNotificacion.equals("ADVERTENCIA")) {
			urlImage = "imagenes/advertencia.png";
		} else if (tipoNotificacion.equals("ERROR")) {
			urlImage = "imagenes/error.png";
		}
		Gritter.notification().withTitle(titulo).withText(msg).withTime(5000).withImage(urlImage).show();

	}
}
