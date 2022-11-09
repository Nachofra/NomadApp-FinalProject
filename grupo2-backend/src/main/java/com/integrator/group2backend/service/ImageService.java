package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Image;
import com.integrator.group2backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image addImage(Image image){
        return this.imageRepository.save(image);
    }

    public List<Image> getAllImage(){
        return this.imageRepository.findAll();
    }

    public Optional<Image> getImageById(Long id){
        return this.imageRepository.findById(id);
    }
}
