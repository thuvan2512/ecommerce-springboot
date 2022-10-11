package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.dto.PicturePostDTO;
import com.thunv.ecommerceou.models.pojo.PicturePost;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PicturePostService {
    PicturePost getPicturePostByID(int picID);
    List<PicturePost> getAllPicturePost();
    PicturePost createPicturePost(PicturePostDTO picturePostDTO);
    PicturePost updatePicturePost(PicturePost picturePost);
    boolean deletePicturePost(int picID);

}
