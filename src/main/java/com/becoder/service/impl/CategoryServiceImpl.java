package com.becoder.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.exception.ExistDataException;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.model.Category;
import com.becoder.repository.CategoryRepository;
import com.becoder.service.CategoryService;
import com.becoder.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {
    
	@Autowired
	private Validation validation;
    @Autowired
    private CategoryRepository categoryRepo;  
    @Autowired
    private ModelMapper mapper;
    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

    //Validation check implementation
    	validation.categoryValidation(categoryDto);
    	
    //check existing category
    	boolean exist = categoryRepo.existsByName(categoryDto.getName());
    	if(exist)
    	{
    	  throw new ExistDataException("Entered Category already exists please provide differant name");	
    	}

		Category category = mapper.map(categoryDto, Category.class);

		if (ObjectUtils.isEmpty(category.getId())) {
			category.setDeleted(false);
//			category.setCreatedBy(1);
//			category.setCreatedOn(new Date());
		} else {
			updateCategory(category);
		}

		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {
		Optional<Category> findById = categoryRepo.findById(category.getId());
		if (findById.isPresent()) {
			Category existCategory = findById.get();
			//category.setCreatedBy(existCategory.getCreatedBy());
			//category.setCreatedOn(existCategory.getCreatedOn());
			category.setDeleted(existCategory.isDeleted());
			//category.setUpdatedBy(1);
			//category.setUpdatedOn(new Date());
		}
	}

    @Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findAll();

		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();

		return categoryDtoList;
	}
	@Override
	public List<CategoryResponse> getActiveCategory() {

		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class))
				.toList();
		return categoryList;
	}

	@Override
	 public CategoryDto getCategoryById(Integer id) throws Exception {
        // Correct usage of orElseThrow with a Supplier
        Category category = categoryRepo.findByIdAndIsDeletedFalse(id);

        if (!ObjectUtils.isEmpty(category)) {
            category.getName().toUpperCase(); // Make sure to assign it if you need it changed
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> findByCatgeory = categoryRepo.findById(id);

		if (findByCatgeory.isPresent()) {
			Category category = findByCatgeory.get();
			category.setDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}
}
