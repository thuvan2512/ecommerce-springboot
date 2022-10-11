package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;
    @JoinColumn(name = "author", referencedColumnName = "id")
    @ManyToOne
    @Getter
    @Setter
    private User author;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.REMOVE, orphanRemoval = true,
    fetch = FetchType.EAGER)
    @Getter @Setter
    @JsonIgnoreProperties({"cart"})
    private Set<CartItem> cartItemSet;
}
