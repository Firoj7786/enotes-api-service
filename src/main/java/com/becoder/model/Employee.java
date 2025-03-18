package com.becoder.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Employee extends BaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	public String name;
	
	public String email;
	
	public String password;
	
	public int salary;
	
	public boolean isActive;
	
	public boolean isDeleted;
	
	public Date joiningDate;
//	public String createdAt;
//	public String updatedAt;
//	public String createdOn;
//	public String updatedOn;

}
