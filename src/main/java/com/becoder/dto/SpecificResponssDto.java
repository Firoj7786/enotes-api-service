package com.becoder.dto;

import java.util.Date;

import com.becoder.model.Category;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecificResponssDto {
//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private int id;
	    
	    private String title;
	    
	    private String description;
	    
	    private Category category;
	    
	    @AllArgsConstructor
	    @NoArgsConstructor
	    @Setter
	    @Getter
		   public static class Category {  
			   
			    private String name;
			    
			    private String description;
			    
				private boolean isActive;
		   }
}
