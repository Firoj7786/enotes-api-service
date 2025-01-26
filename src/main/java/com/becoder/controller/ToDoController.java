package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.ToDoDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.service.ToDoService;
import com.becoder.util.CommonUtils;

@RestController
@RequestMapping("api/v1/todo")
public class ToDoController {
	  @Autowired
	  private ToDoService todoService;
	  
	  
	@PostMapping("/save")
	ResponseEntity<?> saveToDo(@RequestBody ToDoDto toDo) throws ResourceNotFoundException {
		Boolean saveTodo = todoService.saveTodo(toDo);
		if (saveTodo) {
			return CommonUtils.createBuildResponseMessage("Todo Saved Success", HttpStatus.CREATED);
		} else {
			return CommonUtils.createErrorResponseMessage("Todo not save", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/{id}")
	ResponseEntity<?> getToDO(@PathVariable Integer id) throws ResourceNotFoundException {
		ToDoDto todo = todoService.getTodoById(id);
		if (!ObjectUtils.isEmpty(todo)) {
			return CommonUtils.createBuildResponse(todo, HttpStatus.OK);
		} else {
			return CommonUtils.createErrorResponseMessage("Todo not Found", HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/")
	ResponseEntity<?> getAllToDO() throws ResourceNotFoundException {
		List <ToDoDto> todo = todoService.getAllToDo();
		if (!ObjectUtils.isEmpty(todo)) {
			return CommonUtils.createBuildResponse(todo, HttpStatus.OK);
		} else {
			return CommonUtils.createErrorResponseMessage("Todo not Found", HttpStatus.NOT_FOUND);
		}
	}
}
