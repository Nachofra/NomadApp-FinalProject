package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Feature;
import com.integrator.group2backend.repository.FeatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {
    private final FeatureRepository featureRepository;
    public FeatureService(FeatureRepository featureRepository){
        this.featureRepository = featureRepository;
    }
    public Feature addFeature(Feature feature){
        return featureRepository.save(feature);
    }
    public List<Feature> addFeatureList(List<Feature> featureList){
        return featureRepository.saveAll(featureList);
    }
    public Optional<Feature> searchFeatureById(Long featureId){
        return featureRepository.findById(featureId);
    }
    public List<Feature> listAllFeatures(){
        return featureRepository.findAll();
    }
    public Feature updateFeature(Feature feature){
        return featureRepository.save(feature);
    }
    public void deleteFeature(Long id){
        featureRepository.deleteById(id);
    }
}
