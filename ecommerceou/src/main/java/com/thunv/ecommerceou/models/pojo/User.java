package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;
    @Column(name = "active")
    @Getter
    @Setter
    private Integer isActive = 1;
    @Column(name = "avatar")
    @Getter @Setter
    private String avatar;
    @Basic(optional = false)
    @Column(name = "username")
    @Getter @Setter
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    @Setter @Getter
    @JsonIgnore
    private String password;
    @Column(name = "first_name")
    @Getter @Setter
    private String firstName;
    @Column(name = "last_name")
    @Getter @Setter
    private String lastName;
    @Basic(optional = false)
    @Column(name = "email")
    @Getter @Setter
    private String email;
    @Column(name = "phone")
    @Getter @Setter
    private String phone;
    @Column(name = "address")
    @Getter @Setter
    private String address;
    @Column(name = "joined_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date joinedDate;
    @OneToMany(mappedBy = "manager",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<Agency> agencySet;
    @ManyToOne
    @JoinColumn(name = "role",referencedColumnName = "id")
    @Getter @Setter
    private Role role;
    @ManyToOne
    @JoinColumn(name = "gender",referencedColumnName = "id")
    @Getter @Setter
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "auth_provider",referencedColumnName = "id")
    @Getter @Setter
    private AuthProvider authProvider;
    @OneToOne(mappedBy = "author",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private ConfirmCode confirmCode;
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<CommentPost> commentPostSet;
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<Orders> ordersSet;
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<LikePost> likePostSet;
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<FollowAgency> followAgencySet;
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<ResponseAgency> responseAgencySet;
    @OneToMany(mappedBy = "manager",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<CensorshipAgency> censorshipAgencyManagerSet;
    @OneToMany(mappedBy = "censor",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<CensorshipAgency> censorshipAgencyCensorshipSet;
    @OneToMany(mappedBy = "author",cascade = CascadeType.REMOVE, orphanRemoval = true,
    fetch = FetchType.EAGER)
    @JsonIgnore
    @Getter @Setter
    private Set<Cart> cartSet;


    public User() {
    }

    public User(int id, String avatar, String username, String firstName, String lastName, String email, String phone, String address) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
