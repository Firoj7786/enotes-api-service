package com.becoder.service.impl;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.ToDoDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.model.ToDo;
import com.becoder.repository.ToDoRepository;
import com.becoder.service.ToDoService;
@Service
public class ToDoServiceImpl implements ToDoService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private ToDoRepository toDoRepository;
	
    public static final Map<Integer, String> STATUS_MAP = Map.of(
	                    1, "Not Started",
	                    2, "In Progress",
	                    3, "Completed"
	                );
	
	     public Boolean saveTodo(ToDoDto todoDto) throws ResourceNotFoundException
         {
        	 //Save toDo object here 
	    	 if(!STATUS_MAP.containsKey(todoDto.getStatus().getId())) {
	    		 throw new ResourceNotFoundException("Invalid Notes id");
	    	 }
	    	 // Map DTO to Entity and set status
	         ToDo todo = mapper.map(todoDto, ToDo.class);
	         todo.setStatusId(todoDto.getStatus().getId());
	         toDoRepository.save(todo);
	         if(!ObjectUtils.isEmpty(todo)) {
	        	 return true;
               }
	         return false;   
         }

		@Override
		public ToDoDto getTodoById(Integer id) throws ResourceNotFoundException {
			ToDo todo = toDoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Todo Not Found ! id invalid"));
	                ToDoDto todoDto = mapper.map(todo , ToDoDto.class);
	             // Set status details
	                todoDto.setStatus(buildStatusDto(todo.getStatusId()));
	                return todoDto;
		}
		 // Helper method to build StatusDto
	    private ToDoDto.StatusDto buildStatusDto(Integer statusId) {
	        String statusName = STATUS_MAP.get(statusId);
	        if (statusName == null) {
	            throw new IllegalArgumentException("Invalid status ID in database: " + statusId);
	        }

	        return ToDoDto.StatusDto.builder()
	            .id(statusId)
	            .name(statusName)
	            .build();
	    }
		@Override
		public List<ToDoDto> getAllToDo() {
            Integer userId = 1;
          List <ToDo> todo = toDoRepository.findByCreatedBy(userId);
          return  todo.stream().map(td -> mapper.map(td, ToDoDto.class)).toList();
		}
}
