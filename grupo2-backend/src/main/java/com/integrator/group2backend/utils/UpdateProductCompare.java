package com.integrator.group2backend.utils;

import com.integrator.group2backend.entities.*;
import com.integrator.group2backend.service.CategoryService;
import com.integrator.group2backend.service.CityService;
import com.integrator.group2backend.service.ImageService;
import com.integrator.group2backend.service.PolicyService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UpdateProductCompare {
    /*private final CityService cityService;
    private final CategoryService categoryService;
    private final PolicyService policyService;
    private final ImageService imageService;

    public UpdateProductCompare(CityService cityService, CategoryService categoryService, PolicyService policyService, ImageService imageService) {
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.policyService = policyService;
        this.imageService = imageService;
    }*/

    public Product updateProductCompare (Optional<Product> oldProduct, Product newProduct){

        Set<Image> images = new HashSet<>();
        Set<Policy> policies = new HashSet<>();
        Set<Feature> features = new HashSet<>();

        if (newProduct.getTitle() == null){
            newProduct.setTitle(oldProduct.get().getTitle());
        }
        if (newProduct.getDescription() == null){
            newProduct.setDescription(oldProduct.get().getDescription());
        }
        if (newProduct.getRooms() == null){
            newProduct.setRooms(oldProduct.get().getRooms());
        }
        if (newProduct.getBeds() == null){
            newProduct.setBeds(oldProduct.get().getBeds());
        }
        if (newProduct.getGuests() == null){
            newProduct.setGuests(oldProduct.get().getGuests());
        }
        if (newProduct.getDailyPrice() == null){
            newProduct.setDailyPrice(oldProduct.get().getDailyPrice());
        }
        if (newProduct.getAddress() == null){
            newProduct.setAddress(oldProduct.get().getAddress());
        }
        if (newProduct.getNumber() == null){
            newProduct.setNumber(oldProduct.get().getNumber());
        }
        if (newProduct.getFloor() == null){
            newProduct.setFloor(oldProduct.get().getFloor());
        }
        if (newProduct.getCity() == null){
            System.out.println("No actualizo City");
            newProduct.setCity(oldProduct.get().getCity());
        }else {
            System.out.println("Actualizo City");
            //Optional<City> newCity = cityService.getCityById(newProduct.getCity().getId());
            //newProduct.setCity(newCity.get());
        }
        if (newProduct.getCategory() == null){
            newProduct.setCategory(oldProduct.get().getCategory());
        }

        images.addAll(newProduct.getImages());
        if (newProduct.getImages() == null){
            images.addAll(oldProduct.get().getImages().stream().distinct().collect(Collectors.toList()));
        }
        newProduct.setImages(images);

        policies.addAll(newProduct.getPolicies());
        if (newProduct.getImages() == null){
            policies.addAll(oldProduct.get().getPolicies().stream().distinct().collect(Collectors.toList()));
        }
        newProduct.setPolicies(policies);

        features.addAll(newProduct.getFeatures());
        if (newProduct.getFeatures() == null){
            features.addAll(oldProduct.get().getFeatures().stream().distinct().collect(Collectors.toList()));
        }
        newProduct.setFeatures(features);

        System.out.println("---------- OLD PRODUCT ----------");
        System.out.println(oldProduct.get().getTitle());
        System.out.println(oldProduct.get().getDescription());
        System.out.println(oldProduct.get().getRooms());
        System.out.println(oldProduct.get().getBeds());
        System.out.println(oldProduct.get().getGuests());
        System.out.println(oldProduct.get().getDailyPrice());
        System.out.println(oldProduct.get().getAddress());
        System.out.println(oldProduct.get().getNumber());
        System.out.println(oldProduct.get().getFloor());
        System.out.println(oldProduct.get().getCity());
        System.out.println(oldProduct.get().getCategory());
        System.out.println(oldProduct.get().getImages());
        System.out.println(oldProduct.get().getPolicies());
        System.out.println(oldProduct.get().getFeatures());
        System.out.println("---------- NEW PRODUCT ----------");
        System.out.println(newProduct.getTitle());
        System.out.println(newProduct.getDescription());
        System.out.println(newProduct.getRooms());
        System.out.println(newProduct.getBeds());
        System.out.println(newProduct.getGuests());
        System.out.println(newProduct.getDailyPrice());
        System.out.println(newProduct.getAddress());
        System.out.println(newProduct.getNumber());
        System.out.println(newProduct.getFloor());
        System.out.println(newProduct.getCity());
        System.out.println(newProduct.getCategory());
        System.out.println(newProduct.getImages());
        System.out.println(newProduct.getPolicies());
        System.out.println(newProduct.getFeatures());

        return newProduct;
    }
}
