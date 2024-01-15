package com.example.transportsapi.repository;

import com.example.transportsapi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT u FROM UserModel u " +
            "JOIN UserRoleModel ur ON u.id = ur.user.id " +
            "JOIN RoleModel r ON ur.role.id = r.id " +
            "WHERE r.id = :roleId")
    List<UserModel> findByRoleId(@Param("roleId") Long roleId);
}
