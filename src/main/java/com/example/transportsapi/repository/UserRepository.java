package com.example.transportsapi.repository;

import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT u FROM UserModel u " +
            "JOIN UserRoleModel ur ON u.id = ur.user.id " +
            "JOIN RoleModel r ON ur.role.id = r.id " +
            "WHERE r.id = :roleId")
    List<UserModel> findByRoleId(@Param("roleId") Long roleId);





    @Query("SELECT u, mr FROM UserModel u " +
            "JOIN UserRoleModel ur ON u.id = ur.user.id " +
            "JOIN MovilizationRequestModel mr ON u.id = mr.driver.id " +
            "WHERE ur.role.id = 3 " +
            "AND :now >= CONCAT(mr.emitDate, ' ', mr.emitHour) " +
            "AND :now <= CONCAT(mr.expiryDate, ' ', mr.expiryHour) "
            )
    List<UserModel> findBusyDrivers(@Param("now") String now);


    @Query("SELECT u, mr FROM UserModel u " +
            "JOIN UserRoleModel ur ON u.id = ur.user.id " +
            "LEFT JOIN MovilizationRequestModel mr ON u.id = mr.driver.id " +
            "WHERE ur.role.id = 3 " +
            "AND (:now < CONCAT(mr.emitDate, ' ', mr.emitHour) OR :now > CONCAT(mr.expiryDate, ' ', mr.expiryHour) OR mr.id IS NULL)"
    )
    List<UserModel> findFreeDrivers(@Param("now") String now);


    @Query( "SELECT u FROM UserModel u " +
            "JOIN UserRoleModel ur ON u.id = ur.user.id " +
            "JOIN MaintenanceRequestModel ma ON u.id = ma.driver.id " +
            "WHERE ur.role.id = 3 " +
            "AND ma.status != 'TALLER'")
    List<UserModel> findDriversFreeOfMaintenance();


    @Query( "SELECT u FROM UserModel u " +
            "JOIN UserRoleModel ur ON u.id = ur.user.id " +
            "JOIN MaintenanceRequestModel ma ON u.id = ma.driver.id " +
            "WHERE ur.role.id = 3 " +
            "AND ma.status = 'TALLER'")
    List<UserModel> findBusyDriversOfMaintenance();


    @Query("SELECT u, mr FROM UserModel u " +
            "JOIN UserRoleModel ur ON u.id = ur.user.id " +
            "JOIN MovilizationRequestModel mr ON u.id = mr.driver.id " +
            "WHERE ur.role.id = 3 " +
            "AND :now >= CONCAT(mr.emitDate, ' ', mr.emitHour) " +
            "AND :now <= CONCAT(mr.expiryDate, ' ', mr.expiryHour) "
    )
    List<Object[]> findBusyDriversToTable(@Param("now") String now);

}
