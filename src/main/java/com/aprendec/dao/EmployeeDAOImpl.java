package com.aprendec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aprendec.conexion.ConnectionProvider;
import com.aprendec.model.EmployeeDTO;
import com.aprendec.model.EmployeeImpl;
import com.aprendec.service.PayslipService;
import com.aprendec.service.PayslipServiceImpl;

public class EmployeeDAOImpl implements EmployeeDAO {
	private ConnectionProvider connectionProvider = null;

	public EmployeeDAOImpl(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	@Override
	public List<EmployeeDTO> getEmployees() throws SQLException {
		List<EmployeeDTO> employees = new ArrayList<>();

		String sql = "SELECT * FROM empleados";

		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				EmployeeDTO employeeDTO = new EmployeeDTO();

				employeeDTO.setNombre(resultSet.getString(1));
				employeeDTO.setDni(resultSet.getString(2));
				employeeDTO.setSexo(resultSet.getString(3).charAt(0));
				employeeDTO.setCategoria(resultSet.getInt(4));
				employeeDTO.setAnyos(resultSet.getInt(5));

				employees.add(employeeDTO);
			}
		}

		return employees;
	}

	@Override
	public String getSalary(String dni) throws SQLException {
		String salary = null;

		String sql = "SELECT sueldo FROM nominas WHERE dni = ?";

		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, dni);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					System.out.println("Has next");
					salary = resultSet.getString(1);
				}
			}
		}

		return salary;
	}

	@Override
	public List<EmployeeDTO> getEmployeeData(String datum) throws SQLException {
		List<EmployeeDTO> employees = new ArrayList<>();

		String sql = "SELECT * FROM empleados WHERE nombre LIKE ? OR dni LIKE ? OR sexo = ? OR categoria = ? OR anyos = ?";

		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = null;
			ResultSet resultSet = null;

			try {
				statement = connection.prepareStatement(sql);

				statement.setString(1, "%" + datum + "%");
				statement.setString(2, "%" + datum + "%");
				statement.setString(3, String.valueOf(datum.charAt(0)));
				statement.setInt(4, Integer.parseInt(datum));
				statement.setInt(5, Integer.parseInt(datum));

				resultSet = statement.executeQuery();

				while (resultSet.next()) {
					EmployeeDTO employee = new EmployeeDTO();
					employee.setNombre(resultSet.getString("nombre"));
					employee.setDni(resultSet.getString("dni"));
					employee.setSexo(resultSet.getString("sexo").charAt(0));
					employee.setCategoria(resultSet.getInt("categoria"));
					employee.setAnyos(resultSet.getInt("anyos"));

					employees.add(employee);
				}
			} catch (NumberFormatException e) {
				sql = "SELECT * FROM empleados WHERE nombre LIKE ? OR dni LIKE ? OR sexo = ?";

				statement = connection.prepareStatement(sql);

				statement.setString(1, "%" + datum + "%");
				statement.setString(2, "%" + datum + "%");
				statement.setString(3, String.valueOf(datum.charAt(0)));

				resultSet = statement.executeQuery();

				while (resultSet.next()) {
					EmployeeDTO employee = new EmployeeDTO();
					employee.setNombre(resultSet.getString("nombre"));
					employee.setDni(resultSet.getString("dni"));
					employee.setSexo(resultSet.getString("sexo").charAt(0));
					employee.setCategoria(resultSet.getInt("categoria"));
					employee.setAnyos(resultSet.getInt("anyos"));

					employees.add(employee);
				}
			}
		}

		return employees;
	}

	@Override
	public EmployeeDTO getEmployee(String dni) throws SQLException {
		EmployeeDTO employee = new EmployeeDTO();

		String sql = "SELECT * FROM empleados WHERE dni = ?";

		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, dni);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					employee.setNombre(resultSet.getString(1));
					employee.setDni(resultSet.getString(2));
					employee.setSexo(resultSet.getString(3).charAt(0));
					employee.setCategoria(resultSet.getInt(4));
					employee.setAnyos(resultSet.getInt(5));
				}
			}

		}

		return employee;
	}

	@Override
	public boolean edit(EmployeeDTO employeeDTO, String searchedEmployeeDni) throws SQLException {
		String sql = null;

		Connection connection = connectionProvider.getConnection();
		PreparedStatement statement = null;

		boolean estadoOperacion = false;

		try {
			connection.setAutoCommit(false);

			sql = "DELETE FROM nominas WHERE dni = ?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, searchedEmployeeDni);

			statement.executeUpdate();

			sql = "UPDATE empleados SET nombre = ?, dni = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, employeeDTO.getNombre());

			String newDni = employeeDTO.getDni();
			statement.setString(2, newDni);

			statement.setObject(3, employeeDTO.getSexo(), java.sql.Types.CHAR);
			statement.setInt(4, employeeDTO.getCategoria());
			statement.setInt(5, employeeDTO.getAnyos());
			statement.setString(6, searchedEmployeeDni);

			estadoOperacion = statement.executeUpdate() > 0;

			sql = "INSERT INTO nominas (dni, sueldo) VALUES (?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, newDni);

			PayslipService payslipService = new PayslipServiceImpl();
			Integer sueldo = payslipService.calcSalary(employeeDTO);
			statement.setInt(2, sueldo);

			statement.executeUpdate();

			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}
}