package com.becoder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.becoder.model.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

	List<ToDo> findByCreatedBy(Integer userId);

}
