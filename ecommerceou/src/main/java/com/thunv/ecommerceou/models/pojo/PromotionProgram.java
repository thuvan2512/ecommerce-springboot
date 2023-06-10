package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "promotion_program")
public class PromotionProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "avatar")
    @Getter @Setter
    private String avatar;

    @Column(name = "program_name")
    @Getter @Setter
    private String programName;

    @Column(name = "program_title")
    @Getter @Setter
    private String programTitle;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    @Column(name = "begin_usable")
    @Getter @Setter
    private Date beginUsable;

    @Column(name = "end_usable")
    @Getter @Setter
    private Date endUsable;

    @Column(name = "reduction_type")
    @Getter @Setter
    private Integer reductionType;

    @Column(name = "percentage_reduction")
    @Getter @Setter
    private Integer percentageReduction;

    @Column(name = "reduction_amount_max")
    @Getter @Setter
    private Integer reductionAmountMax;

    @ManyToOne
    @JoinColumn(name = "agent_id",referencedColumnName = "id")
//    @JsonIgnore
    @Getter @Setter
    private Agency agency;

    @Column(name = "available_sku")
    @Getter @Setter
    private String availableSku;

    @Column(name = "state")
    @Getter @Setter
    private Integer state = 0;

    @Column(name = "created_date")
    @Getter @Setter
    private Date createdDate = new Date();

    @OneToMany(mappedBy = "program",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<PromotionCode> promotionCodeSet;
}
