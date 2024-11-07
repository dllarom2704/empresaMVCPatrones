package com.aprendec.service;

import com.aprendec.model.EmployeeDTO;

public class PayslipServiceImpl implements PayslipService {
	// calcular sueldo
	private static final int SUELDO_BASE[] = { 50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000,
			230000 };

	@Override
	public Integer calcSalary(EmployeeDTO employeeDTO) {
		return SUELDO_BASE[employeeDTO.getCategoria()] + 5000 * employeeDTO.getAnyos();
	}
}
