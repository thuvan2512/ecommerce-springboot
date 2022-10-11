package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.dto.PicturePostDTO;
import com.thunv.ecommerceou.models.pojo.PicturePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.repositories.PicturePostRepository;
import com.thunv.ecommerceou.services.PicturePostService;
import com.thunv.ecommerceou.services.SalePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicturePostServiceImpl implements PicturePostService {

    @Autowired
    private PicturePostRepository picturePostRepository;
    @Autowired
    private SalePostService salePostService;
    @Override
    public PicturePost getPicturePostByID(int picID) throws RuntimeException {
        return this.picturePostRepository.findById(picID).orElseThrow(() ->
                new RuntimeException("Can not find picture with id = " + picID));
    }

    @Override
    public List<PicturePost> getAllPicturePost() throws RuntimeException{
        try {
            return this.picturePostRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public PicturePost createPicturePost(PicturePostDTO picturePostDTO) throws RuntimeException{
        try {
            PicturePost picturePost = new PicturePost();
            picturePostDTO.loadPicturePostFromDTO(picturePost);
            picturePost.setSalePost(this.salePostService.getSalePostByID(picturePostDTO.getPostID()));
            return this.picturePostRepository.save(picturePost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public PicturePost updatePicturePost(PicturePost picturePost) throws RuntimeException {
        try {
            return this.picturePostRepository.save(picturePost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deletePicturePost(int picID) throws RuntimeException{
        try {
            if (this.picturePostRepository.existsById(picID) == false){
                throw new RuntimeException("Picture does not exist");
            }
            this.picturePostRepository.deleteById(picID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

}
