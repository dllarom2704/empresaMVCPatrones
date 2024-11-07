package com.aprendec.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aprendec.conexion.ConnectionProvider;
import com.aprendec.conexion.MySQLConnectionProvider;
import com.aprendec.dao.EmployeeDAO;
import com.aprendec.dao.EmployeeDAOImpl;
import com.aprendec.model.EmployeeDTO;
import com.aprendec.model.EmployeeImpl;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet(description = "administra peticiones para la tabla empleados", urlPatterns = { "/empleados" })
public class EmpleadoControllerImpl extends HttpServlet implements EmpleadoController {
	private static final long serialVersionUID = 1L;
	private EmployeeDAO employeeDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmpleadoControllerImpl() {
		super();
		ConnectionProvider connectionProvider = new MySQLConnectionProvider();
		employeeDAO = new EmployeeDAOImpl(connectionProvider);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String opcion = request.getParameter("opcion");

		if (opcion.equals("listar")) {
			try {
				List<EmployeeDTO> lista = new ArrayList<>();
				lista = employeeDAO.getEmployees();
				for (EmployeeDTO empleadoDTO : lista) {
					System.out.println(empleadoDTO);
				}

				request.setAttribute("lista", lista);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (opcion.equals("buscarDni")) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/buscarDni.jsp");
			requestDispatcher.forward(request, response);
		} else if (opcion.equals("salario")) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/salario.jsp");
			requestDispatcher.forward(request, response);
		} else if (opcion.equals("buscarDato")) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/buscarDato.jsp");
			requestDispatcher.forward(request, response);
		} else if (opcion.equals("editar")) {
			String dni = request.getParameter("dni");

			try {
				EmployeeDTO employeeDTO = new EmployeeDTO();
				employeeDTO = employeeDAO.getEmployee(dni);

				request.setAttribute("empleado", employeeDTO);

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (opcion.equals("volver")) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
			requestDispatcher.forward(request, response);
		}
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opcion = request.getParameter("opcion");

		if (opcion.equals("buscarDni")) {
			String dni = request.getParameter("dni");

			try {
				String salary = employeeDAO.getSalary(dni);
				request.setAttribute("salario", salary);

				RequestDispatcher requestDispacher = request.getRequestDispatcher("/views/salario.jsp");
				requestDispacher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (opcion.equals("buscarDato")) {
			String datum = request.getParameter("datum");
			try {
				List<EmployeeDTO> employees = employeeDAO.getEmployeeData(datum);
				request.setAttribute("listaEmpleados", employees);
				RequestDispatcher requestDispacher = request
						.getRequestDispatcher("/views/listarEmpleadosBusquedaDatos.jsp");
				requestDispacher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (opcion.equals("editar")) {
			EmployeeDTO employeeDTO = new EmployeeDTO();

			employeeDTO.setNombre(request.getParameter("nombre"));
			employeeDTO.setDni(request.getParameter("dni"));
			employeeDTO.setSexo(request.getParameter("sexo").charAt(0));
			employeeDTO.setCategoria(Integer.parseInt(request.getParameter("categoria")));
			employeeDTO.setAnyos(Integer.parseInt(request.getParameter("anyos")));

			String searchedEmployeeDni = request.getParameter("dniEmpleadoBuscado");

			try {
				employeeDAO.edit(employeeDTO, searchedEmployeeDni);
				System.out.println("Registro editado satisfactoriamente...");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/buscarDato.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
