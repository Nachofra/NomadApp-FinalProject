package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public Optional<Category> searchCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId);
    }
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }
    public List<Category> listAllCategories(){
        return categoryRepository.findAll();
    }
    public Category updateCategory(Category category, Long id){
        Optional<Category> oldCategory = this.categoryRepository.findById(id);
        if(category.getCategoryIllustration().getURL() == null){
            category.setCategoryIllustration(oldCategory.get().getCategoryIllustration());
        }
        if(category.getCategoryImage().getURL() == null){
            category.setCategoryImage(oldCategory.get().getCategoryImage());
        }
        return categoryRepository.save(category);
        //agrego validaciones de imagenes para que queden presistentes en db cuando se acualiza alguna categoría
    }
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
