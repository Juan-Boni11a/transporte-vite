package com.example.transportsapi.controllers;

import com.example.transportsapi.dao.UserDao;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.repository.UserRepository;
import com.example.transportsapi.service.UserService;
import com.example.transportsapi.utils.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserDao userDao;


    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;


    @RequestMapping(value="api/users", method = RequestMethod.GET)
    public List<UserModel> getUsers(@RequestHeader(value = "Authorization") String token) {
        System.out.println("HOLAAA");
        return userService.getAllUsers();
    }

    private  boolean verifyToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId!=null;
    }

    @RequestMapping(value="api/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        userDao.deleteUser(id);
    }

    @RequestMapping(value="api/users", method = RequestMethod.POST)
    public UserModel addUser(@RequestBody  UserModel user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);
        userDao.addUser(user);
        return user;
    }

    @RequestMapping(value="api/users/{userId}/roles/{roleId}", method = RequestMethod.POST)
    public UserModel addRoleToUser(@PathVariable  Long userId, @PathVariable Long roleId) {
        return userService.addRoleToUser(userId, roleId);
    }

    @RequestMapping(value="api/users/{userId}/driver", method = RequestMethod.PUT)
    public UserModel addRoleToUser(@PathVariable  Integer userId, @RequestBody UserModel userRequest) {
        UserModel userFound = userService.getById(userId);

        userFound.setId(Long.valueOf(userId));
        userFound.setLicenseType(userRequest.getLicenseType());
        userFound.setLicenceExpiryDate(userRequest.getLicenceExpiryDate());

        return userService.update(userFound);
    }


    @RequestMapping(value="api/users/busyDrivers", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<UserModel>>>  getBusyDrivers() {
        //return userService.getBusyDrivers();
        List<UserModel> busyOfMr = userService.getBusyDrivers();
        List<UserModel> busyOfMa = userService.getDriversBusyOfMaintenance();

        // Combina las listas y elimina duplicados usando un Set
        Set<UserModel> combinedSet = new HashSet<>(busyOfMr);
        combinedSet.addAll(busyOfMa);


        List<Long> userIds = new ArrayList<>();
        for(UserModel user: combinedSet){
            userIds.add(user.getId());
        }

        List<UserModel> allDrivers = userService.getUsersByRoleId(3L);

        List<UserModel> freeUsers = new ArrayList<>();

        for (UserModel user : allDrivers) {
            if (!userIds.contains(user.getId())) {
                freeUsers.add(user);
            }
        }

        // Crear el objeto JSON
        int busyDriversCount = combinedSet.size();
        int freeDriversCount = freeUsers.size();


        Map<String, List<UserModel>> response = new HashMap<>();

        response.put("busyDrivers", new ArrayList<>(combinedSet));
        response.put("freeDrivers", freeUsers);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping(value="api/users/freeDrivers", method = RequestMethod.GET)
    public List<UserModel> getFreeDrivers() {
        // Llamadas a las consultas
        List<UserModel> freeDrivers = userService.getFreeDrivers();
        List<UserModel> driversFreeOfMaintenance = userService.getDriversFreeOfMaintenance();




        // Combina las listas y elimina duplicados usando un Set
        Set<UserModel> combinedSet = new HashSet<>(freeDrivers);
        combinedSet.addAll(driversFreeOfMaintenance);



        // Convierte el Set de nuevo a una Lista
        return new ArrayList<>(combinedSet);
    }



    @RequestMapping(value="api/users/driversInMovilization", method = RequestMethod.GET)
    public List<Object[]>  getDriversInMovilization() {
        return userService.getDriversInMovilization();
    }





}
