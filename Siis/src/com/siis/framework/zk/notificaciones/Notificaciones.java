package com.casewaresa.framework.zk.notificaciones;

import fi.jawsy.jawwa.zk.gritter.Gritter;
import fi.jawsy.jawwa.zk.gritter.Gritter.NotificationBuilder;

/**
 * Clase que contiene los metodos para generar notificaciones( basadas en
 * gritter)
 * 
 * @author dperezc
 * 
 */
public class Notificaciones {

    private static final String img_informacion = "/imagenes/gritter/information.png";
    private static final String img_alerta = "/imagenes/gritter/alert.png";
    private static final String img_error = "/imagenes/gritter/error.png";

    /**
     * Genera una notificacion en un gritter personalizado con un estilo que se
     * crea en la pagina inicio.zul (gritter-iceberg)
     * 
     * @param titulo
     *            Titulo de la notificacion
     * @param contenido
     *            Contenido de la notificacion
     * @param tiempo
     *            Tiempo para que la notificacion se cierre
     * @return NotificationBuilder
     */
    private static NotificationBuilder getNotificacion(String titulo,
	    String contenido, Integer tiempo) {
	if (tiempo != null) {
	    return Gritter.notification().withSclass("gritter-iceberg")
		    .withTitle(titulo).withText(contenido).withTime(tiempo);
	} else {
	    return Gritter.notification().withSclass("gritter-iceberg")
		    .withTitle(titulo).withText(contenido).withTime(Integer.MAX_VALUE);
	}
    }

    /**
     * Muestra una notificacion normal(simple) con un titulo , un contenido y un
     * tiempo siempre y cuando se determine
     * 
     * @param titulo
     *            Titulo de la notificacion
     * @param contenido
     *            Contenido de la notificacion
     * @param tiempo
     *            Tiempo para que la notificacion se cierre
     */
    public static void mostrarNotificacion(String titulo, String contenido,
	    Integer tiempo) {
	getNotificacion(titulo, contenido, tiempo).show();
    }

    /**
     * Muestra una notificacion con la imagen definida para las notificaciones
     * de informacion con un titulo , un contenido y un tiempo siempre y cuando
     * se determine
     * 
     * @param titulo
     *            Titulo de la notificacion
     * @param contenido
     *            Contenido de la notificacion
     * @param tiempo
     *            Tiempo para que la notificacion se cierre
     */
    public static void mostrarNotificacionInformacion(String titulo,
	    String contenido, Integer tiempo) {
	getNotificacion(titulo, contenido, tiempo).withImage(img_informacion)
		.show();
    }

    /**
     * Muestra una notificacion con la imagen definida para las notificaciones
     * de error con un titulo , un contenido y un tiempo siempre y cuando
     * se determine
     * 
     * @param titulo
     *            Titulo de la notificacion
     * @param contenido
     *            Contenido de la notificacion
     * @param tiempo
     *            Tiempo para que la notificacion se cierre
     */
    public static void mostrarNotificacionError(String titulo,
	    String contenido, Integer tiempo) {
	getNotificacion(titulo, contenido, tiempo).withImage(img_error).show();
    }

    /**
     * Muestra una notificacion con la imagen definida para las notificaciones
     * de alerta con un titulo , un contenido y un tiempo siempre y cuando
     * se determine
     * 
     * @param titulo
     *            Titulo de la notificacion
     * @param contenido
     *            Contenido de la notificacion
     * @param tiempo
     *            Tiempo para que la notificacion se cierre
     */
    public static void mostrarNotificacionAlerta(String titulo,
	    String contenido, Integer tiempo) {
	getNotificacion(titulo, contenido, tiempo).withImage(img_alerta).show();
    }

}
