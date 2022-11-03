package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Product> listAllProducts(){
        return productRepository.findAll();
    }
    public Product updateProduct(Product product){
        return productRepository.save(product);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


    public List<Product> listProductByCityId(Long city_id){
        return this.productRepository.findByCityId(city_id);
    }
}
