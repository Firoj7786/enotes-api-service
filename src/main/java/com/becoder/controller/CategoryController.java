package com.becoder.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.model.Category;
import com.becoder.service.CategoryService;
import com.becoder.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired    
    private CategoryService categoryService; 
    
    @PostMapping("/save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {

		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {
			return CommonUtils.createBuildResponseMessage("Saved Successfully", HttpStatus.CREATED);
		} else {
			return CommonUtils.createErrorResponseMessage("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> listCategory = categoryService.getAllCategory(); 
        if(CollectionUtils.isEmpty(listCategory)) {
        	return CommonUtils.createErrorResponseMessage("Data is not available", HttpStatus.NOT_FOUND);  // 200 OK
        } else {
            return CommonUtils.createBuildResponse(listCategory, HttpStatus.OK);  // 200 OK
        }
    }
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory() {
       
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return CommonUtils.createErrorResponseMessage("Data is not available", HttpStatus.NOT_FOUND);
		} else {
			return CommonUtils.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategortDetailsById(@PathVariable Integer id) {
		CategoryDto categoryDto;
		try {
			categoryDto = categoryService.getCategoryById(id);
			if (ObjectUtils.isEmpty(categoryDto)) {
				return CommonUtils.createErrorResponseMessage("Category not found with Id=" + id, HttpStatus.NOT_FOUND);
			}
			return CommonUtils.createBuildResponse(categoryDto, HttpStatus.OK);
		}
			catch (ResourceNotFoundException e) {
				return CommonUtils.createBuildResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return CommonUtils.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) throws ResourceNotFoundException {
		Boolean deleted = categoryService.deleteCategory(id);
		if (deleted) {
			return CommonUtils.createBuildResponseMessage("Category deleted success", HttpStatus.OK);
		}
		return CommonUtils.createErrorResponseMessage("Category Not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
