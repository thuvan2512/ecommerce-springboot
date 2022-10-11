package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment_post")
public class CommentPost {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "content")
    @Getter @Setter
    private String content;
    @Column(name = "star_rate")
    @Getter @Setter
    private Integer starRate;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "author",referencedColumnName = "id")
    @Getter @Setter
    private User author;
    @ManyToOne
    @JoinColumn(name = "sale_post",referencedColumnName = "id")
    @Getter
    @Setter
    @JsonIgnoreProperties({"itemPostSet","picturePostSet"})
    private SalePost salePost;

    public CommentPost() {
    }
}
