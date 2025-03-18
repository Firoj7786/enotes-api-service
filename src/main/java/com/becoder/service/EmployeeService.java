package com.becoder.service;

import java.util.List;
import java.util.Set;

import com.becoder.dto.EmployeeListDto;
import com.becoder.dto.EmployeeReqDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.model.Employee;

public interface EmployeeService {

	Boolean createEmployee(Employee emp);

	Boolean updateEmployee(EmployeeReqDto empDto) throws ResourceNotFoundException;

	List<EmployeeListDto> getEmployes();

	Boolean deleteEmploye(Integer id) throws ResourceNotFoundException;
    
}
