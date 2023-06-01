package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "promotion_code")
public class PromotionCode {
    @Id
    @Column(name = "id")
    @Getter
    @Setter
    private String id;

    @Column(name = "total_release")
    @Getter @Setter
    private Integer totalRelease;

    @Column(name = "total_current")
    @Getter @Setter
    private Integer totalCurrent;

    @Column(name = "total_use")
    @Getter @Setter
    private Integer totalUse;

    @Column(name = "code")
    @Getter @Setter
    private String code;

    @Column(name = "created_date")
    @Getter @Setter
    private Date createdDate = new Date();

    @Column(name = "end_usable_date")
    @Getter @Setter
    private Date endUsableDate;

    @Column(name = "state")
    @Getter @Setter
    private Integer state = 1;

    @Column(name = "is_public")
    @Getter @Setter
    private Integer isPublic = 0;

    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    @Getter @Setter
    private PromotionProgram program;
}
