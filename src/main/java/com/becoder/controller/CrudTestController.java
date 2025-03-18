package com.becoder.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.EmployeeListDto;
import com.becoder.dto.EmployeeReqDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.model.Employee;
import com.becoder.service.EmployeeService;
import com.becoder.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/employee")
@Slf4j
public class CrudTestController {
	
	@Autowired
	  private EmployeeService empService;

	
	
	   @PostMapping("/save")
	   ResponseEntity<?>  createEmploye(@RequestBody Employee emp) {
		Boolean  employee =  empService.createEmployee(emp);
		if (employee) {
			return CommonUtils.createBuildResponseMessage("Created Success", HttpStatus.OK); 
		}
		return CommonUtils.createErrorResponseMessage("Failed to create Employee", HttpStatus.BAD_REQUEST); 
	   }
	   
	   
	 //update the data 
	   @PostMapping("/update")
	   ResponseEntity<?>  updateEmploye(@RequestBody EmployeeReqDto empDto) throws ResourceNotFoundException {
		  Boolean  employee =  empService.updateEmployee(empDto);
		if (!ObjectUtils.isEmpty(employee)) {
			return CommonUtils.createBuildResponseMessage("Updated Success", HttpStatus.OK); 
		}
		return CommonUtils.createErrorResponseMessage("Failed to update Employee", HttpStatus.BAD_REQUEST); 
	   }
	   
	   
	 //fetch data 
	   @GetMapping("/get")
	   ResponseEntity<?>  getEmployes() {
		   List<EmployeeListDto>  employee =  empService.getEmployes();
		if (!CollectionUtils.isEmpty(employee)) {
			return CommonUtils.createBuildResponse(employee, HttpStatus.OK); 
		}
		return CommonUtils.createErrorResponseMessage("Failed to create Employee", HttpStatus.BAD_REQUEST); 
	   }
	   
	   
	//delete the data 
	   @GetMapping("/delete/{id}")
	   ResponseEntity<?>  deleteEmploye(@PathVariable Integer id) throws ResourceNotFoundException {
		   Boolean  employee =  empService.deleteEmploye(id);
		if (!ObjectUtils.isEmpty(employee)) {
			return CommonUtils.createBuildResponseMessage("Delete Success", HttpStatus.OK); 
		}
		return CommonUtils.createErrorResponseMessage("Failed delete", HttpStatus.BAD_REQUEST); 
	   }
	
	
	 
  
}
