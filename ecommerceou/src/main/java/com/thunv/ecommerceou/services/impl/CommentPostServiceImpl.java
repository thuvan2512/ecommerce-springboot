package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.dto.CommentDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CommentPost;
import com.thunv.ecommerceou.models.pojo.PicturePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.repositories.CommentRepository;
import com.thunv.ecommerceou.services.CommentService;
import com.thunv.ecommerceou.services.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentPostServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NotifyService notifyService;
    @Override
    public CommentPost getCommentPostByID(int commentID) throws RuntimeException{
        return this.commentRepository.findById(commentID).orElseThrow(() ->
                new RuntimeException("Can not find comment with id = " + commentID));
    }

    @Override
    public List<CommentPost> getAllCommentPost() throws RuntimeException{
        try {
            return this.commentRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public CommentPost createCommentPost(CommentDTO commentDTO) throws RuntimeException {
        try {
            CommentPost commentPost = new CommentPost();
            commentDTO.loadCommentFromDTO(commentPost);
            commentPost.setCreatedDate(new Date());
            commentPost.setAuthor(commentDTO.getAuthor());
            commentPost.setSalePost(commentDTO.getPost());

            String recipient = String.format("agency-%s", commentDTO.getPost().getAgency().getId());
            String title = "Your sale post has new comments";
            String detail = String.format("User %s just commented and rated your sale post '%s'.",
                    commentDTO.getAuthor().getUsername(), commentDTO.getPost().getTitle());
            String type = "Interactions";
            this.notifyService.pushNotify(recipient, commentDTO.getAuthor().getAvatar(), title, detail, type);
            return this.commentRepository.save(commentPost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteCommentPost(int commentID) throws RuntimeException{
        try {
            if (this.commentRepository.existsById(commentID) == false){
                throw new RuntimeException("Comment does not exist");
            }
            this.commentRepository.deleteById(commentID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<CommentPost> getListCommentByPost(SalePost salePost) throws RuntimeException{
        try {
            return this.commentRepository.getListCommentByPost(salePost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public int countCommentByPost(SalePost salePost) throws RuntimeException{
        try {
            return this.commentRepository.countCommentByPost(salePost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public double getAverageStarByAgency(Agency agency) throws RuntimeException{
        try {
            return this.commentRepository.getAverageStarByAgency(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
