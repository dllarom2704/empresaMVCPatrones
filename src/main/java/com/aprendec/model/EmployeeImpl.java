package com.aprendec.model;

import com.aprendec.model.exceptions.DatosNoCorrectosException;

public class EmployeeImpl implements Employee {
	private String nombre, dni;
	private char sexo;
	private Integer categoria;
	public Integer anyos;

	public EmployeeImpl() {
	}

	/**
	 * Constructor del objeto Empleado, en este caso se le pasan como parámetros el
	 * nombre y el sexo. Los obtenemos de Persona
	 * 
	 * @param nombre Nombre de la persona
	 * @param sexo   Sexo de la persona
	 */
	public EmployeeImpl(String nombre, char sexo) {
		this.nombre = nombre;
		this.sexo = sexo;

	}

	/**
	 * Constructor del objeto Empleado, en este case se le pasan como parámetros el
	 * nombre, el dni y es sexo
	 * 
	 * @param nombre El nombre de la persona
	 * @param dni    El dni de la persona
	 * @param sexo   El sexo de la persona
	 */
	public EmployeeImpl(String nombre, String dni, char sexo) {
		this.nombre = nombre;
		this.dni = dni;
		this.sexo = sexo;
		categoria = 1;
		anyos = 0;
	}

	/**
	 * Constructor del objeto Empleado
	 * 
	 * @param nombre    Nombre de la persona
	 * @param dni       Dni de la persona
	 * @param sexo      Sexo de la persona
	 * @param categoria Categoría de empleado
	 * @param anyos     Años trabajados
	 * @throws DatosNoCorrectosException
	 */
	public EmployeeImpl(String nombre, String dni, char sexo, Integer categoria, Integer anyos)
			throws DatosNoCorrectosException {
		if (!(categoria >= 1 && categoria <= 10 || !(anyos >= 0))) {
			throw new DatosNoCorrectosException("Datos no correctos");
		}

		this.categoria = categoria;
		this.anyos = anyos;
	}

	/**
	 * Método que incrementa en uno los años trabajados
	 */
	@Override
	public void incrAnyo() {
		anyos++;
	}

	/**
	 * Método que imprime todos los datos de un objeto empleado
	 */
	@Override
	public void imprime() {
		System.out.println("Nombre: " + nombre + "\nDni: " + dni + "\nSexo: " + sexo + "\nCategoría: " + categoria
				+ "\nAños trabajados: " + anyos);
	}

}
