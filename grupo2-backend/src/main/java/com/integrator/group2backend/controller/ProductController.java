package com.integrator.group2backend.controller;

import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.entities.City;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    public static final Logger logger = Logger.getLogger(ProductController.class);
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

    @GetMapping
    public ResponseEntity<List<ProductViewDTO>> listAllProducts() {
        List<ProductViewDTO> searchedProducts = productService.listAllProducts();
        if (!(searchedProducts.isEmpty())) {
            logger.info("Se listaron todos los productos");
            return ResponseEntity.ok(searchedProducts);
        } else {
            logger.error("Error al listar todos los productos");
            return ResponseEntity.badRequest().build();
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
            return ResponseEntity.badRequest().build();
        }
    }

    /*@GetMapping
    public ResponseEntity<List<ProductViewDTO>> listAllProducts(@RequestParam Map<String, String> allParams) {
        List<ProductViewDTO> searchedProducts = new ArrayList<>();
        System.out.println(allParams);
        // If empty bring all products
        if(allParams.isEmpty()){
            searchedProducts = productService.listAllProducts();
        }else if(allParams.get("city") != null && allParams.get("category") != null){
            searchedProducts.addAll(productService.listProductByCityIdAndCategoryId(Long.valueOf(allParams.get("city")), Long.valueOf(allParams.get("category"))));
        }else if (allParams.get("city") == null){
            searchedProducts.addAll(productService.listProductByCategoryId(Long.valueOf(allParams.get("category"))));
        }else{
            searchedProducts.addAll(productService.listProductByCityId(Long.valueOf(allParams.get("city"))));
        }

        if (!(searchedProducts.isEmpty())) {
            logger.info("Se listaron todos los productos");
            return ResponseEntity.ok(searchedProducts);
        } else {
            logger.error("Error al listar todos los productos");
            return ResponseEntity.ok(searchedProducts);
        }
    }
    @GetMapping("/random")
    public ResponseEntity<List<ProductViewDTO>> listRandomAllProducts(@RequestParam Map<String, String> allParams) {
        List<ProductViewDTO> searchedProducts = new ArrayList<>();

        // If empty bring all products
        if(allParams.isEmpty()){
            searchedProducts = productService.listAllProducts();
        }else if(allParams.get("city") != null && allParams.get("category") != null){
            searchedProducts.addAll(productService.listProductByCityIdAndCategoryId(Long.valueOf(allParams.get("city")), Long.valueOf(allParams.get("category"))));
        }else if (allParams.get("city") == null){
            searchedProducts.addAll(productService.listProductByCategoryId(Long.valueOf(allParams.get("category"))));
        }else{
            searchedProducts.addAll(productService.listProductByCityId(Long.valueOf(allParams.get("city"))));
        }
        Collections.shuffle(searchedProducts);

        if (!(searchedProducts.isEmpty())) {
            logger.info("Se listaron todos los productos aleatoriamente");
            return ResponseEntity.ok(searchedProducts);
        } else {
            logger.error("Error al listar todos los productos aleatoriamente");
            return ResponseEntity.ok(searchedProducts);
        }
    }*/
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

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        logger.info("Se agrego un producto");
        return ResponseEntity.ok(addedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
        boolean productExists = productService.searchProductById(productId).isPresent();
        if (productExists) {
            product.setId(productId);
            Product updatedProduct = productService.updateProduct(product);
            logger.info("Se actualizo correctamente el producto con id " + productId);
            return ResponseEntity.ok(updatedProduct);
        } else {
            logger.error("El producto especificado no existe con id " + productId);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        boolean productExist = productService.searchProductById(productId).isPresent();
        if (productExist) {
            productService.deleteProduct(productId);
            logger.info("El producto con id " + productId + " ha sido borrado");
            return ResponseEntity.ok("El producto con id " + productId + " ha sido borrado");
        } else {
            logger.error("El producto con id " + productId + " no existe en la base de datos");
            return ResponseEntity.ok("El producto con id " + productId + " no existe en la base de datos");
        }
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<List<ProductViewDTO>> getProductByCityId(@PathVariable Long id) {
        Optional<City> city = this.cityService.getCityById(id);
        if (!city.isPresent()) {
            logger.error("La ciudad con id " + id + " no existe en la base de datos");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.productService.listProductByCityId(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductViewDTO>> getProductByCategoryId(@PathVariable Long id) {
        Optional<Category> category = this.categoryService.searchCategoryById(id);
        if (!category.isPresent()) {
            logger.error("La categoría con id " + id + " no existe en la base de datos");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.productService.listProductByCategoryId(id));
    }

    @RequestMapping(params = "category")
    public ResponseEntity<List<ProductViewDTO>> searchProductByCategoryId(@RequestParam Long category) {
        List<ProductViewDTO> searchedProductsByCategory = productService.listProductByCategoryId(category);
        if (!searchedProductsByCategory.isEmpty()) {
            logger.info("Se encontraron los productos correspondientes a la Categoria con ID " + category);
            return ResponseEntity.ok(searchedProductsByCategory);
        }
        logger.error("NO se encontraron los productos correspondientes a la Categoria con ID " + category);
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(params = "city")
    public ResponseEntity<List<ProductViewDTO>> searchProductByCityId(@RequestParam String city) {
        List<ProductViewDTO> searchedProductsByCityId = productService.listProductByCityId(Long.parseLong(city));
        if (!searchedProductsByCityId.isEmpty()) {
            logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city);
            return ResponseEntity.ok(searchedProductsByCityId);
        }
        logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city);
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(params = {"city", "category"})
    public ResponseEntity<List<ProductViewDTO>> searchProductByCityIdAndCategoryId(@RequestParam Long city, @RequestParam Long category) {
        List<ProductViewDTO> searchedProductsByCityIdAndCategoryId = productService.listProductByCityIdAndCategoryId(city, category);
        if (!searchedProductsByCityIdAndCategoryId.isEmpty()) {
            logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city + " y a la Categoria con ID " + category);
            return ResponseEntity.ok(searchedProductsByCityIdAndCategoryId);
        }
        logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city + " y a la Categoria con ID " + category);
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(params = {"city", "category", "guests"})
    public ResponseEntity<List<ProductViewDTO>> searchProductByFilter(@RequestParam Long city, @RequestParam Long category, @RequestParam Integer guests) {
        List<ProductViewDTO> searchProductByFilter = productService.listProductByCityIdAndCategoryIdAndGuests(city, category, guests);
        if (!searchProductByFilter.isEmpty()) {
            logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city + ", a la Categoria con ID " + category + " y para " + guests + " inquilinos.");
            return ResponseEntity.ok(searchProductByFilter);
        }
        logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city + ", a la Categoria con ID " + category + " y para " + guests + " inquilinos.");
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(params = {"city", "checkInDate", "checkOutDate"})
    public ResponseEntity<List<ProductViewDTO>> findByCityIdAndCheckInDateAndCheckOutDate(@RequestParam Long city, @RequestParam String checkInDate, @RequestParam String checkOutDate) throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedCheckInDate = dateFormatter.parse(checkInDate);
        Date formattedCheckOutDate = dateFormatter.parse(checkOutDate);
        List<ProductViewDTO> searchedProductByCityCheckInDateCheckOutDate = productService.searchProductsByCityExcludingDates(city, formattedCheckInDate, formattedCheckOutDate);
        if (!searchedProductByCityCheckInDateCheckOutDate.isEmpty()) {
            logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " en las fechas especificadas.");
            return ResponseEntity.ok(searchedProductByCityCheckInDateCheckOutDate);
        }
        logger.error("No se encontraron los productos correspondientes la Ciudad con ID " + city + " en las fechas especificadas.");
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(params = {"city", "category", "checkInDate", "checkOutDate"})
    public ResponseEntity<List<ProductViewDTO>> findProductByCityCategoryCheckInDateCheckOutDate(@RequestParam Long city, @RequestParam Long category, @RequestParam String checkInDate, @RequestParam String checkOutDate) throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedCheckInDate = dateFormatter.parse(checkInDate);
        Date formattedCheckOutDate = dateFormatter.parse(checkOutDate);
        List<ProductViewDTO> searchedProductByCityCategoryCheckInDateCheckOutDate = productService.searchProductByCityCategoryCheckInDateCheckOutDate(city, category, formattedCheckInDate, formattedCheckOutDate);
        if (!searchedProductByCityCategoryCheckInDateCheckOutDate.isEmpty()) {
            logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " y Categoria con ID " + category + " en las fechas especificadas.");
            return ResponseEntity.ok(searchedProductByCityCategoryCheckInDateCheckOutDate);
        }
        logger.error("No se encontraron los productos correspondientes la Ciudad con ID " + city + " y Categoria con ID " + category + " en las fechas especificadas.");
        return ResponseEntity.badRequest().build();
    }
}
