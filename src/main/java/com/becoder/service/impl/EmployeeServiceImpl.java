package com.becoder.service.impl;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.EmployeeListDto;
import com.becoder.dto.EmployeeReqDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.model.Employee;
import com.becoder.repository.EmployeeRepository;
import com.becoder.service.EmployeeService;

import io.jsonwebtoken.lang.Collections;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	ModelMapper modelmapper;

	@Override
	public Boolean createEmployee(Employee emp) {
	Employee employee = empRepo.save(emp);
		if(!ObjectUtils.isEmpty(employee)) {return true;} return false;
	}

	@Override
	public Boolean updateEmployee(EmployeeReqDto empDto) throws ResourceNotFoundException {
	     Employee  employe = empRepo.findById(empDto.getId()).orElseThrow(()-> new ResourceNotFoundException("id not found"));
//	     employe.setName(empDto.getName());
//	     employe.setSalary(empDto.getSalary());
//	     employe.setJoiningDate(empDto.getJoiningDate());
//	     employe.setDeleted(empDto.isDeleted());
//	     employe.setActive(empDto.isActive());
	      modelmapper.map(empDto, employe);
	     Employee save = empRepo.save(employe);
	     if(!ObjectUtils.isEmpty(save)) {
	    	 return true;}
		return false;
	}

	@Override
	public List getEmployes() {
		 List<Employee> employee = empRepo.findAll();
		  // Correct stream mapping
          //List<EmployeeListDto> employeeDtos = employee.stream().map(e -> modelmapper.map(e, EmployeeListDto.class)).collect(Collectors.toList());
		 List<EmployeeListDto> employeeDtos = employee.stream().filter(e -> e.isActive() && !e.isDeleted)
				    .map(e -> modelmapper.map(e, EmployeeListDto.class)).distinct()
				    .collect(Collectors.toList());
	         
		 List<String> employeeNames = employee.stream()
				    .map(Employee::getName) // Extract names only
				    .collect(Collectors.toList());
//Set<EmployeeListDto> employeSet = employee.stream().map(e -> modelmapper.map(e, EmployeeListDto.class)).collect(Collectors.toSet());    
	          return employeeDtos;
	 }

	@Override
	public Boolean deleteEmploye(Integer id) throws ResourceNotFoundException {
	Employee emp = empRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found"));
	if(emp != null) {
		emp.setDeleted(true);
		
		empRepo.save(emp);
		//empRepo.deleteById(id);
		return true;
	}
      return false;
	}
}
