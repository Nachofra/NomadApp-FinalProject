package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.*;
import com.integrator.group2backend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public Optional<Product> searchProductById(Long productId){
        return productRepository.findById(productId);
    }
    public Product addProduct(Product product){
        return productRepository.save(product);
    }
    public List<ProductViewDTO> listAllProducts(){
        List<Product> searchedProducts = productRepository.findAll();
        List<ProductViewDTO> dtoSearchedProducts = mapList(searchedProducts, ProductViewDTO.class);
        return dtoSearchedProducts;
    }
    public List<ProductViewDTO> listRandomAllProducts(){
        List<Product> searchedProducts = productRepository.findAll();
        List<ProductViewDTO> dtoSearchedProducts = mapList(searchedProducts, ProductViewDTO.class);
        Collections.shuffle(dtoSearchedProducts);
        return dtoSearchedProducts;
    }
    public Product updateProduct(Product product){
        return productRepository.save(product);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public List<ProductViewDTO> listProductByCityId(Long city_id){
        List<Product> productFoundByCityId = productRepository.findByCityId(city_id);
        List<ProductViewDTO> dtoProductFoundByCityId = mapList(productFoundByCityId, ProductViewDTO.class);
        return dtoProductFoundByCityId;
    }
    public List<ProductViewDTO> listProductByCategoryId(Long category_id){
        List<Product> productFoundByCategoryId = productRepository.findByCategoryId(category_id);
        List<ProductViewDTO> dtoProductFoundByCategoryId = mapList(productFoundByCategoryId, ProductViewDTO.class);
        return dtoProductFoundByCategoryId;
    }

    @Autowired
    ModelMapper modelMapper;
    public ProductViewDTO convertToDto(Product product) {
        ProductViewDTO productViewDTO = modelMapper.map(product, ProductViewDTO.class);
        return productViewDTO;
    }
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }
}
