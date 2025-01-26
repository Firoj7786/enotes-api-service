package com.becoder.model;

import java.security.Identity;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	 
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	 
	private String mobNo;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Role> roles;

}
