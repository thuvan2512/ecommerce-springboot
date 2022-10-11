package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "response_agency")
public class ResponseAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "star_rate")
    @Getter @Setter
    private Integer starRate;
    @Column(name = "content")
    @Getter @Setter
    private String content;
    @JoinColumn(name = "author", referencedColumnName = "id")
    @ManyToOne
    @Getter @Setter
    private User author;
    @JoinColumn(name = "agency", referencedColumnName = "id")
    @ManyToOne
    @Getter
    @Setter
    private Agency agency;

    public ResponseAgency() {
    }

}
