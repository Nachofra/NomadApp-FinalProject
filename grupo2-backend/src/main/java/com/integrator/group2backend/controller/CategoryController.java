package com.integrator.group2backend.controller;

import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> listAllCategories(){
        List<Category> searchedCategories = categoryService.listAllCategories();

        if(!(searchedCategories.isEmpty())){
            return ResponseEntity.ok(searchedCategories);
        }else{
            // * Change here to throw an error or a message saying "list empty" *
            return ResponseEntity.ok(searchedCategories);
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category savedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCategory(@RequestBody Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("El paciente con id " + categoryId + "ha sido borrado");
    }
}
