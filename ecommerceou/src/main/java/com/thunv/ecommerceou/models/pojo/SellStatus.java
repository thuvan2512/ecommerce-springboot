package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sell_status")
public class SellStatus {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "name")
    @Getter @Setter
    private String name;
    @OneToMany(mappedBy = "sellStatus")
//    @JsonIgnoreProperties({"field"})
    @Getter
    @Setter
    @JsonIgnore
    private Set<SalePost> salePostSet;

    public SellStatus() {
    }

    public SellStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
