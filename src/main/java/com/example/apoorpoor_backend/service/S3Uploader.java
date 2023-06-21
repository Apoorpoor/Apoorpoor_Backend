package com.example.apoorpoor_backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.apoorpoor_backend.dto.chat.ChatImageDto;
import com.example.apoorpoor_backend.model.Image;
import com.example.apoorpoor_backend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Uploader {
    private static final String S3_BUCKET_PREFIX = "S3";

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image is null");
        }

        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        String newFileName = "image" + hour + minute + second + millis;
        String fileExtension = '.' + extractFileExtension(image.getOriginalFilename());
        String imageName = S3_BUCKET_PREFIX + newFileName + fileExtension;

        List<String> allowedExtensions = Arrays.asList(".png", ".jpeg", ".jpg", ".webp", ".gif", ".mp4");
        validateExtension(fileExtension, allowedExtensions);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getContentType());
        objectMetadata.setContentLength(image.getSize());

        try (InputStream inputStream = image.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, imageName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }


        String imageUrl = amazonS3.getUrl(bucketName, imageName).toString();
        saveImageToDatabase(imageUrl);

        return imageUrl;
    }

    private String extractFileExtension(String filename) {
        return filename.replaceAll("^.*\\.(.*)$", "$1");
    }

    private void validateExtension(String fileExtension, List<String> allowedExtensions) {
        if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new IllegalArgumentException("Invalid file extension");
        }
    }

    private void saveImageToDatabase(String imageUrl) {
        Image image = new Image();
        image.setImageUrl(imageUrl);

        imageRepository.save(image);
    }


}