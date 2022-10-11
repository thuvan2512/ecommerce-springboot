package com.thunv.ecommerceou.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class CloudinaryUploadConfig {
    @Autowired
    private Environment env;
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", env.getProperty("cloudinary.cloudName").toString(),
                "api_key", env.getProperty("cloudinary.apiKey").toString(),
                "api_secret", env.getProperty("cloudinary.apiSecret").toString(),
                "secure", true));
        return c;
    }
}
