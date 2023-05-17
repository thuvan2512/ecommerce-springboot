package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "agent_field")
public class AgentField {
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

    @Column(name = "name_en")
    @Getter @Setter
    private String nameEn;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    @Getter
    @Setter
    private Set<Agency> agencySet;

    public AgentField() {
    }

    public AgentField(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
