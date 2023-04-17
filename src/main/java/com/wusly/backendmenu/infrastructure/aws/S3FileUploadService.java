package com.wusly.backendmenu.infrastructure.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wusly.backendmenu.domain.item.Item;
import com.wusly.backendmenu.domain.restaurant.Restaurant;
import com.wusly.backendmenu.error.PhotoUploadUnSuccessfulException;
import com.wusly.backendmenu.service.PhotoUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3FileUploadService implements PhotoUploadService {
    private final AmazonS3 s3client;

    @Override
    public String uploadItemPhoto(MultipartFile file, Restaurant restaurant, UUID itemId) {
        if(file == null)
            return null;
        String key = "%s/item/photo_%s.%s".formatted(restaurant.getId(),itemId,getContentTypeExtension(file.getContentType()));
        uploadPhoto(file,key);
        return key;
    }


    @Override
    public String uploadRestaurantPhoto(MultipartFile photo, Restaurant restaurant) {
        if(photo == null)
            return null;
        String key = "%s/pp/photo.%s".formatted(restaurant.getId(),getContentTypeExtension(photo.getContentType()));
        uploadPhoto(photo,key);
        return key;
    }

    @Override
    public String updateItemPhoto(Item item, Restaurant restaurant, MultipartFile photo) {
        if(photo == null)
            return null;
        String key = "%s/item/photo_%s.%s".formatted(restaurant.getId(),item.getId(),getContentTypeExtension(photo.getContentType()));
        uploadPhoto(photo,key);
        return key;
    }

    @Override
    public String uploadRestaurantSettingPhoto(MultipartFile photo, Restaurant restaurant) {
        if(photo == null)
            return null;
        String key = "setting/photo_%s.%s".formatted(restaurant.getId(),getContentTypeExtension(photo.getContentType()));
        uploadPhoto(photo,key);
        return key;
    }

    private void uploadPhoto(MultipartFile photo, String key) {
        try {
            var result = s3client.putObject(
                    "wuslyrestaurant",
                    "restaurant/%s".formatted(key),
                    photo.getInputStream(),
                    new ObjectMetadata()
            );
            log.info(result.toString());
        }catch(IOException e){
            throw new PhotoUploadUnSuccessfulException("photo upload unsuccessful!");
        }

    }

    public static String getContentTypeExtension(String contentType) {
        return switch (contentType.toLowerCase(Locale.ENGLISH)) {
            case "application/pdf" -> "pdf";
            case "image/jpeg" -> "jpeg";
            case "image/jpg" -> "jpg";
            case "image/png" -> "png";
            case "image/gif" -> "gif";
            default -> throw new IllegalArgumentException("%s content type is invalid!".formatted(contentType));
        };
    }

}
