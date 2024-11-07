package com.aprendec.service;

import java.sql.SQLException;
import java.util.List;

import com.aprendec.dao.EmployeeDAO;
import com.aprendec.model.EmployeeDTO;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDAO employeeDAO;

	public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public List<EmployeeDTO> getEmployees() throws SQLException {
		return employeeDAO.getEmployees();
	}

	@Override
	public String getSalary(String dni) throws SQLException {
		return employeeDAO.getSalary(dni);
	}

	@Override
	public List<EmployeeDTO> getEmployeeData(String datum) throws SQLException {
		return employeeDAO.getEmployeeData(datum);
	}

	@Override
	public EmployeeDTO getEmployee(String dni) throws SQLException {
		return employeeDAO.getEmployee(dni);
	}

	@Override
	public boolean edit(EmployeeDTO employeeDTO, String searchedEmployeeDni) throws SQLException {
		return employeeDAO.edit(employeeDTO, searchedEmployeeDni);
	}
}
