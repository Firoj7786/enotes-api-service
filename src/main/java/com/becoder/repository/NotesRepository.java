package com.becoder.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.becoder.model.Category;
import com.becoder.model.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	Page<Notes> findByCreatedBy(int userId, Pageable pageable);

	List<Notes> findByCreatedByAndIsDeletedTrue(int id);

	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);
	
	//List<Notes> getAllNotes();
	
}
