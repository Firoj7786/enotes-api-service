package com.becoder.dto;
import java.util.Date;

import jakarta.persistence.Column;
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
public class ToDoDto {
	
	private Integer id;

	private String title;

	private StatusDto status;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;
	
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class StatusDto {
		private Integer id;
		private String name;
	}
	
}
