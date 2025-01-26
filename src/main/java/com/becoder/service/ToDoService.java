package com.becoder.service;

import java.util.List;

import com.becoder.dto.ToDoDto;
import com.becoder.exception.ResourceNotFoundException;

public interface ToDoService {

	public Boolean saveTodo(ToDoDto toDo) throws ResourceNotFoundException;

	public ToDoDto getTodoById(Integer id) throws ResourceNotFoundException;

	public List<ToDoDto> getAllToDo();

}
