package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@ToString @EqualsAndHashCode
public class UserModel {

    @Id
    @Getter @Setter @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter @Column(name="name")
    private String name;
    @Getter @Setter @Column(name="lastname")
    private String lastname;
    @Getter @Setter @Column(name="ci")
    private String ci;
    @Getter @Setter @Column(name="phone_number")
    private String phone_number;
    @Getter @Setter @Column(name="email")
    private String email;
    @Getter @Setter @Column(name="password")
    private String password;


    @Getter @Setter @Column(name="license_type")
    private String licenseType;

    @Getter
    @Setter
    @Column(name = "licence_expiry_date")
    private LocalDate licenceExpiryDate;



    /*
    @Getter @Setter
    @ManyToMany(
            fetch= FetchType.EAGER,
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleModel> roles;
    */

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "role_id", columnDefinition = "INT DEFAULT 2")
    private RoleModel role;

}
