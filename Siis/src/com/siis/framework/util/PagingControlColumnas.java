package com.casewaresa.framework.util;

/**
 * Clase para darle las propiedades o atributos a las columnas a la hora de hacer el ordenamiento de registros.
 * @author CASEWAREDES05
 *
 */
public class PagingControlColumnas {
    /**
     * Esta variable hace referencia al nombre de la columna en la base de datos por la cual se va a hacer el ordenamiento
     */
    private String nombreColumna;
    
    /**
     * Esta variable me indica si esta columna va a tener la propiedad de que se pueda ordenar por ella.
     */
    private Boolean ordenamiento;
    
    
    
    public PagingControlColumnas(String nombreColumna, Boolean ordenamiento) {
	super();
	this.nombreColumna = nombreColumna;
	this.ordenamiento = ordenamiento;
    }

    public void setNombreColumna(String nombreColumna) {
	this.nombreColumna = nombreColumna;
    }
    
    public String getNombreColumna() {
	return nombreColumna;
    }

    public void setOrdenamiento(Boolean ordenamiento) {
	this.ordenamiento = ordenamiento;
    }

    public Boolean getOrdenamiento() {
	return ordenamiento;
    }
    
   
}
