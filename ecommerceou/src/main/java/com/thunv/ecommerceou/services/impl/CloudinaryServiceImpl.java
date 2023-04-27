package com.thunv.ecommerceou.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thunv.ecommerceou.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map upload = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto", "folder", "open-market"));
            return  upload.get("secure_url").toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
