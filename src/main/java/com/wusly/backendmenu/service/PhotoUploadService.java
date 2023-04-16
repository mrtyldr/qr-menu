package com.wusly.backendmenu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoUploadService {

    public String uploadPhoto(MultipartFile file){
        return "uploaded";
    }
}
