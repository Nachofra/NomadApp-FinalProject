package com.integrator.group2backend.controller;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.service.CategoryService;
import com.integrator.group2backend.service.CityService;
import com.integrator.group2backend.service.ProductService;
import com.integrator.group2backend.service.ReservationService;
import com.integrator.group2backend.utils.MapperService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    public static final Logger logger = Logger.getLogger(ProductService.class);

    private final ProductService productService;
    private final CityService cityService;
    private final CategoryService categoryService;
    private final ReservationService reservationService;

    private final MapperService mapperService;

    @Autowired
    public ProductController(ProductService productService, CityService cityService, CategoryService categoryService, ReservationService reservationService, MapperService mapperService) {
        this.productService = productService;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.reservationService = reservationService;
        this.mapperService = mapperService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }
    /*@GetMapping
    public ResponseEntity<List<ProductViewDTO>> listAllProducts() {
        return productService.listAllProducts();
    }*/
    @GetMapping("/random")
    public ResponseEntity<List<ProductViewDTO>> listRandomAllProducts() {
        return productService.listRandomAllProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductViewDTO> searchProductById(@PathVariable("id") Long productId) {
        Optional<Product> productFound = productService.searchProductById(productId);
        if (productFound.isPresent()) {
            logger.info("Se encontro correctamente el producto con id " + productId);
            return ResponseEntity.ok(this.mapperService.convert(productFound.get(), ProductViewDTO.class));
        } else {
            logger.error("El producto especificado no existe con id " + productId);
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
        boolean productExists = productService.searchProductById(productId).isPresent();
        if (productExists) {
            product.setId(productId);
            Product updatedProduct = productService.updateProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        boolean productExist = productService.searchProductById(productId).isPresent();
        if (productExist) {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("El producto con id " + productId + " ha sido borrado");
        }
        return ResponseEntity.ok("El producto con id " + productId + " no existe en la base de datos");
    }
    @RequestMapping
    public ResponseEntity<List<ProductViewDTO>> findProductsByCustomFilter(
            @RequestParam(required = false) Integer rooms, @RequestParam(required = false) Integer beds,
            @RequestParam(required = false) Integer bathrooms, @RequestParam(required = false) Integer guests,
            @RequestParam(required = false) Long city, @RequestParam(required = false) Long category,
            @RequestParam(required = false) Float minPrice, @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String checkInDate, @RequestParam(required = false) String checkOutDate)
            throws Exception{
        /*System.out.println("rooms " + rooms);
        System.out.println("beds " + beds);
        System.out.println("bathrooms " + bathrooms);
        System.out.println("guests " + guests);
        System.out.println("city id " + city);
        System.out.println("category id " + category);
        System.out.println("minPrice " + minPrice);
        System.out.println("maxPrice " + maxPrice);
        System.out.println("checkInDate " + checkInDate);
        System.out.println("checkOutDate " + checkOutDate);*/
        List<ProductViewDTO> searchedProductsByCustomFilter = productService.customProductFilter(rooms, beds, bathrooms, guests, city, category, minPrice, maxPrice, checkInDate, checkOutDate);
        return ResponseEntity.ok(searchedProductsByCustomFilter);
    }
    /*@GetMapping("/city/{id}")
    public ResponseEntity<List<ProductViewDTO>> getProductByCityId(@PathVariable Long id) {
        Optional<City> city = this.cityService.getCityById(id);
        if (city.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.productService.listProductByCityId(id));
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductViewDTO>> getProductByCategoryId(@PathVariable Long id) {
        Optional<Category> category = this.categoryService.searchCategoryById(id);
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.productService.listProductByCategoryId(id));
    }
    @RequestMapping(params = "category")
    public ResponseEntity<List<ProductViewDTO>> searchProductByCategoryId(@RequestParam Long category) {
        List<ProductViewDTO> searchedProductsByCategory = productService.listProductByCategoryId(category);
        if (searchedProductsByCategory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(searchedProductsByCategory);
    }

    @RequestMapping(params = "city")
    public ResponseEntity<List<ProductViewDTO>> searchProductByCityId(@RequestParam String city) {
        List<ProductViewDTO> searchedProductsByCityId = productService.listProductByCityId(Long.parseLong(city));
        if (searchedProductsByCityId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(searchedProductsByCityId);
    }

    @RequestMapping(params = {"city", "category"})
    public ResponseEntity<List<ProductViewDTO>> searchProductByCityIdAndCategoryId(@RequestParam Long city, @RequestParam Long category) {
        List<ProductViewDTO> searchedProductsByCityIdAndCategoryId = productService.listProductByCityIdAndCategoryId(city, category);
        if (searchedProductsByCityIdAndCategoryId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(searchedProductsByCityIdAndCategoryId);
    }

    @RequestMapping(params = {"city", "category", "guests"})
    public ResponseEntity<List<ProductViewDTO>> searchProductByFilter(@RequestParam Long city, @RequestParam Long category, @RequestParam Integer guests) {
        List<ProductViewDTO> searchProductByFilter = productService.listProductByCityIdAndCategoryIdAndGuests(city, category, guests);
        if (searchProductByFilter.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(searchProductByFilter);
    }
    @RequestMapping(params = {"city", "checkInDate", "checkOutDate"})
    public ResponseEntity<List<ProductViewDTO>> findByCityIdAndCheckInDateAndCheckOutDate(@RequestParam Long city, @RequestParam String checkInDate, @RequestParam String checkOutDate) throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        List<ProductViewDTO> searchedProductByCityCheckInDateCheckOutDate = productService.searchProductsByCityExcludingDates(city, dateFormatter.parse(checkInDate), dateFormatter.parse(checkOutDate));
        if (searchedProductByCityCheckInDateCheckOutDate.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(searchedProductByCityCheckInDateCheckOutDate);
    }

    @RequestMapping(params = {"city", "category", "checkInDate", "checkOutDate"})
    public ResponseEntity<List<ProductViewDTO>> findProductByCityCategoryCheckInDateCheckOutDate(@RequestParam Long city, @RequestParam Long category, @RequestParam String checkInDate, @RequestParam String checkOutDate) throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        List<ProductViewDTO> searchedProductByCityCategoryCheckInDateCheckOutDate = productService.searchProductByCityCategoryCheckInDateCheckOutDate(city, category, dateFormatter.parse(checkInDate), dateFormatter.parse(checkOutDate));
        if (searchedProductByCityCategoryCheckInDateCheckOutDate.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(searchedProductByCityCategoryCheckInDateCheckOutDate);
    }
*/
}
