package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.entities.City;
import com.integrator.group2backend.entities.Policy;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;

    private ProductService productService;

    @Before
    public void setUp() {
        this.productService = new ProductService(this.productRepository, this.modelMapper);
    }


    @Test
    public void testSearchProductById() {
        Mockito.when(this.productRepository.findById(eq(1L))).thenReturn((null));
        this.productService.searchProductById(1L);
        Mockito.verify(this.productRepository, times(1)).findById(eq(1L));
    }

    @Test
    public void testAddProduct() {
        Category category = new Category();
        category.setId(1L);

        City city = new City();
        city.setId(1L);

        Policy policy = new Policy();
        policy.setId(1L);
        Set<Policy> policyList = new HashSet<>();
        policyList.add(policy);


        Product requestProduct = new Product();
        requestProduct.setTitle("Departamento");
        requestProduct.setCategory(category);
        requestProduct.setPolicies(policyList);
        requestProduct.setCity(city);

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.when(this.productRepository.save(productArgumentCaptor.capture())).thenReturn(null);

        this.productService.addProduct(requestProduct);

        Product capturedProduct = productArgumentCaptor.getValue();

        Assert.assertNotNull(capturedProduct.getCity());
        Assert.assertNotNull(capturedProduct.getPolicies());
        Assert.assertEquals("Departamento", capturedProduct.getTitle());
        Assert.assertEquals(1L, capturedProduct.getCategory().getId(), 1);
    }

    @Test
    public void testListAllProductsWithNoEmptyList() {
        Product p = new Product();
        Mockito.when(this.productRepository.findAll()).thenReturn(Collections.singletonList(p));
        Mockito.when(this.modelMapper.map(eq(p), eq(ProductViewDTO.class))).thenReturn(new ProductViewDTO());

        this.productService.listAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.modelMapper, times(1)).map(eq(p), eq(ProductViewDTO.class));
    }

    @Test
    public void testListAllProductsWithEmptyList() {
        Mockito.when(this.productRepository.findAll()).thenReturn(Collections.emptyList());

        this.productService.listAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void testListRandomAllProductsWithNoEmptyList() {
        Product p = new Product();
        Mockito.when(this.productRepository.findAll()).thenReturn(Collections.singletonList(p));
        Mockito.when(this.modelMapper.map(eq(p), eq(ProductViewDTO.class))).thenReturn(new ProductViewDTO());

        this.productService.listRandomAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.modelMapper, times(1)).map(eq(p), eq(ProductViewDTO.class));
    }

    @Test
    public void testListRandomAllProductsWithEmptyList() {
        Mockito.when(this.productRepository.findAll()).thenReturn(Collections.emptyList());

        this.productService.listRandomAllProducts();

        Mockito.verify(this.productRepository, times(1)).findAll();
        Mockito.verify(this.modelMapper, times(0)).map(any(), any());
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

        this.productService.deleteProduct(1L);

        Mockito.verify(this.productRepository, times(1)).deleteById(eq(1L));

    }

    @Test
    public void testListProductByCityIdWhenNoEmptyList() {
        Product p = new Product();
        Mockito.when(this.productRepository.findByCityId(1L)).thenReturn(Collections.singletonList(p));
        Mockito.when(this.modelMapper.map(eq(p), eq(ProductViewDTO.class))).thenReturn(new ProductViewDTO());

        this.productService.listProductByCityId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCityId(eq(1L));
        Mockito.verify(this.modelMapper, times(1)).map(eq(p), eq(ProductViewDTO.class));
    }

    @Test
    public void testListProductByCityIdWithEmptyList() {
        Mockito.when(this.productRepository.findByCityId(1L)).thenReturn(Collections.emptyList());

        this.productService.listProductByCityId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCityId(eq(1L));
        Mockito.verify(this.modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void testListProductByCategoryIdWhenNoEmptyList() {
        Product p = new Product();
        Mockito.when(this.productRepository.findByCategoryId(1L)).thenReturn(Collections.singletonList(p));
        Mockito.when(this.modelMapper.map(eq(p), eq(ProductViewDTO.class))).thenReturn(new ProductViewDTO());

        this.productService.listProductByCategoryId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCategoryId(eq(1L));
        Mockito.verify(this.modelMapper, times(1)).map(eq(p), eq(ProductViewDTO.class));
    }

    @Test
    public void testListProductByCategoryIdWithEmptyList() {
        Mockito.when(this.productRepository.findByCategoryId(1L)).thenReturn(Collections.emptyList());

        this.productService.listProductByCategoryId(1L);

        Mockito.verify(this.productRepository, times(1)).findByCategoryId(eq(1L));
        Mockito.verify(this.modelMapper, times(0)).map(any(), any());
    }


    @Test
    public void testSearchProductsByCityIdCheckInDateCheckOutDateWithEmptyList() {
        Date datefrom = new Date();
        Date dateTo = new Date();
        Mockito.when(this.productRepository.searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo))).thenReturn(Collections.emptyList());

        this.productService.searchProductsByCityCheckInDateCheckOutDate(1L, datefrom, dateTo);

        Mockito.verify(this.productRepository, times(1)).searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo));
        Mockito.verify(this.modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void testSearchProductsByCityIdCheckInDateCheckOutDateWhenNoEmptyList() {
        Date datefrom = new Date();
        Date dateTo = new Date();
        Product p = new Product();

        Mockito.when(this.productRepository.searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo))).thenReturn(Collections.singletonList(p));

        this.productService.searchProductsByCityCheckInDateCheckOutDate(1L, datefrom, dateTo);

        Mockito.verify(this.productRepository, times(1)).searchProductByCityCheckInDateCheckOutDate(eq(1L), eq(datefrom), eq(dateTo));
        Mockito.verify(this.modelMapper, times(1)).map(any(), any());
    }


}