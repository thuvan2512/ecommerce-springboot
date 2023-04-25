package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "renewal_package")
public class RenewalPackage {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "package_name")
    @Getter @Setter
    private String packageName;

    @Column(name = "number_of_days_available")
    @Getter @Setter
    private Integer numberOfDaysAvailable;

    @Column(name = "usual_price")
    @Getter @Setter
    private Integer usualPrice;

    @Column(name = "discount_price")
    @Getter @Setter
    private Integer discountPrice;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    @OneToMany(mappedBy = "renewalPackage",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<RenewalOrder> renewalOrderSet;
}
