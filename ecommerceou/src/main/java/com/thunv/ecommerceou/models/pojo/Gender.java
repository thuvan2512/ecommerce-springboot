package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gender")
public class Gender {
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
    @OneToMany(mappedBy = "gender")
    @Getter
    @Setter
    @JsonIgnore
    private Set<User> userSet;

    public Gender() {
    }

    public Gender(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
