package com.aprendec.service;

import com.aprendec.model.EmployeeDTO;

public interface PayslipService {
	Integer calcSalary(EmployeeDTO employeeDTO);
}
