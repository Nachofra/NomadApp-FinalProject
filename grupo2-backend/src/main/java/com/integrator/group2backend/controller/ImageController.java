package com.integrator.group2backend.controller;

import com.integrator.group2backend.entities.Image;
import com.integrator.group2backend.entities.Policy;
import com.integrator.group2backend.service.ImageService;
import com.integrator.group2backend.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /*@PostMapping
    private ResponseEntity<Image> addImage(@RequestBody Image image, @RequestPart(value = "file") MultipartFile file){
        return ResponseEntity.ok(this.imageService.addImage(image, file));
    }*/
    @PostMapping
    private ResponseEntity<Image> addImage(@RequestPart(value = "file") MultipartFile file){
        return ResponseEntity.ok(this.imageService.addImage(file));
    }
    @PostMapping("/list")
    private ResponseEntity<List<Image>> addImageList(@RequestBody List<Image> imageList){
        return ResponseEntity.ok(this.imageService.addImageList(imageList));
    }

    @GetMapping("/{id}")
    private ResponseEntity<Image> getImageById(@PathVariable Long id){
        Optional<Image> image = this.imageService.getImageById(id);
        if(image.isPresent()){
            return ResponseEntity.ok(image.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    private ResponseEntity<List<Image>> getAllImage(){
        List<Image> list = this.imageService.getAllImage();
        return ResponseEntity.ok(list);
    }
}
