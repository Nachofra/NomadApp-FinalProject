package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Image;
import com.integrator.group2backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private AmazonClient amazonClient;

    @Autowired
    public ImageService(ImageRepository imageRepository, AmazonClient amazonClient) {
        this.imageRepository = imageRepository;
        this.amazonClient = amazonClient;
    }
    /*public Image addImage(Image image){
        Image newImage = this.imageRepository.save(image);
        return newImage;
    }*/
    public Image addImage(MultipartFile file){
        Image newImage = new Image();
        String fileUrl = amazonClient.uploadFile(file);
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String fileExtension = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
        newImage.setName(fileName);
        newImage.setExtension(fileExtension);
        newImage.setURL(fileUrl);
        return imageRepository.save(newImage);
    }
    public List<Image> addImageList(List<Image> imagelist){
        return this.imageRepository.saveAll(imagelist);
    }
    public List<Image> getAllImage(){
        return this.imageRepository.findAll();
    }
    public Optional<Image> getImageById(Long id){
        return this.imageRepository.findById(id);
    }
}
