package com.integrator.group2backend.service;


import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.entities.Image;
import com.integrator.group2backend.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @Before
    public void setUp() {
        this.categoryService = new CategoryService(this.categoryRepository);
    }

    @Test
    public void testUpdateCategoryWithImagesInDBButNoImagesInRequest() {
        Image categoryImage = new Image(); //creo dos imagenes y seteo id
        categoryImage.setId(10L);

        Image illustrationImage = new Image();
        illustrationImage.setId(11L);

        Category oldCategory = new Category();
        oldCategory.setId(1L);
        oldCategory.setCategoryImage(categoryImage);
        oldCategory.setCategoryIllustration(illustrationImage); //creo categoria y seteo imagenes e id

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class); //creo el captor que va a capturar el Category con el cual es llamado el metodo save

        Mockito.when(this.categoryRepository.save(categoryArgumentCaptor.capture())).thenReturn(null); //acá defino que tiene q devolver el save del repositorio cuando sea llamado

        this.categoryService.updateCategory(new Category(), oldCategory);

        Category capturedCategory = categoryArgumentCaptor.getValue();

        Assert.assertNotNull(capturedCategory.getCategoryImage());
        Assert.assertNotNull(capturedCategory.getCategoryIllustration());
        Assert.assertEquals(10L, capturedCategory.getCategoryImage().getId(), 1);
        Assert.assertEquals(11L, capturedCategory.getCategoryIllustration().getId(), 1);
    }
}