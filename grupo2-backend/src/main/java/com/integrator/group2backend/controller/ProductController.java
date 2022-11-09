package com.integrator.group2backend.controller;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.entities.City;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.service.CategoryService;
import com.integrator.group2backend.service.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.integrator.group2backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    public static final Logger logger = Logger.getLogger(ProductController.class);
    private final ProductService productService;
    private final CityService cityService;
    private final CategoryService categoryService;
    @Autowired
    public ProductController(ProductService productService, CityService cityService, CategoryService categoryService){
        this.productService = productService;
        this.cityService = cityService;
        this.categoryService = categoryService;
    }

    /*@GetMapping
    public ResponseEntity<List<Product>> listAllProducts() {
        List<Product> searchedProducts = productService.listAllProducts();
        if (!(searchedProducts.isEmpty())) {
            logger.info("Se listaron todos los productos");
            return ResponseEntity.ok(searchedProducts);
        } else {
            logger.error("Error al listar todos los productos");
            return ResponseEntity.ok(searchedProducts);
        }
    }*/
    @GetMapping
    public ResponseEntity<List<ProductViewDTO>> listAllProducts() {
        List<ProductViewDTO> searchedProducts = productService.listAllProducts();;
        if (!(searchedProducts.isEmpty())) {
            logger.info("Se listaron todos los productos");
            return ResponseEntity.ok(searchedProducts);
        } else {
            logger.error("Error al listar todos los productos");
            return ResponseEntity.ok(searchedProducts);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<List<ProductViewDTO>> listRandomAllProducts() {
        List<ProductViewDTO> searchedProducts = productService.listRandomAllProducts();
        if (!(searchedProducts.isEmpty())) {
            logger.info("Se listaron todos los productos aleatoriamente");
            return ResponseEntity.ok(searchedProducts);
        } else {
            logger.error("Error al listar todos los productos aleatoriamente");
            return ResponseEntity.ok(searchedProducts);
        }
    }
    /*@GetMapping("/{id}")
    public ResponseEntity<Product> searchProductById(@PathVariable("id") Long productId){
        Optional<Product> productFound = productService.searchProductById(productId);
        if(productFound.isPresent()){
            logger.info("Se encontro correctamente el producto con id " + productId);
            return ResponseEntity.ok(productFound.get());
        }else{
            logger.error("El producto especificado no existe con id " + productId);
            return ResponseEntity.badRequest().build();
        }
    }*/
    @GetMapping("/{id}")
    public ResponseEntity<ProductViewDTO> searchProductById(@PathVariable("id") Long productId){
        Optional<Product> productFound = productService.searchProductById(productId);
        if(productFound.isPresent()){
            logger.info("Se encontro correctamente el producto con id " + productId);
            return ResponseEntity.ok(productService.convertToDto(productFound.get()));
        }else{
            logger.error("El producto especificado no existe con id " + productId);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product addedProduct = productService.addProduct(product);
        logger.info("Se agrego un producto");
        return ResponseEntity.ok(addedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product){
        boolean productExists = productService.searchProductById(productId).isPresent();
        if(productExists){
            product.setId(productId);
            Product updatedProduct = productService.updateProduct(product);
            logger.info("Se actualizo correctamente el producto con id " + productId);
            return ResponseEntity.ok(updatedProduct);
        }else{
            logger.error("El producto especificado no existe con id " + productId);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId){
        boolean productExist = productService.searchProductById(productId).isPresent();
        if(productExist){
            productService.deleteProduct(productId);
            logger.info("El producto con id " + productId + " ha sido borrado");
            return ResponseEntity.ok("El producto con id " + productId + " ha sido borrado");
        }else{
            logger.error("El producto con id " + productId + " no existe en la base de datos");
            return ResponseEntity.ok("El producto con id " + productId + " no existe en la base de datos");
        }
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<List<ProductViewDTO>> getProductByCityId(@PathVariable Long id){
        Optional<City> city = this.cityService.getCityById(id);
        if (!city.isPresent()) {
            logger.error("La ciudad con id " + id + " no existe en la base de datos");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.productService.listProductByCityId(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductViewDTO>> getProductByCategoryId(@PathVariable Long id){
        Optional<Category> category = this.categoryService.searchCategoryById(id);
        if (!category.isPresent()) {
            logger.error("La categoría con id " + id + " no existe en la base de datos");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.productService.listProductByCategoryId(id));
    }


}
