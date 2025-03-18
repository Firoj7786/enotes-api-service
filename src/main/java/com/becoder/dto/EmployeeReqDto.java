package com.becoder.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class EmployeeReqDto {
	  
	public int id;
	
	public String name;
	
	public int salary;
	
	public boolean isActive;
	
	public boolean isDeleted;
	
	public Date joiningDate;
	
//	public String createdAt;
//	
//	public String updatedAt;
//	
//	public String createdOn;
//	
//	public String updatedOn;
	
}
