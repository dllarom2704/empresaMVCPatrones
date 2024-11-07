package com.aprendec.model;

public class EmployeeDTO {
	private String nombre, dni;
	private char sexo;
	private Integer categoria;
	public Integer anyos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public Integer getAnyos() {
		return anyos;
	}

	public void setAnyos(Integer anyos) {
		this.anyos = anyos;
	}

	/**
	 * Getter de la propiedad categoría
	 * 
	 * @return El valor de categoría
	 */
	public Integer getCategoria() {
		return categoria;
	}

	/**
	 * Setter de categoría
	 * 
	 * @param categoria La categoría de empleado
	 */
	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
}
