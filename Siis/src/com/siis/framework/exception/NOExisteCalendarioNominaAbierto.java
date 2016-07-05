package com.casewaresa.framework.exception;

/**
 * <custom-attributes MSG_NO_EXISTE_CALENDARIO_NOMINA_SELECCIONADO="No se ha podido Seleccionar un Calendario N�mina Abierto por el Tipo N�mina Seleccionado en Sesi�n."/>
 * 
 * @author JMORA
 * 
 */
public class NOExisteCalendarioNominaAbierto extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -702593127190368794L;
    private final static String mensaje = "No se ha selecciona un Calendario Nómina por el tipo Nómina en Sesion";;

    /**
     * 
     */
    public NOExisteCalendarioNominaAbierto() {

    }

    /**
     * @param message
     */
    public NOExisteCalendarioNominaAbierto(String message) {
	super(mensaje);

    }

    /**
     * @param message
     * @param cause
     */
    public NOExisteCalendarioNominaAbierto(String message, Throwable cause) {
	super(message, cause);

    }

    /**
     * @param cause
     */
    public NOExisteCalendarioNominaAbierto(Throwable cause) {
	super(cause);

    }

    @Override
    public String toString() {

	return mensaje;
    }

}
