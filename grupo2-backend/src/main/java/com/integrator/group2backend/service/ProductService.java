package com.integrator.group2backend.service;

import com.integrator.group2backend.dto.PolicyDTO;
import com.integrator.group2backend.dto.ProductCreateDTO;
import com.integrator.group2backend.dto.ProductUpdateDTO;
import com.integrator.group2backend.dto.ProductViewDTO;
import com.integrator.group2backend.entities.Category;
import com.integrator.group2backend.entities.City;
import com.integrator.group2backend.entities.Feature;
import com.integrator.group2backend.entities.Image;
import com.integrator.group2backend.entities.PolicyItem;
import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.exception.DataIntegrityViolationException;
import com.integrator.group2backend.exception.ResourceNotFoundException;
import com.integrator.group2backend.repository.ProductRepository;
import com.integrator.group2backend.utils.MapperService;
import com.integrator.group2backend.utils.UpdateProductCompare;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public ProductService(ProductRepository productRepository, MapperService mapperService, UpdateProductCompare updateProductCompare, CategoryService categoryService, FeatureService featureService, PolicyItemService policyItemService, ImageService imageService, CityService cityService, PolicyService policyService) {
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
    public ProductViewDTO addProduct(ProductCreateDTO newProduct) throws DataIntegrityViolationException{
        if(!(newProduct.getImage() == null || newProduct.getPolicyItems_id() == null || newProduct.getCategory_id() == null || newProduct.getFeatures_id() == null || newProduct.getCity_id() == null)){
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

            for (MultipartFile images : newProduct.getImage()) {
                Image image = imageService.addImage(images);
                if (image != null) {
                    image.setProduct(createdProduct);
                    imageList.add(image);
                }
            }
            product.setImages(imageList);

            Optional<Category> category = categoryService.searchCategoryById(newProduct.getCategory_id());
            product.setCategory(category.get());

            Optional<City> city = cityService.getCityById(newProduct.getCity_id());
            product.setCity(city.get());

            for (Long featureId : newProduct.getFeatures_id()) {
                Optional<Feature> feature = featureService.searchFeatureById(featureId);
                featureList.add(feature.get());
            }
            product.setFeatures(featureList);


            for (Long policyItemId: newProduct.getPolicyItems_id()) {
                Optional<PolicyItem> policyItem = policyItemService.getPolicyItemById(policyItemId);
                policyItemsList.add(policyItem.get());
            }
            product.setPolicyItems(policyItemsList);

            // We update the created product with its relationships
            createdProduct = productRepository.save(createdProduct);
            logger.info("Se agrego un producto correctamente");

            return this.setProductViewDTOsingle(createdProduct);
        }else{
            throw new DataIntegrityViolationException("Cannot create the product");
        }
    }

    public ResponseEntity<List<ProductViewDTO>> listAllProducts() throws ResourceNotFoundException {
        List<Product> searchedProducts = productRepository.findAll();
        if (searchedProducts.isEmpty()) {
            logger.error("Error al listar todos los productos");
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se listaron todos los productos");
        return ResponseEntity.ok(this.setProductViewDTO(searchedProducts));
    }

    public ResponseEntity<List<ProductViewDTO>> listRandomAllProducts() throws ResourceNotFoundException {
        List<Product> searchedProducts = productRepository.findAll();
        if ((searchedProducts.isEmpty())) {
            logger.error("Error al listar todos los productos aleatoriamente");
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se listaron todos los productos aleatoriamente");
        Collections.shuffle(searchedProducts);

        return ResponseEntity.ok(this.setProductViewDTO(searchedProducts));
    }

    public ProductViewDTO searchProductById(Long productId) throws ResourceNotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            logger.error("El producto especificado no existe con id " + productId);
            throw new ResourceNotFoundException("No value present: ");
        }
        return this.setProductViewDTOsingle(product.get());
    }

    public Optional<Product> getProductById(Long id) {
        return this.productRepository.findById(id);
    }

    public ProductViewDTO updateProduct(Long productId, ProductUpdateDTO productUpdate) throws ResourceNotFoundException {
        Optional<Product> oldProduct = this.productRepository.findById(productId);
        if (oldProduct.isEmpty()) {
            logger.error("El producto especificado no existe con id " + productId);
            throw new ResourceNotFoundException("No value present: ");
        }
        Product updatedProduct = updateProductCompare.updateProductCompare(oldProduct.get(), productUpdate);
        productRepository.save(updatedProduct);
        return this.setProductViewDTOsingle(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isPresent()) {
            logger.info("El producto con id " + id + " ha sido borrado");
            productRepository.deleteById(id);
            return;
        }
        logger.error("El producto con id " + id + " no existe en la base de datos");
    }

    public List<ProductViewDTO> listProductByCityId(Long city_id) throws ResourceNotFoundException {
        List<Product> productFoundByCityId = productRepository.findByCityId(city_id);
        if (productFoundByCityId.isEmpty()) {
            logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city_id);
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city_id);
        return this.setProductViewDTO(productFoundByCityId);
    }

    public List<ProductViewDTO> listProductByCategoryId(Long category_id) throws ResourceNotFoundException {
        List<Product> productFoundByCategoryId = productRepository.findByCategoryId(category_id);
        if (productFoundByCategoryId.isEmpty()) {
            logger.error("NO se encontraron los productos correspondientes a la Categoria con ID " + category_id);
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se encontraron los productos correspondientes a la Categoria con ID " + category_id);
        return this.setProductViewDTO(productFoundByCategoryId);
    }

    public List<ProductViewDTO> searchProductsByCityCheckInDateCheckOutDate(Long city, Date checkInDate, Date checkOutDate) throws ResourceNotFoundException {
        List<Product> productFoundByCityCheckInDateCheckOutDate = productRepository.searchProductByCityCheckInDateCheckOutDate(city, checkInDate, checkOutDate);
        if (productFoundByCityCheckInDateCheckOutDate.isEmpty()) {
            logger.error("No se encontraron los productos correspondientes la Ciudad con ID " + city + "  en las fechas especificadas.");
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " en las fechas especificadas.");
        return this.setProductViewDTO(productFoundByCityCheckInDateCheckOutDate);
    }

    public List<ProductViewDTO> listProductByCityIdAndCategoryId(Long city_id, Long category_id) throws ResourceNotFoundException {
        List<Product> productFoundByCityIdAndCategoryId = productRepository.findByCityIdAndCategoryId(city_id, category_id);
        if (productFoundByCityIdAndCategoryId.isEmpty()) {
            logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city_id + " y a la Categoria con ID " + category_id);
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city_id + " y a la Categoria con ID " + category_id);
        return this.setProductViewDTO(productFoundByCityIdAndCategoryId);
    }

    public List<ProductViewDTO> listProductByCityIdAndCategoryIdAndGuests(Long city_id, Long category_id, Integer guests) throws ResourceNotFoundException {
        List<Product> productFoundByCityIdAndCategoryIdAndGuests = productRepository.findByCityIdAndCategoryIdAndGuests(city_id, category_id, guests);
        if (productFoundByCityIdAndCategoryIdAndGuests.isEmpty()) {
            logger.error("No se encontraron los productos correspondientes a la Ciudad con ID " + city_id + ", a la Categoria con ID " + category_id + " y para " + guests + " inquilinos.");
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se encontraron los productos correspondientes a la Ciudad con ID " + city_id + ", a la Categoria con ID " + category_id + " y para " + guests + " inquilinos.");
        return this.setProductViewDTO(productFoundByCityIdAndCategoryIdAndGuests);
    }

    public List<ProductViewDTO> searchProductsByCityExcludingDates(Long city, Date checkInDate, Date checkOutDate) throws ResourceNotFoundException {
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
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " en las fechas especificadas.");
        return this.setProductViewDTO(auxList);
    }

    public List<ProductViewDTO> searchProductByCityCategoryCheckInDateCheckOutDate(Long city, Long category, Date checkInDate, Date checkOutDate) throws ResourceNotFoundException {
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
            throw new ResourceNotFoundException("No value present: ");
        }
        logger.info("Se encontraron los productos correspondientes la Ciudad con ID " + city + " y Categoria con ID " + category + " en las fechas especificadas.");
        return this.setProductViewDTO(auxList);
    }

    public List<ProductViewDTO> customProductFilter(Integer rooms, Integer beds, Integer bathrooms, Integer guests, Long city_id, Long category_id,
                                                    Float minPrice, Float maxPrice, String checkInDate, String checkOutDate, Boolean random) throws Exception {

        List<Product> foundByCustomFilter = productRepository.customDynamicQuery(rooms, beds, bathrooms, guests, city_id, category_id, minPrice, maxPrice);

        if (checkInDate != null && checkOutDate != null) {
            //System.out.println("Filtramos por fechas");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date formattedCheckInDate = dateFormatter.parse(checkInDate);
            Date formattedCheckOutDate = dateFormatter.parse(checkOutDate);

            List<Product> foundByDates = productRepository.searchProductByCheckInDateCheckOutDate(formattedCheckInDate, formattedCheckOutDate);

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
            if (auxList.isEmpty()) {
                logger.error("No hay reservas disponibles para los filtros y fechas especificadas.");
                throw new ResourceNotFoundException("No value present: ");
            }
            logger.info("Se filtraron los productos disponibles en las fechas especificadas.");
            if (random) {
                logger.info("Se mezclo la lista auxiliar de productos.");
                Collections.shuffle(auxList);
            }
            return this.setProductViewDTO(auxList);
        }

        if (foundByCustomFilter.isEmpty()) {
            logger.error("No se encontraron los productos correspondientes a los filtros utilizados.");
            throw new ResourceNotFoundException("No value present: ");
        }
        if (random) {
            logger.info("Se mezclo la lista de productos.");
            Collections.shuffle(foundByCustomFilter);
        }
        logger.info("Se filtraron los productos sin filtrar por fechas.");
        return this.setProductViewDTO(foundByCustomFilter);
    }

    public List<ProductViewDTO> setProductViewDTO(List<Product> products) {
        List<ProductViewDTO> listDTO = new ArrayList<>();
        for (Product p : products) {
            ProductViewDTO productViewDTO = this.mapperService.convert(p, ProductViewDTO.class);
            productViewDTO.setPolicies(this.getPolicies(p.getPolicyItems()));
            listDTO.add(productViewDTO);
        }
        return listDTO;
    }

    public ProductViewDTO setProductViewDTOsingle(Product product) {
        ProductViewDTO productViewDTO = this.mapperService.convert(product, ProductViewDTO.class);
        productViewDTO.setPolicies(this.getPolicies(product.getPolicyItems()));
        return productViewDTO;
    }

    public Set<PolicyDTO> getPolicies(Set<PolicyItem> policyItems) {
        return this.policyService.converPolicyItems(policyItems);
    }
}