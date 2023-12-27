package com.example.transportsapi.controllers;

import com.example.transportsapi.dao.UserDao;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.repository.UserRepository;
import com.example.transportsapi.service.UserService;
import com.example.transportsapi.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        if(!verifyToken(token)) { return null; }
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




}
