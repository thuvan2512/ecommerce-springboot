package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "follow_agency")
public class FollowAgency {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "state")
    @Getter @Setter
    private Integer state;
    @JoinColumn(name = "author", referencedColumnName = "id")
    @ManyToOne
    @Getter @Setter
    private User author;
    @JoinColumn(name = "agency", referencedColumnName = "id")
    @ManyToOne
    @Getter
    @Setter
    private Agency agency;

    public FollowAgency() {
    }
}
