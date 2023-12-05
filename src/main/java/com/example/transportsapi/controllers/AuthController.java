package com.example.transportsapi.controllers;

import com.example.transportsapi.dao.UserDao;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private UserDao userDao;


    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="api/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody UserModel user) {

        UserModel authenticatedUser = userDao.getUserByCredentials(user);

        if(authenticatedUser!= null){
            String token = jwtUtil.create( String.valueOf(authenticatedUser.getId()), authenticatedUser.getEmail());
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userData", authenticatedUser);
            return map;
        }

        HashMap<String, Object> map = new HashMap<>();
        UserModel emptyUser = new UserModel();
        map.put("token", "");
        map.put("userData", emptyUser);
        return map;

    }

}
