package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "renewal_manage")
public class RenewalManage {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @JoinColumn(name = "agency_id", referencedColumnName = "id")
    @ManyToOne
    @Getter @Setter
    private Agency agency;

    @Column(name = "updated_date")
    @Getter @Setter
    private Date updatedDate;

    @Column(name = "expire_date")
    @Getter @Setter
    private Date expireDate;

    @Column(name = "is_remind")
    @Getter @Setter
    private Integer isRemind = 0;

    @Column(name = "is_deactivate")
    @Getter @Setter
    private Integer isDeactivate = 0;

    @OneToMany(mappedBy = "renewalManage",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @Getter @Setter
    private Set<RenewalOrder> renewalOrderSet;
}
