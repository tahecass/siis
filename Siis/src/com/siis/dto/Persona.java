package com.siis.dto;

import java.util.Date;
import java.util.List;

import com.siis.framework.dto.BeanAbstracto;
import com.siis.framework.dto.IBeanAbstracto;
import com.siis.framework.dto.impl.LlaveNatural;

public class Persona extends BeanAbstracto implements IBeanAbstracto {

	private Long sec;
	private TipoIdentificacion tipoIdentificacion;

	private String identificacion;
	private String nombres;
	private String apellidos;
	private Date fechaNacimiento;
	private String sexo;
	private String estadoCivil;
	private String email;
	private String direccion;
	private String telefono;
	private String celular;
	private Date fechaCreacion;
	private Date fechaActualizacion;

	public Long getSec() {
		return sec;
	}

	public void setSec(Long sec) {
		this.sec = sec;
	}

	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String getCodigo() {
		// TODO Auto-generated method stub
		return identificacion;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombres;
	}

	@Override
	public String getEstado() {
		// TODO Auto-generated method stub
		return estadoCivil;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return sec;
	}

	@Override
	public void setCodigo(String codigo) {
		// TODO Auto-generated method stub
		identificacion=codigo;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombres=nombre;

	}

	@Override
	public void setPrimaryKey(Long sec) {
	this.sec=sec;

	}

	@Override
	public List<LlaveNatural> getLlaveNatural() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
	    public String toString() {
		return "[" + identificacion + "] " + " "
			+ (nombres != null ? nombres : "") + " "
			+ (apellidos != null ? apellidos : "") + " "
			+ identificacion;
	    }

}
