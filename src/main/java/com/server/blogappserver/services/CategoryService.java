package com.server.blogappserver.services;

import com.server.blogappserver.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(Integer id,CategoryDto categoryDto);

    public CategoryDto getCategoryById(Integer id);

    public List<CategoryDto> getAllCategory();

    public void deleteCategory(Integer id);

}
