package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.*;
import com.integrator.group2backend.repository.ProductRepository;
import com.integrator.group2backend.utils.MapperService;
import com.integrator.group2backend.utils.UpdateProductCompare;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private MapperService mapperService;

    private ProductService productService;
    private CategoryService categoryService;
    private CityService cityService;
    private FeatureService featureService;
    private PolicyItemService policyItemService;
    private ImageService imageService;
    private UpdateProductCompare updateProductCompare;


    @Before
    public void setUp() {
        this.productService = new ProductService(this.productRepository, this.mapperService, this.updateProductCompare, categoryService, featureService, policyItemService, imageService, cityService);
    }


    @Test
    public void testSearchProductById() {
        Mockito.when(this.productRepository.findById(eq(1L))).thenReturn((Optional.empty()));
        this.productService.searchProductById(1L);
        Mockito.verify(this.productRepository, times(1)).findById(eq(1L));
    }

    @Test
    public void testAddProduct() {
        Category category = new Category();
        category.setId(1L);

        City city = new City();
        city.setId(1L);

        PolicyItem policyItem = new PolicyItem();
        policyItem.setId(1L);
        Set<PolicyItem> policyItemsList = new HashSet<>();
        policyItemsList.add(policyItem);


        Product requestProduct = new Product();
        requestProduct.setTitle("Departamento");
        requestProduct.setCategory(category);
        requestProduct.setPolicyItems(policyItemsList);
        requestProduct.setCity(city);

        /*ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.when(this.productRepository.save(productArgumentCaptor.capture())).thenReturn(null);

        this.productService.addProduct(requestProduct);

        Product capturedProduct = productArgumentCaptor.getValue();

        Assert.assertNotNull(capturedProduct.getCity());
        Assert.assertNotNull(capturedProduct.getPolicyItems());
        Assert.assertEquals("Departamento", capturedProduct.getTitle());
        Assert.assertEquals(1L, capturedProduct.getCategory().getId(), 1);*/
    }

    @Test
    public void testListAllProductsWithNoEmptyList() {
        List<Product> list = Collections.singletonList(new Product());
        Mockito.when(this.productRepository.findAll()).thenReturn(list);
        Mockito.when(this.mapperService.mapList(eq(list), eq(ProductViewDTO.class))).thenReturn(Collections.singletonList(new ProductViewDTO()));

        this.productService.listAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.mapperService, times(1)).mapList(eq(list), eq(ProductViewDTO.class));
    }

    @Test
    public void testListAllProductsWithEmptyList() {
        Mockito.when(this.productRepository.findAll()).thenReturn(Collections.emptyList());

        this.productService.listAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.mapperService, times(0)).mapList(any(), any());
    }

    @Test
    public void testListRandomAllProductsWithNoEmptyList() {
        List<Product> productList = Collections.singletonList(new Product());

        Mockito.when(this.productRepository.findAll()).thenReturn(productList);
        Mockito.when(this.mapperService.mapList(eq(productList), eq(ProductViewDTO.class))).thenReturn(Collections.singletonList(new ProductViewDTO()));

        this.productService.listRandomAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.mapperService, times(1)).mapList(eq(productList), eq(ProductViewDTO.class));
    }

    @Test
    public void testListRandomAllProductsWithEmptyList() {
        Mockito.when(this.productRepository.findAll()).thenReturn(Collections.emptyList());

        this.productService.listRandomAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.mapperService, times(0)).mapList(any(), any());
    }

    @Test
    public void updateProduct() {
        Product p = new Product();
        Mockito.when(this.productRepository.save(eq(p))).thenReturn(null);

        this.productService.updateProduct(p);

        Mockito.verify(this.productRepository, times(1)).save(eq(p));
    }

    @Test
    public void testDeleteProduct() {
        Mockito.doNothing().when(this.productRepository).deleteById(eq(1L));
        Mockito.when(this.productRepository.findById(1L)).thenReturn(Optional.of(new Product()));

        this.productService.deleteProduct(1L);

        Mockito.verify(this.productRepository, times(1)).deleteById(eq(1L));
    }

    @Test
    public void testListProductByCityIdWhenNoEmptyList() {
        List<Product> productList = Collections.singletonList(new Product());

        Mockito.when(this.productRepository.findByCityId(1L)).thenReturn(productList);
        Mockito.when(this.mapperService.mapList(eq(productList), eq(ProductViewDTO.class))).thenReturn(null);

        this.productService.listProductByCityId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCityId(eq(1L));
        Mockito.verify(this.mapperService, times(1)).mapList(eq(productList), eq(ProductViewDTO.class));
    }

    @Test
    public void testListProductByCityIdWithEmptyList() {
        Mockito.when(this.productRepository.findByCityId(1L)).thenReturn(Collections.emptyList());

        this.productService.listProductByCityId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCityId(eq(1L));
        Mockito.verify(this.mapperService, times(1)).mapList(eq(Collections.emptyList()), eq(ProductViewDTO.class));
    }

    @Test
    public void testListProductByCategoryIdWhenNoEmptyList() {
        List<Product> productList = Collections.singletonList(new Product());

        Mockito.when(this.productRepository.findByCategoryId(1L)).thenReturn(productList);
        Mockito.when(this.mapperService.mapList(eq(productList), eq(ProductViewDTO.class))).thenReturn(null);

        this.productService.listProductByCategoryId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCategoryId(eq(1L));
        Mockito.verify(this.mapperService, times(1)).mapList(eq(productList), eq(ProductViewDTO.class));
    }

    @Test
    public void testListProductByCategoryIdWithEmptyList() {
        Mockito.when(this.productRepository.findByCategoryId(1L)).thenReturn(Collections.emptyList());

        this.productService.listProductByCategoryId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCategoryId(eq(1L));
        Mockito.verify(this.mapperService, times(1)).mapList(eq(Collections.emptyList()), eq(ProductViewDTO.class));
    }


    @Test
    public void testSearchProductsByCityIdCheckInDateCheckOutDateWithEmptyList() {
        Date datefrom = new Date();
        Date dateTo = new Date();
        Mockito.when(this.productRepository.searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo))).thenReturn(Collections.emptyList());

        this.productService.searchProductsByCityCheckInDateCheckOutDate(1L, datefrom, dateTo);

        Mockito.verify(this.productRepository, times(1)).searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo));
        Mockito.verify(this.mapperService, times(1)).mapList(eq(Collections.emptyList()), eq(ProductViewDTO.class));
    }

    @Test
    public void testSearchProductsByCityIdCheckInDateCheckOutDateWhenNoEmptyList() {
        Date datefrom = new Date();
        Date dateTo = new Date();
        List<Product> productList = Collections.singletonList(new Product());

        Mockito.when(this.productRepository.searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo))).thenReturn(productList);

        this.productService.searchProductsByCityCheckInDateCheckOutDate(1L, datefrom, dateTo);

        Mockito.verify(this.productRepository, times(1)).searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo));
        Mockito.verify(this.mapperService, times(1)).mapList(any(), any());
    }


}