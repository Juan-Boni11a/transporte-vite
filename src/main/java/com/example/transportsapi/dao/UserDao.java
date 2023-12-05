package com.example.transportsapi.dao;

import com.example.transportsapi.models.UserModel;

import java.util.List;

public interface UserDao  {
    List<UserModel> getUsers();

    void deleteUser(Long id);

    void addUser(UserModel user);

    UserModel getUserByCredentials(UserModel user);
}
