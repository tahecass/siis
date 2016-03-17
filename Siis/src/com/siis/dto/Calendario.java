package com.siis.dto;

import java.util.Date;

public class Calendario {
	private Long sec;
	private Date fecha_inicio;
	private Date fecha_fin;
	private String color_borde;
	private String color_content;
	private String titulo;

	public Long getSec() {
		return sec;
	}

	public void setSec(Long sec) {
		this.sec = sec;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getColor_borde() {
		return color_borde;
	}

	public void setColor_borde(String color_borde) {
		this.color_borde = color_borde;
	}

	public String getColor_content() {
		return color_content;
	}

	public void setColor_content(String color_content) {
		this.color_content = color_content;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
