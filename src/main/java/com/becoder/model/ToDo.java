package com.becoder.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ToDo extends BaseModel {
	        @Id
	        @GeneratedValue(strategy = GenerationType.IDENTITY)
	    	private Integer id;

	    	private String title;

	    	@Column(name = "status")
	    	private Integer statusId;
}
