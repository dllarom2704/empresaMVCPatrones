package com.aprendec.service;

import java.sql.SQLException;
import java.util.List;

import com.aprendec.model.EmployeeDTO;


public interface EmployeeService {
	List<EmployeeDTO> getEmployees() throws SQLException;

	String getSalary(String dni) throws SQLException;

	List<EmployeeDTO> getEmployeeData(String datum) throws SQLException;

	EmployeeDTO getEmployee(String dni) throws SQLException;

	boolean edit(EmployeeDTO employee, String searchedEmployeeDni) throws SQLException;
}
