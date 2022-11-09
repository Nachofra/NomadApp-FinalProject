package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.*;
import com.integrator.group2backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    /*public ProductViewDTO addProduct(ProductViewDTO product){
        Product productEntity = new Product();
        productEntity.setTitle(product.getTitle());
        productEntity.setDescription(product.getDescription());
        productEntity.setRooms(product.getRooms());
        productEntity.setBeds(product.getBeds());
        productEntity.setBathrooms(product.getBathrooms());
        productEntity.setGuests(product.getGuests());
        productEntity.setDailyPrice(product.getDailyPrice());
        productEntity.setLatitude(product.getLatitude());
        productEntity.setLongitude(product.getLongitude());

        City city = new City();
        product.setCountryName(city.getName());

        Country country = new Country();
        product.setCountryName(country.getName());

        Feature feature = new Feature();
        product.setFeatures(feature.getName());

        Set<Policy> policies;
        product.getPolicies(policies.setPo);

        List<String> images;

        Product savedProduct = productRepository.save(productEntity);
        ProductViewDTO returnedProduct = new ProductViewDTO();
        return returnedProduct;
    }*/
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

    public List<Product> listProductByCategoryId(Long category_id){
        return this.productRepository.findByCategoryId(category_id);
    }

}
