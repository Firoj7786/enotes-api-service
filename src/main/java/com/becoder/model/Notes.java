package com.becoder.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Notes extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String title;
    
    private String description;
    
    @ManyToOne
    private Category category;
	@ManyToOne
	private FileDetails fileDetails;
	
	private boolean isDeleted;
	
	private LocalDateTime deletedOn;
}
