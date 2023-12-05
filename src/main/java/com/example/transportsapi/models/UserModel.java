package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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



}
