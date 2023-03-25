package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "manage_error_logs")
public class ManageErrorLog {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "type_log")
    @Getter @Setter
    private String typeLog;

    @Column(name = "details")
    @Getter @Setter
    private String details;

    @Column(name = "is_resolve")
    @Getter @Setter
    private Integer isResolve = 0;

    @Column(name = "created_date")
    @Getter @Setter
    private Date createdDate = new Date();
}
