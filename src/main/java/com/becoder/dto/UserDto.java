package com.becoder.dto;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	 
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	 
	private String mobNo;
	
	private List<RoleDto> roles;
	
	
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@ToString
	@Builder
	public static class RoleDto {
		private Integer id;
		
		private String name;
	}

}
