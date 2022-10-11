package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "name")
    @Getter @Setter
    @Size(min = 1,max = 100,message = "Must be between 1 and 100 characters")
    private String name;
    @Column(name = "avatar")
    @Getter @Setter
    private String avatar;
    @JsonIgnore
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
//    @JsonIgnoreProperties({"field"})
    @Getter
    @Setter
    private Set<SalePost> salePostSet;

    public Category() {
    }

    public Category(Integer id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
