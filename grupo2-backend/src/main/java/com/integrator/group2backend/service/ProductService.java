package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.PolicyDTO;
import com.integrator.group2backend.dto.ProductCreateDTO;
import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.*;
import com.integrator.group2backend.exception.ResourceNotFoundException;
import com.integrator.group2backend.repository.ProductRepository;
import com.integrator.group2backend.utils.MapperService;
import com.integrator.group2backend.utils.UpdateProductCompare;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductService {
    public static final Logger logger = Logger.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final MapperService mapperService;
    private final UpdateProductCompare updateProductCompare;
    private final CategoryService categoryService;
    private final FeatureService featureService;
    private final PolicyItemService policyItemService;
    private final ImageService imageService;
    private final CityService cityService;

    private final PolicyService policyService;

    public ProductService(ProductRepository productRepository, MapperService mapperService, UpdateProductCompare updateProductCompare, CategoryService categoryService, FeatureService featureService, PolicyItemService policyItemService, ImageService imageService, CityService cityService,PolicyService policyService) {
        this.productRepository = productRepository;
        this.mapperService = mapperService;
        this.updateProductCompare = updateProductCompare;
        this.categoryService = categoryService;
        this.featureService = featureService;
        this.policyItemService = policyItemService;
        this.imageService = imageService;
        this.cityService = cityService;
        this.policyService = policyService;
    }

    /*public Product addProduct(Product product) {
        logger.info("Se agrego un producto correctamente");
        return productRepository.save(product);
    }*/
    public ProductViewDTO addProduct(ProductCreateDTO newProduct) {
        Product product = new Product();
        Set<Image> imageList = new HashSet<>();
        Set<Feature> featureList = new HashSet<>();
        Set<PolicyItem> policyItemsList = new HashSet<>();

        product.setTitle(newProduct.getTitle());
        product.setDescription(newProduct.getDescription());
        product.setRooms(newProduct.getRooms());
        product.setBeds(newProduct.getBeds());
        product.setBathrooms(newProduct.getBathrooms());
        product.setGuests(newProduct.getGuests());
        product.setDailyPrice(newProduct.getDailyPrice());
        product.setAddress(newProduct.getAddress());
        product.setNumber(newProduct.getNumber());
        product.setFloor(newProduct.getFloor());
        product.setApartment(newProduct.getApartment());
        product.setLatitude(newProduct.getLatitude());
        product.setLongitude(newProduct.getLongitude());

        // We create the product
        Product createdProduct = productRepository.save(product);

        for (MultipartFile images: newProduct.getImage()) {
            Image image = imageService.addImage(images);
            if(image != null){
                image.setProduct(createdProduct);
                imageList.add(image);
            }
        }
        product.setImages(imageList);

        Optional<Category> category = categoryService.searchCategoryById(newProduct.getCategory_id());
        product.setCategory(category.get());

        Optional<City> city = cityService.getCityById(newProduct.getCity_id());
        product.setCity(city.get());

        for (Long featureId: newProduct.getFeatures_id()) {
            Optional<Feature> feature = featureService.searchFeatureById(featureId);
            featureList.add(feature.get());
        }
        product.setFeatures(featureList);

        /*
        for (Long policyItemId: newProduct.getPolicyItems_id()) {
            Optional<PolicyItem> policyItem = policyItemService.getPolicyItemById(policyItemId);
            policyItemsList.add(policyItem.get());
        }
        product.setPolicyItems(policyItemsList);*/

        // We update the created product with its relationships
        createdProduct = productRepository.save(createdProduct);
        logger.info("Se agrego un producto correctamente");

        return this.mapperService.convert(createdProduct, ProductViewDTO.class);
    }
    public ResponseEntity<List<ProductViewDTO>> listAllProducts() {
        List<Product> searchedProducts = productRepository.findAll();
        if (searchedProducts.isEmpty()) {
            logger.error("Error al listar todos los productos");
            return ResponseEntity.badRequest().build();
        }
        List<ProductViewDTO> dtoSearchedProducts = this.mapperService.mapList(searchedProducts, ProductViewDTO.class);
        logger.info("Se listaron todos los productos");
        return ResponseEntity.ok(dtoSearchedProducts);
    }
    public ResponseEntity<List<ProductViewDTO>> listRandomAllProducts() {
        List<Product> searchedProducts = productRepository.findAll();
        if ((searchedProducts.isEmpty())) {
            logger.error("Error al listar todos los productos aleatoriamente");
            return ResponseEntity.badRequest().build();
        }
        logger.info("Se listaron todos los productos aleatoriamente");
        Collections.shuffle(searchedProducts);
        return ResponseEntity.ok(this.mapperService.mapList(searchedProducts, ProductViewDTO.class));
    }
    public ProductViewDTO searchProductById(Long productId) throws ResourceNotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()){
            logger.error("El producto especificado no existe con id " + productId);
            throw new ResourceNotFoundException("No value present: ");
        }

        ProductViewDTO productViewDTO = this.mapperService.convert(product.get(), ProductViewDTO.class);
        productViewDTO.setPolicies(this.getPolicies(product.get().getPolicyItems()));
        logger.info("Se encontro correctamente el producto con id " + productId);
        return productViewDTO;
    }

    public Optional<Product> getProductById(Long id){
        return this.productRepository.findById(id);
    }

    public ProductViewDTO updateProduct(Long productId, ProductCreateDTO productUpdate) throws ResourceNotFoundException {
        Optional<Product> oldProduct = this.productRepository.findById(productId);
        if (oldProduct.isEmpty()){
            logger.error("El producto especificado no existe con id " + productId);
            throw new ResourceNotFoundException("No value present: ");
        }
        Product updatedProduct = updateProductCompare.updateProductCompare(oldProduct.get(), productUpdate);

        ProductViewDTO productViewDTO = this.mapperService.convert(updatedProduct, ProductViewDTO.class);
        productViewDTO.setPolicies(this.getPolicies(updatedProduct.getPolicyItems()));
        logger.info("Se actualizo correctamente el producto con id " + productId);
        productRepository.save(updatedProduct);
        return productViewDTO;
    }
    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isPresent()){
            logger.info("El producto con id " + id + " ha sido borrado");
            productRepository.deleteById(id);
            return;
        }
        logger.error("El producto con id " + id + " no existe en la base de datos");
    }

    public List<ProductViewDTO> listProductByCityId(Long city_id) {
        List<Product> productFoundByCityId = productRepository.findByCityId(city_id);
        if (productFoundByCityId.isEmpty()){
            logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city_id);
        }
        logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city_id);
        return this.mapperService.mapList(productFoundByCityId, ProductViewDTO.class);
    }

    public List<ProductViewDTO> listProductByCategoryId(Long category_id) {
        List<Product> productFoundByCategoryId = productRepository.findByCategoryId(category_id);
        if (productFoundByCategoryId.isEmpty()) {
            logger.error("NO se encontraron los productos correspondientes a la Categoria con ID " + category_id);
        }
        logger.info("Se encontraron los productos correspondientes a la Categoria con ID " + category_id);
        return this.mapperService.mapList(productFoundByCategoryId, ProductViewDTO.class);
    }
    public List<ProductViewDTO> searchProductsByCityCheckInDateCheckOutDate(Long city, Date checkInDate, Date checkOutDate){
        List<Product> productFoundByCityCheckInDateCheckOutDate = productRepository.searchProductByCityCheckInDateCheckOutDate(city, checkInDate, checkOutDate);
        if (productFoundByCityCheckInDateCheckOutDate.isEmpty()){
            logger.error("No se encontraron los productos correspondientes la Ciudad con ID " + city + "  en las fechas especificadas.");
            return this.mapperService.mapList(productFoundByCityCheckInDateCheckOutDate, ProductViewDTO.class);
        }
        logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " en las fechas especificadas.");
        return this.mapperService.mapList(productFoundByCityCheckInDateCheckOutDate, ProductViewDTO.class);
    }

    public List<ProductViewDTO> listProductByCityIdAndCategoryId(Long city_id, Long category_id) {
        List<Product> productFoundByCityIdAndCategoryId = productRepository.findByCityIdAndCategoryId(city_id, category_id);
        if (productFoundByCityIdAndCategoryId.isEmpty()){
            logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city_id + " y a la Categoria con ID " + category_id);
        }
        logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city_id + " y a la Categoria con ID " + category_id);
        return this.mapperService.mapList(productFoundByCityIdAndCategoryId, ProductViewDTO.class);
    }

    public List<ProductViewDTO> listProductByCityIdAndCategoryIdAndGuests(Long city_id, Long category_id, Integer guests) {
        List<Product> productFoundByCityIdAndCategoryIdAndGuests = productRepository.findByCityIdAndCategoryIdAndGuests(city_id, category_id, guests);
        if (productFoundByCityIdAndCategoryIdAndGuests.isEmpty()){
            logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city_id + ", a la Categoria con ID " + category_id + " y para " + guests + " inquilinos.");
        }
        logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city_id + ", a la Categoria con ID " + category_id + " y para " + guests + " inquilinos.");
        return this.mapperService.mapList(productFoundByCityIdAndCategoryIdAndGuests, ProductViewDTO.class);
    }

    public List<ProductViewDTO> searchProductsByCityExcludingDates(Long city, Date checkInDate, Date checkOutDate){
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
        if (auxList.isEmpty()) {
            logger.error("No se encontraron los productos correspondientes la Ciudad con ID " + city + "  en las fechas especificadas.");
            return mapperService.mapList(auxList, ProductViewDTO.class);
        }
        logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " en las fechas especificadas.");
        return mapperService.mapList(auxList, ProductViewDTO.class);
    }

    public List<ProductViewDTO> searchProductByCityCategoryCheckInDateCheckOutDate(Long city, Long category, Date checkInDate, Date checkOutDate){
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
        if (auxList.isEmpty()) {
            logger.error("No se encontraron los productos correspondientes la Ciudad con ID " + city + " y Categoria con ID " + category + " en las fechas especificadas.");
            return mapperService.mapList(auxList, ProductViewDTO.class);
        }
        logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " y Categoria con ID " + category + " en las fechas especificadas.");
        return mapperService.mapList(auxList, ProductViewDTO.class);
    }
    public List<ProductViewDTO> customProductFilter(Integer rooms, Integer beds, Integer bathrooms, Integer guests, Long city_id, Long category_id,
                                                    Float minPrice, Float maxPrice, String checkInDate, String checkOutDate, Boolean random) throws Exception{

        List<Product> foundByCustomFilter = productRepository.customDynamicQuery(rooms, beds, bathrooms, guests, city_id, category_id, minPrice, maxPrice);

        if (checkInDate != null && checkOutDate != null){
            //System.out.println("Filtramos por fechas");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date formattedCheckInDate = dateFormatter.parse(checkInDate);
            Date formattedCheckOutDate = dateFormatter.parse(checkOutDate);

            List<Product> foundByDates = productRepository.searchProductByCheckInDateCheckOutDate(formattedCheckInDate,formattedCheckOutDate);

            List<Product> auxList = new ArrayList<>();
            auxList.addAll(foundByCustomFilter);
            /*System.out.println("Found by dates");
            System.out.println(foundByDates);
            System.out.println("Aux list before filter");
            System.out.println(auxList);*/
            for (Product productFilter : foundByCustomFilter) {
                /*System.out.println("Buscamos en el custom filter");
                System.out.println(productFilter.getReservations().toString());*/
                for (Product productDate : foundByDates) {
                    //System.out.println("Buscamos coincidencias con las fechas");
                    if (productFilter.equals(productDate)) {
                        //System.out.println("Encontramos coincidencias");
                        auxList.remove(productFilter);
                    }
                }
            }
            /*System.out.println("Aux list after filter");
            System.out.println(auxList);*/
            if (auxList.isEmpty()){
                logger.error("No hay reservas disponibles para los filtros y fechas especificadas.");
                throw new ResourceNotFoundException("No value present: ");
//                logger.error("No hay reservas disponibles para los filtros y fechas especificadas.");
//                return mapperService.mapList(auxList, ProductViewDTO.class);
            }
            logger.info("Se filtraron los productos disponibles en las fechas especificadas.");
            if (random){
                logger.info("Se mezclo la lista auxiliar de productos.");
                Collections.shuffle(auxList);
            }
            return this.setProductViewDTO(auxList);
        }

        if (foundByCustomFilter.isEmpty()){
            logger.error("No se encontraron los productos correspondientes a los filtros utilizados.");
            throw new ResourceNotFoundException("No value present: ");
            //return mapperService.mapList(foundByCustomFilter, ProductViewDTO.class);
        }
        if (random){
            logger.info("Se mezclo la lista de productos.");
            Collections.shuffle(foundByCustomFilter);
        }
        logger.info("Se filtraron los productos sin filtrar por fechas.");
        //return mapperService.mapList(foundByCustomFilter, ProductViewDTO.class);
        return this.setProductViewDTO(foundByCustomFilter);
    }

    public List<ProductViewDTO> setProductViewDTO(List<Product> products){
        List<ProductViewDTO> listDTO = new ArrayList<>();
        for (Product p : products) {
            ProductViewDTO productViewDTO = this.mapperService.convert(p, ProductViewDTO.class);
            productViewDTO.setPolicies(this.getPolicies(p.getPolicyItems()));
            listDTO.add(productViewDTO);
        }
        return listDTO;
    }

    public Set<PolicyDTO> getPolicies(Set<PolicyItem> policyItems){
        return this.policyService.converPolicyItems(policyItems);
    }
}