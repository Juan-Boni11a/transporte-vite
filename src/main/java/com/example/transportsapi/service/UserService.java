package com.example.transportsapi.service;

import com.example.transportsapi.models.RoleModel;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.repository.RoleRepository;
import com.example.transportsapi.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public UserModel addRoleToUser(Long userId, Long roleId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        RoleModel role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);

        return user;
    }

    public List<UserModel> getUsersByRoleId(Long roleId) {
        return userRepository.findByRoleId(roleId);
    }


    public List<UserModel> getBusyDrivers() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = now.format(formatter);
        System.out.println(nowAsString);
        return userRepository.findBusyDrivers(nowAsString);
    }

    public List<UserModel> getFreeDrivers() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = now.format(formatter);

        return userRepository.findFreeDrivers(nowAsString);
    }

    public List<UserModel> getDriversFreeOfMaintenance() {
        return userRepository.findDriversFreeOfMaintenance();
    }

    public List<UserModel> getDriversBusyOfMaintenance() {
        return userRepository.findBusyDriversOfMaintenance();
    }


    public List<Object[]> getDriversInMovilization() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = now.format(formatter);

        return userRepository.findBusyDriversToTable(nowAsString);
    }


}
