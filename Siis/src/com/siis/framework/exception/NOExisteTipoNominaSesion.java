/**
 * 
 */
package com.casewaresa.framework.exception;

/**
 * 
 * 
 * <custom-attributes MSG_NO_EXISTE_TIPO_NOMINA_SELECCIONADO ="No Existe un Tipo N�mina Seleccionado en Sesi�n." />
 * 
 * @author JMORA
 * 
 */
public class NOExisteTipoNominaSesion extends Exception {

    /**
     * Serial de la clase
     */
    private static final long serialVersionUID = 856332790059830533L;
    private final static String mensaje = "No existe un Tipo Nómina Seleccionado en Sesión";;

    /**
     * 
     */
    public NOExisteTipoNominaSesion() {

    }

    /**
     * @param message
     */
    public NOExisteTipoNominaSesion(String message) {
	super(mensaje);

    }

    /**
     * @param message
     * @param cause
     */
    public NOExisteTipoNominaSesion(String message, Throwable cause) {
	super(message, cause);

    }

    /**
     * @param cause
     */
    public NOExisteTipoNominaSesion(Throwable cause) {
	super(cause);

    }

    @Override
    public String toString() {

	return mensaje;
    }

}
