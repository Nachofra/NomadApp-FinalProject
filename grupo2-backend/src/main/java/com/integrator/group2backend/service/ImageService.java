package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Image;
import com.integrator.group2backend.repository.ImageRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    public static final Logger logger = Logger.getLogger(ImageService.class);

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
        if (fileUrl == ""){
            return null;
        }
        newImage.setName(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
        newImage.setExtension(fileUrl.substring(fileUrl.lastIndexOf(".") + 1));
        newImage.setURL(fileUrl);
        logger.info("Se agrego una nueva imagen.");
        return imageRepository.save(newImage);
    }
    public List<Image> addMultipleImages(List<MultipartFile> files){
        List<Image> newImages = new ArrayList<Image>();
        for (MultipartFile file:files) {
            Image newImage = new Image();
            String fileUrl = amazonClient.uploadFile(file);
            newImage.setName(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
            newImage.setExtension(fileUrl.substring(fileUrl.lastIndexOf(".") + 1));
            newImage.setURL(fileUrl);
            logger.info("Se agrego una nueva imagen.");
            newImages.add(newImage);
        }
        logger.info("Se creo una nueva lista de imagenes.");
        return imageRepository.saveAll(newImages);
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
