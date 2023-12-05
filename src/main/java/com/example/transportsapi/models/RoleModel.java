package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name="roles")
@ToString @EqualsAndHashCode
public class RoleModel {

    @Id
    @Getter @Setter @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter @Column(name="name")
    private String name;


    @Getter @Setter
    @ManyToMany(
            fetch= FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Set<PermissionModel> permissions;

    /*
    @Getter @Setter
    @ManyToMany( fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<UserModel> users;
    */
}
