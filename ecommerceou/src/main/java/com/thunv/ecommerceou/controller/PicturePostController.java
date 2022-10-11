package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.PicturePostDTO;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.models.pojo.PicturePost;
import com.thunv.ecommerceou.services.PicturePostService;
import com.thunv.ecommerceou.services.SalePostService;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/picture-post")
public class PicturePostController {
    @Autowired
    private PicturePostService picturePostService;
    @Autowired
    private SalePostService salePostService;
    @Autowired
    private Utils utils;

    @GetMapping(value = "/{picID}")
    public ResponseEntity<ModelResponse> getPictureByID(@PathVariable(value = "picID") String picID){
        String ms = "Get picture successfully";
        String code = "200";
        PicturePost pic = null;
        HttpStatus status = HttpStatus.OK;
        try {
            pic = this.picturePostService.getPicturePostByID(Integer.parseInt(picID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,pic)
        );
    }
    @GetMapping(value = "/all")
    public ResponseEntity<ModelResponse> getAllPicturePost(){
        String ms = "Get all pictures successfully";
        String code = "200";
        List<PicturePost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.picturePostService.getAllPicturePost();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @PostMapping(path = "/create")
    public ResponseEntity<ModelResponse> createPicturePost(@RequestBody @Valid PicturePostDTO picturePostDTO,
                                                           BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Insert picture successfully";
        String code = "201";
        PicturePost pic = null;
        if (result.hasErrors()){
            System.out.println("ABC");
        }
        try {
            pic = this.picturePostService.createPicturePost(picturePostDTO);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,pic)
        );
    }
    @PutMapping(path = "/{picID}")
    public ResponseEntity<ModelResponse> updatePicturePost(@RequestBody @Valid PicturePost newPicture,
                                                                @PathVariable(value = "picID") String picID,
                                                           BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Update picture successfully";
        String code = "200";
        PicturePost pic = null;
        HttpStatus status = HttpStatus.OK;
        try {
            PicturePost picturePost = this.picturePostService.getPicturePostByID(Integer.parseInt(picID));
            if (newPicture.getImage() != null){
                picturePost.setImage(newPicture.getImage());
            }
            if (newPicture.getDescription()!= null){
                picturePost.setDescription(newPicture.getDescription());
            }
            pic = this.picturePostService.updatePicturePost(picturePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,pic)
        );
    }
    @DeleteMapping(path = "/{picID}")
    public ResponseEntity<ModelResponse> deletePicturePost(@PathVariable(value = "picID") String picID){
        String ms = "Delete picture successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.picturePostService.deletePicturePost(Integer.parseInt(picID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,null)
        );
    }
}
