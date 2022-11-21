package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.repository.ProductRepository;
import com.integrator.group2backend.utils.MapperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final MapperService mapperService;

    public ProductService(ProductRepository productRepository, MapperService mapperService) {
        this.productRepository = productRepository;
        this.mapperService = mapperService;
    }

    public Optional<Product> searchProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<ProductViewDTO> listAllProducts() {
        List<Product> searchedProducts = productRepository.findAll();
        List<ProductViewDTO> dtoSearchedProducts = this.mapperService.mapList(searchedProducts, ProductViewDTO.class);
        return dtoSearchedProducts;
    }

    public List<ProductViewDTO> listRandomAllProducts() {
        List<Product> searchedProducts = productRepository.findAll();
        List<ProductViewDTO> dtoSearchedProducts = this.mapperService.mapList(searchedProducts, ProductViewDTO.class);
        Collections.shuffle(dtoSearchedProducts);
        return dtoSearchedProducts;
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductViewDTO> listProductByCityId(Long city_id) {
        List<Product> productFoundByCityId = productRepository.findByCityId(city_id);
        List<ProductViewDTO> dtoProductFoundByCityId = this.mapperService.mapList(productFoundByCityId, ProductViewDTO.class);
        return dtoProductFoundByCityId;
    }

    public List<ProductViewDTO> listProductByCategoryId(Long category_id) {
        List<Product> productFoundByCategoryId = productRepository.findByCategoryId(category_id);
        List<ProductViewDTO> dtoProductFoundByCategoryId = this.mapperService.mapList(productFoundByCategoryId, ProductViewDTO.class);
        return dtoProductFoundByCategoryId;
    }

    public List<ProductViewDTO> listProductByCityIdAndCategoryId(Long city_id, Long category_id) {
        List<Product> productFoundByCityIdAndCategoryId = productRepository.findByCityIdAndCategoryId(city_id, category_id);
        List<ProductViewDTO> dtoProductFoundByCityIdAndCategoryId = this.mapperService.mapList(productFoundByCityIdAndCategoryId, ProductViewDTO.class);
        return dtoProductFoundByCityIdAndCategoryId;
    }

    public List<ProductViewDTO> listProductByCityIdAndCategoryIdAndGuests(Long city_id, Long category_id, Integer guests) {
        List<Product> productFoundByCityIdAndCategoryIdAndGuests = productRepository.findByCityIdAndCategoryIdAndGuests(city_id, category_id, guests);
        List<ProductViewDTO> dtoProductFounded = this.mapperService.mapList(productFoundByCityIdAndCategoryIdAndGuests, ProductViewDTO.class);
        return dtoProductFounded;
    }

    public List<ProductViewDTO> searchProductsByCityCheckInDateCheckOutDate(Long city, Date checkInDate, Date checkOutDate) {
        List<Product> productFoundByCityCheckInDateCheckOutDate = productRepository.searchProductByCityCheckInDateCheckOutDate(city, checkInDate, checkOutDate);
        List<ProductViewDTO> dtoProductFoundByCityCheckInDateCheckOutDate = this.mapperService.mapList(productFoundByCityCheckInDateCheckOutDate, ProductViewDTO.class);
        return dtoProductFoundByCityCheckInDateCheckOutDate;
    }

    public List<ProductViewDTO> searchProductsByCityExcludingDates(Long city, Date checkInDate, Date checkOutDate) {
        List<Product> productFoundByCityId = productRepository.findByCityId(city);
        List<Product> productFoundByCityCheckInDateCheckOutDate = productRepository.searchProductByCityCheckInDateCheckOutDate(city, checkInDate, checkOutDate);
        List<Product> auxList = new ArrayList<>();
        auxList.addAll(productFoundByCityId);
        for (Product productCity : productFoundByCityId) {
            for (Product productDate : productFoundByCityCheckInDateCheckOutDate) {
                if (productCity.equals(productDate)) {
                    auxList.remove(productCity);
                }
            }
        }
        List<ProductViewDTO> dtoFinalList = this.mapperService.mapList(auxList, ProductViewDTO.class);
        return dtoFinalList;
    }

    public List<ProductViewDTO> searchProductByCityCategoryCheckInDateCheckOutDate(Long city, Long category, Date checkInDate, Date checkOutDate) {
        List<Product> productFoundByCityIdAndCategoryId = productRepository.findByCityIdAndCategoryId(city, category);
        List<Product> productFoundByCityCheckInDateCheckOutDate = productRepository.searchProductByCityCategoryCheckInDateCheckOutDate(city, category, checkInDate, checkOutDate);
        List<Product> auxList = new ArrayList<>();
        auxList.addAll(productFoundByCityIdAndCategoryId);
        for (Product productCityCategory : productFoundByCityIdAndCategoryId) {
            for (Product productDate : productFoundByCityCheckInDateCheckOutDate) {
                if (productCityCategory.equals(productDate)) {
                    auxList.remove(productCityCategory);
                }
            }
        }
        List<ProductViewDTO> dtoFinalList = this.mapperService.mapList(auxList, ProductViewDTO.class);
        return dtoFinalList;
    }
}
