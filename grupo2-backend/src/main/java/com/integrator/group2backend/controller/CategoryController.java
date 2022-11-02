package com.integrator.group2backend.controller;

import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    public static final Logger logger = Logger.getLogger(ProductController.class);
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> listAllCategories(){
        List<Category> searchedCategories = categoryService.listAllCategories();

        if(!(searchedCategories.isEmpty())){
            logger.info("Se listaron todos las categorias");
            return ResponseEntity.ok(searchedCategories);
        }else{
            logger.error("Error al listar todos las categorias");
            return ResponseEntity.ok(searchedCategories);
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        // * Here we need to forbid the requests with ids that already exists in the database *
        Category addedCategory = categoryService.addCategory(category);
        logger.info("Se agrego una categoria");
        return ResponseEntity.ok(addedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long categoryId, @RequestBody Category category){
        boolean categoryExists = categoryService.searchCategoryById(categoryId).isPresent();
        // If the provided category already exists it can be updated, otherwise it will throw a badRequest response
        if(categoryExists){
            category.setId(categoryId);
            Category updatedCategory = categoryService.updateCategory(category);
            logger.info("Se actualizo correctamente la categoria con id " + categoryId);
            return ResponseEntity.ok(updatedCategory);
        }else{
            logger.error("La categoria especificada no existe con id " + categoryId);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        boolean categoryExists = categoryService.searchCategoryById(categoryId).isPresent();
        // If the provided category already exists it can be deleted, otherwise it will send a response telling
        // the row with that id doesn't exist in the database
        if(categoryExists){
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("La categoría con id " + categoryId + " ha sido borrada");
        }else{
            return ResponseEntity.ok("La categoría con id " + categoryId + " no existe en la base de datos");
        }
    }
}
