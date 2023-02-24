package com.server.controller;

import com.server.payloads.ApiResponce;
import com.server.payloads.CategoryDto;
import com.server.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return new ResponseEntity<>(this.categoryService.getAllCategory(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
        return new ResponseEntity<>(this.categoryService.getCategoryById(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(this.categoryService.createCategory(categoryDto),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer id){
        return new ResponseEntity<>(this.categoryService.updateCategory(id,categoryDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponce> deleteCategoryById(@PathVariable Integer id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponce("Category deleted",true),HttpStatus.OK);
    }
}
