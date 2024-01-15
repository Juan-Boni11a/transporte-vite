package com.example.transportsapi.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_roles")
@ToString
@EqualsAndHashCode
public class UserRoleModel {


    @Id
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;


    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;

}
