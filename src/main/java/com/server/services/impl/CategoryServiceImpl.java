package com.server.services.impl;

import com.server.entities.Category;
import com.server.exceptions.ResourceNotFoundException;
import com.server.payloads.CategoryDto;
import com.server.repositories.CategoryRepo;
import com.server.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.Integer;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.categoryDtoToCategory(categoryDto);
        category=this.categoryRepo.save(category);
        return this.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",id));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category=this.categoryRepo.save(category);
        return this.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",id));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoriesList=this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos=categoriesList.stream()
                .map(category -> this.categoryToCategoryDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("category","CategoryId",id));
        this.categoryRepo.delete(category);
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto){
        Category category=this.modelMapper.map(categoryDto,Category.class);
        return category;
    }
    public CategoryDto categoryToCategoryDto(Category category){
        CategoryDto categoryDto=this.modelMapper.map(category,CategoryDto.class);
        return categoryDto;
    }
}
