package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }
    public List<Category> listAllCategories(){
        return categoryRepository.findAll();
    }
    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
