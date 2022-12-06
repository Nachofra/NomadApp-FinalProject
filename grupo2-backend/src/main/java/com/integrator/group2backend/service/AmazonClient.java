package com.integrator.group2backend.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.integrator.group2backend.controller.CategoryController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {
    public static final Logger logger = Logger.getLogger(AmazonClient.class);

    private AmazonS3 s3client;

    @Value("${amazon.s3.bucket-url}")
    private String endpointUrl;
    @Value("${amazon.s3.default-bucket}")
    private String bucketName;
    @Value("${amazon.aws.access-key-id}")
    private String accessKey;
    @Value("${amazon.aws.access-key-secret}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
    public String uploadFile(MultipartFile multipartFile) {
        if (multipartFile.getSize() <= 3145728 ){
            String fileUrl = "";
            try {
                File file = convertMultiPartToFile(multipartFile);
                String fileName = generateFileName(multipartFile);
                fileUrl = endpointUrl + "/" + fileName;
                uploadFileTos3bucket(fileName, file);
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("Se cargo una imagen en el bucket S3 con endpoint " + endpointUrl);
            return fileUrl;
        }
        logger.error("El archivo de imagen es demasiado pesado.");
        return null;
    }
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        System.out.println(s3client.doesBucketExist(fileName));
        System.out.println(bucketName+"/"+fileName);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        logger.info("Se elimino una imagen del bucket S3 con endpoint " + endpointUrl);
        return "Successfully deleted";
    }
}
