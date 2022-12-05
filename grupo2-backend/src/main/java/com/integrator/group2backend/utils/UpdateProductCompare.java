package com.integrator.group2backend.utils;

import com.integrator.group2backend.dto.ProductCreateDTO;
import com.integrator.group2backend.entities.*;
import com.integrator.group2backend.service.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UpdateProductCompare {
    private final CityService cityService;
    private final CategoryService categoryService;
    private final FeatureService featureService;
    private final PolicyItemService policyItemService;
    private final ImageService imageService;

    public UpdateProductCompare(CityService cityService, CategoryService categoryService, PolicyItemService policyItemService, FeatureService featureService, ImageService imageService) {
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.policyItemService = policyItemService;
        this.featureService = featureService;
        this.imageService = imageService;
    }

    public Product updateProductCompare (Product oldProduct, ProductCreateDTO newProduct){

        Product auxProduct = new Product();
        Set<Feature> features = new HashSet<>();
        Set<PolicyItem> policyItems = new HashSet<>();
        Set<Image> images = new HashSet<>();

        auxProduct.setId(oldProduct.getId());

        if (newProduct.getTitle() == null){
            auxProduct.setTitle(oldProduct.getTitle());
        }else {
            auxProduct.setTitle(newProduct.getTitle());
        }

        if (newProduct.getDescription() == null){
            auxProduct.setDescription(oldProduct.getDescription());
        }else {
            auxProduct.setDescription(newProduct.getDescription());
        }

        if (newProduct.getRooms() == null){
            auxProduct.setRooms(oldProduct.getRooms());
        }else {
            auxProduct.setRooms(newProduct.getRooms());
        }

        if (newProduct.getBeds() == null){
            auxProduct.setBeds(oldProduct.getBeds());
        }else {
            auxProduct.setBeds(newProduct.getBeds());
        }

        if (newProduct.getBathrooms() == null){
            auxProduct.setBathrooms(oldProduct.getBathrooms());
        }else {
            auxProduct.setBathrooms(newProduct.getBathrooms());
        }

        if (newProduct.getGuests() == null){
            auxProduct.setGuests(oldProduct.getGuests());
        }else {
            auxProduct.setGuests(newProduct.getGuests());
        }

        if (newProduct.getDailyPrice() == null){
            auxProduct.setDailyPrice(oldProduct.getDailyPrice());
        }else {
            auxProduct.setDailyPrice(newProduct.getDailyPrice());
        }

        if (newProduct.getAddress() == null){
            auxProduct.setAddress(oldProduct.getAddress());
        }else{
            auxProduct.setAddress(newProduct.getAddress());
        }

        if (newProduct.getNumber() == null){
            auxProduct.setNumber(oldProduct.getNumber());
        }else {
            auxProduct.setNumber(newProduct.getNumber());
        }

        if (newProduct.getFloor() == null){
            auxProduct.setFloor(oldProduct.getFloor());
        }else {
            auxProduct.setFloor(newProduct.getFloor());
        }

        if (newProduct.getApartment() == null){
            auxProduct.setApartment(oldProduct.getApartment());
        }else {
            auxProduct.setApartment(newProduct.getApartment());
        }

        if (newProduct.getLatitude() == null){
            auxProduct.setLatitude(oldProduct.getLatitude());
        }else {
            auxProduct.setLatitude(newProduct.getLatitude());
        }

        if (newProduct.getLongitude() == null){
            auxProduct.setLongitude(oldProduct.getLongitude());
        }else {
            auxProduct.setLongitude(newProduct.getLongitude());
        }

        if (newProduct.getCity_id() == null){
            System.out.println("No actualizo City");
            auxProduct.setCity(oldProduct.getCity());
        }else {
            System.out.println("Actualizo City");
            Optional<City> searchedCity = cityService.getCityById(newProduct.getCity_id());
            auxProduct.setCity(searchedCity.get());
        }

        if (newProduct.getCategory_id() == null){
            System.out.println("No actualizo Category");
            auxProduct.setCategory(oldProduct.getCategory());
        }else {
            System.out.println("Actualizo category");
            Optional<Category> searchedCategory = categoryService.searchCategoryById(newProduct.getCategory_id());
            auxProduct.setCategory(searchedCategory.get());
        }

        if (newProduct.getFeatures_id() == null){
            System.out.println("No actualizo features");
            features.addAll(oldProduct.getFeatures());
            auxProduct.setFeatures(features);
        }else {
            for (Long featureId: newProduct.getFeatures_id()){
                features.add(featureService.searchFeatureById(featureId).get());
            }
            System.out.println("Actualizo features");
            auxProduct.setFeatures(features);
        }

        /*if (newProduct.getPolicyItems_id() == null){
            System.out.println("No actualizo PolicyItems");
            policyItems.addAll(oldProduct.getPolicyItems());
            auxProduct.setPolicyItems(policyItems);
        }else{
            for (Long policyItemId: newProduct.getPolicyItems_id()){
                policyItems.add(policyItemService.getPolicyItemById(policyItemId).get());
            }
            System.out.println("Actualizo PolicyItems");
            auxProduct.setPolicyItems(policyItems);
        }*/

        /*if (newProduct.getImages() == null){
            images.addAll(oldProduct.get().getImages().stream().distinct().collect(Collectors.toList()));
        }
        auxProduct.setImages(images);*/

        System.out.println("---------- OLD PRODUCT ----------");
        System.out.println(oldProduct.getTitle());
        System.out.println(oldProduct.getDescription());
        System.out.println(oldProduct.getRooms());
        System.out.println(oldProduct.getBeds());
        System.out.println(oldProduct.getBathrooms());
        System.out.println(oldProduct.getGuests());
        System.out.println(oldProduct.getDailyPrice());
        System.out.println(oldProduct.getAddress());
        System.out.println(oldProduct.getNumber());
        System.out.println(oldProduct.getFloor());
        System.out.println(oldProduct.getApartment());
        System.out.println(oldProduct.getLatitude());
        System.out.println(oldProduct.getLongitude());
        System.out.println(oldProduct.getCity());
        System.out.println(oldProduct.getCategory());
        System.out.println(oldProduct.getImages());
        System.out.println(oldProduct.getPolicyItems());
        System.out.println(oldProduct.getFeatures());
        System.out.println("---------- AUX PRODUCT ----------");
        System.out.println(auxProduct.getTitle());
        System.out.println(auxProduct.getDescription());
        System.out.println(auxProduct.getRooms());
        System.out.println(auxProduct.getBeds());
        System.out.println(auxProduct.getBathrooms());
        System.out.println(auxProduct.getGuests());
        System.out.println(auxProduct.getDailyPrice());
        System.out.println(auxProduct.getAddress());
        System.out.println(auxProduct.getNumber());
        System.out.println(auxProduct.getFloor());
        System.out.println(auxProduct.getApartment());
        System.out.println(auxProduct.getLatitude());
        System.out.println(auxProduct.getLongitude());
        System.out.println(auxProduct.getCity());
        System.out.println(auxProduct.getCategory());
        System.out.println(auxProduct.getImages());
        System.out.println(auxProduct.getPolicyItems());
        System.out.println(auxProduct.getFeatures());

        return auxProduct;
    }
}
