package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "censorship_agency")
public class CensorshipAgency {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date createdDate;
    @Column(name = "censored_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date censoredDate;
    @ManyToOne
    @JoinColumn(name = "manager",referencedColumnName = "id")
    @Getter @Setter
    private User manager;
    @ManyToOne
    @JoinColumn(name = "censor",referencedColumnName = "id")
    @Getter @Setter
    private User censor;
    @ManyToOne
    @JoinColumn(name = "agency",referencedColumnName = "id")
    @Getter
    @Setter
    private Agency agency;

    public CensorshipAgency() {
    }
}
