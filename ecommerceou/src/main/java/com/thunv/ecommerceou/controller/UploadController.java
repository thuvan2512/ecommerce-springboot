package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.CloudinaryService;
import com.thunv.ecommerceou.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private Environment env;

    @PostMapping(path = "/local-storage")
    public ResponseEntity<ModelResponse> uploadLocalStorage(@RequestParam(value = "file") MultipartFile file){
        try {
            String fileName = this.uploadService.storeFile(file);
            String url = env.getProperty("server.urlImage") +fileName;
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ModelResponse("201","Upload to local storage successfully",url)
            );
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400",ex.getMessage(),null)
            );
        }
    }
    @PostMapping(path = "/cloudinary")
    public ResponseEntity<ModelResponse> uploadCloudinary(@RequestParam(value = "file") MultipartFile file){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ModelResponse("201","Upload to cloudinary successfully",this.cloudinaryService.uploadFile(file))
            );
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400",ex.getMessage(),null)
            );
        }
    }
    @GetMapping(path = "/{fileName:.+}")
    public ResponseEntity<byte[]> viewImage(@PathVariable String fileName) {
        try {
            byte[] bytes = uploadService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ModelResponse> viewAllImages() {
        try {
            List<String> urls = this.uploadService.loadAll()
                    .map(path -> {
                        //convert fileName to url(send request "readDetailFile")
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(UploadController.class,
                                "viewImage", path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ModelResponse("200", "List files successfully", urls));
        }catch (Exception exception) {
            return ResponseEntity.ok(new
                    ModelResponse("400", exception.getMessage(), null));
        }
    }
}
