package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.*;
import com.integrator.group2backend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<ProductViewDTO> listProductByCityIdAndCategoryId(Long city_id, Long category_id){
        List<Product> productFoundByCityIdAndCategoryId = productRepository.findByCityIdAndCategoryId(city_id, category_id);
        List<ProductViewDTO> dtoProductFoundByCityIdAndCategoryId = mapList(productFoundByCityIdAndCategoryId, ProductViewDTO.class);
        return dtoProductFoundByCityIdAndCategoryId;
    }
    public List<ProductViewDTO> listProductByCityIdAndCategoryIdAndGuests(Long city_id, Long category_id, Integer guests){
        List<Product> productFoundByCityIdAndCategoryIdAndGuests = productRepository.findByCityIdAndCategoryIdAndGuests(city_id, category_id, guests);
        List<ProductViewDTO> dtoProductFounded = mapList(productFoundByCityIdAndCategoryIdAndGuests, ProductViewDTO.class);
        return dtoProductFounded;
    }
    public List<ProductViewDTO> searchProductsByCityCheckInDateCheckOutDate(Long city, Date checkInDate, Date checkOutDate){
        List<Product> productFoundByCityCheckInDateCheckOutDate = productRepository.searchProductByCityCheckInDateCheckOutDate(city, checkInDate, checkOutDate);
        List<ProductViewDTO> dtoProductFoundByCityCheckInDateCheckOutDate = mapList(productFoundByCityCheckInDateCheckOutDate, ProductViewDTO.class);
        return dtoProductFoundByCityCheckInDateCheckOutDate;
    }
    /*public List<ProductViewDTO> searchProductsByCityExcludingDates(Long city, Date checkInDate, Date checkOutDate){
        List<Product> productFoundByCityId = productRepository.findByCityId(city);
        List<Product> productFoundByCityCheckInDateCheckOutDate = productRepository.searchProductByCityCheckInDateCheckOutDate(city, checkInDate, checkOutDate);
        List<Product> mergedList = new ArrayList<>();
        List<Product> finalList = new ArrayList<>();
        mergedList.addAll(productFoundByCityId);
        mergedList.addAll(productFoundByCityCheckInDateCheckOutDate);
        System.out.println("\nMerged List\n" + mergedList);
        for (Product finalElement : mergedList){
            System.out.println(finalElement.getId());
        }
        /*for (Product productCity : productFoundByCityId) {
            System.out.println("Comparo con el array principal. Ciudad con id ");
            System.out.println(productCity.getId());
            for (Product productDate : productFoundByCityCheckInDateCheckOutDate) {
                System.out.println("Chequeo las reservas de la ciudad con id ");
                System.out.println(productDate.getId());
                if (productCity.equals(productDate)){
                    System.out.println("Encontre una coincidencia. ELIMINAR.");

                }
            }
        }
        mergedList.sort(Comparator.comparing(Product::getId));
        System.out.println("\nFinal List\n" + mergedList);
        for (Product mergedElement : mergedList){
            if (mergedElement.getId() == ){

            }
        }
        List<ProductViewDTO> dtoFinalList = mapList(finalList, ProductViewDTO.class);
        System.out.println("\n" + dtoFinalList);
        return dtoFinalList;
    }*/

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
