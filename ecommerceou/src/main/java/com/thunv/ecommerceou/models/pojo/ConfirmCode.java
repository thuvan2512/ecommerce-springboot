package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "confirm_code")
public class ConfirmCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;
    @Column(name = "code")
    @Getter @Setter
    private String code;
    @OneToOne
    @JoinColumn(name = "author",referencedColumnName = "id")
    @Getter @Setter
    private User author;

}
