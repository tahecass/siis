package com.siis.dto;
 

public class Indicador extends Bean {

	private Integer secuenciaIndicador;
	private Usuario usuario;
	private String nombre;
	private Long objetivoEsperado;
	private Long objetivoReal;
	private Long desviacion;
	private Unidad unidad;
	private String documentacion;
	private Object clase;

	public Indicador() {

	}

	public Indicador(Integer secuenciaIndicador, Usuario usuario, String nombre, Long objetivoEsperado,
			Long objetivoReal, Long desviacion, Unidad unidad, String documentacion) {
		super();
		this.secuenciaIndicador = secuenciaIndicador;
		this.usuario = usuario;
		this.nombre = nombre;
		this.objetivoEsperado = objetivoEsperado;
		this.objetivoReal = objetivoReal;
		this.desviacion = desviacion;
		this.unidad = unidad;
		this.documentacion = documentacion;
	}

	public Integer getSecuenciaIndicador() {
		return secuenciaIndicador;
	}

	public void setSecuenciaIndicador(Integer secuenciaIndicador) {
		this.secuenciaIndicador = secuenciaIndicador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getObjetivoEsperado() {
		return objetivoEsperado;
	}

	public Object getClase() {
		return this.getClase();
	}

	public void setClase(Object clase) {
		this.clase = clase;
	}

	public void setObjetivoEsperado(Long objetivoEsperado) {
		this.objetivoEsperado = objetivoEsperado;
	}

	public Long getObjetivoReal() {
		return objetivoReal;
	}

	public void setObjetivoReal(Long objetivoReal) {
		this.objetivoReal = objetivoReal;
	}

	public Long getDesviacion() {
		return desviacion;
	}

	public void setDesviacion(Long desviacion) {
		this.desviacion = desviacion;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public String getDocumentacion() {
		return documentacion;
	}

	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}

}
